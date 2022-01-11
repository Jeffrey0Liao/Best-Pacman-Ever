package factory;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import sample.BarObstacle;

/**
 * Class {@code Maze01} represents a concrete map in the pacman game
 * 
 * <p> provided by {@link java.util.HashSet}, {@link java.util.Set}, {@link javafx.scene.Group}, {@link javafx.scene.paint.Color}.<br>
 * 
 * <p> Class {@code Maze} specifies the details of a concrete maze in the pacman game. See {@link sample.drawable}.
 * A maze should specify the position of obstacles in a two-dimensional array.
 * A maze should be customizable with its color of obstacles by {@code customize}
 * A maze should implement all functions provided in the interface {@link sample.Drawable}.
 * 
 * <p>
 * @author Boon Giin Lee
 * @author Heng Yu
 * @author Chen Liao
 * 
 * @version 1.03
 */
public class Maze01 extends Maze{
	private Set<BarObstacle> obstacles;
	private int[][] obsPosition = {{},
									{},
									{},
									{},
									{},
									{},
									{},
									{},
									{},
									{},
									{}};

    Maze01() {
        obstacles = new HashSet<>();
    }
    
    /**
     * Overriding function, please refer to {@code factory.Maze.customize}.
     */
    public void customize(Color obsColor) {
    	BarObstacle.color = obsColor;
    }
    
    /**
     * Overriding function, please refer to {@code factory.Drawable.isTouching}.
     */
	public Boolean isTouching(double x, double y, double padding) {
		// TODO Auto-generated method stub
		for (BarObstacle barObstacle:obstacles) {
            if (
                x >= barObstacle.getX() - padding &&
                x <= barObstacle.getX() + padding + barObstacle.getWidth() &&
                y >= barObstacle.getY() - padding &&
                y <= barObstacle.getY() + padding + barObstacle.getHeight())
            {
                return true;
            }
        }
        return false;
	}
	
	/**
     * Overriding function, please refer to {@code factory.Drawable.hasObstacle}.
     */
	public Boolean hasObstacle(double fromX, double toX, double fromY, double toY) {
		// TODO Auto-generated method stub
		boolean isTouching = false;
        for (double i = fromX; i < toX; i++) {
            for (double j = fromY; j < toY; j++) {
                if (this.isTouching(i, j, 0)) isTouching = true;
            }
        }
        return isTouching;
	}
	
	/**
     * Overriding function, please refer to {@code factory.Drawable.CreateMaze}.
     */
	public void CreateMaze(Group root) {
		// TODO Auto-generated method stub
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~ frame ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // top
        this.obstacles.add(new BarObstacle(0, 0, "horizontal", 48));
        // bottom
        this.obstacles.add(new BarObstacle(0, 600, "horizontal", 48));
        // left
        this.obstacles.add(new BarObstacle(0, 0, "vertical", 11));
        this.obstacles.add(new BarObstacle(0, 350, "vertical", 11));
        // right
        this.obstacles.add(new BarObstacle(1225 - BarObstacle.THICKNESS, 0, "vertical", 11));
        this.obstacles.add(new BarObstacle(1225 - BarObstacle.THICKNESS, 350, "vertical", 11));
        
        root.getChildren().addAll(obstacles);
	}
	
	/**
     * Overriding function, please refer to {@code factory.Maze.getObsPosition}.
     */
	public int[][] getObsPosition(){
		return obsPosition;
	}
}
