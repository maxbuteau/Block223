package ca.mcgill.ecse223.block.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Ball;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.BlockAssignment;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.HallOfFameEntry;
import ca.mcgill.ecse223.block.model.Level;
import ca.mcgill.ecse223.block.model.Paddle;
import ca.mcgill.ecse223.block.model.PlayedBlockAssignment;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.model.PlayedGame.PlayStatus;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;

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

		if (name == null || name == null) {
			throw new InvalidInputException("The name of a game must be specified.");
		}
		if (block223.findGame(name) != null) {
			throw new InvalidInputException("The name of a game must be unique.");
		}
		try {
			Game newGame = new Game(name, 1, (Admin) admin, 1, 1, 1, 10, 10, block223);
			newGame.addLevel();
		} catch (RuntimeException ex) {
			throw new InvalidInputException(ex.getMessage());
		}
	}

	public static void setGameDetails(int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {

		Game game = Block223Application.getCurrentGame();
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to define game settings.");
		}
		if (minBallSpeedX <= 0 && minBallSpeedY <= 0) {
			throw new InvalidInputException("The minimum speed of the ball must be greater than zero.");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to define game settings.");
		}

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can define its game settings.");
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
		
		
		Game game = block223.findGame(name);
		
		if (game != null) {
			if (!(user.equals(block223.findGame(name).getAdmin()))) {
				throw new InvalidInputException("Only the admin who created the game can delete the game.");
			}
			
			if(game.isPublished()) {
				throw new InvalidInputException("A published game cannot be deleted.");
			}

			game.delete();
			try {
				Block223Persistence.save(block223);
			} catch (RuntimeException ex) {
				throw new InvalidInputException(ex.getMessage());
			}
		}
	}

	public static void selectGame(String name) throws InvalidInputException {
		UserRole user = Block223Application.getCurrentUserRole();
		if (!(user instanceof Admin))
			throw new InvalidInputException("Admin privileges are required to select a game.");

		Game game = Block223Application.getBlock223().findGame(name);
		
		if (game == null)
			throw new InvalidInputException("A game with name " + name + " does not exist.");
		if (user != game.getAdmin())
			throw new InvalidInputException("Only the admin who created the game can select the game.");
		if (game.isPublished())
			throw new InvalidInputException("A published game cannot be changed.");
		else
			Block223Application.setCurrentGame(game);
	}

	public static void updateGame(String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {

		Game game = Block223Application.getCurrentGame();
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to define game settings.");
		}
		if (game == null) {
			throw new InvalidInputException("A game must be selected to define game settings.");
		}

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can define its game settings.");
		}

		String currentName = game.getName();
		 if(!name.equals(currentName))
		if (Block223Application.getBlock223().findGame(name) != null) {
			throw new InvalidInputException("The name of a game must be unique.");
		}
