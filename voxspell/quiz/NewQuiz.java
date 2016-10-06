package voxspell.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import voxspell.festival.Festival;
import voxspell.festival.Festival.FestivalService;


/**
 * Class representing a new spelling quiz. Quiz is compiled from the NZCER spelling words list. Difficulty starts
 * from a given level. Class has methods that allows its objects to manage the quiz progression.
 * @author Ben Partridge
 *
 */
public class NewQuiz {

	//Static constants representing answer statistic
	public static final int CORRECT_FIRST_TRY = 0;
	public static final int CORRECT_NOT_FIRST_TRY = 1;
	public static final int WRONG_STILL_TRYING = 2;
	public static final int WRONG_LAST_TRY = 3;
	
	//Instance variables
	private ArrayList<String> wordsInLevel;
	private List<String> wordsToQuiz;
	private int upToWordIndex;
	private int numTries;
	private QuizResults results;
	private QuizRules rules;
	
	/**
	 * Constructor initialises quiz from given level. Creates 10 word list of words to spell from the
	 * NZCER spelling words list.
	 * @param startLevel The difficulty level to start the quiz from.
	 */
	public NewQuiz(){
		results = new QuizResults();
		rules = QuizRules.getInstance();
		WordList wordList = new WordList(rules.getWordListLocation(), rules.getStartLevel());
		//Set up wordList
		wordsInLevel = wordList.getWordList();
		wordsToQuiz = new ArrayList<>();
		
		Collections.shuffle(wordsInLevel);
		for(int i = 0; i < rules.getNumWordsInQuiz() & i < wordsInLevel.size(); i++){
			wordsToQuiz.add(wordsInLevel.get(i));
		}
		//Initialise some instance variables.
		numTries = 0;
	}
	
	/**
	 * @return The word that the user most recently spelled.
	 */
	public String getCurrentWord(){
		String word = wordsToQuiz.get(upToWordIndex);
		return word;
	}
	
	/**
	 * This method is used by other classes to compare a given answer to the correct spelling of the word held
	 * inside the quiz object. Ignores the case of the given word. Keeps track of whether it is the user's first
	 * or second attempt at spelling the word.
	 * @param answer The given spelling attempt for the word.
	 * @return An integer code specifying the correctness of the spelling. These are represented by public static final fields
	 * in the NewQuiz class:
	 * 		CORRECT_FIRST_TRY = 0
	 * 		CORRECT_SECOND_TRY = 1
	 * 		WRONG_FIRST_TRY = 2
	 * 		WRONG_SECOND_TRY = 3
	 */
	public int compare(String answer){
		FestivalService serv = Festival.getInstance();
		//Remove excess whitespace and ignore case.
		answer = answer.trim().toLowerCase();
		String correctAnswer = wordsToQuiz.get(upToWordIndex).toLowerCase().trim();
		if(answer.equals(correctAnswer)){
			if(upToWordIndex == wordsToQuiz.size()-1){
				//End of quiz, do not prompt for next word.
				serv.announce("Correct");
			}else{
				serv.announce("Correct! Next word... ");
				numTries = 0;
			}
			upToWordIndex++;
			if(numTries == 0){
				serv.restart();
				results.add(correctAnswer, WordScore.FirstTry);
				return CORRECT_FIRST_TRY;
			}else{
				serv.restart();
				results.add(correctAnswer, WordScore.NotFirstTry);
				return CORRECT_NOT_FIRST_TRY;
			}
		}else{
			if(numTries < rules.getNumChances()-1){
				serv.announce("Incorrect, try again");
				serv.restart();
				numTries++;
				return WRONG_STILL_TRYING;
			}else{
				if(upToWordIndex == wordsToQuiz.size()-1){
					//End of quiz, do not prompt for next word.
					serv.announce("Incorrect");
				}else{
					serv.announce("Incorrect! Next word... ");
				}
				upToWordIndex++;
				numTries = 0;
				serv.restart();
				results.add(correctAnswer, WordScore.Wrong);
				return WRONG_LAST_TRY;
			}
		}
	}
	
	/**
	 * Returns whether or not the quiz is over by using the current index
	 * that the quiz is up to.
	 * @return	A boolean value, false if the quiz is not finished and true if the
	 * quiz is over.
	 */
	public boolean isEnded(){
		if(upToWordIndex == wordsToQuiz.size()){
			return true;
		}
		return false;
	}
	
	/**
	 * @return The size of the spelling quiz.
	 */
	public int size(){
		return wordsToQuiz.size();
	}
	
	/**
	 * recites the current word to the user using the FestivalService.
	 */
	public void speakWord(){
		String word = wordsToQuiz.get(upToWordIndex);
		FestivalService serv = Festival.getInstance();
		serv.spellSpeed();
		serv.announce(word);
		serv.speakSpeed();
		serv.restart();
	}

}
