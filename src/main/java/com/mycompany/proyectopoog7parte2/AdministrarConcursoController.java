
package com.mycompany.proyectopoog7parte2;

import com.mycompany.modelo.Auspiciante;
import com.mycompany.modelo.Ciudad;
import com.mycompany.modelo.Concurso;
import com.mycompany.modelo.DuenioMascota;
import com.mycompany.modelo.Mascota;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import utils.AlertGenerator;
import utils.Correo;


public class AdministrarConcursoController{

    @FXML
    private Button opCrearConcurso;
    @FXML
    private Button opCancelarAdConcurso;
    @FXML
    private TableView tvConcursos;
    @FXML
    private TableColumn<Concurso, Integer> colCodigo;
    @FXML
    private TableColumn<Concurso, String> colNombre;
    @FXML
    private TableColumn<Concurso, Date> colFecha;
    @FXML
    private TableColumn<Concurso, Ciudad> colCiudad;
    @FXML
    private TableColumn<Concurso, Void> colOpciones;
    @FXML
    private BorderPane bpAdministrarConcursos;
    


    public void initialize() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaEvento"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        agregarOpciones();
        
        tvConcursos.getItems().addAll(Concurso.cargarListaConcursos());
    }    

    @FXML
    private void switchToCrearConcurso() throws  IOException{
        App.setRoot("crearConcurso");
    }


    @FXML
    private void switchToMenuPrincipal() throws  IOException {
        App.setRoot("menuPrincipal");
        
    }

    
    
    //Metodo para editar
    private void editarConcurso(Concurso c){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("crearConcurso.fxml"));//no tiene el controlador especificado
            Parent root = (Parent) fxmlLoader.load();
            CrearConcursoController ct = fxmlLoader.getController();
            fxmlLoader.setController(ct);//se asigna el controlador
            

            ct.llenarComboCiudad(Ciudad.cargarCiudades());
            ct.llenarComboDirigidoA();
            ct.llenarComboAuspiciantes(Auspiciante.cargarListaAuspiciantes());
            ct.llenarTVPremios(c);
            ct.llenarCamposConcurso(c);
            

            App.changeRoot(root);
            
        }catch(IOException e){
            System.out.println("Error IO: "+e);
        }catch(Exception e){
            System.out.println("Excepcion general al cambiar de escena: "+e);
        }
    }
    
    
    private void eliminarConcurso(Concurso c){
        try{
            Alert confirmacion = AlertGenerator.generateAlert(Alert.AlertType.CONFIRMATION,null,"Eliminacion","Se procederá a eliminar al concurso de los registros.\n ¿Continuar?");
            Optional<ButtonType> result = confirmacion.showAndWait();
            if(result.get()==ButtonType.OK){
                ArrayList<Concurso> lista = Concurso.cargarListaConcursos();
                int indice = lista.indexOf(c);
                lista.remove(indice); 
                ArrayList<Concurso> l = lista;
                Concurso.actualizarListaConcursos(l);
                App.setRoot("administrarConcurso");

            }
            }catch(Exception e){
                System.out.println("Error al eliminar el concurso: "+e.getLocalizedMessage());
                e.printStackTrace();
                e.getCause();

            }    
        
    }
    
    
    
    
    private void enviarInvitaciones(Concurso c){
             
        send(c);

        ArrayList<Concurso> listaConcurso = Concurso.cargarListaConcursos();
        int indice = listaConcurso.indexOf(c);
        listaConcurso.set(indice, c);
        Concurso.actualizarListaConcursos(listaConcurso);
        try{
        App.setRoot("administrarConcurso");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    

    private void agregarOpciones() {
        Callback<TableColumn<Concurso, Void>, TableCell<Concurso, Void>> cellFactory = new Callback<TableColumn<Concurso, Void>, TableCell<Concurso, Void>>() {
            @Override
            public TableCell<Concurso, Void> call(final TableColumn<Concurso, Void> param) {
                TableCell<Concurso, Void> cell = new TableCell<Concurso, Void>() {
                   
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            //hbox para ubicar los botones
                            HBox hbOpciones = new HBox(5);
                            //recuperar el duenio
                            //Ciudad ciudad = getTableView().getItems().get(getIndex());
                            //Auspiciante auspiciante = getTableView().getItems().get(getIndex());
                            Concurso concurso = getTableView().getItems().get(getIndex());
                  
                            //boton editar
                            /*
                            ############
                            Solo disponible para concursos no pasados
                            ############
                            */
                            Button btnEd = new Button("Editar");
                            if(concurso.concursoFinalizado()){
                                btnEd.setVisible(false);
                            }
                            btnEd.setOnAction(e ->editarConcurso(concurso));
                               
                            //boton eliminar
                            /*
                            ############
                            Solo disponible para concursos no pasados
                            ############
                            */
                            
                            Button btnEl = new Button("Eliminar");
                            if(concurso.concursoFinalizado()||concurso.getSeEnvioInvitacion()){
                                btnEl.setVisible(false);
                                //btnEl.setDisable(true);
                            }
                            btnEl.setOnAction(e -> eliminarConcurso(concurso));
                            
                            
                            /*
                            ########################
                            BOTON PARA ENVIAR CORREO
                            ########################
                            */
                            Button btnCorreo = new Button("Enviar invitaciones");
                            if(concurso.concursoFinalizado()||concurso.inscripcionesCerradas()){
                                btnCorreo.setDisable(true);
                            }
                            btnCorreo.setOnAction(e -> enviarInvitaciones(concurso));
                            
                            
                            //se agregan botones al hbox
                            
                            /*
                            ###########################################
                            Boton para consultar las mascotas inscritas
                            ###########################################
                            */
                            Button btnConsultar = new Button("Consultar inscritos");
                            
                            //Aqui debe ir el onAction
                            btnConsultar.setOnAction(e->consultarInscritos(concurso));
                            /*
                            ###################################################
                            Boton para consultar la lista de mascotas ganadoras
                            ###################################################
                            */
                            Button btnGanadores = new Button("Consultar ganadores");
                            if(!concurso.concursoFinalizado()){
                                btnGanadores.setVisible(false);
                            }
                            btnGanadores.setOnAction(e->consultarGanadores(concurso));
                            
                            //Aqui debe ir el onAction
                            
                            hbOpciones.getChildren().addAll(btnEd,btnEl,btnCorreo,btnConsultar,btnGanadores);
                            //se ubica hbox en la celda
                            setGraphic(hbOpciones);
                        }
                    }
                };
                return cell;
            }
        };

        colOpciones.setCellFactory(cellFactory);

    }
    
    public void send(Concurso c){
        c.setSeEnvioInvitacion(true);
        System.out.println(c.getSeEnvioInvitacion());
        Thread t = new Thread(()->{
        String asunto = "Invitacion a concurso de mascotas";
        String cuerpo = "Te invitamos a formar parte del concurso "+c.getNombre()+
                "\n Ven y diviertete junto a tu mascota el dia: "+c.getFechaEvento()+
                "\n Hora: "+c.getHoraEvento()+
                "\nLugar: "+c.getLugar()+"\n Inicio de inscripciones: "+c.getFechaInicioInscripcion()+
                "\nFin de inscripciones: "+c.getFechaCierreInscripcion()+"\n Concurso para: "+c.getDirigidoA()+"\n Te esperamos!";
        for(DuenioMascota d: DuenioMascota.cargarDuenios()){
            String destinatario = d.getEmail();
            System.out.println("Enviando correo a: "+d.getEmail());
            
            Correo.enviarCorreo(destinatario, asunto, cuerpo);
            System.out.println("Correo enviado!");
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }      
        });
        t.setDaemon(true);
        t.start();

    }
    
    
    /*
    #########################################################
    METODOS PARA CONSULTAR PARTICIPANTES Y LISTA DE GANADORES
    #########################################################
    */
    
    //Metodo para consultar participantes
    private void consultarInscritos(Concurso c){
        try{
            ArrayList<Mascota> listaMascotasInscritas = c.getMascotasInscritas();
            
            opCrearConcurso.setText("Inscribir participante");
            opCrearConcurso.setVisible(false);
            HBox hbox = new HBox(20);
            HBox hbox2 = new HBox(100);
            VBox vbox = new VBox(20);
            Label mInscritas = new Label("Mascotas inscritas:");
            Label mNoInscritas = new Label("Mascotas no inscritas:");
            hbox2.getChildren().addAll(mInscritas,mNoInscritas);
            
            //tvConcursos.getItems().setAll(listaMascotasInscritas);
            bpAdministrarConcursos.setCenter(null);
            ListView<Mascota> lvInscritos = new ListView<>();
            lvInscritos.getItems().setAll(listaMascotasInscritas);
            
            ListView<Mascota> listViewNoInscritos = new ListView<>();
            ArrayList<Mascota> listaNoInscritos = Mascota.cargarMascotas();
            ArrayList<Mascota> copiaNoInscritos = Mascota.cargarMascotas();

            //Eliminando las mascotas que ya estan inscritas de la lista
            for(Mascota m :listaNoInscritos){
                if(listaMascotasInscritas.contains(m)){
                    copiaNoInscritos.remove(m);               
                }
            }
            //Se crea el lv
            
            if(c.concursoFinalizado()){
                mNoInscritas.setVisible(false);
                hbox.getChildren().addAll(lvInscritos);
                vbox.getChildren().addAll(hbox2,hbox);
                bpAdministrarConcursos.setCenter(vbox);
            }else{
                listViewNoInscritos.getItems().setAll(copiaNoInscritos);
            
                hbox.getChildren().addAll(lvInscritos,listViewNoInscritos);
                vbox.getChildren().addAll(hbox2,hbox);
                bpAdministrarConcursos.setCenter(vbox);
            }

            opCrearConcurso.setOnAction(e->{
                Mascota m = (Mascota) listViewNoInscritos.getSelectionModel().getSelectedItem();
                System.out.println("Se procedera a inscribir a: "+m);
                listaMascotasInscritas.add(m);
                //Se la retira de la lista original
                copiaNoInscritos.remove(m);
                
                //Se actualizan los lv
                
                listViewNoInscritos.getItems().remove(m);
                lvInscritos.getItems().add(m);
                System.out.println(listaMascotasInscritas);
                
            });
            
            
            opCancelarAdConcurso.setText("Atrás");


            opCancelarAdConcurso.setOnAction(e->{
                try {
                    //Se setter a los participantes
                    c.setMascotasInscritas(listaMascotasInscritas);
                    
                    //Se obtiene al concurso
                    int indice = Concurso.cargarListaConcursos().indexOf(c);
                    //Se reemplaza
                    ArrayList<Concurso> listaActualizada =Concurso.cargarListaConcursos();

                    listaActualizada.get(indice).setMascotasInscritas(listaMascotasInscritas);
                    Concurso.actualizarListaConcursos(listaActualizada);
    
                    App.setRoot("administrarConcurso");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            //OnAction sobre el list view
            listViewNoInscritos.setOnMouseClicked(e->opCrearConcurso.setVisible(true));
            
            
        }catch(Exception e){
            System.out.println("ERROR: ");
            System.out.println(e);
        }
        
    }
    
    
     
    private void consultarGanadores(Concurso c){
        try{
            //Obtenemos la lista de concursos
            ArrayList<Concurso> lista = Concurso.cargarListaConcursos();
            //System.out.println(lista);
            opCrearConcurso.setVisible(false);
            
            //Estructura de la tabla
            HBox hbox = new HBox(20);
            VBox vbox = new VBox(20);
            Label ganadoresl = new Label("Ganadores:");
            hbox.getChildren().add(ganadoresl);
            bpAdministrarConcursos.setCenter(null);
            
            ListView<Mascota> lvGanadores = new ListView<>();
            //Se crea el lv [comprobacion de seuridad por nombre y estado]
            for(Concurso x: lista){
                if (x.getNombre().equals(c.getNombre())){
                    //System.out.println(x.getNombre());
                    lvGanadores.getItems().setAll(x.getGanadores());          
                } 
            }
            if(c.concursoFinalizado()){
                hbox.getChildren().add(lvGanadores);
                vbox.getChildren().addAll(hbox);
                bpAdministrarConcursos.setCenter(vbox);
            };
          
            //Boton de cancelar
            opCancelarAdConcurso.setText("Atrás");
            opCancelarAdConcurso.setOnAction(e->{
                try {
                    App.setRoot("administrarConcurso");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            
        //OnAction sobre el list view           
        }catch(Exception e){
            System.out.println("ERROR: ");
            System.out.println(e);
        }
        
    }
    
}
