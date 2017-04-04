/*Author: Alex Erwin
 *Purpose: Server Device 
 */
// package definition
package model;
// import classes
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;
import paintComponents.PaintObject;
// netpaint client
@SuppressWarnings("serial,resource")
// server capture class
public class NetPaintServer implements Runnable {
	// instance variables
	private Socket connection;
	private ObjectInputStream inputFromClient;
	private static ArrayList<ObjectOutputStream> clientOutputStreams;
	private static Vector<PaintObject> mainCanvas;
	// main method that accepts connections
	public static void main(String[] args) {
		// pre-setup
		NetPaintServer.clientOutputStreams = new ArrayList<ObjectOutputStream>();
		NetPaintServer.mainCanvas = new Vector<>();
		// open a server socket to listen for new requests
		try (ServerSocket server = new ServerSocket(4000);) {
			// run until killed
			while(true) {
				// create a netpaintserver object
				NetPaintServer nps = new NetPaintServer(server.accept());
				// pass the netpaintserver object to a thread
				Thread client = new Thread(nps);
				// start the thread
				client.start();					
			}
		} catch (Exception e) { e.printStackTrace(); }	
	}	
	// constructor 
	public NetPaintServer(Socket connection) {
		// add all for the run interface
		this.connection = connection;
		// wrap
		try {
			// create an output stream
			ObjectOutputStream outputToClient = new ObjectOutputStream(this.connection.getOutputStream());
			// add client to streams out
			NetPaintServer.clientOutputStreams.add(outputToClient);
			// add the handle to the input stream
			inputFromClient = new ObjectInputStream(this.connection.getInputStream());
		} catch (IOException e) { e.printStackTrace(); }
	}
	// runnable interface
	public void run() { 
		// read/write from the client
		try {
			// loop
			while (true) {
				// add the paint object sent from the client
				NetPaintServer.mainCanvas.add((PaintObject)inputFromClient.readObject());
				// push the data to all clients
				pushToAllClients();
			}
		} catch (Exception e) { }
	}
	// method to push the data out to everyone
	public void pushToAllClients() {
		// iterate over the collection of connected clients
		for (ObjectOutputStream output : NetPaintServer.clientOutputStreams) {
			try {
				// reset
				output.reset();
				// output the entire collection to everyone
				output.writeObject(NetPaintServer.mainCanvas);
				// explicit flush
				output.flush();
			} catch (Exception e) { 
				// remove the stream
				NetPaintServer.clientOutputStreams.remove(output);
			}
		}
	}
}
