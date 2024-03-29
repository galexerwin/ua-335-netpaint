/* Author: Alex Erwin
 * Purpose: Represents the Line shape and draws it onto the screen when it's draw method is called
 */
// package definition
package paintComponents;
// import classes
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
// concrete class which extends an abstract class
@SuppressWarnings("serial")
public class Line extends PaintObject implements Serializable {
	// constructor calls the super
	public Line(Color color, Point point1, Point point2) {
		// call super constructor
		super(color, point1, point2);	
	}
	// draw using graphics
	@Override
	public void draw(Graphics g) {
		// variables
		Point point1 = this.getPoint1(), point2 = this.getPoint2();
		// set the color
		g.setColor(this.getColor());
		// draw a line with the graphics
		g.drawLine(point1.x, point1.y, point2.x, point2.y);
	}
}
