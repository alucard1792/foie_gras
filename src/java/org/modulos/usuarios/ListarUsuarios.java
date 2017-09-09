/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.modulos.usuarios;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.dao.AreaFacadeLocal;
import org.dao.PersonaFacadeLocal;
import org.dao.RolFacadeLocal;
import org.entidades.Area;
import org.entidades.Persona;
import org.entidades.Rol;

/**
 *
 * @author David
 */
@Named(value = "listarUsuarios")
@ConversationScoped
public class ListarUsuarios implements Serializable {

    @EJB
    private PersonaFacadeLocal pfl;
    @EJB
    private RolFacadeLocal rfl;
    @EJB
    private AreaFacadeLocal afl;
    @Inject
    private Conversation conversacion;
    private Persona personaSeleccionado;
    
    private int rol;
    private int area;
    private Date fechaNacimiento;
    private Date fechaIngreso;
    private List<Rol> roles;
    private Area areas;
    private List<Persona> persona;
    private List<Rol> listaRoles;
    private List<Area> listaAreas;
    public ListarUsuarios() {

    }

    @PostConstruct
    public void init() {
        persona = pfl.findAll();
        listaAreas = afl.findAll();
        listaRoles = rfl.findAll();
        
    }

    public List<Persona> getPersona() {
        return persona;

    }

    public Persona getPersonaSeleccionado() {
        return personaSeleccionado;
    }

    public void setPersonaSeleccionado(Persona personaSeleccionado) {
        this.personaSeleccionado = personaSeleccionado;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public Area getAreas() {
        return areas;
    }

    public void setAreas(Area areas) {
        this.areas = areas;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public List<Area> getListaAreas() {
        return listaAreas;
    }

    public void setListaAreas(List<Area> listaAreas) {
        this.listaAreas = listaAreas;
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
        return "/admin/usuarios/listarUsuarios.xhtml?faces-redirect=true";

    }

    public String preparacionEditar(Persona p) {
        iniciarConversacion();
        personaSeleccionado = p;

        return "/admin/usuarios/editar.xhtml?faces-redirect=true";
    }

    public String actualizarUsuario() {
        pfl.edit(personaSeleccionado);
        return cancelar();

    }

    public void preparacionEliminar(Persona p) {
        iniciarConversacion();
        personaSeleccionado = p;
        System.out.printf("se eliminar: %s", personaSeleccionado.getNombre());

    }

    public void eliminarUsuario() {
        try {
            System.out.println("entro a eliminar");
            pfl.remove(personaSeleccionado);
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario eliminado correctamente", "");
            FacesContext.getCurrentInstance().addMessage(null, msj);

        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error al emiminar el usuario", "intentelo de nuevo");
            FacesContext.getCurrentInstance().addMessage(null, msj);

        }

    }

}
