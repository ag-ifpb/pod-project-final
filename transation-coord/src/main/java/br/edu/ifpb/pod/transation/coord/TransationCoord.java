/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.transation.coord;

import br.edu.ifpb.pod.core.remote.contract.TransationApp;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Classe que coordena todas as transações de todos os bancos de dados da aplicação
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class TransationCoord extends UnicastRemoteObject implements br.edu.ifpb.pod.core.remote.contract.TransationCoord {

    private final TransationApp[] transApp;

    public TransationCoord(TransationApp... transApp) throws RemoteException {
        super();
        this.transApp = transApp;
    }

    @Override
    public void beginAll() throws RemoteException {
        for (TransationApp transApp1 : transApp) {
            transApp1.begin();
        }
    }

    @Override
    public void commitAll() throws RemoteException {
        for (TransationApp transApp1 : transApp) {
            transApp1.commit();
        }
    }

    @Override
    public void rollbackAll() throws RemoteException {
        for (TransationApp transApp1 : transApp) {
            transApp1.rollback();
        }
    }

}
