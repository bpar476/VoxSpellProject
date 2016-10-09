package voxspell.gui;

import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import voxspell.Config;
import voxspell.quiz.QuizResults.Result;
import voxspell.quiz.WordScore;

/**
 * Custom ListCell element used to represent Result items in the ListView used in the SummaryScreenController class.
 * @author bpar
 *
 */
public class WordCell extends ListCell<Result>{
	@Override
	protected void updateItem(Result item, boolean empty){
		if(item != null){
			super.updateItem(item, empty);
			if(item.getScore() == WordScore.FirstTry){
				setText(item.getWord() + ":Correct");
				if(Config.isColourBlindMode()){
					setTextFill(Color.BLUE);
				}else{
					setTextFill(Color.GREEN);
				}
			}else if(item.getScore() == WordScore.NotFirstTry){
				setText(item.getWord() + ":Correct (" + item.getAttempts() + ")");
				setTextFill(new Color(1.0, 0.4, 0, 1));
			}else{
				setText(item.getWord() + " :Wrong");
				setTextFill(Color.RED);
			}
		}
	}
}