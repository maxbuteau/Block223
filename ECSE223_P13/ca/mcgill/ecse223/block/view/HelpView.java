package ca.mcgill.ecse223.block.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class HelpView extends Pane {
	
	private static final int HELP_PADDING = 10;
	private static final int HORIZONTAL_GAP = 20;
	
	private static Label helpLabel1;
	private static Label helpLabel2;
	private static Label helpLabel3;
	private static Label helpLabel4;
	private static GridPane helpGrid;
	
	public HelpView() {
		helpLabel1 = new Label("- To select a block that was added to the game, click on toolbox and then on the desired block;");
		helpLabel1.setStyle(
				"-fx-text-fill: #4B0082;" 
				+ "-fx-padding: 20px;"
				+ "-fx-font:20 Garamond;");
		helpLabel2 = new Label("- When a block is selected, click anywhere in the grid to place it;");
		helpLabel2.setStyle(
				"-fx-text-fill: #4B0082;" 
				+ "-fx-padding: 20px;"
				+ "-fx-font:20 Garamond;");
		helpLabel3 = new Label("- To move a block in the grid, drag it to the new location;");
		helpLabel3.setStyle(
				"-fx-text-fill: #4B0082;" 
				+ "-fx-padding: 20px;"
				+ "-fx-font:20 Garamond;");
		helpLabel4 = new Label("- To remove a block from the grid, right-click on it;");
		helpLabel4.setStyle(
				"-fx-text-fill: #4B0082;" 
				+ "-fx-padding: 20px;"
				+ "-fx-font:20 Garamond;");
		
		
		helpGrid = new GridPane();
		helpGrid.setPadding(new Insets(HELP_PADDING));
		helpGrid.setVgap(HORIZONTAL_GAP);
		
		helpGrid.addColumn(0, helpLabel1, helpLabel2, helpLabel3, helpLabel4);
		this.getChildren().add(helpGrid);
	}
	
	
	
}
