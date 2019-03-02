package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOUserMode;
import ca.mcgill.ecse223.block.controller.TOUserMode.Mode;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPane extends VBox{

	//LOGIN
	private Label loginError;

	private Scene loginScene;
	private Label loginUsernameLabel;
	private TextField loginUsernameField;
	private Label loginPasswordLabel;
	private PasswordField loginPasswordField;
	private Label loginCreateAccountLabel;
	private Button loginButton;
	private Button createItButton;

	public LoginPane(Stage primaryStage, Scene gameSelectionScene, Scene registerScene) {
		this.setAlignment(Pos.CENTER);
		this.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");

		HBox loginUsernameBox = new HBox(10);
		loginUsernameBox.setAlignment(Pos.CENTER);
		loginUsernameLabel = new Label("Username : ");
		loginUsernameField =  new TextField();
		loginUsernameBox.getChildren().addAll(loginUsernameLabel, loginUsernameField);

		HBox loginPasswordBox = new HBox(10);
		loginPasswordBox.setAlignment(Pos.CENTER);
		loginPasswordLabel = new Label("Password : ");
		loginPasswordField = new PasswordField();
		loginPasswordField.setOnKeyPressed(e -> {
			if(e.getCode().equals(KeyCode.ENTER)) {
				loginButton.fire();
			}
		});
		loginPasswordBox.getChildren().addAll(loginPasswordLabel, loginPasswordField);

		loginButton = new Button("Login");
		loginButton.setOnAction(e -> {
			try {
				Block223Controller.login(loginUsernameField.getText(), loginPasswordField.getText());
				loginPasswordField.clear();
				loginUsernameField.clear();
				loginError.setText("");
			}
			catch(InvalidInputException iie) {
				loginError.setText(iie.getMessage());
			}

			TOUserMode toUserMode = Block223Controller.getUserMode();

			if(toUserMode.getMode().equals(Mode.Design)) {
				Block223Page.changeToGameSelectionScene(primaryStage);
			}
		});

		loginCreateAccountLabel = new Label("Don't have an account?");

		createItButton = new Button("Create it here");
		createItButton.setOnAction(e -> {
			primaryStage.setScene(registerScene);
			loginUsernameField.clear();
			loginPasswordField.clear();
			loginError.setText("");
		});

		loginError = new Label();
		loginError.setStyle("-fx-text-fill: #DC143C");

		this.getChildren().addAll(loginUsernameBox, loginPasswordBox, loginButton, loginCreateAccountLabel, createItButton, loginError);

	}
}
