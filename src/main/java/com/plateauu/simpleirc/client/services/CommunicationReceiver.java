package com.plateauu.simpleirc.client.services;

import com.plateauu.simpleirc.client.TalkClient;
import com.plateauu.simpleirc.repository.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.format.DateTimeFormatter;

public class CommunicationReceiver implements Runnable {

    private ObjectInputStream in;
    private TalkClient client;
    private boolean isActive;
    private final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CommunicationReceiver(ObjectInputStream in, TalkClient client) {
        this.in = in;
        this.client = client;
        this.isActive = true;
    }

    @Override
    public void run() {
        Message message;
        try {
            while (isActive) {
                message = (Message) in.readObject();
                boolean isCommand = message.isCommand();
                if (!isCommand) {
                    printMessage(message);
                }
                if (isCommand) {
                    receiveCommands(message);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Oups");
            e.printStackTrace();
        }
    }

    public void receiveCommands(Message message) {
        switch (message.getCommand()) {
            case name:
                client.setName(message.getCommandParameter(0));
                System.out.println(getTimeStamp(message) + "Switched name to " + client.getName());
                break;
            case list:
                new RecieveList(message.getCommandParameter(0));
                break;
            case exit:
            case logout:
            case quit:
                System.out.println(getTimeStamp(message) + "Good bye");
                isActive = false;
                break;
            default:
                break;
        }
    }

    public String getTimeStamp(Message message) {
        return "[" + message.getTimeStamp().format(DEFAULT_FORMATTER)
                + "] ";
    }

    public void printMessage(Message message) {
        System.out.println(getTimeStamp(message)
                +"<" + message.getName() + "> " + message.getMessage());
    }



}
