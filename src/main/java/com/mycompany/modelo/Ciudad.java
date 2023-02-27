package com.mycompany.modelo;

import com.mycompany.proyectopoog7parte2.App;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Ciudad implements Serializable {
    private int id;
    private String nombre;
    private String provincia;
    private static final long serialVersionUID = -7666025569578416275L;
    
    public Ciudad(int id,String nombre, String provincia){
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
    }
    public Ciudad(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }   

    public String getProvincia() {
        return provincia;
    }
    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    
    
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        if(o!=null&&getClass()==o.getClass()){
            //Se hace el downcasting
            Ciudad c = (Ciudad) o;
            return c.id==this.id;
        }else{
            return false;
        }
    }
    
    public String toString(){
        return nombre;
    }
    

    public static ArrayList<Ciudad> cargarCiudades(){
        ArrayList<Ciudad> listaCiudades = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(App.pathCiudades))){
            String line;
            while ((line=br.readLine()) != null) {
                
                String[] datos = line.split(",");
                Ciudad c = new Ciudad(Integer.valueOf(datos[0]), datos[1],datos[2]);
                listaCiudades.add(c);
                
            }
            

        } catch (IOException e) {
            System.out.println("ERROR IO");
            e.printStackTrace();
        }
        return listaCiudades;
        
    }
    
    public static void escribirCiudad(Ciudad c){
            try(BufferedWriter fw = new BufferedWriter(new FileWriter(App.pathCiudades,true))){
            int id = Ciudad.cargarCiudades().size()+1;
            fw.write(id+","+c.nombre+","+c.provincia+"\n");
            
            
        }catch(IOException e){
            System.out.println("error: "+e.getStackTrace());
        }
    }
    
    public static void actualizarListaCiudades(ArrayList<Ciudad> lista){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(App.pathCiudades))){
            int i = 0;
            for(Ciudad c: lista){
                i++;
                c.setId(i);
                bw.write(c.getId()+","+c.getNombre()+","+c.getProvincia()+"\n");
            }
        }catch(IOException e){
            System.out.println(e);
        }
        
    }
    
}
