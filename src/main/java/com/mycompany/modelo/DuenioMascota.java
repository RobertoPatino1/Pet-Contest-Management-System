package com.mycompany.modelo;

import com.mycompany.proyectopoog7parte2.App;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class DuenioMascota extends Persona implements Serializable{
    private int id;
    private static final long serialVersionUID = -3459389148250017616L;
    private String apellidos;

    
    public DuenioMascota(int id,String apellidos, String nombre, String direccion, String telefono, Ciudad ciudad, String email){
        super(nombre, direccion, telefono, ciudad, email); //Llamando las variables de la clase padre Persona
        this.id = id;
  
        this.apellidos = apellidos;
    }
    
 
    
    public String getNombre(){
        return nombre;
    }
    public String getDireccion(){
        return direccion;
    }
    public String getTelefono(){
        return telefono;
    }
    public Ciudad getCiudad(){
        return ciudad;
    }
    public String getEmail(){
        return email;
    }
    public int getId(){
        return id;
    }
    public String getApellidos(){
        return apellidos;
    }
    
    
    
    public void setApellidos(String apellidos){
        this.apellidos=apellidos;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
   
    public static ArrayList<DuenioMascota> cargarDuenios(){
    ArrayList<DuenioMascota> duenios = new ArrayList<>();

    try(BufferedReader bf = new BufferedReader(new FileReader(App.pathDuenios))){
        String linea;
        while((linea=bf.readLine())!=null){

            String[] datos = linea.strip().split(",");

            DuenioMascota d = new DuenioMascota(Integer.parseInt(datos[0]),datos[1],datos[2],datos[3],datos[4],new Ciudad(datos[5]),datos[6]);
            duenios.add(d);


        }
        return duenios;                    
    }catch(IOException e){
        System.out.println("Error al ingresar la ruta del archivo");
        return duenios;
    }
}

    
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o!=null&&o instanceof DuenioMascota){
            //Downcasting
            DuenioMascota duenio = (DuenioMascota) o;
            return this.id == duenio.id;
        }
        return false;
    }
    
        
    public static void escribirDuenio(DuenioMascota d){
        try(BufferedWriter fw = new BufferedWriter(new FileWriter(App.pathDuenios,true))){
            int id = DuenioMascota.cargarDuenios().size()+1;

            fw.write(id+","+d.apellidos+","+d.nombre+","+d.direccion+","+d.telefono+","+d.ciudad.getNombre()+","+d.email+"\n");
            
            
        }catch(IOException e){
            System.out.println("error: "+e.getStackTrace());
        }
    }
    
    
    public static void actualizarListaDuenios(ArrayList<DuenioMascota> lista){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(App.pathDuenios))){
            //Se reescribe todo en el archivo
            int  i = 0;
            for(DuenioMascota d: lista){
                i++;
                d.setId(i);
                
                bw.write(d.getId()+","+d.getApellidos()+","+d.getNombre()+","+d.getDireccion()+","+d.getTelefono()+","+d.getCiudad().getNombre()+","+d.getEmail()+"\n");
            }
        }catch(IOException e){
            
            System.out.println("Error al actualizar el archivo de duenios: "+e);
        }
        
    }
    

    public String toString(){
        return super.toString()+" "+apellidos; 
    }
    
}
