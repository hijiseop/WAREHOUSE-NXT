package com.team.managementInterface;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class InterfaceBackground {
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private Interface gui;
	private String msg;
	
	public final void setGui(Interface gui) {
		this.gui = gui;
	}
	public void connect() {
		try {
			socket = new Socket("localhost",4444);
			System.out.println("Server connected");
	
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			
			out.writeUTF("Start");//need to change type
			System.out.println("Robot Start");
			
			while(in!=null) {
				msg=in.readUTF();//need to change type
				gui.appendMsg(msg);
			}
		
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[]args) {
		InterfaceBackground clientBackground = new InterfaceBackground();
		clientBackground.connect();
	}
	
}
