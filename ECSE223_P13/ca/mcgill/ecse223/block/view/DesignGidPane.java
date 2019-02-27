package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.model.Ball;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Paddle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DesignGidPane extends GridPane {

	private static final int MAX_HORIZONTAL_BLOCKS = (1+(Game.PLAY_AREA_SIDE-2*Game.WALL_PADDING-Block.SIZE)/(Block.SIZE+Game.COLUMNS_PADDING));
	private static final int MAX_VERTICAL_BLOCKS = (1+(Game.PLAY_AREA_SIDE-Paddle.VERTICAL_DISTANCE-Game.WALL_PADDING-Paddle.PADDLE_WIDTH-Ball.BALL_DIAMETER-Block.SIZE)/(Block.SIZE+Game.ROW_PADDING));
	private static final int OUTLINE_WIDTH = 3;
	
	public DesignGidPane(int level) {
		GridPane grid = new GridPane();
		grid.setHgap(Game.COLUMNS_PADDING);
		grid.setVgap(Game.ROW_PADDING);
		grid.setPadding(new Insets(Game.WALL_PADDING));

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
					if (Block223Page.getChosenBlock() != null) {
						Object o = e.getSource();
						Rectangle clickedRectangle = (Rectangle) o;
						int x = grid.getRowIndex(clickedRectangle);
						int y = grid.getColumnIndex(clickedRectangle);
						Block223Controller.positionBlock(Block223Page.getChosenBlock().getId(), level, x+1, y+1);
					}
				});
			}
		}
	}
}