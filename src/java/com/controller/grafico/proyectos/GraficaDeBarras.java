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
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

/**
 *
 * @author Orlando.R.R
 */
@Named(value = "graficaDeBarras")
@ViewScoped
public class GraficaDeBarras implements Serializable{

    /**
     * Creates a new instance of GraficaDeBarras
     */
    public GraficaDeBarras() {
    }
    
    @EJB
    private ProyectoFacadeLocal pfl;
    List<Proyecto> listaProyectosVendidos;
    List<Proyecto> listaProyectosdosmilquince;
    List<Proyecto> listaProyectosVendidosEnDosMilDiesiseis;
    List<Proyecto> listaProyectosVendidosEnDosMilDiesisiete;
    
     private BarChartModel barModel;
   
 
    @PostConstruct
    public void init() {
        listaProyectosVendidos = pfl.cantidaddeproyectosvendidos();
        listaProyectosdosmilquince = pfl.cantidaddeproyectosvendidosenaños();
        listaProyectosVendidosEnDosMilDiesiseis = pfl.cantidadDeproyectosVendidosEnDosMilDiesiseis();
        listaProyectosVendidosEnDosMilDiesisiete = pfl.cantidadDeproyectosVendidosEnDosMilDiesisiete();
        createBarModels();
    }
 
    public BarChartModel getBarModel() {
        return barModel;
    }
     
 
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries dosmilcatorce = new ChartSeries();
        dosmilcatorce.setLabel("2014");
        dosmilcatorce.set("", listaProyectosVendidos.size());
        
        
 
        ChartSeries dosmilquince = new ChartSeries();
        dosmilquince.setLabel("2015");
        dosmilquince.set("2015", listaProyectosdosmilquince.size());
        
        ChartSeries dosmildiesiseis = new ChartSeries();
        dosmildiesiseis.setLabel("2016");
        dosmildiesiseis.set("2016", listaProyectosVendidosEnDosMilDiesiseis.size());
        
        ChartSeries dosmildiesisiete = new ChartSeries();
        dosmildiesisiete.setLabel("2017");
        dosmildiesisiete.set("2017", listaProyectosVendidosEnDosMilDiesisiete.size());
        
 
        model.addSeries(dosmilcatorce);
        model.addSeries(dosmilquince);
        model.addSeries(dosmildiesiseis);
        model.addSeries(dosmildiesisiete);
         
        return model;
    }
     
    private void createBarModels() {
        createBarModel();
    }
     
    private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("Proyectos vendidos en los años transcurridos");
        barModel.setLegendPosition("ne");
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("AÑOS");
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("CANTIDAD");
        yAxis.setMin(0);
        yAxis.setMax(10);
    }
     
   
    
}
