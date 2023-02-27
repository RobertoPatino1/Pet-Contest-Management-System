/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectopoog7parte2;

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

public class AdministrarCiudadesController{


    @FXML
    private TableView tvCiudades;
    @FXML
    private TableColumn<Ciudad, Integer> colId;
    @FXML
    private TableColumn<Ciudad, String> colCiudad;
    @FXML
    private TableColumn<Ciudad, String> colProvincia;
    @FXML
    private TableColumn<Ciudad, Void> colOpciones;

    /**
     * Initializes the controller class.
     */
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colProvincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        
        //Se agregan las opciones
        agregarOpciones();
        tvCiudades.getItems().setAll(Ciudad.cargarCiudades(App.pathCiudades));
    }    

    @FXML
    private void switchToMenuPrincipal() throws IOException{
        App.setRoot("menuPrincipal");
    }

    @FXML
    private void switchToCrearCiudad() throws IOException{
        App.setRoot("crearCiudad");
    }
    
    
    //Metodo para agregar los botones a la columna opciones
    private void agregarOpciones() {

        Callback<TableColumn<Ciudad, Void>, TableCell<Ciudad, Void>> cellFactory = new Callback<TableColumn<Ciudad, Void>, TableCell<Ciudad, Void>>() {
            @Override
            public TableCell<Ciudad, Void> call(final TableColumn<Ciudad, Void> param) {
                TableCell<Ciudad, Void> cell = new TableCell<Ciudad, Void>() {
                   
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            //hbox para ubicar los botones
                            HBox hbOpciones = new HBox(5);
                            //recuperar el empleado de la fila
                            Ciudad ciudad = getTableView().getItems().get(getIndex());
                  
                            //boton editar
                            Button btnEd = new Button("Editar");
                            btnEd.setOnAction(e ->editarCiudad(ciudad));
                               
                            //boton eliminar
                            Button btnEl = new Button("Eliminar");
                            btnEl.setOnAction(e -> eliminarCiudad(ciudad));
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


    private void editarCiudad(Ciudad c){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("crearCiudad.fxml"));//no tiene el controlador especificado
            //CrearCiudadController ct = new CrearCiudadController(); //Recien aqui se esta creando el controlador
            Parent root = (Parent) fxmlLoader.load();
            CrearCiudadController ct = fxmlLoader.getController();
            fxmlLoader.setController(ct);//se asigna el controlador
            
            
            
            ct.llenarCombo(Ciudad.cargarCiudades("archivos/ciudades.csv"));
            ct.llenarCampos(c);

            App.changeRoot(root);
        }catch (IOException ex) {
            System.out.println("Error fatal: "+ex);
            
            
        }catch(Exception e){
            System.out.println("Excepcion general: "+e);
        }   
    }
    
    private void eliminarCiudad(Ciudad c) {

        
        //Primero se muestra la alerta
        try{
        Alert alerta = new Alert(Alert.AlertType.WARNING,"Recuerde que esta acción es irreversible. \n¿Seguro que desea continuar?");
        
        alerta.setHeaderText("Eliminacion de ciudad");
        alerta.showAndWait();
        
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setHeaderText("Eliminacion");
        confirmacion.setContentText("Se procederá a eliminar la ciudad de los registros.\n ¿Continuar?");

        
        //confirmacion.show();

        Optional<ButtonType> result = confirmacion.showAndWait();
        if(result.get()==ButtonType.OK){
            //Se elimina el objeto de la lista
            ArrayList<Ciudad> lista = Ciudad.cargarCiudades("archivos/ciudades.csv");
            System.out.println(lista.contains(c));
            int indice = lista.indexOf(c);
            System.out.println(lista);
            System.out.println(indice);
            lista.remove(indice); 
            
            //Se actualiza el archivo
            Ciudad.actualizarListaCiudades(lista, "archivos/ciudades.csv");
            System.out.println("Se ha eliminado la ciudad: "+c);
            App.setRoot("administrarCiudades");


        }else{
            System.out.println("No problem");
        }
        }catch(Exception e){
            System.out.println("Error al eliminar la ciudad: "+e.getLocalizedMessage());
            e.printStackTrace();
            e.getCause();
           
        }
        
        
    }
    
    
}
