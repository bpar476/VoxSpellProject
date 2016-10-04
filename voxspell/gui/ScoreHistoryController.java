package voxspell.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import voxspell.VoxSpell;

public class ScoreHistoryController {

	@FXML
	private Button fullHistoryButton;
	@FXML
	private Button mainMenu;
	@FXML
	private Label bestScoreLabel;
	@FXML
	private Label bestStreakLabel;
	@FXML
	private Label strongestListLabel;
	@FXML
	private Label highestLevelLabel;
	
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
}
