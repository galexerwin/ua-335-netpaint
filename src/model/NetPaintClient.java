/*Author: Alex Erwin
 *Purpose: Client Send 
 */
// package definition
package model;
// import classes
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.Color;
import java.util.Observable;
import java.util.Vector;
import paintComponents.PaintObject;
import paintComponents.Image;
import paintComponents.Line;
import paintComponents.Oval;
import paintComponents.Rectangle;
// netpaint client
@SuppressWarnings("serial")
public class NetPaintClient extends Observable {
	// instance variables
	ObjectOutputStream 	outputToServer;
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
		if (type.equals("Image"))
			drawThis = new Image(color, point1, point2);
		// try exporting to server
		try {
			// write
			outputToServer.writeObject(drawThis);
			// flush
			outputToServer.flush();
		} 
		catch (IOException e) { e.printStackTrace(); }
	}
	// get canvas
	public Vector<PaintObject> getCanvas() {
		// return the paint objects collection
		return this.localCopy;
	}
	// set the received data
	public void setLocalCopy(Vector<PaintObject> toLocal) {
		// get the data into the local
		this.localCopy = toLocal;
		// update
		setChanged(); // here because of Asynchronous operation
		// notify
		notifyObservers();		
	}
	// set the writer to use
	public void setServerWriteHandle(ObjectOutputStream outputToServer) {
		this.outputToServer = outputToServer;
	}
}
