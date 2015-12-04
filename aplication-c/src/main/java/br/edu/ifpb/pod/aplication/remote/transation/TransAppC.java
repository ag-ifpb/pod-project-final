/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifpb.pod.aplication.remote.transation;

import br.edu.ifpb.pod.aplication.service.transation.Transation;
import br.edu.ifpb.pod.core.remote.contract.TransationApp;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class TransAppC extends UnicastRemoteObject implements TransationApp{

    private final Transation transation;
    
    public TransAppC(Transation transation) throws RemoteException{
        super();
        this.transation=transation;
    }
    
    

    @Override
    public void begin() throws RemoteException {
        transation.begin();
    }

    @Override
    public void commit() throws RemoteException {
        transation.commit();
    }

    @Override
    public void rollback() throws RemoteException {
        transation.rollback();
    }

}
