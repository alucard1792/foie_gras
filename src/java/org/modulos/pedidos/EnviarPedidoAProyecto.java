/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.modulos.pedidos;

import com.controllerEmail.EnviarCorreosMasivos.controller;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.inject.Inject;
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
@ConversationScoped
public class EnviarPedidoAProyecto implements Serializable {

    @EJB
    private PedidoFacadeLocal pedidoFacadeLocal;
    @EJB
    private ProyectoFacadeLocal proyectoFacadeLocal;
    @Inject
    private Conversation conversacion;
    private List<Persona> listaOperariosDisponibles;
    private List<Proyecto> listaProyectos;
    private Proyecto proyecto;
    private Persona operarioAsignado;
    private Dificultad dificultad;
    private Estado estado;
    private Pedido pedidoSeleccionado;
    private controller c;

    public EnviarPedidoAProyecto() {
    }

    @PostConstruct
    public void init() {
        listaOperariosDisponibles = pedidoFacadeLocal.buscarOperarios();
        operarioAsignado = new Persona();
        proyecto = new Proyecto();
        dificultad = new Dificultad();
        estado = new Estado();
        c = new controller();
        
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

    public List<Proyecto> getListaProyectos() {
        return listaProyectos;
    }

    public void setListaProyectos(List<Proyecto> listaProyectos) {
        this.listaProyectos = listaProyectos;
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
        return "/admin/pedidos/listarPedidos.xhtml?faces-redirect=true";

    }

    public String preparacionAsignarOperario(Pedido p) {
        iniciarConversacion();
        pedidoSeleccionado = p;
        return "/admin/pedidos/asignarOperario.xhtml?faces-redirect=true";

    }

    public Pedido getPedidoSeleccionado() {
        return pedidoSeleccionado;
    }

    public void setPedidoSeleccionado(Pedido pedidoSeleccionado) {
        this.pedidoSeleccionado = pedidoSeleccionado;
    }

    public String asignarOperarioAProyecto() {

        System.out.println(pedidoSeleccionado.getIdPedido());
        try {
            String mensaje = "Estimado colaborador " + operarioAsignado.getNombre() + " " + operarioAsignado.getApellido() + "<br/><br/>Nos permitimos informarle que se ha asignadio un nuevo proyecto a su nombre.  por favor solicitamos inicializarlo lo mas pronto posible. gracias.<br/><br/>";
            mensaje += "resumen del proyecto: <br/><br/>" +
                    "id pedido = " + pedidoSeleccionado.getIdPedido() + "<br/>" +
                    "nombre pedido = " + pedidoSeleccionado.getNombreProyecto() + "<br/>" +
                    "descripcion pedido = " + pedidoSeleccionado.getNombreProyecto() + "<br/>" +
                    "cantidad pedido = " + pedidoSeleccionado.getCantidad() + "<br/>" +
                    "nombre cliente = " + pedidoSeleccionado.getNombreCliente() + "<br/>" +
                    "telefono cliente = " + pedidoSeleccionado.getTelefonoCliente() + "<br/>" +
                    "correo cliente = " + pedidoSeleccionado.getCorreoCliente() + "<br/>" +
                    "materia prima = " + pedidoSeleccionado.getMateriasPrimaIdMateria().getReferencia() + "<br/>";

            c.enviarEmailCliente(operarioAsignado.getEmail(), "Notificacion asignacion proyecto: " + pedidoSeleccionado.getNombreProyecto(), mensaje);
            dificultad.setIdDificultad(4);
            estado.setIdEstado(5);
            proyecto.setOperarioIdPersona(operarioAsignado);
            proyecto.setEstadosIdEstado(estado);
            proyecto.setDificultadesIdDificultad(dificultad);
            proyecto.setPedidosIdPedido(pedidoSeleccionado);
            proyecto.setOperarioIdPersona(operarioAsignado);
            proyectoFacadeLocal.create(proyecto);
            pedidoSeleccionado.setEstaAsignado(1);
            pedidoFacadeLocal.edit(pedidoSeleccionado);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return "/admin/pedidos/listarPedidos.xhtml?faces-redirect=true";

    }

}