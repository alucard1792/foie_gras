/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.grafico.proyectos;

import java.io.Serializable;
import java.util.List;
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
    
    
    
    public void init(){
        ProyectosTerminados = pfl.buscarProyectosTerminados();
        
        reporte(ProyectosTerminados);
    }
    
    
    public void reporte(List<Proyecto> ProyectosTerminados){
        
        piemodel = new PieChartModel();
        
        for(Proyecto p : ProyectosTerminados){
            piemodel.set("Proyectos terminados", ProyectosTerminados.size());
        }
        
        
        piemodel.setTitle("Proyectos");
        piemodel.setLegendPosition("e");
        piemodel.setFill(false);
        piemodel.setShowDataLabels(true);
        piemodel.setDiameter(150);
    }
}
