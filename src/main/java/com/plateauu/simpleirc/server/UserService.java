package com.plateauu.simpleirc.server;

import com.plateauu.simpleirc.repository.Commands;
import com.plateauu.simpleirc.repository.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserService implements Runnable {

    Socket clientSocket;
    ObjectInputStream in;
    ObjectOutputStream out;
    TalkServer server;
    String userName;
    private boolean isActive;

    public UserService(Socket clientSocket, TalkServer server) {
        this.clientSocket = clientSocket;
        this.server = server;
        this.out = server.out;
        this.isActive = true;
    }

    @Override
    public void run() {
        Message message = null;

        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            while (isActive) {
                message = (Message) in.readObject();
                System.out.println("Incoming message: " + message.toString());
                resolveClientRequest(message);
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            server.removeOutputStream(out);
            e.printStackTrace();
        }
    }

    private void resolveClientRequest(Message message) throws IOException {
        Commandable command = null;
        boolean isCommand = message.isCommand();
        if (isCommand) {
            Commands commandList = message.getCommand();
            switch (commandList) {
                case name:
                    command = new CommandName(message, server);
                    break;
                case list:
                    command = new CommandList(server.getNamesList());
                    break;
                case exit:
                    command = new CommandExit(server, message.getName(), out);
                    break;
                default:
                    command = new CommandNull();
                    break;
            }

            out.writeObject(command.perform());

        }


        if (!isCommand) {
            server.broadcastToAll(message);
        }
    }
}
