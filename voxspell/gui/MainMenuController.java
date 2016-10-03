package voxspell.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import voxspell.VoxSpell;

public class MainMenuController {
	
	private String nextWindow;
	
	/**
	 * Handles when the "Start a quiz" or "Practice" button is pressed
	 * @param ae The action event generated by the button click event.
	 */
	@FXML
	public void handleStartQuizOrPracticePressed(ActionEvent ae){
		nextWindow = "SpellScreen.fxml";
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
		nextWindow = "Settings.fxml";
		changeScene();
	}
	
	/**
	 * Handles when the "Quit" button is pressed
	 * @param ae The action event generated by the button click event.
	 */
	@FXML
	public void handleQuitPressed(ActionEvent ae){
		VoxSpell.getMainStage().close();
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
