/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.modulos.roles;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.dao.RolFacadeLocal;
import org.entidades.Rol;

/**
 *
 * @author David
 */
@Named(value = "listarRoles")
@ConversationScoped
public class ListarRoles implements Serializable {

    @EJB
    private RolFacadeLocal rolFacadeLocal;
    @Inject
    private Conversation conversacion;
    private Rol rolSeleccionado;
    private List<Rol> listaRoles;

    public ListarRoles() {
        
    }
    
    @PostConstruct
    public void init(){
        listaRoles = rolFacadeLocal.findAll();
        
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public Rol getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Rol rol) {
        this.rolSeleccionado = rol;
    }
    
    public void iniciarConversacion(){
        if(conversacion.isTransient()){
            conversacion.begin();
            
        }
    
    }
    
    public void terminarConversacion(){
        if(!conversacion.isTransient()){
            conversacion.end();
        
        }
    
    }
    
    public String cancelar(){
        terminarConversacion();
        return "/admin/roles/listarRoles.xhtml?faces-redirect=true";
    
    }
    
    public String prepararEditar(Rol r){
        iniciarConversacion();
        rolSeleccionado = r;
        return "/admin/roles/editarRoles.xhtml?faces-redirect=true";
        
    }
    
    public void prepararEliminar(Rol r){
        iniciarConversacion();
        rolSeleccionado = r;
        System.out.printf("se eliminar: %s", rolSeleccionado.getNombreRol());
        
    }
    
    public void eliminarRol(){
        try {
            System.out.println("entro a eliminar");
            rolFacadeLocal.remove(rolSeleccionado);
            FacesMessage mjs = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rol eliminado correctamente", "");
            FacesContext.getCurrentInstance().addMessage(null, mjs);

        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage mjs = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error al eliminar el rol", "intentelo de nuevo");
            FacesContext.getCurrentInstance().addMessage(null, mjs);
            
        }
        
    }
    
    public String actualizar(){
        rolFacadeLocal.edit(rolSeleccionado);
        return  "/admin/roles/listarRoles.xhtml?faces-redirect=true";
    
    }
    
}