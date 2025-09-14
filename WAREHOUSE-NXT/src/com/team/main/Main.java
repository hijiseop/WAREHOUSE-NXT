package com.team.main;
import com.team.motion.*;

import lejos.nxt.SensorPort;
import rp.config.RobotConfigs;
import java.awt.Point;
import java.io.IOException;
import com.team.interfaces.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Main {
	
	
	public static void main(String[] args) throws IOException {
		//Sets up BT connection
		System.out.println("WAITING FOR BT CONN");
		BTConnection pc = Bluetooth.waitForConnection();
		System.out.println("CONNECTED");
		DataInputStream fromPC = pc.openDataInputStream();
		DataOutputStream toPC = pc.openDataOutputStream();
		InterfaceController myScreen = new InterfaceController();
		MotionController myMotion = new MotionController(RobotConfigs.EXPRESS_BOT, SensorPort.S2, SensorPort.S3);
		
		Integer[] array;
		while(true) {
			String command = fromPC.readUTF();
			if(command.equals("1")) {
				myScreen.setMaxItems(50);
				
				int length = fromPC.readInt();
				array = new Integer[length];
				for(int a=0;a<length;a++) {
					int c = fromPC.readInt();
					array[a] = c;
				}

				myMotion.setDirs(array);
				myMotion.run();
				myScreen.pickUp(myScreen.getMaxItems());
//				myScreen.controller();
				
				
			}if(command.equals("2")){
//				InterfaceController.setStatus("DropOff");
				int length = fromPC.readInt();
				array = new Integer[length];
				for(int a=0;a<length;a++) {
					int c = fromPC.readInt();
					array[a] = c;
				}
				myMotion.setDirs(array);
				myMotion.run();
				myScreen.dropOff(myScreen.getNumItems());
//				myScreen.controller();
			}
		}
		
//		Button.waitForAnyPress();
//		InterfaceController myScreen = new InterfaceController();
//		myScreen.controller();
//		myRoute = router.calculateRoute(new Point(0, 0), new Point(6, 0));
//		myMotion.setDirs(myRoute.getCommands());
//		myMotion.run();
//		InterfaceController.setStatus("PickUp");
//		myScreen.controller();
//		InterfaceController.setStatus("DropOff");
//		myRoute = router.calculateRoute(new Point(6, 0), new Point(6, 7));
//		myMotion.setDirs(myRoute.getCommands());
//		myMotion.run();
//		myScreen.controller();
	}

}
