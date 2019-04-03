package ca.mcgill.ecse223.block.view;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOConstant;
import ca.mcgill.ecse223.block.controller.TOGridCell;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class DesignGridPane extends Pane {

	private static int initialX;
	private static int initialY;
	private static TOConstant toConstants;
	private static LastPageLayoutPane lastPageLayoutPane;
	private static int level;
	private static GridPane designGridPane;
	private static DesignGridPane designPane;

	public DesignGridPane(int level, LastPageLayoutPane lastPageLayoutPane) {
		toConstants = Block223Controller.getConstants();
		DesignGridPane.lastPageLayoutPane = lastPageLayoutPane;
		DesignGridPane.level = level;
		designGridPane = new GridPane();
		designPane = this;
		this.setPrefSize(toConstants.getPlayAreaSide(), toConstants.getPlayAreaSide()-toConstants.getVerticalDistance()-toConstants.getBallDiameter()-toConstants.getPaddleWidth());
		designGridPane.setHgap(toConstants.getColumnsPadding());
		designGridPane.setVgap(toConstants.getRowPadding());
		designGridPane.setPadding(new Insets(toConstants.getWallPadding()));
		designPane.getChildren().add(designGridPane);
		displayGrid();
		refresh();
		
		this.setBorder(new Border(new BorderStroke(Color.WHITE, 
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));		
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

		for (TOGridCell gridCell : gridCells) {
			int y = gridCell.getGridHorizontalPosition() - 1;
			int x = gridCell.getGridVerticalPosition() - 1;
			Color blockColor = new Color((double) gridCell.getRed() / toConstants.getMaxColor(),
					(double) gridCell.getGreen() / toConstants.getMaxColor(),
					(double) gridCell.getBlue() / toConstants.getMaxColor(), 1);

			Pane block = (Pane) designGridPane.getChildren().get(y * toConstants.getMaxVerticalBlocks() + x);
			block.setOpacity(1);
			block.setStyle("-fx-background-color: #" + blockColor.toString().substring(2, 8) + ";");

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
					if (e.getButton() == MouseButton.PRIMARY) {
						ChosenBlock chosenBlock = Block223Page.getChosenBlock();
						if (chosenBlock != null) {
							int y = GridPane.getRowIndex(blockBox);
							int x = GridPane.getColumnIndex(blockBox);

							try {
								Block223Controller.positionBlock(chosenBlock.getId(), DesignGridPane.level, x + 1,
										y + 1);
								lastPageLayoutPane.setErrorMessage("");
							} catch (InvalidInputException e1) {
								lastPageLayoutPane.setErrorMessage(e1.getMessage());
							}
							refresh();
						}
					}

					if (e.getButton() == MouseButton.SECONDARY) {
						int y = GridPane.getRowIndex(blockBox);
						int x = GridPane.getColumnIndex(blockBox);
						try {
							Block223Controller.removeBlock(DesignGridPane.level, x + 1, y + 1);
							lastPageLayoutPane.setErrorMessage("");
						} catch (InvalidInputException e1) {
							lastPageLayoutPane.setErrorMessage(e1.getMessage());
						}
						refresh();
					}
				});

				blockBox.setOnDragDetected(e -> {
					initialY = GridPane.getRowIndex(blockBox) + 1;
					initialX = GridPane.getColumnIndex(blockBox) + 1;
					blockBox.startFullDrag();
				});

				blockBox.setOnMouseDragged(e -> {					
					if (blockBox.getOpacity() > 0.6) {
						blockBox.toFront();
						blockBox.setTranslateX(e.getX() + blockBox.getTranslateX() - toConstants.getSize()/2);
						blockBox.setTranslateY(e.getY() + blockBox.getTranslateY() - toConstants.getSize()/2);
						blockBox.setDisable(true);
					}
				});

				blockBox.setOnMouseDragReleased(e -> {
					if(initialY == GridPane.getRowIndex(blockBox) + 1 && initialX == GridPane.getColumnIndex(blockBox) + 1) {
						refresh();
					}
					else {
						try {
							Block223Controller.moveBlock(level, initialX, initialY,
									GridPane.getColumnIndex(blockBox) + 1, GridPane.getRowIndex(blockBox) + 1);
							lastPageLayoutPane.setErrorMessage("");
						} catch (InvalidInputException e1) {
							lastPageLayoutPane.setErrorMessage(e1.getMessage());
						} finally {
							refresh();
						}
					}
				});

			}
		}
	}
}