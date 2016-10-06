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
import voxspell.quiz.QuizResults;
import voxspell.quiz.QuizRules;

public class SummaryScreenController {
	

	//FXML fields
	@FXML
	private Button playVideo;
	@FXML
	private Label levelLabel;
	@FXML
	private Label scoreLabel;
	@FXML
	private Label streakLabel;
	@FXML
	private Label timeLabel;
	@FXML
	private Label excellent;
	@FXML
	private Label good;
	@FXML
	private Label okay;
	@FXML
	private Label poor;
	
	
	@FXML
	public void handleNextLevel(ActionEvent ae){
		QuizRules.setQuizType("New Quiz");
		//QuizRules.setStartLevel(Config.startLevel++);
		//QuizRules.setWordListLocation(Config.wordlist);
		changeScene("SpellScreen.fxml");
	}
	
	@FXML
	public void handleRetry(ActionEvent ae){
		QuizRules.setQuizType("New Quiz");
		//QuizRules.setStartLevel(Config.startLevel);
		//QuizRules.setWordListLocation(Config.wordlist);
		changeScene("SpellScreen.fxml");
	}
	
	@FXML
	public void handleMainMenu(ActionEvent ae){
		changeScene("MainMenu.fxml");
	}
	
	@FXML
	public void handleRewardVideo(ActionEvent ae){
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("RewardPlayer.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.hide();
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void changeScene(String fxmlFile){
		Stage primaryStage = VoxSpell.getMainStage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setResults(QuizResults results){
		if(results.getScore() > results.getNumWords() * 0.9 & results.getStreak() == results.getNumWords()){
			excellent.setVisible(true);
		}else if(results.getScore() > results.getNumWords() * 0.7){
			good.setVisible(true);
		}else if(results.getScore() > results.getNumWords() * 0.5){
			okay.setVisible(true);
		}else{
			poor.setVisible(true);
		}
		
		scoreLabel.setText("Score: " + results.getScore());
		streakLabel.setText("Best Streak: " + results.getStreak());
		long timeInSeconds = results.getTime()/1000;
		long mins = timeInSeconds / 60;
		timeInSeconds = timeInSeconds % 60;
		if(timeInSeconds < 10){
			timeLabel.setText("Time: " + mins + ":0" + timeInSeconds);
		}else{
			timeLabel.setText("Time: " + mins + ":" + timeInSeconds);			
		}
		levelLabel.setText("Level: " + results.getLevel());
		if(results.getScore() > results.getNumWords() * 0.8){
			playVideo.setVisible(true);
		}
	}
	
	@FXML
	public void initialize(){
		
	}
	
}
