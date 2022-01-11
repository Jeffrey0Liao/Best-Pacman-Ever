package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class {@code Cookie} describes the implementation of a single cookie in the game
 * 
 * <p> Provided by {@link javafx.scene.paint.Color}, {@link javafx.scene.shape.Circle} <br>
 * 
 * <p> This class describes the implementation of a single cookie in the game. A cookie here is in the shape of circle and could have
 * position, color and visibility. The visibility could be set with methods {@code hide} and {@code show}.
 * 
 * @author Boon Giin Lee
 * @author Heng Yu
 * @author Chen Liao
 * 
 * @version 1.03
 */
public class Cookie extends Circle {

    private int value;

    public Cookie(double x, double y) {
        this.value = 5;
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(12.5);
        this.setFill(Color.SADDLEBROWN);
    }

    /**
     * Method {@code getValue} returns the value of a certain cookie.
     * @return point of this cookie
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Method {@code hide} sets the cookie invisible from the board.
     */
    public void hide() {
        this.setVisible(false);
    }

    /**
     * Method {@code show} sets the cookie visible from the board.
     */
    public void show() {
        this.setVisible(true);
    }

}