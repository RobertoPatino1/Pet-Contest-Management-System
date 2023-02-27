/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectopoog7parte2;
import com.mycompany.modelo.Mascota;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DetalleMascotaController implements Initializable {


    @FXML
    private Label lblDetalleMascota;
    @FXML
    private Button opRegresarDetalleMascota;
    @FXML
    private Label NombreMascota;
    @FXML
    private Label FechaDeNacimiento;
    @FXML
    private Label RazaMascota;
    @FXML
    private Label DuenioMascota;
    @FXML
    private ImageView ImagenMascota;
    
    private Mascota mascotaOG;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void switchToMenuPrincipalAdministrarMascota() throws IOException {
        App.setRoot("administrarMascotas");
    }
    
    public void llenarCamposMascota(Mascota m){
        
        mascotaOG = m;
        System.out.println(m);
        System.out.println(mascotaOG.getId());
        
        //Se rellenan los campos
        NombreMascota.setText(m.getNombre());
        
        LocalDate fechaNacimientoRelleno = m.getFechaDeNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        FechaDeNacimiento.setText(fechaNacimientoRelleno.toString());
        
        RazaMascota.setText(m.getRaza());
        
        //Se rellena el cb de duenio
        DuenioMascota.setText(m.getDuenio().toString());
        
        
        try {
            String fileName = "files/MascotsImages/"+m.getFoto();//armar la ruta de la foto
            //abrir el stream de la imagen de la persona
            //input = fileName. ;
            System.out.println(fileName);           
            Image image = new Image(new FileInputStream(fileName));
            ImagenMascota.setImage(image);

        } catch (FileNotFoundException ex) {
            try{
                System.out.println("no se encuentra archivo de imagen: ");
                //Hacemos un split por el punto
                String[] datos = m.getFoto().split(".");
                System.out.println(m.getFoto());
                if(datos[1].equals("jpg")){
                    System.out.println("SE CAMBIA A .JPG");
                    String fileName = "archivos/ImagenesMascotas"+datos[0]+".jpg";
                    System.out.println(fileName);
                    Image image = new Image(new FileInputStream(fileName));
                    ImagenMascota.setImage(image);
                }else if(datos[1].equals("png")){
                    System.out.println("SE CAMBIA A .PNG");
                    String fileName = "archivos/ImagenesMascotas"+datos[0]+".png";
                    System.out.println(fileName);
                    Image image = new Image(new FileInputStream(fileName));
                    ImagenMascota.setImage(image);
                }
            }catch(IOException e){
                System.out.println("ERROR: ");
            }
            
            
            
            ex.printStackTrace();
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }

    }

}
