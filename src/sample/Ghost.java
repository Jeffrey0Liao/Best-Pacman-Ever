package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

import factory.Maze;

/**
 * Class {@code Ghost} represent the template of a ghost in pacman game
 * 
 * <p> Provided by {@link javafx.animation.AnimationTimer}, {@link javafx.scene.image.Image},
 * {@link javafx.scene.image.ImageView}, {@link javafx.scene.paint.Color}, {@link javafx.scene.shape.Rectangle}, 
 * {@link java.util.Random}, {@link java.lang.Runnable}.<br>
 * 
 * <p> Class {@code Ghost} implements runnable to enable every ghost instance runs as a single thread by overriding {@code run}.
 * the AnimitionTimer {@link javafx.animation.AnimationTimer} enables the ghost to update its information at every frame of the game.
 * the model of a ghost is a rectangle, it is hovered by the ghost icon, which could be customized by {@code customize}.
 * The running pattern of a ghost is "move until you cannot" pattern, see {@code moveUntilYouCant}, which means a ghost will 
 * follow its current direction until there is an obstacle in front of it. The implementation of initial A* tracing algorithm makes 
 * the ghost AI available, see {@code tracePacman}.
 * 
 * <p>
 * @author Boon Giin Lee
 * @author Heng Yu
 * @author Chen Liao
 * 
 * @version 1.03
 */
public class Ghost extends Rectangle implements Runnable {

    String direction;
    GameManager gameManager;
    Maze maze;
    AnimationTimer animation;
    int timesWalked;
    // default speed value
    double step = 5;
    
    private Image img;
    private ImageView iv;
    
    /**
     * Method {@code Ghost} is the constructor of the class {@code Ghost}
     * <br>
     * @param x : position x
     * @param y : position y
     * @param color : color of the ghost
     * @param maze : the generated maze
     * @param gameManager : the generated instance of class {@code GameManager}
     */
    public Ghost(double x, double y, Color color, Maze maze, GameManager gameManager) {
        this.setX(x);
        this.setY(y);
        this.setFill(color);
        this.maze = maze;
        this.gameManager = gameManager;
        this.setHeight(50);
        this.setWidth(50);
        this.timesWalked = 0;
        this.direction = "down";
        this.createAnimation();
        
        img = new Image("./source/ghost1.png", 50, 50, true, true);
        iv = new ImageView();
        iv.setImage(img);
        iv.setTranslateX(this.getX());
        iv.setTranslateY(this.getY());
        this.setVisible(false);
    }
    
    /**
     * Method {@code customize} enables the icon customization for ghost instances
     * @param path the icon path in the {@code source}
     */
    public void customize(String path) {
    	img = new Image(path, 50, 50, true, true);
        iv = new ImageView();
        iv.setImage(img);
        iv.setTranslateX(this.getX());
        iv.setTranslateY(this.getY());
    }
    
    /**
     * Method {@code speedSetter} sets speed of a ghost.
     * <br>
     * @param level : the difficulty of the game, also representing the speed of the ghost.
     */
    public void speedSetter(int level) {
    	this.step = level;
    }

    /**
     * Method {@code getRandomDirection} enables the ghost to randomly choose the direction except for the specified two.
     * If the difficulty level is greater than or equal to 6, Ghost AI will be adapted by {@code tracePacman}.
     * <br>
     * @param exclude1 : unavailable direction 1
     * @param exclude2 : unavailable direction 2
     * @return next randomly choosed direction
     */
    private String getRandomDirection(String exclude1, String exclude2) {
    	int rnd = 0;
        String[] directions = {"left", "right", "up", "down"};
        if (this.step>=6) {
        	rnd = tracePacman();
        } else {
        	rnd = new Random().nextInt(directions.length);
        }
        while (directions[rnd].equals(exclude1) || directions[rnd].equals(exclude2)) {
            rnd = new Random().nextInt(directions.length);
        }
        return directions[rnd];
    }
    
    /**
     * Method {@code getRandomBoolean} returns a random boolean.
     * <br>
     * @return : a random boolean
     */
    private boolean getRandomBoolean() {
        Random rand = new Random();
        return rand.nextBoolean();
    }

