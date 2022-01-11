package factory;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import sample.BarObstacle;

/**
 * Class {@code Maze} is the parent class of all mazes in the pacman game.
 * 
 * <p> provided by {@link java.util.HashSet}, {@link java.util.Set}, {@link javafx.scene.Group}, {@link javafx.scene.paint.Color}.<br>
 * 
 * <p> Class {@code Maze} specifies the functions of a maze in the pacman game. See {@link sample.drawable}.
 * 
 * <p>
 * @author Boon Giin Lee
 * @author Heng Yu
 * @author Chen Liao
 * 
 * @version 1.03
 */
public class Maze implements Drawable{
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

    Maze() {
        obstacles = new HashSet<>();
    }
    
    /**
     * Overriding function, please refer to {@code factory.Drawable.isTouching}.
     */
	@Override
	public Boolean isTouching(double x, double y, double padding) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
     * Overriding function, please refer to {@code factory.Drawable.hasObstacle}.
     */
	@Override
	public Boolean hasObstacle(double fromX, double toX, double fromY, double toY) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
     * Overriding function, please refer to {@code factory.Drawable.CreateMaze}.
     */
	@Override
	public void CreateMaze(Group root) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Method {@code getObsPosition} returns the obstacle matrix for this certain map 
	 * @return : the obstacle matrix
	 */
	public int[][] getObsPosition(){
		return obsPosition;
	}
	
	/**
	 *  Method {@code customize} enables user to select color for obstacles
	 *  @param color: the color of obstacle selected.
	 */
	@Override
	public void customize(Color color) {
		// TODO Auto-generated method stub
		
	}

}
