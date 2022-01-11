package factory;

import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 *This interface {@code Drawable} defines the basic functions a maze should have
 *
 *<p> Provided by {@link javafx.scene.Group}, {@link javafx.scene.paint.Color} <br>
 *<p> This interface {@code Drawable} defines the basic functions a maze should have.<br>
 *A maze should be customizable, enabled by {@code customize}.<br>
 *A maze could implement obstacle detection, enabled by {@code isTouching} and {@code hasObstacle}
 *A maze could pass the necessary maze information to the javafx parent component, enabled by {@code CreateMaze}
 *
 * 
 * @author Boon Giin Lee
 * @author Heng Yu
 * @author Chen Liao
 * 
 * @version 1.03
 */
public interface Drawable {
	/**
	 * Method {@code customize} enables a user to self define the maze color
	 * @param color : the color of maze.
	 */
	public void customize(Color color);
	
	/**
	 * Method {@code customize} determines if other objects in game is touching the obstacles
	 * <br>
	 * @param x : the x axis value of the object
	 * @param y : the y axis value of the object
	 * @param padding : the radius of the object
	 * @return : if the object is touching any obstacles
	 */
	public Boolean isTouching(double x, double y, double padding);
	
	/**
	 * Method {@code hasObstacle} determines if there is an obstacle in the current coordinate
	 * @param fromX : x axis starting point
	 * @param toX : x axis termination point
	 * @param fromY : y axis starting point
	 * @param toY : y axis termination point
	 * @return if there is an obstacle in the current coordinate
	 */
	public Boolean hasObstacle(double fromX,  double toX, double fromY, double toY);
	
	/**
     * Method {@code CreateMaze} draws the maze in the root group
     * @param root : the root group for javafx 
     */
	public void CreateMaze(Group root);	
}
