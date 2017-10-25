/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.modulos.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.dao.PermisoFacadeLocal;
import org.dao.RolFacadeLocal;
import org.entidades.Permiso;
import org.entidades.Rol;

/**
 *
 * @author David
 */
@Named(value = "crearRol")
@ViewScoped
public class CrearRol implements Serializable {

    @EJB
    private RolFacadeLocal rolFacadeLocal;
    @EJB
    private PermisoFacadeLocal permisoFacadeLocal;
    private List<Permiso> listaPermisos;
    private Rol rol;
    private Permiso permiso;
    private String nombreRol;
    private int estado;
    private  Rol  rol2;
    public CrearRol() {

    } 

    @PostConstruct
    public void init() {
        rol = new Rol();
       rol2 = new Rol();
        permiso = new Permiso();
        listaPermisos = permisoFacadeLocal.findAll();
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Rol getRol2() {
        return rol2;
    }

    public void setRol2(Rol rol2) {
        this.rol2 = rol2;
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

    public List<Permiso> getListaPermisos() {
        return listaPermisos;
    }

    public void setListaPermisos(List<Permiso> listaPermisos) {
        this.listaPermisos = listaPermisos;
    }
 public void crearRolp() {
     rol2.setEstado(1);
     rol2.setNombreRol("prueba");
    Permiso p1= permisoFacadeLocal.find(1);
    Permiso p2= permisoFacadeLocal.find(2);
     List<Permiso> permisos2= new ArrayList<>();
             permisos2.add(p2);
             permisos2.add(p1);
     rol2.setPermisos(permisos2);
 rolFacadeLocal.create(rol2);
 
 
 }
    public String crear() {
        try {
            rolFacadeLocal.create(rol);
            return "/admin/roles/listarRoles.xhtml?faces-redirect=true";

        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error al crear el rol, por favor contacte al admin", "");
            FacesContext.getCurrentInstance().addMessage(null, msj);
            return "";

        }

    }

    public String cancelar() {
        return "/admin/roles/listarRoles.xhtml?faces-redirect=true";

    }

}
