package com.plateauu.talk.server;

import java.io.PrintWriter;

public class CommandChangeName implements Commandable {

	String[] messageArray;
	String actualName;
	String newName;
	TalkServer server;

	public CommandChangeName(String[] messageArray, String actualName, String newName, TalkServer server) {
		super();
		this.messageArray = messageArray;
		this.actualName = actualName;
		this.newName = newName;
		this.server = server;
	}

	@Override
	public String makeAnAction() {
		int index = server.getUserNameIndex(newName);
		int actualNameIndex = server.getUserNameIndex(actualName);
		
		if (index == -1) {
			if (actualNameIndex != -1) {
				server.getNamesList().remove(actualNameIndex);
			}
			server.addName(newName);
			return "commands//name//" + newName;

		} else {
			System.out.println("[" + actualName + "]" + "Name " + messageArray[1] + " is not available");
			return ("[" + actualName + "]" + "Name " + messageArray[1] + " is not available");
		}
	}

}
