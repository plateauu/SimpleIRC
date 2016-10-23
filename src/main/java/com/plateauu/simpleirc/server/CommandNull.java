package com.plateauu.simpleirc.server;

import com.plateauu.simpleirc.repository.Message;

/**
 * Created by plateauu on 23.10.16.
 */

class CommandNull implements Commandable {
    @Override
    public Message perform() {
        return new Message("Server", Boolean.FALSE, "No such command");
    }
}
