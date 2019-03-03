package ca.mcgill.ecse223.block.view;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.Toolkit;
import java.util.List;

import ca.mcgill.ecse223.block.controller.*;

public class Block223Page extends Application{

	private static ChosenBlock chosenBlock;	

	private static Label gameSelectionError;

	private static Scene scene;
	//LOGIN
	private static Scene loginScene;

	//REGISTER
	private static Scene registerScene;

	//GAME SELECTION
	private static Scene gameSelectionScene;
	private static VBox gameSelectionPane;
	private static HBox gameSelectionButtonRow;
	private static ListView<String> gameSelectionList;
	private static ObservableList<String> gameSelectionListData;
	private static Button gameSelectionCreateGameButton;
	private static Button gameSelectionUpdateGameButton;
	private static Scene createGameScene;
	private static VBox createGameBox;
	private static Button gameSelectionLogoutButton;
	private static Button gameSelectionDeleteButton;
	private static Label gameSelectionName;

	//DESIGN PAGE
	private static Scene gameDesignScene;

	private final static double SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final static double SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 60;

	private static Media soundMedia = new Media(getResource("ca/mcgill/ecse223/block/view/resources/click.mp3"));
	private static MediaPlayer sound = new MediaPlayer(soundMedia);

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Image background = new Image(getResource("ca/mcgill/ecse223/block/view/resources/background.jpg"));
		ImageView bg = new ImageView(background);
		bg.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
		bg.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
		Image background2 = new Image(getResource("ca/mcgill/ecse223/block/view/resources/background.jpg"));
		ImageView bg2 = new ImageView(background2);
		bg2.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
		bg2.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
		primaryStage.setResizable(false);

		//REGISTER SCENE
		StackPane loginStack = new StackPane();
		scene = new Scene(loginStack, SCREEN_WIDTH,SCREEN_HEIGHT);
		StackPane registerStack = new StackPane();
		RegisterPane registerPane = new RegisterPane(primaryStage, loginStack, scene);
		registerPane.setSpacing(20);
		registerStack.getChildren().addAll(bg2, registerPane);
		//registerPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(0, 0, false, false, true, false))));
		//registerScene = new Scene(registerStack, SCREEN_WIDTH, SCREEN_HEIGHT);

		//LOGIN SCENE
		
