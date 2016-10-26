package com.plateauu.simpleirc.server.services;

import com.plateauu.simpleirc.repository.Message;

public interface Commandable {
    Message perform();
}
