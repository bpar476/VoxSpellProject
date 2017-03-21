package voxspell.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.WindowEvent;

/**
 * This class is the controller for RewardVideoPlayer. It manages the button presses and
 * video play as well. Takebn from my Assignment 3
 * 
 * @author Ben Partridge
 */
public class RewardPlayerController {

	//FXML fields for use with RewardPlayer.fxml
	@FXML
	private Button stop;
	@FXML
	private Button playPause;
	@FXML
	private MediaView bunnyViewer;

	private Media bunnyVid;
	private MediaPlayer bunnyPlayer;
	private boolean playing;
	private boolean closeBehaviourSet;

	/**
	 * Takes a button event when clicking on a button in the player and processes the correct action.
	 * @param ae The ActionEvent from the button press.
	 */
	@FXML
	public void handleButtonPressed(ActionEvent ae){
		Button butt;
		if( (butt = ((Button)ae.getSource())).equals(playPause)){
			if(!playing){
				bunnyPlayer.play();
				if(!closeBehaviourSet){
					bunnyViewer.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {

						@Override
						public void handle(WindowEvent event) {
							bunnyPlayer.stop();
						}
					});
					closeBehaviourSet = true;
				}
				stop.setDisable(false);
				playing = true;
				playPause.setText("Pause");
			}else{
				bunnyPlayer.pause();
				playPause.setText("Play");
				playing = false;
			}
		}else if( (butt = ((Button)ae.getSource())).equals(stop)){
			bunnyPlayer.stop();
			playPause.setText("Play");
			playing = false;
			stop.setDisable(true);
		}
	}

	/**
	 * Used to stop the video.
	 */
	public void stop(){
		bunnyPlayer.stop();
	}
	
	/**
	 * Sets the media for the media player.
	 */
	@FXML
	public void initialize(){
		String dir = System.getProperty("user.dir");
		bunnyVid = new Media("file:///" + dir +"/.Resources/media/big_buck_bunny_1_minute.mp4");
		bunnyPlayer = new MediaPlayer(bunnyVid);
		bunnyViewer.setMediaPlayer(bunnyPlayer);
		closeBehaviourSet = false;
		playing = false;
	}

}

