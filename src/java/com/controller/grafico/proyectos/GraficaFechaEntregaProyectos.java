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
import org.dao.ProyectoFacadeLocal;
import org.entidades.Proyecto;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Orlando.R.R
 */
@Named(value = "graficaFechaEntregaProyectos")
@ViewScoped
public class GraficaFechaEntregaProyectos implements Serializable{

    @EJB 
    private ProyectoFacadeLocal pfl;
    private PieChartModel piemodel;
    private Proyecto proyecto;
    
    List<Proyecto> fechaEntrega;

    public ProyectoFacadeLocal getPfl() {
        return pfl;
    }

    public void setPfl(ProyectoFacadeLocal pfl) {
        this.pfl = pfl;
    }

    public PieChartModel getPiemodel() {
        return piemodel;
    }

    public void setPiemodel(PieChartModel piemodel) {
        this.piemodel = piemodel;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public List<Proyecto> getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(List<Proyecto> fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public GraficaFechaEntregaProyectos() {
    }

   
    public void init(){
        fechaEntrega = pfl.findByfechaFinalizado();
        
        Reporte(fechaEntrega);
    }
    
    
    public void Reporte(List<Proyecto> fechaEntrega){
        
        piemodel = new PieChartModel();
        
        piemodel.set("Fechas de entrega", fechaEntrega.size());
        
        piemodel.setTitle("Fecha entrega de proyectos");
        piemodel.setLegendPosition("e");
        piemodel.setFill(false);
        piemodel.setShowDataLabels(true);
        piemodel.setDiameter(150);
    }
    
}
