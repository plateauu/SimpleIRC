package com.plateauu.simpleirc.client;

import com.plateauu.simpleirc.Commands;
import com.plateauu.simpleirc.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: make a message object (isCommand:Boolean, messageBody:String, Command:Enum

public class TalkClient {

    private String name;
    private final String hostname;
    private final int portNumber;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TalkClient(String hostname, int porttNumber, String name) {
        this.hostname = hostname;
        this.portNumber = porttNumber;
        this.name = name;
        establishConnection();
    }

    private void establishConnection() {
        try {
            Socket clientSocket = new Socket(hostname, portNumber);
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            in = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("[" + name + "] Connection established");

            Thread t = new Thread(new CommunicationReciever(in, this, clientSocket));
            t.start();

            logInServer();
            startTalking();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logInServer() {
        try {
            Message message = messageCreator("/name " + name);
            out.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(TalkClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void startTalking() {
        String messageBody;
        Scanner scan = new Scanner(System.in);

        while (true) {
            try {
                messageBody = scan.nextLine();
                if (messageBody.length() > 0) {
                    Message message = messageCreator(messageBody);
                    out.writeObject(message);
                }
            } catch (IOException ex) {
                Logger.getLogger(TalkClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Message messageCreator(String messageBody) {
        Message message = null;
        String[] messageArray = messageBody.split(" ");

        Boolean isCommand = checkIfcommand(messageBody);

        if (isCommand) {
            Commands command = setCommand(messageArray);
            List<String> arguments = parseArgumens(messageArray);
            message = new Message(this.name, isCommand, command, arguments);
        } else {
            String txtMessage = messageBody.substring(messageBody.indexOf(" "));
            message = new Message(this.name, isCommand, txtMessage);
        }
        return message;
    }

    private boolean checkIfcommand(String messageBody) {
        return messageBody.startsWith("/");
    }

    private Commands setCommand(String[] messageParam) {
        switch (messageParam[0]) {
            case "/name":
                return Commands.name;
            case "/list":
                return Commands.list;
            default:
                return Commands.message;
            //TODO interfejs + lista
            //TODO metoda na nieznaną komendę
        }

    }

    private List<String> parseArgumens(String[] messageArray) {

        List<String> paramList;
        paramList = Arrays.asList(messageArray);
        paramList.remove(0);
        return paramList;

    }

}
