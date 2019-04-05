package ca.mcgill.ecse223.block.view;

import java.util.List;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.controller.TOConstant;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;



public class ToolboxView extends VBox {

	private static TOConstant constants;
	private static Label toolboxError;
	
	//TOOLBOX
	private static FlowPane blockFlowPane;
	private static VBox toolboxVBox;
	private static Button toolboxDeleteButton;
	private static Button toolboxUpdateButton;
	private static ColorPicker toolboxColorPicker;
	private static Slider toolboxWorthSlider;
	private static Label toolboxWorthSliderTitle;
	private static Label toolboxWorthSliderValue;
	private static HBox toolboxSliderBox;

	private static Pane previousPane = new Pane();

	private final double SCREEN_WIDTH = 600; // to be changed
	public ToolboxView() {
		super(20);
		previousPane = new Pane();
		Label toolboxLabel = new Label("Block toolbox");
		toolboxLabel.setTranslateX(SCREEN_WIDTH/6);
		constants = Block223Controller.getConstants();

		//Grid of blocks for toolbox
		blockFlowPane = new FlowPane();
		blockFlowPane.setPadding(new Insets(30, 15, 30, 15));
		blockFlowPane.setVgap(20);
		blockFlowPane.setHgap(20);
		blockFlowPane.setAlignment(Pos.CENTER);
		blockFlowPane.setPrefWidth(120);


		//Toolbox buttons
		toolboxColorPicker = new ColorPicker();
		toolboxDeleteButton = new Button("Delete block");
		toolboxUpdateButton = new Button("Update");
		toolboxWorthSlider = new Slider(constants.getMinPoints(), constants.getMaxPoints(), (constants.getMaxPoints()+constants.getMinPoints())/2);
		toolboxError = new Label();
		toolboxError.setStyle("-fx-text-fill: #DC143C");

		//Initialize toolbox worth slider
		toolboxWorthSliderTitle = new Label("Worth");
		toolboxWorthSliderValue = new Label(""+(int)toolboxWorthSlider.getValue());
		
		toolboxSliderBox = new HBox(20);
		toolboxSliderBox.getChildren().addAll(toolboxWorthSliderTitle, toolboxWorthSlider, toolboxWorthSliderValue);
		
		this.getChildren().addAll(blockFlowPane, toolboxDeleteButton, toolboxColorPicker, toolboxSliderBox, toolboxUpdateButton, toolboxError);
		this.setAlignment(Pos.CENTER);

		refreshToolbox();
		actionListeners();
	}
	
	private void actionListeners() {
		toolboxWorthSlider.valueProperty().addListener(e ->{
			toolboxWorthSliderValue.setText((int)toolboxWorthSlider.getValue()+"");
		});
	}

	public static void refreshToolbox() {
		if(blockFlowPane != null) {
			blockFlowPane.getChildren().clear();
			try {
				List<TOBlock> toBlocks = Block223Controller.getBlocksOfCurrentDesignableGame();
				for(TOBlock toBlock : toBlocks) {
					
					//Getting block color for each block
					Color blockColor = new Color((double)toBlock.getRed()/255, (double)toBlock.getGreen()/255, (double)toBlock.getBlue()/255, 1);
					Pane toBlockPane = new Pane();
					toBlockPane.setStyle("-fx-background-color: #"+blockColor.toString().substring(2, 8)+";");
					toBlockPane.setPrefSize(constants.getSize()*2, constants.getSize()*2);
					

					//Getting worth and id for each block
					Label toBlockId = new Label(String.valueOf(toBlock.getId()));
					Label toBlockWorth = new Label(String.valueOf(toBlock.getPoints()));

					//Adding color, worth, and id for each block in tile
					toolboxVBox = new VBox();
					toolboxVBox.setAlignment(Pos.CENTER);
					toolboxVBox.getChildren().addAll(toBlockPane, toBlockId, toBlockWorth);
					blockFlowPane.getChildren().addAll(toolboxVBox);

					if(previousPane.getEffect()==null) {
						DropShadow dropShadow = new DropShadow();
						dropShadow.setRadius(10);
						dropShadow.setColor(Color.CHARTREUSE);
						toBlockPane.setEffect(dropShadow);
						String id = (((Label)toolboxVBox.getChildren().get(1)).getText());
						Block223Page.setChosenBlock(id);
						previousPane = toBlockPane;
					}
					toolboxVBox.setOnMouseClicked(ciao -> {
						previousPane.setEffect(null);
						DropShadow dropShadow = new DropShadow();
						dropShadow.setRadius(10);
						dropShadow.setColor(Color.CHARTREUSE);
						toBlockPane.setEffect(dropShadow);
						String id = ((Label)((VBox)ciao.getSource()).getChildren().get(1)).getText();
						Block223Page.setChosenBlock(id);
						previousPane = toBlockPane;
					});

				}
			}
			catch(InvalidInputException iie) {
				toolboxError.setText(iie.getMessage());
			}

			toolboxUpdateButton.setOnAction(e -> {
				Block223Page.buttonPressSound();
				ChosenBlock chosenBlock = Block223Page.getChosenBlock();
				Color color = toolboxColorPicker.getValue();
				int worth = (int)toolboxWorthSlider.getValue();

				if(chosenBlock != null) {
					try {
						Block223Controller.updateBlock(chosenBlock.getId(), (int)(color.getRed()*255), (int)(color.getGreen()*255), (int)(color.getBlue()*255), worth);
					}
					catch(InvalidInputException iie) {
						System.out.println("fuck");
						toolboxError.setText(iie.getMessage());
					}
					refreshToolbox();
					DesignGridPane.refresh();
				}
			});

			toolboxDeleteButton.setOnAction(e -> {
				Block223Page.buttonPressSound();
				ChosenBlock chosenBlock = Block223Page.getChosenBlock();

				if(chosenBlock != null) {
					try {
						Block223Controller.deleteBlock(chosenBlock.getId());
						previousPane = new Pane();
					} catch (InvalidInputException iie) {
						toolboxError.setText(iie.getMessage());
					}
					refreshToolbox();
					DesignGridPane.refresh();
				}
			});	
		}
	}
}



