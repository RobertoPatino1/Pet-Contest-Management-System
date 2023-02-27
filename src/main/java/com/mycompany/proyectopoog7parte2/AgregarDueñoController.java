
package com.mycompany.proyectopoog7parte2;

import com.mycompany.modelo.Ciudad;
import com.mycompany.modelo.DuenioMascota;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AgregarDueñoController{


    @FXML
    private Button opGuardarDueño;
    @FXML
    private Button opCancelarDueño;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox<Ciudad> cbCiudad;
    @FXML
    private Label lblAgregarDuenio;
    @FXML
    private Button buttonActualizar;
    
    private DuenioMascota duenioOG;
 
    
    public void initialize() {

       buttonActualizar.setVisible(false);

        llenarComboCiudad(Ciudad.cargarCiudades());
    }    
    
    @FXML
    private void switchGuardarDueño() throws IOException,NumberFormatException{
        try{

            String nombre = txtNombre.getText();
            
            String apellido = txtApellido.getText();
            String direccion = txtDireccion.getText();
            String telefono = txtTelefono.getText();
            if(!telefono.equals("")){
                int num = Integer.valueOf(telefono); 
            }
            

            
            String email = txtEmail.getText();
            
            
            Ciudad c = (Ciudad) cbCiudad.getValue();
            int id = DuenioMascota.cargarDuenios().size()+1;
            System.out.println(id);
            DuenioMascota d = new DuenioMascota(id, apellido.toUpperCase(), nombre.toUpperCase(), direccion, telefono, c, email);

            //Lo escribimos en la lista
            DuenioMascota.escribirDuenio(d);
            Alert exito = new Alert(Alert.AlertType.INFORMATION,"Se ha creado el dueño de manera exitosa");
            exito.show();
            App.setRoot("administrarDueños"); //FIXME: Esta linea solo nos manda al menu principal, ya que no guarda nada
        }catch(NullPointerException e){
            Alert alerta = new Alert(Alert.AlertType.ERROR,"Asegúrese de llenar todos los campos"); //FIXME
            alerta.setTitle("Error");
            alerta.setHeaderText("Ha ocurrido un error:");
            System.out.println(e.getClass());
            alerta.show();       
        }catch(NumberFormatException e){
            System.out.println("Error al llevar el String a int");
            Alert alerta = new Alert(Alert.AlertType.ERROR,"Asegúrese de ingresar solo números\n en el numero de teléfono"); //FIXME
            alerta.setTitle("Error");
            alerta.setHeaderText("Ha ocurrido un error:");
            System.out.println(e.getClass());
            alerta.show();  
        }

    }

    @FXML
    private void switchCancelarDueño() throws IOException{
        App.setRoot("administrarDueños");
    }

    @FXML
    private void actualizarDuenio() {
        try{
            int indice = duenioOG.getId()-1;
            System.out.println(duenioOG);
            
            ArrayList<DuenioMascota> listaDuenios = DuenioMascota.cargarDuenios();
            
            DuenioMascota duenioActualizar = listaDuenios.get(indice);
            
            duenioActualizar.setNombre(txtNombre.getText());
            duenioActualizar.setApellidos(txtApellido.getText());
            duenioActualizar.setDireccion(txtDireccion.getText());
            duenioActualizar.setTelefono(txtTelefono.getText());
            duenioActualizar.setEmail(txtEmail.getText());
            
            
            if(duenioActualizar.getNombre().equals("")||duenioActualizar.getApellidos().equals("")
                ||duenioActualizar.getDireccion().equals("")||duenioActualizar.getTelefono().equals("")
                ||duenioActualizar.getEmail().equals("")){
                throw  new NullPointerException();
            }
            if(!duenioActualizar.getTelefono().equals("")){
                int num = Integer.valueOf(duenioActualizar.getTelefono()); //Validamos que solo se ingresen numeros
            }
            listaDuenios.set(indice, duenioActualizar);
            DuenioMascota.actualizarListaDuenios(listaDuenios);
            
            
            Alert confirmacion = new Alert(Alert.AlertType.INFORMATION,"Recuerde que este cambio no será aplicado a los elementos" +"\n"+"ya registrados en el sistema");
            confirmacion.setHeaderText("Se ha actualizado el dueño de manera exitosa");
            confirmacion.show();
            App.setRoot("administrarDueños");
        }catch(IOException e){
            System.out.println("Error al crear el objeto: "+e.getStackTrace());
        }catch(NullPointerException e){
            System.out.println("No se han llenado todos los campos");
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Ha ocurrido un error:");
            error.setContentText("Asegurese de llenar todos los campos");
            error.show();
        }catch(NumberFormatException e){
            System.out.println("Error al llevar el String a int");
            Alert alerta = new Alert(Alert.AlertType.ERROR,"Asegúrese de ingresar solo números\n en el numero de teléfono"); //FIXME
            alerta.setTitle("Error");
            alerta.setHeaderText("Ha ocurrido un error:");
            System.out.println(e.getClass());
            alerta.show();  
        }catch(IndexOutOfBoundsException e){
            System.out.println("Error en los limites de la lista");
        }
        
        
        
        
    }
    
    
    
    public void llenarComboCiudad(ArrayList<Ciudad> ciudades){
        cbCiudad.getItems().clear();
        ArrayList<Ciudad> listaCiudades = new ArrayList<>();
        for(Ciudad c:ciudades){
            if(!listaCiudades.contains(c)){
                listaCiudades.add(c);
            }
        }
        cbCiudad.getItems().setAll(listaCiudades);
    }
    
    public void llenarCamposDuenio(DuenioMascota d){
        duenioOG = d;
        System.out.println(d);
        System.out.println(duenioOG.getId());
        
        opGuardarDueño.setVisible(false);
        
        buttonActualizar.setVisible(true);
        
        lblAgregarDuenio.setText("Editar Dueño");
        
        //Se rellenan los campos
        txtNombre.setText(d.getNombre());
        txtApellido.setText(d.getApellidos());
        txtDireccion.setText(d.getDireccion());
        txtTelefono.setText(d.getTelefono());
        txtEmail.setText(d.getEmail());
        //Se da el valor a los combo
        cbCiudad.setValue(d.getCiudad());
        cbCiudad.setDisable(true);
        
    }

}
