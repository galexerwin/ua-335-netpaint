/* Author: Alex Erwin
 * Purpose: Quiz 11
 * 
 */
// import classes
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
// server side of network socket conversation
public class Server {
	public static void main(String[] args) {
		// variables
		String response = "";
		// open a server socket to listen for new requests
		try {
			ServerSocket server = new ServerSocket(4000); 
			Socket connection = server.accept();
			// make both connection streams available
			ObjectOutputStream outputToClient = new ObjectOutputStream(connection.getOutputStream());
			ObjectInputStream inputFromClient = new ObjectInputStream(connection.getInputStream());
			// loop until io is finished
			while (true) {
				// read from client
				try { response = (String) inputFromClient.readObject();	} 
				catch (Exception e) {}
				// log
				System.out.println("Server read: " + response);
				// respond to client
				outputToClient.writeObject("Hi client, you wrote: " + response);
				// determine if the user quit
				if (response.toLowerCase().equals("quit"))
					break;
			}
			// close the connection
			connection.close();
			// close the server
			server.close();
		} catch (Exception e) {}
	}
}
