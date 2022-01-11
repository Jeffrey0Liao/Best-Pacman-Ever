package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import factory.Maze;
import factory.MazeFactory;

/**
 * This class {@code GameManager} enables the interactions among all elements in pacman.
 * 
 * <p> Privided by {@link javafx.animation.AnimationTimer}, {@link javafx.scene.Group}, 
 * {@link javafx.scene.image.ImageView}, {@link javafx.scene.input.KeyCode}, {@link javafx.scene.input.KeyEvent}, 
 * {@link javafx.scene.paint.Color}, {@link javafx.scene.shape.Rectangle}, {@link javafx.scene.text.Font}, 
 * {@link java.io.BufferedReader}, {@link java.io.BufferedWriter}, {@link java.io.FileReader}, {@link java.io.FileWriter}, 
 * {@link java.io.IOException}, {@link java.text.SimpleDateFormat}, {@link java.util.ArrayList}, {@link java.util.Arrays}, 
 * {@link java.util.Date}, {@link java.util.HashSet}, {@link java.util.Set}, {@code factory.Maze}, {@code factory.MazeFactory}.<br>
 * 
 * <p>
 * This class implements basic functions for the game to run. 
 * In-game page could be drawn in this class.
 * Page update per frame could be achieved.
 * start game, end game, restart game is implemented
 * 
 * @author Goongiin Lee
 * @author Yu Heng
 * @author Chen Liao
 * 
 * @version 1.0.3
 */
public class GameManager {
	
	// display container
	public Group root;
	
	// main elements in the layout
    public Pacman pacman;
    public Set<Cookie> cookieSet;
    public Set<Ghost> ghosts; 
    public Set<ImageView> ghostIcons;
    public Score scoreBoard;
    public ScoreBoard perScoreBoard;
    public ArrayList<Integer> highScoreBoard = new ArrayList<Integer>();
    // customize items
    private SetUp setup;
    public Maze maze;
    public MazeFactory mf;
    public Rectangle background;
    
    // animation timer
    public AnimationTimer leftPacmanAnimation;
    public AnimationTimer rightPacmanAnimation;
    public AnimationTimer upPacmanAnimation;
    public AnimationTimer downPacmanAnimation;
    
    // counters and flags
    private int money;
    public int lifes;
    public int score;
    private int lifes_cpy;
    private int speed_cpy;
    public boolean gameEnded;
    public int cookiesEaten;

    /**
     * Constructor for Class {@code GameManager}.
     * Initialize the game manager.
     * @param root the base javafx group for in-game page
     */
    GameManager(Group root) {
        this.root = root;
        this.mf = new MazeFactory();
        this.pacman = Pacman.getInstance();
        Pacman.pacmanInit(2.5 * BarObstacle.THICKNESS, 2.5 * BarObstacle.THICKNESS, this);
        this.cookieSet = new HashSet<>();
        this.ghosts = new HashSet<>();
        this.perScoreBoard = new ScoreBoard();
        this.lifes = 3;
        this.score = 0;
        this.cookiesEaten = 0;
        
        //this.maze = mf.getMaze(0);
    }
    
