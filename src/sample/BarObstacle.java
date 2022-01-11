package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class {@code BarObstacle} defines a bar obstacle appears in the game board
 * 
 * <p> Provided by {@link javafx.scene.paint.Color}, {@link javafx.scene.shape.Rectangle}<br>
 * 
 * <p> Class {@code BarObstacle} could define an instance of obstacle bar from a rectangle as the template.
 * Specific position, orientation and length of the obstacle could be defined with {@code BarObstacle}
 * 
 * @author Boon Giin Lee
 * @author Heng Yu
 * @author Chen Liao
 * 
 * @version 1.03
 */
public class BarObstacle extends Rectangle {
	public static Color color;
    public static double THICKNESS = 25;
    
    /**
     * Method {@code BarObstacle} is the constructor of {@code sample.BarObstacle}
     * @param x - position x
     * @param y - position y
     * @param orientation - horizontal or vertical
     * @param length - the length of the bar (1 == 100px)
     */
    public BarObstacle(double x, double y, String orientation, double length) {
        this.setX(x);
        this.setY(y);
        if (orientation.equals("horizontal")) {
            this.setHeight(BarObstacle.THICKNESS);
            this.setWidth(length * BarObstacle.THICKNESS);
        } else {
            this.setHeight(length * BarObstacle.THICKNESS);
            this.setWidth(BarObstacle.THICKNESS);
        }
        this.setFill(color);
        this.setStrokeWidth(3);
    }
}
