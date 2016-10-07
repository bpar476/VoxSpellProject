package voxspell.quiz;

public class QuizRules {
	
	private static final int DEFAULT_NUM_CHANCES = 2;
	private static final int DEFAULT_START_LEVEL = 1;
	private static final String DEFAULT_WORD_LIST = (System.getProperty("user.dir") + "/.Resources/NZCER-spelling-lists.txt");
	private static final int DEFAULT_QUIZ_SIZE = 3;
	private static final String DEFAULT_QUIZ_TYPE = "Spelling Quiz";
	
	private static QuizRules singleton;
	private static boolean locationSet;
	private static boolean levelSet;
	private static boolean numSet;
	private static boolean chanceSet;
	private static boolean typeSet;
	
	private String wordListLocation;
	private String quizType;
	private int startLevel;
	private int numWordsInQuiz;
	private int numChances;
	
	public static void setWordListLocation(String location){
		if(singleton == null){
			singleton = new QuizRules();
		}
		singleton.wordListLocation = location;
		locationSet = true;
	}
	
	public static void setQuizType(String quizType) {
		if(singleton == null){
			singleton = new QuizRules();
		}
		singleton.quizType = quizType;
	}
	
	public static void setStartLevel(int level){
		if(singleton == null){
			singleton = new QuizRules();
		}
		singleton.startLevel = level;
		levelSet = true;
	}
	
	public static void setNumWordsInQuiz(int num){
		if(singleton == null){
			singleton = new QuizRules();
		}
		singleton.numWordsInQuiz = num;
		numSet = true;
	}
	
	public static void setNumChances(int num){
		if(singleton == null){
			singleton = new QuizRules();
		}
		singleton.numChances = num;
		chanceSet = true;
	}
	
	/**
	 * Resets all the fields in the QuizRules object.
	 */
	public static void reset(){
		singleton = new QuizRules();
		chanceSet = false;
		levelSet = false;
		locationSet = false;
		numSet = false;
	}
	
	public String getWordListLocation() {
		return wordListLocation;
	}
	
	public int getStartLevel() {
		return startLevel;
	}
	
	public int getNumWordsInQuiz() {
		return numWordsInQuiz;
	}
	
	public int getNumChances() {
		return numChances;
	}
	
	public String getQuizType() {
		return quizType;
	}
	
	/**
	 * Returns the singleton QuizRuels instance and gives it default values if certain
	 * fields are not initialised.
	 * @return
	 */
	public static QuizRules getInstance(){
		if(singleton == null){
			singleton = new QuizRules();
		}
		if(!chanceSet){
			singleton.numChances = DEFAULT_NUM_CHANCES;
		}
		if(!levelSet){
			singleton.startLevel = DEFAULT_START_LEVEL;
		}
		if(!locationSet){
			singleton.wordListLocation = DEFAULT_WORD_LIST;
		}
		if(!numSet){
			singleton.numWordsInQuiz = DEFAULT_QUIZ_SIZE;
		}
		if(!typeSet){
			singleton.quizType = DEFAULT_QUIZ_TYPE;
		}
		return singleton;
	}


}
