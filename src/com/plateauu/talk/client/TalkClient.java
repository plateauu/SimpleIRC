package com.plateauu.talk.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TalkClient {
	String hostname;
	int portNumber;
	String fromServer, fromUser;
	
	public TalkClient(String hostname, int porttNumber){
		this.hostname = hostname;
		this.portNumber = porttNumber;
		prepareConnection();
	}
	
	private void prepareConnection(){
		try {
			Socket clientSocket= new Socket(hostname, portNumber);
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out.println(">>  Connection establised");
			System.out.println("[CLIENT] Connection established");
			startTalking(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void startTalking(BufferedReader in, PrintWriter out){
		Scanner scan = new Scanner(System.in);
		try {
			while ((fromServer = in.readLine()) != null){
				System.out.println("<SERVER> " + fromServer);
				if(fromServer.equals("bye.")){
					break;
				}
				fromUser = scan.nextLine();
				if(fromUser != null){
					System.out.println("<CLIENT> "+ fromUser);
					out.println(fromUser);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
