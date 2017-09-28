/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.modulos.pedidos;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;
import org.dao.PedidoFacadeLocal;
import org.entidades.Pedido;
import org.entidades.Rol;
import org.login.ControladorSesion;

/**
 *
 * @author David
 */
@Named(value = "listarPedidos")
@ConversationScoped
public class ListarPedidos implements Serializable {
    @EJB
    private PedidoFacadeLocal pfl;
    @Inject
    private Conversation conversacion;
    @Inject
    private ControladorSesion controladorSesion;
    private Pedido pedidoSeleccionado;
    private List<Pedido> listaPedidos;

    public ListarPedidos() {

    }

    @PostConstruct
    public void init() {
        
        for(Rol rol:controladorSesion.getP().getRoles()){
            if (rol.getIdRol() == 1 || rol.getIdRol() == 2) {
                listaPedidos = pfl.findAll();
                
            }else if(rol.getIdRol() == 3 || rol.getIdRol() == 6){
                listaPedidos = pfl.listarPedidosVendedorAsignados(controladorSesion.getP());
            
            }
            
        }

    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public Pedido getPedidoSeleccionado() {
        return pedidoSeleccionado;
    }

    public void setPedidoSeleccionado(Pedido pedidoSeleccionado) {
        this.pedidoSeleccionado = pedidoSeleccionado;
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

    public String preparacionEditar(Pedido p) {
        iniciarConversacion();
        pedidoSeleccionado = p;
        return "/admin/pedidos/editarPedido.xhtml?faces-redirect=true";
        
    }

    public String actualizarPedido() {
        pfl.edit(pedidoSeleccionado);
        return cancelar();

    }

    public void preparacionEliminar(Pedido p) {

        pedidoSeleccionado = p;
        System.out.printf("se va a eliminar: %s", pedidoSeleccionado.getNombreProyecto());

    }

    public String eliminarPedido() {
        pfl.remove(pedidoSeleccionado);
        return "/admin/pedidos/listarPedidos.xhtml?faces-redirect=true";
        
    }
    
    public Pedido buscarIdPedido(Pedido p){
        return p;
    }

}
