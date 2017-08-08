/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.modulos.roles;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.dao.RolFacadeLocal;
import org.entidades.Rol;

/**
 *
 * @author David
 */
@Named(value = "crearRol")
@ViewScoped
public class CrearRol implements Serializable{

    @EJB
    private RolFacadeLocal rolFacadeLocal;
    private Rol rol;
    private String nombreRol;
    private int estado;

    public CrearRol() {
        
    }
    
    @PostConstruct
    public void init(){
        
        
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public String crear(){
        rol = new Rol(null, nombreRol, estado);
        rolFacadeLocal.create(rol);
        return "listarRoles.xhtml?faces-redirect=true";
        
    }
    
    public String cancelar(){
        return "/admin/roles/editarRoles.xhtml?faces-redirect=true";
        
    }
       
}