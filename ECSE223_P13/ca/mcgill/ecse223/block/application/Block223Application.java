package ca.mcgill.ecse223.block.application;

import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.Block223Page;

public class Block223Application {
	
	private static Block223 btms;
	
	public static void main(String[] args) {
		
	}

	public static Block223 getBtms() {
		if (btms == null) {
			// load model
			btms = Block223Persistence.load();
		}
 		return btms;
	}
	
}
