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
import javafx.geometry.Insets;
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
	private static Label HoFError;
	private static Button nextHFButton;
	private static Button prevHFButton;
	private HBox navigationButtonBox;
	private final static int rowsPerPage = 10;
	private static int index = 0;
	private static TOHallOfFame toHF;

	public HallOfFamePane() {
		//error
		HoFError = new Label();

		//HoF box
		ImageView imv = new ImageView();
		Image hofImage = new Image(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/HoF.png"));
		imv.setImage(hofImage);
		imv.setFitHeight(Block223Page.getScreenHeight() / 4);
		imv.setFitWidth(Block223Page.getScreenWidth() / 4);
		imv.setPreserveRatio(true);
		HBox headerRegion = new HBox(20);
		headerRegion.getChildren().add(imv);
		headerRegion.setAlignment(Pos.CENTER);

		//Next, prev buttons
		navigationButtonBox = new HBox(20);
		nextHFButton = new Button("Next");
		nextHFButton.setFocusTraversable(false);
		nextHFButton.setPadding(new Insets(10));
		prevHFButton = new Button("Previous");
		prevHFButton.setFocusTraversable(false);
		prevHFButton.setPadding(new Insets(10));
		navigationButtonBox.getChildren().addAll(prevHFButton, nextHFButton);
		navigationButtonBox.setPadding(new Insets(10));
		navigationButtonBox.setAlignment(Pos.CENTER);

		System.out.println("1: "+ index);
		nextHFButton.setOnMousePressed(e -> {	
			index++;
			System.out.println("2: "+ index);
			if(index > toHF.getEntries().size() / 10.0) {
				index = (int) Math.floor(toHF.getEntries().size() / 10.0);
			}	
		});

		prevHFButton.setOnMousePressed(e -> {	
			index--;
			System.out.println("3: "+ index);
			if(index < 0) {
				index = 0;
			}System.out.println("4: "+ index);
		});

		//Creating tableView for HoF
		hallOfFameListData = FXCollections.observableArrayList();

		hallOfFameList = new TableView<>();
		hallOfFameList.setItems(hallOfFameListData);
		hallOfFameList.setFocusTraversable(false);

		TableColumn<TableView<TOHallOfFameEntry>, String> usernameCol = new TableColumn<>("Username");
		usernameCol.setCellValueFactory(new PropertyValueFactory<>("playername"));

		TableColumn<TableView<TOHallOfFameEntry>, Integer> scoreCol = new TableColumn<>("score");
		scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

		hallOfFameList.getColumns().addAll(usernameCol, scoreCol);
		usernameCol.setMinWidth(Block223Page.getScreenWidth()/8);
		scoreCol.setMinWidth(Block223Page.getScreenWidth()/8);

		hallOfFameList.setStyle("-fx-font:18 Garamond; -fx-font-weight: bold;");
		refreshHallOfFamePane();


		this.getChildren().addAll(headerRegion, hallOfFameList, navigationButtonBox);	
		this.setPrefWidth(Block223Page.getScreenWidth()/4);
		this.setPadding(new Insets(20));


	}

	private static void refreshHallOfFamePane() {
		hallOfFameList.getItems().clear();
		try {
			HoFError.setText("");
			toHF = Block223Controller.getHallOfFame(index * rowsPerPage + 1, (index + 1) * rowsPerPage);
			for (TOHallOfFameEntry to : toHF.getEntries()) {
				System.out.println("yeah");
				//ca rentre pas dans ce loop !!!!!!!!
				hallOfFameListData.add(to);
			}
		} catch(InvalidInputException e) {
			HoFError.setText((e.getMessage()));
		}
	}
}
