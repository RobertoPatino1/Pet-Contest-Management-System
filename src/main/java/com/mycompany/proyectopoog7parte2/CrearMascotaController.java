
package com.mycompany.proyectopoog7parte2;

import com.mycompany.modelo.DuenioMascota;
import com.mycompany.modelo.Mascota;
import com.mycompany.enums.TipoAnimal;
import utils.UtilDate;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class CrearMascotaController{
    String direccionImagen;
    
    @FXML
    private Button opGuardarCrearMascota;
    @FXML
    private Button opCancelarCrearMascota;
    @FXML
    private RadioButton rbPerro;
    @FXML
    private ToggleGroup tipoDeMascota;
    @FXML
    private RadioButton rbGato;
    @FXML
    private TextField txtNombreMascota;
    @FXML
    private DatePicker datePickerFechaDeNacimiento;
    @FXML
    private TextField txtRaza;
    @FXML
    private ComboBox<DuenioMascota> cbDuenios;
    @FXML
    private ImageView imgvFotoMascota;
    @FXML
    private Button btnBuscarFoto;
    @FXML
    private Label lblCrearMascota;
    @FXML
    private Button buttonActualizar;
    
    private Mascota mascotaOG;
    
    /**
     * Initializes the controller class.
     */
   
    public void initialize() {
        buttonActualizar.setVisible(false);

        llenarComboDuenioMascota(DuenioMascota.cargarDuenios());
    }    
    

    @FXML
    private void switchToGuardarMascota() throws IOException{
        //Se debe recuperar la informacion ingresada por el usuario
        try{
            //Se asigna el id
            int id = Mascota.cargarMascotas().size()+1;
            //System.out.println(id);
            //Se recupera el nombre de la mascota
            String nombreMascota = txtNombreMascota.getText();

            //Se recupera el tipo de mascota seleccionado 
            RadioButton selectedRadioButton  = (RadioButton) tipoDeMascota.getSelectedToggle();
            String tipoMascota = selectedRadioButton.getText().toUpperCase();

            
            //Se recupera la fecha de nacimiento y se la convierte de LocalDate a Date
            LocalDate fechaNacimiento = datePickerFechaDeNacimiento.getValue();
            Date fecha = UtilDate.getDateFromString(fechaNacimiento.toString());
            
            //Se recupera la raza
            String raza = txtRaza.getText();

            //Se recupera el duenio
            DuenioMascota duenio = (DuenioMascota)cbDuenios.getValue();
            if(duenio.equals(null)){
                System.out.println("No se ha seleccionado el duenio de la mascota");
                throw new NullPointerException(); 
            }
            
            
            //Se crea el objeto
            /*FIXMEEEEEEEEEEEEE: AQUI SE DEBE SELECCIONAR CORRECTAMENTE LA FOTO DE LA MASCOTA.
              Se debe implementar esta funcionalidad correctamente
            */
            if(direccionImagen!=null){
                System.out.println(direccionImagen);
                Mascota mascota = new Mascota(id, nombreMascota, TipoAnimal.valueOf(tipoMascota), raza, fecha, direccionImagen, duenio);
                Mascota.escribirMascota(mascota);
                Alert exito = new Alert(Alert.AlertType.INFORMATION, "La mascota ha sido creada de manera exitosa");
                exito.setTitle("Exito");
                exito.setHeaderText("Operacion exitosa");
                exito.show();
                App.setRoot("administrarMascotas");
                
            }else{
                System.out.println("Se pide confirmacion indicando que se colocara la foto por defecto");
                Alert confirmacionFotoDefault = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacionFotoDefault.setTitle("Confirmación");
                confirmacionFotoDefault.setHeaderText("Se procederá a agregar una foto por defecto para su mascota");
                confirmacionFotoDefault.setContentText("Está usted de acuerdo?");
                
                Optional<ButtonType> result = confirmacionFotoDefault.showAndWait();
                if(result.get()==ButtonType.OK){
            
                    
                    //Se crea el objeto con la imagen default
                    Mascota mascota = new Mascota(id, nombreMascota, TipoAnimal.valueOf(tipoMascota), raza, fecha, "incognito.jpg", duenio);
                    Mascota.escribirMascota(mascota);
                    App.setRoot("administrarMascotas");
                    
                }else{
                    //Selecciona la opcion cancelar.
                    //No se hace nada
                }
                
                
            }               
        }catch(NullPointerException e){
            System.out.println(e.getStackTrace());
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR,"Asegúrese de llenar todos los campos"); //FIXME
            alerta.setTitle("Error");
            alerta.setHeaderText("Ha ocurrido un error:");
            System.out.println(e.getClass());
            alerta.show();  
            
        }catch(IndexOutOfBoundsException e){
            System.out.println(e);
            System.out.println(e.getCause());
            System.out.println(e.getStackTrace());
            e.printStackTrace();
        }
        
        
        catch(RuntimeException e){
            System.out.println("El archivo ya existe en la carpetaaaaaaaaaa");
            System.out.println(e.getStackTrace());
            Alert alerta = new Alert(Alert.AlertType.ERROR,"La imagen que se ha seleccionado ya se encuentra en uso.\nPor favor seleccione otra"); //FIXME
            alerta.setTitle("Error");
            alerta.setHeaderText("Ha ocurrido un error:");
            System.out.println(e.getClass());
            alerta.show();  
        }catch(Exception e){
            System.out.println("ERROR: "+e.getStackTrace());
        }
        
    }

    @FXML
    private void switchToMenuPrincipalAdministrarMascota() throws IOException{
        App.setRoot("administrarMascotas");
    }

    @FXML
    private void buscarFoto() throws IOException,RuntimeException {
        //Se crea el objeto de tipo FileChooser
        try{
        FileChooser fileChooser = new FileChooser(); //Este nos permite abrir el explorador de archivo
        fileChooser.setTitle("Buscar foto");
        
        //Se filtran los archivos que se van a mostrar
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")       
        );
        
        //Se obtiene la imagen seleccionada
        File imgFile = fileChooser.showOpenDialog(null);
        
        //Se muestra la imagen
        if(imgFile!=null){
            Image image = new Image("file:"+imgFile.getAbsolutePath());
            System.out.println(imgFile.getAbsolutePath());
            imgvFotoMascota.setImage(image);
            
            
            //Se copia la imagen
            Path from = Paths.get(imgFile.toURI());
            Path to = Paths.get("archivos/imagenesMascotas/"+imgFile.getName());
            System.out.println(imgFile.getName());
            Files.copy(from, to);
            
        }
        
        direccionImagen = imgFile.getName();
    
       }catch(FileAlreadyExistsException e){
            System.out.println("El archivo ya existe en la carpetaaaaaaaaaa");
            System.out.println(e.getStackTrace());
            Alert alerta = new Alert(Alert.AlertType.ERROR,"La imagen que se ha seleccionado ya se encuentra en uso, por favor seleccione otra"); //FIXME
            alerta.setTitle("Error");
            alerta.setHeaderText("Ha ocurrido un error:");
            System.out.println(e.getClass());
            alerta.show();  
       }
    }
    
    //Metodo para cargar la imagen por defecto 
    private void cargarFotoPorDefecto(){
        InputStream  input = null;
        try {           
            input =  App.class.getResource("archivos/imagenesMascotas/incognito.jpg").openStream();
            Image image = new Image(input, 100, 100, false, false);
            imgvFotoMascota.setImage(image);
        } catch (IOException ex) {
            System.out.println("No se pudo cargar foto por defecto");
        } finally {
            if (input!=null){
            try {
                input.close();
            } catch (IOException ex) {
                System.out.println("Error al cerrar el recurso");
            }
            }
        }
    }

    @FXML
    private void actualizarMascota() {
        //Se recupera el indice de la mascota recien creada
        try{
            int indice = mascotaOG.getId()-1;
            System.out.println(mascotaOG);
            
            //Entramos a la lista de mascotas y se accede al objeto con ese indice
            ArrayList<Mascota> listaMascotas = Mascota.cargarMascotas();
            
            //Se obtiene la mascota que sera actualizada
            Mascota mascotaActualizar = listaMascotas.get(indice);
            
            //Se rellenan los campos
            mascotaActualizar.setNombre(txtNombreMascota.getText());
            mascotaActualizar.setRaza(txtRaza.getText());
            
            //Se valida que los campos no sean vacios
            if(mascotaActualizar.getNombre().equals("")||mascotaActualizar.getRaza().equals("")){
                throw new NullPointerException();
            }
            
            //Se asigna la mascota a la lista
            listaMascotas.set(indice, mascotaActualizar);
            
            //Se actualiza la lista de mascotas
            Mascota.actualizarListaMascotas(listaMascotas);
            
            //Se le muestra un aviso al usuario
            Alert confirmacion = new Alert(Alert.AlertType.INFORMATION,"Recuerde que este cambio no será aplicado a los elementos" +"\n"+"ya registrados en el sistema");
            confirmacion.setHeaderText("Se ha actualizado la mascota de manera exitosa");
            confirmacion.show();
            App.setRoot("administrarMascotas");
            
        }catch(IOException e){
            System.out.println("Error al escribir: "+e);
        }catch(NullPointerException e){
            System.out.println("No se llenaron todos los campos");
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Ha ocurrido un error:");
            error.setContentText("Asegurese de llenar todos los campos");
            error.show();
        }catch(IndexOutOfBoundsException e){
            System.out.println("ERROR CON LOS INDICES DE LA LISTA: "+e);
        }
        
    }
    
    
    
    //Metodos para llenar los campos
    public void llenarComboDuenioMascota(ArrayList<DuenioMascota> duenios){
        cbDuenios.getItems().clear();
        ArrayList<DuenioMascota> listaDueniosMascotas = new ArrayList<>();
        for(DuenioMascota d: duenios){
            if(!listaDueniosMascotas.contains(d)){
                listaDueniosMascotas.add(d);
            }
            
        }
        cbDuenios.getItems().addAll(listaDueniosMascotas);
    }
    
    public void llenarCamposMascota(Mascota m){
        mascotaOG = m;
        System.out.println(m);
        System.out.println(mascotaOG.getId());
        
        //Se vuelve invisible el boton de guardado
        opGuardarCrearMascota.setVisible(false);
        
        //Se vuelve visible la opcion de actualizar
        buttonActualizar.setVisible(true);
        
        //Se cambia el titulo de la ventana
        lblCrearMascota.setText("Editar mascota");
        
        //Se rellenan los campos
        txtNombreMascota.setText(m.getNombre());
        
        /*
        ###############################
        Se rellena el campo de la fecha
        Esta recibe un objeto de tipo LocalDate
        
        REVISAR ESTAS LINEAS EN CASO DE ERROR
        ###############################
        */
        LocalDate fechaNacimientoRelleno = m.getFechaDeNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        datePickerFechaDeNacimiento.setValue(fechaNacimientoRelleno);
        
        //#####################################################
        
        //Se rellena el campo de raza
        txtRaza.setText(m.getRaza());
        
        //Se rellena el cb de duenio
        cbDuenios.setValue(m.getDuenio());
        cbDuenios.setDisable(true);
        
        
        /*
        ######################
        SE DESACTIVA EL BOTON BUSCAR FOTO
        ######################
        */
        btnBuscarFoto.setDisable(true);
        
        
        
        //Se rellena el radioButton y se lo vuelve no editable
        rbPerro.setDisable(true);
        rbGato.setDisable(true);
        
        if(m.getTipoMascota().equals(TipoAnimal.PERRO)){
            rbPerro.setSelected(true);
            
        }else{
            rbGato.setSelected(true);
            
        }
        
        

    }
    
    
    



}
