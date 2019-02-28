package ca.mcgill.ecse223.block.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.*;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;

public class Block223Page extends Application{

	private static ChosenBlock chosenBlock;	

	private Label loginError;
	private Label registerError;
	private Label gameSelectionError;

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

	//GAME SELECTION
	private Scene gameSelectionScene;
	private VBox gameSelectionPane;
	private HBox gameSelectionButtonRow;
	private ListView<String> gameSelectionList;
	private ObservableList<String> gameSelectionListData;
	private Button gameSelectionCreateGameButton;
	private Button gameSelectionUpdateGameButton;
	private Scene createGameScene;
	private VBox createGameBox;

	//TOOLBOX
	private Button toolboxButton;
	private VBox toolboxBox;
	private Button toolboxDeleteButton;
	private Button toolboxColorPickerButton;
	private Scene toolboxScene;	
	private static Pane selectedPane = new Pane();

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
		primaryStage.setResizable(false);
		primaryStage.setFullScreen(true);
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
				loginUsernameField.clear();
				loginPasswordField.clear();
				loginError.setText("");
			}
			catch(InvalidInputException iie) {
				loginError.setText(iie.getMessage());
			}

			TOUserMode toUserMode = Block223Controller.getUserMode();

			if(toUserMode.getMode().equals(Mode.Design)) {
				changeToGameSelectionScene(primaryStage);
			}
		});

		loginCreateAccountLabel = new Label("Don't have an account ?");

		createItButton = new Button("Create it here");
		createItButton.setOnAction(e -> {
			primaryStage.setScene(registerScene);
			loginUsernameField.clear();
			loginPasswordField.clear();
			loginError.setText("");
		});

		loginError = new Label();
		loginError.setStyle("-fx-text-fill: #DC143C");

		loginPane.getChildren().addAll(loginUsernameBox, loginPasswordBox, loginButton, loginCreateAccountLabel, createItButton, loginError);

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
		registerUsernamePlayerField.setOnKeyPressed(e -> registerUsernameAdminField.setText(registerUsernamePlayerField.getText()));
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
					registerError.setText("");
				}
				catch(InvalidInputException iie) {
					registerError.setText(iie.getMessage());
				}
			}
			registerError.setText("Password and Confirm Password must be the same");
		});

		registerError = new Label();
		registerError.setStyle("-fx-text-fill: #DC143C");

		registerPane.getChildren().addAll(registerBox, registerButton, registerError);

		Pane pane = new Pane();
		Scene scene = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT);
		buttonPressSound();
		//change the values accordingly ^
		primaryStage.setScene(loginScene);
		primaryStage.show();
		primaryStage.getIcons().add(new Image("ca/mcgill/ecse223/block/view/resources/logo.jpg"));


		/* Louis' comment: Did a first draft of the QUIT label which can be found below.
		 * Let me know if you can find a better style (font, colors, etc.) for it.
		 */
		Label quitLabel = new Label("QUIT");
		quitLabel.setStyle("-fx-font:20 Garamond; -fx-padding:3px; -fx-text-fill: #DC143C; -fx-border-color:black;-fx-background-color:POWDERBLUE;-fx-font-weight:bold");
	}

	private void changeToGameSelectionScene(Stage primaryStage) {

		//SELECTION GAME
		gameSelectionPane = new VBox(20);
		
		gameSelectionScene = new Scene(gameSelectionPane, SCREEN_WIDTH, SCREEN_HEIGHT);

		Image background = new Image(getResource("ca/mcgill/ecse223/block/view/resources/background.jpg"));
		gameSelectionPane.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(0, 0, false, false, true, false))));
		primaryStage.setFullScreen(true);
		//Buttons
		gameSelectionButtonRow = new HBox(20);
		gameSelectionButtonRow.setAlignment(Pos.BOTTOM_CENTER);
		gameSelectionButtonRow.setPadding(new Insets(10, 10, 10, 10));
		gameSelectionCreateGameButton = new Button("Create");

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

		gameSelectionUpdateGameButton = new Button("Update");
		gameSelectionUpdateGameButton.setOnAction(e->{
			try {
				Block223Controller.selectGame(gameSelectionList.getSelectionModel().getSelectedItem());
			} catch (InvalidInputException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			setGameUpdateScene(primaryStage, 20, loginScene);
		});
		gameSelectionButtonRow.getChildren().addAll(gameSelectionCreateGameButton, gameSelectionUpdateGameButton);

		//List
		gameSelectionList = new ListView<String>();
		gameSelectionList.setStyle("-fx-background-color: transparent;");
		gameSelectionPane.setPadding(new Insets(20,20,20,20));
		gameSelectionListData = FXCollections.observableArrayList();

		//error
		gameSelectionError = new Label();
		gameSelectionError.setStyle("-fx-text-fill: #DC143C");

		refreshGameSelection();
		gameSelectionList.setItems(gameSelectionListData);
		gameSelectionPane.getChildren().addAll(gameSelectionList, gameSelectionButtonRow, gameSelectionError);
		primaryStage.setScene(gameSelectionScene);
		primaryStage.setFullScreen(true);
		primaryStage.setResizable(false);
	}


	private void setGameUpdateScene(Stage primaryStage, double spacing, Scene login) {
		LastPageLayoutPane l = new LastPageLayoutPane(primaryStage, spacing, login);
		Image background = new Image(getResource("ca/mcgill/ecse223/block/view/resources/background.jpg"));
		l.setBackground(new Background(new BackgroundImage(background, null, null, null, new BackgroundSize(0, 0, false, false, true, false))));

		primaryStage.setScene(new Scene(l));
		primaryStage.setFullScreen(true);
		primaryStage.setResizable(false);
		
	}
	private void setToolboxPane() {
		Stage toolboxStage = new Stage();
		toolboxBox = new VBox(20);
		Label toolboxLabel = new Label("Block toolbox");
		toolboxLabel.setTranslateX(SCREEN_WIDTH/6);

		//Color picker
		ColorPicker toolboxColorPicker = new ColorPicker();

		//Slider worth
		Slider toolboxWorthSlider = new Slider(1,1000, 500); //need to get min and max from block.java

		//grid of blocks for toolbox
		TilePane blockTilePane = new TilePane();
		blockTilePane.setPadding(new Insets(30, 0, 30, 0));
		blockTilePane.setVgap(20);
		blockTilePane.setHgap(20);
		blockTilePane.setAlignment(Pos.CENTER);
		blockTilePane.setPrefColumns(4);
		try {
			List<TOBlock> toBlocks = Block223Controller.getBlocksOfCurrentDesignableGame();
			for(TOBlock toBlock : toBlocks) {
				//Getting block color for each block
				Color blockColor = new Color(toBlock.getRed(), toBlock.getGreen(), toBlock.getBlue(), 100);
				Pane toBlockPane = new Pane();
				toBlockPane.setBackground(new Background(new BackgroundFill(blockColor, CornerRadii.EMPTY, Insets.EMPTY)));

				//Getting worth and id for each block
				Label toBlockId = new Label(String.valueOf(toBlock.getId()));
				Label toBlockWorth = new Label(String.valueOf(toBlock.getPoints()));

				//Adding color, worth, and id for each block in tile
				blockTilePane.getChildren().addAll(toBlockPane, toBlockId, toBlockWorth);

				//Adding shadow to selected block in toolbox
				toBlockPane.setOnMouseClicked(ciao -> {
					selectedPane.setEffect(null);
					DropShadow dropShadow = new DropShadow();
					dropShadow.setRadius(10);
					dropShadow.setColor(Color.CHARTREUSE);
					toBlockPane.setEffect(dropShadow);
					selectedPane = toBlockPane;


				}
						);
				//change selected block color
				toolboxColorPicker.setOnMouseClicked(colorEvent -> {
					BackgroundFill slectedColor = new BackgroundFill(toolboxColorPicker.getValue(),CornerRadii.EMPTY, Insets.EMPTY);
					toBlockPane.setBackground(new Background(slectedColor));
				});

				//change selected block worth
				toolboxWorthSlider.setOnMouseDragged(worthEvent -> {
					toolboxWorthSlider.getValue();
				});

			}
		} catch (InvalidInputException e1) {
			e1.printStackTrace();
		}

		toolboxDeleteButton = new Button("Delete block");
		toolboxColorPickerButton = new Button("Select block color");
		toolboxBox.getChildren().addAll(blockTilePane, toolboxDeleteButton, toolboxColorPickerButton);

		toolboxScene = new Scene(toolboxBox, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 6);
		toolboxStage.setScene(toolboxScene);
		toolboxStage.show();
		toolboxStage.setAlwaysOnTop(true);
		toolboxStage.setResizable(false);
		toolboxButton.setDisable(true);
	}


	private void refreshGameSelection() {
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
		sound.setCycleCount(1);
		sound.stop();
		sound.play();
		sound.setVolume(0.5);

	}

	public static ChosenBlock getChosenBlock() {
		return chosenBlock;
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