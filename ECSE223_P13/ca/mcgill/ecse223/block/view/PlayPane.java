package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOConstant;
import ca.mcgill.ecse223.block.controller.TOCurrentBlock;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

	private static Button quit;
	private static Button logout;
	private static Rectangle paddle;
	private static Circle ball;
	private static boolean started;

	private static TOCurrentlyPlayedGame pgame;
	private static TOConstant constants;
	
	private static Button gameOver;

	private static MediaPlayer mediaPlayer;
	private static MediaView mediaView;
	private static ImageView imageView;
	private static String inputs = "";
	
	private Stage primaryStage;

	public PlayPane(Stage primaryStage) {
		this.primaryStage = primaryStage;
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
		
		//Sorry
		gameOver = new Button("Game Over!");
		gameOver.setFocusTraversable(false);
		gameOver.setOnAction(e->{
			Block223Page.setGameOverScene(primaryStage);
		});

		startGame = new Button("Start Game");
		startGame.setFocusTraversable(false);
		startGame.setOnAction(e -> {
			startGame.setDisable(true);
			quit.setDisable(true);
			logout.setDisable(true);
			started = true;
			mediaPlayer.stop();
			this.setCenter(playArea);
			// initiating a thread to start the game loop
			Runnable task = new Runnable() {
				public void run() {
					try {
						Block223Controller.startGame(PlayPane.this);
						startGame.setDisable(false);
						quit.setDisable(false);
						logout.setDisable(false);
						started = false;

					} catch (InvalidInputException e) {}
				}
			};
			Thread t2 = new Thread(task);
			t2.setDaemon(true);
			t2.start();
		});
		

		quit = new Button("Quit");
		quit.setFocusTraversable(false);
		quit.setOnAction(e -> {
			mediaPlayer.stop();
			Block223Page.changeToPlayableGameSelectionScene(primaryStage);
		});
		
		logout = new Button("Log out");
		logout.setFocusTraversable(false);
		logout.setOnAction(e -> {
			mediaPlayer.stop();
			Block223Controller.logout();
			primaryStage.setScene(Block223Page.getLoginScene());	
		});
		
		buttonsBox.getChildren().addAll(startGame,quit, logout, gameOver);		

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
		
		paddle = new Rectangle();
		paddle.setWidth(pgame.getCurrentPaddleLength());
		paddle.setHeight(constants.getPaddleWidth());
		Image beam = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/beam.png"));
		paddle.setFill(new ImagePattern(beam));
		paddle.setTranslateX(pgame.getCurrentPaddleX());
		paddle.setTranslateY(constants.getPlayAreaSide()-constants.getVerticalDistance()-constants.getPaddleWidth());

		ball = new Circle();
		ball.setRadius(constants.getBallDiameter()/2);
		Image meteor = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/meteor.png"));
		ball.setFill(new ImagePattern(meteor));
		ball.setTranslateX(pgame.getCurrentBallX());
		ball.setTranslateY(pgame.getCurrentBallY());
		
		imageView = new ImageView();
		Image earth = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/earth.png"));
		imageView.setFitWidth(constants.getPlayAreaSide()/0.9);
		imageView.setFitHeight(constants.getPlayAreaSide()/2.9);
		imageView.setImage(earth);
		imageView.setTranslateX(constants.getPlayAreaSide()/2 - imageView.getFitWidth()/2);
		imageView.setTranslateY(constants.getPlayAreaSide() - imageView.getFitHeight()/3.5);
		
		playArea.getChildren().addAll(paddle, ball, imageView);

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
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				playArea.getChildren().clear();
				playArea.getChildren().addAll(paddle, ball, imageView);
				
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
			}
		});
		
		ball.setTranslateX(pgame.getCurrentBallX());
		ball.setTranslateY(pgame.getCurrentBallY());
		paddle.setTranslateX(pgame.getCurrentPaddleX());
		PlayHeader.refreshHeader(pgame.getCurrentLevel(), pgame.getLives(), pgame.getScore());
	}

	public static HBox getButtonsBox() {
		return buttonsBox;
	}

	@Override
	public void gameOver() {
		Block223Page.setGameOverScene(primaryStage);
	}

}
