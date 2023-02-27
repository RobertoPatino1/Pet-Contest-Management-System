
package utils;

import javafx.scene.control.Alert;


public class AlertGenerator {
    
    public static Alert generateAlert(Alert.AlertType alertType,String title,String header, String body){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(body);
        return alert;
    }
}
