package ca.mcgill.ecse223.block.application;

import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.Block223Page;

public class Block223Application {
	
	private static Block223 block223;
	private static UserRole loggedUser;
	
	public static void main(String[] args) {
		
	}

	public static Block223 getBlock223() {
		if (block223 == null) {
			// load model
			block223 = Block223Persistence.load();
		}
 		return block223;
	}

	public static UserRole getLoggedUser() {
		return loggedUser;
	}

	public static void setLoggedUser(UserRole loggedUser) {
		Block223Application.loggedUser = loggedUser;
	}
	
	
	
}
