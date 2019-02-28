package ca.mcgill.ecse223.block.view;

import java.util.List;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class ToolboxView extends VBox {

	//TOOLBOX
	private TilePane blockTilePane;
	private Button toolboxButton;
	private VBox toolboxBox;
	private Button toolboxDeleteButton;
	private Button toolboxColorPickerButton;
	private Scene toolboxScene;	
	private static Pane selectedPane = new Pane();

	private final double SCREEN_WIDTH = 500; // to be changed
	private final double SCREEN_HEIGHT = 500; // to be changed

	public ToolboxView() {
		super(20);
		Label toolboxLabel = new Label("Block toolbox");
		toolboxLabel.setTranslateX(SCREEN_WIDTH/6);

		//Grid of blocks for toolbox
		blockTilePane = new TilePane();
		blockTilePane.setPadding(new Insets(30, 0, 30, 0));
		blockTilePane.setVgap(20);
		blockTilePane.setHgap(20);
		blockTilePane.setAlignment(Pos.CENTER);
		blockTilePane.setPrefColumns(4);

		//Toolbox buttons
		toolboxColorPickerButton = new Button("Select block color");
		toolboxDeleteButton = new Button("Delete block");

		this.getChildren().addAll(blockTilePane, toolboxDeleteButton, toolboxColorPickerButton);

		refreshToolbox();

		toolboxScene = new Scene(this, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 6);

	}

	private void refreshToolbox() {
		blockTilePane.getChildren().clear();
		try {
			List<TOBlock> toBlocks = Block223Controller.getBlocksOfCurrentDesignableGame();
			for(TOBlock toBlock : toBlocks) {
				//Getting block color for each block
				Color blockColor = new Color(toBlock.getRed(), toBlock.getGreen(), toBlock.getBlue(), 100);
				Pane toBlockPane = new Pane();
				toBlockPane.setBackground(new Background(new BackgroundFill(blockColor, CornerRadii.EMPTY, Insets.EMPTY)));

				//Getting worth and id for each block
				Label toBlockId = new Label(String.valueOf(toBlock.getId()));
				Label toBlockWorth = new Label(String.valueOf(toBlock.getPoints()));

				//Adding color, worth, and id for each block in tile
				blockTilePane.getChildren().addAll(toBlockPane, toBlockId, toBlockWorth);

				//Adding shadow to selected block in toolbox
				toBlockPane.setOnMouseClicked(ciao -> {
					selectedPane.setEffect(null);
					DropShadow dropShadow = new DropShadow();
					dropShadow.setRadius(10);
					dropShadow.setColor(Color.CHARTREUSE);
					toBlockPane.setEffect(dropShadow);
					selectedPane = toBlockPane;	

				}
						);
				//change selected block color
				toolboxColorPickerButton.setOnMouseClicked(colorPickerEvent ->{
					ColorPicker toolboxColorPicker = new ColorPicker();
					toolboxColorPicker.setOnMouseClicked(colorEvent -> {
						BackgroundFill slectedColor = new BackgroundFill(toolboxColorPicker.getValue(),CornerRadii.EMPTY, Insets.EMPTY);
						toBlockPane.setBackground(new Background(slectedColor));
					});
				});

				//change selected block worth
				Slider toolboxWorthSlider = new Slider(1,1000, 500); //need to get min and max from block.java
				toolboxWorthSlider.setOnMouseDragged(worthEvent -> {
					toolboxWorthSlider.getValue();
				});

				toolboxDeleteButton.setOnMouseClicked(deleteBlockEvent -> {
					try {
						Block223Controller.deleteBlock(toBlock.getId());
					} catch (InvalidInputException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}
		} catch (InvalidInputException e1) {
			e1.printStackTrace();
		}
	}
}



