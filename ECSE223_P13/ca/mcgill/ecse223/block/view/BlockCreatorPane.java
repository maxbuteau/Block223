package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOConstant;
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
	private Button addToToolBox;
	
	private Label errorMessage;
	
	public BlockCreatorPane(double spacing) {
		TOConstant constants = Block223Controller.getConstants();
		
		this.setStyle("-fx-background-color: white;");
		blockCreatorLabel = new Label("Create Block");
		
		blockCreatorColor = new HBox(spacing/2);
		
		blockCreatorColor.setAlignment(Pos.CENTER);
		blockCreatorColorLabel =  new Label("Color : ");
		blockCreatorColorPicker = new ColorPicker();
		blockCreatorColorPicker.setOnAction(e -> {
			Color color = blockCreatorColorPicker.getValue();
			try {
			String hexFormat = "#"+Integer.toHexString(color.hashCode()).substring(0, 6).toUpperCase();
			createBlock.setStyle("-fx-background-color: "+hexFormat+";  -fx-background-radius: 0; -fx-border-color: transparent;");
			}
			catch(StringIndexOutOfBoundsException ex) {
				createBlock.setStyle("-fx-background-color: black;");
			}
		});
		blockCreatorColor.getChildren().addAll(blockCreatorColorLabel, blockCreatorColorPicker);	
		
		blockCreatorWorth = new HBox(spacing/2);
		blockCreatorWorth.setAlignment(Pos.CENTER);
		blockCreatorWorthLabel = new Label("Worth : ");
		blockCreatorWorthSlider = new Slider(constants.getMinPoints(), constants.getMaxPoints(), (constants.getMaxPoints()+constants.getMinPoints())/2);
		blockCreatorWorthSlider.valueProperty().addListener(e -> {
			createBlock.setText(""+(int)blockCreatorWorthSlider.getValue());
			sliderValue.setText(""+(int)blockCreatorWorthSlider.getValue());
		});
		sliderValue = new Label(""+(int)blockCreatorWorthSlider.getValue());
		blockCreatorWorth.getChildren().addAll(blockCreatorWorthLabel, blockCreatorWorthSlider, sliderValue);
		blockCreatorWorth.setPrefWidth(spacing*12);
		
		createBlock = new Button(""+(int)blockCreatorWorthSlider.getValue());
//		createBlock.setBorder(new Border(new BorderStroke(Color.BLACK, 
//	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
		createBlock.setMinWidth(4*constants.getSize());
		createBlock.setMinHeight(4*constants.getSize());
		
		addToToolBox = new Button("Add Block");
		addToToolBox.setOnAction(e -> {
			Color color = blockCreatorColorPicker.getValue();
			try {
				Block223Controller.addBlock((int)(color.getRed()*255), (int)(color.getGreen()*255), (int)(color.getBlue()*255), (int)blockCreatorWorthSlider.getValue());
			}
			catch(InvalidInputException iie) {
				errorMessage.setText(iie.getMessage());
			}
			ToolboxView.refreshToolbox();
		});
		addToToolBox.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");
		
		
		errorMessage = new Label();
		errorMessage.setStyle("-fx-text-fill: #DC143C");
		
		
		this.getChildren().addAll(blockCreatorLabel, blockCreatorColor, blockCreatorWorth, createBlock, addToToolBox, errorMessage);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(spacing);
//		this.setBorder(new Border(new BorderStroke(Color.VIOLET, 
//	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(8))));
		this.setPadding(new Insets(0, spacing, 0, spacing));
		setStyles();
	}

	private void setStyles() {
		this.setStyle("-fx-background-color: transparent;");
		blockCreatorLabel.setStyle("-fx-font:30 Garamond;");
		this.getStylesheets().add("ca/mcgill/ecse223/block/view/resources/style.css");
		createBlock.setStyle("-fx-background-color: white; -fx-background-radius: 0; -fx-border-color: transparent;");

	}
	

	
}

