package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Main;
import sample.SetUp;

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
public class SettingController {
	
	private ObservableList<String> pacmans = FXCollections.observableArrayList("Classic", "Nick", "Leo", "Andrew");
	private ObservableList<String> ghosts = FXCollections.observableArrayList("Classic", "Ghost1", "Ghost2", "Ghost3");
	private ObservableList<String> obstacleColors = FXCollections.observableArrayList("Default", "Red", "Yellow", "Blue", "Green", "White", "Black", "Pink", "Purple");
	private ObservableList<String> backgroundColors = FXCollections.observableArrayList("Default", "Red", "Yellow", "Blue", "Green", "White", "Black", "Pink", "Purple");
	private ObservableList<String> difficulties = FXCollections.observableArrayList("Heaven", "Novice", "Normal", "Hard", "Hell");
	private SetUp setup;
	private int map;
	
	/**
	 * Method {@code getChoices} is a method set for fetching selections as string values from
	 * the given combo boxes.
	 */
	private void getChoices() {
		String pacman = pacmanBox.getValue();
		String ghost = ghostBox.getValue();
		String obstacleColor = obstacleBox.getValue();
		String backgroundColor = backgroundBox.getValue();
		String difficulty = difficultyBox.getValue();
		this.setup = new SetUp(pacman, ghost, obstacleColor, backgroundColor, difficulty, this.map);
	}
	

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="a"
    private ComboBox<String> pacmanBox; // Value injected by FXMLLoader
    
    @FXML
    private ComboBox<String> ghostBox; // Value injected by FXMLLoader
    
    @FXML
    private ComboBox<String> obstacleBox; // Value injected by FXMLLoader
    
    @FXML
    private ComboBox<String> backgroundBox; // Value injected by FXMLLoader
    
    @FXML
    private ComboBox<String> difficultyBox; // Value injected by FXMLLoader
    
    @FXML
    private ImageView mapView;
    
    /**
     * Method {@code selectMap1} is the response function for clicking on button Map1.
     * in the setting page. The function will set the map identifier to 0 and shows the 
     * picture of the corresponding map.
     * <br>
     * @param event is a java action event for on click.
     */
    @FXML
    void selectMap1(ActionEvent event) {
    	this.map = 0;
    	Image img = new Image("./source/Map00.JPG", 400, 220, true, true);
    	mapView.setImage(img);
    }
    
    /**
     * Method {@code selectMap2} is the response function for clicking on button Map2.
     * in the setting page. The function will set the map identifier to 1 and shows the 
     * picture of the corresponding map.
     * <br>
     * @param event is a java action event for on click.
     */
    @FXML
    void selectMap2(ActionEvent event) {
    	this.map = 1;
    	Image img = new Image("./source/Map01.JPG", 400, 220, true, true);
    	mapView.setImage(img);
    }
    
    /**
     * Method {@code selectMap3} is the response function for clicking on button Map3.
     * in the setting page. The function will set the map identifier to 2 and shows the 
     * picture of the corresponding map.
     * <br>
     * @param event is a java action event for on click.
     */
    @FXML
    void selectMap3(ActionEvent event) {
    	this.map = 2;
    	Image img = new Image("./source/Map02.JPG", 400, 220, true, true);
    	mapView.setImage(img);
    }
    
    /**
     * Method {@code selectMap4} is the response function for clicking on button Map4.
     * in the setting page. The function will set the map identifier to 3 and shows the 
     * picture of the corresponding map.
     * <br>
     * @param event is a java action event for on click.
     */
    @FXML
    void selectMap4(ActionEvent event) {
    	
    }
    
    
    /**
     * Method {@code gameStartButton} is the response function for clicking on button Start.
     * in the setting page. The function will collect all selections in the setting page
     * and post them as a instance of {@code sample.SetUp} to {@code sample.GameManager}.
     * <br>
     * @param event is a java action event for on click.
     */
    @FXML
    void gameStartButton(ActionEvent event) {
    	this.getChoices();
    	Main.starter(this.setup);
    }
    
    /**
     * Method {@code storeButton} is the response function for clicking on button store.
     * Go to the store page if the button is on click.
     * <br>
     * @param event event is a java action event for on click.
     */
    @FXML
    void storeButton(ActionEvent event) {
    	Main.theStage.setScene(Main.theStorePage);
    }
    
    /**
     * Method {@code backButton} is the response function for clicking on button back.
     * Go back to the start page if the button is on click.
     * <br>
     * @param event event is a java action event for on click.
     */
    @FXML
    void backButton(ActionEvent event) {
    	Main.theStage.setScene(Main.theStartPage);
    }
    
    /**
     * Method {@code initialize} is the initialization function for loading the setting page.
     * Contents of combo boxes will be loaded when this function is called.
     * The function will be called when the FXML file is loaded.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        pacmanBox.getItems().addAll(pacmans);
        pacmanBox.setValue("Classic");
        ghostBox.getItems().addAll(ghosts);
        ghostBox.setValue("Classic");
        obstacleBox.getItems().addAll(obstacleColors);
        obstacleBox.setValue("Default");
        backgroundBox.getItems().addAll(backgroundColors);
        backgroundBox.setValue("Default");
        difficultyBox.getItems().addAll(difficulties);
        difficultyBox.setValue("Normal");
        
    }
}
