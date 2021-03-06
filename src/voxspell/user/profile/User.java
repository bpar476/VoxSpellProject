package voxspell.user.profile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import voxspell.user.statistics.QuizHistory;

/**
 * Class to represent a user of the application. Stores their result history as well as
 * user credentials (username and password).
 * @author bpar
 *
 */
public class User implements Serializable{
	
	private static final String FILE_LOCATION = System.getProperty("user.dir") + "/.Resources/data/";
	private static final long serialVersionUID = -4799555998018565833L;
	private QuizHistory history;
	private String username;
	private int bestScore;
	private int bestStreak;
	private String bestWordList;
	private int highestLevel;
	private int unlockedLevel;
	
	public User(String username){
		unlockedLevel = 1;
		history = new QuizHistory(this);
		this.username = username;
	}
	
	//Getters and setters for statistics
	
	public int getHighestLevel(){
		return highestLevel;
	}
	
	public int getUnlockedLevel(){
		return unlockedLevel;
	}
	
	public int getBestScore(){
		return bestScore;
	}
	
	public int getBestStreak(){
		return bestStreak;
	}
	
	public String getBestList(){
		return bestWordList;
	}
	
	public void setHighestLevel(int level){
		highestLevel = level;
	}
	
	public void setBestScore(int score){
		bestScore = score;
	}
	
	public void unlockLevel(int level){
		unlockedLevel = level;
	}
	
	public void setBestWordList(String listname){
		bestWordList = listname;
	}
	
	public void setBestStreak(int streak){
		bestStreak = streak;
	}
	
	/**
	 * Creates a simple string representation of the user (their username).
	 */
	@Override
	public String toString(){
		return this.username;
	}
	
	/**
	 * Returns the user's quiz history object. Holding their last 20 quiz results.
	 * @return
	 */
	public QuizHistory getHistory(){
		return this.history;
	}
	
	public void reset(){
		history.reset();
		bestScore = 0;
		bestStreak = 0;
		bestWordList = "";
		highestLevel = 0;
	}
	
	/**
	 * Stores the state of the user object in a serialized object file.
	 */
	public void write(){
		File writeLocation = new File(FILE_LOCATION + toString() + ".ser");
		if(!writeLocation.exists()){
			try {
				writeLocation.createNewFile();
			} catch (IOException e) {
				System.err.println("Fatal error: Unable to create user result file");
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(writeLocation);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
			oos.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
