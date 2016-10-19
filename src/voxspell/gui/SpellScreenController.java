package voxspell.gui;

import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import voxspell.Config;
import voxspell.VoxSpell;
import voxspell.festival.Festival;
import voxspell.quiz.DummyQuiz;
import voxspell.quiz.NewQuiz;
import voxspell.quiz.PracticeQuiz;
import voxspell.quiz.Quiz;
import voxspell.quiz.QuizResults;
import voxspell.quiz.QuizRules;
import voxspell.user.profile.User;

/**
 * Controller for Spelling interface. Works with QuizRules and Quiz classes to manage the quiz experience
 * Also has GUI options for changing the voice and returning to the main menu. Handles key events for pressing 
 * enter to submit your words. Quiz logic offloaded to NewQuiz object.
 * @author bpar
 *
 */
public class SpellScreenController {


	//functional fields
	private boolean inQuiz;
	private Quiz quiz;
	private int wordUpTo;
	private int score;
	private int streak;
	private int highStreak;
	private Long startTime;
	private Long endTime;
	private QuizResults results;

	//FXML fields
	@FXML
	private Button skipWord;
	@FXML
	private Button startSubmit;
	@FXML
	private Button speakAgain;
	@FXML
	private Button mainMenu;
	@FXML
	private TextField spellZone;
	@FXML
	private Label quizTypeLabel;
	@FXML
	private Label levelLabel;
	@FXML
	private Label correct;
	@FXML
	private Label incorrect;
	@FXML
	private Label progressLabel;
	@FXML
	private Label spellListLabel;
	@FXML
	private Label streakLabel;
	@FXML
	private Label scoreLabel;
	@FXML
	private Label previousEnterLabel;
	@FXML
	private Button endQuiz;
	@FXML
	private ComboBox voiceBox;

	/**
	 * Handles when the start quiz or submit button is pressed. Takes the entered word and uses the quiz to check its correctness.
	 * Updates labels.
	 * @param ae
	 */
	@FXML
	public void	handleStartSubmitButtonPressed(ActionEvent ae){
		if(!inQuiz){
			quizTypeLabel.setText(quiz.getType());
			//If quiz has not started, instantiate quiz object and set up labels.
			Festival.FestivalService serv = Festival.getInstance();
			wordUpTo = 1;
			serv.announce("Starting new quiz");
			serv.restart();
			speakAgain.setDisable(false);
			spellZone.setDisable(false);
			inQuiz = true;
			startSubmit.setText("Submit");
			if(quiz instanceof PracticeQuiz){
				progressLabel.setVisible(false);
				endQuiz.setVisible(true);
				skipWord.setVisible(true);
			}else{
				progressLabel.setVisible(true);
			}
			progressLabel.setText("Word 1/" + quiz.size());
			levelLabel.setText("Level: " + quiz.getLevel());
			quiz.speakWord();
			startTime = System.currentTimeMillis();
		}else{
			//If currently in a quiz, get the entered word and submit it.
			String answer = spellZone.getText();
			//TODO check for non-letter characters
			if(answer.equals("")){
				return;
			}
			submit(answer);
		}
		spellZone.requestFocus();
	}

	/**
	 * Used to submit the user's effort when they press a key (only matters for Enter).
	 * @param ke keyboard event generated when a key is pressed.
	 */
	@FXML
	public void handleEnterPressed(KeyEvent ke){
		if(inQuiz){
			if(ke.getCode() == KeyCode.ENTER){
				String answer = spellZone.getText();
				if(answer.equals("")){
					return;
				}
				submit(answer);
				spellZone.setText("");
			}
		}
	}

