/*Author: Alex Erwin
 * 
 * 
 */
// package definition
package controller;
// import classes
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.NetPaintClient;
import view.*;
// gui class
@SuppressWarnings("serial")
public class NetPaintGui extends JFrame {
	// instance variables
	private NetPaintClient npc;
	private DefaultView defView;
	private JPanel currentView;	
	private int width = 1100, height = 825;
	// new main method
	public static void main(String[] args) {
		// new gui
		NetPaintGui gui = new NetPaintGui();
		// make it visible
		gui.setVisible(true);		
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