    /**
     * Method {@code fetchSetUp} get settings for the game
     * 
     * @param setup instance of class {@link sample.SetUp}, containing customization variables. 
     * 
     * customizations for:
     * pacman icon
     * ghost icon
     * color of obstacles
     * color of the background
     * game difficulty 
     * map
     */
    public void fetchSetUp(SetUp setup) {
    	this.setup = setup;
    	// factory elements fetching
        this.maze = mf.getMaze(this.setup.mp);
    	// pacman icon customization
    	Pacman.customize(this.setup.pacmanPath);
    	// ghost icon customization (outside)
    	// obs
    	this.maze.customize(this.setup.obsColor);
    	// bg
    	// diffi
    	
    	BufferedReader reader = null;    	
    	try {
            reader = new BufferedReader(new FileReader("./src/source/Store.txt"));
            String tempString = reader.readLine();
            String[] commands = tempString.split(",");
            this.money = Integer.valueOf(commands[0]);
            this.lifes = Integer.valueOf(commands[1]);
            Pacman.setSpeed(Integer.valueOf(commands[2]));
            this.lifes_cpy = this.lifes;
            this.speed_cpy = Integer.valueOf(commands[2]);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    	
    	this.leftPacmanAnimation = this.pacman.createAnimation("left");
        this.rightPacmanAnimation = this.pacman.createAnimation("right");
        this.upPacmanAnimation = this.pacman.createAnimation("up");
        this.downPacmanAnimation = this.pacman.createAnimation("down");
    }

    /**
     * Method {@code lifeLost} set one life less.
     * Minus 10 marks
     * Reset the position of pacman 
     */
    public void lifeLost() {
        this.leftPacmanAnimation.stop();
        this.rightPacmanAnimation.stop();
        this.upPacmanAnimation.stop();
        this.downPacmanAnimation.stop();
        for (Ghost ghost : ghosts) {
            ghost.getAnimation().stop();
        }
        this.pacman.setCenterX(2.5 * BarObstacle.THICKNESS);
        this.pacman.setCenterY(2.5 * BarObstacle.THICKNESS);
        Pacman.moveIcon();
        lifes--;
        score -= 10;
        this.scoreBoard.lifes.setText("Lifes: " + this.lifes);
        this.scoreBoard.score.setText("Score: " + this.score);
        if (lifes == 0) {
            this.endGame();
        }
    }

    /**
     * Method {@code endGame} ends the game
     * Clear pacman and ghosts
     * generate the result of this round and add it to the high score list
     */
    public void endGame() {
        this.gameEnded = true;
        root.getChildren().remove(pacman);
        for (Ghost ghost : ghosts) {
            root.getChildren().remove(ghost);
        }
        javafx.scene.text.Text endGame = new javafx.scene.text.Text("Game Over, press ESC to restart");
        endGame.setX(BarObstacle.THICKNESS * 3);
        endGame.setY(BarObstacle.THICKNESS * 28);
        endGame.setFont(Font.font("Arial", 40));
        endGame.setFill(Color.ROYALBLUE);
        root.getChildren().remove(this.scoreBoard.score);
        root.getChildren().remove(this.scoreBoard.lifes);
        root.getChildren().add(endGame);
        
        BufferedWriter writer = null;    	
    	try {
            writer = new BufferedWriter(new FileWriter("./src/source/Store.txt"));
            String tempString = null;
            tempString = Integer.toString(this.money += this.score) + "," + Integer.toString(this.lifes_cpy) + "," +Integer.toString(this.speed_cpy);
            writer.write(tempString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        //display high score board
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //get date & time
        this.root = this.perScoreBoard.addScoreBoard(root, new Round(Integer.toString(this.score), Integer.toString(this.setup.diffi), df.format(new Date())));
    }
    

    /**
     * Method {@code restartGame} restarts the game.
     * @param event binded as an {@link javafx.scene.input.KeyEvent} with the key "ESC"
     * 
     * reinitialize game data and game board.
     * draw the in-game page again.
     */
    public void restartGame(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE && gameEnded) {
            root.getChildren().clear();
            this.cookieSet.clear();
            this.ghosts.clear();
            this.drawBoard();
            this.pacman.setCenterX(2.5 * BarObstacle.THICKNESS);
            this.pacman.setCenterY(2.5 * BarObstacle.THICKNESS);
            Pacman.moveIcon();
            this.lifes = this.lifes_cpy;
            this.score = 0;
            this.cookiesEaten = 0;
            gameEnded = false;
        }
    }

    /**
     * Method {@code drawBoard} draws the board of the game with the cookies and the Pacman.
     * 
     * The obstacle layout is based on the map fetched from the map factory {@link factory.MazeFactory}.
     * The layout of cookies is based on the skip matrix in each maze {@code sample.Maze00.skip}.
     * This class also draws:
     * pacman
     * ghosts
     * current score board
     * background board
     */
    public void drawBoard() {
    	this.background = new Rectangle(1225, 700, this.setup.bgColor);
    	this.root.getChildren().add(background);
        this.maze.CreateMaze(root);
        // 1st line
        Integer skip[] = Arrays.stream(maze.getObsPosition()[0]).boxed().toArray(Integer[]::new); // int[] -> Integer[]
        for (int i = 0; i < 23; i++) {
            if (!Arrays.asList(skip).contains(i)) {
                Cookie cookie = new Cookie(((2*i) + 2.5) * BarObstacle.THICKNESS, 2.5 * BarObstacle.THICKNESS);
                this.cookieSet.add(cookie);
                root.getChildren().add(cookie);
            }
        }

        // 2nd line
        skip = Arrays.stream(maze.getObsPosition()[1]).boxed().toArray(Integer[]::new);
        for (int i = 0; i < 23; i++) {
            if (!Arrays.asList(skip).contains(i)) {
                Cookie cookie = new Cookie(((2*i) + 2.5) * BarObstacle.THICKNESS, 4.5 * BarObstacle.THICKNESS);
                this.cookieSet.add(cookie);
                root.getChildren().add(cookie);
            }
        }

        // 3rd line
        skip = Arrays.stream(maze.getObsPosition()[2]).boxed().toArray(Integer[]::new);
        for (int i = 0; i < 23; i++) {
            if (!Arrays.asList(skip).contains(i)) {
                Cookie cookie = new Cookie(((2*i) + 2.5) * BarObstacle.THICKNESS, 6.5 * BarObstacle.THICKNESS);
                this.cookieSet.add(cookie);
                root.getChildren().add(cookie);
            }
        }

        // 4th line
        skip = Arrays.stream(maze.getObsPosition()[3]).boxed().toArray(Integer[]::new);
        for (int i = 0; i < 23; i++) {
            if (!Arrays.asList(skip).contains(i)) {
                Cookie cookie = new Cookie(((2 * i) + 2.5) * BarObstacle.THICKNESS, 8.5 * BarObstacle.THICKNESS);
                this.cookieSet.add(cookie);
                root.getChildren().add(cookie);
            }
        }

        // 5th line
        skip = Arrays.stream(maze.getObsPosition()[4]).boxed().toArray(Integer[]::new);
        for (int i = 0; i < 23; i++) {
            if (!Arrays.asList(skip).contains(i)) {
                Cookie cookie = new Cookie(((2*i) + 2.5) * BarObstacle.THICKNESS, 10.5 * BarObstacle.THICKNESS);
                this.cookieSet.add(cookie);
                root.getChildren().add(cookie);
            }
        }

        // 6th line
        skip = Arrays.stream(maze.getObsPosition()[5]).boxed().toArray(Integer[]::new);
        for (int i = 0; i < 23; i++) {
            if (!Arrays.asList(skip).contains(i)) {
                Cookie cookie = new Cookie(((2*i) + 2.5) * BarObstacle.THICKNESS, 12.5 * BarObstacle.THICKNESS);
                this.cookieSet.add(cookie);
                root.getChildren().add(cookie);
            }
        }

        // 7th line
        skip = Arrays.stream(maze.getObsPosition()[6]).boxed().toArray(Integer[]::new);
        for (int i = 0; i < 23; i++) {
            if (!Arrays.asList(skip).contains(i)) {
                Cookie cookie = new Cookie(((2 * i) + 2.5) * BarObstacle.THICKNESS, 14.5 * BarObstacle.THICKNESS);
                this.cookieSet.add(cookie);
                root.getChildren().add(cookie);
            }
        }

        // 8th line
        skip = Arrays.stream(maze.getObsPosition()[7]).boxed().toArray(Integer[]::new);
        for (int i = 0; i < 23; i++) {
            if (!Arrays.asList(skip).contains(i)) {
                Cookie cookie = new Cookie(((2 * i) + 2.5) * BarObstacle.THICKNESS, 16.5 * BarObstacle.THICKNESS);
                this.cookieSet.add(cookie);
                root.getChildren().add(cookie);
            }
        }

        // 9th line
        skip = Arrays.stream(maze.getObsPosition()[8]).boxed().toArray(Integer[]::new);
        for (int i = 0; i < 23; i++) {
            if (!Arrays.asList(skip).contains(i)) {
                Cookie cookie = new Cookie(((2 * i) + 2.5) * BarObstacle.THICKNESS, 18.5 * BarObstacle.THICKNESS);
                this.cookieSet.add(cookie);
                root.getChildren().add(cookie);
            }
        }

        // 10th line
        skip = Arrays.stream(maze.getObsPosition()[9]).boxed().toArray(Integer[]::new);
        for (int i = 0; i < 23; i++) {
            if (!Arrays.asList(skip).contains(i)) {
                Cookie cookie = new Cookie(((2*i) + 2.5) * BarObstacle.THICKNESS, 20.5 * BarObstacle.THICKNESS);
                this.cookieSet.add(cookie);
                root.getChildren().add(cookie);
            }
        }

        // 11th line
        skip = Arrays.stream(maze.getObsPosition()[10]).boxed().toArray(Integer[]::new);
        for (int i = 0; i < 23; i++) {
            if (!Arrays.asList(skip).contains(i)) {
                Cookie cookie = new Cookie(((2 * i) + 2.5) * BarObstacle.THICKNESS, 22.5 * BarObstacle.THICKNESS);
                this.cookieSet.add(cookie);
                root.getChildren().add(cookie);
            }
        }
        root.getChildren().add(this.pacman);
        root.getChildren().add(Pacman.getIcon());
        this.generateGhosts();
        this.scoreBoard = new Score(root, this.lifes_cpy);
        this.background.toBack();
    }

    /**
     * Method {@code generateGhosts} generates the ghosts for the pacman.
     * Each ghost starts as an individual thread {@link java.lang.Runnable}.
     * the instances of ghosts are managed as a set.
     */
    public void generateGhosts() {
        Ghost g1 = new Ghost(18.5 * BarObstacle.THICKNESS, 12.5 * BarObstacle.THICKNESS, Color.DEEPPINK, maze, this);
        g1.customize(this.setup.ghostPath);
        g1.speedSetter(this.setup.diffi);
        this.root.getChildren().add(g1);
        this.root.getChildren().add(g1.getIcon());
        this.ghosts.add(g1);
        
        Ghost g2 = new Ghost(22.5 * BarObstacle.THICKNESS, 12.5 * BarObstacle.THICKNESS, Color.GREENYELLOW, maze, this);
        g2.customize(this.setup.ghostPath);
        g2.speedSetter(this.setup.diffi);
        this.root.getChildren().add(g2);
        this.root.getChildren().add(g2.getIcon());
        this.ghosts.add(g2);
        
        Ghost g3 = new Ghost(28.5 * BarObstacle.THICKNESS, 12.5 * BarObstacle.THICKNESS, Color.BLACK, maze, this);
        g3.customize(this.setup.ghostPath);
        g3.speedSetter(this.setup.diffi);
        this.root.getChildren().add(g3);
        this.root.getChildren().add(g3.getIcon());
        this.ghosts.add(g3);
        
        Ghost g4 = new Ghost(28.5 * BarObstacle.THICKNESS, 9.5 * BarObstacle.THICKNESS, Color.SPRINGGREEN, maze, this);
        g4.customize(this.setup.ghostPath);
        g4.speedSetter(this.setup.diffi);
        this.root.getChildren().add(g4);
        this.root.getChildren().add(g4.getIcon());
        this.ghosts.add(g4);
    }

//    /**
//     * Moves the pacman
//     * @param event
//     */
//    public void movePacman(KeyEvent event) {
//        for (Ghost ghost : this.ghosts) {
//            ghost.run();
//        }
//        switch(event.getCode()) {
//            case RIGHT:
//                this.rightPacmanAnimation.start();
//                break;
//            case LEFT:
//                this.leftPacmanAnimation.start();
//                break;
//            case UP:
//                this.upPacmanAnimation.start();
//                break;
//            case DOWN:
//                this.downPacmanAnimation.start();
//                break;
//        }
//    }
//
//    /**
//     * Stops the pacman
//     * @param event
//     */
//    public void stopPacman(KeyEvent event) {
//        switch(event.getCode()) {
//            case RIGHT:
//                this.rightPacmanAnimation.stop();
//                break;
//            case LEFT:
//                this.leftPacmanAnimation.stop();
//                break;
//            case UP:
//                this.upPacmanAnimation.stop();
//                break;
//            case DOWN:
//                this.downPacmanAnimation.stop();
//                break;
//        }
//    }
//
//    /**
//     * Creates an animation of the movement.
//     * @param direction
//     * @return
//     */
//    private AnimationTimer createAnimation(String direction) {
//        double step = 5;
//        return new AnimationTimer()
//        {
//            public void handle(long currentNanoTime)
//            {
//            switch (direction) {
//                case "left":
//                    if (!maze.isTouching(pacman.getCenterX() - pacman.getRadius(), pacman.getCenterY(), 15)) {
//                        pacman.setCenterX(pacman.getCenterX() - step);
//                        Pacman.moveIcon();
//                        checkCookieCoalition(pacman, "x");
//                        checkGhostCoalition();
//                        checkDoorCoalition();
//                    }
//                    break;
//                case "right":
//                    if (!maze.isTouching(pacman.getCenterX() + pacman.getRadius(), pacman.getCenterY(), 15)) {
//                        pacman.setCenterX(pacman.getCenterX() + step);
//                        Pacman.moveIcon();
//                        checkCookieCoalition(pacman, "x");
//                        checkGhostCoalition();
//                        checkDoorCoalition();
//                    }
//                    break;
//                case "up":
//                    if (!maze.isTouching(pacman.getCenterX(), pacman.getCenterY() - pacman.getRadius(), 15)) {
//                        pacman.setCenterY(pacman.getCenterY() - step);
//                        Pacman.moveIcon();
//                        checkCookieCoalition(pacman, "y");
//                        checkGhostCoalition();
//                        checkDoorCoalition();
//                    }
//                    break;
//                case "down":
//                   if (!maze.isTouching(pacman.getCenterX(), pacman.getCenterY() + pacman.getRadius(), 15)) {
//                       pacman.setCenterY(pacman.getCenterY() + step);
//                       Pacman.moveIcon();
//                       checkCookieCoalition(pacman, "y");
//                       checkGhostCoalition();
//                       checkDoorCoalition();
//                   }
//                   break;
//            }
//            }
//        };
//    }
//
//    /**
//     * Checks if the Pacman touches cookies.
//     * @param pacman
//     * @param axis
//     */
//    private void checkCookieCoalition(Pacman pacman, String axis) {
//        double pacmanCenterY = pacman.getCenterY();
//        double pacmanCenterX = pacman.getCenterX();
//        double pacmanLeftEdge = pacmanCenterX - pacman.getRadius();
//        double pacmanRightEdge = pacmanCenterX + pacman.getRadius();
//        double pacmanTopEdge = pacmanCenterY - pacman.getRadius();
//        double pacmanBottomEdge = pacmanCenterY + pacman.getRadius();
//        for (Cookie cookie:cookieSet) {
//            double cookieCenterX = cookie.getCenterX();
//            double cookieCenterY = cookie.getCenterY();
//            double cookieLeftEdge = cookieCenterX - cookie.getRadius();
//            double cookieRightEdge = cookieCenterX + cookie.getRadius();
//            double cookieTopEdge = cookieCenterY - cookie.getRadius();
//            double cookieBottomEdge = cookieCenterY + cookie.getRadius();
//            if (axis.equals("x")) {
//                // pacman goes right
//                if ((cookieCenterY >= pacmanTopEdge && cookieCenterY <= pacmanBottomEdge) && (pacmanRightEdge >= cookieLeftEdge && pacmanRightEdge <= cookieRightEdge)) {
//                    if (cookie.isVisible()) {
//                        this.score += cookie.getValue();
//                        this.cookiesEaten++;
//                    }
//                    cookie.hide();
//                }
//                // pacman goes left
//                if ((cookieCenterY >= pacmanTopEdge && cookieCenterY <= pacmanBottomEdge) && (pacmanLeftEdge >= cookieLeftEdge && pacmanLeftEdge <= cookieRightEdge)) {
//                    if (cookie.isVisible()) {
//                        this.score += cookie.getValue();
//                        this.cookiesEaten++;
//                    }
//                    cookie.hide();
//                }
//            } else {
//                // pacman goes up
//                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanBottomEdge >= cookieTopEdge && pacmanBottomEdge <= cookieBottomEdge)) {
//                    if (cookie.isVisible()) {
//                        this.score += cookie.getValue();
//                        this.cookiesEaten++;
//                    }
//                    cookie.hide();
//                }
//                // pacman goes down
//                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanTopEdge <= cookieBottomEdge && pacmanTopEdge >= cookieTopEdge)) {
//                    if (cookie.isVisible()) {
//                        this.score += cookie.getValue();
//                        this.cookiesEaten++;
//                    }
//                    cookie.hide();
//                }
//            }
//            this.scoreBoard.score.setText("Score: " + this.score);
//            if (this.cookiesEaten == this.cookieSet.size() && this.gameEnded == false) {
//            	this.gameEnded = true;
//                this.endGame();
//            }
//        }
//    }
//
    /**
     * Method {@code checkGhostCoalition} checks if pacman is touching a ghost.
     * 
     * If the boundary of pacman has any overlap with the boundary of any ghosts, a coalition is qualified.
     * pacman will lose one life in this round {@code sample.GameManager.lifes}.
     */
    public void checkGhostCoalition() {
        double pacmanCenterY = pacman.getCenterY();
        double pacmanCenterX = pacman.getCenterX();
        double pacmanLeftEdge = pacmanCenterX - pacman.getRadius();
        double pacmanRightEdge = pacmanCenterX + pacman.getRadius();
        double pacmanTopEdge = pacmanCenterY - pacman.getRadius();
        double pacmanBottomEdge = pacmanCenterY + pacman.getRadius();
        for (Ghost ghost : ghosts) {
            double ghostLeftEdge = ghost.getX();
            double ghostRightEdge = ghost.getX() + ghost.getWidth();
            double ghostTopEdge = ghost.getY();
            double ghostBottomEdge = ghost.getY() + ghost.getHeight();
            if ((pacmanLeftEdge <= ghostRightEdge && pacmanLeftEdge >= ghostLeftEdge) || (pacmanRightEdge >= ghostLeftEdge && pacmanRightEdge <= ghostRightEdge)) {
                if ((pacmanTopEdge <= ghostBottomEdge && pacmanTopEdge >= ghostTopEdge) || (pacmanBottomEdge >= ghostTopEdge && pacmanBottomEdge <= ghostBottomEdge)) {
                    lifeLost();
                }
            }
        }
    }
    
    /**
     * Method {@code checkDoorCoalition} check if pacman is passing through magic doors.
     * 
     * If the pacman is in the area of either magic door, it would be immediately send to the opposite one.
     * 
     */
    public void checkDoorCoalition() {
        double pacmanCenterX = pacman.getCenterX();
        if (pacmanCenterX <= 0.0) {
        	pacman.setCenterX(1215.0);
        	pacman.setCenterY(300.0);
        	Pacman.moveIcon();
        }
        if (pacmanCenterX >= 1225.0) {
        	pacman.setCenterX(10.0);
        	pacman.setCenterY(325.0);
        	Pacman.moveIcon();
        }
    }

}
