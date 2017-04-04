/*Author: Alex Erwin
 * 
 * 
 */
// package definition
package model;
// import classes
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.Color;
import java.util.Observable;
import java.util.Vector;
import paintComponents.PaintObject;
import paintComponents.Line;
import paintComponents.Oval;
import paintComponents.Rectangle;
// netpaint client
@SuppressWarnings("serial")
public class NetPaintClient extends Observable {
	// instance variables
	Vector<PaintObject> localCopy;
	// constructor
	public NetPaintClient() { this.localCopy = new Vector<>(); }
	// draw object and save to server
	public void drawObjectToServer(String type, Point point1, Point point2, Color color) {
		// variables
		PaintObject drawThis = null;
		// determine what to draw
		if (type.equals("Line"))
			drawThis = new Line(color, point1, point2);
		if (type.equals("Rectangle"))
			drawThis = new Rectangle(color, point1, point2);		
		if (type.equals("Oval"))
			drawThis = new Oval(color, point1, point2);	
		// save this over the wire
		sendToServer(drawThis);
		// update
		setChanged();
		// notify
		notifyObservers();
	}
	// send the data to the server and write the response to the local copy
	@SuppressWarnings("unchecked")
	public void sendToServer(PaintObject drawing) {
		// connect to a server and get the two streams from the server
		try (
				Socket server = new Socket("localhost", 4000);
				ObjectOutputStream outputToServer = new ObjectOutputStream(server.getOutputStream());
				ObjectInputStream inputFromServer = new ObjectInputStream(server.getInputStream());				
			) {
				// write the drawing to the server
				outputToServer.writeObject(drawing);
				// read the collection of objects from the server
				try { this.localCopy = (Vector<PaintObject>)inputFromServer.readObject(); }
				catch(Exception e) { e.printStackTrace();}	
		} catch (IOException e) { e.printStackTrace(); }		
	}
	// get canvas
	public Vector<PaintObject> getCanvas() {
		// return the paint objects collection
		return this.localCopy;
	}
}
