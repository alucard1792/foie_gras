/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.modulos.pedidos;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.dao.PedidoFacadeLocal;
import org.dao.ProyectoFacadeLocal;
import org.entidades.Dificultad;
import org.entidades.Estado;
import org.entidades.Pedido;
import org.entidades.Persona;
import org.entidades.Proyecto;

/**
 *
 * @author David
 */
@Named(value = "enviarPedidoAProyecto")
@SessionScoped
public class EnviarPedidoAProyecto implements Serializable {

    @EJB
    private PedidoFacadeLocal pedidoFacadeLocal;
    @EJB
    private ProyectoFacadeLocal proyectoFacadeLocal;
    private List<Persona> listaOperariosDisponibles;
    private Proyecto proyecto;
    private Persona operarioAsignado;
    private Dificultad dificultad;
    private Estado estado;

    public EnviarPedidoAProyecto() {
    }

    @PostConstruct
    public void init() {
        listaOperariosDisponibles = pedidoFacadeLocal.buscarOperarios();
        operarioAsignado = new Persona();
        proyecto = new Proyecto();
        dificultad = new Dificultad();
        estado = new Estado();

    }

    public List<Persona> getListaOperariosDisponibles() {
        return listaOperariosDisponibles;
    }

    public Persona getOperarioAsignado() {
        return operarioAsignado;
    }

    public void setOperarioAsignado(Persona operarioAsignado) {
        this.operarioAsignado = operarioAsignado;
    }

    public void asignarOperarioAProyecto(Pedido pedido) {

       try {
            dificultad.setIdDificultad(4);
            estado.setIdEstado(5);
            proyecto.setOperarioIdPersona(operarioAsignado);
            proyecto.setEstadosIdEstado(estado);
            proyecto.setDificultadesIdDificultad(dificultad);
            proyecto.setPedidosIdPedido(pedido);
            proyecto.setOperarioIdPersona(operarioAsignado);
            proyectoFacadeLocal.create(proyecto);
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        
    }

}
