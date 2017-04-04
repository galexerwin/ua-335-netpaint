/*Author: Alex Erwin
 *Purpose: GUI Entry 
 */
// package definition
package controller;
// import classes
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.NetPaintClient;
import paintComponents.PaintObject;
import paintComponents.Line;
import paintComponents.Oval;
import paintComponents.Rectangle;
import view.*;
// gui class
@SuppressWarnings("serial")
public class NetPaintGui extends JFrame {
	// instance variables
	private NetPaintClient npc;
	private DefaultView defView;
	private JPanel currentView;	
	private int width = 1100, height = 825;
	private ObjectOutputStream outputToServer;
	private ObjectInputStream inputFromServer;
	private Socket server;	
	// new main method
	public static void main(String[] args) {
		// new gui
		NetPaintGui gui = new NetPaintGui();
		// make it visible
		gui.setVisible(true);	
		
		gui.dataReader();
	}
	// GUI constructor
	public NetPaintGui() {
		// define the window properties
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Netpaint Client"); // set title
		this.setSize(this.width, this.height); // size and location
		this.setLocation(50,50);
		// new model
		npc = new NetPaintClient();
		// new view
		defView = new DefaultView(this.npc, this.width, this.height);
		// add observers
		npc.addObserver(defView);
		// set the default view
		setViewTo(defView);
		// make a persistant connection to the server
		makePersistantConnection();
		// notify the model we have a connection
		npc.setServerWriteHandle(this.outputToServer);
	}	
	// make a server connection
	private void makePersistantConnection() {
		// connect to a server and get the two streams from the server
		try {
			this.server = new Socket("localhost", 4000);
			this.outputToServer = new ObjectOutputStream(this.server.getOutputStream());
			this.inputFromServer = new ObjectInputStream(this.server.getInputStream());			
		} catch (IOException e) { 
			JOptionPane.showMessageDialog(null, "Could not find server!");
			System.exit(0);
		}
	}
	// data server read
	public void dataReader() {
		// vector to receive
		Vector<PaintObject> data = null;
		// loop until shut down
		while (true) {
			// wrap
			try {
				// capture the input data
				data = (Vector<PaintObject>)inputFromServer.readObject();
				// pass the data to the net paint client
				npc.setLocalCopy(data);
			} catch (Exception e) { 
				e.printStackTrace(); 
				System.exit(0);
			}
		}		
	}
	// set view, for which iteration
	private void setViewTo(JPanel newView) {
		if (currentView != null)
		  remove(currentView);
		currentView = newView;
		add(currentView);
		currentView.repaint();
		validate();
	}	
}
