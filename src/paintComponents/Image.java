/* Author: Alex Erwin
 * Purpose: Represents the Line shape and draws it onto the screen when it's draw method is called
 */
// package definition
package paintComponents;
// import classes
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
// concrete class which extends an abstract class
@SuppressWarnings("serial")
public class Image extends PaintObject implements Serializable {
	// instance variables
	private static final String filename = "image.jpg";
	// constructor calls the super
	public Image(Color color, Point point1, Point point2) {
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
		// get 2D
		Graphics2D g2 = (Graphics2D) g;
		// determine where to place
		if (point1.x < point2.x) {
			upperLeftX = point1.x;
			upperLeftY = point1.y;
			calcWidth = point2.x - point1.x;
			calcHeight = point2.y - point1.y;
			// be sure the height isn't negative
			if (calcHeight < 0) {
				upperLeftY = point2.y;
				calcHeight = point1.y - point2.y;
			}
		} else {
			upperLeftX = point2.x;
			upperLeftY = point2.y;
			calcWidth = point1.x - point2.x;
			calcHeight = point1.y - point2.y;
			// be sure the height isn't negative
			if (calcHeight < 0) {
				upperLeftY = point1.y;
				calcHeight = point2.y - point1.y;
			}
		}			
		// load the image
		try {
			
			// create an image
			BufferedImage img = ImageIO.read(new File(Image.filename)); 
			// draw the image
			g2.drawImage(img, upperLeftX, upperLeftY, calcWidth, calcHeight, null);
			
		    

		} 
		catch (IOException e) {}		
	}
}
