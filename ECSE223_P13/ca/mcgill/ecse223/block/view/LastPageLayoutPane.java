package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LastPageLayoutPane extends Pane {

	// Define the class nodes and containers:
	private Text error;
	private DesignGridPane designPane;
	private BlockCreatorPane blockPane;
	private SettingsPane settingsPane;
	private Button quitLabel;
	private HBox changeLevel;
	private Button blockToolbox;
	private Button saveGame;
	private VBox levelAndBlockContainer;
	private HBox motherContainer;
	private TOGame game;
	private Label level;
	private Media errorSFXmedia;
	private Stage blockToolboxStage;

	private int currentLvl = 1;
	private double spacing;

	// Default constructor that initializes said nodes and containers
	public LastPageLayoutPane(Stage primaryStage, double spacing, Scene login) {
		// get the current game
		 TOGame game = Block223Controller.getCurrentDesignableGame();

		this.spacing = spacing;
		// Test game for now
		//game = new TOGame("name", 5, 5, 5, 5, 5, 5, 7);
		// Instantiate all fields

		designPane = new DesignGridPane(currentLvl, this);
		blockPane = new BlockCreatorPane(spacing);
		settingsPane = new SettingsPane(game, spacing);
		quitLabel = new Button("Log out");
		quitLabel.setStyle("-fx-font:18 Garamond;");
		saveGame = new Button("Save game");
		saveGame.setStyle("-fx-font:18 Garamond;");
		blockToolbox = new Button("Block Toolbox");
		blockToolbox.setStyle("-fx-font:18 Garamond;");
		changeLevel = new HBox();
		levelAndBlockContainer = new VBox(spacing*2);
		motherContainer = new HBox(spacing*3);
		error = new Text("                                                                           ");
		error.setStyle("-fx-text-fill: #DC143C;");

		// Everything is now initialized. Call a method to paint the pane.
		paint(primaryStage, login);
	}

	// Paints the pane
	private void paint(Stage primaryStage, Scene login) {

		// Fill the containers with their subcontainers/nodes
		motherContainer.getChildren().addAll(designPane, levelAndBlockContainer, settingsPane);
		levelAndBlockContainer.getChildren().addAll(blockPane, blockToolbox, changeLevel);
		levelAndBlockContainer.setAlignment(Pos.CENTER);
		changeLevel.setTranslateX(spacing*2);

		// add some additional settings to them
		motherContainer.setPadding(new Insets(spacing*2, spacing, 2*spacing, spacing));
		levelAndBlockContainer.setPadding(new Insets(spacing/2));

		// Create the change level feature
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
		
		//initialize the sfx
		errorSFXmedia = new Media(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/ErrorSFX.mp3"));
		MediaPlayer errorSFX = new MediaPlayer(errorSFXmedia);
		errorSFX.setCycleCount(1);
		errorSFX.setVolume(0.5);

		// action listeners
		previousLvl.setOnMouseClicked(e -> {
			errorSFX.stop();
			if (currentLvl > 1) {
				designPane = new DesignGridPane(--currentLvl, this);
				level.setText("Level "+currentLvl);
			}
			else {
				errorSFX.play();
			}
		});
		
		nextLvl.setOnMouseClicked(e -> {
			errorSFX.stop();
			if (currentLvl < game.getNrLevels()) {
				designPane = new DesignGridPane(++currentLvl, this);
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
		quitLabel.setOnAction(e->{
			Block223Controller.logout();
			primaryStage.setScene(login);		
		});
		saveGame.setOnAction(e->{
			try {
				Block223Controller.saveGame();
			} catch (InvalidInputException e1) {
				errorSFX.stop();
				errorSFX.play();
				error.setText(e1.getMessage());
			}
		});
		HBox lowerButts = new HBox(spacing*15);
		lowerButts.getChildren().addAll(error, saveGame, quitLabel);
		VBox fullCont = new VBox(spacing);
		fullCont.getChildren().addAll(motherContainer, lowerButts);
		this.getChildren().add(fullCont);
		primaryStage.setFullScreen(true);

	}
	
	public void setErrorMessage(String errorMsg) {
		error.setText(errorMsg);
	}

}
