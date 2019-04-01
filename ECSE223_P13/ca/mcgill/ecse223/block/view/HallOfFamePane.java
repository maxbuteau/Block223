package ca.mcgill.ecse223.block.view;

import java.util.List;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HallOfFamePane extends VBox{

	private static ObservableList<TOHallOfFameEntry> hallOfFameListData;
	private static TableView hallOfFameList;
	private VBox hallOfFameBox;
	private Label hallOfFameLabel;
	private static Button nextHFButton;
	private static Button prevHFButton;
	private HBox navigationButtonBox;
	private final static int rowsPerPage = 10;

	public HallOfFamePane() {
		//HoF box
		hallOfFameBox = new VBox(20);
		hallOfFameLabel = new Label("Hall Of Fame");
		ImageView imv = new ImageView();
		Image hofImage = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/HoF.png"));
		imv.setImage(hofImage);
		imv.setFitHeight(Block223Page.getScreenHeight() / 4);
		imv.setFitWidth(Block223Page.getScreenWidth() / 8);
		imv.setPreserveRatio(true);

		//Next, prev buttons
		navigationButtonBox = new HBox(20);
		nextHFButton = new Button("Next");
		prevHFButton = new Button("Previous");
		navigationButtonBox.getChildren().addAll(prevHFButton, nextHFButton);

		//Creating tableView for HoF
		hallOfFameListData = FXCollections.observableArrayList();
		hallOfFameList = new TableView<>();
		TableColumn<TableView<TOHallOfFameEntry>, String> usernameCol = new TableColumn<>("Username");
		usernameCol.setCellValueFactory(new PropertyValueFactory<>("playername"));
		TableColumn<TableView<TOHallOfFameEntry>, Integer> scoreCol = new TableColumn<>("score");
		scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
		hallOfFameList.setStyle("-fx-font:18 Garamond; -fx-font-weight: bold;");
		hallOfFameList.getColumns().addAll(usernameCol, scoreCol);
		usernameCol.setMinWidth(Block223Page.getScreenWidth()/8);
		scoreCol.setMinWidth(Block223Page.getScreenWidth()/8);
		refreshHallOfFamePane();

		//		nextHFButton.setOnAction(e -> {		
		//		});
		//		
		//		prevHFButton.setOnAction(e -> {		
		//		});

		hallOfFameBox.getChildren().addAll(hallOfFameLabel, hallOfFameList, navigationButtonBox);	
		hallOfFameBox.setPrefWidth(Block223Page.getScreenWidth()/4);

	}

	private static void refreshHallOfFamePane() {
		hallOfFameList.getItems().clear();
		int i = 1;
		if(nextHFButton.isPressed()) i++;
		if(prevHFButton.isPressed()) i--;
		if(i <= 0) i = 1;
		try {
				List<TOHallOfFameEntry> toHF = Block223Controller.getHallOfFame(1, i * rowsPerPage).getEntries();
				for (TOHallOfFameEntry to : toHF) {
					to.getPlayername();
					to.getScore();
					hallOfFameListData.add(to);
				}
			} catch(InvalidInputException e) {
				e.printStackTrace();
		}
	}
}
