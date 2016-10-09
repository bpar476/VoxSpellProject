package voxspell.user.statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import voxspell.quiz.QuizResults;
import voxspell.user.profile.User;

public class QuizHistory implements Serializable{
	
	private static final long serialVersionUID = -1651130866394505785L;
	private ArrayList<QuizResults> history;
	private final User usr;
	
	public QuizHistory(User usr){
		history = new ArrayList<>();
		this.usr = usr;
	}
	
	/**
	 * Adds a quiz result to the list. Makes sure list isn't larger than 20 items.
	 * @param result
	 */
	public void add(QuizResults result){
		if(history.size() > 50){
			history.remove(0);
		}
		history.add(result);
	}
}
