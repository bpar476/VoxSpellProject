package voxspell.quiz;

import voxspell.festival.Festival;
import voxspell.festival.Festival.FestivalService;

/**
 * Class initially used to test the Spell screen gui.
 * @author bpar
 *
 */
public class DummyQuiz implements Quiz{
		
	public int compare(String answer){
		if(answer.equalsIgnoreCase("word")){
			return CORRECT_FIRST_TRY;
		}else{
			return WRONG_LAST_TRY;
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

	@Override
	public int size() {
		return 0;
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public QuizResults getResults() {
		return null;
	}

	@Override
	public String getType() {
		return "Dummy Quiz";
	}
}
