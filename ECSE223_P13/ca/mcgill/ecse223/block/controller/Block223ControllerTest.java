package ca.mcgill.ecse223.block.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.model.UserRole;

class Block223ControllerTest {

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
	
	@Test
	void setGameDetailsNotAdminTest() {
		Assertions.assertThrows(InvalidInputException.class, () ->{
			Block223Controller.register("I'm a player", "123","456");
			Block223Controller.login("I'm a player", "123");
			Block223Controller.createGame("test");
			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 1, 1);
		});
	}
	
//	@Test
//	void setGameDetailsNoGameTest() {
//		Assertions.assertThrows(InvalidInputException.class, () ->{
//			Block223Controller.register("I'm an admin", "123","456");
//			Block223Controller.login("I'm an admin", "456");
//			Block223Controller.setGameDetails(1, 1, 1, 1, 1.0, 1, 1);
//		});
//	}
	
//	@Test
//	void deleteGameTest() {
//		fail("Not yet implemented");
//	}
//	@Test
//	void selectGameTest() {
//		fail("Not yet implemented");
//	}
//	@Test
//	void updateGameTest() {
//		fail("Not yet implemented");
//	}
//	@Test
//	void deleteBlockTest() {
//		fail("Not yet implemented");
//	}
//	@Test
//	void updateBlockTest() {
//		fail("Not yet implemented");
//	}
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
