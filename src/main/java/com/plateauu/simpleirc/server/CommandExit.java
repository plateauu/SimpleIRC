/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plateauu.simpleirc.server;

import com.plateauu.simpleirc.repository.Commands;
import com.plateauu.simpleirc.repository.Message;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandExit implements Commandable {

    private TalkServer server;
    private String userName;
    private ObjectOutputStream out;

    public CommandExit(TalkServer server, String userName, ObjectOutputStream out) {
        this.server = server;
        this.out = out;
        this.userName = userName;
    }

    @Override
    public Message perform() {
        Message message;
        boolean isExists = server.isUserExists(userName);
        List<String> paramList = new ArrayList<>(Arrays.asList("Good Bye"));
        if (isExists) {
            server.removeOutputStream(out);
            server.getNamesList().remove(userName);
            server.broadcastToAll(new Message("Server", Boolean.FALSE, userName + " has been disconnected"));
            System.out.println("Disconnected: " + userName);
            message = new Message("Server", Boolean.TRUE, Commands.exit, paramList);
        } else {
            message = new Message("Server", Boolean.FALSE, "Can't disconnect");
        }
        return message;
    }

}
