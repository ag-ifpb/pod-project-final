/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.core.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 * Classe que acessa os dados dos bancos de dados relacional das aplicações que
 * utilização da especificação JPA (Java Persistence API)
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class DAO<K, T> {

    private EntityManager em;

    public DAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Salva uma entidade no banco de dados
     *
     * @param t
     */
    public void persist(T t) {
        em.persist(t);
    }

    /**
     * Atualiza uma entidade no banco de dados
     *
     * @param t
     */
    public void update(T t) {
        em.merge(t);
    }

    /**
     * Retorna uma entidade de acordo com a chave primária da entidade
     *
     * @param clazz
     * @param key
     * @return
     */
    public T findOne(Class<T> clazz, K key) {
        return em.find(clazz, key);
    }

    /**
     * Retorn todas as entidades do banco de dados
     * @param t
     * @return 
     */
    public List<T> findAll(Class<T> t) {
        TypedQuery<T> query = em.createQuery("SELECT t FROM " + t.getSimpleName() + " t", t);
        query.setHint(QueryHints.REFRESH, HintValues.TRUE);
        return query.getResultList();
    }
}
