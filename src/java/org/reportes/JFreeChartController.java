/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reportes;

import org.reportes.GraficoRule;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.annotation.PostConstruct;
/**
 *
 * @author Ismael
 */
@Named(value = "jFreeChartController")
@RequestScoped
public class JFreeChartController {

    @EJB
    private GraficoRule gr;
    /**
     * Creates a new instance of JFreeChartController
     */
    public JFreeChartController() {
    }
    

    @PostConstruct
    public void init(){
        generarGrafico();
    }
    public String getGrafico() {
        return gr.getGraficoPruebaDato();
    }
    public void generarGrafico() {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            DefaultPieDataset dataset = new DefaultPieDataset();
            dataset.setValue("Item1", 10);
            dataset.setValue("Item2", 15);
            dataset.setValue("Item3", 8);
            dataset.setValue("Item4", 12.5);
            dataset.setValue("Item5", 30);
            dataset.setValue("Item5", 60);
            JFreeChart chart = ChartFactory.createPieChart("Sales", dataset, true, true, Locale.ENGLISH);
            File f = new File(fc.getExternalContext().getRealPath("/") + "img.png");
            ChartUtilities.saveChartAsPNG(f, chart, 300, 300);
        } catch (IOException ex) {
            Logger.getLogger(JFreeChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
