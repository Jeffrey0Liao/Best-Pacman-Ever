package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sample.Main;

/**
 * Class {@code controller.StartingController} is the Controller Class of 'SettingPage.fxml'.
 * 
 * <p> provided by {@link java.io.IOException}, {@link javafx.event.ActionEvent}, {@link javafx.fxml.FXML},
 * {@link javafx.event.ActionEvent}, {@link javafx.event.ActionEvent} and {@link sample.Main}.<br>
 * 
 * <p> This is the controller class for page the game starting page. Actions are supervised and reacted in this class.
 * user can launch the game by clicking the start button.
 * 
 * <p>
 * @author Boon Giin Lee
 * @author Heng Yu
 * @author Chen Liao
 * 
 * @version 1.03
 */
public class StartController {
	
	/**
     * Method {@code startButton} is the response function for clicking on button start.
     * Go to the setting page if the button is on click.
     * <br>
     * @param event event is a java action event for on click.
     * @throws standard IO exception
     */
	@FXML
	private void startButton(ActionEvent event) throws IOException {
		Main.setUp();
	}
}
