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

    public UserService(Socket clientSocket, ObjectOutputStream out, TalkServer server) {
        this.clientSocket = clientSocket;
        this.server = server;
        this.out = out;
    }

    @Override
    public void run() {
        Message message = null;

        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            while (true) {
                message = (Message) in.readObject();
                System.out.println("Incoming message: " + message.toString());
                resolveClientRequest(message);
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    private void resolveClientRequest(Message message) throws IOException {
        boolean isCommand = message.isCommand();
        if (isCommand) {
            Commands command = message.getCommand();
            switch (command) {
                case name:
                    Commandable changeName = new CommandName(message, server);
                    out.writeObject(changeName.performCommand());
                    break;
                case list:
                    Commandable list = new CommandList(server.getNamesList());
                    out.writeObject(list.performCommand());
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
