package com.plateauu.simpleirc.server.services;

import com.plateauu.simpleirc.repository.Message;

/**
 * Created by plateauu on 23.10.16.
 */

public class CommandNull implements Commandable {
    @Override
    public Message perform() {
        return new Message("Server", Boolean.FALSE, "No such command");
    }
}
