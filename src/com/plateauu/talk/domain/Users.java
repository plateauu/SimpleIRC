package com.plateauu.talk.domain;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Users {
	String name;
	BufferedReader in;
	PrintWriter out;
	
	public Users(String name, BufferedReader in, PrintWriter out){
		this.name = name;
		this.in = in;
		this.out = out;
		
	}

	public String getName() {
		return name;
	}

	public BufferedReader getIn() {
		return in;
	}

	public PrintWriter getOut() {
		return out;
	}
	
	
	
	
	
	
}
