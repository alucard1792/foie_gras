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
@Named(value = "graficaProyectos")
@ViewScoped
public class GraficaProyectos implements Serializable{

    
    public GraficaProyectos() {
    }
    
    @EJB
    private ProyectoFacadeLocal pfl;
    List<Proyecto> ProyectosTerminados;
    List<Proyecto> ProyectosIniciados;
    List<Proyecto> ProyectosPausados;
    List<Proyecto> ProyectosSinComenzar;
    private PieChartModel piemodel;
    private Proyecto proyecto;

    public ProyectoFacadeLocal getPfl() {
        return pfl;
    }

    public void setPfl(ProyectoFacadeLocal pfl) {
        this.pfl = pfl;
    }

    public List<Proyecto> getProyectosTerminados() {
        return ProyectosTerminados;
    }

    public void setProyectosTerminados(List<Proyecto> ProyectosTerminados) {
        this.ProyectosTerminados = ProyectosTerminados;
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
    
    
    @PostConstruct
    public void init(){
        ProyectosTerminados = pfl.buscarProyectosTerminados();
        ProyectosIniciados = pfl.buscarProyectosIniciados();
        ProyectosPausados = pfl.buscarProyectosPausados();
        ProyectosSinComenzar = pfl.buscarProyectosSinComenzar();
        
        reporte(ProyectosTerminados, ProyectosIniciados, ProyectosPausados, ProyectosSinComenzar);
        
    }
    
    
    public void reporte(List<Proyecto> ProyectosTerminados, List<Proyecto> ProyectosIniciados, List<Proyecto> ProyectosPausados, List<Proyecto> ProyectosSinComenzar){
        
        piemodel = new PieChartModel();
        
       
            piemodel.set("Proyectos terminados", ProyectosTerminados.size());
            piemodel.set("Proyectos Iniciados", ProyectosIniciados.size());
            piemodel.set("Proyectos Pausados", ProyectosPausados.size());
            piemodel.set("Proyectos sin comenzar", ProyectosSinComenzar.size());
        
        
        
        piemodel.setTitle("Proyectos");
        piemodel.setLegendPosition("e");
        piemodel.setFill(false);
        piemodel.setShowDataLabels(true);
        piemodel.setDiameter(150);
    }
}
