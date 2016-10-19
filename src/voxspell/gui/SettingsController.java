package voxspell.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import voxspell.Config;
import voxspell.VoxSpell;
import voxspell.festival.Festival;

/**
 * Controller for settings screen. Handles user changing application settings and write them
 * to the voxspell properties file. User can change settings, confirm them and continue with the changes
 * saved to the voxspell properties file.
 * @author bpar
 *
 */
public class SettingsController {

	private static final String WORDLIST_EXTENSION = "/.Resources/wordlists/";
	private static final String PROPERTIES_EXTENSION = "/.Resources/data/voxspell.prop"; 

	@FXML
	private RadioButton colourBlindSelector;
	@FXML
	private Button addWordList;
	@FXML
	private Button resetHistory;
	@FXML
	private Button mainMenu;
	@FXML
	private ComboBox<String> voicesBox;
	@FXML
	private ComboBox<File> wordListsBox;
	@FXML
	private ComboBox<String> startDifficultyBox;
	@FXML
	private Label confirmedNotification;
	@FXML
	private RadioButton musicSelector;
	private boolean colourBlind;
	private boolean musicDisabled;

	/**
	 * Handles when the user presses the "back to main menu" button.
	 * @param ae
	 */
	@FXML
	public void mainMenuButtonPressed(ActionEvent ae){
		BufferedReader rdr = null;
		//Creates an alert that will be shown if the user has made changes and not saved them.
		Alert confirmDeparture = new Alert(AlertType.CONFIRMATION);
		confirmDeparture.setContentText("If you leave without clicking confirm"
				+ " you will lose the changes you have made.\n\nDo you still want to continue?");
		confirmDeparture.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		confirmDeparture.setHeaderText("Leave unsaved changes?");
		confirmDeparture.setTitle("Leave settings");
		try{
			//Reads through properties file and checks to see if there are discrepancies between file and
			//current state of settings screen. If there are, show the alert to allow user to save changes.
			rdr = new BufferedReader(new FileReader(System.getProperty("user.dir") + PROPERTIES_EXTENSION));
			String line;
			while((line = rdr.readLine()) != null){
				String[] splitProperty = line.split("=");
				if(splitProperty[0].equals("voice")){
					if(!splitProperty[1].equals(voicesBox.getSelectionModel().getSelectedItem())){
						Optional<ButtonType> result = confirmDeparture.showAndWait();
						if(result.get() == ButtonType.CANCEL){
							return;
						}else{
							Stage primaryStage = VoxSpell.getMainStage();
							try {
								Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
								Scene scene = new Scene(root);
								primaryStage.setScene(scene);
								primaryStage.show();
								return;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}else if(splitProperty[0].equals("wordlist")){
					if(!splitProperty[1].equals(wordListsBox.getSelectionModel().getSelectedItem().getName())){
						Optional<ButtonType> result = confirmDeparture.showAndWait();
						if(result.get() == ButtonType.CANCEL){
							return;
						}else{
							Stage primaryStage = VoxSpell.getMainStage();
							try {
								Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
								Scene scene = new Scene(root);
								primaryStage.setScene(scene);
								primaryStage.show();
								return;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}else if(splitProperty[0].equals("startlevel")){
					if(!splitProperty[1].equals(startDifficultyBox.getSelectionModel().getSelectedItem())){
						Optional<ButtonType> result = confirmDeparture.showAndWait();
						if(result.get() == ButtonType.CANCEL){
							return;
						}else{
							Stage primaryStage = VoxSpell.getMainStage();
							try {
								Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
								Scene scene = new Scene(root);
								primaryStage.setScene(scene);
								primaryStage.show();
								return;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}else if(splitProperty[0].equals("colourblind")){
					if(!splitProperty[1].equals("" + colourBlind)){
						Optional<ButtonType> result = confirmDeparture.showAndWait();
						if(result.get() == ButtonType.CANCEL){
							return;
						}else{
							Stage primaryStage = VoxSpell.getMainStage();
							try {
								Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
								Scene scene = new Scene(root);
								primaryStage.setScene(scene);
								primaryStage.show();
								return;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try {
				rdr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

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
	
	/**
	 * event handler for when the user toggles the music disable button.
	 */
	@FXML
	public void musicSelectorToggled(ActionEvent ae){
		confirmedNotification.setVisible(false);
		musicDisabled = !musicDisabled;
	}
	
	/**
	 * Prompts the user to make sure they want to do this, then delete's all of their
	 * quiz history data.
	 * @param ae
	 */
	@FXML
	public void resetHistoryPressed(ActionEvent ae){
		Alert al = new Alert(Alert.AlertType.CONFIRMATION);
		al.setContentText("Are you sure you want to delete all\nyour spelling history");
		Optional<ButtonType> butt = al.showAndWait();
		if(butt.get() == ButtonType.OK){
			Config.getUser().reset();
		}
	}
	
	/**
	 * Event handler for when the user toggles colourblind more button. 
	 * @param ae
	 */
	@FXML
	public void colourBlindToggled(ActionEvent ae){
		confirmedNotification.setVisible(false);
		colourBlind = !colourBlind;
	}
	
	/**
	 * Disables changes saved notification when the level is changed to reflect
	 * current state of settings.
	 * @param ae
	 */
	@FXML
	public void levelChanged(ActionEvent ae){
		confirmedNotification.setVisible(false);
	}
	/**
	 * Disables changes saved notification when the festival voice is changed \
	 * to reflect current state of settings.
	 * @param ae
	 */
	@FXML
	public void voiceChanged(ActionEvent ae){
		confirmedNotification.setVisible(false);
	}

	/**
	 * Updates the selectable items in the difficulty level box when a word list is selected.
	 * @param ae action event generated by combo box.
	 */
	@FXML
	public void wordListSelectionMade(ActionEvent ae){
		confirmedNotification.setVisible(false);
		ObservableList<String> levels = FXCollections.observableArrayList();
		BufferedReader rdr = null;
		if(wordListsBox.getValue() != null){
			try {
				rdr = new BufferedReader(new FileReader(wordListsBox.getValue()));
			} catch (FileNotFoundException e) {
				System.err.println("Error: File: " + wordListsBox.getValue() + " not found");
				return;
			}

			String line = null;
			try {
				while((line = rdr.readLine()) != null){
					if(line.charAt(0) == '%'){
						String[] levelLine = line.split("\\s+");
						if(Integer.parseInt(levelLine[1]) <= Config.getUser().getUnlockedLevel()){
							levels.add(levelLine[1]);
						}
					}
				}
				rdr.close();
			} catch (IOException e) {
				System.err.println("Error reading file: " + wordListsBox.getValue());
				return;
			}

			startDifficultyBox.setItems(levels);
			startDifficultyBox.getSelectionModel().select(0);
		}
	}

	
	/**
	 * Writes the current state of the settings screen to the voxspell properties file
	 * and shows a notification to show that it was successful.
	 * @param ae
	 */
	@FXML
	public void confirmPressed(ActionEvent ae){
		//Update config file
		String wordList = wordListsBox.getSelectionModel().getSelectedItem().getName();
		String startLevel = startDifficultyBox.getSelectionModel().getSelectedItem();
		String voice = voicesBox.getSelectionModel().getSelectedItem();

		try {
			File voxspellProp = new File(System.getProperty("user.dir") + PROPERTIES_EXTENSION);
			voxspellProp.delete();
			voxspellProp.createNewFile();
			PrintWriter wr = new PrintWriter(voxspellProp);
			wr.append("startlevel=" + startLevel + "\n");
			wr.append("wordlist=" + wordList + "\n");
			wr.append("colourblind=" + colourBlind + "\n");
			wr.append("voice=" + voice + "\n");
			wr.append("music_disabled=" + musicDisabled + "\n");
			wr.flush();
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Update festival voice
		if(voicesBox.getSelectionModel().getSelectedItem().equals("New Zealand")){
			Festival.getInstance().kiwiVoice();
		}else if(voicesBox.getSelectionModel().getSelectedItem().equals("American")){
			Festival.getInstance().americanVoice();
		}
		Festival.getInstance().restart();
		confirmedNotification.setVisible(true);
	}
	
	/**
	 * Presents a file chooser to the user so that they can import their own wordlist files.
	 * The files must follow the format specified in the NZCER-spelling-list.txt file. That is,
	 * it has a level header for each level of the format %Level X.
	 * @param ae
	 */
	@FXML
	public void addWordListPressed(ActionEvent ae){
		FileChooser chooser = new FileChooser();
		Stage st = new Stage();
		st.setTitle("Select a spelling list to add to your library");
		File wordList = chooser.showOpenDialog(st);
		if(wordList != null){
			File wordListCopy = new File(System.getProperty("user.dir") + WORDLIST_EXTENSION + wordList.getName());
			try {
				BufferedReader rdr = new BufferedReader(new FileReader(wordList));
				BufferedWriter wr = new BufferedWriter(new PrintWriter(wordListCopy));
				String line;
				while((line = rdr.readLine())!=null){
					wr.append(line);
					wr.newLine();
				}
				wr.flush();
				wr.close();
				rdr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ObservableList<File> wordLists = FXCollections.observableArrayList();
			File wordListDir = new File(System.getProperty("user.dir") + WORDLIST_EXTENSION);
			for(File wordlist : wordListDir.listFiles()){
				wordLists.add(wordlist);
			}		
			wordListsBox.setItems(wordLists);
		}
	}
	
	public static void reset(){
		String wordList = "NZCER-spelling-lists.txt";
		String startLevel = "1";
		String voice = "American";

		try {
			File voxspellProp = new File(System.getProperty("user.dir") + PROPERTIES_EXTENSION);
			voxspellProp.delete();
			voxspellProp.createNewFile();
			PrintWriter wr = new PrintWriter(voxspellProp);
			wr.append("startlevel=" + startLevel + "\n");
			wr.append("wordlist=" + wordList + "\n");
			wr.append("colourblind=" + false + "\n");
			wr.append("voice=" + voice + "\n");
			wr.append("music_disabled=" + false + "\n");
			wr.flush();
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up values in combo boxes when scene is loaded.
	 */
	@FXML
	public void initialize(){
		//Set voice combo box items.
		ObservableList<String> voices = FXCollections.observableArrayList();
		voices.add("New Zealand");
		voices.add("American");
		//TODO add english voice.
		voicesBox.setItems(voices);
		voicesBox.setPromptText("Select a voice for reading words");
		//Set word lists combo box;
		ObservableList<File> wordLists = FXCollections.observableArrayList();
		File wordListDir = new File(System.getProperty("user.dir") + WORDLIST_EXTENSION);
		for(File wordlist : wordListDir.listFiles()){
			wordLists.add(wordlist);
		}
		wordListsBox.setCellFactory(new Callback<ListView<File>,ListCell<File>>(){

			@Override
			public ListCell<File> call(ListView<File> arg0) {
				return new FileCell();
			}

		});
		wordListsBox.setButtonCell(new FileCell());
		wordListsBox.setItems(wordLists);
		wordListsBox.setPromptText("Select a spelling list");

		//Reads properties file to set default selected values
		BufferedReader rdr = null;
		BufferedReader levelReader = null;
		try {
			rdr = new BufferedReader(new FileReader( new File(System.getProperty("user.dir")+ PROPERTIES_EXTENSION)));
			String line;
			while((line = rdr.readLine()) != null){
				String[] property = line.split("=");
				if(property[0].equals("voice")){
					voicesBox.getSelectionModel().select(property[1]);
				}else if(property[0].equals("wordlist")){
					wordListsBox.getSelectionModel().select(new File(System.getProperty("user.dir") + WORDLIST_EXTENSION + property[1]));
					String levelLine = null;
					ObservableList<String> levels = FXCollections.observableArrayList();
					levelReader = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + WORDLIST_EXTENSION + property[1])));
					while((levelLine = levelReader.readLine()) != null){
						if(levelLine.charAt(0) == '%'){
							String[] splitLev = levelLine.split("\\s+");
							if(Integer.parseInt(splitLev[1]) <= Config.getUser().getUnlockedLevel()){
								levels.add(splitLev[1]);
							}
						}
					}
					startDifficultyBox.setItems(levels);
				}else if(property[0].equals("startlevel")){
					startDifficultyBox.getSelectionModel().select(property[1]);
				}else if(property[0].equals("colourblind")){
					if(property[1].equals("true")){
						colourBlindSelector.fire();
						colourBlind = true;
					}else{
						colourBlind = false;						
					}
				}else if(property[0].equals("music_disabled")){
					if(property[1].equals("true")){
						musicSelector.fire();
						musicDisabled = true;
					}else{
						musicDisabled = false;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(rdr != null){
					rdr.close();
				}
				if(levelReader != null){
					levelReader.close();
				}
			} catch (IOException e) {
				System.err.println("Unable to close IO streams");
				e.printStackTrace();
			}
		}
	}
}
