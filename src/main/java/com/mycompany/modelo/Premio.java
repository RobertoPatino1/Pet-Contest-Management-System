package com.mycompany.modelo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Premio implements Serializable{
    private int lugar;
    private String descripcion;
    private Auspiciante auspiciante;
    private static final long serialVersionUID = -204119547411335783L;
    
    public Premio(int lugar, String descripcion, Auspiciante auspiciante){
        this.lugar = lugar;
        this.descripcion = descripcion;
        this.auspiciante = auspiciante;
    }
    


    public int getLugar() {
        return lugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Auspiciante getAuspiciante() {
        return auspiciante;
    }

    public void setLugar(int lugar) {
        this.lugar = lugar;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setAuspiciante(Auspiciante auspiciante) {
        this.auspiciante = auspiciante;
    }
    
    @Override
    public String toString(){
        return lugar+". "+descripcion+",auspicia: "+auspiciante;
    }
    
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        if(o!=null&&getClass()==o.getClass()){
            //Se hace el downcasting
            Premio p = (Premio) o;
            return p.descripcion==this.descripcion&&p.lugar==this.lugar;
        }else{
            return false;
        }
    }
    
    

    public static void generarArchivoPremios(String path){
        ArrayList<Premio> listaPremios = new ArrayList<>();
        ArrayList<Auspiciante> listaAuspiciantes = Auspiciante.cargarListaAuspiciantes();
        listaPremios.add(new Premio(1, "$200 y productos Dog Chow", listaAuspiciantes.get(1)));
        listaPremios.add(new Premio(2, "$100 y productos Dog Chow", listaAuspiciantes.get(1)));
        listaPremios.add(new Premio(3, "50 y consulta gratis Dr Pet", listaAuspiciantes.get(2)));
        try(ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(path))){            
            escritor.writeObject(listaPremios);
            
        }catch(FileNotFoundException e){
            System.out.println("No se pudo encontrar el archivo");
        }catch(IOException e){
            System.out.println("No se pudo escribir");
        }catch(Exception e){
            System.out.println("Ha ocurrido un error inesperado");
        }
    }
    
    
    

    public static ArrayList<Premio> cargarListaPremios(String path){
        ArrayList<Premio> listaPremios = new ArrayList<>();
        try(ObjectInputStream lector = new ObjectInputStream(new FileInputStream(path))){
            listaPremios = (ArrayList<Premio>) lector.readObject();
            
        }catch(FileNotFoundException e){
            System.out.println("Archivo no encontrado: "+e);
        }catch(IOException e){
            System.out.println("No se pudo leer: "+e);
        }catch(Exception e){
            System.out.println("Error general: "+e);
        }
        return listaPremios;
    }
    


    public static void actualizarArchivoPremios(Premio pr,String path){
        ArrayList<Premio> listaPremios = cargarListaPremios(path);
        listaPremios.add(pr);
        try(ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(path))){
            escritor.writeObject(listaPremios);
        }catch(FileNotFoundException e){
            System.out.println("No se encontro el archivo");
        }catch(IOException e){
            System.out.println("Error al escribir: "+e);
        }catch(Exception e){
            System.out.println("Error general");
        }
    }
    
    
    
    
    
}
