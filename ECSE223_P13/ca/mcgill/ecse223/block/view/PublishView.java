package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PublishView extends VBox {
	
	private static Label publishCheck;
	private static Button yes;
	private static Button no;
	private static VBox outerVBox;
	private static HBox yesNoHBox;
	private static Label publishError;
	
	private static final int V_GAP = 10;
	private static final int H_GAP = 10;
	
	public PublishView(Stage primaryStage) {
		publishCheck = new Label("Are you sure you want to publish the game "+Block223Application.getCurrentGame().getName()+" ? A published game cannot be edited anymore!");
		yes = new Button("Publish");
		no = new Button("Cancel");
		publishError = new Label();
		publishError.setStyle("-fx-text-fill: #DC143C");
		
		yes.setOnAction(e->{
			try {
				Block223Controller.publishGame();
				LastPageLayoutPane.closePublishStage();
				LastPageLayoutPane.pressSave();
				Block223Page.changeToGameSelectionScene(primaryStage);
			} catch (InvalidInputException e1) {
				publishError.setText(e1.getMessage());
			}
		});
		
		no.setOnAction(e->{
			LastPageLayoutPane.closePublishStage();
			Block223Page.setGameOverScene(primaryStage);
		});
		
		yesNoHBox = new HBox(H_GAP);
		yesNoHBox.setAlignment(Pos.CENTER);
		yesNoHBox.getChildren().addAll(yes, no);
		
		outerVBox = new VBox(V_GAP);
		outerVBox.setAlignment(Pos.CENTER);
		outerVBox.getChildren().addAll(publishCheck, yesNoHBox, publishError);
		this.getChildren().add(outerVBox);
	}

}
