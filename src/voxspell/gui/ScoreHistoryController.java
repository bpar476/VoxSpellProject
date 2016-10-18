package voxspell.gui;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import voxspell.Config;
import voxspell.VoxSpell;
import voxspell.quiz.QuizResults;
import voxspell.user.profile.User;
import voxspell.user.statistics.QuizHistory;

/**
 * Controller for the "Score history" screen. Shows a line graph with the last 10 Quizzes the current
 * user has completed. Shows a summary of the user's greatest achievements. Allows user to move
 * to a more detailed screen where the user can look at each individual of the last 20 quizzes they have completed.
 * @author bpar
 *
 */
public class ScoreHistoryController {

	@FXML
	private LineChart<Date,Number> lastTenQuizzes;
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
		changeScene("MainMenu.fxml");
	}

	@FXML
	public void handleFullDetailPressed(){
		changeScene("DetailedStatsScreen.fxml");
	}

	/**
	 * Does some setup before the scene loads. Fills the chart and puts the detail in the user's
	 * Personal bests summary.
	 */
	@FXML
	public void initialize(){
		//Set up chart
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();

		xAxis.setLabel("Date");
		yAxis.setLabel("Score");

		//Fills chart with
		XYChart.Series series = new XYChart.Series();
		QuizHistory history = Config.getUser().getHistory();
		Iterator<QuizResults> resultsIterator = history.iterator();
		int i = 0;
		while(resultsIterator.hasNext() & i < 10){
			QuizResults results = resultsIterator.next();
			series.getData().add(new XYChart.Data<>("Quiz: " + (i+1),results.getScore()));
			i++;
		}
		lastTenQuizzes.getData().add(series);

		User usr = Config.getUser();
		bestScoreLabel.setText("Best score: " + usr.getBestScore());
		bestStreakLabel.setText("Best streak: " + usr.getBestStreak());
		String wordList = usr.getBestList();
		if(wordList == null){
			strongestListLabel.setText("Best wordlist: N/A");
		}else{
			strongestListLabel.setText("Best wordlist: " + usr.getBestList());
		}
		highestLevelLabel.setText("Highest level: " + usr.getHighestLevel());

	}

	//Helper method to change the scene.
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
}
