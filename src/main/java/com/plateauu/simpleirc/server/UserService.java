package com.plateauu.simpleirc.server;

import com.plateauu.simpleirc.repository.Message;
import com.plateauu.simpleirc.server.repository.MessageResolver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class UserService implements Runnable {

    private Socket clientSocket;
    private ObjectOutputStream out;
    private TalkServer server;
    private boolean isActive;

    UserService(Socket clientSocket, TalkServer server) {
        this.clientSocket = clientSocket;
        this.server = server;
        this.out = server.getOut();
        this.isActive = true;
    }

    @Override
    public void run() {
        Message message;

        try {

            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            while (isActive) {
                message = (Message) in.readObject();
                System.out.println("Incoming message: " + message.toString());
                MessageResolver clientResolver = MessageResolver.getInstance(out, server);
                clientResolver.resolveRequest(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

