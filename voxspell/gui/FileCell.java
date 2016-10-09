package voxspell.gui;

import java.io.File;

import javafx.scene.control.ListCell;

/**
 * Custom ListCell used to represent files. Just uses the file's basename instead of the absolute path.
 * @author bpar
 *
 */
public class FileCell extends ListCell<File> {
	@Override
	protected void updateItem(File item, boolean empty){
		if(item != null){
			super.updateItem(item, empty);
			setText(item.getName());
		}
	}
}
