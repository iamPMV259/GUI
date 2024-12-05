package application;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SearchCsvApp extends Application {

    private TableView<ObservableList<String>> tableView;
    private ComboBox<String> searchBox;
    private BarChart<String, Number> barChart;

    private final String folderPath = "src/main/java/data";
    private final List<String> keywords = Arrays.asList(
            "Blockchain", "DeFi", "Web3", "Bitcoin", "Ethereum", "NFT", "Crypto", "All"
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("KOL Ranking App");

        // Thanh menu bên trái
        VBox menuBar = new VBox();
        menuBar.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0; -fx-spacing: 10;");
        Button searchButton = new Button("Search Table");
        Button chartButton = new Button("KOL Chart");
        menuBar.getChildren().addAll(searchButton, chartButton);

        // Layout Search Table
        VBox searchLayout = createSearchLayout();

        // Layout KOL Chart
        VBox chartLayout = createChartLayout();

        // Container chính với BorderPane
        BorderPane root = new BorderPane();
        root.setLeft(menuBar);
        root.setCenter(searchLayout); // Mặc định là giao diện Search Table

        // Sự kiện khi nhấn các nút trong thanh menu
        searchButton.setOnAction(e -> root.setCenter(searchLayout));
        chartButton.setOnAction(e -> root.setCenter(chartLayout));

        // Scene và Stage
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createSearchLayout() {
        Label header = new Label("KOL Ranking Table");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black; -fx-alignment: center; -fx-padding: 10;");

        searchBox = new ComboBox<>(FXCollections.observableArrayList(keywords));
        searchBox.setPromptText("Select a keyword to search");
        Button searchButton = new Button("Search");

        tableView = new TableView<>();

        HBox searchBar = new HBox(10, searchBox, searchButton);
        searchBar.setStyle("-fx-padding: 10; -fx-alignment: center;");

        VBox layout = new VBox(10, header, searchBar, tableView);

        searchButton.setOnAction(e -> searchAndLoadCsv());
        searchBox.setOnAction(e -> {
            clearTable();
            searchAndLoadCsv();
        });

        return layout;
    }

    private VBox createChartLayout() {
        Label header = new Label("KOL Chart");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black; -fx-alignment: center; -fx-padding: 10;");

        ComboBox<String> chartSearchBox = new ComboBox<>(FXCollections.observableArrayList(keywords));
        chartSearchBox.setPromptText("Select a keyword to search");
        Button loadChartButton = new Button("Load Chart");

        // Tạo biểu đồ
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Username");
        yAxis.setLabel("Ranking Points");
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Ranking Top 10 KOLS For Each HashTag");

        HBox searchBar = new HBox(10, chartSearchBox, loadChartButton);
        searchBar.setStyle("-fx-padding: 10; -fx-alignment: center;");

        VBox layout = new VBox(10, header, searchBar, barChart);

        loadChartButton.setOnAction(e -> {
            String keyword = chartSearchBox.getValue();
            if (keyword != null && !keyword.isEmpty()) {
                loadChart(keyword);
            } else {
                showAlert("Error", "Please select a keyword to load chart.", Alert.AlertType.ERROR);
            }
        });

        return layout;
    }

    private void searchAndLoadCsv() {
        String keyword = searchBox.getValue();
        if (keyword == null || keyword.isEmpty()) {
            showAlert("Error", "Please select a keyword to search.", Alert.AlertType.ERROR);
            return;
        }

        clearTable();

        if (keyword.equals("All")) {
            loadAllCsvFiles();
        } else {
            File csvFile = new File(folderPath, keyword + ".csv");
            if (csvFile.exists()) {
                loadCsvFile(csvFile);
            } else {
                showAlert("Error", "File not found: " + csvFile.getName(), Alert.AlertType.ERROR);
            }
        }
    }

    
    private void loadChart(String keyword) {
        barChart.getData().clear();
    
        File csvFile = new File(folderPath, keyword + ".csv");
        if (!csvFile.exists()) {
            showAlert("Error", "File not found: " + csvFile.getName(), Alert.AlertType.ERROR);
            return;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstRow = true;
    
            // Dữ liệu tạm thời để lọc top 10
            ObservableList<XYChart.Data<String, Number>> dataList = FXCollections.observableArrayList();
    
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
    
                if (isFirstRow) {
                    isFirstRow = false; // Bỏ qua dòng tiêu đề
                } else {
                    String username = values[1]; // Cột Username
                    double rankingPoint = Double.parseDouble(values[2]); // Cột Ranking Points
                    dataList.add(new XYChart.Data<>(username, rankingPoint));
                }
            }
    
            // Lọc top 10 người có điểm cao nhất
            dataList.sort((a, b) -> Double.compare(b.getYValue().doubleValue(), a.getYValue().doubleValue())); // Sắp xếp giảm dần
            ObservableList<XYChart.Data<String, Number>> top10Data = FXCollections.observableArrayList(
                    dataList.subList(0, Math.min(10, dataList.size())));
    
            // Thêm dữ liệu vào biểu đồ
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Top 10 for " + keyword);
    
            for (XYChart.Data<String, Number> data : top10Data) {
                series.getData().add(data);
            }
    
            barChart.getData().add(series);
    
            // Điều chỉnh khoảng cách và giao diện
            CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
            xAxis.setTickLabelRotation(45); // Xoay nhãn trục x để tránh đè
            xAxis.setTickLabelGap(15); // Tăng khoảng cách giữa nhãn
            barChart.setBarGap(5); // Tăng khoảng cách giữa các cột
            barChart.setCategoryGap(30); // Tăng khoảng cách giữa các nhóm
        } catch (IOException | NumberFormatException ex) {
            showAlert("Error", "Failed to load the chart: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    
    

    private void loadAllCsvFiles() {
        File folder = new File(folderPath);
        File[] csvFiles = folder.listFiles((dir, name) -> name.endsWith(".csv"));

        if (csvFiles == null || csvFiles.length == 0) {
            showAlert("Error", "No CSV files found in the folder.", Alert.AlertType.ERROR);
            return;
        }

        for (File csvFile : csvFiles) {
            loadCsvFile(csvFile);
        }
    }

    private void loadCsvFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstRow = tableView.getColumns().isEmpty();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (isFirstRow) {
                    for (int i = 0; i < values.length; i++) {
                        final int colIndex = i;
                        TableColumn<ObservableList<String>, String> column = new TableColumn<>(values[i]);
                        column.setCellValueFactory(data ->
                                new SimpleStringProperty(data.getValue().get(colIndex)));
                        tableView.getColumns().add(column);
                    }
                    isFirstRow = false;
                } else {
                    ObservableList<String> row = FXCollections.observableArrayList(values);
                    tableView.getItems().add(row);
                }
            }
        } catch (IOException ex) {
            showAlert("Error", "Failed to load the CSV file: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void clearTable() {
        tableView.getItems().clear();
        tableView.getColumns().clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
