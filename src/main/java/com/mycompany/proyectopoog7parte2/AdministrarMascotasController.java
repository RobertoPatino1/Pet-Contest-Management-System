
package com.mycompany.proyectopoog7parte2;

import com.mycompany.modelo.DuenioMascota;
import com.mycompany.modelo.Mascota;
import com.mycompany.enums.TipoAnimal;
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
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcTipoMascota.setCellValueFactory(new PropertyValueFactory<>("tipoMascota"));
        tcDuenio.setCellValueFactory(new PropertyValueFactory<>("duenio"));
        
        agregarOpciones();
        
        tvMascotas.getItems().addAll(Mascota.cargarMascotas());
        
        
        
    }    
    
    @FXML
    private void switchToCrearMascota() throws IOException {
        App.setRoot("crearMascota");
    }

    @FXML
    private void switchToMenuPrincipal() throws IOException {
        App.setRoot("menuPrincipal");
    }
    
    
    
    
    
    private void editarMascota(Mascota m){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("crearMascota.fxml"));//no tiene el controlador especificado
            Parent root = (Parent) fxmlLoader.load();
            CrearMascotaController ct = fxmlLoader.getController();
            fxmlLoader.setController(ct);//se asigna el controlador
            
            ct.llenarComboDuenioMascota(DuenioMascota.cargarDuenios());
            ct.llenarCamposMascota(m);
            
            App.changeRoot(root);
            
        }catch(IOException e){
            System.out.println("Error IO: "+e);
        }catch(Exception e){
            System.out.println("Excepcion general al cambiar de escena: "+e);
        }
    }
    
    
    
        
    private void eliminarMascota(Mascota m){

        try{
            Alert confirmacion = AlertGenerator.generateAlert(Alert.AlertType.CONFIRMATION,null,"Eliminacion","Se procederá a eliminar a la mascota de los registros.\n ¿Continuar?");

            Optional<ButtonType> result = confirmacion.showAndWait();
            if(result.get()==ButtonType.OK){

                ArrayList<Mascota> lista = Mascota.cargarMascotas();
                int indice = lista.indexOf(m);
                lista.remove(indice); 
                Mascota.actualizarListaMascotas(lista);
                App.setRoot("administrarMascotas");
            }

            }catch(IOException e){
                System.out.println("Error al eliminar la mascota: "+e.getLocalizedMessage());
                e.printStackTrace();


            }     
    }
    

   
    private void visualizarDetalleMascota(Mascota m){

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("detalleMascota.fxml"));//no tiene el controlador especificado
            Parent root = (Parent) fxmlLoader.load();
            DetalleMascotaController ct = fxmlLoader.getController();
            fxmlLoader.setController(ct);//se asigna el controlador
            
            ct.llenarCamposMascota(m);
            App.changeRoot(root);
            
        }catch(IOException e){
            System.out.println("Error IO: "+e);
        }catch(Exception e){
            System.out.println("Excepcion general al cambiar de escena: "+e);
        }
        
    }
    
    

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
