package ca.mcgill.ecse223.block.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.*;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;

public class Block223Page extends Application{
	
	//LOGIN
	Scene loginScene;
	Label loginLabel;
	Label loginUsernameLabel;
	TextField loginUsernameField;
	Label loginPasswordLabel;
	PasswordField loginPasswordField;
	Label loginCreateAccountLabel;
	Button loginButton;
	Button createItButton;
	Label loginError;
	
	//REGISTER
	Scene registerScene;
	Label registerLabel;
	Label registerPlayerAccountLabel;
	Label registerAdminAccountLabel;
	Label registerUsernamePlayerLabel;
	TextField registerUsernamePlayerField;
	Label registerUsernameAdminLabel;
	TextField registerUsernameAdminField;
	Label registerPasswordPlayerLabel;
	PasswordField registerPasswordPlayerField;
	Label registerPasswordAdminLabel;
	PasswordField registerPasswordAdminField;
	Label registerConfirmPasswordPlayerLabel;
	PasswordField registerConfirmPasswordPlayerField;
	Label registerConfirmPasswordAdminLabel;
	PasswordField registerConfirmPasswordAdminField;
	Button registerButton;
	Label registerError;
	

	private final double SCREEN_WIDTH = 500; // to be changed
	private final double SCREEN_HEIGHT = 500; // to be changed
	public static void main(String[] args) {
		Application.launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {			
		//LOGIN SCENE
		VBox loginPane = new VBox(20);
		loginPane.setAlignment(Pos.CENTER);
		loginScene = new Scene(loginPane, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		loginLabel = new Label("LOGIN");
		loginLabel.setStyle("-fx-font:100 Garamond; -fx-font-weight:bold");
		
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
				loginError.setText(iie.getMessage());
			}
			
			TOUserMode toUserMode = Block223Controller.getUserMode();

			if(toUserMode.getMode().equals(Mode.Design)) {
				//primaryStage.setScene(gameSelectionScene);
			}
			loginUsernameField.clear();
			loginPasswordField.clear();
			loginError.setText("");
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
		
		loginPane.getChildren().addAll(loginLabel, loginUsernameBox, loginPasswordBox, loginButton, loginCreateAccountLabel, createItButton, loginError);
		
		//REGISTER
		VBox registerPane = new VBox(20);
		registerPane.setAlignment(Pos.CENTER);
		registerScene = new Scene(registerPane, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		registerLabel = new Label("REGISTER");
		registerLabel.setStyle("-fx-font:100 Garamond; -fx-font-weight:bold");
		
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
					registerError.setText("");
				}
				catch(InvalidInputException iie) {
					registerError.setText(iie.getMessage());
				}
			}
		});
		
		registerError = new Label();
		registerError.setStyle("-fx-text-fill: #DC143C");
		
		registerPane.getChildren().addAll(registerLabel, registerBox, registerButton, registerError);
		
		Pane pane = new Pane();
		Scene scene = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT);
		//change the values accordingly ^
		primaryStage.setScene(loginScene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
		
		/* Louis' comment: Did a first draft of the QUIT label which can be found below.
		 * Let me know if you can find a better style (font, colors, etc.) for it.
		 */
		Label quitLabel = new Label("QUIT");
		quitLabel.setStyle("-fx-font:20 Garamond; -fx-padding:3px; -fx-text-fill: #DC143C; -fx-border-color:black;-fx-background-color:POWDERBLUE;-fx-font-weight:bold");
	}

}