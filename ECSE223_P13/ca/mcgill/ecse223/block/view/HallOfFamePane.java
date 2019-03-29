package ca.mcgill.ecse223.block.view;

import java.util.List;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.controller.TOPlayableGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HallOfFamePane extends VBox{

	private static ObservableList<TOHallOfFameEntry> hallOfFameListData;
	private static TableView hallOfFameList;
	private VBox hallOfFameBox;
	private Label hallOfFameLabel;
	private Button nextHFButton;
	private Button prevHFButton;
	private HBox navigationButtonBox;
	
	public HallOfFamePane() {
		hallOfFameListData = FXCollections.observableArrayList();
		hallOfFameList = new TableView<>();
		hallOfFameList.setItems(hallOfFameListData);
		
		hallOfFameBox = new VBox(20);
		hallOfFameLabel = new Label("Hall Of Fame");
		
		navigationButtonBox = new HBox(20);
		nextHFButton = new Button("Next");
		prevHFButton = new Button("Previous");
		navigationButtonBox.getChildren().addAll(prevHFButton, nextHFButton);
		
		nextHFButton.setOnAction(e -> {	
			
		});
		
		prevHFButton.setOnAction(e -> {	
			
		});
		
		hallOfFameBox.getChildren().addAll(hallOfFameLabel, hallOfFameList, navigationButtonBox);	
		hallOfFameBox.setPrefWidth(Block223Page.getScreenWidth()/3);
			
	}
	private static void refreshHallOfFamePane() {
		hallOfFameList.getItems().clear();
		try {
			//TODO refresh all not just 10 entries
			List<TOHallOfFameEntry> toHF = Block223Controller.getHallOfFame(1, 10).getEntries();
			for (TOHallOfFameEntry to : toHF) {
				hallOfFameListData.add(to);
			}
		} catch(InvalidInputException e) {
			e.printStackTrace();
		}
	}
}