		LoginPane loginPane = new LoginPane(primaryStage, gameSelectionScene, registerStack, scene);
		loginPane.setSpacing(20);
		loginStack.getChildren().addAll(bg, loginPane);
		//loginPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(0, 0, false, false, true, false))));
		//loginScene = new Scene(loginStack, SCREEN_WIDTH, SCREEN_HEIGHT);


		
		buttonPressSound();
		//change the values accordingly ^
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.getIcons().add(new Image("ca/mcgill/ecse223/block/view/resources/logo.jpg"));
	}
	
	public static Scene getLoginScene() {
		return loginScene;
	}
	
	public static Scene getRegisterScene() {
		return registerScene;
	}
	
	public static void changeToGameSelectionScene(Stage primaryStage) {

		//SELECTION GAME
		gameSelectionPane = new VBox(80);
		gameSelectionPane.setPadding(new Insets(60,60,60,60));
		gameSelectionName = new Label("Game names");
		gameSelectionName.setTranslateX(SCREEN_WIDTH/3.7);
		gameSelectionName.setStyle("-fx-text-fill: #FFFFFF;-fx-font-weight: bold;-fx-font:35 Garamond;-fx-font-weight: bold;");

		gameSelectionScene = new Scene(gameSelectionPane, SCREEN_WIDTH, SCREEN_HEIGHT);

		Image background = new Image(getResource("ca/mcgill/ecse223/block/view/resources/background.jpg"));
		gameSelectionPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(0, 0, false, false, true, false))));
		//Buttons
		gameSelectionButtonRow = new HBox(30);
		gameSelectionButtonRow.setPadding(new Insets(10, 10, 10, 10));
		gameSelectionCreateGameButton = new Button("Create Game");

		gameSelectionDeleteButton = new Button("Delete game");

		gameSelectionCreateGameButton.setOnAction(e -> {
			Stage createGameStage = new Stage();
			createGameBox = new VBox(20);
			Label createGameNameLabel = new Label("Game name : ");
			createGameNameLabel.setTranslateX(SCREEN_WIDTH / 6);
			TextField createGameNameField = new TextField();

			createGameNameField.setOnKeyPressed(ev -> {
				if(ev.getCode().equals(KeyCode.ENTER)) {
					try {
						Block223Controller.createGame(createGameNameField.getText());
						createGameStage.close();
						gameSelectionError.setText("");
					} catch (InvalidInputException e1) {
						gameSelectionError.setText(e1.getMessage());
					}

					refreshGameSelection();
					gameSelectionCreateGameButton.setDisable(false);
				}
			});



			createGameBox.getChildren().addAll(createGameNameLabel, createGameNameField);
			createGameScene = new Scene(createGameBox, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 6);
			createGameStage.setScene(createGameScene);
			createGameStage.show();
			createGameStage.setAlwaysOnTop(true);
			createGameStage.setResizable(false);
			gameSelectionCreateGameButton.setDisable(true);
			createGameStage.setOnCloseRequest(emmacona->{
				gameSelectionCreateGameButton.setDisable(false);
			});

		});

		gameSelectionUpdateGameButton = new Button("Update Game");
		gameSelectionUpdateGameButton.setOnAction(e->{
			try {
				Block223Controller.selectGame(gameSelectionList.getSelectionModel().getSelectedItem());
				setGameUpdateScene(primaryStage, 20, loginScene);
				gameSelectionError.setText("");
			} catch (InvalidInputException e1) {
				gameSelectionError.setText(e1.getMessage());
			}
			catch (NullPointerException e2) {
				gameSelectionError.setText("A game must be selected to update it.");
			}

		});

		gameSelectionLogoutButton = new Button("Logout");
		gameSelectionLogoutButton.setOnAction(e -> {
			Block223Controller.logout();
			primaryStage.setScene(loginScene);	
		});

		gameSelectionButtonRow.getChildren().addAll(gameSelectionCreateGameButton,gameSelectionDeleteButton, gameSelectionUpdateGameButton, gameSelectionLogoutButton);
		gameSelectionButtonRow.setTranslateX(SCREEN_WIDTH/5.3);
		//List
		gameSelectionList = new ListView<String>();
		gameSelectionList.setStyle("-fx-background-color: transparent;");
		gameSelectionPane.setPadding(new Insets(20,20,20,20));
		gameSelectionListData = FXCollections.observableArrayList();

		gameSelectionDeleteButton.setOnAction(f->{
			try {
				Block223Controller.deleteGame(gameSelectionList.getSelectionModel().getSelectedItem());
				refreshGameSelection();
				gameSelectionError.setText("");
			} catch (InvalidInputException e1) {
				gameSelectionError.setText(e1.getMessage());
			}
			catch (NullPointerException e2) {
				gameSelectionError.setText("A game must be selected to delete it.");
			}
		});

		//error
		gameSelectionError = new Label();
		gameSelectionError.setStyle("-fx-text-fill: #DC143C");

		refreshGameSelection();
		gameSelectionList.setItems(gameSelectionListData);
		gameSelectionPane.getChildren().addAll(gameSelectionName,gameSelectionList, gameSelectionButtonRow, gameSelectionError);
		primaryStage.setScene(gameSelectionScene);
		gameSelectionScene.getStylesheets().add(getResource("ca/mcgill/ecse223/block/view/resources/style.css"));
		primaryStage.setResizable(false);
	}


	private static void setGameUpdateScene(Stage primaryStage, double spacing, Scene login) {
		LastPageLayoutPane l = new LastPageLayoutPane(primaryStage, spacing, login);
		Image background = new Image(getResource("ca/mcgill/ecse223/block/view/resources/background.jpg"));
		l.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(0, 0, false, false, true, false))));

		gameDesignScene = new Scene(l, SCREEN_WIDTH, SCREEN_HEIGHT); 
		primaryStage.setScene(gameDesignScene);
		primaryStage.setResizable(false);

	}

	private static void refreshGameSelection() {
		gameSelectionList.getItems().clear();
		try {
			List<TOGame> toGames = Block223Controller.getDesignableGames();
			for (TOGame toGame : toGames) {
				String toGameName = toGame.getName();
				gameSelectionListData.add(toGameName);
			}
		} catch (InvalidInputException e2) {
			e2.printStackTrace();
		}
	}

	public static String getResource(String res)
	{
		return ClassLoader.getSystemResource(res).toString();
	}

	private static void buttonPressSound() {
		sound.stop();
		sound.play();
		sound.setVolume(1);
	}

	public static ChosenBlock getChosenBlock() {
		return chosenBlock;
	}

	public static void setChosenBlock(String id) {
		chosenBlock = new ChosenBlock(id);
	}
}

class ChosenBlock {
	private int id;

	public ChosenBlock(String id) {
		this.id = Integer.parseInt(id);
	}

	public int getId() {
		return id;
	}
}