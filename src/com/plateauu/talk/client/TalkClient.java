package com.plateauu.talk.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TalkClient {
	String name;
	String hostname;
	int portNumber;
	String fromServer, fromUser;
	
	public TalkClient(String hostname, int porttNumber, String name){
		this.hostname = hostname;
		this.portNumber = porttNumber;
		this.name = name;
		establishConnection();
	}
	
	private void establishConnection(){
		try {
			Socket clientSocket= new Socket(hostname, portNumber);
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("[TALK_CLIENT] Connection established");
			startTalking(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void startTalking(BufferedReader in, PrintWriter out){
		Scanner scan = new Scanner(System.in);
		
		try {
			while ((fromServer = in.readLine()) != null){
				System.out.println(fromServer);
				fromUser = scan.nextLine();
				if(fromUser != null){
					out.println(this.name + "//" + fromUser);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
