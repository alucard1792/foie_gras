/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.modulos.stock;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.dao.MateriaPrimaFacadeLocal;
import org.dao.StockFacadeLocal;
import org.entidades.MateriaPrima;
import org.entidades.Stock;

/**
 *
 * @author David
 */
@Named(value = "crearStock")
@ViewScoped
public class CrearStock implements Serializable {

    @EJB
    private MateriaPrimaFacadeLocal materiaPrimaFacadeLocal;
    @EJB
    private StockFacadeLocal stockFacadeLocal;
    private List<MateriaPrima>listaMateriaPrima;
    
    private MateriaPrima materiaPrima;
    private Stock stockEntidad;


    @PostConstruct
    public void init() {
        listaMateriaPrima = materiaPrimaFacadeLocal.findAll();
        stockEntidad = new Stock();
        materiaPrima = new MateriaPrima();
        
    }

    public MateriaPrima getMateriaPrima() {
        return materiaPrima;
    }

    public void setMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    public Stock getStockEntidad() {
        return stockEntidad;
    }

    public void setStockEntidad(Stock stockEntidad) {
        this.stockEntidad = stockEntidad;
    }
    
    public List<MateriaPrima> getListaMateriaPrima() {
        return listaMateriaPrima;
    }

    public String crear() {
        try {
            stockFacadeLocal.create(stockEntidad);            
            return "/admin/stock/listarStock.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error al crear el stock, por favor contacte al admin", "");
            FacesContext.getCurrentInstance().addMessage(null, msj);
            return "";
            
        }
        
    }

    public String cancelar() {
        System.out.println(toString());
        return "/admin/stock/listarStock.xhtml?faces-redirect=true";

    }
    
}