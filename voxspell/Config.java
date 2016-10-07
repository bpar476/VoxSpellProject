package voxspell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Config {
	
	private final static File cfg = new File(System.getProperty("user.dir") + "/.Resources/voxspell.prop");
	
	/**
	 * Reads the config file and finds the start level. Returns it to caller.
	 * @return
	 */
	public static int getStartLevel(){
		BufferedReader rdr = null;
		try {
			rdr = new BufferedReader(new FileReader(cfg));
		} catch (FileNotFoundException e) {
			System.err.println("Error: voxspell.prop file not found. Unable to configure voxspell");
			e.printStackTrace();
		}
		String line;
		try {
			while((line = rdr.readLine()) != null){
				String[] property = line.split("=");
				if(property[0].equals("startlevel")){
					return Integer.parseInt(property[1]);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO replace with custom exception?
		return -1;
	}
	
	/**
	 * Reads the config file to find the name of the wordlist file and returns the relative path.
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String getWordListLocation() throws FileNotFoundException{
		BufferedReader rdr = null;
		try {
			rdr = new BufferedReader(new FileReader(cfg));
		} catch (FileNotFoundException e) {
			System.err.println("Error: voxspell.prop file not found. Unable to configure voxspell");
			e.printStackTrace();
		}
		String line;
		try {
			while((line = rdr.readLine()) != null){
				String[] property = line.split("=");
				if(property[0].equals("wordlist")){
					return System.getProperty("user.dir") + "/.Resources/" + property[1];
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new FileNotFoundException("Error: Word list location not specified in config file");
	}
	
	/**
	 * Reads the config file and determines whether the spelling application is in colourblind mode.
	 * @return
	 */
	public static boolean isColourBlindMode(){
		BufferedReader rdr = null;
		try {
			rdr = new BufferedReader(new FileReader(cfg));
		} catch (FileNotFoundException e) {
			System.err.println("Error: voxspell.prop file not found. Unable to configure voxspell");
			e.printStackTrace();
		}
		String line;
		try {
			while((line = rdr.readLine()) != null){
				String[] property = line.split("=");
				if(property[0].equals("colourblind")){
					return(Boolean.parseBoolean(property[1]));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO replace with custom exception?
		return false;
	}
	
	/**
	 * Reads the config file and returns the selected voice.
	 * @return
	 */
	public static String getVoice(){
		BufferedReader rdr = null;
		try {
			rdr = new BufferedReader(new FileReader(cfg));
		} catch (FileNotFoundException e) {
			System.err.println("Error: voxspell.prop file not found. Unable to configure voxspell");
			e.printStackTrace();
		}
		String line;
		try {
			while((line = rdr.readLine()) != null){
				String[] property = line.split("=");
				if(property[0].equals("voice")){
					return property[1];
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO replace with custom exception?
		return "";
	}
}
