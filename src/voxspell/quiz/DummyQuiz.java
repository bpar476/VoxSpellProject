package voxspell.quiz;

import voxspell.festival.Festival;
import voxspell.festival.Festival.FestivalService;

/**
 * Class initially used to test the Spell screen gui.
 * @author bpar
 *
 */
public class DummyQuiz{
	
	public static final int CORRECT_FIRST_TRY = 0;
	public static final int CORRECT_SECOND_TRY = 1;
	public static final int WRONG_FIRST_TRY = 2;
	public static final int WRONG_SECOND_TRY = 3;
		
	public int compare(String answer){
		if(answer.equalsIgnoreCase("word")){
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
