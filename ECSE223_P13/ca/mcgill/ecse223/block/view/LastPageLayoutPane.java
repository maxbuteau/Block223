package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOConstant;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LastPageLayoutPane extends BorderPane {

	// Define the class nodes and containers:
	private Label error = new Label();
	private static TOConstant constants;
	private static TOGame game;
	private Media errorSFXmedia;
	private Stage blockToolboxStage;
	private Stage helpStage;
	private static Stage publishStage;
	
	private VBox settingsBox;
	private static SettingsPane settingsPane;

	private VBox blockCreatorBox;
	private BlockCreatorPane blockPane;
	private Button blockToolbox;

	private VBox gridBox;
	private DesignGridPane designPane;
	private HBox changeLevel;
	private Label level;

	private VBox buttons_error;
	private HBox buttons;
	private Button quitButton;
	private static Button saveGame;
	private Button helpButton;
	private Button backToGameButton;
	private static Button publishButton;
	private Button testButton;

	private int currentLvl = 1;
	private double spacing;
	

	// Default constructor that initializes said nodes and containers
	public LastPageLayoutPane(Stage primaryStage, double spacing) {
		constants = Block223Controller.getConstants();
		
		// get the current game
		error.setText("");
		try {
			game = Block223Controller.getCurrentDesignableGame();
		} catch (InvalidInputException e) {
			Block223Page.changeToGameSelectionScene(primaryStage);
		}

		this.spacing = spacing;
		// Instantiate all fields

		designPane = new DesignGridPane(currentLvl, this);
		blockPane = new BlockCreatorPane(spacing);
		settingsPane = new SettingsPane(game, spacing, this);
		quitButton = new Button("Log out");
		saveGame = new Button("Save game");
		blockToolbox = new Button("Block Toolbox");
		changeLevel = new HBox();
		error = new Label("");
		error.setStyle("-fx-text-fill: #DC143C;-fx-font:21 Garamond;");
		helpButton = new Button("Help");
		backToGameButton = new Button("Back to game selection page");
		testButton = new Button("Test Game");
		publishButton =  new Button("Publish Game");
		this.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");

		// Everything is now initialized. Call a method to paint the pane.
		paint(primaryStage);
	}

	// Paints the pane
	private void paint(Stage primaryStage) {
		//Create the change level feature
		ImageView previousLvl = new ImageView(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/arrow.png"));
		previousLvl.setRotate(270);
		previousLvl.setFitHeight(spacing*4);
		previousLvl.setFitWidth(spacing*4);
		ImageView nextLvl = new ImageView(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/arrow.png"));
		nextLvl.setRotate(90);
		nextLvl.setFitHeight(spacing*4);
		nextLvl.setFitWidth(spacing*4);
		level = new Label("Level 1");
		level.setStyle("-fx-font:24 Garamond; -fx-text-fill: white;-fx-font-weight: bold;");
		level.setTranslateY(spacing*5/4);
		changeLevel.getChildren().addAll(previousLvl, level, nextLvl);

		buttons_error = new VBox(20);
		buttons = new HBox(20);
		buttons.getChildren().addAll(saveGame, quitButton, helpButton, backToGameButton, testButton, publishButton);
		buttons.setAlignment(Pos.CENTER);
		buttons_error.setAlignment(Pos.CENTER);
		error.setAlignment(Pos.CENTER);
		buttons_error.setPadding(new Insets(0, 0, 0, 0));
		buttons_error.getChildren().addAll(buttons, error);

		gridBox = new VBox(20);
		gridBox.getChildren().addAll(designPane, changeLevel, blockToolbox);
		gridBox.setAlignment(Pos.CENTER);
		changeLevel.setTranslateX(spacing*4);
		gridBox.setPadding(new Insets(0, 0, 0, Block223Page.getScreenWidth()/30));
		
		blockCreatorBox = new VBox(0);
		blockCreatorBox.getChildren().addAll(blockPane);
		blockCreatorBox.setAlignment(Pos.CENTER);
		blockCreatorBox.setPadding(new Insets(0, Block223Page.getScreenWidth()/75, 0, Block223Page.getScreenWidth()/75));

		settingsBox = new VBox(20);
		settingsBox.getChildren().addAll(settingsPane);
		settingsBox.setAlignment(Pos.CENTER);
		settingsBox.setPadding(new Insets(0, Block223Page.getScreenWidth()/30, 0, 0));
		
		// Fill the containers with their subcontainers/nodes
		this.setCenter(blockCreatorBox);
		this.setRight(settingsBox);
		this.setLeft(gridBox);
		this.setBottom(buttons_error);
		
		this.setOnMouseDragReleased(e -> {
			designPane.refresh();
		});
		
		//initialize the sfx
		errorSFXmedia = new Media(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/ErrorSFX.mp3"));
		MediaPlayer errorSFX = new MediaPlayer(errorSFXmedia);
		errorSFX.setCycleCount(1);
		errorSFX.setVolume(0.5);

		// action listeners
		previousLvl.setOnMouseClicked(e -> {
			errorSFX.stop();
			if (currentLvl > 1) {
				Block223Page.buttonPressSound();
				((VBox) this.getLeft()).getChildren().remove(0);
				currentLvl -= 1;
				designPane = new DesignGridPane(currentLvl, this);
				((VBox) this.getLeft()).getChildren().add(0,designPane);
				level.setText("Level "+currentLvl);
			}
			else {
				errorSFX.play();
			}
		});

		nextLvl.setOnMouseClicked(e -> {
			errorSFX.stop();
			if (currentLvl < game.getNrLevels()) {
				Block223Page.buttonPressSound();
				((VBox) this.getLeft()).getChildren().remove(0);
				currentLvl += 1;
				designPane = new DesignGridPane(currentLvl, this);
				((VBox) this.getLeft()).getChildren().add(0,designPane);
				level.setText("Level "+currentLvl);

			}
			else {
				errorSFX.play();
			}
		});

		blockToolbox.setOnAction(e->{
			Block223Page.buttonPressSound();
			blockToolboxStage = new Stage();
			blockToolboxStage.initStyle(StageStyle.UTILITY);
			blockToolboxStage.setTitle("BlockToolbox");
			blockToolboxStage.setAlwaysOnTop(true);
			blockToolboxStage.initOwner(primaryStage);
			blockToolboxStage.setScene(new Scene(new ToolboxView()));
			blockToolboxStage.setResizable(false);
			blockToolboxStage.show();
			blockToolbox.setDisable(true);
			blockToolboxStage.setOnCloseRequest(ex->{
				blockToolbox.setDisable(false);
			});
		});
		quitButton.setOnAction(e->{
			Block223Page.buttonPressSound();
			Block223Controller.logout();
			primaryStage.setScene(Block223Page.getLoginScene());
			Block223Page.playCiaoGunty();

		});
		helpButton.setOnAction(e->{
			Block223Page.buttonPressSound();
			helpStage = new Stage();
			helpStage.initStyle(StageStyle.UTILITY);
			helpStage.setAlwaysOnTop(true);
			helpStage.initOwner(primaryStage);
			helpStage.setScene(new Scene(new HelpView()));
			helpStage.setResizable(false);
			helpStage.show();
			helpStage.setTitle("Help");
			helpButton.setDisable(true);
			helpStage.setOnCloseRequest(ex->{
				helpButton.setDisable(false);
			});

		});
		saveGame.setOnAction(e->{
			
			try {
				error.setText("");
				Block223Page.buttonPressSound();
				Block223Controller.saveGame();
			} catch (InvalidInputException e1) {
				System.out.println(e1.getMessage());
				errorSFX.stop();
				errorSFX.play();
				error.setText(e1.getMessage());
			}
		});
		
		backToGameButton.setOnAction(e -> {
			Block223Page.buttonPressSound();
			Block223Page.changeToGameSelectionScene(primaryStage);
		});
		
		testButton.setOnAction(e->{
			error.setText("");
			try {
				if(Block223Controller.getBlocksOfCurrentDesignableGame().size()==0) {
					errorSFX.stop();
					errorSFX.isAutoPlay();
					error.setText("A block must be created before a game can be tested.");
				}
				else {
					Block223Page.buttonPressSound();
					Block223Page.setTestingScene(primaryStage);
				}
			} catch (InvalidInputException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
		publishButton.setOnAction(e -> {
			Block223Page.buttonPressSound();
			publishStage = new Stage();
			publishStage.setAlwaysOnTop(true);
			publishStage.initOwner(primaryStage);
			publishStage.setScene(new Scene(new PublishView(primaryStage)));
			publishStage.setResizable(false);
			publishStage.show();
			publishStage.setTitle("Publish Game");
			publishButton.setDisable(true);
			publishStage.setOnCloseRequest(ex->{
				publishButton.setDisable(false);
			});
		});

		error.setWrapText(true);
		error.setMaxWidth(Block223Page.getScreenWidth());

	}

	public void setErrorMessage(String errorMsg) {
		error.setText(errorMsg);
	}

	public static void closePublishStage() {
		publishStage.close();
		publishButton.setDisable(false);
	}
	
	public static void pressSave() {
		saveGame.fire();
	}
	
	public void refreshDetails() {
		error.setText("");
		try {
			game = Block223Controller.getCurrentDesignableGame();
		} catch (InvalidInputException e) {
			error.setText(e.getMessage());
		}
		if(currentLvl>game.getNrLevels()-1) {
			this.level.setText("Level "+game.getNrLevels());
			currentLvl = game.getNrLevels();
			((VBox) this.getLeft()).getChildren().remove(0);
			designPane = new DesignGridPane(currentLvl, this);
			((VBox) this.getLeft()).getChildren().add(0,designPane);

		}
	}
	
	public static SettingsPane getSettingsPane() {
		return settingsPane;
	}
}