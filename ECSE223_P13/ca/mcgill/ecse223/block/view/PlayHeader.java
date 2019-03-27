package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayHeader extends HBox{

	private VBox levelBox;
	private VBox livesBox;
	private Label headerText;
	private TOCurrentlyPlayedGame pGame;
	private int pLevel;
	private int pLives;
	private int pScore;
	private Label scoreLabel;
	private Label levelLabel;
	private Label levelNumber;
	private Label livesLabel;
	private Label livesNumber;
	private Label scoreNumber;

	public PlayHeader() {
		try {
			pGame = Block223Controller.getCurrentPlayableGame();
			pLevel = pGame.getCurrentLevel();
			pLives = pGame.getLives();
			pScore = pGame.getScore();
		} catch (InvalidInputException e) {
			e.getMessage();
		}
		//Block 223
		headerText = new Label("Block 223");

		//VBox level + score label
		levelBox = new VBox(20);
		levelLabel = new Label("Level : ");
		scoreLabel = new Label("Score : ");
		levelNumber = new Label(""+pLevel);

		levelBox.getChildren().addAll(levelLabel, levelNumber, scoreLabel);

		//VBox Lives + score value
		livesBox = new VBox(20);
		livesLabel = new Label("Lives : ");
		livesNumber = new Label(""+pLives);
		scoreNumber = new Label(""+pScore);

		livesBox.getChildren().addAll(livesLabel, livesNumber, scoreNumber);

		this.getChildren().addAll(headerText, levelBox, livesBox);
	}

	public void refreshHeader(){
		pLevel = pGame.getCurrentLevel();
		pLives = pGame.getLives();
		pScore = pGame.getScore();
		
		levelNumber.setText(""+pLevel);
		livesNumber.setText(""+pLives);
		scoreNumber.setText(""+pScore);

	}

}
