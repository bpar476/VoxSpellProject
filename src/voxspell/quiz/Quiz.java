package voxspell.quiz;

public interface Quiz {
	//Static constants representing answer statistic
	public static final int CORRECT_FIRST_TRY = 0;
	public static final int CORRECT_NOT_FIRST_TRY = 1;
	public static final int WRONG_STILL_TRYING = 2;
	public static final int WRONG_LAST_TRY = 3;
	
	/**
	 * This method is inteded to be used by the quiz to compare user answer
	 * with correct answer
	 * @param answer the user's attempt at spelling the word.
	 * @return an integer code representing the user's feedbeck. See public static final constants.
	 */
	public int compare(String answer);

	/**
	 * This method is used to tell when the quiz is over.
	 * @return true if the quiz is over, false otherwise.
	 */
	public boolean isEnded();

	/**
	 * This method is used to tell the size of the quiz (number of words).
	 */
	public int size();
	
	/**
	 * This method is used to get the current level of the quiz.
	 * @return
	 */
	public int getLevel();

	/**
	 * This tells the quiz to speak the current word again.
	 */
	public void speakWord();

	/**
	 * This method is used to get the user's results from the quiz.
	 * @return
	 */
	public QuizResults getResults();

	/**
	 * This method is used to tell the GUI what type of quiz the user is doing.
	 * @return
	 */
	public String getType();


}
