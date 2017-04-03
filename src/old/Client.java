/* Author: Alex Erwin
 * Purpose: Quiz 11
 * 
 */
// import classes
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
// client side of network socket conversation
public class Client {
	public static void main(String[] args) {
		// variables
		String response = "";		
		// connect to a server and get the two streams from the server
		try (Socket server = new Socket("localhost", 4000);) {
			
			// make both connection streams available
			ObjectOutputStream outputToServer = new ObjectOutputStream(server.getOutputStream());
			ObjectInputStream inputFromServer = new ObjectInputStream(server.getInputStream());
			// capture keyboard io
			Scanner keyboard = new Scanner(System.in);
			// Do some IO with the server where both client and server have loops
			while(true) {
				// send a banner message
				System.out.println("Enter A Message:");
				// get the next line from the keyboard
				response = keyboard.nextLine();
				// write a response to the server
				outputToServer.writeObject(response);
				// read the response from the server
				try { System.out.println((String)inputFromServer.readObject());} 
				catch(Exception e) {}				
				// determine if user quit
				if (response.toLowerCase().equals("quit")) 
					break;
			}
			// close the connection to the server
			server.close();
			// close the io from keyboard
			keyboard.close();
			// respond
			System.out.println("You entered the magic word");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
