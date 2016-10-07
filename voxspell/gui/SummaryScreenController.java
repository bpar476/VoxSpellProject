package voxspell.gui;

import java.io.IOException;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import voxspell.VoxSpell;
import voxspell.quiz.QuizResults;
import voxspell.quiz.QuizResults.Result;
import voxspell.quiz.QuizRules;
import voxspell.quiz.WordScore;

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
	private ListView<QuizResults.Result> history;

	private ObservableList<Result> resultsList;


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
		history.setCellFactory(new Callback<ListView<Result>, ListCell<Result>>(){

			@Override
			public ListCell<Result> call(ListView<Result> arg0) {
				return new WordCell();
			}

		});

		resultsList = FXCollections.observableArrayList();
		Iterator<Result> resultIt = results.iterator();
		while(resultIt.hasNext()){
			resultsList.add(resultIt.next());
		}
		history.setItems(resultsList);

	}

	private class WordCell extends ListCell<Result>{
		@Override
		protected void updateItem(Result item, boolean empty){
			if(item != null){
			super.updateItem(item, empty);
				if(item.getScore() == WordScore.FirstTry){
					setText(item.getWord() + ": Correct");
					setTextFill(Color.GREEN);
				}else if(item.getScore() == WordScore.NotFirstTry){
					setText(item.getWord() + ": Correct (" + item.getAttempts() + ")");
					setTextFill(new Color(1.0, 0.4, 0, 1));
				}else{
					setText(item.getWord() + ": Wrong");
					setTextFill(Color.RED);
				}
			}
		}
	}

}
