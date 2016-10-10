package voxspell.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import voxspell.VoxSpell;

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
	public void handleFinishPressed(ActionEvent ae){
		noUsername.setVisible(false);
		noPassword.setVisible(false);
		passwordMismatch.setVisible(false);
		
		String confirmedPassword = confirmPasswordField.getText();
		String username = usernameField.getText();
		String password = passwordField.getText();
		try {
			BufferedReader rdr = new BufferedReader(new FileReader(userInfoFile));
			String line;
			try {
				while((line = rdr.readLine()) != null){
					if(username.equals(line.split(":")[0])){
						usernameTaken.setVisible(true);
						return;
					}
				}
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
		
		if(username.equals("")){
			noUsername.setVisible(true);
		}else if(password.equals("")){
			noPassword.setVisible(true);
		}else if(!password.equals(confirmedPassword)){
			passwordMismatch.setVisible(true);
		}else{
			PrintWriter wr = null;
			try {
				wr = new PrintWriter(new FileWriter(userInfoFile));
			} catch (IOException e) {
				System.err.println("Fatal Error: unable to write to user info file.");
				e.printStackTrace();
			}
			wr.append("\n" + username + ":" + password.hashCode());
			wr.flush();
			wr.close();
			changeScene("LoginScreen.fxml");
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
