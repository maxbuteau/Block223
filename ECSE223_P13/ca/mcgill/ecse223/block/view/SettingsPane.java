package ca.mcgill.ecse223.block.view;


import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOConstant;
import ca.mcgill.ecse223.block.controller.TOGame;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SettingsPane extends Pane{

	//Define the constant values
	private final TOConstant constants = Block223Controller.getConstants();
	private final int MAX_BLOCKS = constants.getMaxHorizontalBlocks()*constants.getMaxVerticalBlocks(); //change
	private final int MIN_BLOCKS = 1;
	private final int MAX_LEVELS = constants.getMaxNrLevels();
	private final int MIN_LEVELS = constants.getMinNrLevels();
	private final double MAX_INCR = 5; //arbitrarily set the max to 5
	private final double MIN_INCR = 0;
	private final double MAX_PADDLE = constants.getPlayAreaSide();
	private final double MIN_PADDLE = 1; //arbitrarily set the minimum length to 1 pixel
	private final double MAX_SPEED = 200; //maximum speed arbitrarily set to 200pix/s
	private final double MIN_SPEED = 1; //arbitrarily set the minimum speed to 1 pixel/second
	
	//Create the sliders
	//default value bad, need to be that of model get via togame
	private Slider nrBlocksSlider;
	private Slider nrLevelsSlider;
	private Slider increasingFactorSlider;
	private Slider minPaddleSlider;
	private Slider maxPaddleSlider;
	private Slider minXSpeedSlider;
	private Slider minYSpeedSlider;
	
	//Create labels associated with said sliders
	private Label errorMsg = new Label("");
	private Label ballParams = new Label("Ball Speed");
	private Label paddleParams = new Label("Paddle Length");
	private Label levelParam = new Label("Level");
	private Label gameName = new Label("Game name");
	private Label nrBlocksLabel = new Label("Number of blocks");
	private Label nrLevelsLabel = new Label("Number of levels");
	private Label increasingFactorLabel = new Label("Increasing factor");
	private Label minPaddleLabel = new Label("Minimum length");
	private Label maxPaddleLabel = new Label("Maximum length");
	private Label minXSpeedLabel = new Label("Minimum X speed");
	private Label minYSpeedLabel = new Label("Minimum Y speed");
	private Label nrBlocksValue;
	private Label nrLevelsValue;
	private Label increasingFactorValue;
	private Label minPaddleValue;
	private Label maxPaddleValue;
	private Label minXSpeedValue;
	private Label minYSpeedValue;
	
	//Text field to change the game name
	private TextField gameTF;
	
	//Save button
	private Button save = new Button("Save parameters");
	
	private LastPageLayoutPane x;
	SettingsPane(TOGame game, double spacing, LastPageLayoutPane x){
		this.setStyle("-fx-background-color: white;");
		this.x = x;
		//initialize sliders with their last saved values as default value
		minYSpeedSlider = new Slider(MIN_SPEED, MAX_SPEED, game.getMinBallSpeedY());
		minXSpeedSlider = new Slider(MIN_SPEED, MAX_SPEED, game.getMinBallSpeedX());
		maxPaddleSlider = new Slider(MIN_PADDLE, MAX_PADDLE, game.getMaxPaddleLength());
		minPaddleSlider = new Slider(MIN_PADDLE, MAX_PADDLE, game.getMinPaddleLength());
		increasingFactorSlider = new Slider(MIN_INCR, MAX_INCR, game.getBallSpeedIncreaseFactor());
		nrLevelsSlider = new Slider(MIN_LEVELS, MAX_LEVELS, game.getNrLevels());
		nrBlocksSlider = new Slider(MIN_BLOCKS, MAX_BLOCKS, game.getNrBlocksPerLevel());
		
		//initialize the labels
		nrBlocksValue = new Label(""+game.getNrBlocksPerLevel());
		nrLevelsValue = new Label(""+game.getNrLevels());
		increasingFactorValue = new Label((double) ((int) (100 * game.getBallSpeedIncreaseFactor())) / 100 + " px/s");
		minPaddleValue = new Label(""+game.getMinPaddleLength()+ " px");
		maxPaddleValue = new Label(""+game.getMaxPaddleLength()+" px");
		minXSpeedValue = new Label(""+game.getMinBallSpeedX()+" px/s");
		minYSpeedValue = new Label(""+game.getMinBallSpeedY()+" px/s");
		
		//initialize the textfield
		gameTF = new TextField(game.getName());
		
		//Create gridpanes in which to place everything
		GridPane grid1 = new GridPane();
		grid1.addColumn(0, increasingFactorLabel, minXSpeedLabel, minYSpeedLabel);
		grid1.addColumn(1, increasingFactorSlider, minXSpeedSlider, minYSpeedSlider);
		grid1.addColumn(2, increasingFactorValue, minXSpeedValue, minYSpeedValue);
		grid1.setPadding(new Insets(spacing/5*2));
		grid1.setHgap(spacing/20*3);
		grid1.setVgap(spacing/10);
		
		GridPane grid2 = new GridPane();
		grid2.addColumn(0, minPaddleLabel, maxPaddleLabel);
		grid2.addColumn(1, minPaddleSlider, maxPaddleSlider);
		grid2.addColumn(2, minPaddleValue, maxPaddleValue);
		grid2.setPadding(new Insets(spacing/5*2));
		grid2.setHgap(spacing/20*3);
		grid2.setVgap(spacing/10);
		
		GridPane grid3 = new GridPane();
		grid3.addColumn(0, nrBlocksLabel, nrLevelsLabel);
		grid3.addColumn(1, nrBlocksSlider, nrLevelsSlider);
		grid3.addColumn(2, nrBlocksValue, nrLevelsValue);
		grid3.setPadding(new Insets(spacing/5*2));
		grid3.setHgap(3/20*spacing);
		grid3.setVgap(spacing/10);
		
		VBox v1 = new VBox();
		v1.getChildren().addAll(ballParams, grid1);
		
		VBox v2 = new VBox();
		v2.getChildren().addAll(levelParam, grid3);
		
		VBox v3 = new VBox();
		v3.getChildren().addAll(paddleParams, grid2);
		
		HBox gameBox = new HBox(spacing/2);
		gameBox.setPadding(new Insets(spacing/2,0,spacing/2,0));
		gameBox.getChildren().addAll(gameName, gameTF);
		
		save.setTranslateX(spacing*5);
		
		VBox v4 = new VBox(spacing*3/4);
		v4.setPadding(new Insets(spacing,spacing,spacing,spacing));
		v4.getChildren().addAll(gameBox, v1,v2,v3, errorMsg, save);
		
		this.getChildren().add(v4);
		//we'll use a separate method to do the action listeners
		actionListeners();
		
		//we'll use a separate method to set the styles
		setStyles();
	
	}

	private void setStyles() {
		ballParams.setStyle("-fx-font:23 Garamond;");
		levelParam.setStyle("-fx-font:23 Garamond;");
		paddleParams.setStyle("-fx-font:23 Garamond;");
		gameName.setStyle("-fx-font:23 Garamond;");
		errorMsg.setStyle("-fx-font:20 Garamond;-fx-text-fill: #FF0000");
		errorMsg.setWrapText(true);
		errorMsg.setPrefWidth(this.getBoundsInParent().getWidth()*2.7);
		save.setStyle("-fx-font:16 Garamond;");
		//this.setStyle("-fx-border-color: black");
		this.setBorder(new Border(new BorderStroke(Color.VIOLET, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(8))));
		this.setOpacity(0.75);
		save.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");
		
	}

	private void actionListeners() {
		
		//Slider action listeners
		minPaddleSlider.valueProperty().addListener(e->{
			minPaddleValue.setText((int)minPaddleSlider.getValue() + " px");
		});
		maxPaddleSlider.valueProperty().addListener(e->{
			maxPaddleValue.setText((int) maxPaddleSlider.getValue() + " px");
		});
		increasingFactorSlider.valueProperty().addListener(e->{
			increasingFactorValue.setText((double) ((int) (100 * increasingFactorSlider.getValue())) / 100 + " px/s");
		});
		nrBlocksSlider.valueProperty().addListener(e->{
			nrBlocksValue.setText((int)nrBlocksSlider.getValue()+ "");
		});
		nrLevelsSlider.valueProperty().addListener(e->{
			nrLevelsValue.setText((int)nrLevelsSlider.getValue()+ "");
		});
		minXSpeedSlider.valueProperty().addListener(e->{
			minXSpeedValue.setText((int)minXSpeedSlider.getValue()+ " px/s");
		});
		minYSpeedSlider.valueProperty().addListener(e->{
			minYSpeedValue.setText((int)minYSpeedSlider.getValue()+ " px/s");
		});
		
		//Button action listener
		save.setOnAction(e->{
			try {
				errorMsg.setText("");
				Block223Controller.updateGame(gameTF.getText(),(int) nrLevelsSlider.getValue(), (int)nrBlocksSlider.getValue(),
						(int)minXSpeedSlider.getValue(), (int)minYSpeedSlider.getValue(),increasingFactorSlider.getValue(),
						(int)maxPaddleSlider.getValue(), (int)minPaddleSlider.getValue());
				x.refresh();
			} catch (InvalidInputException e1) {
				errorMsg.setText(e1.getMessage());
			}
			if ((int)nrBlocksSlider.getValue() < Block223Controller.HighestNrOfBlocksInLevel(Block223Application.getCurrentGame())) {
				nrBlocksSlider.setValue(Block223Controller.HighestNrOfBlocksInLevel(Block223Application.getCurrentGame()));
			}
			
		});
		
		//TODO update paddle, level block
	}
} 
