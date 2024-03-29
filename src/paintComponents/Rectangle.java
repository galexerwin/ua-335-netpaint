/* Author: Alex Erwin
 * Purpose: Represents the Rectangle shape and draws it onto the screen when it's draw method is called
 */
// package definition
package paintComponents;
// import classes
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
// concrete class which extends an abstract class
public class Rectangle extends PaintObject implements Serializable {
	// constructor calls the super
	public Rectangle(Color color, Point point1, Point point2) {
		// call super constructor
		super(color, point1, point2);
	}
	// draw using graphics
	@Override
	public void draw(Graphics g) {
		// variables
		Point point1 = this.getPoint1(), point2 = this.getPoint2();
		int upperLeftX, upperLeftY;
		int calcWidth, calcHeight;
		// determine where to place
		if (point1.x < point2.x) {
			upperLeftX = point1.x;
			upperLeftY = point1.y;
			calcWidth = point2.x - point1.x;
			calcHeight = point2.y - point1.y;
		} else {
			upperLeftX = point2.x;
			upperLeftY = point2.y;
			calcWidth = point1.x - point2.x;
			calcHeight = point1.y - point2.y;
		}	
		// set the color
		g.setColor(this.getColor());
		// draw a rectangle with the graphics
		g.fillRect(upperLeftX, upperLeftY, calcWidth, calcHeight);
		
	}
}
