package voxspell.quiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WordList {

	private File wordListFile;
	private ArrayList<String> wordList;
	int level;
	boolean levelFound;

	public WordList(String location, int chosenLevel){
		wordList = new ArrayList<>();
		levelFound = false;
		level = chosenLevel;
		wordListFile = new File(location);

		buildWordList();
	}

	public ArrayList<String> getWordList(){
		return wordList;
	}

	public void nextLevel(){
		level++;
		buildWordList();
	}

	private void buildWordList(){
		BufferedReader rdr = null;
		try {
			rdr = new BufferedReader(new FileReader(wordListFile));
		} catch (FileNotFoundException e) {
			System.err.println("Error: file " + wordListFile.getAbsolutePath() + " not found");
			e.printStackTrace();
		}

		String line;
		try {
			while((line = rdr.readLine()) != null){
				if(levelFound){
					if(line.charAt(0) == '%'){
						break;
					}else{
						wordList.add(line.trim());						
					}
				}else{
					if(line.charAt(0) == '%'){
						if((Integer.parseInt(line.split("\\s+")[1]) == level)){
							levelFound = true;
						}
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading file: " + wordListFile.getAbsolutePath());
			e.printStackTrace();
		}
		try {
			rdr.close();
		} catch (IOException e) {
			System.err.println("Error closing IOStream for file: " + wordListFile.getAbsolutePath());
			e.printStackTrace();
		}
	}
}
