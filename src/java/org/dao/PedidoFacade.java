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
import org.entidades.Pedido;
import org.entidades.Persona;
import org.entidades.Rol;

/**
 *
 * @author David
 */
@Stateless
public class PedidoFacade extends AbstractFacade<Pedido> implements PedidoFacadeLocal {

    @PersistenceContext(unitName = "fixed_up_prototipo_v2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedidoFacade() {
        super(Pedido.class);
    }

    @Override
    public List<Persona> buscarOperarios() {
        List<Persona> operariosDisponibles = new ArrayList<>();
        Rol rol = new Rol(4);
        try {
            TypedQuery<Persona> q = getEntityManager().createNamedQuery("Persona.findOperarios", Persona.class);
            q.setParameter("rol", rol);
            operariosDisponibles = q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return operariosDisponibles;

    }

}
