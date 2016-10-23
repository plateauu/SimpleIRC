package com.plateauu.simpleirc.client.repository;

import com.plateauu.simpleirc.repository.Commands;
import com.plateauu.simpleirc.repository.Message;

import java.util.List;

public class MessageCreator {


    private Message message;
    private String name;


    public MessageCreator(String name, String messageBody) {
        this.name = name;
        this.message = create(messageBody);
    }

    public Message getMessage() {
        return message;
    }

    private Message create(String messageBody) {
        Message message;
        String[] messageArray = messageBody.split(" ");

        Boolean isCommand = checkIfcommand(messageBody);

        if (isCommand) {
            CommandParser commandParser = new CommandParser(messageArray);
            Commands command = commandParser.getCommand();
            List<String> arguments = commandParser.parseArgumens(messageArray);
            message = new Message(this.name, isCommand, command, arguments);

        } else {
            message = new Message(this.name, isCommand, messageBody);
        }

        return message;
    }

    private boolean checkIfcommand(String messageBody) {
        return messageBody.startsWith("/");
    }


}
