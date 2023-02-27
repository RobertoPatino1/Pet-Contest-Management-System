
package com.mycompany.proyectopoog7parte2;

import com.mycompany.modelo.Auspiciante;
import com.mycompany.modelo.Premio;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AniadirPremioController{


    @FXML
    private TextField txtLugar;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private ComboBox<Auspiciante> cbAuspiciante;
    @FXML
    private Label lblAniadirPremio;
   
  
    public void initialize() {
        //Se asignan los valores al combo
        cbAuspiciante.getItems().setAll(Auspiciante.cargarListaAuspiciantes());
    }    
    
    @FXML
    private void guardarPremio(ActionEvent event) {
        try{
            
            String descripcion = txtDescripcion.getText();
            Auspiciante auspiciante = cbAuspiciante.getValue();
            if(txtLugar.getText().equals("")||descripcion.equals("")||auspiciante.equals(null)){
                throw new NullPointerException();
            }
            int lugar = Integer.valueOf(txtLugar.getText());
            
            //Se procede a crear el objeto
            Premio premio = new Premio(lugar, descripcion, auspiciante);
            

            CrearConcursoController.concursoCrear.agregarPremio(premio);
                
           
            Alert exito = new Alert(Alert.AlertType.INFORMATION,"Se ha creado el premio de manera exitosa");
            exito.setHeaderText("Creacion de premio");
            exito.show();

            cerrarVentana();
            
        }catch(NumberFormatException e){
            System.out.println("Se ingresaron letras en lugar de numeros");
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error al llenar el campo 'lugar'");
            error.setContentText("Asegurese de solo ingresar numeros enteros");
            error.show();
        }catch(NullPointerException e){
            System.out.println("No se llenaron todos los campos");
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error al llenar los campos");
            error.setContentText("Asegurese de llenar todos los campos correctamente");
            error.show();
        }
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) lblAniadirPremio.getScene().getWindow();
        CrearConcursoController c = new CrearConcursoController();
        
        stage.close();
        
        
    }
    
    private void llenarTableView(ArrayList<Premio> lista){
        CrearConcursoController c = new CrearConcursoController();
        c.tvPremios.getItems().addAll(lista);
    }

}
