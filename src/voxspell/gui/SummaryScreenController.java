package voxspell.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import voxspell.Config;
import voxspell.VoxSpell;
import voxspell.quiz.NewQuiz;
import voxspell.quiz.PracticeQuiz;
import voxspell.quiz.QuizResults;
import voxspell.quiz.QuizResults.Result;
import voxspell.quiz.QuizRules;

/**
 * Controller class for the post-quiz summary. Takes results of quiz from SpellScreenController and 
 * displays them in a nice format for user. Includes all words and how they did spelling them as 
 * well as other information like score, best streak and time taken.
 * @author bpar
 *
 */
public class SummaryScreenController {

	private static final String REWARD_SONG_LOCATION = System.getProperty("user.dir") + "/.Resources/media/milky-chu_-_With_You_And_Icecream_Flying_-_cut.mp3";

	QuizResults results;
	//FXML fields
	@FXML
	private Button playVideo;
	@FXML
	private Button nextLevelButton;
	@FXML
	private Button backToStats;
	@FXML
	private Button mainMenuButton;
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
	
	private Media rewardSong;
	private MediaPlayer rewardSongPlayer;
	private ObservableList<Result> resultsList;

	/**
	 * Moves the user back to the spelling interface at the next level of difficulty
	 * for the current list. (Disabled if there is no next level).
	 * @param ae ActionEvent triggered by button press.
	 */
	@FXML
	public void handleNextLevel(ActionEvent ae){
		QuizRules.setStartLevel(QuizRules.getInstance().getLevel() + 1);
		QuizRules.setWordListLocation(Config.getWordListLocation());
		changeToSpellScene();
	}
	
	/**
	 * Moves the user back to the spelling interface at the same level of difficulty
	 * for the current list.
	 * @param ae ActionEvent triggered by button press.
	 */
	@FXML
	public void handleRetry(ActionEvent ae){
		QuizRules.setStartLevel(Config.getStartLevel());
		QuizRules.setWordListLocation(Config.getWordListLocation());
		changeToSpellScene();
	}
	
	//Helper method to change to a spelling scene.
	private void changeToSpellScene(){
		rewardSongPlayer.stop();
		Stage primaryStage = VoxSpell.getMainStage();
		try {
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(getClass().getResource("SpellScreen.fxml").openStream());
			SpellScreenController controller = loader.getController();
			
			if(results.getQuizType().equals("New Quiz")){
				controller.setQuiz(new NewQuiz());
			}else if(results.getQuizType().equals("Practice Quiz")){
				controller.setQuiz(new PracticeQuiz());
			}		
			Scene scene = new Scene(root);
			scene.getStylesheets().add(DetailedStatisticsScreenController.class.getResource("main.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles the action when the user presses the "Main menu" button
	 * @param ae The action event generated by clicking the button.
	 */
	@FXML
	public void handleMainMenu(ActionEvent ae){
		QuizRules.reset();
		changeScene("MainMenu.fxml");
	}
	
	/**
	 * Handles action for when user presses the "Play video" button. Only available if they scored
	 * over 80% in their quiz. Launches the RewardPlayer screen. @see RewardPlayerController.
	 * @param ae
	 */
	@FXML
	public void handleRewardVideo(ActionEvent ae){
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("RewardPlayer.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.hide();
			rewardSongPlayer.pause();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			rewardSongPlayer.play();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Helper method to change the scene.
	private void changeScene(String fxmlFile){
		rewardSongPlayer.stop();
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
	
	/**
	 * Method used to give the summary screen results from a quiz. Looks at the results
	 * and processes them to create the summary screen view.
	 * @param results
	 * @throws FileNotFoundException 
	 */
	public void setResults(QuizResults results) throws FileNotFoundException{
		this.results = results;
		//Looks through results object and sets all up the gui according to those values.
		if(results.getScore() > results.answeredSize() * 0.9 & results.getBestStreak() == results.answeredSize()){
			if(Config.isColourBlindMode()){
				excellent.setTextFill(Color.BLUE);
			}
			excellent.setVisible(true);
		}else if(results.getScore() > results.answeredSize() * 0.7){
			if(Config.isColourBlindMode()){
				good.setTextFill(Color.AQUA);
			}
			good.setVisible(true);
		}else if(results.getScore() > results.answeredSize() * 0.5){
			okay.setVisible(true);
			nextLevelButton.setVisible(false);
		}else{
			poor.setVisible(true);
			nextLevelButton.setVisible(false);
		}

		scoreLabel.setText("Score: " + results.getScore());
		streakLabel.setText("Best Streak: " + results.getBestStreak());
		long timeInSeconds = results.getTime()/1000;
		long mins = timeInSeconds / 60;
		timeInSeconds = timeInSeconds % 60;
		if(timeInSeconds < 10){
			timeLabel.setText("Time: " + mins + ":0" + timeInSeconds);
		}else{
			timeLabel.setText("Time: " + mins + ":" + timeInSeconds);			
		}
		levelLabel.setText("Level: " + results.getLevel());
		if(results.getScore() > results.answeredSize() * 0.8){
			playVideo.setVisible(true);
			nextLevelButton.setVisible(true);
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

		//Make sure user can't go to a level that isn't supported by the wordlist.
		File wordListFile = new File(Config.getWordListLocation());
		BufferedReader rdr = new BufferedReader(new FileReader(wordListFile));
		String line = "";
		int maxLevel = 0;
		try {
			while((line = rdr.readLine()) != null){
				if(line.charAt(0) == '%'){
					maxLevel++;
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading wordlist file");
			e.printStackTrace();
		}
		if(QuizRules.getInstance().getLevel() == maxLevel){
			nextLevelButton.setVisible(false);
			//TODO tell user they are on the highest level.
		}
	}
	
	/**
	 * Handles when the user clicks a word in the history list. It opens a web browser and loads
	 * a web page showing the definition of the highlighted word.
	 * @param me
	 */
	@FXML
	public void handleListClicked(MouseEvent me){
		Result res = history.getSelectionModel().getSelectedItem();
		String word = res.getWord();
		String url = "http://www.dictionary.com/browse/" + word + "?s=t";
		try{
			ProcessBuilder pb = new ProcessBuilder("x-www-browser", url);
			pb.start();
		}catch(IOException e){
			System.err.println("Error opening web browser, check your internet connection");
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets so that instead of returning to the main menu screen, the
	 * return location becomes the statistics screen.
	 */
	protected void setPrevScreenStats(){
		backToStats.setVisible(true);
		mainMenuButton.setVisible(false);
	}
	
	@FXML
	public void handleBackToStatsPressed(){
		changeScene("DetailedStatsScreen.fxml");
	}
	
	@FXML
	public void initialize(){
		/*Music: With You and Ice Cream Flying, Milky-chu. 
		Taken from:
		http://freemusicarchive.org/music/milky-chu/Carnival_For_Edelweisss_Ensemble/With_You_And_Icecream_Flying
		*/
		rewardSong = new Media("file:///" + REWARD_SONG_LOCATION);
		rewardSongPlayer = new MediaPlayer(rewardSong);
		rewardSongPlayer.setOnEndOfMedia(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				rewardSongPlayer.seek(Duration.ZERO);
			}
		});;
		if(!Config.getMuted())
		rewardSongPlayer.play();
	}

}
