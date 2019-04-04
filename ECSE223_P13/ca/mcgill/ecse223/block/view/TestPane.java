package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOConstant;
import ca.mcgill.ecse223.block.controller.TOCurrentBlock;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TestPane extends BorderPane implements Block223PlayModeInterface {
	private static Pane testArea;
	private static TOConstant constants;
	private static Button finishTest;
	private static Button resumeTest;
	private static boolean testStarted;
	private static HBox buttonBox;
	private static Rectangle paddle;
	private static Circle ball;
	private static TOCurrentlyPlayedGame pgame;
	private static String inputs;
	
	public TestPane(Stage primaryStage) {
		constants = Block223Controller.getConstants();
		this.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");
		
		testArea = new Pane();
		testArea.setMaxSize(constants.getPlayAreaSide(), constants.getPlayAreaSide());
		testArea.setBorder(new Border(new BorderStroke(Color.WHITE, 
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		this.setCenter(testArea);
		
		finishTest = new Button("Finished Testing");
		finishTest.setDisable(true);
		finishTest.setFocusTraversable(false);
		finishTest.setOnAction(e -> {
			Block223Page.setGameUpdateScene(primaryStage, 20);
		});
		
		resumeTest = new Button("Resume Test");
		resumeTest.setDisable(true);
		resumeTest.setFocusTraversable(false);
		resumeTest.setOnAction(e -> {
			resumeTest.setDisable(true);
			Runnable task = new Runnable() {
				public void run() {
					try {
						Block223Controller.startGame(TestPane.this);
						testStarted = false;
						finishTest.setDisable(false);
						resumeTest.setDisable(false);

					} catch (InvalidInputException e) {
					}
				}
			};
			Thread t2 = new Thread(task);
			t2.setDaemon(true);
			t2.start();
		});
		
		buttonBox = new HBox(20);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(resumeTest, finishTest);
		buttonBox.setPadding(new Insets(0, 0, Block223Page.getScreenHeight()/4, 0));
		
		this.setBottom(buttonBox);

		paddle = new Rectangle();
		paddle.setWidth(LastPageLayoutPane.getSettingsPane().getMaxPaddleSliderValue());
		paddle.setHeight(constants.getPaddleWidth());
		paddle.setFill(Color.WHITE);
		paddle.setTranslateX(constants.getPlayAreaSide()/2);
		paddle.setTranslateY(constants.getPlayAreaSide()-constants.getVerticalDistance()-constants.getPaddleWidth());

		ball = new Circle();
		ball.setRadius(constants.getBallDiameter()/2);
		ball.setFill(Color.WHITE);
		ball.setTranslateX(constants.getPlayAreaSide()/2);
		ball.setTranslateY(constants.getPlayAreaSide()/2);
		
		testArea.getChildren().addAll(paddle, ball);
		
		testStarted = true;
		
		Runnable task = new Runnable() {
			public void run() {
				try {
					Block223Controller.testGame(TestPane.this);
					testStarted = false;
					finishTest.setDisable(false);
					resumeTest.setDisable(false);

				} catch (InvalidInputException e) {
				}
			}
		};
		Thread t2 = new Thread(task);
		t2.setDaemon(true);
		t2.start();
	}

	@Override
	public String takeInputs() {
		String temp = ""+inputs;
		inputs = "";
		return temp;
	}
	
	public static void setInputs(String str) {
		inputs = inputs+str;
	}

	@Override
	public void refresh() {	
		try {
			pgame = Block223Controller.getCurrentPlayableGame();
		} catch(InvalidInputException iie ) {}
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				testArea.getChildren().clear();
				testArea.getChildren().addAll(paddle, ball);
				
				for(TOCurrentBlock toBlock : pgame.getBlocks()) {
					Rectangle block = new Rectangle();
					block.setWidth(constants.getSize());
					block.setHeight(constants.getSize());
					Color blockColor = new Color((double)toBlock.getRed()/255, 						
							(double)toBlock.getGreen()/255, (double)toBlock.getBlue()/255, 1);
					block.setFill(blockColor);
					block.setTranslateX(toBlock.getX());
					block.setTranslateY(toBlock.getY());
					testArea.getChildren().add(block);
				}				
			}
		});
		
		ball.setTranslateX(pgame.getCurrentBallX());
		ball.setTranslateY(pgame.getCurrentBallY());
		paddle.setTranslateX(pgame.getCurrentPaddleX());
	}

	
	public static boolean isTestStarted() {
		return testStarted;
	}
	
	@Override
	public void endGame(int nrOfLives, TOHallOfFameEntry hof) {
		// TODO Auto-generated method stub
		
	}
}
