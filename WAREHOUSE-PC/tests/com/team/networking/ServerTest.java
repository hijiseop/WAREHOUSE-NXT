package com.team.networking;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.junit.Test;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class ServerTest {
	static DataInputStream input;
	static DataOutputStream output;

	//Test 1 show that a connection can be established
	@Test
	public void connectionTest() throws NXTCommException, IOException{
		NXTInfo robot =new NXTInfo(NXTCommFactory.BLUETOOTH, "Ghicatron","0016530FD7F4");
		NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		if (nxtComm.open(robot)) {
			assert nxtComm.open(robot);
			input = new DataInputStream(nxtComm.getInputStream());
			output = new DataOutputStream(nxtComm.getOutputStream());
		}		
		output.writeInt(1);
		output.flush();
		int response = input.readInt();
		assert response == 1;

	}
	
	//Test 2 show that we can send messages to robot
	
	//Test 3 show that we can receive messages

}
