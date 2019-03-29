package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOConstant;
import ca.mcgill.ecse223.block.controller.TOCurrentBlock;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PlayPane extends BorderPane implements Block223PlayModeInterface {
	private static Pane playArea;
	private static PlayHeader playHeader;
	private static HallOfFamePane hofPane;
	private static HBox buttonsBox;
	private static Button startGame;
	private static Button logOut;
	private static Button backGameSelection;
	private static Rectangle paddle;
	private static Circle ball;
	private static boolean started;

	private static TOCurrentlyPlayedGame pgame;
	private static TOConstant constants;

	private static MediaPlayer mediaPlayer;
	private static MediaView mediaView;
	private static String inputs = "";

	public PlayPane(Stage primaryStage) {
		try {
			pgame = Block223Controller.getCurrentPlayableGame();
			constants = Block223Controller.getConstants();
		}
		catch(InvalidInputException iie ) {
			//TODO
		}

		playArea = new Pane();
		playArea.setMaxSize(constants.getPlayAreaSide(), constants.getPlayAreaSide());
		playArea.setBorder(new Border(new BorderStroke(Color.WHITE, 
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		Image background = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/alien.jpg"));
		playArea.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(constants.getPlayAreaSide(), constants.getPlayAreaSide(), false, false, false, false))));

		playHeader = new PlayHeader();
		playHeader.setBorder(new Border(new BorderStroke(Color.WHITE, 
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		hofPane = new HallOfFamePane();

		buttonsBox = new HBox(20);
		buttonsBox.setAlignment(Pos.CENTER);

		startGame = new Button("Start Game");
		startGame.setOnAction(e -> {
			startGame.setDisable(true);
			started = true;
			mediaPlayer.stop();
			this.setCenter(playArea);
			// initiating a thread to start the game loop
			Runnable task = new Runnable() {
				@Override
				public void run() {
					try {
						Block223Controller.startGame(PlayPane.this);
						startGame.setDisable(false);
						started = false;
					} catch (InvalidInputException e) {}
				}
			};
			Thread t2 = new Thread(task);
			t2.setDaemon(true);
			t2.start();
		});

		logOut = new Button("Log Out");
		logOut.setOnAction(e -> {
			Block223Controller.logout();
			primaryStage.setScene(Block223Page.getLoginScene());
		});
		
		backGameSelection = new Button("Go back to game selection");
		backGameSelection.setOnAction(e -> {
			Block223Page.changeToPlayableGameSelectionScene(primaryStage);
		});
		
		buttonsBox.getChildren().addAll(startGame);

		mediaPlayer = new MediaPlayer(new Media(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/gameVideo.mp4")));
		mediaView = new MediaView(mediaPlayer);
		mediaView.setFitWidth(Block223Page.getScreenWidth()/2);
		mediaPlayer.play();
		
		this.setCenter(mediaView);
		this.setTop(playHeader);
		this.setBottom(buttonsBox);
		this.setRight(hofPane);
		this.setPadding(new Insets(0,0,40,0));

		displayPlayArea();

		this.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");
	}

	public static void setInputs(String str) {
		inputs = inputs+str;
	}

	public static boolean isStarted() {
		return started;
	}

	public static void displayPlayArea() {

		for(TOCurrentBlock toBlock : pgame.getBlocks()) {
			Rectangle block = new Rectangle();
			block.setWidth(constants.getSize());
			block.setHeight(constants.getSize());
			Color blockColor = new Color((double)toBlock.getRed()/255, (double)toBlock.getGreen()/255, (double)toBlock.getBlue()/255, 1);
			block.setFill(blockColor);
			block.setTranslateX(toBlock.getX());
			block.setTranslateY(toBlock.getY());
			playArea.getChildren().add(block);
		}

		paddle = new Rectangle();
		paddle.setWidth(pgame.getCurrentPaddleLength());
		paddle.setHeight(constants.getPaddleWidth());
		Image beam = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/beam.png"));
		paddle.setFill(new ImagePattern(beam));
		paddle.setTranslateX(pgame.getCurrentPaddleX());
		paddle.setTranslateY(constants.getPlayAreaSide()-constants.getVerticalDistance()-constants.getPaddleWidth());

		ball = new Circle();
		ball.setRadius(constants.getBallDiameter());
		Image meteor = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/meteor.png"));
		ball.setFill(new ImagePattern(meteor));
		ball.setTranslateX(pgame.getCurrentBallX());
		ball.setTranslateY(pgame.getCurrentBallY());
		
		ImageView imv = new ImageView();
		Image earth = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/earth.png"));
		imv.setFitWidth(constants.getPlayAreaSide()/0.9);
		imv.setFitHeight(constants.getPlayAreaSide()/2.9);
		imv.setImage(earth);
		imv.setTranslateX(constants.getPlayAreaSide()/2 - imv.getFitWidth()/2);
		imv.setTranslateY(constants.getPlayAreaSide() - imv.getFitHeight()/3.5);
		
		playArea.getChildren().addAll(paddle, ball, imv);
	}

	@Override
	public String takeInputs() {
		String temp = ""+inputs;
		inputs = "";
		return temp;
	}

	@Override
	public void refresh() {	
		try {
			pgame = Block223Controller.getCurrentPlayableGame();
		} catch(InvalidInputException iie ) {}

		ball.setTranslateX(pgame.getCurrentBallX());
		ball.setTranslateY(pgame.getCurrentBallY());
		paddle.setTranslateX(pgame.getCurrentPaddleX());
		PlayHeader.refreshHeader(pgame.getCurrentLevel(), pgame.getLives(), pgame.getScore());
	}

}
