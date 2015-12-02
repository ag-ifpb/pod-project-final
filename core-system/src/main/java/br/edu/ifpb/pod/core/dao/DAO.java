/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifpb.pod.core.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class DAO <K,T>{

    private EntityManager em;

    public DAO(EntityManager em) {
        this.em = em;
    }
    
    public void persist(T t){
        em.persist(t);
    }
    
    public void update(T t){
        em.merge(t);
    }
    
    public T findOne(Class<T> clazz,K key){
        return em.find(clazz, key);
    }
    
    public List<T> findAll(Class<T> t){
        TypedQuery<T> query=em.createQuery("SELECT t FROM "+t.getSimpleName()+" t",t);
        return query.getResultList();
    }
}
