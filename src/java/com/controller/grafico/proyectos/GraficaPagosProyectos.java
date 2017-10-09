/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.grafico.proyectos;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.dao.PedidoFacadeLocal;
import org.dao.ProyectoFacadeLocal;
import org.entidades.Pedido;
import org.entidades.Proyecto;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Orlando.R.R
 */
@Named(value = "graficaPagosProyectos")
@ViewScoped
public class GraficaPagosProyectos implements Serializable{

    /**
     * Creates a new instance of GraficaPagosProyectos
     */
    public GraficaPagosProyectos() {
    }
    @EJB 
    private PedidoFacadeLocal pfl;
    @EJB
    private ProyectoFacadeLocal proyectofacadelocal;
    private PieChartModel piemodel;
    private Pedido pedido;
    List<Pedido> listapedido;
    List<Pedido> listapedidopago;
    List<Proyecto> listaproyectosvendidos;

    public PedidoFacadeLocal getPfl() {
        return pfl;
    }

    public void setPfl(PedidoFacadeLocal pfl) {
        this.pfl = pfl;
    }

    public List<Pedido> getListapedido() {
        return listapedido;
    }

    public void setListapedido(List<Pedido> listapedido) {
        this.listapedido = listapedido;
    }

    public List<Pedido> getListapedidopago() {
        return listapedidopago;
    }

    public void setListapedidopago(List<Pedido> listapedidopago) {
        this.listapedidopago = listapedidopago;
    }
    
    

    

    public PieChartModel getPiemodel() {
        return piemodel;
    }

    public void setPiemodel(PieChartModel piemodel) {
        this.piemodel = piemodel;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    
    @PostConstruct
    public void init(){
        listapedido = pfl.pedidosSinPagar(0);
        listapedidopago = pfl.pedidosSinPagar(1);
        
        
       
        Reporte(listapedido, listapedidopago);
    }
    
    
    public void Reporte(List<Pedido> listapedido, List<Pedido> listapedidopago){
        
        piemodel = new PieChartModel();
        
        piemodel.set("Sin pagar", listapedido.size());
        piemodel.set("Pagos", listapedidopago.size());
        
        piemodel.setTitle("Pedidos pagos y sin pagar");
        piemodel.setLegendPosition("e");
        piemodel.setFill(false);
        piemodel.setShowDataLabels(true);
        piemodel.setDiameter(150);
    }
   
    
    
}
