package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;

public interface Block223PlayModeInterface {
	
	public String takeInputs();
	
	public void refresh();
	
	public void endGame(TOCurrentlyPlayedGame toPgame);
}
