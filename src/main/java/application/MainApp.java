package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.SearchView;
import view.ChartView;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        String[] keywords = {"Blockchain", "DeFi", "Web3", "Bitcoin", "Ethereum", "NFT", "Crypto", "Memecoin", "All"};

       
        SearchView searchView = new SearchView(keywords);
        ChartView chartView = new ChartView(keywords);

        
        Button searchButton = new Button("Search Table");
        Button chartButton = new Button("KOL Chart");
        searchButton.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
        chartButton.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

  
        BorderPane menuPane = new BorderPane();
        VBox buttonBox = new VBox(10); 
        buttonBox.setAlignment(Pos.TOP_CENTER); 
        buttonBox.getChildren().addAll(searchButton, chartButton);
        menuPane.setLeft(buttonBox);

        BorderPane root = new BorderPane();
        root.setLeft(menuPane);
        root.setCenter(searchView.getRoot()); 

        searchButton.setOnAction(e -> root.setCenter(searchView.getRoot()));
        chartButton.setOnAction(e -> root.setCenter(chartView.getRoot()));

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("KOL Ranking App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
