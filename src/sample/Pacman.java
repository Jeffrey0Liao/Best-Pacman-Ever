package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class {@code Pacman} is the class defining a pacman in the game page.
 * 
 * <p> Provided by {@link javafx.animation.AnimationTimer}, {@link javafx.scene.image.Image}, {@link javafx.scene.image.ImageView}, 
 * {@link javafx.scene.input.KeyEvent}, {@link javafx.scene.paint.Color} and {@link javafx.scene.shape.Circle}.<br>
 * 
 * <p> This class controls a pacman in game. The actual model of pacman is a shape of circle,
 * Singleton design pattern is adapted to make sure there only exists one instance of pacman throughhout the game.
 * The initiating method {@code pacmanInit} is defined public static and it could customize the position and the icon of pacman.
 * The move of pacman is achieved by key binding, thus with animation timer, the pacman could update its position at every frame
 * of the game.
 * pacman here could also check if it is touching a cookie.
 * 
 * <p>
 * @author Boon Giin Lee
 * @author Heng Yu
 * @author Chen Liao
 * 
 * @version 1.03
 */
public class Pacman extends Circle {
	//singleton design pattern 
	private static Pacman pacman = new Pacman();
	private static double step;
	private static Image img;
	private static ImageView iv = new ImageView();
	private static GameManager gameManager;
	
	/**
	 * Invisible constructor for class {@code sample.Pacman}.
	 */
	private Pacman() {
		
	}
    
	/**
	 * Method {@code pacmanInit} initiates a pacman
	 * <br>
	 * @param x : position x
	 * @param y : position y
	 * @param gm : current game manager
	 */
	public static void pacmanInit(double x, double y, GameManager gm) {
		gameManager = gm;
        pacman.setCenterX(x);
        pacman.setCenterY(y);
        pacman.setRadius(25);
        pacman.setFill(Color.YELLOW);
        img = new Image("./source/pacman.png", 50.0, 50.0, true, true);
        iv.setImage(img);
        iv.setTranslateX(pacman.getCenterX()-25.0);
        iv.setTranslateY(pacman.getCenterY()-25.0);
        pacman.setVisible(false);
    }
    
	/**
	 * Method {@code customize} customize the icon of pacman
	 * <br>
	 * @param path : icon image path
	 */
	public static void customize(String path) {
		img = new Image(path, 50.0, 50.0, true, true);
        iv.setImage(img);
        iv.setTranslateX(pacman.getCenterX()-25.0);
        iv.setTranslateY(pacman.getCenterY()-25.0);
	}
	
	/**
	 * Method {@code setSpeed} sets the step length of pacman
	 * <br>
	 * @param step : one step length of the pacman
	 */
	public static void setSpeed(int step) {
		Pacman.step = (double)step;
	}
	
	/**
	 * Method {@code getInstance} returns the only instance of pacman.
	 * <br>
	 * @return the instance of pacman
	 */
    public static Pacman getInstance() {
    	return pacman;
    }
    
    /**
     * Method {@code getInstance} returns the icon of pacman.
	 * <br>
	 * @return the icon of pacman
     */
    public static ImageView getIcon() {
    	return iv;
    }
    
    /**
     * Method {@code getInstance} moves the icon of pacman.
     */
    public static void moveIcon() {
    	iv.setTranslateX(pacman.getCenterX()-25.0);
        iv.setTranslateY(pacman.getCenterY()-25.0);
    }
    
    /**
     * Method {@code movePacman} moves the model of pacman
     * <br>
     * @param  event event is a java action event for key entering.
     */
    public void movePacman(KeyEvent event) {
        for (Ghost ghost : gameManager.ghosts) {
            ghost.run();
        }
        switch(event.getCode()) {
            case RIGHT:
                gameManager.rightPacmanAnimation.start();
                break;
            case LEFT:
            	gameManager.leftPacmanAnimation.start();
                break;
            case UP:
            	gameManager.upPacmanAnimation.start();
                break;
            case DOWN:
            	gameManager.downPacmanAnimation.start();
                break;
        }
    }

    /**
     * Method {@code stopPacman} stops the pacman
     * <br>
     * @param event : event event is a java action event for key entering. 
     */
    public void stopPacman(KeyEvent event) {
        switch(event.getCode()) {
            case RIGHT:
            	gameManager.rightPacmanAnimation.stop();
                break;
            case LEFT:
            	gameManager.leftPacmanAnimation.stop();
                break;
            case UP:
            	gameManager.upPacmanAnimation.stop();
                break;
            case DOWN:
            	gameManager.downPacmanAnimation.stop();
                break;
        }
    }

