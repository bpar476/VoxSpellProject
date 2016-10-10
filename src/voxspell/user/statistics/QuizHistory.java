package voxspell.user.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

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
	
	public void refresh(){
		for(QuizResults res : history){
			res.setQuizType(res.getTrueQuizType());
			res.setWordlist(res.getTrueWordlist());
		}
	}
	
	/**
	 * Returns an iterator to iterate over all the quiz results in this QuizHistory object.
	 * @return
	 */
	public Iterator<QuizResults> iterator(){
		return history.iterator();
	}
}
