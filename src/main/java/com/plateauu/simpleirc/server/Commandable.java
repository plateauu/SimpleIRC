package com.plateauu.simpleirc.server;

import com.plateauu.simpleirc.repository.Message;

public interface Commandable {
    Message perform();
}
