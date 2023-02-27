
package com.mycompany.proyectopoog7parte2;

import com.mycompany.modelo.Ciudad;
import com.mycompany.modelo.DuenioMascota;
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

public class AdministrarDueñosController{


    @FXML
    private Button opAgregarDueño;
    @FXML
    private Button opCancelarAdDueñoM;
    @FXML
    private TableView tvDuenios;
    @FXML
    private TableColumn<DuenioMascota, Integer> colCodigo;
    @FXML
    private TableColumn<DuenioMascota, String> colNombres;
    @FXML
    private TableColumn<DuenioMascota, String> colApellidos;
    @FXML
    private TableColumn<DuenioMascota, String> colTelefono;
    @FXML
    private TableColumn<DuenioMascota, Ciudad> colCiudad;
    @FXML
    private TableColumn<DuenioMascota, Void> colOpciones;
   
    public void initialize() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        
        agregarOpciones();
        
        tvDuenios.getItems().setAll(DuenioMascota.cargarDuenios());

    }
    
    
    @FXML
    private void switchToAgregarDuenio() throws IOException{
        App.setRoot("agregarDueño");
    }

    @FXML
    private void switchToMenuPrincipal() throws IOException{
        App.setRoot("menuPrincipal");
        
    }
    
    
    private void editarDuenio(DuenioMascota d){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("agregarDueño.fxml"));//no tiene el controlador especificado
            Parent root = (Parent) fxmlLoader.load();
            AgregarDueñoController ct = fxmlLoader.getController();
            fxmlLoader.setController(ct);//se asigna el controlador
            ct.llenarComboCiudad(Ciudad.cargarCiudades());
            ct.llenarCamposDuenio(d);
            App.changeRoot(root);
            
        }catch(IOException e){
            System.out.println("Error IO: "+e);
        }catch(Exception e){
            System.out.println("Excepcion general al cambiar de escena: "+e);
        }
    }
    
    
    
    private void eliminarDuenio(DuenioMascota d){

        try{


            Alert confirmacion = AlertGenerator.generateAlert(Alert.AlertType.CONFIRMATION,null,"Eliminacion","Se procederá a eliminar al duño de los registros.\n ¿Continuar?");
            Optional<ButtonType> result = confirmacion.showAndWait();
            if(result.get()==ButtonType.OK){
                ArrayList<DuenioMascota> lista = DuenioMascota.cargarDuenios();
                int indice = lista.indexOf(d);
                lista.remove(indice); 
                DuenioMascota.actualizarListaDuenios(lista);
                App.setRoot("administrarDueños");
            }
        }catch(Exception e){
            System.out.println("Error al eliminar el duenio: "+e.getLocalizedMessage());
            e.printStackTrace();
            e.getCause();
           
        }     
    }
    
    
    

    private void agregarOpciones() {
        Callback<TableColumn<DuenioMascota, Void>, TableCell<DuenioMascota, Void>> cellFactory = (final TableColumn<DuenioMascota, Void> param) -> {
            TableCell<DuenioMascota, Void> cell = new TableCell<DuenioMascota, Void>() {
                
                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        
                        HBox hbOpciones = new HBox(5);
                        
                        DuenioMascota duenioMascota = getTableView().getItems().get(getIndex());
                        
                        Button btnEd = new Button("Editar");
                        btnEd.setOnAction(e ->editarDuenio(duenioMascota));
                        
                        Button btnEl = new Button("Eliminar");
                        btnEl.setOnAction(e -> eliminarDuenio(duenioMascota));
                        hbOpciones.getChildren().addAll(btnEd,btnEl);
                        setGraphic(hbOpciones);
                    }
                }
            };
            return cell;
        };

        colOpciones.setCellFactory(cellFactory);

    }

}
