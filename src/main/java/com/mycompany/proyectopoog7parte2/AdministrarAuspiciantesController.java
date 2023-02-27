/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectopoog7parte2;

import com.mycompany.modelo.Auspiciante;
import com.mycompany.modelo.Ciudad;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;


public class AdministrarAuspiciantesController{

    @FXML
    private TableView tvAdministrarAuspiciantes;
    @FXML
    private TableColumn<Auspiciante, Integer> colCodigo;
    @FXML
    private TableColumn<Auspiciante, String> colNombre;
    @FXML
    private TableColumn<Auspiciante, String> colTelefono;
    @FXML
    private TableColumn<Auspiciante, Ciudad> colCiudad;
    @FXML
    private TableColumn<Auspiciante, Void> colOpciones;

 
   
    public void initialize() {
        // TODO
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        
        //Se agregan las opciones al table view
        agregarOpciones();
        
        tvAdministrarAuspiciantes.getItems().addAll(Auspiciante.cargarListaAuspiciantes(App.pathAuspiciantes));
        
    }    

    @FXML
    private void switchToCrearAuspiciante() throws IOException{
        App.setRoot("crearAuspiciantes");
    }

    @FXML
    private void switchToMenuPrincipal() throws IOException {
        App.setRoot("menuPrincipal");
    }
    
    
    //Metodos de edicion y eliminacion
    private void editarAuspiciante(Auspiciante au){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("crearAuspiciantes.fxml"));//no tiene el controlador especificado
            Parent root = (Parent) fxmlLoader.load();
            CrearAuspiciantesController ct = fxmlLoader.getController();
            fxmlLoader.setController(ct);//se asigna el controlador
            
            //Se llena el combo y los campos del duenio a editar
            ct.llenarComboCiudad(Ciudad.cargarCiudades(App.pathCiudades));
            ct.llenarCamposAuspiciante(au);
            
            //Se cambia a la escena de edicion
            App.changeRoot(root);
            
        }catch(IOException e){
            System.out.println("Error IO: "+e);
        }catch(Exception e){
            System.out.println("Excepcion general al cambiar de escena: "+e);
        }
    }
    
    
    //Metodo para eliminar un auspiciante

    private void eliminarAuspiciante(Auspiciante au){

        //Primero se muestra la alerta
        try{
        Alert alerta = new Alert(Alert.AlertType.WARNING,"Recuerde que esta acción es irreversible. \n¿Seguro que desea continuar?");
        
        alerta.setHeaderText("Eliminacion de auspiciante");
        alerta.showAndWait();
        
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setHeaderText("Eliminacion");
        confirmacion.setContentText("Se procederá a eliminar al auspiciante de los registros.\n ¿Continuar?");


        Optional<ButtonType> result = confirmacion.showAndWait();
        if(result.get()==ButtonType.OK){
            //Se obtiene la lista de duenios
            ArrayList<Auspiciante> lista = Auspiciante.cargarListaAuspiciantes(App.pathAuspiciantes);
            System.out.println(lista.contains(au));
            
            //Se obtiene el indice del duenio a eliminar
            int indice = lista.indexOf(au);
            System.out.println(lista);
            System.out.println(indice);
            
            //Se elimina al auspiciante en el indice obtenido
            lista.remove(indice); 
            System.out.println(lista);
            
            //Se actualiza el archivo
            Auspiciante.actualizarListaAuspiciantes(lista, App.pathAuspiciantes);
            System.out.println("Se ha eliminado al auspiciante: "+au.getNombre());
            App.setRoot("administrarAuspiciantes");


        }else{
            System.out.println("No problem");
        }
        }catch(Exception e){
            System.out.println("Error al eliminar el auspiciante: "+e.getLocalizedMessage());
            e.printStackTrace();
            e.getCause();
           
        }     
    }
    
    
    
    //Codigo para crear y manejar los eventos en los botones
    //Metodo para agregar los botones a la columna opciones
    private void agregarOpciones() {
        Callback<TableColumn<Auspiciante, Void>, TableCell<Auspiciante, Void>> cellFactory = new Callback<TableColumn<Auspiciante, Void>, TableCell<Auspiciante, Void>>() {
            @Override
            public TableCell<Auspiciante, Void> call(final TableColumn<Auspiciante, Void> param) {
                TableCell<Auspiciante, Void> cell = new TableCell<Auspiciante, Void>() {
                   
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            //hbox para ubicar los botones
                            HBox hbOpciones = new HBox(5);
                            //recuperar el duenio
                            //Ciudad ciudad = getTableView().getItems().get(getIndex());
                            Auspiciante auspiciante = getTableView().getItems().get(getIndex());
                  
                            //boton editar
                            Button btnEd = new Button("Editar");
                            btnEd.setOnAction(e ->editarAuspiciante(auspiciante));
                               
                            //boton eliminar
                            Button btnEl = new Button("Eliminar");
                            btnEl.setOnAction(e -> eliminarAuspiciante(auspiciante));
                            //se agregan botones al hbox
                            hbOpciones.getChildren().addAll(btnEd,btnEl);
                            //se ubica hbox en la celda
                            setGraphic(hbOpciones);
                        }
                    }
                };
                return cell;
            }
        };

        colOpciones.setCellFactory(cellFactory);

    }
    
}
