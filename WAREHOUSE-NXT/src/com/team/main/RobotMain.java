package com.team.main;
import com.team.motion.*;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import rp.config.RobotConfigs;
import com.team.routeplanning.*;
import java.awt.Point;
import java.io.IOException;
import com.team.interfaces.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class RobotMain {
	
	
	public static void main(String[] args) throws IOException {
		//System.out.println("WAITING FOR BT");
		//BTConnection pc = Bluetooth.waitForConnection();
		//System.out.println("CONNECTED");
		//DataInputStream fromPC = pc.openDataInputStream();
		//DataOutputStream toPC = pc.openDataOutputStream();
		
		//String hi = fromPC.readUTF();
		//System.out.println(hi);
		
		Route myRoute;
		Button.waitForAnyPress();
		MotionController myMotion = new MotionController(RobotConfigs.EXPRESS_BOT, SensorPort.S2, SensorPort.S3);
		RoutePlanner router = new RoutePlanner();
		InterfaceController myScreen = new InterfaceController();
		
		myRoute = router.calculateRoute(new Point(0, 0), new Point(6, 0));
		myMotion.setDirs(myRoute.getCommands());
		myMotion.run();
		
		//The myMotion stuff doesn't work properly but interface is fine
		myScreen.setMaxItems(50);
		myScreen.pickUp(myScreen.getMaxItems());
		myRoute = router.calculateRoute(new Point(6, 0), new Point(6, 7));
		myMotion.setDirs(myRoute.getCommands());
		myMotion.run();

	}

}
