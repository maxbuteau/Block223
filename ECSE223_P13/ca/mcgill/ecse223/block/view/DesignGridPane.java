package ca.mcgill.ecse223.block.view;

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
	private int initialX = 0;
	private int initialY = 0;
	private TOConstant toConstants;
	private LastPageLayoutPane lastPageLayoutPane;
	private int level;
	private boolean onDrag = false;
	
	public DesignGridPane(int level, LastPageLayoutPane lastPageLayoutPane) {
		toConstants = Block223Controller.getConstants();
		this.lastPageLayoutPane = lastPageLayoutPane;
		this.level = level;
		
		this.setPrefSize(toConstants.getPlayAreaSide(), toConstants.getPlayAreaSide());
		this.setHgap(toConstants.getColumnsPadding());
		this.setVgap(toConstants.getRowPadding());
		this.setPadding(new Insets(toConstants.getWallPadding()));
		
		displayGrid();
	}
	
	private void refresh() {
		this.getChildren().clear();
		displayGrid();
		List<TOGridCell> gridCells = null;
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
			
			Pane block = (Pane) this.getChildren().get(y*toConstants.getMaxVerticalBlocks()+x);
			block.setOpacity(1);
			block.setStyle("-fx-background-color: #"+blockColor.toString().substring(2, 8)+";");
		}
	}
	
	private void displayGrid() {
		for (int i = 0; i < toConstants.getMaxHorizontalBlocks(); i++) {
			for (int j = 0; j < toConstants.getMaxVerticalBlocks(); j++) {
				Pane blockBox = new Pane();
				blockBox.setStyle("-fx-background-color: #FFFFFF;");
				blockBox.setOpacity(0.5);
				blockBox.setPrefSize(toConstants.getSize(), toConstants.getSize());
				this.add(blockBox, i, j);
				
				blockBox.setOnMouseClicked(e -> {		
					if(e.getButton() == MouseButton.PRIMARY) {
						ChosenBlock chosenBlock = Block223Page.getChosenBlock();
						if (chosenBlock != null) {
							Object o = e.getSource();
							Pane clickedPane = (Pane) o;
							int x = this.getRowIndex(clickedPane);
							int y = this.getColumnIndex(clickedPane);
							
							try {
								Block223Controller.positionBlock(chosenBlock.getId(), this.level, x+1, y+1);
							} catch (InvalidInputException e1) {
								lastPageLayoutPane.setErrorMessage(e1.getMessage());
							}
							refresh();
						}
					}
					
					if(e.getButton() == MouseButton.SECONDARY) {
						Object o = e.getSource();
						Pane clickedPane = (Pane) o;
						int x = this.getRowIndex(clickedPane);
						int y = this.getColumnIndex(clickedPane);
						try {
							Block223Controller.removeBlock(this.level, x+1, y+1);
						} catch (InvalidInputException e1) {
							lastPageLayoutPane.setErrorMessage(e1.getMessage());
						}
						refresh();
					}
				});
				
				blockBox.setOnDragDetected(e -> {
					Object o = e.getSource();
					Pane clickedPane = (Pane) o;
					int x = this.getRowIndex(clickedPane);
					int y = this.getColumnIndex(clickedPane);
					initialX = x+1;
					initialY = y+1;
					onDrag = true;
				});
				
				blockBox.setOnMouseReleased(e -> {
					if(onDrag) {
						Object o = e.getSource();
						Pane clickedPane = (Pane) o;
						int x = this.getRowIndex(clickedPane);
						int y = this.getColumnIndex(clickedPane);
						try {
							Block223Controller.moveBlock(this.level, initialX, initialY, x+1, y+1);
							System.out.println(initialX);
							System.out.println(initialY);
							System.out.println(x);
							System.out.println(y);
						} catch (InvalidInputException e1) {
							lastPageLayoutPane.setErrorMessage(e1.getMessage());
						}
						refresh();
						onDrag = false;
					}
				});
			}
		}
	}
}