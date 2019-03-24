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
	private static Pane temp = new Pane();
	private static Pane temp2 = new Pane();

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
		temp2.setStyle("-fx-background-color: #FFFFFF;");
		temp2.setOpacity(0.5);
		temp2.setPrefSize(toConstants.getSize(), toConstants.getSize());
		if (!lastPageLayoutPane.getChildren().contains(temp))
			lastPageLayoutPane.getChildren().add(temp);
//		if (this.getChildren().contains(temp))
//			this.getChildren().add(temp);
//		if (!lastPageLayoutPane.getChildren().contains(temp2))
//			lastPageLayoutPane.getChildren().add(temp2);
		displayGrid();
		refresh();
		this.setOnMouseDragReleased(e->{refresh();});
		this.setOnMouseDragExited(e->{refresh();});
	}

	public static void refresh() {
		designGridPane.getChildren().clear();
		temp.setPrefSize(toConstants.getSize(), toConstants.getSize());
		displayGrid();
		List<TOGridCell> gridCells = new ArrayList<TOGridCell>();
		try {
			gridCells = Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(level);
		} catch (InvalidInputException e) {
			lastPageLayoutPane.setErrorMessage(e.getMessage());
		}

		for (TOGridCell gridCell : gridCells) {
			int x = gridCell.getGridHorizontalPosition() - 1;
			int y = gridCell.getGridVerticalPosition() - 1;
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

				temp.setVisible(false);
				temp2.setVisible(false);
				blockBox.setOnMouseClicked(e -> {
					if (e.getButton() == MouseButton.PRIMARY) {
						ChosenBlock chosenBlock = Block223Page.getChosenBlock();
						if (chosenBlock != null) {
							int x = GridPane.getRowIndex(blockBox);
							int y = GridPane.getColumnIndex(blockBox);

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
						int x = GridPane.getRowIndex(blockBox);
						int y = GridPane.getColumnIndex(blockBox);
						try {
							Block223Controller.removeBlock(DesignGridPane.level, x + 1, y + 1);
							lastPageLayoutPane.setErrorMessage("");
						} catch (InvalidInputException e1) {
							lastPageLayoutPane.setErrorMessage(e1.getMessage());
						}
						refresh();
					}
				});

				final int c = i;
				final int v = j;
				blockBox.setOnDragDetected(e -> {
					blockBox.setVisible(false);
					System.out.println("");
					temp2=new Pane();
					temp2.setStyle("-fx-background-color: #FFFFFF;");
					temp2.setOpacity(0.5);
					temp2.setPrefSize(toConstants.getSize(), toConstants.getSize());
					temp.setStyle(blockBox.getStyle());
					if (blockBox.getOpacity() <0.6)

						temp.setStyle("");
				
					temp2.setVisible(true);
					initialX = GridPane.getRowIndex(blockBox) + 1;
					initialY = GridPane.getColumnIndex(blockBox) + 1;
					designGridPane.add(temp2, c, v);
					blockBox.startFullDrag();
					
				});

				blockBox.setOnMouseDragged(e -> {
					if (blockBox.getOpacity() >0.6) {
					temp.setVisible(true);
					temp.setLayoutX(e.getSceneX() - toConstants.getSize() / 2);
					temp.setLayoutY(e.getSceneY() - toConstants.getSize() / 2);
					blockBox.setTranslateX(e.getX());
					blockBox.setTranslateY(e.getY());
					}
				});

				
				blockBox.setOnMouseDragReleased(e -> {
					try {
						
						temp.setVisible(false);
						blockBox.setVisible(true);
						designGridPane.getChildren().remove(temp2);
						if(GridPane.getRowIndex(blockBox) ==null || GridPane.getColumnIndex(blockBox)==null)
							refresh();
						Block223Controller.moveBlock(DesignGridPane.level, initialX, initialY,
								GridPane.getRowIndex(blockBox) + 1, GridPane.getColumnIndex(blockBox) + 1);
						lastPageLayoutPane.setErrorMessage("");
						
						
					} catch (InvalidInputException e1) {
						lastPageLayoutPane.setErrorMessage(e1.getMessage());
					} finally {
						refresh();
					}
				});

			}
		}
	}
}