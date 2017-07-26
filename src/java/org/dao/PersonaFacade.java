/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.entidades.Persona;
import javax.persistence.TypedQuery;

/**
 *
 * @author David
 */
@Stateless
public class PersonaFacade extends AbstractFacade<Persona> implements PersonaFacadeLocal {

    @PersistenceContext(unitName = "fixed_up_prototipo_v2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaFacade() {
        super(Persona.class);
    }

    public Persona iniciarSesion(int documento, String password) {
        Persona u = null;
        try {
            TypedQuery<Persona> q = getEntityManager().createNamedQuery("Persona.login", Persona.class);
            q.setParameter("documento", documento);
            q.setParameter("password", password);
            u = q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return u;
    }

    @Override
    public List<Persona> filtroMultiCriterio(Map<String, Object> usuariosFiltro) {
        List<Persona> Persona = new ArrayList<>();
        String sql = "SELECT u FROM Persona u WHERE ";
        try {
            Iterator<String> it = usuariosFiltro.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                sql += key + " =: " + key + (it.hasNext() ? " AND " : "");
            }
            TypedQuery<Persona> q = (TypedQuery<Persona>) getEntityManager().createQuery(sql);
            it = usuariosFiltro.keySet().iterator();

            while (it.hasNext()) {
                String key = it.next();
                sql += key + " =: " + key + (it.hasNext() ? " AND " : "");

            }

            Persona = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return Persona;

    }

}
