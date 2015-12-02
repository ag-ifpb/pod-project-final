/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifpb.pod.aplication.transation;

import br.edu.ifpb.pod.core.remote.contract.TransationApp;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class TransationAppA extends UnicastRemoteObject implements TransationApp{
    
    private final EntityTransaction transaction;

    public TransationAppA(EntityTransaction transaction) throws RemoteException{
        this.transaction=transaction;
    }
    
    

    @Override
    public void begin() throws RemoteException {
        transaction.begin();
    }

    @Override
    public void commit() throws RemoteException {
        transaction.commit();
    }

    @Override
    public void rollback() throws RemoteException {
        transaction.rollback();
    }

}
