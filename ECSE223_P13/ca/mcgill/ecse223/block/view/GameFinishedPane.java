package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameFinishedPane extends VBox {
	
	//Gaps
	private static final int V_GAP = 20;
	private static final int SCORE_GAP = 40;
	private static final int LIVES_GAP = 40;
	private static final int BUTTON_GAP = 20;
	private static final int H_GAP = 150;
	
	//Text fields
	private Text scoreTitle;
	private Text score;
	private Text livesRemainingTitle;
	private Text livesRemaining;
	
	//Buttons
	private Button backToMenu;
	private Button logOut;
	
	//Boxes
	private VBox outerBox;
	private VBox innerBox;
	private HBox scoreBox;
	private HBox livesBox;
	private HBox buttonBox;
	private HBox statsAndHofBox;
	
	//Stuff that updates
	private static HallOfFamePane hofPane;
	private int pLives;
	private int pScore;
	
	private ImageView alien;
	
	public GameFinishedPane(Stage primaryStage, int nrOfLives, TOHallOfFameEntry hof) {
		this.setAlignment(Pos.CENTER);
			pLives = nrOfLives;
			pScore = hof.getScore();

			
			HBox gameOverBox = new HBox(20);

		//Game Over message
		if (pLives == 0) {
//			gameOver = new Text("Game Over!");
//			settingFont(gameOver, Color.RED, 64);
			ImageView imv = new ImageView();
			Image hofImage = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/gameOver.png"));
			imv.setImage(hofImage);
			imv.setFitWidth(Block223Page.getScreenWidth() / 2);
			imv.setPreserveRatio(true);
			alien = new ImageView(new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/alienloss.gif")));
			gameOverBox.getChildren().add(imv);
		}
		else {
//			gameOver = new Text("Congratulations!");
//			settingFont(gameOver, Color.GOLD, 64);
			ImageView imv = new ImageView();
			Image hofImage = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/congrats.png"));
			imv.setImage(hofImage);
			imv.setFitWidth(Block223Page.getScreenWidth() / 2);
			imv.setPreserveRatio(true);
			alien = new ImageView(new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/alienwin.gif")));
			gameOverBox.getChildren().add(imv);
		}
		
		gameOverBox.setAlignment(Pos.CENTER);
		gameOverBox.setPadding(new Insets(5,0,0,0));
		
		//Score
		HBox scoreTitleBox = new HBox(20);
		ImageView imvScore = new ImageView();
		Image scoreImage = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/score3.png"));
		imvScore.setImage(scoreImage);
		imvScore.setFitHeight(Block223Page.getScreenHeight() / 8);
		imvScore.setPreserveRatio(true);
		scoreTitleBox.getChildren().add(imvScore);
		score = new Text(""+pScore);
		settingFont(score, Color.WHITE, 32);
		
		scoreBox = new HBox(SCORE_GAP);
		scoreBox.getChildren().addAll(scoreTitleBox, score,alien);
		scoreBox.setAlignment(Pos.CENTER);
		
		//Lives Remaining
		HBox livesTitleBox = new HBox(20);
		ImageView imvLives = new ImageView();
		Image livesImage = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/livesRemaining.png"));
		imvLives.setImage(livesImage);
		imvLives.setFitHeight(Block223Page.getScreenHeight() / 8);
		imvLives.setPreserveRatio(true);
		livesTitleBox.getChildren().add(imvLives);
		livesRemaining = new Text(""+pLives);
		settingFont(livesRemaining, Color.WHITE, 32);
		
		livesBox = new HBox(LIVES_GAP);
		livesBox.getChildren().addAll(livesTitleBox, livesRemaining);
		livesBox.setAlignment(Pos.CENTER);
		
		innerBox = new VBox(V_GAP);
		innerBox.getChildren().addAll(scoreBox, livesBox);
		innerBox.setAlignment(Pos.CENTER);
		
		//Hall Of Fame
		hofPane = new HallOfFamePane(hof.getTOHallOfFame().getGamename());
		
		//Buttons
		backToMenu = new Button("Games Menu");
		logOut = new Button("Log Out");
		this.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");
		
		buttonBox = new HBox(BUTTON_GAP);
		buttonBox.getChildren().addAll(backToMenu, logOut);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPadding(new Insets(0, 0, 40, 0));
		
		//button functionality
		
		backToMenu.setOnAction(e->{
			Block223Page.buttonPressSound();
			Block223Page.changeToPlayableGameSelectionScene(primaryStage);
		});
		
		logOut.setOnAction(e->{
			Block223Page.buttonPressSound();
			Block223Page.logOutAfterGameOver(primaryStage);
		});
		
		statsAndHofBox = new HBox(H_GAP);
		statsAndHofBox.getChildren().addAll(innerBox, hofPane);
		statsAndHofBox.setAlignment(Pos.CENTER);
		
		//Master VBox
		outerBox = new VBox(V_GAP);
		outerBox.getChildren().addAll(gameOverBox, statsAndHofBox, buttonBox);
		outerBox.setAlignment(Pos.CENTER);
		this.getChildren().add(outerBox);
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				Platform.runLater(()->{
				scoreBox.getChildren().remove(alien);});
			}
		}, 6000);
	
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
