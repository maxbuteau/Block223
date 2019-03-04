package ca.mcgill.ecse223.block.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Ball;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.BlockAssignment;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Level;
import ca.mcgill.ecse223.block.model.Paddle;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

public class Block223Controller {

	// ****************************
	// Modifier methods
	// ****************************
	public static void createGame(String name) throws InvalidInputException {
		UserRole admin = Block223Application.getCurrentUserRole();
		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to create a game.");
		}

		Block223 block223 = Block223Application.getBlock223();

		if(name == null || name == null) {
			throw new InvalidInputException("The name of a game must be specified.");
		}
		if (Block223.findGame(name) != null) {
			throw new InvalidInputException("The name of a game must be unique.");
		}
		try {
			Game newGame = new Game(name, 1, (Admin) admin, 1, 1, 1, 10, 10, block223);
			newGame.addLevel();
		}
		catch (RuntimeException ex) {
			throw new InvalidInputException(ex.getMessage());
		}
	}


	public static void setGameDetails(int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {

		Game game = Block223Application.getCurrentGame();
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required update game settings.");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to update game settings.");
		}

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can edit its settings.");
		}

		if (nrLevels > 99 || nrLevels < 1)
			throw new InvalidInputException("The number of levels must be between 1 and 99.");

		Ball ball = game.getBall();
		Paddle paddle = game.getPaddle();

		try {
			game.setNrBlocksPerLevel(nrBlocksPerLevel);
			ball.setMinBallSpeedX(minBallSpeedX);
			ball.setMinBallSpeedY(minBallSpeedY);
			ball.setBallSpeedIncreaseFactor(ballSpeedIncreaseFactor);
			paddle.setMaxPaddleLength(maxPaddleLength);
			paddle.setMinPaddleLength(minPaddleLength);

			if (minPaddleLength > maxPaddleLength)
				throw new InvalidInputException("Min paddle length has to be smaller than the max.");

			List<Level> levels = game.getLevels();
			while (nrLevels < levels.size()) {
				levels.get(levels.size() - 1).delete();
			}
			while (nrLevels > levels.size()) {
				game.addLevel();
			}
		} catch (RuntimeException ex) {
			throw new InvalidInputException(ex.getMessage());
		}
	}

	public static void deleteGame(String name) throws InvalidInputException {
		UserRole user = Block223Application.getCurrentUserRole();
		Block223 block223 = Block223Application.getBlock223();
		if (!(user instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to delete a game.");
		}
		if (!(user.equals(Block223.findGame(name).getAdmin()))) {
			throw new InvalidInputException("Only the admin who created the game can delete the game.");
		}

		Game game = Block223.findGame(name);
		if (game != null) {
			game.delete();
			try {
				Block223Persistence.save(block223);}
			catch(RuntimeException ex) {
				throw new InvalidInputException(ex.getMessage());
			}
		}
	}

	public static void selectGame(String name) throws InvalidInputException {
		UserRole user = Block223Application.getCurrentUserRole();
		if (!(user instanceof Admin))
			throw new InvalidInputException("Admin privileges are required to select a game.");

		Game game = Block223.findGame(name);
		if (game == null)
			throw new InvalidInputException("Game with name " + name + " does not exist.");
		if (user != game.getAdmin())
			throw new InvalidInputException("Only the admin who created the game can select the game.");
		else Block223Application.setCurrentGame(game);
	}

	public static void updateGame(String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {

		Game game = Block223Application.getCurrentGame();
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to update a game.");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to update it.");
		}

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can update it.");
		}

		String currentName = game.getName();
		if(!name.equals(currentName))
		if(Block223.findGame(name) != null) {
			throw new InvalidInputException("The name of a game must be unique.");
		}
