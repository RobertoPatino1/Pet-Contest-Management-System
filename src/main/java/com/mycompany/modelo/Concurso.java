package com.mycompany.modelo;

import com.mycompany.enums.TipoAnimalesConcurso;
import com.mycompany.proyectopoog7parte2.App;
import utils.UtilDate;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Concurso implements Serializable{
    private int id;
    private String nombre;
    private Date fechaEvento;
    private String horaEvento;
    private Date fechaInicioInscripcion;
    private Date fechaCierreInscripcion;
    private Ciudad ciudad;
    private String lugar;
    private ArrayList<Premio> listaPremio; 
    private Auspiciante auspiciante;
    private TipoAnimalesConcurso dirigidoA;
    private boolean estaAbierto; //True si el concurso esta abierto/ false si esta cerrado
    private boolean concursoFinalizado; //True si el concurso ha terminado
    private static final long serialVersionUID = 6426991392696108758L;
    private boolean seEnvioInvitacion;

    private ArrayList<Mascota> mascotasInscritas = new ArrayList<>();

    private ArrayList<Mascota> ganadores = new ArrayList<>();
    

    public Concurso(int id, String nombre, Date fechaEvento, String horaEvento, Date fechaInicioInscripcion, Date fechaCierreInscripcion, Ciudad ciudad, String lugar, ArrayList<Premio> listaPremio, Auspiciante auspiciante, TipoAnimalesConcurso dirigidoA){
        this.id = id;
        this.nombre= nombre;
        this.fechaEvento= fechaEvento;
        this.horaEvento= horaEvento;
        this.fechaInicioInscripcion= fechaInicioInscripcion;
        this.fechaCierreInscripcion= fechaCierreInscripcion;
        this.ciudad= ciudad;
        this.lugar= lugar;
        this.listaPremio= listaPremio;
        this.auspiciante= auspiciante;
        this.dirigidoA= dirigidoA;
        this.estaAbierto = true;         //El concurso esta abierto para inscripciones
        this.concursoFinalizado = false; //El concurso aun no ha terminado
    }
    

    public Concurso(int id, String nombre, Date fechaEvento, String horaEvento, Date fechaInicioInscripcion, Date fechaCierreInscripcion, Ciudad ciudad, String lugar, ArrayList<Premio>  listaPremio, Auspiciante auspiciante, TipoAnimalesConcurso dirigidoA, ArrayList<Mascota> mascotasInscritas){
        this.id = id;
        this.nombre= nombre;
        this.fechaEvento= fechaEvento;
        this.horaEvento= horaEvento;
        this.fechaInicioInscripcion= fechaInicioInscripcion;
        this.fechaCierreInscripcion= fechaCierreInscripcion;
        this.ciudad= ciudad;
        this.lugar= lugar;
        this.listaPremio= listaPremio;
        this.auspiciante= auspiciante;
        this.dirigidoA= dirigidoA;
        this.estaAbierto = true;
        this.concursoFinalizado = false;
        this.mascotasInscritas= mascotasInscritas;
    }
    
    public Concurso(int id, String nombre, Date fechaEvento, String horaEvento, Date fechaInicioInscripcion, Date fechaCierreInscripcion, Ciudad ciudad, String lugar, ArrayList<Premio>  listaPremio, Auspiciante auspiciante, TipoAnimalesConcurso dirigidoA, ArrayList<Mascota> mascotasInscritas, ArrayList<Mascota> ganadores){
        this.id = id;
        this.nombre= nombre;
        this.fechaEvento= fechaEvento;
        this.horaEvento= horaEvento;
        this.fechaInicioInscripcion= fechaInicioInscripcion;
        this.fechaCierreInscripcion= fechaCierreInscripcion;
        this.ciudad= ciudad;
        this.lugar= lugar;
        this.listaPremio= listaPremio;
        this.auspiciante= auspiciante;
        this.dirigidoA= dirigidoA;
        this.estaAbierto = false;
        this.concursoFinalizado = true;
        this.mascotasInscritas= mascotasInscritas;
        this.ganadores= ganadores;
    }
    
    public Concurso(String nombre){
        this.nombre = nombre;
    }


    public void cerrarInscripcionesConcurso(){
        estaAbierto = false;
    }

    public void finalizarConcurso(){
        concursoFinalizado = true;
    }

    public void seleccionarGanadores(ArrayList<Mascota> ganadores){
        this.ganadores = ganadores;
    }

    public void inscribirParticipantes(Mascota m){
        if(estaAbierto)
            mascotasInscritas.add(m);
        else
            System.out.println("El concurso se encuentra cerrado.");
    }

    public void consultarGanadores(){
        System.out.println("Se presenta la lista de ganadores\n");
        System.out.println(ganadores);
    }

    

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public Date getFechaInicioInscripcion() {
        return fechaInicioInscripcion;
    }

    public Date getFechaCierreInscripcion() {
        return fechaCierreInscripcion;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public String getLugar() {
        return lugar;
    }

    public ArrayList<Premio>  getListaPremio() {
        return listaPremio;
    }

    public Auspiciante getAuspiciante() {
        return auspiciante;
    }

    public TipoAnimalesConcurso getDirigidoA() {
        return dirigidoA;
    }

    public boolean isEstaAbierto() {
        return estaAbierto;
    }

    public boolean isConcursoFinalizado() {
        return concursoFinalizado;
    }

    public ArrayList<Mascota> getMascotasInscritas() {
        return mascotasInscritas;
    }

    public ArrayList<Mascota> getGanadores() {
        return ganadores;
    }
    
    public boolean getSeEnvioInvitacion(){
        return seEnvioInvitacion;
    }
    
    

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public void setFechaInicioInscripcion(Date fechaInicioInscripcion) {
        this.fechaInicioInscripcion = fechaInicioInscripcion;
    }

    public void setFechaCierreInscripcion(Date fechaCierreInscripcion) {
        this.fechaCierreInscripcion = fechaCierreInscripcion;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setPremio(ArrayList<Premio> listaPremio) {
        this.listaPremio = listaPremio;
    }

    public void setAuspiciante(Auspiciante auspiciante) {
        this.auspiciante = auspiciante;
    }

    public void setDirigidoA(TipoAnimalesConcurso dirigidoA) {
        this.dirigidoA = dirigidoA;
    }

    public void setEstaAbierto(boolean estaAbierto) {
        this.estaAbierto = estaAbierto;
    }

    public void setConcursoFinalizado(boolean concursoFinalizado) {
        this.concursoFinalizado = concursoFinalizado;
    }

    public void setMascotasInscritas(ArrayList<Mascota> mascotasInscritas) {
        this.mascotasInscritas = mascotasInscritas;
    }

    public void setGanadores(ArrayList<Mascota> ganadores) {
        this.ganadores = ganadores;
    }
    public void setSeEnvioInvitacion(boolean t){
        this.seEnvioInvitacion = t;
    }
    
    public void agregarListaYpremio(ArrayList<Premio> lista, Premio p){
        //this.listaPremio = lista;
        listaPremio.add(p);
    }
    
    public void eliminarPremio(){
        listaPremio.remove(0);
    }
    
    
    public boolean inscripcionesCerradas(){
        //Retorna true si ya se cerraron las inscripciones
        Date d = new Date();
        return d.after(this.fechaCierreInscripcion);
    }
    

    

    //Metodo equals
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o!=null&& o instanceof Concurso){
            //Downcasting
            Concurso concursoEquals = (Concurso) o;
            return this.id == concursoEquals.id;
        }
        return false;
    }


    //Metodo toString
    public String toString(){
        if(concursoFinalizado){
            return nombre+"|"+"Estado: Finalizado";

        }
        return nombre;
    }
    

    public static boolean concursoComenzado(Concurso c){
        Date d = new Date();
        return !c.getFechaEvento().after(d);
    }
    
    public void agregarPremio(Premio p){
        listaPremio.add(p);
    }
    public boolean concursoFinalizado(){
        Date d = new Date();
        return this.fechaEvento.before(d);
    }
    

    
    public static void crearArchivoConcursos(String path){
        ArrayList<Concurso> listaConcursos = new ArrayList<Concurso>();
        
        //lista de concursos para los premios
         ArrayList<Premio> premiosConcurso1 = new  ArrayList<>();
         ArrayList<Premio> premiosConcurso2 = new  ArrayList<>();
         ArrayList<Premio> premiosConcurso3 = new  ArrayList<>();
         ArrayList<Premio> premiosConcurso4 = new  ArrayList<>();
         ArrayList<Premio> premiosConcurso5 = new  ArrayList<>();
         
         //Para concurso 1
         Premio p1con1 = new Premio(1, "Comida gratis para el animal por 1 año", Auspiciante.cargarListaAuspiciantes().get(0));
         Premio p2con1 = new Premio(2, "Comida gratis para el animal por medio año", Auspiciante.cargarListaAuspiciantes().get(0));
         
         //Para concurso 2
         Premio p1con2 = new Premio(1, "Comida gratis para el animal por 1 año", Auspiciante.cargarListaAuspiciantes().get(1));
         Premio p2con2 = new Premio(2, "Comida gratis para el animal por medio año", Auspiciante.cargarListaAuspiciantes().get(1));
         
         //Para concurso 3
         Premio p1con3 = new Premio(1, "Comida gratis para el animal por 1 año", Auspiciante.cargarListaAuspiciantes().get(2));
         Premio p2con3 = new Premio(2, "Comida gratis para el animal por medio año", Auspiciante.cargarListaAuspiciantes().get(2));
         
         //Para concurso 4
         Premio p1con4 = new Premio(1, "Comida gratis para el animal por 1 año", Auspiciante.cargarListaAuspiciantes().get(0));
         Premio p2con4 = new Premio(2, "Comida gratis para el animal por medio año", Auspiciante.cargarListaAuspiciantes().get(1));
         
         //Para concurso 5
         Premio p1con5 = new Premio(1, "Comida gratis para el animal por 1 año", Auspiciante.cargarListaAuspiciantes().get(1));
         Premio p2con5 = new Premio(2, "Comida gratis para el animal por medio año", Auspiciante.cargarListaAuspiciantes().get(2));
         
         //Añadiendo los premios a los concursos
         premiosConcurso1.add(p1con1);
         premiosConcurso1.add(p2con1);
         premiosConcurso2.add(p1con2);
         premiosConcurso2.add(p2con2);
         premiosConcurso3.add(p1con3);
         premiosConcurso3.add(p2con3);
         premiosConcurso4.add(p1con4);
         premiosConcurso4.add(p2con4);
         premiosConcurso5.add(p1con5);
         premiosConcurso5.add(p2con5);

        

    
        Concurso con1 = new Concurso(1, "Love Animals", UtilDate.getDateFromString("2022-02-16"), "12:00", UtilDate.getDateFromString("2022-01-26"), UtilDate.getDateFromString("2022-02-11"),Ciudad.cargarCiudades().get(1), "Avenida nombre 1", premiosConcurso1, Auspiciante.cargarListaAuspiciantes().get(0), TipoAnimalesConcurso.TODOS);
        Concurso con2 = new Concurso(2, "Match Canino", UtilDate.getDateFromString("2022-02-17"), "13:00", UtilDate.getDateFromString("2022-01-27"), UtilDate.getDateFromString("2022-02-12"),Ciudad.cargarCiudades().get(0), "Avenida nombre 3", premiosConcurso2, Auspiciante.cargarListaAuspiciantes().get(1), TipoAnimalesConcurso.TODOS);
        Concurso con3 = new Concurso(3, "Happy Paws", UtilDate.getDateFromString("2022-02-18"), "14:00", UtilDate.getDateFromString("2022-01-28"), UtilDate.getDateFromString("2022-02-13"),Ciudad.cargarCiudades().get(2), "Avenida nombre 2", premiosConcurso3, Auspiciante.cargarListaAuspiciantes().get(2), TipoAnimalesConcurso.TODOS);
        
   
        ArrayList<Mascota> listaConcursantes4 = new ArrayList<Mascota>();
        
        ArrayList<Mascota> listaMascotas = Mascota.cargarMascotas();
        
        for(int i = 0;i<10;i++){
            listaConcursantes4.add(listaMascotas.get(i));
        }
        
        ArrayList<Mascota> listaGanadores4 = new ArrayList<>();
        listaGanadores4.add(listaConcursantes4.get(0));
        listaGanadores4.add(listaConcursantes4.get(6));
        listaGanadores4.add(listaConcursantes4.get(3));        
        
        Concurso con4 = new Concurso(4, "Heroes Gatunos", UtilDate.getDateFromString("2021-11-16"), "11:00", UtilDate.getDateFromString("2021-10-26"), UtilDate.getDateFromString("2021-11-11"),Ciudad.cargarCiudades().get(0), "14 y Francisco segura", premiosConcurso4, Auspiciante.cargarListaAuspiciantes().get(2), TipoAnimalesConcurso.GATOS, listaConcursantes4,listaGanadores4);
        
        //Concurso 5
        ArrayList<Mascota> listaConcursantes5 = new ArrayList<Mascota>();
        for(int i = 4;i<20;i++){
            listaConcursantes5.add(listaMascotas.get(i));
        }
        //Seleccionamos a 3 ganadores
        ArrayList<Mascota> listaGanadores5 = new ArrayList<>();
        listaGanadores5.add(listaConcursantes5.get(0));
        listaGanadores5.add(listaConcursantes5.get(6));
        listaGanadores5.add(listaConcursantes5.get(3));
        //System.out.print(listaGanadores5);
        
        Concurso con5 = new Concurso(5, "Heroes Caninos", UtilDate.getDateFromString("2021-11-17"), "12:00", UtilDate.getDateFromString("2021-10-27"), UtilDate.getDateFromString("2021-11-12"),Ciudad.cargarCiudades().get(1), "10 y Francisco segura", premiosConcurso5, Auspiciante.cargarListaAuspiciantes().get(1), TipoAnimalesConcurso.PERROS,listaConcursantes5,listaGanadores5);

        //Se agregan todos los concursos a la lista general
        listaConcursos.add(con1);
        listaConcursos.add(con2);
        listaConcursos.add(con3);
        listaConcursos.add(con4);
        listaConcursos.add(con5);
                
        
        try(ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(path))){
            //Se escribe la lista con los objetos
            escritor.writeObject(listaConcursos);
        }catch(IOException e){
            System.out.println("Error al crear el archivo serializado: "+e);
        }catch(Exception e){
            System.out.println("Error inesperado: "+e);
        }
    }
    
    

  
    
    
    
    public static ArrayList<Concurso> cargarListaConcursos(){
        ArrayList<Concurso> listaConcursos = new ArrayList<Concurso>();
        try(ObjectInputStream lector = new ObjectInputStream(new FileInputStream(App.pathConcursos))){
            listaConcursos = (ArrayList<Concurso>) lector.readObject();
        }catch(FileNotFoundException e){
            System.out.println("Archivo no encontrado: "+e);
        }catch(IOException e){
            System.out.println("Error al cargar la lista de concursos: "+e);
        }catch(Exception e){
            System.out.println("Error inesperado: "+e);
        }
        return listaConcursos;
    }
    
    
    public static void actualizarArchivoConcursos(Concurso c){
        ArrayList<Concurso> listaConcursos = cargarListaConcursos();
        listaConcursos.add(c);
        
        //Ahora actualizamos la lista con el concurso escrito
        try(ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(App.pathConcursos))){
            //Se escribe la lista con los auspiciantes actualizada
            escritor.writeObject(listaConcursos);
        }catch(FileNotFoundException e){
            System.out.println("No se encontro el archivo");
        }catch(IOException e){
            System.out.println("Error al escribir: "+e);
        }catch(Exception e){
            System.out.println("Error general");
        }
        
    }
    
    
    public static void actualizarListaConcursos(ArrayList<Concurso> lista){
        int i = 0;

        ArrayList<Concurso> listaActualizada = new ArrayList<>();
        
        try(ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(App.pathConcursos))){
            for(Concurso c:lista){
                i++;
                c.setId(i);
                listaActualizada.add(c);
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