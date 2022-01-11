package factory;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import sample.BarObstacle;

import java.util.HashSet;
import java.util.Set;

/**
 * Class {@code Maze00} represents a concrete map in the pacman game
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
public class Maze02 extends Maze{

    private Set<BarObstacle> obstacles;
    private int[][] obsPosition = {{4, 17, 21},
    								{1, 2, 4, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 19, 20, 21},
    								{1, 4, 17},
    								{1, 4, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 19, 18, 20, 21},
    								{1, 7, 8, 9, 10, 11, 12, 13, 14, 15, 21},
    								{1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19},
    								{ 7, 8, 9, 10, 11, 12, 13, 14, 15, 21},
    								{1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 21},
    								{5, 17, 21},
    								{1, 2, 3, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 19, 20, 21},
    								{1, 5, 17}};

    Maze02() {
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
    public Boolean hasObstacle(double fromX,  double toX, double fromY, double toY) {
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
        
        //~~~~~~~~~~~~~~~~~~~~~~~~~ Islands ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // obsTopLeft
        this.obstacles.add(new BarObstacle(10 * BarObstacle.THICKNESS, BarObstacle.THICKNESS, "vertical", 8));
        // obsTopRight
        this.obstacles.add(new BarObstacle(44 * BarObstacle.THICKNESS, BarObstacle.THICKNESS, "vertical", 4));
        this.obstacles.add(new BarObstacle(36 * BarObstacle.THICKNESS, BarObstacle.THICKNESS, "vertical", 8));
        // obsTopMiddle
        //this.obstacles.add(new BarObstacle(20 * BarObstacle.THICKNESS, BarObstacle.THICKNESS, "vertical", 4));
        // obsBottomMiddle
        //this.obstacles.add(new BarObstacle(24 * BarObstacle.THICKNESS, 600 - 4 * BarObstacle.THICKNESS, "vertical", 4));
        // obsBottomRight
        this.obstacles.add(new BarObstacle(36 * BarObstacle.THICKNESS, 600 - 8 * BarObstacle.THICKNESS, "vertical", 8));
        // obsBottomLeft
        this.obstacles.add(new BarObstacle(12 * BarObstacle.THICKNESS, 600 - 7 * BarObstacle.THICKNESS, "vertical", 7));
        // obsTopMiddle line1
        this.obstacles.add(new BarObstacle(16 * BarObstacle.THICKNESS, 4 * BarObstacle.THICKNESS, "horizontal", 17));
        // obsTopLeft line1 
        this.obstacles.add(new BarObstacle(4 * BarObstacle.THICKNESS, 4 * BarObstacle.THICKNESS, "horizontal", 3));
        // obsTopRight line1
        this.obstacles.add(new BarObstacle(40 * BarObstacle.THICKNESS, 4 * BarObstacle.THICKNESS, "horizontal", 4));
        
        this.obstacles.add(new BarObstacle(4 * BarObstacle.THICKNESS, 4 * BarObstacle.THICKNESS, "vertical", 9));
        
        this.obstacles.add(new BarObstacle(40 * BarObstacle.THICKNESS, 4 * BarObstacle.THICKNESS, "horizontal", 4));
        
        this.obstacles.add(new BarObstacle(36 * BarObstacle.THICKNESS, 8 * BarObstacle.THICKNESS, "horizontal", 8));
        
        this.obstacles.add(new BarObstacle(4 * BarObstacle.THICKNESS, 12 * BarObstacle.THICKNESS, "horizontal", 9));
        
        this.obstacles.add(new BarObstacle(4 * BarObstacle.THICKNESS, 600 - 4 * BarObstacle.THICKNESS, "horizontal", 5));
        
        this.obstacles.add(new BarObstacle(4 * BarObstacle.THICKNESS, 600 - 4 * BarObstacle.THICKNESS, "vertical", 4));
        
        // cageRightV
        this.obstacles.add(new BarObstacle(32 * BarObstacle.THICKNESS, 600 - 16 * BarObstacle.THICKNESS, "vertical", 3));
        this.obstacles.add(new BarObstacle(32 * BarObstacle.THICKNESS, 600 - 10 * BarObstacle.THICKNESS, "vertical", 3));
        // cageLeftV
        this.obstacles.add(new BarObstacle(16 * BarObstacle.THICKNESS, 600 - 16 * BarObstacle.THICKNESS, "vertical", 3));
        this.obstacles.add(new BarObstacle(16 * BarObstacle.THICKNESS, 600 - 10 * BarObstacle.THICKNESS, "vertical", 3));
        // cateRightH
        this.obstacles.add(new BarObstacle(17 * BarObstacle.THICKNESS, 8 * BarObstacle.THICKNESS, "horizontal", 5));
        // cateLeftH
        this.obstacles.add(new BarObstacle(27 * BarObstacle.THICKNESS, 8 * BarObstacle.THICKNESS, "horizontal", 5));
        // cageBottom
        this.obstacles.add(new BarObstacle(16 * BarObstacle.THICKNESS, 600 - 8 * BarObstacle.THICKNESS, "horizontal", 17));
        
        this.obstacles.add(new BarObstacle(36 * BarObstacle.THICKNESS, 12 * BarObstacle.THICKNESS, "horizontal", 5));
        
        this.obstacles.add(new BarObstacle(44 * BarObstacle.THICKNESS, 8 * BarObstacle.THICKNESS, "vertical", 3));
        
        this.obstacles.add(new BarObstacle(44 * BarObstacle.THICKNESS, 14 * BarObstacle.THICKNESS, "vertical", 7));
        
        this.obstacles.add(new BarObstacle(40 * BarObstacle.THICKNESS, 20 * BarObstacle.THICKNESS, "horizontal", 5));
        
        this.obstacles.add(new BarObstacle(16 * BarObstacle.THICKNESS, 600 - 4 * BarObstacle.THICKNESS, "horizontal", 17));
        
        this.obstacles.add(new BarObstacle(4 * BarObstacle.THICKNESS, 600 - 8 * BarObstacle.THICKNESS, "horizontal", 9));
        
        root.getChildren().addAll(obstacles);
    }
    
    /**
     * Overriding function, please refer to {@code factory.Maze.getObsPosition}.
     */
    public int[][] getObsPosition(){
		return obsPosition;
	}
    
}
