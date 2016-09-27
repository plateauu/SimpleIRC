package com.plateauu.talk.server;

import java.io.PrintWriter;

public class CommandName implements Commandable {

    String[] messageArray;
    String actualName;
    String newName;
    TalkServer server;

    public CommandName(String[] messageArray, String actualName, String newName, TalkServer server) {
        this.messageArray = messageArray;
        this.actualName = actualName;
        this.newName = newName;
        this.server = server;
    }

    @Override
    public String performCommand() {
        
        int actualNameIndex = server.getUserNameIndex(actualName);

        if (!server.isUserExists(newName)) {
            if (server.isUserExists(actualName)) {
                server.getNamesList().remove(actualNameIndex);
            }
            server.addName(newName);
            return "commands//name//" + newName;

        } else {
            return ("[" + actualName + "]" + "Name " + messageArray[1] + " is not available");
        }
    }

}
