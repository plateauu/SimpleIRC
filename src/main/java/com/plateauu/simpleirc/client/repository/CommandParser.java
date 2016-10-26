package com.plateauu.simpleirc.client.repository;

import com.plateauu.simpleirc.repository.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandParser {

    private Commands command;

    public CommandParser(String[] messageArray) {
        command = setCommand(messageArray);
    }

    public Commands getCommand() {
        return command;
    }

    private Commands setCommand(String[] messageParam) {
        switch (messageParam[0]) {
            case "/name":
                return Commands.name;
            case "/list":
                return Commands.list;
            case "/logout":
            case "/exit":
            case "/quit":
                return Commands.exit;
            case "/topic":
                return Commands.topic;
            default:
                return Commands.message;
            //TODO interfejs + lista
        }

    }


    protected List<String> parseArgumens(String[] messageArray) {
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
