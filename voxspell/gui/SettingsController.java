package voxspell.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import voxspell.VoxSpell;

public class SettingsController {

	@FXML
	private Button addWordList;
	@FXML
	private Button resetHistory;
	@FXML
	private Button mainMenu;
	@FXML
	private ComboBox<String> voices;
	@FXML
	private ComboBox<String> wordLists;
	@FXML
	private ComboBox<String> startDifficulty;
	
	/**
	 * Handles when the user presses the "back to main menu" button.
	 * @param ae
	 */
	@FXML
	public void mainMenuButtonPressed(ActionEvent ae){
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
}
