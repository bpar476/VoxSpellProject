package voxspell.gui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import voxspell.Config;
import voxspell.VoxSpell;
import voxspell.quiz.QuizRules;

public class MainMenuController {
	
	private String nextWindow;
	
	/**
	 * Handles when the "Start a quiz" button is pressed
	 * @param ae The action event generated by the button click event.
	 */
	@FXML
	public void handleStartQuizPressed(ActionEvent ae){
		nextWindow = "SpellScreen.fxml";
		QuizRules.setQuizType("New Quiz");
		QuizRules.setStartLevel(Config.getStartLevel());
		QuizRules.setWordListLocation(Config.getWordListLocation());
		changeScene();
	}
	
	/**
	 * Handles when the "Practice" button is pressed
	 * @param ae The action event generated by the button click event.
	 */
	@FXML
	public void handlePracticePressed(ActionEvent ae){
		nextWindow = "SpellScreen.fxml";
		QuizRules.setQuizType("Practice quiz");
		QuizRules.setInfinite(true);
		QuizRules.setNumWordsInQuiz(Integer.MAX_VALUE);
		QuizRules.setNumChances(-1);
		QuizRules.setStartLevel(Config.getStartLevel());
		QuizRules.setQuizType("Practice quiz");
		QuizRules.setWordListLocation(Config.getWordListLocation());
		changeScene();
	}
	
	/**
	 * Handles when the "Score History" button is pressed
	 * @param ae The action event generated by the button click event.
	 */
	@FXML
	public void handleScoreHistoryPressed(ActionEvent ae){
		nextWindow = "ScoreHistory.fxml";
		changeScene();
	}
	
	/**
	 * Handles when the "Settings" button is pressed
	 * @param ae The action event generated by the button click event.
	 */
	@FXML
	public void handleSettingsPressed(ActionEvent ae){
		nextWindow = "SettingsMenu.fxml";
		changeScene();
	}
	
	/**
	 * Handles when the "Quit" button is pressed
	 * @param ae The action event generated by the button click event.
	 */
	@FXML
	public void handleLogoutPressed(ActionEvent ae){
		nextWindow = "LoginScreen.fxml";
		changeScene();
	}
	
	//Helper method to change the scene.
	private void changeScene(){
		Stage primaryStage = VoxSpell.getMainStage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource(nextWindow));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
