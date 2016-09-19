package com.plateauu.talk.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/*
 * TODO: change name implementation
 * 
 */

public class TalkClient {
	String name;
	String hostname;
	int portNumber;
	BufferedReader in;
	PrintWriter out;

	public TalkClient(String hostname, int porttNumber, String name) {
		this.hostname = hostname;
		this.portNumber = porttNumber;
		this.name = name;
		establishConnection();
	}

	public class ComunnicationReciever implements Runnable {

		@Override
		public void run() {
			String message;
			try {
				while ((message = in.readLine()) != null) {
					System.out.println(message);
				}
			} catch (Exception e) {
				System.err.println();
			}
		}
	}

	private void establishConnection() {
		try {
			Socket clientSocket = new Socket(hostname, portNumber);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("["+ name + "] Connection established");

			Thread t = new Thread(new ComunnicationReciever());
			t.start();
			
			sendName();
			
			startTalking();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendName() {
		out.println("/name " + name);
		
	}

	private void startTalking() {
		String message;
		Scanner scan = new Scanner(System.in);

		while (true) {
			message = scan.nextLine();
			if (message.length() > 0) {
				out.println(this.name + "//" + message);
			}
		}
	}
	
	

}
