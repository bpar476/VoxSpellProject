package voxspell.quiz;

import java.util.Collections;

import voxspell.festival.Festival;

public class PracticeQuiz extends NewQuiz{
	
	public PracticeQuiz(){
		super();
		type = "Practice Quiz";
	}
	
	/**
	 * User has option to skip word in a practice quiz.
	 */
	public void skipWord(){
		upToWordIndex++;
		numTries = 0;
	}
	
	/**
	 * Reuses the logic for comparison from NewQuiz but reshuffles the wordlist and starts again
	 * when the user reaches the "end" of the quiz.
	 */
	@Override
	public int compare(String answer){
		int result = super.compare(answer);
		if(upToWordIndex == size()){
			Collections.shuffle(wordsToQuiz);
			upToWordIndex = 0;
			Festival.getInstance().announce("Next word");
		}
		return result;
	}
}
