package voxspell.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import voxspell.Config;
import voxspell.VoxSpell;
import voxspell.user.profile.User;

/**
 * Controller class for login screen interface. The user is required to enter their username and password, or 
 * has the option of creating an account to login with in future. Has shortcut keys to login with just the enter key.
 * @author bpar
 *
 */
public class LoginController {

	private static final String DATA_LOCATION = System.getProperty("user.dir") + "/.Resources/data/";
	private static final File userInfo = new File(DATA_LOCATION + "user_info.dat");

	@FXML
	private Button login;
	@FXML
	private Button createProfile;
	@FXML
	private Label userNotRecognised;
	@FXML
	private Label wrongPassword;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;

	/**
	 * Handles logic for when user presses login button. Looks through user data file to find entered username.
	 * Then checks password. If password does not match then a label appears notifying the user that they entered the wrong
	 * password. If the entered username is not found in the file then a label appears telling the user that the username
	 * was not recognised.
	 * @param ae
	 */
	@FXML
	public void handleLoginPressed(){
		wrongPassword.setVisible(false);
		userNotRecognised.setVisible(false);
		BufferedReader rdr = null;
		try {
			rdr = new BufferedReader(new FileReader(userInfo));
		} catch (FileNotFoundException e) {
			//Create user data file
			try {
				//If the file is not found for some reason, create the file and try again.
				userInfo.createNewFile();
				handleLoginPressed();
			} catch (IOException e1) {
				System.err.println("Unable to read/create user information file. Fatal error.");
				e1.printStackTrace();
			}
		}
		
		//Reads throught the user info file and checks supplied credentials against database.
		String line;
		try {
			while((line = rdr.readLine())!=null){
				String[] userData = line.split(":");
				if(usernameField.getText().equals(userData[0])){
					if(passwordField.getText().hashCode() == Integer.parseInt(userData[1])){
						User usr = null;
						//Look for serialised user file.
						File userObjectFile = new File(DATA_LOCATION + usernameField.getText() + ".ser");
						if(userObjectFile.exists()){
							//If it exists, read it and set it as the user.
							FileInputStream fin = new FileInputStream(userObjectFile);
							ObjectInputStream ois = new ObjectInputStream(fin);
							try {
								usr = (User) ois.readObject();
								usr.getHistory().refresh();
								ois.close();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							//Otherwise create new user.
							usr = new User(userData[0]);
						}
						Config.setUser(usr);
						changeScene("MainMenu.fxml");
						return;
					}else{
						wrongPassword.setVisible(true);
						return;
					}
				}
			}
			userNotRecognised.setVisible(true);
			return;
		} catch (IOException e) {
			userInfo.delete();
			try {
				userInfo.createNewFile();
				handleLoginPressed();
			} catch (IOException e1) {
				System.err.println("Unable to read/create user information file. Fatal error.");
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Takes the user to a screen where they can create a new speller profile.
	 * @param ae
	 */
	@FXML
	public void createProfilePressed(ActionEvent ae){
		changeScene("CreateProfile.fxml");
	}
	
	/**
	 * Lets the user login when they press enter.
	 * @param ke
	 */
	@FXML
	public void handleEnterPressed(KeyEvent ke){
		if(ke.getCode() == KeyCode.ENTER){
			handleLoginPressed();
		}
	}

	public void initialize(){
		usernameField.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					if(newValue.matches("(.*)(\\s+)(.*)")){
						((StringProperty)observable).setValue(oldValue);
					}
				}
			);
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
