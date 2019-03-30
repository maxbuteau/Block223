package ca.mcgill.ecse223.block.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.Toolkit;
import java.util.List;

import ca.mcgill.ecse223.block.controller.*;
import ca.mcgill.ecse223.block.model.Game;

public class Block223Page extends Application{

	private static ChosenBlock chosenBlock;	

	private static Label gameSelectionError;
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
	
	//PLAYABLE GAME SELECTION
	private static Scene playableGameSelectionScene;
	private static VBox playableGameSelectionPane;
	private static HBox playableGameSelectionButtonRow;
	private static TableView<TOPlayableGame> playableGameSelectionList;
	private static TableColumn<TOPlayableGame, String> columnName;
	private static TableColumn<TOPlayableGame, Integer> columnId;	
	private static TableColumn<TOPlayableGame, Integer> columnLevel;
	private static ObservableList<TOPlayableGame> playableGameSelectionListData;
	private static Label playableGameSelectionName;
	private static Button playableGameSelectionLogoutButton;
	private static Button playableGameSelectionSelectButton;
	private static Label playableGameSelectionError;

	//DESIGN PAGE
	private static Scene gameDesignScene;
	
	//PLAY PAGE
	private static Scene playScene;

	private final static double SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final static double SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100;

	private static Media soundMedia = new Media(getResource("ca/mcgill/ecse223/block/view/resources/click.mp3"));
	private static MediaPlayer sound = new MediaPlayer(soundMedia);
	
	//GAME OVER PAGE
	private static Scene gameOverScene;

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
	
