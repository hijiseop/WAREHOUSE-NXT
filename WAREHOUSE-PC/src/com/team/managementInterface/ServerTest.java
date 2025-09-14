package com.team.managementInterface;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//import com.sun.corba.se.spi.orbutil.fsm.Action;
//
//import javafx.scene.layout.Border;

public class ServerTest extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 234091271546264764L;
	private JTextArea jta = new JTextArea(40,35);
	private JTextField jtf = new JTextField(35);
	private ServerBackground server = new ServerBackground();
	
	public ServerTest() {
		
		add(jta, BorderLayout.CENTER);
		add(jtf, BorderLayout.SOUTH);
		jtf.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200, 100, 400, 600);
		setTitle("Server Test Gui");	
		
		server.setGui(this);
		server.setting();
		
	}
	
	public static void main(String[]args) {
		new ServerTest();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = jtf.getText()+"\n";
		jta.append(msg);
		System.out.println(msg);
		
		server.sendMessage(msg);
		
		jtf.setText("");
	}
	
	public void appendMsg(String msg) {
		jta.append(msg);
		System.out.println(msg);
	}
}
