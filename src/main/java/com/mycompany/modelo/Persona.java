package com.mycompany.modelo;

import java.io.Serializable;

public class Persona implements Serializable{
    protected String nombre;
    protected String direccion;
    protected String telefono; 
    protected Ciudad ciudad;
    protected String email;
    
    public Persona(String nombre, String direccion, String telefono, Ciudad ciudad, String email){
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.email = email;
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

    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public void setDireccion(String direccion){
        this.direccion=direccion;
    }
    public void setTelefono(String telefono){
        this.telefono=telefono;
    }
    public void setCiudad(Ciudad ciudad){
        this.ciudad=ciudad;
    }
    public void setEmail(String email){
        this.email=email;
    }


    public String toString(){
        return nombre;
    }
}