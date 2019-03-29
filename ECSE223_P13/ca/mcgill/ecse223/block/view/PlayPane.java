package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOConstant;
import ca.mcgill.ecse223.block.controller.TOCurrentBlock;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOPlayableGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PlayPane extends BorderPane implements Block223PlayModeInterface {
	private static Pane playArea;
	private static PlayHeader playHeader;
	private static Button startGame;
	private static HallOfFamePane hallOfFamePane;
	
	private static TOCurrentlyPlayedGame pgame;
	private static TOConstant constants;
	
	private static Block223PlayModeInterface bp;
	
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
		
		playHeader = new PlayHeader();
		playHeader.setBorder(new Border(new BorderStroke(Color.WHITE, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		hallOfFamePane = new HallOfFamePane();
		hallOfFamePane.setBorder(new Border(new BorderStroke(Color.WHITE, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		hallOfFamePane.setPrefWidth(Block223Page.getScreenWidth()/4);
		
		startGame = new Button("Start Game");
		startGame.setAlignment(Pos.CENTER);
				
		this.setCenter(playArea);
		this.setTop(playHeader);
		this.setBottom(startGame);
		this.setRight(hallOfFamePane);
		displayPlayArea();
		
		this.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");
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
		
		Rectangle paddle = new Rectangle();
		paddle.setWidth(pgame.getCurrentPaddleLength());
		paddle.setHeight(constants.getPaddleWidth());
		paddle.setFill(Color.WHITE);
		paddle.setTranslateX(pgame.getCurrentPaddleX());
		paddle.setTranslateY(constants.getPlayAreaSide()-constants.getVerticalDistance()-constants.getPaddleWidth());
		
		Circle ball = new Circle();
		ball.setRadius(constants.getBallDiameter());
		Image meteor = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/meteor.png"));
		ball.setFill(new ImagePattern(meteor));
		ball.setTranslateX(pgame.getCurrentBallX());
		ball.setTranslateY(pgame.getCurrentBallY());
		
		playArea.getChildren().addAll(paddle, ball);
	}
	
	@Override
	public String takeInputs() {
		if (bp == null) {
			return "";
		}
		return bp.takeInputs();
	}

	@Override
	public void refresh() {
		System.out.println("UI is refreshing now...");
	}

}
