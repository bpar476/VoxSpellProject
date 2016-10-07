package voxspell.quiz;

import java.util.ArrayList;
import java.util.Iterator;

public class QuizResults {

	private ArrayList<Result> results;
	private int bestStreak;
	private int score;
	private long timeTaken;
	private int numWordsInQuiz;
	private int level;
	
	public QuizResults(){
		results = new ArrayList<>();
	}
	
	/**
	 * Adds a word-result pair to the total quiz results
	 * @param word
	 * @param score
	 */
	public void add(String word, WordScore score, int attempts){
		results.add(new Result(word, score, attempts));
	}
	
	public Iterator<Result> iterator(){
		return results.iterator();
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
	
	public int getStreak(){
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
	
	/**
	 * Nested class representing the a user's result for spelling a given word.
	 * @author bpar
	 *
	 */
	public class Result{
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
