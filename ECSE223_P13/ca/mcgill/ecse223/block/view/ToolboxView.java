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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class ToolboxView extends VBox {

	private Label toolboxError;
	//TOOLBOX
	private TilePane blockTilePane;
	private Button toolboxButton;
	private VBox toolboxBox;
	private Button toolboxDeleteButton;
	private Button toolboxColorPickerButton;
	private Button toolboxUpdateButton;
	private Scene toolboxScene;	
	private static Pane selectedPane = new Pane();
	private ColorPicker toolboxColorPicker;
	private HBox toolboxWorthSliderBox;
	private Slider toolboxWorthSlider;

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
		toolboxColorPicker = new ColorPicker();
		toolboxDeleteButton = new Button("Delete block");
		toolboxUpdateButton = new Button("Update");
		toolboxWorthSlider = new Slider(1,1000, 500);
		toolboxError = new Label();
		toolboxError.setStyle("-fx-text-fill: #DC143C");

		this.getChildren().addAll(blockTilePane, toolboxDeleteButton, toolboxColorPicker, toolboxWorthSlider, toolboxUpdateButton, toolboxError);

		refreshToolbox();
	}

	private void refreshToolbox() {
		blockTilePane.getChildren().clear();
		try {
			List<TOBlock> toBlocks = Block223Controller.getBlocksOfCurrentDesignableGame();
			for(TOBlock toBlock : toBlocks) {
				//Getting block color for each block
				Color blockColor = new Color(toBlock.getRed(), toBlock.getGreen(), toBlock.getBlue(), 1);
				Pane toBlockPane = new Pane();
				toBlockPane.setBackground(new Background(new BackgroundFill(blockColor, CornerRadii.EMPTY, Insets.EMPTY)));

				//Getting worth and id for each block
				Label toBlockId = new Label(String.valueOf(toBlock.getId()));
				Label toBlockWorth = new Label(String.valueOf(toBlock.getPoints()));

				//Adding color, worth, and id for each block in tile
				blockTilePane.getChildren().addAll(toBlockPane, toBlockId, toBlockWorth);

				blockTilePane.setOnMouseClicked(ciao -> {
					selectedPane.setEffect(null);
					DropShadow dropShadow = new DropShadow();
					dropShadow.setRadius(10);
					dropShadow.setColor(Color.CHARTREUSE);
					toBlockPane.setEffect(dropShadow);
					selectedPane = blockTilePane;	
				});


			}
		}
		catch(InvalidInputException iie) {
			toolboxError.setText(iie.getMessage());
		}

		toolboxUpdateButton.setOnAction(e -> {
			Label idLabel = (Label) selectedPane.getChildren().get(1);
			Label worthLabel = (Label) selectedPane.getChildren().get(2);
			Color color = toolboxColorPicker.getValue();

			try {
				Block223Controller.updateBlock(Integer.parseInt(idLabel.getText()), (int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(worthLabel.getText()));
			}
			catch(InvalidInputException iie) {
				toolboxError.setText(iie.getMessage());
			}
			refreshToolbox();
		});

		toolboxDeleteButton.setOnAction(e -> {
			Label idLabel = (Label) selectedPane.getChildren().get(1);

			try {
				Block223Controller.deleteBlock(Integer.parseInt(idLabel.getText()));
			} catch (InvalidInputException iie) {
				toolboxError.setText(iie.getMessage());
			}
			refreshToolbox();
		});
	}


}



