package voxspell.quiz;

import java.util.ArrayList;
import java.util.Iterator;

public class QuizResults {

	private ArrayList<Result> results;
	
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
}
