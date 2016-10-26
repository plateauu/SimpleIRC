package com.plateauu.simpleirc.server;

import com.plateauu.simpleirc.repository.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TalkServer {

    private int port = 13333;
    private ObjectOutputStream out;
    private List<ObjectOutputStream> usersOutStreams;
    private List<String> namesList;
    private String channelTopic;

    public TalkServer() throws IOException {
        establishServer();
    }

    private void establishServer() throws IOException {

        usersOutStreams = new ArrayList<>();
        namesList = new ArrayList<>();
        channelTopic = "Temporary topic...";
        int counter = 0;

        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            usersOutStreams.add(out);

            Thread t = createThread(clientSocket, counter);

            System.out.println("[SERVER]" + t.getName() + " has connected");
            Message welcomeMessage = new Message("Server", Boolean.FALSE, "Welcome to localhost.server");
            out.writeObject(welcomeMessage);
            welcomeMessage = new Message("Server", Boolean.FALSE, "Topic is: [" + channelTopic + "]");
            out.writeObject(welcomeMessage);

        }
    }

    private Thread createThread(Socket clientSocket, int counter) {
        Runnable newUser = new UserService(clientSocket, this);
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
                System.out.println("Broadcast message: " + message.toString());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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

    public boolean isUserExists(String name) {
        int index = getUserNameIndex(name);
        if (index == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean removeOutputStream(ObjectOutputStream out) {
        return usersOutStreams.remove(out);
    }

    ObjectOutputStream getOut() {
        return out;
    }

    public void setChannelTopic(String channelTopic) {
        this.channelTopic = channelTopic;
    }
}
