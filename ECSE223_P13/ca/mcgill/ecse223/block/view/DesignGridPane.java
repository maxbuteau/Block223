package ca.mcgill.ecse223.block.view;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOConstant;
import ca.mcgill.ecse223.block.controller.TOGridCell;
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

public class DesignGridPane extends GridPane {

	private static final int OUTLINE_WIDTH = 3;
	private static int initialX;
	private static int initialY;
	private static TOConstant toConstants;
	private static LastPageLayoutPane lastPageLayoutPane;
	private static int level;
	private static DesignGridPane designGridPane;
	
	public DesignGridPane(int level, LastPageLayoutPane lastPageLayoutPane) {
		toConstants = Block223Controller.getConstants();
		this.lastPageLayoutPane = lastPageLayoutPane;
		this.level = level;
		designGridPane = this;
		
		this.setPrefSize(toConstants.getPlayAreaSide(), toConstants.getPlayAreaSide());
		this.setHgap(toConstants.getColumnsPadding());
		this.setVgap(toConstants.getRowPadding());
		this.setPadding(new Insets(toConstants.getWallPadding()));
		
		displayGrid();
		refresh();
	}
	
	public static void refresh() {
		designGridPane.getChildren().clear();
		displayGrid();
		List<TOGridCell> gridCells = new ArrayList<TOGridCell>();
		try {
			gridCells = Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(level);
		} catch (InvalidInputException e) {
			lastPageLayoutPane.setErrorMessage(e.getMessage());
		}
		
		for(TOGridCell gridCell : gridCells) {
			int x = gridCell.getGridHorizontalPosition() - 1;
			int y = gridCell.getGridVerticalPosition() - 1;
			Color blockColor = new Color(
					(double)gridCell.getRed()/toConstants.getMaxColor(),
					(double)gridCell.getGreen()/toConstants.getMaxColor(),
					(double)gridCell.getBlue()/toConstants.getMaxColor(),
					1);

			Pane block = (Pane) designGridPane.getChildren().get(y*toConstants.getMaxVerticalBlocks()+x);
			block.setOpacity(1);
			block.setStyle("-fx-background-color: #"+blockColor.toString().substring(2, 8)+";");
			
			block.setOnDragDetected(e -> {
				int xx = designGridPane.getRowIndex(block);
				int yy = designGridPane.getColumnIndex(block);
				initialX = xx+1;
				initialY = yy+1;
				block.startFullDrag();
			});
					
			block.setOnMouseDragged(e -> {
				block.setTranslateX(e.getX());
				block.setTranslateY(e.getY());
			});
			
			block.setOnMouseDragReleased(e -> {
				int xx = designGridPane.getRowIndex(block);
				int yy = designGridPane.getColumnIndex(block);
				try {
					Block223Controller.moveBlock(designGridPane.level, initialX, initialY, xx+1, yy+1);
				} catch (InvalidInputException e1) {
					lastPageLayoutPane.setErrorMessage(e1.getMessage());
				}
				refresh();
			});
		}
		


	}
	
	public static void displayGrid() {
		for (int i = 0; i < toConstants.getMaxHorizontalBlocks(); i++) {
			for (int j = 0; j < toConstants.getMaxVerticalBlocks(); j++) {
				Pane blockBox = new Pane();
				blockBox.setStyle("-fx-background-color: #FFFFFF;");
				blockBox.setOpacity(0.5);
				blockBox.setPrefSize(toConstants.getSize(), toConstants.getSize());
				designGridPane.add(blockBox, i, j);
				
				blockBox.setOnMouseClicked(e -> {		
					if(e.getButton() == MouseButton.PRIMARY) {
						ChosenBlock chosenBlock = Block223Page.getChosenBlock();
						if (chosenBlock != null) {
							int x = designGridPane.getRowIndex(blockBox);
							int y = designGridPane.getColumnIndex(blockBox);
							
							try {
								Block223Controller.positionBlock(chosenBlock.getId(), designGridPane.level, x+1, y+1);
							} catch (InvalidInputException e1) {
								lastPageLayoutPane.setErrorMessage(e1.getMessage());
							}
							refresh();
						}
					}
					
					if(e.getButton() == MouseButton.SECONDARY) {
						int x = designGridPane.getRowIndex(blockBox);
						int y = designGridPane.getColumnIndex(blockBox);
						try {
							Block223Controller.removeBlock(designGridPane.level, x+1, y+1);
						} catch (InvalidInputException e1) {
							lastPageLayoutPane.setErrorMessage(e1.getMessage());
						}
						refresh();
					}
				});
				
				
			}
		}
	}
}