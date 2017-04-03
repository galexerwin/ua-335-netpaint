/* Author: Alex Erwin
 * Purpose: Abstract Class for a graphics shape. Extended by Line, Oval, Rectangle
 */
// import classes
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
// abstract class
public abstract class PaintObject {
	// abstract variables
	private Color color;
	private Point point1;
	private Point point2;	
	// abstract constructor
	protected PaintObject(Color color, Point point1, Point point2) {
		this.color = color;
		this.point1 = point1;
		this.point2 = point2;
	}
	// abstract method draw
	abstract void draw(Graphics g);
	// getters
	protected Color getColor() {
		return this.color;
	}
	protected Point getPoint1() {
		return this.point1;
	}
	protected Point getPoint2() {
		return this.point2;
	}
}
