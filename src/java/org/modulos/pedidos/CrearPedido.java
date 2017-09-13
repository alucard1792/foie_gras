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
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.dao.MateriaPrimaFacadeLocal;
import org.dao.PedidoFacadeLocal;
import org.entidades.MateriaPrima;
import org.entidades.Pedido;
import org.login.ControladorSesion;

/**
 *
 * @author David
 */
@Named(value = "crearPedido")
@ViewScoped
public class CrearPedido implements Serializable {


    @EJB
    private PedidoFacadeLocal pedidoFacadeLocal;
    @EJB
    private MateriaPrimaFacadeLocal materiaPrimaFacadeLocal;
    @Inject
    private ControladorSesion controladorSesion;
    private List<MateriaPrima>listaMateriaPrima;
    private Pedido pedido;

    public CrearPedido() {
    }
    
    @PostConstruct
    public void init(){
        listaMateriaPrima = materiaPrimaFacadeLocal.findAll();
        pedido = new Pedido();

    }

    public ControladorSesion getControladorSesion() {
        return controladorSesion;
    }

    public void setControladorSesion(ControladorSesion controladorSesion) {
        this.controladorSesion = controladorSesion;
    }

    public List<MateriaPrima> getListaMateriaPrima() {
        return listaMateriaPrima;
    }

    public void setListaMateriaPrima(List<MateriaPrima> listaMateriaPrima) {
        this.listaMateriaPrima = listaMateriaPrima;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    public String crear() {
        pedido.setIdPedido(null);
        pedido.setVendedorIdPersona(controladorSesion.getP());
        pedidoFacadeLocal.create(pedido);
        return "/admin/pedidos/listarPedidos.xhtml?faces-redirect=true";
        
    }
    
    public String cancelar() {
        return "/admin/pedidos/listarPedidos.xhtml?faces-redirect=true";

    }

}
