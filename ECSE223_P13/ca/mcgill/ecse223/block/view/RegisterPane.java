package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterPane extends VBox {

	private Label registerError;
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
	private HBox buttonBox;
	private Button registerButton;
	private Button backLoginButton;

	public RegisterPane(Stage primaryStage) {

		this.setAlignment(Pos.CENTER);
		this.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");

		HBox registerBox = new HBox(10);
		registerBox.setAlignment(Pos.CENTER);

		VBox registerPlayerBox = new VBox(10);
		registerPlayerAccountLabel = new Label("Player Account");
		HBox registerUsernamePlayer = new HBox(10);
		registerUsernamePlayerLabel = new Label("Username : ");
		registerUsernamePlayerField = new TextField();
		registerUsernamePlayerField.setOnKeyReleased(e -> registerUsernameAdminField.setText(registerUsernamePlayerField.getText()));
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
		
		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.CENTER);
		
		registerButton = new Button("Register");
		registerButton.setOnAction(e -> {			
			if(registerPasswordPlayerField.getText().equals(registerConfirmPasswordPlayerField.getText()) && registerPasswordAdminField.getText().equals(registerConfirmPasswordAdminField.getText())) {
				try {
					Block223Controller.register(registerUsernamePlayerField.getText(), registerPasswordPlayerField.getText(), registerPasswordAdminField.getText());
					primaryStage.setScene(Block223Page.getLoginScene());
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
		
		backLoginButton = new Button("Back to Login Screen");
		backLoginButton.setOnAction(e -> {
			primaryStage.setScene(Block223Page.getLoginScene());
		});
		buttonBox.getChildren().addAll(registerButton, backLoginButton);

		registerError = new Label();
		registerError.setStyle("-fx-text-fill: #DC143C");

		this.getChildren().addAll(registerBox, buttonBox, registerError);
	}
}
