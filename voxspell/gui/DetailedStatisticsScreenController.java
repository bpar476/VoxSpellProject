package voxspell.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import voxspell.VoxSpell;

public class DetailedStatisticsScreenController {
	
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

}
