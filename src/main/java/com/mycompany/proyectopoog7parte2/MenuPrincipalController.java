/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectopoog7parte2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


public class MenuPrincipalController implements Initializable {

    @FXML
    private Button opAdmConcurso;
    @FXML
    private Button opAdmMascotas;
    @FXML
    private Button opAdmDueños;
    @FXML
    private Button opAdminCiudades;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void switchToAdministrarConcursos() throws  IOException{
        App.setRoot("administrarConcurso");
    }

    @FXML
    private void switchToAdministrarMascotas() throws  IOException{
        App.setRoot("administrarMascotas");
    }

    @FXML
    private void switchToAdministrarDuenios() throws  IOException{
        App.setRoot("administrarDueños");
    }

    @FXML
    private void switchToAdministrarCiudades() throws  IOException {
        App.setRoot("administrarCiudades");
    }

    @FXML
    private void switchToAdministrarAuspiciantes() throws IOException{
        App.setRoot("administrarAuspiciantes");
    }
    
}
