package voxspell.quiz;

import java.util.ArrayList;
import java.util.Iterator;

public class QuizResults {

	private ArrayList<Result> results;
	private int bestStreak;
	private int score;
	private long timeTaken;
	private int numWordsInQuiz;
	
	public QuizResults(){
		results = new ArrayList<>();
	}
	
	/**
	 * Adds a word-result pair to the total quiz results
	 * @param word
	 * @param score
	 */
	public void add(String word, WordScore score){
		results.add(new Result(word, score));
	}
	
	
	public class Result{
		private String word;
		private WordScore score;
		
		public Result(String word, WordScore score){
			this.word = word;
			this.score = score;
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
	}
	
	/**
	 * Returns an iterator over the results stored in this QuizResults object. The parts of these
	 * results can be accessed throught the {@link Result#getWord()} and {@link Result#getScore()} methods.
	 * @return
	 */
	public Iterator<Result> iterator(){
		return results.iterator();
	}
	
	/**
	 * Sets the score achieved in the associated quiz.
	 * @param score The final score of the user after finishing their quiz
	 */
	public void setScore(int score){
		this.score = score;
	}
	
	/**
	 * Sets the best streak that a user achieved at the end of a quiz.
	 * @param streak The user's best streak throughout the quiz.
	 */
	public void setBestStreak(int streak){
		this.bestStreak = streak;
	}
	
	/**
	 * Sets the time the user took to complete the quiz.
	 * @param time the time the user took to complete the quiz.
	 */
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
}
