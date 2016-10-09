package voxspell.user.profile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import voxspell.user.statistics.QuizHistory;

public class User implements Serializable{
	
	private static final String FILE_LOCATION = System.getProperty("user.dir") + "/.Resources/data/";
	private static final long serialVersionUID = -4799555998018565833L;
	private QuizHistory history;
	private String username;
	
	public User(String username){
		history = new QuizHistory(this);
		this.username = username;
	}
	
	@Override
	public String toString(){
		return this.username;
	}
	
	public QuizHistory getHistory(){
		return this.history;
	}
	
	public void write(){
		File writeLocation = new File(FILE_LOCATION + toString() + ".ser");
		if(!writeLocation.exists()){
			try {
				writeLocation.createNewFile();
			} catch (IOException e) {
				System.err.println("Fatal error: Unable to create user result file");
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(writeLocation);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
			oos.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
