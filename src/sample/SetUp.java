package sample;

import javafx.scene.paint.Color;

/**
 * Class {@code SetUp} is the integration class for all customizations.
 * 
 * <p> Provided by {@link javafx.scene.paint.Color} <br>
 * 
 * <p> This class fetches all setting up identifiers for the customizations.
 * Major identifiers contains the pacman icon, the ghost icon, the color of obstacles, the color
 * of background, the difficulty level and the map selector.
 * 
 * <p>
 * @author Boon Giin Lee
 * @author Heng Yu
 * @author Chen Liao
 * 
 * @version 1.03
 */
public class SetUp {
	
	private String pacman;
	private String ghost;
	private String obstacleColor;
	private String backgroundColor;
	private String difficulty;
	private int map;
	
	public String pacmanPath;
	public String ghostPath;
	public Color obsColor;
	public Color bgColor;
	public int diffi;
	public int mp;
	
	/**
	 * Constructor for class {@code sample.SetUp}.
	 * <br>
	 * @param pacman : pacman customization identifier.
	 * @param ghost : ghosts customization identifier.
	 * @param obstacleColor : obstacle color customization identifier.
	 * @param backgroundColor : background color customization identifier.
	 * @param difficulty : difficulty customization identifier.
	 * @param map: map customization identifier.
	 */
	public SetUp(String pacman, String ghost, String obstacleColor, String backgroundColor, String difficulty, int map) {
		this.pacman = pacman;
		this.ghost = ghost;
		this.obstacleColor = obstacleColor;
		this.backgroundColor = backgroundColor;
		this.difficulty = difficulty;
		this.map = map;
		this.mp = this.map;
		
		this.pacmanPath = "./source/pacman.png";
		this.ghostPath = "./source/ghost1.png";
		this.obsColor = Color.RED;
		this.bgColor = Color.RED;
		this.diffi = 5;
		
		this.selectAll();
	}
	
	/**
	 * integration selector
	 */
	private void selectAll() {
		this.selectPacman();
		this.selectGhost();
		this.selectObstacle();
		this.selectBackground();
		this.selectDifficulty();
	}
	
	/**
	 * pacman selector
	 */
	private void selectPacman() {
		if (pacman.equals("Classic")) {
			this.pacmanPath = "./source/pacman.png";
		} else if (pacman.equals("ZhouMinghao")) {
			this.pacmanPath = "./source/pacman.png";
		} else if (pacman.equals("MaoJiren")) {
			this.pacmanPath = "./source/pacman.png";
		} else {
			this.pacmanPath = "./source/pacman.png";
		}
	}
	
	/**
	 * ghost selector
	 */
	private void selectGhost() {
		if (ghost.equals("Classic")) {
			this.ghostPath = "./source/ghost1.png";
		} else if (ghost.equals("Ghost1")) {
			this.ghostPath = "./source/ghost2.png";
		} else if (ghost.equals("Ghost2")) {
			this.ghostPath = "./source/ghost3.png";
		} else {
			this.ghostPath = "./source/ghost4.png";
		}
	}
	
	/**
	 * obstacle color selector
	 */
	private void selectObstacle() {		
		if (obstacleColor.equals("Default")) {
			this.obsColor = Color.RED;
		} else if (obstacleColor.equals("Red")) {
			this.obsColor = Color.RED;
		} else if (obstacleColor.equals("Yellow")) {
			this.obsColor = Color.YELLOW;
		} else if (obstacleColor.equals("Blue")){
			this.obsColor = Color.BLUE;
		} else if (obstacleColor.equals("Green")) {
			this.obsColor = Color.GREEN;
		} else if (obstacleColor.equals("White")) {
			this.obsColor = Color.WHITE;
		} else if (obstacleColor.equals("Black")) {
			this.obsColor = Color.BLACK;
		} else if (obstacleColor.equals("Pink")){
			this.obsColor = Color.PINK;
		} else {
			this.obsColor = Color.PURPLE;
		}
	}
	
	/**
	 * background color selector
	 */
	private void selectBackground() {
		if (backgroundColor.equals("Default")) {
			this.bgColor = Color.INDIANRED;
		} else if (backgroundColor.equals("Red")) {
			this.bgColor = Color.INDIANRED;
		} else if (backgroundColor.equals("Yellow")) {
			this.bgColor = Color.LIGHTYELLOW;
		} else if (backgroundColor.equals("Blue")){
			this.bgColor = Color.LIGHTSKYBLUE;
		} else if (backgroundColor.equals("Green")) {
			this.bgColor = Color.GREENYELLOW;
		} else if (backgroundColor.equals("White")) {
			this.bgColor = Color.FLORALWHITE;
		} else if (backgroundColor.equals("Black")) {
			this.bgColor = Color.DIMGRAY;
		} else if (backgroundColor.equals("Pink")){
			this.bgColor = Color.LIGHTPINK;
		} else {
			this.bgColor = Color.MEDIUMPURPLE;
		}
	}
	
	/**
	 * difficulty selector
	 */
	private void selectDifficulty() {
		if (difficulty.equals("Heaven")) {
			this.diffi = 3;
		} else if (difficulty.equals("Novice")) {
			this.diffi = 4;
		} else if (difficulty.equals("Hard")) {
			this.diffi = 6;
		} else if (difficulty.equals("Hell")){
			this.diffi = 7;
		} else {
			this.diffi = 5;
		}
	}
}
