
package com.mycompany.proyectopoog7parte2;

import com.mycompany.modelo.Ciudad;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CrearCiudadController{


    @FXML
    private TextField txtNombre;
    
    @FXML
    private Label lblCrearCiudad;
    @FXML
    private ComboBox<String> cbProvincia;
    @FXML
    private Button buttonActualizar;
    @FXML
    private Button buttonGuardar;
    private Ciudad ciudadOG;
    /**
     * Initializes the controller class.
     */
    
    public void initialize() {
        buttonActualizar.setVisible(false);
     
        
        ArrayList<String> listaProvincias = new ArrayList<>();
        
        llenarCombo(Ciudad.cargarCiudades());
    }    
    
    @FXML
    private void guardarCiudad(ActionEvent event) throws IOException{
        try{
            //Recuperando el nombre de la ciudad
            String nombreCiudad = txtNombre.getText();
            //Recuperando la seleccion del combo box
            String nombreProvincia = cbProvincia.getValue();
            if(nombreProvincia.equals(null)||nombreCiudad.equals("")){
               throw new NullPointerException();
            }
            //Se recupera el ultimo id
            int id = Ciudad.cargarCiudades().size()+1;
            //Creamos el objeto
            Ciudad c = new Ciudad(id, nombreCiudad, nombreProvincia);
            //Se la escribe en el archivo
            Ciudad.escribirCiudad(c);
            
            
            System.out.println("Se ha creado y agg la ciudad exitosamente");
            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setHeaderText("Crear ciudad");
            exito.setContentText("Se ha creado la ciudad de manera exitosa");
            exito.show();
            App.setRoot("administrarCiudades");
        }catch(NullPointerException e){
            System.out.println("No se han llenado todos los campos");
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Ha ocurrido un error:");
            error.setContentText("Asegurese de llenar o seleccionar todos los campos");
            error.show();
        }
    }

    @FXML
    private void cancelar() throws IOException{
        App.setRoot("administrarCiudades");
    }
    
    
    //Metodos para llenar campos
    public void llenarCombo(ArrayList<Ciudad> ciudades){
        cbProvincia.getItems().clear();
        ArrayList<String> listaProvincias = new ArrayList<>();
        //Vamos a llenar el combo box con los nombres de provincias de las ciudades

        for(Ciudad c:ciudades){
            if(!listaProvincias.contains(c.getProvincia())){
                
                
                listaProvincias.add(c.getProvincia());
                
            }
            
        }
        cbProvincia.getItems().setAll(listaProvincias);
    }
    
    public void llenarCampos(Ciudad c){
        ciudadOG = c;
        System.out.println(c);
        System.out.println(ciudadOG.getId());
        buttonGuardar.setVisible(false); //Volvemos el boton de guardar como invisible
        buttonActualizar.setVisible(true); //Y volvemos este como visible
        lblCrearCiudad.setText("Editar ciudad");
        txtNombre.setText(c.getNombre());
        cbProvincia.setValue(c.getProvincia());
        cbProvincia.setDisable(true);
   

    }

    @FXML
    private void actualizarCiudad(){
        try{
        //Primero recuperamos el indice
        int indice = ciudadOG.getId()-1;
        System.out.println(ciudadOG);
        //Entramos a la lista y accedemos al objeto en ese indice
        ArrayList<Ciudad> listaCiudades = Ciudad.cargarCiudades();
        
        Ciudad ciudadActualizar = listaCiudades.get(indice);
        
        ciudadActualizar.setNombre(txtNombre.getText());
        //Se valida que el nombre no sea vacio
        if(ciudadActualizar.getNombre().equals("")){
            throw new NullPointerException();
        }
        //Se asigna la ciudad en la lista
        System.out.println(ciudadActualizar);
        listaCiudades.set(indice, ciudadActualizar);
        
        
        //Se actualiza la lista de ciudades
        Ciudad.actualizarListaCiudades(listaCiudades);

        
        //Le mostramos un aviso al usuario
        Alert confirmacion = new Alert(Alert.AlertType.INFORMATION,"Recuerde que este cambio no será aplicado a los elementos" +"\n"+"ya registrados en el sistema");
        confirmacion.setHeaderText("Se ha actualizado la ciudad de manera exitosa");
        confirmacion.show();
        
        App.setRoot("administrarCiudades");
        }catch(IOException e){
            System.out.println("Error al crear el objeto: "+e);
        }catch(NullPointerException e){
            System.out.println("No se han llenado todos los campos");
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Ha ocurrido un error:");
            error.setContentText("Asegurese de llenar o seleccionar todos los campos");
            error.show();
        }catch(IndexOutOfBoundsException e){
            System.out.println("Se atrapo el error");
        }
    }   

}
