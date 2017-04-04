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
	private NetPaintClient 	npc;
	private JPanel			upper, middle, lower;
	private JScrollPane 	viewport;
	private JColorChooser 	colorOption; 
	private ButtonGroup 	shapeOption;
	private Point 			point1, point2;
	private int 			width, height;
	private int				upperHeight, middleHeight, lowerHeight;
	// constructor
	public DefaultView(NetPaintClient npc, int width, int height) {
		// set defaults
		this.npc = npc;
		this.height = height;
		this.width = width;
		// calculate the height
		int maxHeight = height;
		// fix middleHeight
		middleHeight = 30;
		// subtract
		maxHeight -= middleHeight;
		// set lowerHeight
		lowerHeight = 475;
		// subtract
		maxHeight -= lowerHeight;
		// set upperHeight
		upperHeight = maxHeight;
		// setup the view
		initializeView();		
	}
	// initialize the view
	private void initializeView() {
		// add the upper
		this.upper = new DrawWindow();
		// set dimensions
		this.upper.setPreferredSize(new Dimension(this.width * 2, this.height * 2));
		// background color
		this.upper.setBackground(Color.WHITE);
		// mouse clicks
		this.upper.addMouseListener(this);
		// set up scrollable
		this.viewport = new JScrollPane(this.upper);
		// set the size
		this.viewport.setPreferredSize(new Dimension(this.width, this.upperHeight));
		// add the radio group to style it
		this.middle = getRadioGroup();
		// set dimensions
		this.middle.setPreferredSize(new Dimension(this.width, this.middleHeight));
		// add the color group to style it
		this.lower = getColorChoice();
		// set dimensions
		this.lower.setPreferredSize(new Dimension(this.width, this.lowerHeight));
		// add elements
		add(this.viewport);
		add(this.middle);
		add(this.lower);
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
		// return the canvas
		return canvas;
	}
	// color chooser
	private JPanel getColorChoice() {
		// variables
		JPanel canvas = new JPanel();		
		// get the user's selected color
		this.colorOption = new JColorChooser();
		// 
		this.colorOption.setPreferredSize(new Dimension(this.width, 300));
		// add the color option to the canvas
		canvas.add(this.colorOption);
		// return the canvas
		return canvas;		
	}
	// execute the action
	private void executeAction() {
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
		this.upper.repaint();
	}
    public void paintComponent(Graphics g) {}	
	// private panel for updates
	private class DrawWindow extends JPanel {
		// paint the game onto the canvas
		@Override
		public void paintComponent(Graphics g) {
			// call super repaint for gui elements
		    super.paintComponent(g);	
	    	// get the paint objects
	    	Vector<PaintObject> allPaintObjects = npc.getCanvas();
	    	// iterate over the paint objects
			for (PaintObject ob : allPaintObjects) {
				ob.draw(g);
			}
		}
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