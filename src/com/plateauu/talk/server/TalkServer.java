package com.plateauu.talk.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.plateauu.talk.domain.Users;

public class TalkServer {

	int port = 13333;
	BufferedReader in;
	PrintWriter out;
	String inputLine, outputLine;
	List<Users> users;
	

	public TalkServer() {
		
		users = new ArrayList<>();

		try {
			establishServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		;
		
		

	}

	public void establishServer() throws IOException {
		BufferedReader in;
		PrintWriter out;
		
		ServerSocket serverSocket = new ServerSocket(port);
		Socket clientSocket = serverSocket.accept();
		
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		users.add(new Users("Name", in, out));
		
		System.out.println("[SERVER] Connection established");
		out.println("Connection established");
		startListen();
	} 	

	
	private void startListen() {
		try {
				while ((inputLine = in.readLine()) != null) {
				System.out.println("<CLIENT> " + inputLine );
				users.get(0).getOut().println("SERVER >> " + inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
