package com.plateauu.simpleirc.client;

import com.plateauu.simpleirc.repository.Commands;
import com.plateauu.simpleirc.repository.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            message = new Message(this.name, isCommand, messageBody);
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
        paramList = new ArrayList<>(Arrays.asList(messageArray));
        int commandIndex = findCommandIndex(paramList);
        if (commandIndex != -1) {
            paramList.remove(commandIndex);
        }
        return paramList;
    }

    private int findCommandIndex(List<String> paramList) {
        int index = -1;
        for (String param : paramList) {
            if (param.startsWith("/")) {
                index = paramList.indexOf(param);
                break;
            }

        }
        return index;
    }

}
