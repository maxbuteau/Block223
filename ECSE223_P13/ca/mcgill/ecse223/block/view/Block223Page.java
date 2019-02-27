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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.util.List;

import ca.mcgill.ecse223.block.controller.*;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;

public class Block223Page extends Application{

	private Label error;

	//LOGIN
	private Scene loginScene;
	private Label loginUsernameLabel;
	private TextField loginUsernameField;
	private Label loginPasswordLabel;
	private PasswordField loginPasswordField;
	private Label loginCreateAccountLabel;
	private Button loginButton;
	private Button createItButton;

	//REGISTER
	private Scene registerScene;
	private Label registerPlayerAccountLabel;
	private Label registerAdminAccountLabel;
	private Label registerUsernamePlayerLabel;
	private TextField registerUsernamePlayerField;
	private Label registerUsernameAdminLabel;
	private TextField registerUsernameAdminField;
	private Label registerPasswordPlayerLabel;
	private PasswordField registerPasswordPlayerField;
	private Label registerPasswordAdminLabel;
	private PasswordField registerPasswordAdminField;
	private Label registerConfirmPasswordPlayerLabel;
	private PasswordField registerConfirmPasswordPlayerField;
	private Label registerConfirmPasswordAdminLabel;
	private PasswordField registerConfirmPasswordAdminField;
	private Button registerButton;

	// Page Selection page (create, update, delete games)
	private Scene gameSelectionScene;
	private VBox gameSelectionPane;
	private HBox gameSelectionButtonRow;

	private ListView<String> gameSelectionList;
	private ObservableList<String> gameSelectionListData;

	private Button gameSelectionCreateGameButton;
	private Button gameSelectionDeleteGameButton;
	private Button gameSelectionUpdateGameButton;

	private Scene createGameScene;
	private VBox createGameBox;

	private final double SCREEN_WIDTH = 500; // to be changed
	private final double SCREEN_HEIGHT = 500; // to be changed

	private static Media soundMedia = new Media(getResource("ca/mcgill/ecse223/block/view/resources/click.mp3"));
	private static MediaPlayer sound = new MediaPlayer(soundMedia);

