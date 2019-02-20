package ca.mcgill.ecse223.block.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.BlockAssignment;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Level;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

public class Block223Controller {

	// ****************************
	// Modifier methods
	// ****************************
	public static void createGame(String name) throws InvalidInputException {
	}

	public static void setGameDetails(int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
	}

	public static void deleteGame(String name) throws InvalidInputException {
	}

	public static void selectGame(String name) throws InvalidInputException {
	}

	public static void updateGame(String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
	}

	public static void addBlock(int red, int green, int blue, int points) throws InvalidInputException {
	}

	public static void deleteBlock(int id) throws InvalidInputException {
	}

	public static void updateBlock(int id, int red, int green, int blue, int points) throws InvalidInputException {
	}

	public static void positionBlock(int id, int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
	}

	public static void moveBlock(int level, int oldGridHorizontalPosition, int oldGridVerticalPosition,
			int newGridHorizontalPosition, int newGridVerticalPosition) throws InvalidInputException {
		
		if(!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to move a block.");
		}
		if(Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to move a block.");
		}
		
		Game game = Block223Application.getCurrentGame();
		
		if(Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can move a block.");
		}
		
		Level aLevel = null;
		try {
			aLevel = game.getLevel(level);
		}
		catch(IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level "+level+"does not exist for the game.");
		}
		
		BlockAssignment oldAssignment = aLevel.findBlockAssignment(oldGridHorizontalPosition, oldGridVerticalPosition);
		BlockAssignment newAssignment = aLevel.findBlockAssignment(newGridHorizontalPosition, newGridVerticalPosition);
		
		if(oldAssignment == null) {
			throw new InvalidInputException("A block does not exist at location "+oldGridHorizontalPosition+"/"+oldGridVerticalPosition+".");
		}
		if(newAssignment != null) {
			throw new InvalidInputException("A block already exists at location "+newGridHorizontalPosition+"/"+newGridVerticalPosition+".");
		}
		
		try {
			oldAssignment.setGridHorizontalPosition(newGridHorizontalPosition);
			oldAssignment.setGridVerticalPosition(newGridVerticalPosition);
		}
		catch(RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	public static void removeBlock(int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
	}

	public static void saveGame() throws InvalidInputException {
	}

	public static void register(String username, String playerPassword, String adminPassword)
			throws InvalidInputException {
		String error = "";
		
		if(Block223Application.getCurrentUserRole() != null) {
			error = "Cannot register a new user while a user is logged in.";
		}
		if(playerPassword.equals(adminPassword)) {
			error = "The passwords have to be different.";
		}
		if(error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		Block223 block223 = Block223Application.getBlock223();
		
		User user = null;
		try {
			Player player = new Player(playerPassword, block223);
			user = new User(username, block223, player);
		}
		catch(RuntimeException e) {
			error = e.getMessage();
			
			if(error.equals("Cannot create due to duplicate username")) {
				error = "The username has already been taken.";
			}
			throw new InvalidInputException(error);
		}
		
		if(adminPassword != null && !adminPassword.equals("")) {
			try {
				Admin admin = new Admin(adminPassword, block223);
				user.addRole(admin);
				Block223Persistence.save(block223);
			}
			catch(RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}	
	}

	public static void login(String username, String password) throws InvalidInputException {		
		if(Block223Application.getCurrentUserRole() != null) {
			throw new InvalidInputException("Cannot login a user while a user is already logged in.");
		}
		
		User user = User.getWithUsername(username);
		
		if(user == null) {
			throw new InvalidInputException("The username and password do not match.");
		}
		
		List<UserRole> roles = user.getRoles();
		
		boolean didMatch = false;
		for(UserRole role : roles) {
			String rolePassword = role.getPassword();
			
			if(rolePassword.equals(password)) {
				Block223Application.setCurrentUserRole(role);
				Block223Application.resetBlock223();
				didMatch = true;
			}
		}
		
		if(!didMatch) {
			throw new InvalidInputException("The username and password do not match.");
		}
	}

	public static void logout() {
		Block223Application.setCurrentUserRole(null);
	}
	
	// ****************************
	// Query methods
	// ****************************
	public static List<TOGame> getDesignableGames() throws InvalidInputException {
		Block223 block223 = Block223Application.getBlock223();
		
		UserRole admin = Block223Application.getCurrentUserRole();
		if(!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		ArrayList<TOGame> result = new ArrayList<TOGame>();
		List<Game> games = block223.getGames();
		
		for(Game game : games) {
			Admin gameAdmin = game.getAdmin();
			
			if(gameAdmin.equals(admin)) {
				TOGame toGame = new TOGame(game.getName(), game.getLevels().size(),
						game.getNrBlocksPerLevel(), game.getBall().getMinBallSpeedX(),
						game.getBall().getMinBallSpeedY(), game.getBall().getBallSpeedIncreaseFactor(),
						game.getPaddle().getMaxPaddleLength(), game.getPaddle().getMinPaddleLength());
				
				result.add(toGame);
			}
		}
		return result;
	}

	public static TOGame getCurrentDesignableGame() {
		Game game = Block223Application.getCurrentGame();
		
		TOGame toGame = new TOGame(game.getName(), game.getLevels().size(),
				game.getNrBlocksPerLevel(), game.getBall().getMinBallSpeedX(),
				game.getBall().getMinBallSpeedY(), game.getBall().getBallSpeedIncreaseFactor(),
				game.getPaddle().getMaxPaddleLength(), game.getPaddle().getMinPaddleLength());
	
		return toGame;
	}

	public static List<TOBlock> getBlocksOfCurrentDesignableGame() throws InvalidInputException {		
		if(!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		if(Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		if(Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}
		
		Game game = Block223Application.getCurrentGame();
		ArrayList<TOBlock> result = new ArrayList<TOBlock>();
		List<Block> blocks = game.getBlocks();
		
		for(Block block : blocks) {
			TOBlock toBlock = new TOBlock(block.getId(), block.getRed(),
					block.getGreen(), block.getBlue(), block.getPoints());
			
			result.add(toBlock);
		}
		return result;
	}

	public static TOBlock getBlockOfCurrentDesignableGame(int id) throws InvalidInputException {
		if(!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		if(Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		if(Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}
		
		Game game = Block223Application.getCurrentGame();
		Block block = game.findBlock(id);
		if(block == null) {
			throw new InvalidInputException("The block does not exist.");
		}
		TOBlock toBlock = new TOBlock(block.getId(), block.getRed(),
				block.getGreen(), block.getBlue(), block.getPoints());
		
		return toBlock;
	}

	public List<TOGridCell> getBlocksAtLevelOfCurrentDesignableGame(int level) throws InvalidInputException {
		if(!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		if(Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		if(Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}
		
		Game game = Block223Application.getCurrentGame();
		ArrayList<TOGridCell> result = new ArrayList<TOGridCell>();
		
		Level aLevel;
		try {
			aLevel = game.getLevel(level);
		}
		catch(IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level "+level+"does not exist for the game.");
		}
		List<BlockAssignment> assignments = aLevel.getBlockAssignments();
		
		for(BlockAssignment assignment : assignments) {
			TOGridCell toGridCell = new TOGridCell(assignment.getGridHorizontalPosition(),
					assignment.getGridVerticalPosition(), assignment.getBlock().getId(),
					assignment.getBlock().getRed(), assignment.getBlock().getGreen(),
					assignment.getBlock().getBlue(), assignment.getBlock().getPoints());
			
			result.add(toGridCell);
		}
		return result;
	}

	public static TOUserMode getUserMode() {
		UserRole userRole = Block223Application.getCurrentUserRole();
		
		if(userRole == null) {
			TOUserMode toUserMode = new TOUserMode(Mode.None);
			return toUserMode;
		}
		if(userRole instanceof Player) {
			TOUserMode toUserMode = new TOUserMode(Mode.Play);
			return toUserMode;
		}
		if(userRole instanceof Admin) {
			TOUserMode toUserMode = new TOUserMode(Mode.Design);
			return toUserMode;
		}
		return null;
	}

}
