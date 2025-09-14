package com.team.network;
import java.awt.Point;
import java.io.*;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import com.team.routeplanning.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
public class Server {

	static DataInputStream input;
	static DataOutputStream output;
	final static Logger logger = Logger.getLogger(Server.class);
	public static void main(String[] args) throws NXTCommException, IOException {
		BasicConfigurator.configure();
//		NXTInfo robot =new NXTInfo(NXTCommFactory.BLUETOOTH, "Bob","0016530FF063");
		NXTInfo robot2 =new NXTInfo(NXTCommFactory.BLUETOOTH, "Ghicatron","0016530FD7F4");
		NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		
		if (nxtComm.open(robot2)) {
			logger.debug("Server connected to: " + robot2.name);
			input = new DataInputStream(nxtComm.getInputStream());
			output = new DataOutputStream(nxtComm.getOutputStream());
		}
		
		RoutePlanner router = new RoutePlanner();
		Route myRoute = router.calculateRoute(new Point(0,0), new Point(3,2));
		Integer[] directions = myRoute.getCommands();
		logger.debug("Server: Calculated pickup route");
		Route myDropOff = router.calculateRoute(new Point(3,2), new Point(11,7));
		Integer[] directionsToDrop = myDropOff.getCommands();
		logger.debug("Server: Calculated dropoff route");
		
		while(true) {
			String userInput = (new BufferedReader(new InputStreamReader(System.in))).readLine();
			if(userInput.equals("1")){
				logger.debug("Server: Sending Pickup commands");
				output.writeUTF(userInput);
				output.flush();
				output.writeInt(directions.length);
				for(int i=0;i<directions.length;i++) {
					output.writeInt(directions[i]);
					output.flush();
				}
			}else if(userInput.equals("2")){
				logger.debug("Server: sending drop off commands");
				output.writeUTF(userInput);
				output.flush();
				output.writeInt(directionsToDrop.length);
				for(int i=0;i<directionsToDrop.length;i++) {
					output.writeInt(directionsToDrop[i]);
					output.flush();
				}
			}
				
		}
	}


}
