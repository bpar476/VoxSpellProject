package voxspell;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import voxspell.Festival;

/**
 * 
 * This class contains the main method for the application. It sets up the main menu
 * scene when the application is run.
 * 
 * @author Tim Frew & Ben Partridge
 *
 */
public class VoxSpell extends Application {
	/**
	 * Does basic GUI initialisation.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			//Loads an initial window to choose the initial difficulty. InitialDifficulty transitions to the main menu.
			Parent root = FXMLLoader.load(getClass().getResource("SpellScreen.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					Festival.getInstance().close();
				}

			});
			primaryStage.setTitle("VoxSpell");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Initialises resource files for first time use if the files don't exist already.
	public static void main(String[] args) {
	/*	File stats, diff, specStats;
		String dir = System.getProperty("user.dir");
		stats = new File(dir + "/.Resources/statistics.ser");
		diff = new File(dir + "/.Resources/currentDifficultyLevel.txt");
		specStats = new File(dir + "/.Resources/specificStatsLevel.txt");
		try{
			stats.createNewFile();
			specStats.createNewFile();
			diff.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
		}*/
		launch(args);
	}
}
