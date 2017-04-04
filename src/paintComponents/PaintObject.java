/* Author: Alex Erwin
 * Purpose: Abstract Class for a graphics shape. Extended by Line, Oval, Rectangle
 */
// package definition
package paintComponents;
// import classes
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
// abstract class
@SuppressWarnings("serial")
public abstract class PaintObject implements Serializable {
	// abstract variables
	private Color color;
	private Point point1;
	private Point point2;
	
	protected PaintObject() {}
	// abstract constructor
	protected PaintObject(Color color, Point point1, Point point2) {
		this.color = color;
		this.point1 = point1;
		this.point2 = point2;
	}
	// abstract method draw
	public abstract void draw(Graphics g);
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
