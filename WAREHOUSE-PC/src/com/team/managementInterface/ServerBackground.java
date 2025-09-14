package com.team.managementInterface;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException; 
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBackground {	//just Server Test
	private ServerSocket serverSocket;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private ServerTest gui;
	
	public final void setGui(ServerTest gui) {
		this.gui = gui;
	}
	
	public void setting() {
		try {
			serverSocket = new ServerSocket(4444);
			System.out.println("waiting to connection ..");
			socket = serverSocket.accept();
			System.out.println(socket.getInetAddress()+ " connected");
			
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		
			String msg =in.readUTF();
			System.out.println("Interface : " + msg);
			gui.appendMsg(msg);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ServerBackground serverBackground = new ServerBackground();
		serverBackground.setting();
	}

	public void sendMessage(String msg) {
		try {
		out.writeUTF(msg);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
