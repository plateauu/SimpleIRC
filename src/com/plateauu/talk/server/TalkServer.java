package com.plateauu.talk.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/*
 * TODO: Check why it doesn't remove names from namesList
 */

public class TalkServer {

    private int port = 13333;
    private BufferedReader in;
    private PrintWriter out;
    private List<PrintWriter> usersOutStreams;
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
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            usersOutStreams.add(out);

            Thread t = createThread(clientSocket, counter);

            System.out.println("[SERVER]" + t.getName() + " Connected");
            out.println("Welcome to localhost");
        }

    }

    private Thread createThread(Socket clientSocket, int counter) {
        Runnable newUser = new UserService(clientSocket, out, this);
        Thread t = new Thread(newUser);
        t.setName(++counter + " user");
        t.start();
        return t;
    }

    public List<String> getNamesList() {
        return namesList;
    }
    
    

    public void addName(String name) {
        this.namesList.add(name);
    }

    public void broadcastToAll(String[] messageArray) {
        if (messageArray.length > 1) {
            for (PrintWriter userOutput : usersOutStreams) {
                userOutput.println("<" + messageArray[0] + "> " + messageArray[1]);
            }
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
