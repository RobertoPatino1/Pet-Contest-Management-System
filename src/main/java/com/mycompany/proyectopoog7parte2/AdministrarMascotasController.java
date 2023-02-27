/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectopoog7parte2;

import com.mycompany.modelo.DuenioMascota;
import com.mycompany.modelo.Mascota;
import com.mycompany.modelo.TipoAnimal;
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
/**
 * FXML Controller class
 *
 * @author Det-Pc
 */
public class AdministrarMascotasController{


    @FXML
    private Button opAgregarMascota;
    @FXML
    private Button opCancelarAdMascota;
    @FXML
    private TableView tvMascotas;
    @FXML
    private TableColumn<Mascota, Integer> tcCodigo;
    @FXML
    private TableColumn<Mascota, String> tcNombre;
    @FXML
    private TableColumn<Mascota, TipoAnimal> tcTipoMascota;
    @FXML
    private TableColumn<Mascota, DuenioMascota> tcDuenio;
    @FXML
    private TableColumn<Mascota, Void> tcOpciones;

 
    public void initialize() {
        //System.out.println(Mascota.cargarMascotas("archivos/mascotas.csv"));
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcTipoMascota.setCellValueFactory(new PropertyValueFactory<>("tipoMascota"));
        tcDuenio.setCellValueFactory(new PropertyValueFactory<>("duenio"));
        
        //Se agregan las opciones al table view
        agregarOpciones();
        
        tvMascotas.getItems().addAll(Mascota.cargarMascotas(App.pathMascotas));
        
        
        
    }    
    
    @FXML
    private void switchToCrearMascota() throws IOException {
        App.setRoot("crearMascota");
    }

    @FXML
    private void switchToMenuPrincipal() throws IOException {
        App.setRoot("menuPrincipal");
    }
    
    
    
    
    
    //Metodo para editar una mascota
    private void editarMascota(Mascota m){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("crearMascota.fxml"));//no tiene el controlador especificado
            Parent root = (Parent) fxmlLoader.load();
            CrearMascotaController ct = fxmlLoader.getController();
            fxmlLoader.setController(ct);//se asigna el controlador
            
            //Se llena el combo y los campos del duenio a editar
            ct.llenarComboDuenioMascota(DuenioMascota.cargarDuenios(App.pathDuenios));
            ct.llenarCamposMascota(m);
            
            //Se cambia a la escena de edicion
            App.changeRoot(root);
            
        }catch(IOException e){
            System.out.println("Error IO: "+e);
        }catch(Exception e){
            System.out.println("Excepcion general al cambiar de escena: "+e);
        }
    }
    
    
    
    //Metodo para eliminar una mascota
        
    private void eliminarMascota(Mascota m){
        //Se muestra la alerta
        //Primero se muestra la alerta
        try{
        Alert alerta = new Alert(Alert.AlertType.WARNING,"Recuerde que esta acción es irreversible. \n¿Seguro que desea continuar?");
        
        alerta.setHeaderText("Eliminacion de mascota");
        alerta.showAndWait();
        
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setHeaderText("Eliminacion");
        confirmacion.setContentText("Se procederá a eliminar la mascota de los registros.\n ¿Continuar?");
        
        Optional<ButtonType> result = confirmacion.showAndWait();
        if(result.get()==ButtonType.OK){
            //Se obtiene la lista de duenios
            ArrayList<Mascota> lista = Mascota.cargarMascotas(App.pathMascotas);
            System.out.println(lista.contains(m));
            
            //Se obtiene el indice del duenio a eliminar
            int indice = lista.indexOf(m);
            System.out.println(lista);
            System.out.println(indice);
            
            //Se elimina al duenio en el indice obtenido
            lista.remove(indice); 
            System.out.println(lista);
            
            
            //Se actualiza el archivo
            Mascota.actualizarListaMascotas(lista, App.pathMascotas);
            System.out.println("Se ha eliminado la mascota: "+m.getNombre());
            App.setRoot("administrarMascotas");


        }else{
            System.out.println("No problem");
        }
        }catch(Exception e){
            System.out.println("Error al eliminar la mascota: "+e.getLocalizedMessage());
            e.printStackTrace();
            e.getCause();
           
        }     
    }
    
    //Metodo para visualizar una mascota
   
    private void visualizarDetalleMascota(Mascota m){

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("detalleMascota.fxml"));//no tiene el controlador especificado
            Parent root = (Parent) fxmlLoader.load();
            DetalleMascotaController ct = fxmlLoader.getController();
            fxmlLoader.setController(ct);//se asigna el controlador
            
            ct.llenarCamposMascota(m);
            //Se cambia a la escena de edicion
            App.changeRoot(root);
            
        }catch(IOException e){
            System.out.println("Error IO: "+e);
        }catch(Exception e){
            System.out.println("Excepcion general al cambiar de escena: "+e);
        }
        
    }
    
    
    //Codigo para crear y manejar los eventos en los botones
    //Metodo para agregar los botones a la columna opciones
    private void agregarOpciones() {
        Callback<TableColumn<Mascota, Void>, TableCell<Mascota, Void>> cellFactory = new Callback<TableColumn<Mascota, Void>, TableCell<Mascota, Void>>() {
            @Override
            public TableCell<Mascota, Void> call(final TableColumn<Mascota, Void> param) {
                TableCell<Mascota, Void> cell = new TableCell<Mascota, Void>() {
                   
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            //hbox para ubicar los botones
                            HBox hbOpciones = new HBox(5);
                            Mascota mascota = getTableView().getItems().get(getIndex());
                            Button btnEd = new Button("Editar");
                            btnEd.setOnAction(e ->editarMascota(mascota));
                            Button btnEl = new Button("Eliminar");
                            btnEl.setOnAction(e -> eliminarMascota(mascota));
                            
                            //boton detalle
                            Button btnDetalle = new Button("Detalle");
                            btnDetalle.setOnAction(e -> visualizarDetalleMascota(mascota));
                            
                            //se agregan botones al hbox
                            hbOpciones.getChildren().addAll(btnDetalle,btnEd,btnEl);
                            //se ubica hbox en la celda
                            setGraphic(hbOpciones);
                        }
                    }
                };
                return cell;
            }
        };

        tcOpciones.setCellFactory(cellFactory);

    }
    
    
    
    
    
    

}
