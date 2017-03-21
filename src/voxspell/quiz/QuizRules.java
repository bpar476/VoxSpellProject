package voxspell.quiz;

/**
 * Singleton class holding the current rules for spelling quizzes. Parameters can be set and reset
 * and are used to create Quiz objects at runtime.
 * @author bpar
 *
 */
public class QuizRules {
	
	private static final int DEFAULT_NUM_CHANCES = 2;
	private static final int DEFAULT_START_LEVEL = 1;
	private static final String DEFAULT_WORD_LIST = (System.getProperty("user.dir") + "/.Resources/wordlists/NZCER-spelling-lists.txt");
	private static final int DEFAULT_QUIZ_SIZE = 10 ;
	
	private static QuizRules singleton;
	private static boolean locationSet;
	private static boolean levelSet;
	private static boolean numSet;
	private static boolean chanceSet;
	
	private String wordListLocation;
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
	
	public int getLevel() {
		return startLevel;
	}
	
	public int getNumWordsInQuiz() {
		return numWordsInQuiz;
	}
	
	public int getNumChances() {
		return numChances;
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
		return singleton;
	}


}
