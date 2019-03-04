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
			Block223Controller.login("I'm a player", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.logout();
			Block223Controller.login("I'm a player", "123");
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
			Block223Controller.selectGame("test");
			Block223Controller.logout();

			Block223Controller.login("I'm another admin", "456");

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
			Block223Controller.login("I'm an admin", "123");
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
			Block223Controller.login("I'm an admin", "123");
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
			Block223Controller.selectGame("test");
			Block223Controller.logout();
			Block223Controller.login("I'm an admin", "123");

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
			Block223Controller.selectGame("test");
			Block223Controller.logout();
			Block223Controller.login("I'm an admin", "123");

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
			Block223Controller.selectGame("test");
			Block223Controller.logout();

			Block223Controller.register("I'm another admin", "123","456");
			Block223Controller.login("I'm another admin", "456");

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
			Block223Controller.login("I'm an admin", "123");
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
			Block223Controller.register("I'm another admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.logout();


			Block223Controller.login("I'm another admin", "456");


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
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.logout();
			Block223Controller.login("I'm an admin", "123");

			Block223Controller.updateBlock(0, 1, 1, 1, 1);
		});

		//no game selected
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.updateBlock(0, 1, 1, 1, 1);
		});

		//not correct admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.logout();

			Block223Controller.register("I'm another admin", "123","456");
			Block223Controller.login("I'm another admin", "456");
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
			Block223Controller.updateBlock(0, 1, 1, 0, 1);
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

	//POSITION BLOCK EXCEPTION CHECKING
	@Test
	void positionBlockTest() {
		//not admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.logout();
			Block223Controller.login("I'm an admin", "123");

			Block223Controller.positionBlock(0, 1, 1, 1);
		});

		//game not selected
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.positionBlock(0, 1, 1, 1);
		});

		//not correct admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.logout();

			Block223Controller.register("I'm another admin", "123","456");
			Block223Controller.login("I'm another admin", "456");
			Block223Controller.positionBlock(0, 1, 1, 1);
		});

		//Level does not exist
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.setGameDetails(10, 1, 1, 1, 1.0, 1, 1);
			Block223Controller.positionBlock(11, 1, 1, 1);
		});

		//max nb of blocks
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.addBlock(1, 2, 1, 1);
			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 1, 1);
			Block223Controller.positionBlock(1, 1, 1, 1);
		});

		//A block already exists at location
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.addBlock(1, 2, 1, 1);
			Block223Controller.setGameDetails(1, 2, 1, 1, 1.0, 1, 1);
			Block223Controller.positionBlock(0, 1, 1, 1);
			Block223Controller.positionBlock(1, 1, 1, 1);
		});

		//block does not exist
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 1, 1);
			Block223Controller.positionBlock(0, 1, 1, 1);
		});

		//position must be btw 1 and max vertical/maxhorizontal
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 1, 1);
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.positionBlock(0, 1, 0, 1);
		});

		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 1, 1);
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.positionBlock(0, 1, 1, 0);
		});
	}

	//MOVE BLOCK EXCEPTION CHECKING
	@Test
	void moveBlockTest() {
		//not admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.logout();
			Block223Controller.login("I'm an admin", "123");

			Block223Controller.moveBlock(1, 1, 1, 1, 1);
		});

		//game not selected
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.moveBlock(1, 1, 1, 1, 1);
		});

		//not correct admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.logout();

			Block223Controller.register("I'm another admin", "123","456");
			Block223Controller.login("I'm another admin", "456");
			Block223Controller.moveBlock(1, 1, 1, 1, 1);
		});

		//Level does not exist
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.setGameDetails(10, 1, 1, 1, 1.0, 1, 1);
			Block223Controller.moveBlock(11, 1, 1, 1, 1);
		});

		//A block does not exist at location
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 1, 1);
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.positionBlock(0, 5, 1, 1);
		});

		//A block already exists at location
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 2, 1, 1, 1.0, 1, 1);
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.addBlock(2, 1, 1, 1);
			Block223Controller.positionBlock(0, 1, 1, 1);
			Block223Controller.positionBlock(1, 1, 1, 1);
		});

		//position out of bounds
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 1, 1);
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.positionBlock(0, 1, 0, 1);
		});

		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 1, 1);
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.positionBlock(0, 1, 1, 0);
		});
	}

	//REMOVE BLOCK EXCEPTION CHECKING
	@Test
	void removeBlockTest() {
		//not admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.logout();
			Block223Controller.login("I'm an admin", "123");
			Block223Controller.removeBlock(1, 1, 1);
		});

		//game not selected
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.removeBlock(1, 1, 1);
		});

		//not correct admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.addBlock(1, 1, 1, 1);
			Block223Controller.logout();

			Block223Controller.register("I'm another admin", "123","456");
			Block223Controller.login("I'm another admin", "456");
			Block223Controller.removeBlock(1, 1, 1);
		});
	}

	//SAVE GAME EXCEPTION CHECKING		
	@Test
	void saveGameTest() {
		//not admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.logout();
			Block223Controller.login("I'm an admin", "123");
			Block223Controller.saveGame();
		});

		//game not selected
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.saveGame();
		});

		//not correct admin
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.createGame("test");
			Block223Controller.selectGame("test");
			Block223Controller.logout();

			Block223Controller.register("I'm another admin", "123","456");
			Block223Controller.login("I'm another admin", "456");
			Block223Controller.saveGame();
		});
	}

	//REGISTER EXCEPTION CHECKING
	@Test
	void registerTest() {
		//cannot register user while another logged in
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.register("I'am another user", "123","456");
		});
		
		//passwords have to be different
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","123");
		});
		
		//player password needs to be specified
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "","123");
		});
		
		//username has already been taken
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.logout();
			Block223Controller.register("I'm an admin", "789","101112");
		});
		
		//username must be specified
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("", "123","456");
		});
	}

	//LOGIN EXCEPTION CHECKING
	@Test
	void loginTest() {
		//Cannot login a user while a user is already logged in
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.register("I'am another user", "123","456");
			Block223Controller.login("I'm an admin", "456");
			Block223Controller.login("I'am another user", "123");
		});
		
		//username and password do not match (empty)
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "");
		});
		
		//username and password do not match
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm an admin", "123","456");
			Block223Controller.login("I'm an admin", "789");
		});
	}

	//LOGOUT EXCEPTION CHECKING
	//no exception is thrown for this feature



}
