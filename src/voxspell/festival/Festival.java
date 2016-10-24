package voxspell.festival;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import voxspell.Config;

/**
 * Singleton class encapsulating a Festival process. Taken from my assignment 3.
 * 
 * @author Ben Partridge
 *
 */
public class Festival {

	private static FestivalService serv;
	
	public static FestivalService getInstance(){
		if(serv == null){
			serv = new FestivalService();
		}
		return serv;
	}
	
	public static void clear(){
		serv.kill();
		serv = new FestivalService();
		if(Config.getVoice().equals("New Zealand")){
			serv.kiwiVoice();
		}
	}
	
	/**
	 * Nested class to handle concurrency of festival. Constructor is protected so that Festival class
	 * encapsulates the festival process. A festival process constantly runs in the background and gets
	 * Scheme code piped to it through a PrintWriter. The class extends Service so that it can be started
	 * and restarted to repeat similar tasks. Scheme code is put into a queue and when the Service starts the
	 * task all commands are executed in a FIFO manner.
	 * @author bpar
	 *
	 */
	public static class FestivalService extends Service<Void>{

		private static BufferedReader festOut;
		private static PrintWriter festIn;
		private static BufferedReader festErr;
		private static Queue<String> command;
		private static Process proc;

		//Starts a festival process that can be written to at any stage.
		protected FestivalService(){
			command = new LinkedList<>();
			ProcessBuilder festival = new ProcessBuilder("/bin/bash", "-c", "festival");
			try {
				proc = festival.start();
				festOut = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				festErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
				festIn = new PrintWriter(new OutputStreamWriter(proc.getOutputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Private helper method to make code easier to understand.
		protected void setCommand(String comm){
			command.add(comm);
		}
		
		protected void kill(){
			proc.destroy();
		}
		
		/**
		 * Schedules a word to be announced by festival next time the Service is started.
		 * @param word
		 */
		public void announce(String word){
			setCommand("(SayText " + "\"" + word + "\")");
		}
		
		/**
		 * Schedules the voice to be changed to the auckland voice next time the Service is started.
		 */
		public void kiwiVoice(){
			setCommand("(voice_akl_nz_jdt_diphone)");
		}

		/**
		 * Schedules the voice to be changed to the american voice next time the Service is started.
		 */
		public void americanVoice(){
			setCommand("(voice_kal_diphone)");
		}
		
		/**
		 * Schedules the speech speed to be slowed down for speaking a spelling word in a quiz.
		 */
		public void spellSpeed(){
			setCommand("(Parameter.set `Duration_Stretch 2.0)");
		}
		
		/**
		 * Schedules the speech speed to be set back to normal for ordinary speech.
		 */
		public void speakSpeed(){
			setCommand("(Parameter.set `Duration_Stretch 1.0)");
		}
		
		/**
		 * Kills the festival process. Needs to be called before terminating VoxSpell or festival will continue
		 * to run in the background.
		 */
		public void close(){
			proc.destroy();
		}
		
		/**
		 * Writes every command in the queue to the standard input of the festival instance sequeuntially.
		 */
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>(){

				@Override
				protected Void call() throws Exception {
					while(!command.isEmpty()){
						festIn.write(command.remove());
						festIn.flush();
					}
					return null;
				}

			};
		}

	}
}