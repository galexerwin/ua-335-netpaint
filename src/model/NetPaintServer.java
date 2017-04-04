/*Author: Alex Erwin
 * 
 * 
 */
// package definition
package model;
// import classes
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import paintComponents.PaintObject;
// netpaint client
@SuppressWarnings("serial")
// server capture class
public class NetPaintServer implements Runnable {
	// instance variables
	private Socket connection;
	private Vector<PaintObject> mainCanvas;
	// main method that accepts connections
	public static void main(String[] args) {
		// variables
		Vector<PaintObject> mainCanvas = new Vector<>();
		// open a server socket to listen for new requests
		try (ServerSocket server = new ServerSocket(4000);) {
			// run until killed
			while(true) {
				// create a netpaintserver object
				NetPaintServer nps = new NetPaintServer(server.accept(), mainCanvas);
				// pass the netpaintserver object to a thread
				Thread client = new Thread(nps);
				// start the thread
				client.start();
			}
		} catch (Exception e) { e.printStackTrace(); }	
	}
	// constructor 
	public NetPaintServer(Socket connection, Vector<PaintObject> mainCanvas) {
		// add all for the run interface
		this.connection = connection;
		this.mainCanvas = mainCanvas;
	}
	// runnable interface
	public void run() {
		// read/write from the client
		try (
				ObjectOutputStream outputToClient = new ObjectOutputStream(connection.getOutputStream());
				ObjectInputStream inputFromClient = new ObjectInputStream(connection.getInputStream());				
			) {
			// add the paint object sent from the client
			mainCanvas.add((PaintObject)inputFromClient.readObject());
			// reset
			outputToClient.reset();
			// output the entire collection to everyone (Todo)
			outputToClient.writeObject(mainCanvas);
			// close connection
			connection.close();
		} catch (Exception e) { e.printStackTrace(); }
	}
}