    /**
     * Method {@code getAnimation} gets the animation for the ghost.
     * <br>
     * @return : the animation for the ghost
     */
    public AnimationTimer getAnimation() {
        return animation;
    }

    /**
     * Method {@code checkIftheresPathToGo} check which direction is valid currently.
     * <br>
     * @param direction : the current direction of a ghost.
     */
    private void checkIftheresPathToGo(String direction) {
        double rightEdge, leftEdge, topEdge, bottomEdge;
        switch (direction) {
            case "down":
                leftEdge = getX() - 10;
                bottomEdge = getY() + getHeight() + 15;
                rightEdge = getX() + getWidth() + 10;
                if (!maze.hasObstacle(leftEdge, rightEdge, bottomEdge - 1, bottomEdge)) {
                    this.direction = direction;
                }
                break;
            case "up":
                leftEdge = getX() - 10;
                rightEdge = getX() + getWidth() + 10;
                topEdge = getY() - 15;
                if (!maze.hasObstacle(leftEdge, rightEdge, topEdge - 1, topEdge)) {
                    this.direction = direction;
                }
                break;
            case "left":
                leftEdge = getX() - 15;
                bottomEdge = getY() + getHeight() + 10;
                topEdge = getY() - 10;
                if (!maze.hasObstacle(leftEdge - 1, leftEdge, topEdge, bottomEdge)) {
                    this.direction = direction;
                }
                break;
            case "right":
                bottomEdge = getY() + getHeight() + 10;
                rightEdge = getX() + getWidth() + 15;
                topEdge = getY() - 10;
                if (!maze.hasObstacle(rightEdge - 1, rightEdge, topEdge, bottomEdge)) {
                    this.direction = direction;
                }
                break;
        }
    }

    /**
     * Method {@code moveUntilYouCant} moves a ghost along under the current direction until there is an obstacle.
     * <br>
     * @param whereToGo : current direction
     * @param whereToChangeTo : next direction
     * @param leftEdge : obstacle 1
     * @param topEdge : obstacle 2
     * @param rightEdge : obstacle 3
     * @param bottomEdge : obstacle 4
     * @param padding : padding with the obstacles
     */
    private void moveUntilYouCant(String whereToGo, String whereToChangeTo, double leftEdge, double topEdge, double rightEdge, double bottomEdge, double padding) {
        double step = this.step;
        switch (whereToGo) {
            case "left":
                if (!maze.isTouching(leftEdge, topEdge, padding)) {
                    setX(leftEdge - step);
                    this.moveIcon();
                } else {
                    while (maze.isTouching(getX(), getY(), padding)) {
                        setX(getX() + 1);
                        this.moveIcon();
                    }
                    direction = whereToChangeTo;
                }
                break;
            case "right":
                if (!maze.isTouching(rightEdge, topEdge, padding)) {
                    setX(leftEdge + step);
                    this.moveIcon();
                } else {
                    while (maze.isTouching(getX() + getWidth(), getY(), padding)) {
                        setX(getX() - 1);
                        this.moveIcon();
                    }
                    direction = whereToChangeTo;
                }
                break;
            case "up":
                if (!maze.isTouching(leftEdge, topEdge, padding)) {
                    setY(topEdge - step);
                    this.moveIcon();
                } else {
                    while (maze.isTouching(getX(), getY(), padding)) {
                        setY(getY() + 1);
                        this.moveIcon();
                    }
                    direction = "left";
                }
                break;
            case "down":
                if (!maze.isTouching(leftEdge, bottomEdge, padding)) {
                    setY(topEdge + step);
                    this.moveIcon();
                } else {
                    while (maze.isTouching(getX(), getY() + getHeight(), padding)) {
                        setY(getY() - 1);
                        this.moveIcon();
                    }
                    direction = "right";
                }
                break;
        }

    }

