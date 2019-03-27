package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
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
import javafx.stage.Stage;

public class LastPageLayoutPane extends BorderPane {

	// Define the class nodes and containers:
	private Label error = new Label();
	private static TOGame game;
	private Media errorSFXmedia;
	private Stage blockToolboxStage;
	private Stage helpStage;
	private static Stage publishStage;
	
	private VBox settingsBox;
	private SettingsPane settingsPane;

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
	private static double Spacing;
	private double spacing;

	// Default constructor that initializes said nodes and containers
	public LastPageLayoutPane(Stage primaryStage, double spacing) {
		// get the current game
		error.setText("");
		try {
			game = Block223Controller.getCurrentDesignableGame();
		} catch (InvalidInputException e) {
			Block223Page.changeToGameSelectionScene(primaryStage);
		}

		this.spacing = spacing;
		Spacing = spacing;
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
		
		//initialize the sfx
		errorSFXmedia = new Media(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/ErrorSFX.mp3"));
		MediaPlayer errorSFX = new MediaPlayer(errorSFXmedia);
		errorSFX.setCycleCount(1);
		errorSFX.setVolume(0.5);

		// action listeners
		previousLvl.setOnMouseClicked(e -> {
			errorSFX.stop();
			if (currentLvl > 1) {
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
			blockToolboxStage = new Stage();
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
			Block223Controller.logout();
			primaryStage.setScene(Block223Page.getLoginScene());

		});
		helpButton.setOnAction(e->{
			helpStage = new Stage();
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
				Block223Controller.saveGame();
			} catch (InvalidInputException e1) {
				errorSFX.stop();
				errorSFX.play();
				error.setText(e1.getMessage());
			}
		});
		
		backToGameButton.setOnAction(e -> {
			Block223Page.changeToGameSelectionScene(primaryStage);
		});
		
		testButton.setOnAction(e->{
			try {
				Block223Controller.testGame(null);
			} catch (InvalidInputException e1) {
				error.setText(e1.getMessage());
			}
		});
		
		publishButton.setOnAction(e -> {
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
			if (Block223Application.getCurrentGame().getPublished() == true) {
				publishButton.setDisable(true);
			}
		});

		error.setWrapText(true);
		error.setMaxWidth(Block223Page.getScreenWidth());

	}

	public void setErrorMessage(String errorMsg) {
		error.setText(errorMsg);
	}

	public void refresh() {
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
	public static double getOffX() {
		return Spacing;
	}
	public static double getOffY() {
		return Spacing*2;
	}
	public static void closePublishStage() {
		publishStage.close();
		publishButton.setDisable(false);
	}
	public static void pressSave() {
		saveGame.fire();
	}
}
