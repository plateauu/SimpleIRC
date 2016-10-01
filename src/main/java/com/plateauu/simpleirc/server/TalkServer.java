package com.plateauu.simpleirc.server;

import com.plateauu.simpleirc.repository.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TalkServer {

    private int port = 13333;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private List<ObjectOutputStream> usersOutStreams;
    private List<String> namesList;

    public TalkServer() throws IOException {
        establishServer();
    }

    public void establishServer() throws IOException {

        usersOutStreams = new ArrayList<>();
        namesList = new ArrayList<>();
        int counter = 0;

        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            usersOutStreams.add(out);

            Thread t = createThread(clientSocket, counter);

            System.out.println("[SERVER]" + t.getName() + " Connected");
            Message welcomeMessage = new Message("Server", Boolean.FALSE, "Welcome to localhost");
            out.writeObject(welcomeMessage);
        }
    }

    private Thread createThread(Socket clientSocket, int counter) {
        Runnable newUser = new UserService(clientSocket, out, this);
        Thread thread = new Thread(newUser);
        thread.setName(++counter + " user");
        thread.start();
        return thread;
    }

    public List<String> getNamesList() {
        return namesList;
    }

    public void addName(String name) {
        this.namesList.add(name);
    }

    public void broadcastToAll(Message message) {
        try {
            for (ObjectOutputStream userOutput : usersOutStreams) {
                userOutput.writeObject(message);
            }
        } catch (IOException ex) {
            Logger.getLogger(TalkServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


public int getUserNameIndex(String name) {
        int index = -1;
        for (String user : namesList) {
            if (user.equals(name)) {
                index = namesList.indexOf(user);
            }
        }
        return index;
    }

    boolean isUserExists(String name) {
        int index = getUserNameIndex(name);
        if (index == -1) {
            return false;
        } else {
            return true;
        }
    }

}
