package br.edu.ifpb.pod.core.remote.contract;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface remota que define os serviço de transação das entidades de todos os
 * bancos de dados
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public interface TransationCoord extends Remote {

    public void beginAll() throws RemoteException;

    public void commitAll() throws RemoteException;

    public void rollbackAll() throws RemoteException;
}
