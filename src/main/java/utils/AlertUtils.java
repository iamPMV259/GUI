package utils;

import javafx.scene.control.Alert;

public class AlertUtils {
    /**
     * 
     *
     * @param title   
     * @param message 
     */
    public static void showError(String title, String message) {
        showAlert(title, message, Alert.AlertType.ERROR);
    }

    /**
     * 
     *
     * @param title   
     * @param message 
     */
    public static void showInfo(String title, String message) {
        showAlert(title, message, Alert.AlertType.INFORMATION);
    }

    /**
     * 
     *
     * @param title   
     * @param message 
     */
    public static void showWarning(String title, String message) {
        showAlert(title, message, Alert.AlertType.WARNING);
    }

    /**
     * 
     *
     * @param title   
     * @param message 
     * @param type    
     */
    public static void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
