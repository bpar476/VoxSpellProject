package voxspell.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import voxspell.VoxSpell;

/**
 * Controller class for the create profile screen. This screen is entered when a user has not
 * created their own unique login. Requires a user to enter a username and password and confirm that password.
 * @author bpar
 *
 */
public class CreateProfileController {

	private static File userInfoFile = new File(System.getProperty("user.dir") + "/.Resources/data/user_info.dat");

	@FXML
	private Button cancel;
	@FXML
	private Button finish;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private TextField confirmPasswordField;
	@FXML
	private Label noUsername;
	@FXML
	private Label noPassword;
	@FXML
	private Label passwordMismatch;
	@FXML
	private Label usernameTaken;

	/**
	 * Handles action for when the user clicks the "finished" button to finish creating their profile.
	 * Checks that the username and password fields are not empty and then takes the user back to the login
	 * screen so that they can log in with their new profile.
	 * @param ae
	 */
	@FXML
	public void handleFinishPressed(){
		noUsername.setVisible(false);
		noPassword.setVisible(false);
		passwordMismatch.setVisible(false);
		usernameTaken.setVisible(false);

		String confirmedPassword = confirmPasswordField.getText();
		String username = usernameField.getText();
		String password = passwordField.getText();

		//Make sure something is actually entered in each field.
		if(username.equals("")){
			noUsername.setVisible(true);
		}else if(password.equals("")){
			noPassword.setVisible(true);
		}else if(!password.equals(confirmedPassword)){
			passwordMismatch.setVisible(true);
		}else{

			try {
				BufferedReader rdr = new BufferedReader(new FileReader(userInfoFile));
				String line;
				try {
					//Check to make sure that username is not already taken.
					while((line = rdr.readLine()) != null){
						if(username.equals(line.split(":")[0])){
							usernameTaken.setVisible(true);
							rdr.close();
							return;
						}
					}
					rdr.close();
				} catch (IOException e) {
					System.err.println("Fatal error: unable to access user info file");
				}
			} catch (FileNotFoundException e1) {
				try {
					userInfoFile.createNewFile();
				} catch (IOException e) {
					System.err.println("Fatal error: unable to create/read user data file");
					e.printStackTrace();
				}
			}

			//If all fields are filled correctly, write information to file holding user data.
			PrintWriter wr = null;
			try {
				wr = new PrintWriter(new FileWriter(userInfoFile, true));
				//Store password hashcode for security.

				String line;
				//Check to make sure that username is not already taken.
				wr.append(username + ":" + password.hashCode() + "\n");
				wr.flush();
				wr.close();
			} catch (IOException e) {
				System.err.println("Fatal Error: unable to write to user info file.");
				e.printStackTrace();
			}
			SettingsController.reset();
			changeScene("LoginScreen.fxml");
		}
	}
	
	@FXML
	public void handleEnterPressed(KeyEvent ke){
		if(ke.getCode() == KeyCode.ENTER){
			handleFinishPressed();
		}
	}

	/**
	 * Takes user back to login screen if they decide they don't need to create an account.
	 * @param ae
	 */
	@FXML
	public void handleCancelPressed(ActionEvent ae){
		changeScene("LoginScreen.fxml");
	}


	@FXML
	public void initialize(){
		//Code skeleton taken from
		//http://stackoverflow.com/questions/15615890/recommended-way-to-restrict-input-in-javafx-textfield
		passwordField.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					if(newValue.matches("(.*)(\\s+)(.*)")){
						((StringProperty)observable).setValue(oldValue);
					}
				}
			);
		usernameField.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					if(newValue.matches("(.*)(\\s+)(.*)")){
						((StringProperty)observable).setValue(oldValue);
					}
				}
			);
		confirmPasswordField.textProperty().addListener(
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
