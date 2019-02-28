package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOConstant;
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

	private static final int OUTLINE_WIDTH = 3;
	
	public DesignGidPane(int level, LastPageLayoutPane lastPageLayoutPane) {
		TOConstant toConstants = Block223Controller.getConstants();
		
		this.setPrefSize(390, 390);
		
		GridPane grid = new GridPane();
		grid.setHgap(toConstants.getColumnsPadding());
		grid.setVgap(toConstants.getRowPadding());
		grid.setPadding(new Insets(toConstants.getWallPadding()));

		int initialX;
		int initialY;
		
		for (int i = 0; i < toConstants.getMaxHorizontalBlocks(); i++) {
			for (int j = 0; j < toConstants.getMaxVerticalBlocks(); j++) {
				Rectangle blockBox = new Rectangle();
				blockBox.setWidth(toConstants.getSize());
				blockBox.setHeight(toConstants.getSize());
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
