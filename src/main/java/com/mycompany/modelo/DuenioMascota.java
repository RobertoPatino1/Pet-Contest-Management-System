package com.mycompany.modelo;

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

    //Constructor que inicializa todos los atributos
    public DuenioMascota(int id,String apellidos, String nombre, String direccion, String telefono, Ciudad ciudad, String email){
        super(nombre, direccion, telefono, ciudad, email); //Llamando las variables de la clase padre Persona
        this.id = id;
  
        this.apellidos = apellidos;
    }
    
 
    //Getters
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
    
    //Setters
    
    public void setApellidos(String apellidos){
        this.apellidos=apellidos;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
   
    public static ArrayList<DuenioMascota> cargarDuenios(String ruta){
    ArrayList<DuenioMascota> duenios = new ArrayList<>();

    try(BufferedReader bf = new BufferedReader(new FileReader(ruta))){
        String linea;
        while((linea=bf.readLine())!=null){

            String[] datos = linea.strip().split(",");

            DuenioMascota d = new DuenioMascota(Integer.valueOf(datos[0]),datos[1],datos[2],datos[3],datos[4],new Ciudad(datos[5]),datos[6]);
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
    
        
    public static void escribirDuenio(DuenioMascota d, String path){
        try(BufferedWriter fw = new BufferedWriter(new FileWriter(path,true))){
            int id = DuenioMascota.cargarDuenios("archivos/duenosP4.csv").size()+1;
            
            //Se debe escribir el indice en el que se encuentra el 
            fw.write(id+","+d.apellidos+","+d.nombre+","+d.direccion+","+d.telefono+","+d.ciudad.getNombre()+","+d.email+"\n");
            
            
        }catch(IOException e){
            System.out.println("error: "+e.getStackTrace());
        }
    }
    
    
    public static void actualizarListaDuenios(ArrayList<DuenioMascota> lista,String path){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
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
