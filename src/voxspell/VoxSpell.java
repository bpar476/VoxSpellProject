package voxspell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import voxspell.festival.Festival;
import voxspell.user.profile.User;

/**
 * 
 * This class contains the main method for the application. It sets up the main menu
 * scene when the application is run.
 * 
 * @author Tim Frew & Ben Partridge
 *
 */
public class VoxSpell extends Application {
	
	private static Stage mainStage;
	
	public static Stage getMainStage(){
		return mainStage;
	}
	/**
	 * Does basic GUI initialisation.
	 * @throws FileNotFoundException if properties file is not set up.
	 */
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		mainStage = primaryStage;
		mainStage.setResizable(false);
		BufferedReader rdr = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "/.Resources/data/voxspell.prop")));
		String line;
		try {
			while((line = rdr.readLine()) != null){
				String[] splitProperty = line.split("=");
				if(splitProperty[0].equals("voice")){
					if(splitProperty[1].equals("New Zealand")){
						Festival.getInstance().kiwiVoice();
					}else if(splitProperty[1].equals("American")){
						Festival.getInstance().americanVoice();
					}
					Festival.getInstance().restart();
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Parent root = FXMLLoader.load(getClass().getResource("gui/LoginScreen.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					Festival.getInstance().close();
					User user = Config.getUser();
					if(user != null){
						user.write();
					}
				}

			});
			primaryStage.setTitle("VoxSpell");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
