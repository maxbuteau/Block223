package ca.mcgill.ecse223.block.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.model.UserRole;

class Block223ControllerTest {

	//CREATE GAME EXCEPTION CHECKING
	@Test
	void createGamePlayerTest(){
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm a player", "123","456");
			Block223Controller.login("I'm a player", "123");
			Block223Controller.createGame("test");
		});
	}
	@Test
	void noNameTest(){
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("");
		});
	}
	@Test
	void notUniqueGameTest(){
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("I am used");
			Block223Controller.createGame("I am used");

		});
	}

	//SET GAME DETAILS EXCEPTION CHECKING
	@Test
	void setGameDetailsNotAdminTest() {
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm a player", "123","456");
			Block223Controller.login("I'm a player", "123");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 1, 1);
		});
	}

	@Test
	void setGameDetailsNoGameTest() {
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 1, 1);
		});
	}
	@Test
	void setGameDetailsNotCorrectAdminTest() {
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.register("I'm another admin", "123","456");

			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.logout();

			Block223Controller.login("I'm another admin", "456");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 1, 1);
		});
	}

	@Test
	void setGameDetailsLevelTest() {
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(0, 1, 1, 1, 1.0, 1, 1);
		});

		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(100, 1, 1, 1, 1.0, 1, 1);
		});
	}

	@Test
	void setGameDetailsBlockTest() {
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 0, 1, 1, 1.0, 1, 1);
		});
	}

	@Test
	void setGameDetailsBallTest() {
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 0, 1, 1.0, 1, 1);
		});

		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 0, 1.0, 1, 1);
		});
	}

	@Test
	void setGameDetailsIncreaseFactorTest() {
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 1, 0.0, 1, 1);
		});
	}

	@Test
	void setGameDetailsPaddleTest() {
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 0, 1);
		});

		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 0, 1.0, 400, 1);
		});

		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 1, 0);
		});

		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 0, 1.0, 1, 400);
		});
	}

	//DELETE A GAME EXCEPTION CHECKING

	@Test
	void deleteGameNotAdminTest() {
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.logout();

			Block223Controller.register("I'm a player", "123","456");
			Block223Controller.login("I'm a player", "123");
			Block223Controller.deleteGame("test");
		});
	}

	@Test
	void deleteGameNotCorrectAdminTest() {
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.logout();

			Block223Controller.register("I'm another admin", "123","456");
			Block223Controller.login("I'm another admin", "456");
			Block223Controller.deleteGame("test");
		});
	}

	//SELECT GAME EXCEPTION CHECKING
	@Test
	void selectGameTest() {
		//not an admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.logout();

			Block223Controller.register("I'm a player", "123","456");
			Block223Controller.login("I'm a player", "123");
			Block223Controller.selectGame("test");
		});

		//not correct admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.logout();

			Block223Controller.register("I'm another admin", "123","456");
			Block223Controller.login("I'm another admin", "456");
			Block223Controller.selectGame("test");
		});

		//game does not exist
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.selectGame("test");
		});

	}

	//UPDATE GAME EXCEPTION CHECKING
	@Test
	void updateGameTest() {
		//not an admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.logout();

			Block223Controller.register("I'm a player", "123","456");
			Block223Controller.login("I'm a player", "123");
			Block223Controller.selectGame("test");
			Block223Controller.updateGame("test", 1, 1, 1, 1, 1.0, 1, 1);
		});
		//no game selected
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.updateGame("test", 1, 1, 1, 1, 1.0, 1, 1);
		});
		//name of the game must be unique
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.createGame("test2");
			Block223Controller.selectGame("test");
			Block223Controller.updateGame("test2", 1, 1, 1, 1, 1.0, 1, 1);
		});
		//name of the game must be specified
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.updateGame("", 1, 1, 1, 1, 1.0, 1, 1);
		});
	}

	//ADD BLOCK EXCEPTION CHECKING
	@Test
	void addBlockTest() {
		//not admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.logout();

			Block223Controller.register("I'm a player", "123","456");
			Block223Controller.login("I'm a player", "123");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
		});

		//no game selected
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
		});

		//not correct admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.logout();

			Block223Controller.register("I'm another admin", "123","456");
			Block223Controller.login("I'm another admin", "456");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
		});

		//red out of range
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(-1, 1, 1, 1);
		});
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(256, 1, 1, 1);
		});

		//green out of range
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, -1, 1, 1);
		});
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 256, 1, 1);
		});
		//blue out of range
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, -1, 1);
		});
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 256, 1);
		});

		//points out of range
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 0);
		});
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1001);
		});

	}

	//DELETE BLOCK EXCEPTION CHECKING
	@Test
	void deleteBlockTest() {
		//not admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.logout();

			Block223Controller.register("I'm a player", "123","456");
			Block223Controller.login("I'm a player", "123");
			Block223Controller.selectGame("test");
			Block223Controller.deleteBlock(0);
		});

		//no game selected
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.deleteBlock(0);
		});

		//not correct admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.logout();

			Block223Controller.register("I'm another admin", "123","456");
			Block223Controller.login("I'm another admin", "456");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.deleteBlock(0);
		});


	}

		@Test
		void updateBlockTest() {
			//not admin
			Assertions.assertThrows(InvalidInputException.class, () ->{
				Block223Controller.register("I'm an admin", "123","456");
				Block223Controller.login("I'm an admin", "456");
				Block223Controller.createGame("test");
				Block223Controller.logout();

				Block223Controller.register("I'm a player", "123","456");
				Block223Controller.login("I'm a player", "123");
				Block223Controller.selectGame("test");
				Block223Controller.updateBlock(0, 1, 1, 1, 1);
			});

			//no game selected
			Assertions.assertThrows(InvalidInputException.class, () ->{
				Block223Controller.register("I'm an admin", "123","456");
				Block223Controller.login("I'm an admin", "456");
				Block223Controller.createGame("test");
				Block223Controller.updateBlock(0, 1, 1, 1, 1);
			});

			//not correct admin
			Assertions.assertThrows(InvalidInputException.class, () ->{
				Block223Controller.register("I'm an admin", "123","456");
				Block223Controller.login("I'm an admin", "456");
				Block223Controller.createGame("test");
				Block223Controller.logout();

				Block223Controller.register("I'm another admin", "123","456");
				Block223Controller.login("I'm another admin", "456");
				Block223Controller.selectGame("test");
				Block223Controller.updateBlock(0, 1, 1, 1, 1);
			});
			
			//red out of range
			Assertions.assertThrows(InvalidInputException.class, () ->{
				Block223Controller.register("I'm an admin", "123","456");
				Block223Controller.login("I'm an admin", "456");
				Block223Controller.createGame("test");
				Block223Controller.selectGame("test");
				Block223Controller.addBlock(1, 1, 1, 1);
				Block223Controller.updateBlock(0, 0, 1, 1, 1);
			});
			Assertions.assertThrows(InvalidInputException.class, () ->{
				Block223Controller.register("I'm an admin", "123","456");
				Block223Controller.login("I'm an admin", "456");
				Block223Controller.createGame("test");
				Block223Controller.selectGame("test");
				Block223Controller.addBlock(1, 1, 1, 1);
				Block223Controller.updateBlock(0, 256, 1, 1, 1);
			});

			//green out of range
			Assertions.assertThrows(InvalidInputException.class, () ->{
				Block223Controller.register("I'm an admin", "123","456");
				Block223Controller.login("I'm an admin", "456");
				Block223Controller.createGame("test");
				Block223Controller.selectGame("test");
				Block223Controller.addBlock(1, 1, 1, 1);
				Block223Controller.updateBlock(0, 1, 0, 1, 1);
			});
			Assertions.assertThrows(InvalidInputException.class, () ->{
				Block223Controller.register("I'm an admin", "123","456");
				Block223Controller.login("I'm an admin", "456");
				Block223Controller.createGame("test");
				Block223Controller.selectGame("test");
				Block223Controller.addBlock(1, 1, 1, 1);
				Block223Controller.updateBlock(0, 1, 256, 1, 1);
			});
			//blue out of range
			Assertions.assertThrows(InvalidInputException.class, () ->{
				Block223Controller.register("I'm an admin", "123","456");
				Block223Controller.login("I'm an admin", "456");
				Block223Controller.createGame("test");
				Block223Controller.selectGame("test");
				Block223Controller.addBlock(1, 1, 1, 1);
				Block223Controller.updateBlock(1, 1, 0, 1, 1);
			});
			Assertions.assertThrows(InvalidInputException.class, () ->{
				Block223Controller.register("I'm an admin", "123","456");
				Block223Controller.login("I'm an admin", "456");
				Block223Controller.createGame("test");
				Block223Controller.selectGame("test");
				Block223Controller.addBlock(1, 1, 1, 1);
				Block223Controller.updateBlock(0, 1, 1, 256, 1);
			});

			//points out of range
			Assertions.assertThrows(InvalidInputException.class, () ->{
				Block223Controller.register("I'm an admin", "123","456");
				Block223Controller.login("I'm an admin", "456");
				Block223Controller.createGame("test");
				Block223Controller.selectGame("test");
				Block223Controller.addBlock(1, 1, 1, 1);
				Block223Controller.updateBlock(0, 1, 1, 1, 0);
			});
			Assertions.assertThrows(InvalidInputException.class, () ->{
				Block223Controller.register("I'm an admin", "123","456");
				Block223Controller.login("I'm an admin", "456");
				Block223Controller.createGame("test");
				Block223Controller.selectGame("test");
				Block223Controller.addBlock(1, 1, 1, 1);
				Block223Controller.updateBlock(0, 1, 1, 1, 1001);
			});

			
		}
	
	
	//	@Test
	//	void positionBlockTest() {
	//		fail("Not yet implemented");
	//	}
	//	@Test
	//	void moveBlockTest() {
	//		fail("Not yet implemented");
	//	}
	//	@Test
	//	void removeBlockTest() {
	//		fail("Not yet implemented");
	//	}
	//	@Test
	//	void saveGameTest() {
	//		fail("Not yet implemented");
	//	}
	//	@Test
	//	void registerTest() {
	//		fail("Not yet implemented");
	//	}
	//	@Test
	//	void loginTest() {
	//		fail("Not yet implemented");
	//	}
	//	@Test
	//	void logoutTest() {
	//		fail("Not yet implemented");
	//	}
	//	
	//	

}
