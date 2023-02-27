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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class CrearAuspiciantesController {

    @FXML
    private ComboBox cbCiudades;
    @FXML
    private Button buttonGuardar;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtWebpage;
    @FXML
    private Label lblCrearAuspiciantes;
    @FXML
    private Button buttonActualizarAuspiciante;
    
    private Auspiciante auspicianteOG;

    /**
     * Initializes the controller class.
     */

    
    public void initialize() {
        //Ocultamos el boton de actualizar
        buttonActualizarAuspiciante.setVisible(false);
       // TODO
       //Rellenamos el combo box
       //cbCiudades.getItems().addAll(Ciudad.cargarCiudades("archivos/ciudades.csv"));
        llenarComboCiudad(Ciudad.cargarCiudades(App.pathCiudades));
    }    

    @FXML
    private void guardarAuspiciante(ActionEvent event){
        try{
            //Ahora debemos recuperar los datos que el usuario ingreso en los textBox para luego crear el objeto
            //Vamos a obtener los datos
            String nombre = txtNombre.getText();
            String direccion = txtDireccion.getText();
            String telefono = txtTelefono.getText();
            

            String email = txtEmail.getText();
            
            
            //Ahora debemos obtener el valor de la ciudad
            Ciudad c = (Ciudad) cbCiudades.getValue();
            
            //Se recupera el valor de la webPage
            String webPage = txtWebpage.getText();
            //Debemos recuperar el ultimo id
            int id = Auspiciante.cargarListaAuspiciantes(App.pathAuspiciantes).size()+1;
            System.out.println(id);
            //Creamos el objeto

            Auspiciante au = new Auspiciante(id, nombre.toUpperCase(), direccion, telefono, c, email, webPage);
            if(au.getNombre().equals("") || au.getDireccion().equals("")||au.getTelefono().equals("")
                ||au.getEmail().equals("")||au.getWebPage().equals("")||au.getCiudad().equals("")){
                throw  new NullPointerException();
            }
            if(!au.getTelefono().equals("")){
                //Validando que solo se ingresen numeros, una vez que se ha validado que este campo no es vacio
                int num = Integer.valueOf(au.getTelefono()); //Validamos que solo se ingresen numeros
            }
            //Lo escribimos en la lista
            Auspiciante.actualizarArchivoAuspiciantes(au, App.pathAuspiciantes);
            System.out.println("Se ha escrito el auspiciante exitosamente"); //FIXME: DEBEMOS MANDAR UN AVISO DE QUE SE ESCRIBIO
            Alert exito = new Alert(Alert.AlertType.INFORMATION,"El auspiciante ha sido creado de manera exitosa");
            exito.setHeaderText("Creacion de auspiciante");
            exito.show();
            App.setRoot("administrarAuspiciantes"); //FIXME: Esta linea solo nos manda al menu principal, ya que no guarda nada
        }catch(IOException e){
            System.out.println("Error al escribir: "+e);
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
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("administrarAuspiciantes");
    }

    @FXML
    private void actualizarAuspiciante(ActionEvent event) {
        try{
            //Se recupera el indice del duenio recien creado
            int indice = auspicianteOG.getCodigo()-1;
            System.out.println(auspicianteOG);
            
            //Entramos a la lista de auspiciantes y se accede al objeto con ese indice
            ArrayList<Auspiciante> listaAuspiciantes = Auspiciante.cargarListaAuspiciantes(App.pathAuspiciantes);
            
            //Se obtiene el auspiciante que sera actualizado
            Auspiciante auspicianteActualizar = listaAuspiciantes.get(indice);
            
            //Se rellenan los campos
            auspicianteActualizar.setNombre(txtNombre.getText());
            
            auspicianteActualizar.setDireccion(txtDireccion.getText());
            auspicianteActualizar.setTelefono(txtTelefono.getText());
            auspicianteActualizar.setEmail(txtEmail.getText());
            auspicianteActualizar.setWebPage(txtWebpage.getText());
            
            
            //Se valida que los campos no esten vacios
            if(auspicianteActualizar.getNombre().equals("") || auspicianteActualizar.getDireccion().equals("")||auspicianteActualizar.getTelefono().equals("")
                ||auspicianteActualizar.getEmail().equals("")||auspicianteActualizar.getWebPage().equals("")){
                throw  new NullPointerException();
            }
            if(!auspicianteActualizar.getTelefono().equals("")){
                //Validando que solo se ingresen numeros, una vez que se ha validado que este campo no es vacio
                int num = Integer.valueOf(auspicianteActualizar.getTelefono()); //Validamos que solo se ingresen numeros
            }
            
            System.out.println(auspicianteActualizar);
            //Se asigna el duenio a la lista
            listaAuspiciantes.set(indice, auspicianteActualizar);
            
            //Se escribe el auspiciante actualizado en la lista
            //**************************************************************
            //Se actualiza la lista de auspiciantes
            Auspiciante.actualizarListaAuspiciantes(listaAuspiciantes, App.pathAuspiciantes);   
            
            //Le mostramos un aviso al usuario
            Alert confirmacion = new Alert(Alert.AlertType.INFORMATION,"Recuerde que este cambio no será aplicado a los elementos" +"\n"+"ya registrados en el sistema");
            confirmacion.setHeaderText("Se ha actualizado el auspiciante de manera exitosa");
            confirmacion.show();
            App.setRoot("administrarAuspiciantes");
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
    
    
    
    //Metodos de rellenado
    public void llenarComboCiudad(ArrayList<Ciudad> ciudades){
        cbCiudades.getItems().clear();
        ArrayList<Ciudad> listaCiudades = new ArrayList<>();
        for(Ciudad c:ciudades){
            if(!listaCiudades.contains(c)){
                listaCiudades.add(c);
            }
        }
        cbCiudades.getItems().setAll(listaCiudades); 
    }
    
    
    public void llenarCamposAuspiciante(Auspiciante au){
        auspicianteOG = au;
        
        //Se oculta la opcion de guardar
        buttonGuardar.setVisible(false);
        
        //Se vuelve visible la opcion de actualizar
        buttonActualizarAuspiciante.setVisible(true);
        
        //Se cambia el titulo de la ventana
        lblCrearAuspiciantes.setText("Actualizar auspiciante");
        //Se rellenan los campos
        
        txtNombre.setText(au.getNombre());
        txtDireccion.setText(au.getDireccion());
        txtTelefono.setText(au.getTelefono());
        cbCiudades.setValue(au.getCiudad());
        txtEmail.setText(au.getEmail());
        txtWebpage.setText(au.getWebPage());
        
        cbCiudades.setDisable(true);
        
    }
     
    

}
