package com.plateauu.simpleirc.repository;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable{

    private String senderName;
    private Boolean isCommand;
    private Commands command;
    private List<String> commandArguments;
    private String message;

    public Message(String name, Boolean isCommand, Commands command, List<String> arguments) {
        this.senderName = name;
        this.isCommand = isCommand;
        this.command = command;
        this.commandArguments = arguments;
    }

    public Message(String name, Boolean isCommand, String message) {
        this.senderName = name;
        this.isCommand = isCommand;
        this.message = message;
    }

    public String getName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    } 

    public Boolean isCommand() {
        return isCommand;
    }

    public Commands getCommand() {
        return command;
    }

    public List<String> getCommandParameter() {
        return commandArguments;
    }

    public String getCommandParameter(int indexOf) {
        return commandArguments.get(indexOf);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message{" + "name=" + senderName + ", isCommand=" + isCommand + ", command=" + command + ", commandParameter=" + commandArguments + ", message=" + message + '}';
    }

}
