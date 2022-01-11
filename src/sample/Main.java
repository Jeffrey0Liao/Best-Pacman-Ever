package sample;

import java.net.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * @version 1.0.3
 * This class {@code Main} is the main class (launch class) of the pacman game.
 * 
 * This class {@code Main} extends Application class
 * @see javafx.event.EventHandler<ActionEvent> 
 * to enable the utilization of javafx
 * 
 * @param theStage initial launching stage for this game
 * @param theStartPage initial launching scene set in the primary stage
 * @param theSettingPage subordinate setting page, could be set in the primary stage
 * 
 * @author Boongiin Lee
 * @author Yu Heng
 * @author Chen Liao
 */
public class Main extends Application implements EventHandler<ActionEvent>{
	public static Stage theStage;
	public static Scene theStartPage;
	public static Scene theSettingPage;
	public static Scene theStorePage;
	
	/**
	 * This method {@code start} launches the primary stage of pacman
	 * {@link #start(Stage)} 
	 * The method will load FXML files of the start page and the setting page 
	 * then set the start page as the first scene of the primary stage
	 * 
	 */
    @Override
    public void start(Stage theStage) throws Exception{
    	
    	Main.theStage = theStage;
    	theStage.setTitle( "Pacman" );
        
        //create start page of this game
    	Parent startPage = FXMLLoader.load(getClass().getResource("/source/StartPage.fxml"));
        Main.theStartPage = new Scene(startPage, 1225, 700);
        theStage.setScene(theStartPage);
        
        
        //create setting page of the game
        Parent settingPage = FXMLLoader.load(getClass().getResource("/source/SettingPage.fxml"));
		Main.theSettingPage = new Scene(settingPage, 1225, 700);
		
		//load store page of the game
		Parent storePage = FXMLLoader.load(getClass().getResource("/source/StorePage.fxml"));
		Main.theStorePage = new Scene(storePage, 1225, 700);
		
        
        //initial page presentation
        theStage.show();
    }
    
    /**
     * This method {@code setUp} will set the setting page as the current scene of the primary stage
     * {@link #setUp()}
     * This method will be called by
     */
    public static void setUp() {
    	Main.theStage.setScene(Main.theSettingPage);
    }
    
    /**
     * This method {@code starter} will set up the in-game page of pacman
     * {@link #starter(SetUp)}
     * 
     * This method takes an instance of SetUp as input and generate corresponding game board based on settings
     * {@code sample.SetUp}
     * 
     * The method will first create a game manager with a base javafx group. 
     * Then the game manager will be initialized by settings.
     * Finally game manager will create the in-game scene with the base javafx group
     * {@code sample.GameManager.drawBoard}
     * <br> @param setup the setting package contains all customization information
     */
    public static void starter(SetUp setup) {
    	 //create in-game page
    	Group root = new Group();
    	Scene theScene = new Scene( root );
    	Canvas canvas = new Canvas( 1225, 600 );
    	root.getChildren().add( canvas );
      
    	GameManager gameManager = new GameManager(root);
    	gameManager.fetchSetUp(setup);
    	gameManager.drawBoard();
	
    	theScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> gameManager.pacman.movePacman(event));
    	theScene.addEventHandler(KeyEvent.KEY_RELEASED, event -> gameManager.pacman.stopPacman(event));
	  	theScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> gameManager.restartGame(event));
	  	
	  	theStage.setScene(theScene);
	}
    
    /**
     * Main function of pacman game
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }


	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}