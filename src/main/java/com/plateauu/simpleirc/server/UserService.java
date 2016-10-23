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
        boolean isCommand = message.isCommand();
        if (isCommand) {
            Commands command = message.getCommand();
            switch (command) {
                case name:
                    Commandable changeName = new CommandName(message, server);
                    out.writeObject(changeName.perform());
                    break;
                case list:
                    Commandable list = new CommandList(server.getNamesList());
                    out.writeObject(list.perform());
                    break;
                case exit:
                    Commandable exit = new CommandExit(server, message.getName(), out);
                    out.writeObject(exit.perform());
                    break;
                default:
                    break;
            }
        }

        if (!isCommand) {
            server.broadcastToAll(message);
        }
    }
}
