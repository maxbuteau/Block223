package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.TOGame;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class LastPageLayoutPane extends Pane {

	// Define the class nodes and containers:
	private PlayAreaPane playPane;
	private BlockCreatorPane blockPane;
	private SettingsPane settingsPane;
	private Label quitLabel;
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

	// Default constructor that initializes said nodes and containers
	public LastPageLayoutPane(Stage primaryStage) {
		// get the current game
		// TOGame game = Block223Application.getDesignableGame();

		// Test game for now
		game = new TOGame("name", 5, 5, 5, 5, 5, 5, 7);
		// Instantiate all fields

		playPane = new PlayAreaPane(currentLvl, game.getMinPaddleLength(), game.getMaxPaddleLength(),
				game.getNrLevels());
		blockPane = new BlockCreatorPane();
		settingsPane = new SettingsPane(game);
		quitLabel = new Label("QUIT");
		saveGame = new Button("Save game");
		blockToolbox = new Button("Block Toolbox");
		changeLevel = new HBox();
		levelAndBlockContainer = new VBox();
		motherContainer = new HBox(75);

		// Everything is now initialized. Call a method to paint the pane.
		paint(primaryStage);
	}

	// Paints the pane
	private void paint(Stage primaryStage) {

		// Fill the containers with their subcontainers/nodes
		motherContainer.getChildren().addAll(playPane, levelAndBlockContainer, settingsPane);
		levelAndBlockContainer.getChildren().addAll(blockPane, blockToolbox, changeLevel);

		// add some additional settings to them
		motherContainer.setPadding(new Insets(40, 20, 40, 20));
		levelAndBlockContainer.setPadding(new Insets(10));

		// Create the change level feature
		ImageView previousLvl = new ImageView(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/arrow.png"));
		previousLvl.setRotate(270);
		previousLvl.setFitHeight(80);
		previousLvl.setFitWidth(80);
		ImageView nextLvl = new ImageView(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/arrow.png"));
		nextLvl.setRotate(90);
		nextLvl.setFitHeight(80);
		nextLvl.setFitWidth(80);
		level = new Label("Level 1");
		level.setStyle("-fx-font:20 Garamond;");
		level.setTranslateY(25);
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
				playPane = new PlayAreaPane(--currentLvl, game.getMinPaddleLength(), game.getMaxPaddleLength(),
						game.getNrLevels());
				level.setText("Level "+currentLvl);
			}
			else {
				errorSFX.play();
			}
		});
		
		nextLvl.setOnMouseClicked(e -> {
			errorSFX.stop();
			if (currentLvl < game.getNrLevels()) {
				playPane = new PlayAreaPane(++currentLvl, game.getMinPaddleLength(), game.getMaxPaddleLength(),
						game.getNrLevels());
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
			blockToolboxStage.setScene(new Scene(new BlockToolboxPane()));
			blockToolboxStage.setResizable(false);
			blockToolboxStage.show();
			blockToolbox.setDisable(true);
			blockToolboxStage.setOnCloseRequest(ex->{
				blockToolbox.setDisable(false);
			});
		});
		
		
		
		// TODO add images and make pretty, block toolbox, sound when clicking

		// Set the label style to the following:
		quitLabel.setStyle(
				"-fx-font:20 Garamond; -fx-padding:3px; -fx-text-fill: #DC143C; -fx-border-color:black;-fx-background-color:POWDERBLUE;-fx-font-weight:bold");
		this.getChildren().add(motherContainer);

	}

}
