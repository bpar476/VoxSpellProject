package voxspell.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import voxspell.DummyQuiz;
import voxspell.Festival;
import voxspell.Festival.FestivalService;

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
		}
		spellZone.requestFocus();
	}
	
	/**
	 * Used to submit the user's effort when they press the enter key.
	 * @param ke
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
