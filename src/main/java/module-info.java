module com.mycompany.proyectopoog7parte2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.mail;
    
    opens com.mycompany.modelo to javafx.base;
    opens com.mycompany.proyectopoog7parte2 to javafx.fxml;
    exports com.mycompany.proyectopoog7parte2;
}
