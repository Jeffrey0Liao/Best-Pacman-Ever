
package factory;

/**
 * Class {@code MazeFactory} is the integration class of maps
 * 
 * <p> no external package is imported<br>
 * 
 * <p> This class is the integration class of the factory design pattern for mazes.
 * Based on the input identifier, the factory returns a specify maze instance of a category from various registered mazes.
 * 
 * <p>
 * @author Boon Giin Lee
 * @author Heng Yu
 * @author Chen Liao
 * 
 * @version 1.03
 */
public class MazeFactory {
	Maze maze;
	
	// constructor
	public MazeFactory() {
		
	}
	
	/**
	 * Method {@code getMaze} returns a specific maze instance based on the selection of the caller
	 * @param type : maze identifier
	 * @return : a specific maze
	 */
	public Maze getMaze(int type) {
		if (type == 0) {
			return new Maze00();
		} else if (type == 1) {
			return new Maze01();
		} else if (type == 2){
			return new Maze02();
		} else {
			return null;
		}
	}
}
