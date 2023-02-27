package com.mycompany.proyectopoog7parte2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    private static Scene scene;
    public static String pathConcursos = "files/contests.ser";
    public static String pathMascotas = "files/mascots.csv";
    public static String pathDuenios = "files/owners.csv";
    public static String pathAuspiciantes = "files/sponsors.ser";
    public static String pathPremios = "files/awards.ser";
    public static String pathCiudades= "files/cities.csv";


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("menuPrincipal"), 680, 640);
        stage.setScene(scene);
        stage.setTitle("Amigos peludos EC");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    //metodo para cambiar el contenido de la escena
    static void changeRoot(Parent rootNode) {
        scene.setRoot(rootNode);
    }

}