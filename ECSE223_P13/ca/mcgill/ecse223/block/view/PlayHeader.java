package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	private static Label levelNumber;
	private Label livesLabel;
	private static Label livesNumber;
	private static Label scoreNumber;

	public PlayHeader() {

		try {
			pGame = Block223Controller.getCurrentPlayableGame();
			pLevel = pGame.getCurrentLevel();
			pLives = pGame.getLives();
			pScore = pGame.getScore();
		} catch (InvalidInputException e) {
			e.getMessage();
		}
		//Block 223 header
		ImageView imv = new ImageView();
		Image headerImage = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/HEADER.png"));
		imv.setImage(headerImage);
		HBox headerRegion = new HBox(20);
		imv.setFitHeight(Block223Page.getScreenHeight() / 4);
		imv.setFitWidth(Block223Page.getScreenWidth() / 2);
		imv.setPreserveRatio(true);
		headerRegion.getChildren().add(imv);

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
		scoreLabel = new Label("Score :");
		scoreNumber = new Label(""+pScore);

		scoreBox.getChildren().addAll(scoreLabel, scoreNumber);

		level_lives_score_container = new VBox(20);
		level_lives_score_container.getChildren().addAll(levelBox, livesBox, scoreBox);
		level_lives_score_container.setTranslateX(Block223Page.getScreenWidth() / 5);
		level_lives_score_container.setPadding(new Insets(20));

		this.getChildren().addAll(headerRegion, level_lives_score_container);
		this.setStyle("-fx-padding: 10;" + 
				"-fx-border-style: solid inside;" + 
				"-fx-border-width: 0;" +
				"-fx-border-insets: 5;" + 
				"-fx-border-radius: 5;");
		this.setAlignment(Pos.CENTER);

	}

	public static void refreshHeader(int level, int lives, int score){
		Platform.runLater(new Runnable() {

			public void run() {
				levelNumber.setText(""+level);
				livesNumber.setText(""+lives);
				scoreNumber.setText(""+score);				
			}
		});
	}

}
