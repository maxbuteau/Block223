package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Ball;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Paddle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DesignGidPane extends GridPane {

	private static final int MAX_HORIZONTAL_BLOCKS = (1+(Game.PLAY_AREA_SIDE-2*Game.WALL_PADDING-Block.SIZE)/(Block.SIZE+Game.COLUMNS_PADDING));
	private static final int MAX_VERTICAL_BLOCKS = (1+(Game.PLAY_AREA_SIDE-Paddle.VERTICAL_DISTANCE-Game.WALL_PADDING-Paddle.PADDLE_WIDTH-Ball.BALL_DIAMETER-Block.SIZE)/(Block.SIZE+Game.ROW_PADDING));
	private static final int OUTLINE_WIDTH = 3;
	
	public DesignGidPane(int level, LastPageLayoutPane lastPageLayoutPane) {
		this.setPrefSize(390, 390);
		
		GridPane grid = new GridPane();
		grid.setHgap(Game.COLUMNS_PADDING);
		grid.setVgap(Game.ROW_PADDING);
		grid.setPadding(new Insets(Game.WALL_PADDING));

		int initialX;
		int initialY;
		
		for (int i = 0; i < MAX_HORIZONTAL_BLOCKS; i++) {
			for (int j = 0; j < MAX_VERTICAL_BLOCKS; j++) {
				Rectangle blockBox = new Rectangle();
				blockBox.setWidth(Block.SIZE);
				blockBox.setHeight(Block.SIZE);
				blockBox.setFill(Color.WHITE);
				blockBox.setStroke(Color.BLACK);
				blockBox.setStrokeWidth(OUTLINE_WIDTH);
				grid.add(blockBox, i, j);
				blockBox.setOnMouseClicked(e -> {		
					if(e.getButton() == MouseButton.PRIMARY) {
						ChosenBlock chosenBlock = Block223Page.getChosenBlock();
						if (chosenBlock != null) {
							Object o = e.getSource();
							Rectangle clickedRectangle = (Rectangle) o;
							int x = grid.getRowIndex(clickedRectangle);
							int y = grid.getColumnIndex(clickedRectangle);
							try {
								Block223Controller.positionBlock(chosenBlock.getId(), level, x+1, y+1);
							} catch (InvalidInputException e1) {
								lastPageLayoutPane.setErrorMessage(e1.getMessage());
							}
						}
					}
					
					if(e.getButton() == MouseButton.SECONDARY) {
						Object o = e.getSource();
						Rectangle clickedRectangle = (Rectangle) o;
						int x = grid.getRowIndex(clickedRectangle);
						int y = grid.getColumnIndex(clickedRectangle);
						try {
							Block223Controller.removeBlock(level, x+1, y+1);
						} catch (InvalidInputException e1) {
							lastPageLayoutPane.setErrorMessage(e1.getMessage());
						}
					}
				});
				blockBox.setOnDragDetected(e -> {
					Object o = e.getSource();
					Rectangle clickedRectangle = (Rectangle) o;
					int x = grid.getRowIndex(clickedRectangle);
					int y = grid.getColumnIndex(clickedRectangle);
					initialX = x+1;
					initialY = y+1;
				});
				
				blockBox.setOnDragDropped(e -> {
					Object o = e.getSource();
					Rectangle clickedRectangle = (Rectangle) o;
					int x = grid.getRowIndex(clickedRectangle);
					int y = grid.getColumnIndex(clickedRectangle);
					try {
						Block223Controller.moveBlock(level, initialX, initialY, x, y);
					} catch (InvalidInputException e1) {
						lastPageLayoutPane.setErrorMessage(e1.getMessage());
					}
				});
			}
		}
	}
}
