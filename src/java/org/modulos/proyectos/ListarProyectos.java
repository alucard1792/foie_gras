/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.modulos.proyectos;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.dao.DificultadFacadeLocal;
import org.dao.EstadoFacadeLocal;
import org.dao.ProyectoFacadeLocal;
import org.entidades.Dificultad;
import org.entidades.Estado;
import org.entidades.Proyecto;
import org.entidades.Rol;
import org.login.ControladorSesion;

/**
 *
 * @author David
 */
@Named(value = "listarProyectos")
@ConversationScoped
public class ListarProyectos implements Serializable {

    @EJB
    private ProyectoFacadeLocal pfl;
    @EJB
    private DificultadFacadeLocal dificultadFacadeLocal;
    @EJB
    private EstadoFacadeLocal estadoFacadeLocal;
    @Inject
    private Conversation conversacion;
    @Inject
    private ControladorSesion controladorSesion;
    private Proyecto proyectoSeleccionado;
    private List<Proyecto> proyecto;
    //datos temporales
    private Estado estado;
    private Dificultad dificultad;
    private List<Estado> listaEstados;
    private List<Dificultad> listaDificultades;
    private Date currentDate;
    
    public ListarProyectos() {

    }

    @PostConstruct
    public void init() {
        currentDate = new Date();
        for (Rol rol : controladorSesion.getP().getRoles()) {
            if (rol.getIdRol() == 1 || rol.getIdRol() == 2) {
                proyecto = pfl.findAll();

            } else if (rol.getIdRol() == 4 || rol.getIdRol() == 5) {
                proyecto = pfl.listarProyectosOperariosAsignados(controladorSesion.getP());

            }

        }

    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public List<Estado> getListaEstados() {
        return listaEstados;
    }

    public void setListaEstados(List<Estado> listaEstados) {
        this.listaEstados = listaEstados;
    }

    public List<Dificultad> getListaDificultades() {
        return listaDificultades;
    }

    public void setListaDificultades(List<Dificultad> listaDificultades) {
        this.listaDificultades = listaDificultades;
    }

    public ControladorSesion getControladorSesion() {
        return controladorSesion;
    }

    public void setControladorSesion(ControladorSesion controladorSesion) {
        this.controladorSesion = controladorSesion;
    }

    public List<Proyecto> getProyecto() {
        return proyecto;
    }

    public Proyecto getProyectoSeleccionado() {
        return proyectoSeleccionado;
    }

    public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
        this.proyectoSeleccionado = proyectoSeleccionado;
    }

    private void iniciarConversacion() {
        if (conversacion.isTransient()) {
            conversacion.begin();

        }

    }

    private void terminarConversacion() {
        if (!conversacion.isTransient()) {
            conversacion.end();

        }

    }

    public String cancelar() {
        terminarConversacion();
        return "/admin/proyectos/listarProyectos.xhtml?faces-redirect=true";

    }

    public String preparacionEditar(Proyecto p) {
        iniciarConversacion();
        listaDificultades = dificultadFacadeLocal.findAll();
        listaEstados = estadoFacadeLocal.findAll();
        proyectoSeleccionado = p;
        return "/admin/proyectos/editarProyecto.xhtml?faces-redirect=true";

    }
     
    public String iniciarProyecto() {
        Calendar cal = Calendar.getInstance();
        proyectoSeleccionado.setFechaInicio(cal.getTime());
        estado = new Estado(1);
        proyectoSeleccionado.setEstadosIdEstado(estado);
        pfl.edit(proyectoSeleccionado);
        return cancelar();

    }
     
    public String finalizarProyecto(Proyecto p) {
        Calendar cal = Calendar.getInstance();
        proyectoSeleccionado = p;
        proyectoSeleccionado.setFechaFinalizado(cal.getTime());
        pfl.edit(proyectoSeleccionado);
        return cancelar();

    }

    public String actualizarProyecto() {
        Calendar cal = Calendar.getInstance();
        proyectoSeleccionado.setFechaInicio(cal.getTime());
        pfl.edit(proyectoSeleccionado);
        return cancelar();

    }

    public void eliminarProyecto() {
        try {
            System.out.println("entro a eliminar");
            pfl.remove(proyectoSeleccionado);
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, "proyecto eliminado correctamente", "");
            FacesContext.getCurrentInstance().addMessage(null, msj);

        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error al eliminar el usuario", "intentelo de nuevo");
            FacesContext.getCurrentInstance().addMessage(null, msj);

        }

    }

}