    /**
     * Method {@code createAnimation} creates an animation of the movement.
     * <br>
     * @param direction : the current direction of the pacman.
     * @return new animation timer of the pacman.
     */
    public AnimationTimer createAnimation(String direction) {
        double step = this.step;
        return new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
            switch (direction) {
                case "left":
                    if (!gameManager.maze.isTouching(pacman.getCenterX() - pacman.getRadius(), pacman.getCenterY(), 15)) {
                        pacman.setCenterX(pacman.getCenterX() - step);
                        Pacman.moveIcon();
                        checkCookieCoalition(pacman, "x");
                        gameManager.checkGhostCoalition();
                        gameManager.checkDoorCoalition();
                    }
                    break;
                case "right":
                    if (!gameManager.maze.isTouching(pacman.getCenterX() + pacman.getRadius(), pacman.getCenterY(), 15)) {
                        pacman.setCenterX(pacman.getCenterX() + step);
                        Pacman.moveIcon();
                        checkCookieCoalition(pacman, "x");
                        gameManager.checkGhostCoalition();
                        gameManager.checkDoorCoalition();
                    }
                    break;
                case "up":
                    if (!gameManager.maze.isTouching(pacman.getCenterX(), pacman.getCenterY() - pacman.getRadius(), 15)) {
                        pacman.setCenterY(pacman.getCenterY() - step);
                        Pacman.moveIcon();
                        checkCookieCoalition(pacman, "y");
                        gameManager.checkGhostCoalition();
                        gameManager.checkDoorCoalition();
                    }
                    break;
                case "down":
                   if (!gameManager.maze.isTouching(pacman.getCenterX(), pacman.getCenterY() + pacman.getRadius(), 15)) {
                       pacman.setCenterY(pacman.getCenterY() + step);
                       Pacman.moveIcon();
                       checkCookieCoalition(pacman, "y");
                       gameManager.checkGhostCoalition();
                       gameManager.checkDoorCoalition();
                   }
                   break;
            }
            }
        };
    }

    /**
     * Method {@code checkCookieCoalition} checks if the Pacman touches cookies.
     * <br>
     * @param pacman : the only pacman instance
     * @param axis : defines if pacman goes horizontally or vertically.
     */
    private void checkCookieCoalition(Pacman pacman, String axis) {
        double pacmanCenterY = pacman.getCenterY();
        double pacmanCenterX = pacman.getCenterX();
        double pacmanLeftEdge = pacmanCenterX - pacman.getRadius();
        double pacmanRightEdge = pacmanCenterX + pacman.getRadius();
        double pacmanTopEdge = pacmanCenterY - pacman.getRadius();
        double pacmanBottomEdge = pacmanCenterY + pacman.getRadius();
        for (Cookie cookie:gameManager.cookieSet) {
            double cookieCenterX = cookie.getCenterX();
            double cookieCenterY = cookie.getCenterY();
            double cookieLeftEdge = cookieCenterX - cookie.getRadius();
            double cookieRightEdge = cookieCenterX + cookie.getRadius();
            double cookieTopEdge = cookieCenterY - cookie.getRadius();
            double cookieBottomEdge = cookieCenterY + cookie.getRadius();
            if (axis.equals("x")) {
                // pacman goes right
                if ((cookieCenterY >= pacmanTopEdge && cookieCenterY <= pacmanBottomEdge) && (pacmanRightEdge >= cookieLeftEdge && pacmanRightEdge <= cookieRightEdge)) {
                    if (cookie.isVisible()) {
                    	gameManager.score += cookie.getValue();
                    	gameManager.cookiesEaten++;
                    }
                    cookie.hide();
                }
                // pacman goes left
                if ((cookieCenterY >= pacmanTopEdge && cookieCenterY <= pacmanBottomEdge) && (pacmanLeftEdge >= cookieLeftEdge && pacmanLeftEdge <= cookieRightEdge)) {
                    if (cookie.isVisible()) {
                    	gameManager.score += cookie.getValue();
                    	gameManager.cookiesEaten++;
                    }
                    cookie.hide();
                }
            } else {
                // pacman goes up
                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanBottomEdge >= cookieTopEdge && pacmanBottomEdge <= cookieBottomEdge)) {
                    if (cookie.isVisible()) {
                    	gameManager.score += cookie.getValue();
                    	gameManager.cookiesEaten++;
                    }
                    cookie.hide();
                }
                // pacman goes down
                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanTopEdge <= cookieBottomEdge && pacmanTopEdge >= cookieTopEdge)) {
                    if (cookie.isVisible()) {
                    	gameManager.score += cookie.getValue();
                    	gameManager.cookiesEaten++;
                    }
                    cookie.hide();
                }
            }
            gameManager.scoreBoard.score.setText("Score: " + gameManager.score);
            if (gameManager.cookiesEaten == gameManager.cookieSet.size() && gameManager.gameEnded == false) {
            	gameManager.gameEnded = true;
            	gameManager.endGame();
            }
        }
    }
}
