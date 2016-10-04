package voxspell.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import voxspell.VoxSpell;
import voxspell.festival.Festival;
import voxspell.festival.Festival.FestivalService;
import voxspell.quiz.DummyQuiz;

public class SpellScreenController {

	//functional fields
	private boolean inQuiz;
	private DummyQuiz quiz;
	private int wordUpTo;
	private int score;
	private int streak;

	//FXML fields
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
	
	/**
	 * Handles when the start quiz or submit button is pressed. Takes the entered word and uses the quiz to check its correctness.
	 * Updates labels.
	 * @param ae
	 */
	@FXML
	public void	handleStartSubmitButtonPressed(ActionEvent ae){
		if(!inQuiz){
			speakAgain.setDisable(false);
			spellZone.setDisable(false);
			inQuiz = true;
			startSubmit.setText("Submit");
			quiz = new DummyQuiz();
			quiz.speakWord();
		}else{
			String answer = spellZone.getText();
			if(answer.equals("")){
				return;
			}
			submit(answer);
			if(quiz.isEnded()){
				speakAgain.setDisable(true);
			}
		}
		spellZone.requestFocus();
	}
	
	/**
	 * Used to submit the user's effort when they press a key (only matters for Enter).
	 * @param ke keyboard event generated when a key is pressed.
	 */
	@FXML
	public void handleEnterPressed(KeyEvent ke){
		if(ke.getCode() == KeyCode.ENTER){
			String answer = spellZone.getText();
			if(answer.equals("")){
				return;
			}
			submit(answer);
			spellZone.setText("");
		}
	}
	
	/**
	 * Method to handle when the "Say again" button is pressed
	 * @param ae action event generated by button being clicked.
	 */
	@FXML
	public void handleSayAgainPressed(){
		quiz.speakWord();
	}
	
	/**
	 * Method to handle when a user wants to go back to the main menu.
	 * @param ae action event generated by button being clicked.
	 */
	@FXML
	public void handleMainMenuPressed(ActionEvent ae){
		Stage primaryStage = VoxSpell.getMainStage();
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
		int result = quiz.compare(answer);
		FestivalService serv = Festival.getInstance();
		if(result == DummyQuiz.CORRECT_FIRST_TRY || result == DummyQuiz.CORRECT_SECOND_TRY){
			serv.announce("Correct!");
			wordUpTo++;
			incorrect.setVisible(false);
			correct.setVisible(true);
			score++;
			streak++;
		}else{
			streak = 0;
			serv.announce("Incorrect");
			correct.setVisible(false);
			incorrect.setVisible(true);
		}
		spellZone.setText("");
		progressLabel.setText("Word " + wordUpTo + "/10");
		previousEnterLabel.setText("You entered: " + answer);
		scoreLabel.setText("Score: " + score);
		streakLabel.setText("Streak: " + streak);
		quiz.speakWord();
		serv.restart();
	}
	
	
	@FXML
	public void initialize(){
		quizTypeLabel.setText("Quiz: DummyQuiz");
		levelLabel.setText("Level: 1");
		wordUpTo = 0;
		progressLabel.setText("Word 0/10");
		score = 0;
		streak = 0;
	}
}
