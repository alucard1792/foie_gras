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
    private PieChartModel piemodel;
    private Pedido pedido;
    List<Pedido> listapedido;
    
    List<Proyecto> pagos;

    

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
        
       
        Reporte(listapedido);
    }
    
    
    public void Reporte(List<Pedido> listapedido){
        
        piemodel = new PieChartModel();
        
        piemodel.set("PAGOS", listapedido.size());
        
        piemodel.setTitle("Fecha entrega de proyectos");
        piemodel.setLegendPosition("e");
        piemodel.setFill(false);
        piemodel.setShowDataLabels(true);
        piemodel.setDiameter(150);
    }
   
    
    
}
