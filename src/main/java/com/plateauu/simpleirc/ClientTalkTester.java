package com.plateauu.simpleirc;

import com.plateauu.simpleirc.client.TalkClient;

public class ClientTalkTester {
	public static void main(String[] args){
		TalkClient client = new TalkClient("127.0.0.1", 13333, "PLateauu");
                Message message = new Message("Plateauu", Boolean.TRUE, "Message");
                
                
	}
}
