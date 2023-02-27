
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
import utils.AlertGenerator;


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

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        
        addTableButtons();
        
        tvAdministrarAuspiciantes.getItems().addAll(Auspiciante.cargarListaAuspiciantes());
        
    }    

    @FXML
    private void switchToCrearAuspiciante() throws IOException{
        App.setRoot("crearAuspiciantes");
    }

    @FXML
    private void switchToMenuPrincipal() throws IOException {
        App.setRoot("menuPrincipal");
    }
    
    

    private void editarAuspiciante(Auspiciante au){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("crearAuspiciantes.fxml"));//no tiene el controlador especificado
            Parent root = (Parent) fxmlLoader.load();
            CrearAuspiciantesController ct = fxmlLoader.getController();
            fxmlLoader.setController(ct);
            

            ct.llenarComboCiudad(Ciudad.cargarCiudades());
            ct.llenarCamposAuspiciante(au);
            

            App.changeRoot(root);
            
        }catch(IOException e){
            System.out.println("Error IO: "+e);
        }catch(Exception e){
            System.out.println("Excepcion general al cambiar de escena: "+e);
        }
    }
    
    


    private void eliminarAuspiciante(Auspiciante au){
        try{
            Alert confirmacion = AlertGenerator.generateAlert(Alert.AlertType.CONFIRMATION,null,"Eliminacion","Se procederá a eliminar al auspiciante de los registros.\n ¿Continuar?");

            Optional<ButtonType> result = confirmacion.showAndWait();
            if(result.get()==ButtonType.OK){
                ArrayList<Auspiciante> lista = Auspiciante.cargarListaAuspiciantes();
                lista.remove(lista.indexOf(au)); 

                Auspiciante.actualizarListaAuspiciantes(lista);
                App.setRoot("administrarAuspiciantes");
            }
            
            }catch(IOException e){
                System.out.println("Error al eliminar el auspiciante: "+e.getLocalizedMessage());
                e.printStackTrace();
                e.getCause();

            }     
    }
    
    
    private void addTableButtons() {
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
                            HBox hboxOptions = new HBox(5);
                            Auspiciante sponsor = getTableView().getItems().get(getIndex());
                            createTableButtons(sponsor, hboxOptions);
                            //se ubica hbox en la celda
                            setGraphic(hboxOptions);
                        }
                    }
                };
                return cell;
            }
        };

        colOpciones.setCellFactory(cellFactory);

    }
    private void createTableButtons(Auspiciante sponsor,HBox hboxOptions){
        //boton editar
        Button btnEd = new Button("Editar");
        btnEd.setOnAction(e ->editarAuspiciante(sponsor));

        //boton eliminar
        Button btnEl = new Button("Eliminar");
        btnEl.setOnAction(e -> eliminarAuspiciante(sponsor));
        //se agregan botones al hbox
        hboxOptions.getChildren().addAll(btnEd,btnEl);
    }
    
}