    /**
     * Method {@code createAnimation} creates an animation of the ghost.
     * A ghost could update its information visually at every frame of the game.
     */
    public void createAnimation() {

        this.animation = new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                gameManager.checkGhostCoalition();
                double leftEdge = getX();
                double topEdge = getY();
                double rightEdge = getX() + getWidth();
                double bottomEdge = getY() + getHeight();
                double padding = 12;
                timesWalked++;
                int walkAtLeast = 4;
                switch (direction) {
                    case "left":
                    	checkDoorCoalition();
                        moveUntilYouCant("left", "down", leftEdge, topEdge, rightEdge, bottomEdge, padding);
                        if (timesWalked > walkAtLeast) {
                            checkIftheresPathToGo(getRandomDirection("left", "right"));
                            timesWalked = 0;
                        }
                        break;
                    case "right":
                    	checkDoorCoalition();
                        moveUntilYouCant("right", "up", leftEdge, topEdge, rightEdge, bottomEdge, padding);
                        if (timesWalked > walkAtLeast) {
                            checkIftheresPathToGo(getRandomDirection("left", "right"));
                             timesWalked = 0;
                        }
                        break;
                    case "up":
                        moveUntilYouCant("up", "left", leftEdge, topEdge, rightEdge, bottomEdge, padding);
                        if (timesWalked > walkAtLeast) {
                            checkIftheresPathToGo(getRandomDirection("up", "down"));
                            timesWalked = 0;
                        }
                        break;
                    case "down":
                        moveUntilYouCant("down", "right", leftEdge, topEdge, rightEdge, bottomEdge, padding);
                        if (timesWalked > walkAtLeast) {
                            checkIftheresPathToGo(getRandomDirection("up", "down"));
                            timesWalked = 0;
                        }
                        break;
                }
            }
        };
    }
    
    /**
     * Method {@code tracePacman} select a direction for the ghost based on A* 
     * <br>
     * @return the current optimized direction.
     */
    public int tracePacman() {
    	double pacmanCenterX = this.gameManager.pacman.getCenterX();
    	double pacmanCenterY = this.gameManager.pacman.getCenterY();
    	double ghostCenterY = this.getY()+(getHeight()/2);
        double ghostCenterX = this.getX()+(getWidth()/2);
        
        // A* score map calculation
        double scoreOfMovingUp = Math.pow((pacmanCenterX-ghostCenterX), 2)+Math.pow((pacmanCenterY-(ghostCenterY-this.step)), 2);
        double scoreOfMovingDown = Math.pow((pacmanCenterX-ghostCenterX), 2)+Math.pow((pacmanCenterY-(ghostCenterY+this.step)), 2);
        double scoreOfMovingLeft = Math.pow((pacmanCenterX-(ghostCenterX-this.step)), 2)+Math.pow((pacmanCenterY-ghostCenterY), 2);
        double scoreOfMovingRight = Math.pow((pacmanCenterX-(ghostCenterX+this.step)), 2)+Math.pow((pacmanCenterY-ghostCenterY), 2);
    	double[] scoreMap = {scoreOfMovingLeft, scoreOfMovingRight, scoreOfMovingUp, scoreOfMovingDown};
    	
    	double min = Double.MAX_VALUE;
    	int index = 0;
    	for (int i=0; i<scoreMap.length; i++) {
    		if (scoreMap[i]<min) {
    			min = scoreMap[i];
    			index = i;
    		}
    	}
        
    	return index;
    }
    
    /**
     * Method {@code checkDoorCoalition} checks if a ghost is passing the magic doors.
     * 
     */
    public void checkDoorCoalition() {
    	double ghostCenterY = getY()+(getHeight()/2);
        double ghostCenterX = getX()+(getWidth()/2);
        if (ghostCenterX <= 0.0) {
        	this.setX(1215.0-(getWidth()/2));
        	this.moveIcon();
        }
        if (ghostCenterX >= 1225.0) {
        	this.setX(10.0-(getWidth()/2));
        	this.moveIcon();
        }
    }
    
    /**
     * Method {@code getIcon} returns the current icon of the ghost
     * @return the image view contains the current icon of the pacman
     */
    public ImageView getIcon() {
    	return iv;
    }
    
    /**
     * Method {@code moveIcon} moves the icon of the ghost
     */
    public void moveIcon() {
    	iv.setTranslateX(this.getX());
        iv.setTranslateY(this.getY());
    }
    
    /**
     * Override function, please refer to {@link void java.lang.Runnable.run}
     */
    @Override
    public void run() {
        this.animation.start();
    }
}
