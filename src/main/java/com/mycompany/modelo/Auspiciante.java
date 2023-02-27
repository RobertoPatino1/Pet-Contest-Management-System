package com.mycompany.modelo;
import com.mycompany.proyectopoog7parte2.App;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Auspiciante extends Persona implements Serializable{
    private int codigo;
    private String webPage;
    private static final long serialVersionUID = -4765000104052213919L;

    public Auspiciante(int codigo, String nombre, String direccion, String telefono, Ciudad ciudad, String email, String webPage){
        super(nombre, direccion, telefono, ciudad, email);
        this.webPage = webPage;
        this.codigo = codigo;
    }


    public String getWebPage() {
        return webPage;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public String getEmail() {
        return email;
    }
    public int getCodigo(){
        return codigo;
    }
    

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setId(int codigo){
        this.codigo = codigo;
    }
    

    public String toString(){
        return super.getNombre();
    }
    
    
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o!=null&&o instanceof Auspiciante){
            //Downcasting
            Auspiciante auspiciante = (Auspiciante) o;
            return this.codigo == auspiciante.codigo;
        }
        return false;
    }
    
    public static void generarArchivoAuspiciantes(String path){
        ArrayList<Auspiciante> listaAuspiciantes = new ArrayList<>();
            Auspiciante au1 = new Auspiciante(1, "Vet Cruz del Sur", "direccion1","11111111",Ciudad.cargarCiudades().get(0),"email1@gmail.com","webpage1.com");
            Auspiciante au2 = new Auspiciante(2, "Dog Chow", "direccion2","22222222",Ciudad.cargarCiudades().get(1),"email2@gmail.com","webpage2.com");
            Auspiciante au3 = new Auspiciante(3, "Dr Pet", "direccion3","33333333",Ciudad.cargarCiudades().get(2),"email3@gmail.com","webpage3.com");
            Auspiciante au4 = new Auspiciante(4, "Patitas limpias", "direccion4","44444444",Ciudad.cargarCiudades().get(3),"email4@gmail.com","webpage4.com");
            listaAuspiciantes.add(au1);
            listaAuspiciantes.add(au2);
            listaAuspiciantes.add(au3);
            listaAuspiciantes.add(au4);
        try(ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(path))){
            escritor.writeObject(listaAuspiciantes);
        }catch(FileNotFoundException e){
            System.out.println("No se encontro el archivo");
        }catch(IOException e){
            System.out.println("Error al escribir: "+e);
        }catch(Exception e){
            System.out.println("Error general");
        }
    }
    
    
    
    public static ArrayList<Auspiciante> cargarListaAuspiciantes(){
        ArrayList<Auspiciante> listaAuspiciantes = new ArrayList<>();
        try(ObjectInputStream lector = new ObjectInputStream(new FileInputStream(App.pathAuspiciantes))){
            listaAuspiciantes = (ArrayList<Auspiciante>)lector.readObject();
        }catch(FileNotFoundException e){
            System.out.println("No se ha encontrado el archivo");
        }catch(ClassNotFoundException e){
            System.out.println("Error");
        }catch(IOException e){
            System.out.println("Error al leer los datos: "+e.getMessage()+e);
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("Error inesperado");
        }
        return listaAuspiciantes;
        
    }
    
    public static void actualizarArchivoAuspiciantes(Auspiciante au){
        ArrayList<Auspiciante> listaAuspiciantes = cargarListaAuspiciantes();
        listaAuspiciantes.add(au);
        try(ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(App.pathAuspiciantes))){
            escritor.writeObject(listaAuspiciantes);
        }catch(FileNotFoundException e){
            System.out.println("No se encontro el archivo");
        }catch(IOException e){
            System.out.println("Error al escribir: "+e);
        }catch(Exception e){
            System.out.println("Error general");
        }
    }
    
    public static void actualizarListaAuspiciantes(ArrayList<Auspiciante> lista){
        int i = 0;
        ArrayList<Auspiciante> listaActualizada = new ArrayList<>();
        
        try(ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(App.pathAuspiciantes))){
            for(Auspiciante a:lista){
                i++;
                a.setId(i);
                listaActualizada.add(a);
            }
            
           escritor.writeObject(listaActualizada);
        }catch(IOException e){
            System.out.println("Error al escribir: "+e);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Error con los limites al actualizar el archivo");
        }catch(Exception e){
            System.out.println("Error general: "+e);
        }
        
    }
    

    
}
