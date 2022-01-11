package sample;

/**
 * Class {@code Round} defines a basic data structure for a {@link sample.ScoreBoard} to pop up at the end of each round.
 * 
 * <p> No external package imported<br>
 * <p> An instance of Round should contain the score of this round, the difficulty of this round and the time when game ends.
 *
 * <p>
 * @author Chen Liao
 * 
 * @version 1.03
 */

public class Round {
	
	public String score;
	public String difficulty;
	public String dateTime;
	
	// constructor
	/**
	 * constructor of {@code sample.Round}
	 * <br>
	 * @param score : the score of this round.
	 * @param difficulty : the difficulty of this round
	 * @param dateTime : current system time
	 */
	public Round(String score, String difficulty, String dateTime) {
		this.score = score;
		this.difficulty = difficulty;
		this.dateTime = dateTime;
	}
}
