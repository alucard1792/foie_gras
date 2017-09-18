/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.modulos.materia_prima;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.dao.MateriaPrimaFacadeLocal;
import org.dao.ProveedorFacadeLocal;
import org.dao.StockFacadeLocal;
import org.entidades.MateriaPrima;
import org.entidades.Proveedor;
import org.entidades.Stock;

/**
 *
 * @author David
 */
@Named(value = "crearMateriaPrima")
@ViewScoped
public class CrearMateriaPrima implements Serializable {

    @EJB
    private MateriaPrimaFacadeLocal materiaPrimaFacadeLocal;
    @EJB
    private ProveedorFacadeLocal proveedorFacadeLocal;
    @EJB
    private StockFacadeLocal stockFacadeLocal;
    private MateriaPrima materiaPrima;
    private Stock stock;
    private Proveedor proveedor;
    private List<Stock>listaStock;
    private List<Proveedor>listaProveedores;

    public CrearMateriaPrima() {

    }

    @PostConstruct
    public void init() {
        listaProveedores = proveedorFacadeLocal.findAll();
        //listaStock = stockFacadeLocal.findAll();
        proveedor = new Proveedor();
        stock = new Stock();
        materiaPrima = new MateriaPrima();
    }

    public List<Stock> getListaStock() {
        return listaStock;
    }

    public List<Proveedor> getListaProveedores() {
        return listaProveedores;
    }

    public MateriaPrima getMateriaPrima() {
        return materiaPrima;
    }

    public void setListaStock(List<Stock> listaStock) {
        this.listaStock = listaStock;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }
    
    
    public void setMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    
    public String crear() {
        try {
            List<Proveedor>listaProveedorTemp = new ArrayList<>();
            List<Stock>listaStockTemp = new ArrayList<>();
            
            Calendar cal = Calendar.getInstance();
            stock.setFechaIngreso(cal.getTime());
            listaStockTemp.add(stock);
            materiaPrima.setStockList(listaStockTemp);
            listaProveedorTemp.add(proveedorFacadeLocal.find(proveedor.getIdProveedor()));
            materiaPrima.setProveedorList(listaProveedorTemp);
            
            materiaPrimaFacadeLocal.create(materiaPrima);
            return "/admin/materiaPrima/listarMateriaPrima.xhtml?faces-redirect=true";

        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msj = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error al crear la materia prima, por favor contacte al admin", "");
            FacesContext.getCurrentInstance().addMessage(null, msj);
            return "";

        }

    }

    public String cancelar() {
        return "/admin/materiaPrima/listarMateriaPrima.xhtml?faces-redirect=true";

    }

}