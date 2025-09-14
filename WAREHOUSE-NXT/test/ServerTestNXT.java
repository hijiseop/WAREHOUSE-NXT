import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class ServerTestNXT {

	public static void main(String[] args) throws IOException {
		System.out.println("WAITING FOR CONNECT");
		BTConnection pc = Bluetooth.waitForConnection();
		System.out.println("CONNECTED");
		DataInputStream fromPC = pc.openDataInputStream();
		DataOutputStream toPC = pc.openDataOutputStream();
		
		int input = fromPC.readInt();
		toPC.writeInt(input);
		toPC.flush();
	}
}
