
package com.mycompany.proyectopoog7parte2;

import com.mycompany.modelo.Auspiciante;
import com.mycompany.modelo.Ciudad;
import com.mycompany.modelo.Concurso;
import com.mycompany.modelo.Premio;
import com.mycompany.enums.TipoAnimalesConcurso;
import utils.UtilDate;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CrearConcursoController{

    @FXML
    private Button opCrearConcurso;
    @FXML
    private Button opCancelarCrearConcurso;
    @FXML
    public TableView tvPremios;
    @FXML
    private TableColumn<Premio, Integer> colLugar;
    @FXML
    private TableColumn<Premio, String> colDescripcion;
    @FXML
    private TableColumn<Premio, Auspiciante> colAuspiciante;
    @FXML
    private Label lblTituloCrearConcurso;
    @FXML
    private DatePicker dateFechaConcurso;
    @FXML
    private TextField txtNombreConcurso;
    @FXML
    private ComboBox<TipoAnimalesConcurso> cbTipoMascotasConcurso;
    @FXML
    private TextField txtHoraConcurso;
    @FXML
    private DatePicker dateFechaInicioInscripcion;
    @FXML
    private DatePicker dateFechaCierreInscripcion;
    @FXML
    private TextField txtLugarConcurso;
    @FXML
    private ComboBox<Auspiciante> cbAuspiciantesConcurso;
    @FXML
    private ComboBox<Ciudad> cbCiudadConcurso;
    @FXML
    private Button buttonAniadirPremio;
    
    private Concurso concursoOG;
    
    public static Concurso concursoCrear = new Concurso("Null");
    public Concurso c = concursoCrear;
    @FXML
    private Button buttonActualizarConcurso;
    @FXML
    private Label lblPremios;
    @FXML
    private VBox vboxPremios;
    /**
     * Initializes the controller class.
     */

    public void initialize() {
        //Se oculta la opcion de actualizar
        buttonActualizarConcurso.setVisible(false);
        
        
        
       ArrayList<Premio> l = new ArrayList<>();
        concursoCrear.setPremio(l);
        // TODO
        //Se llenan los combos con los elementos de los enums
        cbTipoMascotasConcurso.getItems().setAll(TipoAnimalesConcurso.PERROS,TipoAnimalesConcurso.GATOS,TipoAnimalesConcurso.TODOS);
        cbCiudadConcurso.getItems().setAll(Ciudad.cargarCiudades());
        cbAuspiciantesConcurso.getItems().setAll(Auspiciante.cargarListaAuspiciantes());
        
        colLugar.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colAuspiciante.setCellValueFactory(new PropertyValueFactory<>("auspiciante"));
        
        //llamar a llenar panel en hilo
        
        Thread t = new Thread( () ->
        {
            while (true){
                Platform.runLater( () -> llenarTableView());
                //se utiliza runlater para hacer los cambios en la interfaz grafica
                //algunos controles como el label a veces no permiten que en el hilo se hagan los cambios
                
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        );
        
        t.setDaemon(true);
        t.start();
    }    


    @FXML
    private void switchToMenuPrincipalCrearConcurso() throws IOException{
        App.setRoot("administrarConcurso");
        
    }

    @FXML
    private void switchToGuardarConcurso() throws IOException{
        try {
            //Se le asigna un id al concurso

            int id = Concurso.cargarListaConcursos().size()+1;

            //Se racuperan los datos del concurso ingresados
            TipoAnimalesConcurso tipoAnimalConcurso = cbTipoMascotasConcurso.getValue();
            String nombreConcurso = txtNombreConcurso.getText();
            LocalDate fechaConc = dateFechaConcurso.getValue();
            Date fechaConcurso = UtilDate.getDateFromString(fechaConc.toString());
            String horaString = txtHoraConcurso.getText();
            if(!horaString.equals("")){
                LocalTime lt = LocalTime.parse(horaString);

                //Debemos hacer catch de un dateTime parse exception
                //Aqui se valida que se entre en el formato adecuado
            }
            LocalDate fechaInicioInscripcion = dateFechaInicioInscripcion.getValue();
            Date fechaInicioInscripcionFinal = UtilDate.getDateFromString(fechaInicioInscripcion.toString());
            LocalDate fechaCierreInscripcion = dateFechaCierreInscripcion.getValue();
            Date fechaCierreInscripcionFinal = UtilDate.getDateFromString(fechaCierreInscripcion.toString());
            Ciudad ciudadConcurso = cbCiudadConcurso.getValue();
            String lugar = txtLugarConcurso.getText();
            Auspiciante auspicianteConcurso = cbAuspiciantesConcurso.getValue();
            
            //Y el premio???
            ArrayList<Premio> listaPremiosGuardar = c.getListaPremio();
            System.out.println(listaPremiosGuardar);
            
            if(listaPremiosGuardar.size()==0){
                System.out.println("No se han llenado los premios");
                throw new NullPointerException();
            }
            
            //Se procede a crear el objeto
            Concurso concursoGuardar = new Concurso(id, nombreConcurso, fechaConcurso, horaString, fechaInicioInscripcionFinal, fechaCierreInscripcionFinal, ciudadConcurso, lugar, listaPremiosGuardar, auspicianteConcurso, tipoAnimalConcurso);
            
            //Se muestra el mensaje de exito
            Alert exito = new Alert(Alert.AlertType.INFORMATION);
            exito.setTitle("Concurso");
            exito.setHeaderText("Creacion de concurso");
            exito.setContentText("El concurso ha sido creado de manera exitosa");
           
            
            //Se escribe el concurso en el archivo de concursos y se lo actualiza.
            Concurso.actualizarArchivoConcursos(concursoGuardar);
            exito.show();
            //Se cambia de escena
            App.setRoot("administrarConcurso");
        }catch (DateTimeParseException e) {
            System.out.println("Se ingreso la hora del evento en un formato inadecuado");
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error al ingresar la hora");
            error.setContentText("Asegurese de ingresar la hora en el formato:\nhh:mm ");
            error.show();
            
        }catch(NullPointerException e){
            System.out.println("Los campos no fueron llenados correctamente");
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error al llenar los campos");
            error.setContentText("Asegurese de llenar todos los campos correctamente");
            error.show();
        }

        
        
        
        
    }

    @FXML
    private void switchToAniadirPremio() throws IOException{
        System.out.println(c.getListaPremio());
        try {
            //muestra una nueva ventana para poder actualizar el stock de cualquier producto
            Stage st = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("aniadirPremio.fxml"));
            
            
            Scene scene = new Scene( fxmlLoader.load());
            st.setScene(scene);
            
            st.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        //App.setRoot("aniadirPremio");
    }
    
    
   private void llenarTableView(){
       tvPremios.getItems().clear();
       tvPremios.getItems().setAll(concursoCrear.getListaPremio());
   }
   
   
   
   //Metodos de llenado
   
   //Llenado del combo
   public void llenarComboDirigidoA(){
       cbTipoMascotasConcurso.getItems().setAll(TipoAnimalesConcurso.PERROS,TipoAnimalesConcurso.GATOS,TipoAnimalesConcurso.TODOS);

   }
   
   public void llenarComboCiudad(ArrayList<Ciudad> ciudades){
        cbCiudadConcurso.getItems().clear();
        ArrayList<Ciudad> listaCiudades = new ArrayList<>();
        for(Ciudad c:ciudades){
            if(!listaCiudades.contains(c)){
                listaCiudades.add(c);
            }
        }
        cbCiudadConcurso.getItems().setAll(listaCiudades); 
   }
   
   public void llenarComboAuspiciantes(ArrayList<Auspiciante> auspiciantes){
       cbAuspiciantesConcurso.getItems().clear();
       ArrayList<Auspiciante> listaAuspiciantes = new ArrayList<>();
       for(Auspiciante au: auspiciantes){
           if(!listaAuspiciantes.contains(au)){
               listaAuspiciantes.add(au);
           }
       }
       cbAuspiciantesConcurso.getItems().setAll(listaAuspiciantes);
   }
   
   
   public void llenarTVPremios(Concurso c){
       tvPremios.getItems().clear();
       tvPremios.getItems().setAll(c.getListaPremio());
   }
   
   //Llenar los campos
   
   public void llenarCamposConcurso(Concurso c){
       concursoOG = c;
        //Se cambia el label
        System.out.println(c.getListaPremio());
        lblTituloCrearConcurso.setText("Editar concurso");
        opCrearConcurso.setVisible(false);
        buttonActualizarConcurso.setVisible(true);
        
        //Se rellenan los campos
        cbTipoMascotasConcurso.setValue(c.getDirigidoA());
        
        txtNombreConcurso.setText(c.getNombre());
        
        LocalDate fechaInicioConc = c.getFechaEvento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dateFechaConcurso.setValue(fechaInicioConc);        
        
        txtHoraConcurso.setText(c.getHoraEvento());
        
        LocalDate fechaInicioInscripcion = c.getFechaInicioInscripcion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dateFechaInicioInscripcion.setValue(fechaInicioInscripcion);
        
        LocalDate fechaCierreInscripcion = c.getFechaCierreInscripcion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dateFechaCierreInscripcion.setValue(fechaCierreInscripcion);
       
        cbCiudadConcurso.setValue(c.getCiudad());
        
        txtLugarConcurso.setText(c.getLugar());
        
        cbAuspiciantesConcurso.setValue(c.getAuspiciante());
        
        
        
        
        
            
        //Se desactiva los campos
        dateFechaInicioInscripcion.setDisable(true);
        dateFechaCierreInscripcion.setDisable(true);
        dateFechaConcurso.setDisable(true);
        cbAuspiciantesConcurso.setDisable(true);
        cbCiudadConcurso.setDisable(true);
        tvPremios.setVisible(false);
        
        //Se muestra un nuevo lisview con la informacion de todos los premios
        
        
        ListView<Premio> lvPremios = new ListView<>();
        lvPremios.getItems().setAll(c.getListaPremio());
        vboxPremios.getChildren().clear();
        vboxPremios.getChildren().add(lvPremios);
        
        
        
   }
   
   
   
   
   

    @FXML
    private void actualizarConcurso(ActionEvent event) {
        try{
            int indice = concursoOG.getId()-1;
            System.out.println(concursoOG);
            
            //Entramos a la lista de concurso y accedemos al objeto con ese indice
            ArrayList<Concurso> listaConcursos = Concurso.cargarListaConcursos();
            
            //Se obtiene el concurso que sera actualizado
            Concurso concursoActualizar = listaConcursos.get(indice);
            
            //Se rellenan los campos
            concursoActualizar.setNombre(txtNombreConcurso.getText());
            concursoActualizar.setHoraEvento(txtHoraConcurso.getText());
            concursoActualizar.setLugar(txtLugarConcurso.getText());
            
            if(concursoActualizar.getNombre().equals("")||concursoActualizar.getHoraEvento().equals("")||concursoActualizar.getLugar().equals("")){
                throw new NullPointerException();
            }
            
            
            if(!txtHoraConcurso.getText().equals("")){
                LocalTime lt = LocalTime.parse(txtHoraConcurso.getText());

                //Debemos hacer catch de un dateTime parse exception
                //Aqui se valida que se entre en el formato adecuado
            }
            
            System.out.println(concursoActualizar);
            
            //Se asigna el concurso a la lista
            listaConcursos.set(indice, concursoActualizar);
            
            //Se escribe el concurso actualizado en la lista
            //**************************************************************
            //Se actualiza la lista de concursos
            Concurso.actualizarListaConcursos(listaConcursos);
            
            //Le mostramos un aviso al usuario
            Alert confirmacion = new Alert(Alert.AlertType.INFORMATION,"Recuerde que este cambio no será aplicado a los elementos" +"\n"+"ya registrados en el sistema");
            confirmacion.setHeaderText("Se ha actualizado el concurso de manera exitosa");
            confirmacion.show();
            App.setRoot("administrarConcurso");
        
        
        }catch(IOException e){
            System.out.println("No se pudo escribir: "+e);
        }
        catch (DateTimeParseException e) {
            System.out.println("Se ingreso la hora del evento en un formato inadecuado");
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error al ingresar la hora");
            error.setContentText("Asegurese de ingresar la hora en el formato:\nhh:mm ");
            error.show();
            
        }catch(NullPointerException e){
            System.out.println("Los campos no fueron llenados correctamente");
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error al llenar los campos");
            error.setContentText("Asegurese de llenar todos los campos correctamente");
            error.show();
    
        }
    }
   
    
    
    
}
