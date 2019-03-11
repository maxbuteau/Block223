package ca.mcgill.ecse223.block.application;

import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.Block223Page;
import javafx.application.Application;

public class Block223Application {
	
	private static Block223 block223 = null;
	private static UserRole currentUserRole = null;
	private static Game currentGame = null;
	
	public static void main(String[] args) {
		new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(Block223Page.class);
            }
        }.start();
		
	}

	public static Block223 getBlock223() {
		if (block223 == null) {
			// load model
			block223 = Block223Persistence.load();
		}
 		return block223;
	}
	
	public static Block223 resetBlock223() {
		if(block223 != null) {
			block223.delete();
		}
		setCurrentGame(null);
		
		block223 = Block223Persistence.load();
		return block223;
	}

	public static UserRole getCurrentUserRole() {
		return currentUserRole;
	}

	public static void setCurrentUserRole(UserRole currentUserRole) {
		Block223Application.currentUserRole = currentUserRole;
	}

	public static Game getCurrentGame() {
		return currentGame;
	}

	public static void setCurrentGame(Game aGame) {
		currentGame = aGame;
	}
}
