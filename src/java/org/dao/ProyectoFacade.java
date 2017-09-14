/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dao;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.entidades.Persona;
import org.entidades.Estado;
import org.entidades.Proyecto;

/**
 *
 * @author David
 */
@Stateless
public class ProyectoFacade extends AbstractFacade<Proyecto> implements ProyectoFacadeLocal {

    @PersistenceContext(unitName = "fixed_up_prototipo_v2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProyectoFacade() {
        super(Proyecto.class);
    }

    @Override
    public List<Proyecto> listarProyectosOperariosAsignados(Persona p) {
        List<Proyecto>listaProyectos = new ArrayList<>();
        Persona persona =  new Persona();
        persona.setIdPersona(p.getIdPersona());
        
        try {
            TypedQuery<Proyecto> q = getEntityManager().createNamedQuery("Proyecto.findByOperarioAsignado", Proyecto.class);
            q.setParameter("operarioIdPersona", persona);
            listaProyectos = q.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        
        return listaProyectos;
        
    }
    public List<Proyecto> buscarProyectosTerminados() {
        
        List<Proyecto> proyectosTerminados = new ArrayList<>();
        
        Estado estado = new Estado(3);
        
        try {
            TypedQuery<Proyecto> q = getEntityManager().createNamedQuery("proyectosTerminados", Proyecto.class);
            q.setParameter("estado", estado);
            
            proyectosTerminados = q.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proyectosTerminados;
    }

   
    
}
