package voxspell.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import voxspell.Config;
import voxspell.VoxSpell;
import voxspell.user.profile.User;

public class LoginController {

	private static File userInfo = new File(System.getProperty("user.dir") + "/.Resources/data/user_info.dat");

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
	private TextField passwordField;

	/**
	 * Handles logic for when user presses login button. Looks through user data file to find entered username.
	 * Then checks password. If password does not match then a label appears notifying the user that they entered the wrong
	 * password. If the entered username is not found in the file then a label appears telling the user that the username
	 * was not recognised.
	 * @param ae
	 */
	@FXML
	public void handleLoginPressed(ActionEvent ae){
		//TODO make login by enter possible
		//TODO make password hidden
		wrongPassword.setVisible(false);
		userNotRecognised.setVisible(false);
		BufferedReader rdr = null;
		try {
			rdr = new BufferedReader(new FileReader(userInfo));
		} catch (FileNotFoundException e) {
			//Create user data file
			try {
				userInfo.createNewFile();
				handleLoginPressed(ae);
			} catch (IOException e1) {
				System.err.println("Unable to read/create user information file. Fatal error.");
				e1.printStackTrace();
			}
		}

		String line;
		try {
			rdr.readLine();
			while((line = rdr.readLine())!=null){
				String[] userData = line.split(":");
				if(usernameField.getText().equals(userData[0])){
					if(passwordField.getText().hashCode() == Integer.parseInt(userData[1])){
						//TODO Make user state storable.
						Config.setUser(new User(userData[0],"Bob"));
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
				handleLoginPressed(ae);
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
