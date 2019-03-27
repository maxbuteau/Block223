package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class PublishView extends Pane {
	
	private static Label publishCheck;
	private static Button yes;
	private static Button no;
	private static GridPane publishGrid;
	private static Label publishError;
	
	public PublishView() {
		publishCheck = new Label("Are you sure you want to publish the game "+Block223Application.getCurrentGame().getName()+" ? A published game cannot be edited anymore!");
		yes = new Button("Yes");
		no = new Button("No");
		publishError = new Label();
		publishError.setStyle("-fx-text-fill: #DC143C");
		
		yes.setOnAction(e->{
			try {
				Block223Controller.publishGame();
			} catch (InvalidInputException e1) {
				publishError.setText(e1.getMessage());
			}
		});
		
		publishGrid = new GridPane();
		
		publishGrid.addColumn(0, publishCheck, yes, no, publishError);
		this.getChildren().add(publishGrid);
	}

}
