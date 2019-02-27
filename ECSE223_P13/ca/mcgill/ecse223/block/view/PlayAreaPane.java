package ca.mcgill.ecse223.block.view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class PlayAreaPane extends Pane{

	private GridPane grid;
	private Pane paddle;
	PlayAreaPane(int level, int minPaddle, int maxPaddle, int nrLevels){
		this.setPrefSize(400, 400);
		paddle = new Pane();
		paddle.setPrefSize(minPaddle+(maxPaddle - minPaddle)/nrLevels*level, 5);
	}
}
