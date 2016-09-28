package com.plateauu.talk.client;

import java.io.BufferedReader;
import java.net.Socket;

public class CommunicationReciever implements Runnable {

    private BufferedReader in;
    private TalkClient client;

    public CommunicationReciever(BufferedReader in, TalkClient client, Socket clientSocket) {
        this.in = in;
        this.client = client;
    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                boolean command = recieveCommands(message);
                if (!command) {
                    System.out.println(message);
                }
            }
        } catch (Exception e) {
            System.out.println("Oups");
        }
    }

    private boolean recieveCommands(String message) {
        String[] command = message.split("//");
        if (command[0].equals("commands")) {
            switch (command[1].toLowerCase()) {
                case "name":
                    client.setName(command[2]);
                    System.out.println("Switched name to " + client.getName());
                    return true;
                case "list":
                    new RecieveList(command[2]);
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }
}
