/*Author: Alex Erwin
 * 
 * 
 */
// package definition
package view;
// import classes
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.*;
import model.*;
import paintComponents.*;
//gui view implementation
@SuppressWarnings("serial")
public class DefaultView extends JPanel implements Observer, MouseListener {
	// instance variables
	NetPaintClient 		npc;
	JScrollPane 		viewport;
	JColorChooser 		colorOption; 
	ButtonGroup 		shapeOption;
	Point 				point1, point2;
	int 				width, height;
	// constructor
	public DefaultView(NetPaintClient npc, int width, int height) {
		// set defaults
		this.npc = npc;
		this.height = height;
		this.width = width;
		// setup the view
		initializeView();		
	}
	// initialize the view
	private void initializeView() {
		// set the layout
		this.setLayout(new GridLayout(3,1));
		// set up the scroll pane for viewport
		this.viewport = new JScrollPane();
		// add a mouse listener to the scroll pane
		this.viewport.addMouseListener(this);
		// get the user's selected color
		this.colorOption = new JColorChooser();
		// add elements
		add(this.viewport);
		add(getRadioGroup());
		add(this.colorOption);
	}
	// radio group
	private JPanel getRadioGroup() {
		// variables
		JPanel canvas = new JPanel();
		JRadioButton bttnLine, bttnRect, bttnOval, bttnJImg;
		ArrayList<String> names = new ArrayList<String>(Arrays.asList("Line", "Rectangle", "Oval", "Image"));
		// create the radio buttons
		bttnLine = new JRadioButton(names.get(0));
		bttnLine.setActionCommand(names.get(0));
		bttnLine.setSelected(true);
		bttnRect = new JRadioButton(names.get(1));
		bttnRect.setActionCommand(names.get(1));
		bttnOval = new JRadioButton(names.get(2));
		bttnOval.setActionCommand(names.get(2));
		bttnJImg = new JRadioButton(names.get(3));
		bttnJImg.setActionCommand(names.get(3));
		// add the buttons to a group
		this.shapeOption = new ButtonGroup();
		this.shapeOption.add(bttnLine);
		this.shapeOption.add(bttnRect);
		this.shapeOption.add(bttnOval);
		this.shapeOption.add(bttnJImg);
		// add
		canvas.add(bttnLine);
		canvas.add(bttnRect);
		canvas.add(bttnOval);
		canvas.add(bttnJImg);
		// return the buttons
		return canvas;
	}
	// execute the action
	private void executeAction() {
		System.out.println("X1");
		// send selection and points
		npc.drawObjectToServer(this.shapeOption.getSelection().getActionCommand(), point1, point2, this.colorOption.getColor());
		// reset points
		point1 = null;
		point2 = null;
	}
	// when update is called
	@Override
	public void update(Observable arg0, Object arg1) {
		// repaint the view port on update
		this.viewport.repaint();
	}
    public void paintComponent(Graphics g) {
    	// get the paint objects
    	Vector<PaintObject> allPaintObjects = npc.getCanvas();
    	// call super repaint for gui elements
    	super.paintComponent(g);
        // draw all of the paint objects
    	//if (allPaintObjects.size() != 0)
        //for (PaintObject ob : allPaintObjects)
          //ob.draw(g);         
      }	
	// handle clicks from the user
	@Override
	public void mouseClicked(MouseEvent e) {
		// variables
		double mouseX = e.getX(), mouseY = e.getY();
		// check state of the points
		if (point1 == null) {
			// setup point 1
			point1 = new Point((int)mouseX, (int)mouseY);
		} else {
			// setup point 2
			point2 = new Point((int)mouseX, (int)mouseY);
			// execute the requested action
			executeAction();
		}
	}
	// these stubs do nothing as of yet
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}	
}
