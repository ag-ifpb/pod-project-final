/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifpb.pod.aplication.service.transation;

/**
 * Interface que possui a definição dos serviços necessarios para uma
 * Transação
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public interface Transation {

    /**
     * Define o serviço que inicia uma transação
     */
    public void begin();

    /**
     * Define o serviço que faz o <i>commit</i> uma transação
     */
    public void commit();

    /**
     * Define o serviço que faz o <i>rollback</i> uma transação
     */
    public void rollback();
}
