package ca.mcgill.ecse223.block.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Block223Page extends Application{

	private static Scene gameSelectionScene;
	private static Scene loginScene;
	private static Scene updateGameScene;
	private final double SCREEN_WIDTH = 500; // to be changed
	private final double SCREEN_HEIGHT = 500; // to be changed
	public static void main(String[] args) {
		Application.launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();
		Scene scene = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT);
		//change the values accordingly ^
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
		
		/* Louis' comment: Did a first draft of the QUIT label which can be found below.
		 * Let me know if you can find a better style (font, colors, etc.) for it.
		 */
		Label quitLabel = new Label("QUIT");
		quitLabel.setStyle("-fx-font:20 Garamond; -fx-padding:3px; -fx-text-fill: #DC143C; -fx-border-color:black;-fx-background-color:POWDERBLUE;-fx-font-weight:bold");
	}

}
