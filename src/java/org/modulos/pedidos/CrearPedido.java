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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.dao.MateriaPrimaFacadeLocal;
import org.dao.PedidoFacadeLocal;
import org.dao.StockFacadeLocal;
import org.entidades.MateriaPrima;
import org.entidades.Pedido;
import org.entidades.Stock;
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
    @EJB
    private StockFacadeLocal stockFacadeLocal;
    @Inject
    private ControladorSesion controladorSesion;
    private List<MateriaPrima> listaMateriaPrima;
    private Pedido pedido;
    private Stock stockMateriaPrima;
    private MateriaPrima materiaPrima;
    private controller c;

    public CrearPedido() {
    }

    @PostConstruct
    public void init() {
        listaMateriaPrima = materiaPrimaFacadeLocal.listarPorStockMayor0();
        pedido = new Pedido();
        c = new controller();

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
        try {
            String mensaje = "";
            if(pedido.getRealizoPago() == 1){
                mensaje += "Señor(a) " + pedido.getNombreCliente() + "<br/><br/>Nos permitimos informarle que su pedido se ha ingresado exitosamente al sistema, dentro de poco se le enviara un email notificando el inicio de su pedido.<br/><br/>gracias";
           
            }else{
                mensaje = "Señor(a) " + pedido.getNombreCliente() + "<br/><br/>Nos permitimos informarle que su pedido se ha ingresado exitosamente al sistema. Le recordamos realizar el pago de su pedido para que pueda iniciar la produccion de su pedido.<br/><br/>gracias.";

            }
            c.enviarEmailCliente(pedido.getCorreoCliente(), "Notificacion ingreso pedido: " + pedido.getNombreProyecto(), mensaje);
            materiaPrima = materiaPrimaFacadeLocal.find(pedido.getMateriasPrimaIdMateria().getIdMateria());
            pedido.setMateriasPrimaIdMateria(materiaPrima);
            pedido.setVendedorIdPersona(controladorSesion.getP());
            pedidoFacadeLocal.create(pedido);
            return "/admin/pedidos/listarPedidos.xhtml?faces-redirect=true";

        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error al crear el pedido, por favor contacte al admin", "");
            FacesContext.getCurrentInstance().addMessage(null, msj);
            return "";
        }

    }

    public String cancelar() {
        return "/admin/pedidos/listarPedidos.xhtml?faces-redirect=true";

    }

}
