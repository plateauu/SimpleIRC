package com.plateauu.simpleirc.client;

import com.plateauu.simpleirc.repository.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class CommunicationReciever implements Runnable {

    private ObjectInputStream in;
    private TalkClient client;
    private boolean isActive;

    public CommunicationReciever(ObjectInputStream in, TalkClient client, Socket clientSocket) {
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
                    recieveCommands(message);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Oups");
            e.printStackTrace();
        }
    }

    private void recieveCommands(Message message) {
        switch (message.getCommand()) {
            case name:
                client.setName(message.getCommandParameter(0));
                System.out.println(printTimeStamp(message) + "Switched name to " + client.getName());
                break;
            case list:
                new RecieveList(message.getCommandParameter(0));
                break;
            case exit:
                System.out.println(printTimeStamp(message) + "Good bye");
                isActive = false;
                break;
            default:
                break;
        }
    }

    private String printTimeStamp(Message message) {
        return "[" + message.getTimeStamp().format(client.getFormatter()) 
                + "] ";
    }

    private void printMessage(Message message) {
        System.out.println(printTimeStamp(message) 
                +"<" + message.getName() + "> " + message.getMessage());
    }

}
