package voxspell.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import voxspell.quiz.QuizResults;

public class SummaryScreenController {
	
	//Logical fields
	private QuizResults results;
	
	//FXML fields
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
		
	}
	
	@FXML
	public void handleRetry(ActionEvent ae){
		
	}
	
	@FXML
	public void handleMainMenu(ActionEvent ae){
	
	}
	
	@FXML
	public void handleRewardVideo(ActionEvent ae){
		
	}
	
	public void setResults(QuizResults results){
		this.results = results;
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
	}
	
	@FXML
	public void initialize(){
		
	}
	
}
