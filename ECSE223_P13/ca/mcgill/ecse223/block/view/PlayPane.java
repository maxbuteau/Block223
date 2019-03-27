package ca.mcgill.ecse223.block.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PlayPane extends BorderPane implements Block223PlayModeInterface {
	private static Pane playArea;
	
	public PlayPane(Stage primaryStage) {
		playArea = new Pane();
		
		displayPlayArea();
	}
	
	public static void displayPlayArea() {
		
	}
	
	@Override
	public String takeInputs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
