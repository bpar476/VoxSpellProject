package voxspell.gui;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import voxspell.Config;
import voxspell.VoxSpell;
import voxspell.quiz.QuizResults;

public class DetailedStatisticsScreenController {
	
	@FXML
	private TableView<QuizResults> quizTable;
	@FXML
	private TableColumn dateCol;
	@FXML
	private TableColumn<QuizResults,String> typeCol;
	@FXML
	private TableColumn<QuizResults,String> listCol;
	@FXML
	private TableColumn scoreCol;
	@FXML
	private TableColumn streakCol;
	@FXML
	private Button back;
	@FXML
	private Button moreDetails;
	
	/**
	 * Takes the user back to the previous screen (Score history screen).
	 * @param ae
	 */
	@FXML
	public void backPressed(ActionEvent ae){
		Stage primaryStage = VoxSpell.getMainStage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ScoreHistory.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads up the results from the table and launches the score summary screen with those results.
	 * @param ae
	 */
	@FXML
	public void moreDetailsPressed(ActionEvent ae){
		Stage primaryStage = VoxSpell.getMainStage();
		try {
			FXMLLoader loader = new FXMLLoader();
			//May need to create another pane or make screen use a different controller.
			Parent root = loader.load(getClass().getResource("ScoreSummary.fxml").openStream());
			SummaryScreenController controller = (SummaryScreenController)loader.getController();
			//TODO Get results from table
			//---->controller.setResults(results);			
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize(){
		fillTable();
	}
	
	private void fillTable(){
		final ObservableList<QuizResults> data = FXCollections.observableArrayList();
		dateCol.setCellValueFactory(new PropertyValueFactory<QuizResults,Date>("dateCompleted"));
		typeCol.setCellValueFactory(cellData -> cellData.getValue().getQuizTypeProperty());
		listCol.setCellValueFactory(cellData -> cellData.getValue().getWordlistProperty());
		scoreCol.setCellValueFactory(new PropertyValueFactory<QuizResults,Number>("score"));
		streakCol.setCellValueFactory(new PropertyValueFactory<QuizResults,Number>("bestStreak"));
		
		Iterator<QuizResults> resultIterator = Config.getUser().getHistory().iterator();
		
		while(resultIterator.hasNext()){
			data.add(resultIterator.next());
		}
		
		quizTable.setItems(data);
	}
}
