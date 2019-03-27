package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayHeader extends HBox{

	private HBox levelBox;
	private HBox livesBox;
	private HBox scoreBox;
	private VBox level_lives_score_container;
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

		//VBox level
		levelBox = new HBox(20);
		levelLabel = new Label("Level : ");
		levelNumber = new Label(""+pLevel);

		levelBox.getChildren().addAll(levelLabel, levelNumber);

		//VBox Lives
		livesBox = new HBox(20);
		livesLabel = new Label("Lives : ");
		livesNumber = new Label(""+pLives);

		livesBox.getChildren().addAll(livesLabel, livesNumber);
		
		//VBox Score
		scoreBox = new HBox(20);
		scoreLabel = new Label("Score : ");
		scoreNumber = new Label(""+pScore);
		
		scoreBox.getChildren().addAll(scoreLabel, scoreNumber);
		
		level_lives_score_container = new VBox(20);
		level_lives_score_container.getChildren().addAll(levelBox, livesBox, scoreBox);

		this.getChildren().addAll(headerText, level_lives_score_container);
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