	public static void main(String[] args) {
		Application.launch(args);

	}
	@Override
	public void start(Stage primaryStage) throws Exception {	
		Image background = new Image(getResource("ca/mcgill/ecse223/block/view/resources/background.jpg"));

		//LOGIN SCENE
		VBox loginPane = new VBox(20);	
		loginPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(0, 0, false, false, true, false))));
		loginPane.setAlignment(Pos.CENTER);
		loginPane.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");
		loginScene = new Scene(loginPane, SCREEN_WIDTH, SCREEN_HEIGHT);

		HBox loginUsernameBox = new HBox(10);
		loginUsernameBox.setAlignment(Pos.CENTER);
		loginUsernameLabel = new Label("Username : ");
		loginUsernameField =  new TextField();
		loginUsernameBox.getChildren().addAll(loginUsernameLabel, loginUsernameField);

		HBox loginPasswordBox = new HBox(10);
		loginPasswordBox.setAlignment(Pos.CENTER);
		loginPasswordLabel = new Label("Password : ");
		loginPasswordField = new PasswordField();
		loginPasswordBox.getChildren().addAll(loginPasswordLabel, loginPasswordField);

		loginButton = new Button("Login");
		loginButton.setOnAction(e -> {
			try {
				Block223Controller.login(loginUsernameField.getText(), loginPasswordField.getText());
			}
			catch(InvalidInputException iie) {
				error.setText(iie.getMessage());
			}

			TOUserMode toUserMode = Block223Controller.getUserMode();

			if(toUserMode.getMode().equals(Mode.Design)) {
				//primaryStage.setScene(gameSelectionScene);
			}
			loginUsernameField.clear();
			loginPasswordField.clear();
			error.setText("");
		});

		loginCreateAccountLabel = new Label("Don't have an account ?");

		createItButton = new Button("Create it here");
		createItButton.setOnAction(e -> {
			primaryStage.setScene(registerScene);
			loginUsernameField.clear();
			loginPasswordField.clear();
			error.setText("");
		});

		error = new Label();
		error.setStyle("-fx-text-fill: #DC143C");

		loginPane.getChildren().addAll(loginUsernameBox, loginPasswordBox, loginButton, loginCreateAccountLabel, createItButton, error);

		//REGISTER
		VBox registerPane = new VBox(20);
		registerPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(0, 0, false, false, true, false))));
		registerPane.setAlignment(Pos.CENTER);
		registerPane.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");
		registerScene = new Scene(registerPane, SCREEN_WIDTH, SCREEN_HEIGHT);

		HBox registerBox = new HBox(10);
		registerBox.setAlignment(Pos.CENTER);

		VBox registerPlayerBox = new VBox(10);
		registerPlayerAccountLabel = new Label("Player Account");
		HBox registerUsernamePlayer = new HBox(10);
		registerUsernamePlayerLabel = new Label("Username : ");
		registerUsernamePlayerField = new TextField();
		registerUsernamePlayerField.setOnKeyTyped(e -> registerUsernameAdminField.setText(registerUsernamePlayerField.getText()));
		registerUsernamePlayer.getChildren().addAll(registerUsernamePlayerLabel, registerUsernamePlayerField);
		HBox registerPasswordPlayer = new HBox(10);
		registerPasswordPlayerLabel = new Label("Password : ");
		registerPasswordPlayerField = new PasswordField();
		registerPasswordPlayer.getChildren().addAll(registerPasswordPlayerLabel, registerPasswordPlayerField);
		HBox registerConfirmPasswordPlayer = new HBox(10);
		registerConfirmPasswordPlayerLabel = new Label("Confirm\nPassword : ");
		registerConfirmPasswordPlayerField = new PasswordField();
		registerConfirmPasswordPlayer.getChildren().addAll(registerConfirmPasswordPlayerLabel, registerConfirmPasswordPlayerField);
		registerPlayerBox.getChildren().addAll(registerPlayerAccountLabel, registerUsernamePlayer, registerPasswordPlayer, registerConfirmPasswordPlayer);
		VBox registerAdminBox = new VBox(10);
		registerAdminAccountLabel = new Label("Admin Account");
		HBox registerUsernameAdmin = new HBox(10);
		registerUsernameAdminLabel = new Label("Username : ");
		registerUsernameAdminField = new TextField();
		registerUsernameAdminField.setEditable(false);
		registerUsernameAdmin.getChildren().addAll(registerUsernameAdminLabel, registerUsernameAdminField);
		HBox registerPasswordAdmin = new HBox(10);
		registerPasswordAdminLabel = new Label("Password : ");
		registerPasswordAdminField = new PasswordField();
		registerPasswordAdmin.getChildren().addAll(registerPasswordAdminLabel, registerPasswordAdminField);
		HBox registerConfirmPasswordAdmin = new HBox(10);
		registerConfirmPasswordAdminLabel = new Label("Confirm\nPassword : ");
		registerConfirmPasswordAdminField = new PasswordField();
		registerConfirmPasswordAdmin.getChildren().addAll(registerConfirmPasswordAdminLabel, registerConfirmPasswordAdminField);
		registerAdminBox.getChildren().addAll(registerAdminAccountLabel, registerUsernameAdmin, registerPasswordAdmin, registerConfirmPasswordAdmin);	

		registerBox.getChildren().addAll(registerPlayerBox, registerAdminBox);
		registerButton = new Button("Register");
		registerButton.setOnAction(e -> {			
			if(registerPasswordPlayerField.getText().equals(registerConfirmPasswordPlayerField.getText()) && registerPasswordAdminField.getText().equals(registerConfirmPasswordAdminField.getText())) {
				try {
					Block223Controller.register(registerUsernamePlayerField.getText(), registerPasswordPlayerField.getText(), registerPasswordAdminField.getText());
					primaryStage.setScene(loginScene);
					registerUsernamePlayerField.clear();
					registerUsernameAdminField.clear();
					registerPasswordPlayerField.clear();
					registerPasswordAdminField.clear();
					registerConfirmPasswordPlayerField.clear();
					registerConfirmPasswordAdminField.clear();
					error.setText("");
				}
				catch(InvalidInputException iie) {
					error.setText(iie.getMessage());
				}
			}
			error.setText("Password and Confirm Password must be the same");
		});

		registerPane.getChildren().addAll(registerBox, registerButton, error);

		// GAME SELECTION PANE
		gameSelectionPane = new VBox(20);
		gameSelectionScene = new Scene(gameSelectionPane, SCREEN_WIDTH, SCREEN_HEIGHT);

		// GAME SELECTION BUTTONS
		gameSelectionButtonRow = new HBox(20);
		gameSelectionButtonRow.setAlignment(Pos.BOTTOM_CENTER);
		gameSelectionButtonRow.setPadding(new Insets(10, 10, 10, 10));
		gameSelectionCreateGameButton = new Button("Create");
		gameSelectionDeleteGameButton = new Button("Delete");
		gameSelectionUpdateGameButton = new Button("Update");
		gameSelectionButtonRow.getChildren().addAll(gameSelectionCreateGameButton, gameSelectionUpdateGameButton,
				gameSelectionDeleteGameButton);

		// LIST OF GAMES
		gameSelectionList = new ListView<String>();
		gameSelectionListData = FXCollections.observableArrayList();
		try {
			List<TOGame> toGames = Block223Controller.getDesignableGames();
			for (TOGame toGame : toGames) {
				String toGameName = toGame.getName();
				gameSelectionListData.add(toGameName);
			}
		} catch (InvalidInputException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		gameSelectionList.setItems(gameSelectionListData);
		gameSelectionPane.getChildren().addAll(gameSelectionList, gameSelectionButtonRow);

		// CREATE GAME POPUP
		gameSelectionCreateGameButton.setOnAction(e -> {
			Stage createGameStage = new Stage();
			createGameBox = new VBox(20);
			Label createGameNameLabel = new Label("Game name : ");
			createGameNameLabel.setTranslateX(SCREEN_WIDTH / 6);
			TextField createGameNameField = new TextField();

			createGameNameField.setOnKeyPressed(neverUnlucky -> {
				if(neverUnlucky.getCode().equals(KeyCode.ENTER)) {
					try {
						createGameStage.close();
						Block223Controller.createGame(createGameNameField.getText());
					} catch (InvalidInputException e1) {
						error.setText(e1.getMessage());
					}
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


			Pane pane = new Pane();
			Scene scene = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT);
			buttonPressSound();
			//change the values accordingly ^
			primaryStage.setScene(loginScene);
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.getIcons().add(new Image("ca/mcgill/ecse223/block/view/resources/logo.jpg"));


			/* Louis' comment: Did a first draft of the QUIT label which can be found below.
			 * Let me know if you can find a better style (font, colors, etc.) for it.
			 */
			Label quitLabel = new Label("QUIT");
			quitLabel.setStyle("-fx-font:20 Garamond; -fx-padding:3px; -fx-text-fill: #DC143C; -fx-border-color:black;-fx-background-color:POWDERBLUE;-fx-font-weight:bold");
	}

	public static String getResource(String res)
	{
		return ClassLoader.getSystemResource(res).toString();
	}

	public static void buttonPressSound() {
		sound.setCycleCount(1);
		sound.stop();
		sound.play();
		sound.setVolume(0.5);

	}
}
