package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameFinishedPane extends VBox {
	
	private static final int V_GAP = 20;
	private static final int SCORE_GAP = 40;
	private static final int LEVEL_GAP = 40;
	private static final int LIVES_GAP = 40;
	private static final int BUTTON_GAP = 20;
	private static final int H_GAP = 150;
	
	//Text fields
	private Text gameOver;
	private Text scoreTitle;
	private Text score;
	private Text levelReachedTitle;
	private Text levelReached;
	private Text livesRemainingTitle;
	private Text livesRemaining;
	
	//Buttons
	private Button playAgain;
	private Button backToMenu;
	private Button logOut;
	
	//Boxes
	private VBox outerBox;
	private VBox innerBox;
	private HBox scoreBox;
	private HBox levelBox;
	private HBox livesBox;
	private HBox buttonBox;
	private HBox statsAndHofBox;
	
	//Stuff that updates
	private static HallOfFamePane hofPane;
	private TOCurrentlyPlayedGame pGame;
	private int pLevel;
	private int pLives;
	private int pScore;
	
	public GameFinishedPane(Stage primaryStage) {
		this.setAlignment(Pos.CENTER);
		
		try {
			pGame = Block223Controller.getCurrentPlayableGame();
			pLevel = pGame.getCurrentLevel();
			pLives = pGame.getLives();
			pScore = pGame.getScore();
		} catch (InvalidInputException e) {
			e.getMessage();
		}
		
		//Game Over message
		if (pGame.getLives() == 0) {
			gameOver = new Text("Game Over!");
			settingFont(gameOver, Color.RED, 64);
			Block223Page.setGameOverScene(primaryStage);
		}
		else {
			gameOver = new Text("GGWP!!!");
			settingFont(gameOver, Color.GOLD, 64);
		}
		
		//Score
		scoreTitle = new Text("Score:");
		settingFont(scoreTitle, Color.LIGHTGOLDENRODYELLOW, 32);
		score = new Text(""+pScore);
		settingFont(score, Color.WHITE, 32);
		
		scoreBox = new HBox(SCORE_GAP);
		scoreBox.getChildren().addAll(scoreTitle, score);
		scoreBox.setAlignment(Pos.CENTER);
		
		//Level Reached
		levelReachedTitle = new Text("Level Reached:");
		settingFont(levelReachedTitle, Color.LIGHTGOLDENRODYELLOW, 32);
		levelReached = new Text(""+pLevel);
		settingFont(levelReached, Color.WHITE, 32);
		
		levelBox = new HBox(LEVEL_GAP);
		levelBox.getChildren().addAll(levelReachedTitle, levelReached);
		levelBox.setAlignment(Pos.CENTER);
		
		//Lives Remaining
		livesRemainingTitle = new Text("Lives Remaining:");
		settingFont(livesRemainingTitle, Color.LIGHTGOLDENRODYELLOW, 32);
		livesRemaining = new Text(""+pLives);
		settingFont(livesRemaining, Color.WHITE, 32);
		
		livesBox = new HBox(LIVES_GAP);
		livesBox.getChildren().addAll(livesRemainingTitle, livesRemaining);
		livesBox.setAlignment(Pos.CENTER);
		
		innerBox = new VBox(V_GAP);
		innerBox.getChildren().addAll(scoreBox, levelBox, livesBox);
		innerBox.setAlignment(Pos.CENTER);
		//Hall Of Fame
		hofPane = new HallOfFamePane();
		
		
		//Buttons
		playAgain = new Button("Play Again");
		backToMenu = new Button("Games Menu");
		logOut = new Button("Log Out");
		this.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");
		
		buttonBox = new HBox(BUTTON_GAP);
		buttonBox.getChildren().addAll(playAgain, backToMenu, logOut);
		buttonBox.setAlignment(Pos.CENTER);
		
		//button functionality
		playAgain.setOnAction(e->{
			Block223Page.setPlayScene(primaryStage);
		});
		
		backToMenu.setOnAction(e->{
			Block223Page.changeToPlayableGameSelectionScene(primaryStage);
		});
		
		logOut.setOnAction(e->{
			Block223Page.logOutAfterGameOver(primaryStage);
		});
		
		statsAndHofBox = new HBox(H_GAP);
		statsAndHofBox.getChildren().addAll(innerBox, hofPane);
		statsAndHofBox.setAlignment(Pos.CENTER);
		
		//Master VBox
		outerBox = new VBox(V_GAP);
		outerBox.getChildren().addAll(gameOver, statsAndHofBox, buttonBox);
		outerBox.setAlignment(Pos.CENTER);
		this.getChildren().add(outerBox);
	}
	
	private static void settingFont(Text text, Color color, int size) {
		DropShadow ds = new DropShadow();
		ds. setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f,  0.4f,  0.4f));
		
		text.setEffect(ds);
		text.setCache(true);
		text.setX(10.0f);
		text.setY(270.0f);
		text.setFill(color);
		text.setFont(Font.font(null, FontWeight.BOLD, size));
	}
}
