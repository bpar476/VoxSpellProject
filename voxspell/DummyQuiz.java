package voxspell;

import voxspell.Festival.FestivalService;

public class DummyQuiz extends NewQuiz implements Quiz {
	
	public DummyQuiz(int startLevel){
		level = startLevel;
	}
	
	
	
	public int compare(String answer){
		if(answer.equals("correct")){
			return CORRECT_FIRST_TRY;
		}else{
			return WRONG_SECOND_TRY;
		}
	}
	
	public boolean isEnded(){
		return false;
	}
	
	public void speakWord(){
		FestivalService serv = Festival.getInstance();
		serv.announce("Word");
		serv.restart();
	}
}
