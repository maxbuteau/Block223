package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	
	//Text fields
	private Text gameOver;
	private Text ggWp;
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
	private HBox scoreBox;
	private HBox levelBox;
	private HBox livesBox;
	private HBox buttonBox;
	
	public GameFinishedPane(Stage primaryStage) {
		this.setAlignment(Pos.CENTER);
		
		//Game Over message
		gameOver = new Text("Game Over!");
		settingFont(gameOver, Color.RED, 64);
		
		//Score
		scoreTitle = new Text("Score:");
		settingFont(scoreTitle, Color.BLACK, 32);
		score = new Text("1000");
		settingFont(score, Color.WHITE, 32);
		
		scoreBox = new HBox(SCORE_GAP);
		scoreBox.getChildren().addAll(scoreTitle, score);
		scoreBox.setAlignment(Pos.CENTER);
		
		//Level Reached
		levelReachedTitle = new Text("Level Reached:");
		settingFont(levelReachedTitle, Color.BLACK, 32);
		levelReached = new Text("45");
		settingFont(levelReached, Color.WHITE, 32);
		
		levelBox = new HBox(LEVEL_GAP);
		levelBox.getChildren().addAll(levelReachedTitle, levelReached);
		levelBox.setAlignment(Pos.CENTER);
		
		//Lives Remaining
		livesRemainingTitle = new Text("Lives Remaining:");
		settingFont(livesRemainingTitle, Color.BLACK, 32);
		livesRemaining = new Text("2");
		settingFont(livesRemaining, Color.WHITE, 32);
		
		livesBox = new HBox(LIVES_GAP);
		livesBox.getChildren().addAll(livesRemainingTitle, livesRemaining);
		livesBox.setAlignment(Pos.CENTER);
		
		//Buttons
		playAgain = new Button("Play Again");
		backToMenu = new Button("Games Menu");
		logOut = new Button("Log Out");
		this.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");
		
		buttonBox = new HBox(BUTTON_GAP);
		buttonBox.getChildren().addAll(playAgain, backToMenu, logOut);
		buttonBox.setAlignment(Pos.CENTER);
		
		//Log Out button functionality
		logOut.setOnAction(e->{
			Block223Page.logOutAfterGameOver(primaryStage);
		});
		
		//Master VBox
		outerBox = new VBox(V_GAP);
		outerBox.getChildren().addAll(gameOver, scoreBox, levelBox, livesBox, buttonBox);
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
