package com.mycompany.modelo;

import com.mycompany.enums.TipoAnimal;
import utils.UtilDate;
import com.mycompany.proyectopoog7parte2.App;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Mascota implements Serializable{
    private String nombre;
    private TipoAnimal tipoMascota;
    private String raza;
    private Date fechaDeNacimiento;
    private String foto;
    private DuenioMascota duenio;
    private int id;
    private boolean disponibleParaFuturasInscripciones;
    private boolean haParticipado;


    public Mascota(int id, String nombre, TipoAnimal tipoMascota, String raza, Date fechaDeNacimiento, String foto, DuenioMascota duenio){
        this.nombre = nombre;
        this.tipoMascota = tipoMascota;
        this.raza = raza;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.foto = foto;
        this.duenio = duenio;
        this.id = id;
        this.disponibleParaFuturasInscripciones = true;
        this.haParticipado = false; //Se intuye que ninguna mascota ha participado en un concurso
    }
    


    public String getNombre() {
        return nombre;
    }

    public TipoAnimal getTipoMascota() {
        return tipoMascota;
    }

    public String getRaza() {
        return raza;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public String getFoto() {
        return foto;
    }

    public DuenioMascota getDuenio() {
        return duenio;
    }

    public int getId() {
        return id;
    }

    public boolean isDisponibleParaFuturasInscripciones() {
        return disponibleParaFuturasInscripciones;
    }

    public boolean isHaParticipado() {
        return haParticipado;
    }
    
    


    public void setDisponibilidadDeInscripciones(boolean disp){
        this.disponibleParaFuturasInscripciones = disp;
    }
    
    public void setParticipacion(boolean part){
        this.haParticipado = part;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipoMascota(TipoAnimal tipoMascota) {
        this.tipoMascota = tipoMascota;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setDuenio(DuenioMascota duenio) {
        this.duenio = duenio;
    }

    public void setDisponibleParaFuturasInscripciones(boolean disponibleParaFuturasInscripciones) {
        this.disponibleParaFuturasInscripciones = disponibleParaFuturasInscripciones;
    }

    public void setHaParticipado(boolean haParticipado) {
        this.haParticipado = haParticipado;
    }
    
    


    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o!=null&&o instanceof Mascota){
            //Downcasting
            Mascota mascotaEquals = (Mascota) o;
            return this.id == mascotaEquals.id;
        }
        return false;
    }


    public String toString(){
        return nombre.toUpperCase();
    }
    
    
    
    
    
    

    
    public static ArrayList<Mascota> cargarMascotas(){
        ArrayList<Mascota> listaMascotas = new ArrayList<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(App.pathMascotas))){
            String line;
            while((line = br.readLine())!=null){
                String[] datos = line.strip().split(";");
                int indiceDuenio = Integer.valueOf(datos[6]);       
                DuenioMascota d = DuenioMascota.cargarDuenios().get(indiceDuenio-1);        
                if(datos[2].toUpperCase().equals("PERRO")){
                    Mascota m = new Mascota(Integer.valueOf(datos[0]),datos[1],TipoAnimal.PERRO,datos[3],UtilDate.getDateFromString(datos[4]),datos[5],d);
                    listaMascotas.add(m);
                }else{
                    Mascota m = new Mascota(Integer.valueOf(datos[0]),datos[1],TipoAnimal.GATO.GATO,datos[3],UtilDate.getDateFromString(datos[4]),datos[5],d);
                    listaMascotas.add(m);
                }
            }
            
        }catch(IOException e){
            System.out.println("Ha ocurrido un error: "+e);
        }return listaMascotas;
    }
    

    public static void escribirMascota(Mascota m){
        try(BufferedWriter fw = new BufferedWriter(new FileWriter(App.pathMascotas,true))){
 
            Date date = m.getFechaDeNacimiento();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaString = dateFormat.format(date);
    
           
            int indiceDuenio = DuenioMascota.cargarDuenios().indexOf(m.duenio);
            int idDuenio = DuenioMascota.cargarDuenios().get(indiceDuenio).getId();
            fw.write(m.id+";"+m.nombre+";"+m.tipoMascota+";"+m.raza+";"+fechaString+";"+m.foto+";"+idDuenio+"\n");
            
            
        }catch(IOException e){
            System.out.println("error: "+e.getStackTrace());
        }
        
    }
    

    public static void actualizarListaMascotas(ArrayList<Mascota> lista){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(App.pathMascotas))){

            int  i = 0;
            for(Mascota m: lista){
                i++;
                m.setId(i);
     
                Date date = m.getFechaDeNacimiento();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String fechaString = dateFormat.format(date);

                int indiceDuenio = DuenioMascota.cargarDuenios().indexOf(m.duenio);
                int idDuenio = DuenioMascota.cargarDuenios().get(indiceDuenio).getId();

                
                bw.write(m.getId()+";"+m.getNombre()+";"+m.getTipoMascota()+";"+m.getRaza()+";"+fechaString+";"+m.getFoto()+";"+idDuenio+"\n");
            }
        }catch(IOException e){
            
            System.out.println("Error al actualizar el archivo de mascotas: "+e);
        }
    }
    
}