	/**
	 * Stops the current quiz (only available for quizess that have infinite length.
	 */
	@FXML
	public void handleStopQuizPressed(ActionEvent ae){
		if(inQuiz){
			endTime = System.currentTimeMillis();
			results = quiz.getResults();
			results.setTimeTaken(endTime - startTime);
			if(highStreak == 0){
				highStreak = streak;
			}
			results.setBestStreak(highStreak);
			results.setScore(score);
			results.setLevel(quiz.getLevel());
			results.setNumWords(quiz.size());
			results.setQuizType(quiz.getType());
			String[] listLocation = Config.getWordListLocation().split("/");
			results.setWordlist(listLocation[listLocation.length-1]);

			Config.getUser().getHistory().add(results);

			//Load next screen and pass information to controller
			Stage primaryStage = VoxSpell.getMainStage();
			try {
				FXMLLoader loader = new FXMLLoader();
				Parent root = loader.load(getClass().getResource("ScoreSummary.fxml").openStream());
				SummaryScreenController controller = (SummaryScreenController)loader.getController();
				//Parent root = FXMLLoader.load(getClass().getResource("ScoreSummary.fxml"));
				controller.setResults(results);
				Scene scene = new Scene(root);
				scene.getStylesheets().add(DetailedStatisticsScreenController.class.getResource("main.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Lets the user skip the word. Only visible in practice mode.
	 */
	@FXML
	public void handleSkipPressed(){
		((PracticeQuiz)quiz).skipWord();
		spellZone.requestFocus();
		quiz.speakWord();
	}

	/**
	 * Method to handle when the "Say again" button is pressed
	 * @param ae action event generated by button being clicked.
	 */
	@FXML
	public void handleSayAgainPressed(){
		quiz.speakWord();
		spellZone.requestFocus();
	}

	/**
	 * Method to handle when a user wants to go back to the main menu.
	 * @param ae action event generated by button being clicked.
	 */
	@FXML
	public void handleMainMenuPressed(ActionEvent ae){
		Stage primaryStage = VoxSpell.getMainStage();
		QuizRules.reset();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*	
	 * Helper method to be used whenever you want a user to check if what they entered in the TextField
	 * is correct.
	 */
	private void submit(String answer){
		//Uses quiz object to determine correctness of user answer.
		int result = quiz.compare(answer);
		if(result == Quiz.CORRECT_FIRST_TRY || result == Quiz.CORRECT_NOT_FIRST_TRY){
			//If it is correct, increment the score and tell user they got it right.
			wordUpTo++;
			incorrect.setVisible(false);
			correct.setVisible(true);
			score++;
			streak++;
		}else if(result == Quiz.WRONG_LAST_TRY){
			//If they got the answer wrong and they have no more attempts, go to next word.
			//Reset streak.
			if(streak > highStreak){
				//Check to see if the current streak is the highest the user has achieved the whole quiz.
				highStreak = streak;
			}
			streak = 0;
			correct.setVisible(false);
			incorrect.setVisible(true);
			wordUpTo++;
		}else if(result == Quiz.WRONG_STILL_TRYING){
			//Reset streak if they are still trying, but do not go to next word.
			if(streak > highStreak){
				highStreak = streak;
			}
			streak = 0;
			correct.setVisible(false);
			incorrect.setVisible(true);
		}
		//Reset labels
		spellZone.setText("");
		progressLabel.setText("Word " + wordUpTo + "/" + quiz.size());
		previousEnterLabel.setText("You entered: " + answer);
		scoreLabel.setText("Score: " + score);
		streakLabel.setText("Streak: " + streak);
		//Check if the quiz is ended and do necessary actions.
		if(quiz.isEnded()){
			//Store result metadata in QuizResults object.
			endTime = System.currentTimeMillis();
			results = quiz.getResults();
			results.setTimeTaken(endTime - startTime);
			if(highStreak == 0){
				highStreak = streak;
			}
			results.setBestStreak(highStreak);
			results.setScore(score);
			results.setLevel(quiz.getLevel());
			results.setNumWords(quiz.size());
			results.setQuizType(quiz.getType());
			String[] listLocation = Config.getWordListLocation().split("/");
			results.setWordlist(listLocation[listLocation.length-1]);

			//Udate user' bests, only if not in practice
			if(!(quiz instanceof PracticeQuiz)){
				User usr = Config.getUser();
				if(score > usr.getBestScore()){
					usr.setBestScore(score);
					usr.setBestWordList(listLocation[listLocation.length-1]);
				}
				if(highStreak > usr.getBestStreak()){
					usr.setBestStreak(highStreak);
				}
				if(quiz.getLevel() > usr.getHighestLevel()){
					usr.setHighestLevel(quiz.getLevel());
					usr.unlockLevel(quiz.getLevel() +1);
				}
				usr.getHistory().add(results);
			}
			//Load next screen and pass Result information to controller
			Stage primaryStage = VoxSpell.getMainStage();
			try {
				FXMLLoader loader = new FXMLLoader();
				Parent root = loader.load(getClass().getResource("ScoreSummary.fxml").openStream());
				SummaryScreenController controller = (SummaryScreenController)loader.getController();
				controller.setResults(results);
				Scene scene = new Scene(root);
				scene.getStylesheets().add(DetailedStatisticsScreenController.class.getResource("main.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else{
			quiz.speakWord();
		}
		spellZone.requestFocus();
	}

	@FXML
	public void handlevoiceChanged(ActionEvent ae){
		String voice = (String)voiceBox.getSelectionModel().getSelectedItem();
		if(voice.equals("New Zealand")){
			Festival.getInstance().kiwiVoice();
		}else if(voice.equals("American")){
			Festival.getInstance().americanVoice();
		}
		Festival.getInstance().restart();
	}

	/**
	 * Used by the GUI to define what type of Quiz the SpellScreen should load.
	 * @param quiz
	 */
	protected void setQuiz(Quiz quiz){
		this.quiz = quiz;
	}

	/**
	 * Called by JavaFX framework to set up GUI elements.
	 */
	@FXML
	public void initialize(){
		wordUpTo = 0;
		//TODO should the progress label even be visible before a quiz starts?
		progressLabel.setVisible(false);
		score = 0;
		streak = 0;
		QuizRules rules = QuizRules.getInstance();
		String listLocation = rules.getWordListLocation();
		String[] path = listLocation.split("/");
		String basename = path[path.length-1];
		spellListLabel.setText(basename);
		if(Config.isColourBlindMode()){
			correct.setTextFill(Color.BLUE);
		}
		ObservableList<String> voices = FXCollections.observableArrayList();
		voices.add("New Zealand");
		voices.add("American");
		//TODO add english voice.
		voiceBox.setItems(voices);
		voiceBox.setPromptText(Config.getVoice());
		spellZone.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					if(newValue.matches("(.*)([^A-Za-z\\s])(.*)")){
						((StringProperty)observable).setValue(oldValue);
					}
				}
			);
	}

}
