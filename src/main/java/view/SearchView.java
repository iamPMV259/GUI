package view;


import javafx.collections.ObservableList;
import java.util.ArrayList;
import controller.SearchController;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

public class SearchView {

    private final VBox root;
    private final ComboBox<String> searchBox;
    private final TableView<ObservableList<String>> tableView;
    private final Button searchButton;
    private final SearchController controller;

    /**
     * 
     *
     * @param keywords 
     */
    public SearchView(ArrayList<String> keywords) {
        // Initialize components
        searchBox = new ComboBox<>(FXCollections.observableArrayList(keywords));
        searchBox.setPromptText("Select a keyword to search");

        tableView = new TableView<>();
        searchButton = new Button("Search");

        
        controller = new SearchController(searchBox, tableView);

       
        Label header = new Label("KOL Ranking Table");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black;");

      
        HBox searchBar = new HBox(10, searchBox, searchButton);
        searchBar.setAlignment(Pos.CENTER);
        searchBar.setStyle("-fx-padding: 10;");


        root = new VBox(10, header, searchBar, tableView);
        root.setStyle("-fx-padding: 10;");

        
        searchButton.setOnAction(e -> controller.loadSearchResults());
    }

    /**
     * 
     *
     * @return 
     */
    public VBox getRoot() {
        return root;
    }
}
