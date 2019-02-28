package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Block;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class BlockCreatorPane extends VBox {
		
	private Label blockCreatorLabel;
	
	private HBox blockCreatorColor;
	private Label blockCreatorColorLabel;
	private ColorPicker blockCreatorColorPicker;
	
	private HBox blockCreatorWorth;
	private Label blockCreatorWorthLabel;
	private Slider blockCreatorWorthSlider;
	private Label sliderValue;
	
	private Button createBlock;
	
	private Label errorMessage;
	
	public BlockCreatorPane(double spacing) {
		this.setStyle("-fx-background-color: white;");
		blockCreatorLabel = new Label("Create Block");
		
		blockCreatorColor = new HBox(spacing/2);
		
		blockCreatorColor.setAlignment(Pos.CENTER);
		blockCreatorColorLabel =  new Label("Color : ");
		blockCreatorColorPicker = new ColorPicker();
		blockCreatorColorPicker.setOnAction(e -> {
			Color color = blockCreatorColorPicker.getValue();
			String hexFormat = "#"+Integer.toHexString(color.hashCode()).substring(0, 6).toUpperCase();
			createBlock.setStyle("-fx-background-color: "+hexFormat);
		});
		blockCreatorColor.getChildren().addAll(blockCreatorColorLabel, blockCreatorColorPicker);	
		
		blockCreatorWorth = new HBox(spacing/2);
		blockCreatorWorthLabel = new Label("Worth : ");
		blockCreatorWorthSlider = new Slider(Block.MIN_POINTS, Block.MAX_POINTS, (Block.MAX_POINTS+Block.MIN_POINTS)/2);
		blockCreatorWorthSlider.valueProperty().addListener(e -> {
			createBlock.setText(""+(int)blockCreatorWorthSlider.getValue());
			sliderValue.setText(""+(int)blockCreatorWorthSlider.getValue());
		});
		sliderValue = new Label(""+(int)blockCreatorWorthSlider.getValue());
		blockCreatorWorth.getChildren().addAll(blockCreatorWorthLabel, blockCreatorWorthSlider, sliderValue);
		blockCreatorWorth.setPrefWidth(spacing*12);
		
		createBlock = new Button(""+(int)blockCreatorWorthSlider.getValue());
		createBlock.setMinWidth(4*Block.SIZE);
		createBlock.setMinHeight(4*Block.SIZE);
		createBlock.setOnAction(e -> {
			Color color = blockCreatorColorPicker.getValue();
			try {
				Block223Controller.addBlock((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), (int) blockCreatorWorthSlider.getValue());
			}
			catch(InvalidInputException iie) {
				errorMessage.setText(iie.getMessage());
			}
		});
		
		errorMessage = new Label();
		errorMessage.setStyle("-fx-text-fill: #DC143C");
		
		
		this.getChildren().addAll(blockCreatorLabel, blockCreatorColor, blockCreatorWorth, createBlock, errorMessage);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(spacing);
		this.setBorder(new Border(new BorderStroke(Color.VIOLET, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(8))));
		this.setPadding(new Insets(spacing, spacing, spacing, spacing));
		setStyles();
	}

	private void setStyles() {
		blockCreatorColorLabel.setStyle("-fx-font:15 Garamond;");
		blockCreatorWorthLabel.setStyle("-fx-font:15 Garamond;");
		blockCreatorLabel.setStyle("-fx-font:23 Garamond;");
		createBlock.setStyle("-fx-font:23 Garamond;");
this.setOpacity(0.75);
	}
	

	
}
