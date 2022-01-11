package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Class {@code Score} defines the score board shown when the game is in progress
 * 
 * <p>Provided by {@link javafx.scene.Group}, {@link javafx.scene.paint.Color}, {@link javafx.scene.text.Font}, 
 * {@link javafx.scene.text.Text}. <br>
 * 
 * <p> This class represents the real time in-game score board.
 * Current score and current lives left are shown on the board.
 * 
 * <p>
 * @author Boon Giin Lee
 * @author Heng Yu
 * @author Chen Liao
 * 
 * @version 1.03
 */
public class Score {

    public Text score;
    public Text lifes;
    
    /**
     * Constructor for class {@code smaple.Score}
     * @param root : base element of javafx page
     * @param life : lives left
     */
    Score(Group root, int life) {
        this.score = new Text(BarObstacle.THICKNESS * 4, BarObstacle.THICKNESS * 28, "Score: 0");
        this.lifes = new Text(BarObstacle.THICKNESS * 20, BarObstacle.THICKNESS * 28,"Lifes: " + Integer.toString(life));
        score.setFill(Color.MAGENTA);
        score.setFont(Font.font("Arial", 30));

        lifes.setFill(Color.MAROON);
        lifes.setFont(Font.font("Arial", 30));

        root.getChildren().add(score);
        root.getChildren().add(lifes);
    }
}
