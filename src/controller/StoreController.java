package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.Main;

/**
 * Class {@code controller.SettingController} is the Controller Class of 'SettingPage.fxml'.
 * 
 * <p> provided by {@link java.net.URL}, {@link java.util.ResourceBundle}, {@link javafx.collections.FXCollections}, 
 * {@link javafx.collections.ObservableList}, {@link javafx.event.ActionEvent}, {@link javafx.fxml.FXML},
 * {@link javafx.scene.control.ComboBox}, {@link javafx.scene.image.Image}, {@link javafx.scene.image.ImageView}, 
 * {@link sample.Main}, {@link sample.SetUp}.<br>
 * 
 * <p> This is the controller class for page the game setting page. Actions are supervised and reacted in this class.
 * combo boxes are initialized with observable array lists.
 * the icon of pacman, the icon of ghosts, the color of obstacles, the color of background, the difficulty and the map layout could 
 * be customized by provided combo boxes and buttons.
 * 
 * <p>
 * @author Boon Giin Lee
 * @author Heng Yu
 * @author Chen Liao
 * 
 * @version 1.03
 */
public class StoreController {
	
	private String[] commands;
	private int money;
	private int life;
	private int speed;
	
	@FXML
	private Label yourMoney;
	
    @FXML
    private Button speedUp;

    @FXML
    private Button moreLife;
    
    /**
     * Method {@code buyLife} is the response function for clicking on button extra life.
     * Buy pacman an extra life permanently at a cost of $1000 if the button is on click.
     * <br>
     * @param event event is a java action event for on click.
     */
    @FXML
    void buyLife(ActionEvent event) {
    	this.money -= 1000;
    	this.life +=1;
    	this.yourMoney.setText("$ " + Integer.toString(this.money));
    	
    	if (this.money<1000) {
    		moreLife.setDisable(true);
    		speedUp.setDisable(true);
    	} else {
    		moreLife.setDisable(false);
    		speedUp.setDisable(false);
    	}
    }
    
    /**
     * Method {@code buySpeed} is the response function for clicking on button speed up.
     * Buy pacman a speed up upgrade permanently at a cost of $1000 if the button is on click.
     * <br>
     * @param event event is a java action event for on click.
     */
    @FXML
    void buySpeed(ActionEvent event) {
    	this.money -= 1000;
    	this.speed += 1;
    	this.yourMoney.setText("$ " + Integer.toString(this.money));
    	
    	if (this.money<1000) {
    		moreLife.setDisable(true);
    		speedUp.setDisable(true);
    	} else {
    		moreLife.setDisable(false);
    		speedUp.setDisable(false);
    	}
    }
    
    /**
     * Method {@code storeGoBack} is the response function for clicking on button go back.
     * Go back to the setting page if the button is on click.
     * <br>
     * @param event event is a java action event for on click.
     */
    @FXML
    void storeGoBack(ActionEvent event) {
    	BufferedWriter writer = null;
    	
    	try {
            writer = new BufferedWriter(new FileWriter("./src/source/Store.txt"));
            String tempString = null;
            tempString = Integer.toString(this.money) + "," + Integer.toString(this.life) + "," +Integer.toString(this.speed);
            writer.write(tempString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    	Main.theStage.setScene(Main.theSettingPage);
    }
    
    /**
     * Method {@code initialize} is the initialization function for loading the store page.
     * data of the player will be loaded when this function is called.
     * Calculate for current money left, current pacman lives and current pacman speed.
     * The function will be called when the FXML file is loaded.
     */
    @FXML
    void initialize() {
    	
    	BufferedReader reader = null;    	
    	try {
            reader = new BufferedReader(new FileReader("./src/source/Store.txt"));
            String tempString = reader.readLine();
            String[] commands = tempString.split(",");
            this.commands = commands;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    	
    	this.money = Integer.valueOf(this.commands[0]);
    	this.life = Integer.valueOf(this.commands[1]);
    	this.speed = Integer.valueOf(this.commands[2]);
    	this.yourMoney.setText("$ " + Integer.toString(this.money));
    	if (this.money<1000) {
    		moreLife.setDisable(true);
    		speedUp.setDisable(true);
    	} else {
    		moreLife.setDisable(false);
    		speedUp.setDisable(false);
    	}
    }
}
