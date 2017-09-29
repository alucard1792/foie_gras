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
    private List<MateriaPrima>listaMateriaPrima;
    private Pedido pedido;
    private Stock stockMateriaPrima;
    private MateriaPrima materiaPrima;

    public CrearPedido() {
    }
    
    @PostConstruct
    public void init(){
        listaMateriaPrima = materiaPrimaFacadeLocal.listarPorStockMayor0();
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
        try {
            System.out.println(stockMateriaPrima);
            materiaPrima = materiaPrimaFacadeLocal.find(pedido.getMateriasPrimaIdMateria().getIdMateria());
            System.out.println(materiaPrima.getIdMateria());
            for(Stock stock:materiaPrima.getStockList()){
                System.out.println(stock.getStock());
            
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*try {
            pedido.setIdPedido(null);
            pedido.setVendedorIdPersona(controladorSesion.getP());
            pedidoFacadeLocal.create(pedido);
            return "/admin/pedidos/listarPedidos.xhtml?faces-redirect=true";
            
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error al crear el pedido, por favor contacte al admin", "");
            FacesContext.getCurrentInstance().addMessage(null, msj);
            return "";
            
        }
        */
            return "";
    }
    
    public String cancelar() {
        return "/admin/pedidos/listarPedidos.xhtml?faces-redirect=true";

    }

}