		RegisterPane registerPane = new RegisterPane(primaryStage);
		registerPane.setSpacing(20);
		registerPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(SCREEN_WIDTH, SCREEN_HEIGHT, false, false, false, false))));
		registerScene = new Scene(registerPane, SCREEN_WIDTH, SCREEN_HEIGHT);

		//LOGIN SCENE
		
		LoginPane loginPane = new LoginPane(primaryStage, gameSelectionScene);
		loginPane.setSpacing(20);
	
		loginPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(SCREEN_WIDTH, SCREEN_HEIGHT, false, false, false, false))));
		loginScene = new Scene(loginPane, SCREEN_WIDTH, SCREEN_HEIGHT);


		
		buttonPressSound();
		//change the values accordingly ^
		primaryStage.setScene(loginScene);
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
		gameSelectionPane.setPadding(new Insets(60,SCREEN_WIDTH/4,60,SCREEN_WIDTH/4));
		gameSelectionName = new Label("Game names");
		gameSelectionName.setMaxWidth(Double.MAX_VALUE);
		gameSelectionName.setAlignment(Pos.CENTER);
		gameSelectionName.setStyle("-fx-text-fill: #FFFFFF;-fx-font-weight: bold;-fx-font:35 Garamond;-fx-font-weight: bold;");

		gameSelectionScene = new Scene(gameSelectionPane, SCREEN_WIDTH, SCREEN_HEIGHT);

		Image background = new Image(getResource("ca/mcgill/ecse223/block/view/resources/background.jpg"));
		gameSelectionPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(SCREEN_WIDTH, SCREEN_HEIGHT, false, false, false, false))));
		
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
				setGameUpdateScene(primaryStage, 20);
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
		gameSelectionButtonRow.setAlignment(Pos.CENTER);
		//List
		gameSelectionList = new ListView<String>();
		gameSelectionList.setStyle("-fx-font:18 Garamond; -fx-font-weight: bold;");
		gameSelectionList.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2 && e.getButton() == MouseButton.PRIMARY) {
				gameSelectionUpdateGameButton.fire();
			}
		});
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


	private static void setGameUpdateScene(Stage primaryStage, double spacing) {
		LastPageLayoutPane l = new LastPageLayoutPane(primaryStage, spacing);
		Image background = new Image(getResource("ca/mcgill/ecse223/block/view/resources/background.jpg"));
		l.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(SCREEN_WIDTH, SCREEN_HEIGHT, false, false, false, false))));

		gameDesignScene = new Scene(l, SCREEN_WIDTH, SCREEN_HEIGHT); 
		primaryStage.setScene(gameDesignScene);
		primaryStage.setResizable(false);

	}
	
	public static void setPlayScene(Stage primaryStage) {
		PlayPane pp = new PlayPane(primaryStage);
		Image background = new Image(getResource("ca/mcgill/ecse223/block/view/resources/background.jpg"));
		pp.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(SCREEN_WIDTH, SCREEN_HEIGHT, false, false, false, false))));

		playScene = new Scene(pp, SCREEN_WIDTH, SCREEN_HEIGHT);
		playScene.setOnKeyPressed(e -> {
			if(PlayPane.isStarted()) {
				if(e.getCode() == KeyCode.RIGHT) PlayPane.setInputs("r");
				else if(e.getCode() == KeyCode.LEFT) PlayPane.setInputs("l");
				else if(e.getCode() == KeyCode.SPACE) PlayPane.setInputs(" ");
				else {
					//We do nothing
				}
			}
		});
		primaryStage.setScene(playScene);
		primaryStage.setResizable(false);
	}
	
	public static void setGameOverScene(Stage primaryStage) {
		GameFinishedPane fp = new GameFinishedPane(primaryStage);
		Image background = new Image(getResource("ca/mcgill/ecse223/block/view/resources/background.jpg"));
		fp.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(SCREEN_WIDTH, SCREEN_HEIGHT, false, false, false, false))));
		
		gameOverScene = new Scene(fp, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		primaryStage.setScene(gameOverScene);
		primaryStage.setResizable(false);
	}
	
	public static void logOutAfterGameOver(Stage primaryStage) {
		Block223Controller.logout();
		primaryStage.setScene(loginScene);
		primaryStage.setResizable(false);
	}
	
	public static void changeToPlayableGameSelectionScene(Stage primaryStage) {

		playableGameSelectionPane = new VBox(80);
		playableGameSelectionPane.setPadding(new Insets(60,SCREEN_WIDTH/4,60,SCREEN_WIDTH/4));
		playableGameSelectionName = new Label("Game names");
		playableGameSelectionName.setMaxWidth(Double.MAX_VALUE);
		playableGameSelectionName.setAlignment(Pos.CENTER);
		playableGameSelectionName.setStyle("-fx-text-fill: #FFFFFF;-fx-font-weight: bold;-fx-font:35 Garamond;-fx-font-weight: bold;");

		playableGameSelectionScene = new Scene(playableGameSelectionPane, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		playableGameSelectionButtonRow = new HBox(30);
		playableGameSelectionButtonRow.setPadding(new Insets(10, 10, 10, 10));

		Image background = new Image(getResource("ca/mcgill/ecse223/block/view/resources/background.jpg"));
		playableGameSelectionPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(SCREEN_WIDTH, SCREEN_HEIGHT, false, false, false, false))));
		
		//Buttons
		playableGameSelectionLogoutButton = new Button("Logout");
		playableGameSelectionLogoutButton.setOnAction(e -> {
			Block223Controller.logout();
			primaryStage.setScene(loginScene);	
		});
		
		playableGameSelectionSelectButton =  new Button("Select Game");
		playableGameSelectionSelectButton.setOnAction(e -> {
			TOPlayableGame pgame = playableGameSelectionList.getSelectionModel().getSelectedItem();
			String name = pgame.getName();
			int id = pgame.getNumber();
			
			try {
				Block223Controller.selectPlayableGame(name, id);
				Block223Page.setPlayScene(primaryStage);
			}
			catch(InvalidInputException iie) {
				playableGameSelectionError.setText(iie.getMessage());
			}
		});

		playableGameSelectionButtonRow.getChildren().addAll(playableGameSelectionLogoutButton, playableGameSelectionSelectButton);
		playableGameSelectionButtonRow.setAlignment(Pos.CENTER);
		
		//List
		playableGameSelectionListData = FXCollections.observableArrayList();
		
		playableGameSelectionList = new TableView<>();
		playableGameSelectionList.setItems(playableGameSelectionListData);
		columnName = new TableColumn<>("Game Name");
		columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnId = new TableColumn<>("ID");
		columnId.setCellValueFactory(new PropertyValueFactory<>("number"));
		columnLevel = new TableColumn<>("Current Level");
		columnLevel.setCellValueFactory(new PropertyValueFactory<>("currentLevel"));
		playableGameSelectionList.getColumns().addAll(columnName, columnId, columnLevel);
		columnName.setMinWidth((SCREEN_WIDTH - playableGameSelectionPane.getPadding().getLeft() - playableGameSelectionPane.getPadding().getRight()) / playableGameSelectionList.getColumns().size());
		columnId.setMinWidth((SCREEN_WIDTH - playableGameSelectionPane.getPadding().getLeft() - playableGameSelectionPane.getPadding().getRight()) / playableGameSelectionList.getColumns().size());
		columnLevel.setMinWidth((SCREEN_WIDTH - playableGameSelectionPane.getPadding().getLeft() - playableGameSelectionPane.getPadding().getRight()) / playableGameSelectionList.getColumns().size()-1);

		playableGameSelectionList.setStyle("-fx-font:18 Garamond; -fx-font-weight: bold;");
		
		playableGameSelectionList.setStyle("-fx-font:18 Garamond; -fx-font-weight: bold;");
		playableGameSelectionList.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2 && e.getButton() == MouseButton.PRIMARY) {
				playableGameSelectionSelectButton.fire();
			}
		});

		//error
		playableGameSelectionError = new Label();
		playableGameSelectionError.setStyle("-fx-text-fill: #DC143C");

		refreshPlayableGameSelection();
		playableGameSelectionPane.getChildren().addAll(playableGameSelectionName,playableGameSelectionList, playableGameSelectionButtonRow, playableGameSelectionError);
		primaryStage.setScene(playableGameSelectionScene);
		playableGameSelectionScene.getStylesheets().add(getResource("ca/mcgill/ecse223/block/view/resources/style.css"));
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
	
	private static void refreshPlayableGameSelection() {
		playableGameSelectionList.getItems().clear();
		try {
			List<TOPlayableGame> toPGames = Block223Controller.getPlayableGames();
			for(TOPlayableGame toPGame : toPGames) {
				playableGameSelectionListData.add(toPGame);
			}
		} catch(InvalidInputException e) {
			e.printStackTrace();
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

	public static double getScreenWidth() {
		return SCREEN_WIDTH;
	}

	public static double getScreenHeight() {
		return SCREEN_HEIGHT;
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