package voxspell.quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class used to store information about a single quiz. Includes score,
 * The words the user spelled and the result they had with them. Also implements
 * serializable so that it can be stored.
 * @author bpar
 *
 */
public class QuizResults implements Serializable{

	private static final long serialVersionUID = 3992581471467692760L;
	private ArrayList<Result> results;
	private int bestStreak;
	private int score;
	private long timeTaken;
	private int numWordsInQuiz;
	private int level;
	private Date dateCompleted;
	private transient SimpleStringProperty quizTypeProperty;
	private transient SimpleStringProperty wordlistProperty;
	private String quizType;
	private String wordlist;
	
	public QuizResults(){
		results = new ArrayList<>();
		dateCompleted = new Date();
	}
	
	/**
	 * Adds a word-result pair to the total quiz results
	 * @param word
	 * @param score
	 */
	public void add(String word, WordScore score, int attempts){
		results.add(new Result(word, score, attempts));
	}
	
	//Getters and Setters...
	
	public void setQuizType(String type){
		quizType = type;
		quizTypeProperty = new SimpleStringProperty(quizType);
	}
	
	public SimpleStringProperty getQuizTypeProperty(){
		return quizTypeProperty;
	}
	
	public String getQuizType(){
		return quizTypeProperty.get();
	}
	
	public String getTrueQuizType(){
		return quizType;
	}
	
	
	public void setScore(int score){
		this.score = score;
	}
	
	public void setBestStreak(int streak){
		this.bestStreak = streak;
	}
	
	public void setTimeTaken(Long time){
		this.timeTaken = time;
	}
	
	public int getScore(){
		return score;
	}
	
	public int getBestStreak(){
		return bestStreak;
	}
	
	public long getTime(){
		return timeTaken;
	}
	
	public void setNumWords(int num){
		this.numWordsInQuiz = num;
	}
	
	public int getNumWords(){
		return numWordsInQuiz;
	}
	
	public void setLevel(int lev){
		this.level = lev;
	}
	
	public int getLevel(){
		return level;
	}
	
	public Date getDateCompleted(){
		return dateCompleted;
	}
	
	public String getWordlist() {
		return wordlistProperty.get();
	}
	
	public String getTrueWordlist(){
		return wordlist;
	}

	public SimpleStringProperty getWordlistProperty(){
		return wordlistProperty;
	}
	
	public void setWordlist(String wordlist) {
		this.wordlist = wordlist;
		wordlistProperty = new SimpleStringProperty(wordlist);
	}
	
	/**
	 * Returns the number of words that the user attempted to spell in this quiz.
	 * @return
	 */
	public int answeredSize(){
		return results.size();
	}
	
	/**
	 * Returns an Iterator object to iterate over all the word Results stored in this object.
	 * @return
	 */
	public Iterator<Result> iterator(){
		return results.iterator();
	}

	/**
	 * Nested class representing the a user's result for spelling a given word.
	 * @author bpar
	 *
	 */
	public class Result implements Serializable{
		private static final long serialVersionUID = 6912547030460845110L;
		private String word;
		private WordScore score;
		private int attempts;
		
		public Result(String word, WordScore score, int attempts){
			this.word = word;
			this.score = score;
			this.attempts = attempts;
		}
		
		/**
		 * Returns the word associated witht this spelling result.
		 * @return
		 */
		public String getWord(){
			return word;
		}
		
		/**
		 * Returns the score associated with this spelling result.
		 * @return
		 */
		public WordScore getScore(){
			return score;
		}
		
		/**
		 * Returns the number of times the user tried to spell the given word
		 */
		public int getAttempts(){
			return attempts;
		}
	}
}