//		if (name.equals("")|| name == null) {
//			throw new InvalidInputException("The name of a game must be specified.");
//		}

		try {
			game.setName(name);
		} catch (RuntimeException ex) {
			throw new InvalidInputException(ex.getMessage());
		}

		int highestNrBlocks = HighestNrOfBlocksInLevel(game);

		if (nrBlocksPerLevel < highestNrBlocks) {
			nrBlocksPerLevel = highestNrBlocks;

			throw new InvalidInputException(
					"The maximum number of blocks per level cannot be less than the number of existing blocks in a level.");
		}
		if (currentName != name) {
			try {
				setGameDetails(nrLevels, nrBlocksPerLevel, minBallSpeedX, minBallSpeedY, ballSpeedIncreaseFactor,
						maxPaddleLength, minPaddleLength);
			} catch (RuntimeException e) {
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
			throw new InvalidInputException("Only the admin who created the game can update a block.");
		}

		Game game = Block223Application.getCurrentGame();

		// gets all the blocks in the game
		List<Block> gameBlockList = new ArrayList<Block>();

		gameBlockList = game.getBlocks();

		// iterates through the blockList, throws exception if block with identical RGB
		// value is found
		for (Block block : gameBlockList) {
			if (block.getRed() == red && block.getGreen() == green && block.getBlue() == blue && block.getId() != id) {
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
			aLevel = game.getLevel(level - 1);
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
			aLevel = game.getLevel(level - 1);
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level " + level + " does not exist for the game.");
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
		// TOConstant constants = getConstants();
		// if(newGridVerticalPosition > constants.getMaxVerticalBlocks()-1 ||
		// newGridHorizontalPosition > constants.getMaxHorizontalBlocks()-1)
		// throw new InvalidInputException("The final position is not within the
		// grid.");

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
			throw new InvalidInputException("Admin privileges are required to remove a block.");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to remove a block.");
		}

		Game game = Block223Application.getCurrentGame();

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can remove a block.");
		}

		Level aLevel = game.getLevel(level - 1);

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

		try {
			Block223Persistence.save(block223);
		} catch (RuntimeException ex) {
			throw new InvalidInputException(ex.getMessage());
		}
	}

	public static void register(String username, String playerPassword, String adminPassword)
			throws InvalidInputException {
		if (Block223Application.getCurrentUserRole() != null) {
			throw new InvalidInputException("Cannot register a new user while a user is logged in.");
		}
		if (playerPassword != null && playerPassword.equals(adminPassword)) {
			throw new InvalidInputException("The passwords have to be different.");
		}

		Block223 block223 = Block223Application.getBlock223();
		String error = "";

		User user;
		Player player;

		try {
			player = new Player(playerPassword, block223);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

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

			if (gameAdmin.equals(admin) && !game.isPublished()) {
				TOGame toGame = new TOGame(game.getName(), game.getLevels().size(), game.getNrBlocksPerLevel(),
						game.getBall().getMinBallSpeedX(), game.getBall().getMinBallSpeedY(),
						game.getBall().getBallSpeedIncreaseFactor(), game.getPaddle().getMaxPaddleLength(),
						game.getPaddle().getMinPaddleLength());

				result.add(toGame);
			}
		}
		return result;
	}

	public static TOGame getCurrentDesignableGame() throws InvalidInputException {
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		Game game = Block223Application.getCurrentGame();

		if (game == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}
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
			aLevel = game.getLevel(level - 1);
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
	// Helper method to get the highest number of blocks in a level in a particular
	// game
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

	// play mode

	public static void selectPlayableGame(String name, int id) throws InvalidInputException {
		if(!(Block223Application.getCurrentUserRole() instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
	
		Block223 block223 = Block223Application.getBlock223();
		
		Game game = block223.findGame(name);
		
		PlayedGame pgame;

		if(game != null) {
			UserRole player = Block223Application.getCurrentUserRole();
			String username = block223.findUsername(player);
			
			pgame = new PlayedGame(username, game, block223);
			pgame.setPlayer((Player) player);
		}
		else {
			pgame = block223.findPlayableGame(id);
			
			if(pgame == null) {
				throw new InvalidInputException("The game does not exist.");
			}
			
			if(pgame.getPlayer() != Block223Application.getCurrentUserRole()) {
				throw new InvalidInputException("Only the player that started a game can continue the game.");
			}
		}
		
		Block223Application.setCurrentPlayableGame(pgame);
	}

	public static void startGame(Block223PlayModeInterface ui) throws InvalidInputException {
		if(Block223Application.getCurrentUserRole() == null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		
		PlayedGame game = Block223Application.getCurrentPlayableGame();
		
		if(game == null) {
			throw new InvalidInputException("A game must be selected to play it.");
		}
		
		if(Block223Application.getCurrentUserRole() instanceof Admin && game.getPlayer() == null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		
		if(Block223Application.getCurrentUserRole() instanceof Admin && game.getGame().getAdmin() != Block223Application.getCurrentUserRole()) {
			throw new InvalidInputException("Only the admin of a game can test the game");
		}
		
		if(Block223Application.getCurrentUserRole() instanceof Player && game.getPlayer() == null) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}
		
		game.play();
		ui.takeInputs();
		
		while(game.getPlayStatus() == PlayStatus.Moving) {
			String userInputs = ui.takeInputs();
			
			//Update Paddle Position
			for(int i = 0; i < userInputs.length(); ++i) {
				if(userInputs.charAt(i) == 'l') 
					game.setCurrentPaddleX(game.getCurrentPaddleX() + PlayedGame.PADDLE_MOVE_LEFT);
				
				else if(userInputs.charAt(i) == 'r')
					game.setCurrentPaddleX(game.getCurrentPaddleX() + PlayedGame.PADDLE_MOVE_RIGHT);
				
				else if(userInputs.charAt(i) == ' ')
					break;
			}
			
			game.move();
			
			if(userInputs.contains("")) {
				game.pause();
			}
			
			try {
				TimeUnit.MILLISECONDS.sleep((long) game.getWaitTime());
			} catch (InterruptedException e) {}
			
			ui.refresh();
		}
		
		if(game.getPlayStatus() == PlayStatus.GameOver) {
			Block223Application.setCurrentPlayableGame(null);
		}
		
		else if(game.getPlayer() != null) {
			Block223 block223 = Block223Application.getBlock223();
			Block223Persistence.save(block223);
		}
	}

	public static void testGame() throws InvalidInputException {
	}

	public static void publishGame() throws InvalidInputException {
		
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)){
			throw new InvalidInputException("Admin privileges are required to publish a game");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to publish it");
		}
		Game game = Block223Application.getCurrentGame();

		if (Block223Application.getCurrentUserRole() != game.getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can publish it.");
		}
		if (game.hasBlocks() == false) {
			throw new InvalidInputException("At least one block must be defined for a game to be published");
		}
		game.setPublished(true);
		
	}
	// play mode queries

	public static List<TOPlayableGame> getPlayableGames() throws InvalidInputException {
	}

	public static TOCurrentlyPlayedGame getCurrentPlayableGame() throws InvalidInputException {
		if(Block223Application.getCurrentUserRole() == null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		
		if(pgame == null) {
			throw new InvalidInputException("A game must be selected to play it.");
		}
		
		if(Block223Application.getCurrentUserRole() instanceof Admin && pgame.getPlayer() != null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		
		if(Block223Application.getCurrentUserRole() instanceof Admin && pgame.getGame().getAdmin() != Block223Application.getCurrentUserRole()) {
			throw new InvalidInputException("Only the admin of a game can test the game");
		}
		
		if(Block223Application.getCurrentUserRole() instanceof Player && pgame.getPlayer() == null) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}
			
		boolean paused = pgame.getPlayStatus() == PlayStatus.Ready || pgame.getPlayStatus() == PlayStatus.Paused;
		
		TOCurrentlyPlayedGame result = new TOCurrentlyPlayedGame(
				pgame.getGame().getName(),
				paused,
				pgame.getScore(),
				pgame.getLives(),
				pgame.getCurrentLevel(),
				pgame.getPlayername(),
				pgame.getCurrentBallX(),
				pgame.getCurrentBallY(),
				pgame.getCurrentPaddleLength(),
				pgame.getCurrentPaddleX()
				);
		
		List<PlayedBlockAssignment> pblocks = pgame.getBlocks();
		
		for(PlayedBlockAssignment pblock : pblocks) {
			TOCurrentBlock to = new TOCurrentBlock(
					pblock.getBlock().getRed(),
					pblock.getBlock().getGreen(),
					pblock.getBlock().getBlue(),
					pblock.getBlock().getPoints(),
					pblock.getX(),
					pblock.getY(),
					result
					);
		}
		
		return result;
	}

	public static TOHallOfFame getHallOfFame(int start, int end) throws InvalidInputException {
		if(!(Block223Application.getCurrentUserRole() instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to access a game�s hall of fame.");
		}
		if(Block223Application.getCurrentPlayableGame() == null) {
			throw new InvalidInputException("A game must be selected to view its hall of fame.");
		}
		
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		Game game = pgame.getGame();
		TOHallOfFame result = new TOHallOfFame(game.getName());
		if(start < 1) start = 1;
		if(end > game.numberOfHallOfFameEntries()) end = game.numberOfHallOfFameEntries();
		start = start - 1;
		end = end -1;
		
		for(int i = start; i <= end; i++) {
			String username = Block223Application.getBlock223().findUsername(game.getHallOfFameEntry(i).getPlayer());
			TOHallOfFameEntry to = new TOHallOfFameEntry( i+1, username, game.getHallOfFameEntry(i).getScore(), result);
		}
		return result;
	}

	public static TOHallOfFame getHallOfFameWithMostRecentEntry(int numberOfEntries) throws InvalidInputException {
		if(!(Block223Application.getCurrentUserRole() instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to access a game�s hall of fame.");
		}
		if(Block223Application.getCurrentPlayableGame() == null) {
			throw new InvalidInputException("A game must be selected to view its hall of fame.");
		}
		
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		Game game = pgame.getGame();
		TOHallOfFame result = new TOHallOfFame(game.getName());
		HallOfFameEntry mostRecent = game.getMostRecentEntry();
		int index = game.indexOfHallOfFameEntry(mostRecent);
		
		//question????
		int start = index - numberOfEntries/2;
		if(start < 1) start = 1;
		int end = start + numberOfEntries - 1;
		if(end > game.numberOfHallOfFameEntries()) end = game.numberOfHallOfFameEntries();
		
		for (int i = start; i < end; i++) {
			String username = pgame.getPlayername();
			TOHallOfFameEntry to = new TOHallOfFameEntry(i + 1, username, game.getHallOfFameEntry(index).getScore(), result);
		}
		return result;
	}
}