//		if (name.equals("")|| name == null) {
//			throw new InvalidInputException("The name of a game must be specified.");
//		}
		
		try{
			game.setName(name);
		}catch (RuntimeException ex) {
			throw new InvalidInputException(ex.getMessage());
		}

		int highestNrBlocks = HighestNrOfBlocksInLevel(game);

		if (nrBlocksPerLevel < highestNrBlocks) {
			nrBlocksPerLevel = highestNrBlocks;

			throw new InvalidInputException("The number of blocks cannot be set to a value smaller than the number of blocks already in a level ("+highestNrBlocks+")");
		}
		if(currentName != name) {
			try {
				setGameDetails(nrLevels, nrBlocksPerLevel, minBallSpeedX, minBallSpeedY, ballSpeedIncreaseFactor,
						maxPaddleLength, minPaddleLength);
			}catch(RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}
	}

	public static void addBlock(int red, int green, int blue, int points) throws InvalidInputException {

		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to add a block.");
		}

		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to add a block.");
		}

		Game game = Block223Application.getCurrentGame();

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can add a block.");
		}

		List<Block> gameBlockList = new ArrayList<Block>();

		gameBlockList = game.getBlocks();

		for (Block block : gameBlockList) {
			if (block.getRed() == red && block.getGreen() == green && block.getBlue() == blue) {
				throw new InvalidInputException("A block with the same color already exists for the game.");
			}
		}

		try {
			new Block(red, green, blue, points, game);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void deleteBlock(int id) throws InvalidInputException {

		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to delete a block.");
		}

		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to delete a block.");
		}

		Game game = Block223Application.getCurrentGame();

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can delete a block.");
		}

		Block block = game.findBlock(id);

		if (block != null) {
			block.delete();
		}
	}

	public static void updateBlock(int id, int red, int green, int blue, int points) throws InvalidInputException {

		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to update a block.");
		}

		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to update a block.");
		}

		if (Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can update the block.");
		}

		Game game = Block223Application.getCurrentGame();

		// gets all the blocks in the game
		List<Block> gameBlockList = new ArrayList<Block>();

		gameBlockList = game.getBlocks();

		// iterates through the blockList, throws exception if block with identical RGB
		// value is found
		for (Block block : gameBlockList) {
			if (block.getRed() == red && block.getGreen() == green && block.getBlue() == blue) {
				throw new InvalidInputException("A block with the same color already exists for the game.");
			}
		}

		Block block = game.findBlock(id);

		if (block == null) {
			throw new InvalidInputException("The block does not exist.");
		}

		try {
			block.setRed(red);
			block.setGreen(green);
			block.setBlue(blue);
			block.setPoints(points);

		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void positionBlock(int id, int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {

		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to position a block.");
		}

		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to position a block.");
		}

		if (Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can position a block.");
		}

		Game game = Block223Application.getCurrentGame();

		Level aLevel = null;
		try {
			aLevel = game.getLevel(level-1);
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level " + level + " does not exist for the game.");
		}

		if (aLevel.getBlockAssignments().size() >= game.getNrBlocksPerLevel()) {
			throw new InvalidInputException("The number of blocks has reached the maximum number ("
					+ game.getNrBlocksPerLevel() + ") allowed for this game.");
		}

		List<BlockAssignment> existingBlockAssignments = new ArrayList<BlockAssignment>();
		existingBlockAssignments = aLevel.getBlockAssignments();

		for (BlockAssignment blockAssignment : existingBlockAssignments) {
			if (blockAssignment.getGridHorizontalPosition() == gridHorizontalPosition
					&& blockAssignment.getGridVerticalPosition() == gridVerticalPosition) {
				throw new InvalidInputException("A block already exists at location " + gridHorizontalPosition + "/"
						+ gridVerticalPosition + ".");
			}
		}

		Block block = game.findBlock(id);

		if (block == null) {
			throw new InvalidInputException("The block does not exist.");
		}

		BlockAssignment blockAssignment;

		try {

			blockAssignment = new BlockAssignment(gridHorizontalPosition, gridVerticalPosition, aLevel, block, game);

		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

		game.addBlockAssignment(blockAssignment);

	}

	public static void moveBlock(int level, int oldGridHorizontalPosition, int oldGridVerticalPosition,
			int newGridHorizontalPosition, int newGridVerticalPosition) throws InvalidInputException {

		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to move a block.");
		}

		Game game = Block223Application.getCurrentGame();

		if (game == null) {
			throw new InvalidInputException("A game must be selected to move a block.");
		}

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can move a block.");
		}

		Level aLevel = null;
		try {
			aLevel = game.getLevel(level-1);
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level " + level + "does not exist for the game.");
		}

		BlockAssignment oldAssignment = aLevel.findBlockAssignment(oldGridHorizontalPosition, oldGridVerticalPosition);
		BlockAssignment newAssignment = aLevel.findBlockAssignment(newGridHorizontalPosition, newGridVerticalPosition);

		if (oldAssignment == null) {
			throw new InvalidInputException("A block does not exist at location " + oldGridHorizontalPosition + "/"
					+ oldGridVerticalPosition + ".");
		}
		if (newAssignment != null) {
			throw new InvalidInputException("A block already exists at location " + newGridHorizontalPosition + "/"
					+ newGridVerticalPosition + ".");
		}
		//		TOConstant constants = getConstants();
		//		if(newGridVerticalPosition > constants.getMaxVerticalBlocks()-1 || newGridHorizontalPosition > constants.getMaxHorizontalBlocks()-1)
		//			throw new InvalidInputException("The final position is not within the grid.");

		try {
			oldAssignment.setGridHorizontalPosition(newGridHorizontalPosition);
			oldAssignment.setGridVerticalPosition(newGridVerticalPosition);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void removeBlock(int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {

		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to remove a block");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to remove a block");
		}

		Game game = Block223Application.getCurrentGame();

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can remove a block");
		}

		Level aLevel = game.getLevel(level-1);

		BlockAssignment aBlockAssignment = aLevel.findBlockAssignment(gridHorizontalPosition, gridVerticalPosition);

		if (aBlockAssignment != null) {
			aBlockAssignment.delete();
		}
	}

	public static void saveGame() throws InvalidInputException {

		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to save a game.");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to save it.");
		}

		Game game = Block223Application.getCurrentGame();

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can save it.");
		}
		Block223 block223 = Block223Application.getBlock223();

		try{
			Block223Persistence.save(block223);
		}
		catch (RuntimeException ex) {
			throw new InvalidInputException(ex.getMessage());
		}
	}

	public static void register(String username, String playerPassword, String adminPassword)
			throws InvalidInputException {
		if (Block223Application.getCurrentUserRole() != null) {
			throw new InvalidInputException("Cannot register a new user while a user is logged in.");
		}
		if (playerPassword.equals(adminPassword)) {
			throw new InvalidInputException("The passwords have to be different.");
		}
		if (playerPassword.equals("")|| playerPassword== null) {
			throw new InvalidInputException("The player password needs to be specified.");
		}

		Block223 block223 = Block223Application.getBlock223();
		String error = "";

		User user;
		Player player = new Player(playerPassword, block223);
		try {
			user = new User(username, block223, player);
		} catch (RuntimeException e) {
			error = e.getMessage();
			if (error.equals("Cannot create due to duplicate username")) {
				error = "The username has already been taken.";
			}
			player.delete();
			throw new InvalidInputException(error);
		}

		if (adminPassword != null && !adminPassword.equals("")) {
			try {
				Admin admin = new Admin(adminPassword, block223);
				user.addRole(admin);
				Block223Persistence.save(block223);
			} catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}
	}

	public static void login(String username, String password) throws InvalidInputException {
		if (Block223Application.getCurrentUserRole() != null) {
			throw new InvalidInputException("Cannot login a user while a user is already logged in.");
		}

		Block223Application.resetBlock223();
		User user = User.getWithUsername(username);

		if (user == null) {
			throw new InvalidInputException("The username and password do not match.");
		}

		List<UserRole> roles = user.getRoles();

		boolean didMatch = false;
		for (UserRole role : roles) {
			String rolePassword = role.getPassword();

			if (rolePassword.equals(password)) {
				Block223Application.setCurrentUserRole(role);
				didMatch = true;
			}
		}

		if (!didMatch) {
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
		if (!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		ArrayList<TOGame> result = new ArrayList<TOGame>();
		List<Game> games = block223.getGames();

		for (Game game : games) {
			Admin gameAdmin = game.getAdmin();

			if (gameAdmin.equals(admin)) {
				TOGame toGame = new TOGame(game.getName(), game.getLevels().size(), game.getNrBlocksPerLevel(),
						game.getBall().getMinBallSpeedX(), game.getBall().getMinBallSpeedY(),
						game.getBall().getBallSpeedIncreaseFactor(), game.getPaddle().getMaxPaddleLength(),
						game.getPaddle().getMinPaddleLength());

				result.add(toGame);
			}
		}
		return result;
	}

	public static TOGame getCurrentDesignableGame() {
		Game game = Block223Application.getCurrentGame();
		TOGame toGame = new TOGame(game.getName(), game.getLevels().size(), game.getNrBlocksPerLevel(),
				game.getBall().getMinBallSpeedX(), game.getBall().getMinBallSpeedY(),
				game.getBall().getBallSpeedIncreaseFactor(), game.getPaddle().getMaxPaddleLength(),
				game.getPaddle().getMinPaddleLength());

		return toGame;
	}

	public static List<TOBlock> getBlocksOfCurrentDesignableGame() throws InvalidInputException {
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		if (Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}

		Game game = Block223Application.getCurrentGame();
		ArrayList<TOBlock> result = new ArrayList<TOBlock>();
		List<Block> blocks = game.getBlocks();

		for (Block block : blocks) {
			TOBlock toBlock = new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(),
					block.getPoints());

			result.add(toBlock);
		}
		return result;
	}

	public static TOBlock getBlockOfCurrentDesignableGame(int id) throws InvalidInputException {
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		if (Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}

		Game game = Block223Application.getCurrentGame();
		Block block = game.findBlock(id);
		if (block == null) {
			throw new InvalidInputException("The block does not exist.");
		}
		TOBlock toBlock = new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(),
				block.getPoints());

		return toBlock;
	}

	public static List<TOGridCell> getBlocksAtLevelOfCurrentDesignableGame(int level) throws InvalidInputException {
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		if (Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}

		Game game = Block223Application.getCurrentGame();
		ArrayList<TOGridCell> result = new ArrayList<TOGridCell>();

		Level aLevel;
		try {
			aLevel = game.getLevel(level-1);
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level " + level + " does not exist for the game.");
		}
		List<BlockAssignment> assignments = aLevel.getBlockAssignments();

		for (BlockAssignment assignment : assignments) {
			TOGridCell toGridCell = new TOGridCell(assignment.getGridHorizontalPosition(),
					assignment.getGridVerticalPosition(), assignment.getBlock().getId(), assignment.getBlock().getRed(),
					assignment.getBlock().getGreen(), assignment.getBlock().getBlue(),
					assignment.getBlock().getPoints());

			result.add(toGridCell);
		}
		return result;
	}

	public static TOUserMode getUserMode() {
		UserRole userRole = Block223Application.getCurrentUserRole();

		if (userRole == null) {
			TOUserMode toUserMode = new TOUserMode(Mode.None);
			return toUserMode;
		}
		if (userRole instanceof Player) {
			TOUserMode toUserMode = new TOUserMode(Mode.Play);
			return toUserMode;
		}
		if (userRole instanceof Admin) {
			TOUserMode toUserMode = new TOUserMode(Mode.Design);
			return toUserMode;
		}
		return null;
	}

	public static TOConstant getConstants() {
		return new TOConstant(Game.MIN_NR_LEVELS, Game.MAX_NR_LEVELS, Game.PLAY_AREA_SIDE, Game.WALL_PADDING,
				Game.COLUMNS_PADDING, Game.ROW_PADDING, Block.MIN_COLOR, Block.MAX_COLOR, Block.MIN_POINTS,
				Block.MAX_POINTS, Block.SIZE, Ball.BALL_DIAMETER, Paddle.PADDLE_WIDTH, Paddle.VERTICAL_DISTANCE,
				(1 + (Game.PLAY_AREA_SIDE - 2 * Game.WALL_PADDING - Block.SIZE) / (Block.SIZE + Game.COLUMNS_PADDING)),
				(1 + (Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Game.WALL_PADDING - Paddle.PADDLE_WIDTH
						- Ball.BALL_DIAMETER - Block.SIZE) / (Block.SIZE + Game.ROW_PADDING)));
	}

	// ****************************
	//Helper method to get the highest number of blocks in a level in a particular game
	// ****************************
	public static int HighestNrOfBlocksInLevel(Game agame) {
		List<Level> levels = agame.getLevels();
		ArrayList<Integer> nrBlocksInLevel = new ArrayList<Integer>();

		for (Level level : levels) {
			nrBlocksInLevel.add(level.getBlockAssignments().size());
		}
		int highestNrBlocks = Collections.max(nrBlocksInLevel);
		return highestNrBlocks;
	}
}
