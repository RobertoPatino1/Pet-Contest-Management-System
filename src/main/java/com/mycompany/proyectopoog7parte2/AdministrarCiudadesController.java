
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
import utils.AlertGenerator;

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
        
        agregarOpciones();
        tvCiudades.getItems().setAll(Ciudad.cargarCiudades());
    }    

    @FXML
    private void switchToMenuPrincipal() throws IOException{
        App.setRoot("menuPrincipal");
    }

    @FXML
    private void switchToCrearCiudad() throws IOException{
        App.setRoot("crearCiudad");
    }
    
    
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
            Parent root = (Parent) fxmlLoader.load();
            CrearCiudadController ct = fxmlLoader.getController();
            fxmlLoader.setController(ct);
            
            
            
            ct.llenarCombo(Ciudad.cargarCiudades());
            ct.llenarCampos(c);

            App.changeRoot(root);
        }catch (IOException ex) {
            System.out.println("Error fatal: "+ex);
            
            
        }catch(Exception e){
            System.out.println("Excepcion general: "+e);
        }   
    }
    
    private void eliminarCiudad(Ciudad c) {

        try{
            Alert confirmacion = AlertGenerator.generateAlert(Alert.AlertType.CONFIRMATION,null,"Eliminacion","Se procederá a eliminar la ciudad de los registros.\n ¿Continuar?");

            Optional<ButtonType> result = confirmacion.showAndWait();
            if(result.get()==ButtonType.OK){
                ArrayList<Ciudad> lista = Ciudad.cargarCiudades();
                int indice = lista.indexOf(c);
                lista.remove(indice);           
                Ciudad.actualizarListaCiudades(lista);
                App.setRoot("administrarCiudades");
            }
        }catch(Exception e){
            System.out.println("Error al eliminar la ciudad: "+e.getLocalizedMessage());
            e.printStackTrace();
            e.getCause();
           
        }
        
        
    }
    
    
}
