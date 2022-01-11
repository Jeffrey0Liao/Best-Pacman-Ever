
package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

import javafx.scene.Group;
import javafx.scene.control.Label;

/**
 * Class {@code ScoreBoard} represents the permanent high score list appears at the end of each round.
 * 
 * <p> Provided by {@link java.io.BufferedReader}, {@link java.io.BufferedWriter}, {@link java.io.FileReader}, 
 * {@link java.io.FileWriter}, {@link java.io.IOException}, {@link java.util.Comparator}, {@link java.util.PriorityQueue}.<br>
 * 
 * <p> This class controls the generation and visualization of the permanent high score list. The list is generated at the start
 * of the game by file reading.
 * The data structure of the score list is a priority queue. 
 * Every time a new record is added, the corresponding file would be updated.
 * 
 * <p>
 * @author Boon Giin Lee
 * @author Heng Yu
 * @author Chen Liao
 * 
 * @version 1.03
 */
public class ScoreBoard {
	private PriorityQueue<Round> pq;
	private Group root;
	
	/**
	 * Constructor for class {@code sample.ScoreBoard}.
	 */
	public ScoreBoard() {
		this.pq = new PriorityQueue<Round>(30, ScoreBoard.comparator);
		this.fileReader();
	}
	
	/**
	 * Comparator override
	 */
	public static Comparator<Round> comparator = new Comparator<Round>(){
		@Override
		public int compare(Round r1, Round r2) {
			// TODO Auto-generated method stub
			int a = Integer.parseInt(r1.score);
		    int b = Integer.parseInt(r2.score);
			if (a >= b) {
				return -1;
			} else if (a <= b){
				return 1;
			}
			return 0;
		}
    };
    
    /**
     * method {@code addRound} adds a new round to the current round priority queue.
     * <br>
     * @param rd : current round
     */
    private void addRound(Round rd) {
    	this.pq.add(rd);
    	this.fileWriter();
    }
    
    /**
     * method {@code fileReader} read the content of the file and add the content to the 
     * round priority queue.
     */
    private void fileReader() {
    	BufferedReader reader = null;
    	
    	try {
            reader = new BufferedReader(new FileReader("./src/source/ScoreBoard.txt"));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
        	    String[] commands = tempString.split(",");
        	    if (commands.length!=0) {
        	    	this.pq.add(new Round(commands[0], commands[1], commands[2]));
        	    }
            } 			    
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * method {@code fileWriter} updates the file with priority queue after every round
     */ 
    private void fileWriter() {
    	BufferedWriter writer = null;
    	
    	try {
            writer = new BufferedWriter(new FileWriter("./src/source/ScoreBoard.txt"));
            String tempString = null;
            
            for (Round rd : this.pq) {
            	tempString = rd.score + "," + rd.difficulty + "," + rd.dateTime;
            	writer.write(tempString);
            	writer.newLine();
            }			    
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * Method {@code addScoreBoard} adds a new round to the queue and visualize the high score list 
     * on the game board after each round
     * <br>
     * @param root : base javafx element
     * @param rd : this round
     * @return : the updated game board
     */
    public Group addScoreBoard(Group root, Round rd) {
    	this.root = root;
    	double posX = 550.0;
    	double posY = 50.0;
    	//record score in this turn
    	this.addRound(rd);
    	root.getChildren().clear();
    	
    	PriorityQueue<Round> pq_copy = new PriorityQueue<Round>(30, ScoreBoard.comparator);
    	pq_copy.addAll(this.pq);
    	
    	Label lbTitle = new Label("High Score Board");
    	lbTitle.setTranslateX(posX);
    	lbTitle.setTranslateY(posY);
    	root.getChildren().add(lbTitle);
    	posY += 50;
    	Label lbHint = new Label("Press ESC to restart the game");
    	lbHint.setTranslateX(posX-40);
    	lbHint.setTranslateY(posY);
    	root.getChildren().add(lbHint);
    	posY += 50;
    	while (pq_copy.size()!=0) {
    		Round a_rd = pq_copy.poll();
    		String difficulty = selectString(a_rd.difficulty);
    		Label lb1 = new Label(a_rd.score + "      " + difficulty + "      " + a_rd.dateTime);
    		lb1.setTranslateX(posX-50);
    		lb1.setTranslateY(posY);
    		root.getChildren().add(lb1);
    		posY += 50.0;
    	}
    	
    	return this.root;
    }
    
    private String selectString(String df) {
    	if (df.equals("3")) {
    		return "Heaven";
    	} else if (df.equals("4")) {
    		return "Novice";
    	} else if (df.equals("5")) {
    		return "Normal";
    	} else if (df.equals("6")) {
    		return "Hard";
    	} else {
    		return "Hell";
    	} 
    }
}
