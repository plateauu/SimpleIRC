package com.plateauu.talk;

import com.plateauu.talk.client.TalkClient;

public class ClientTalkTester {
	public static void main(String[] args){
		TalkClient client = new TalkClient("127.0.0.1", 13333, "PLateauu");
	}
}