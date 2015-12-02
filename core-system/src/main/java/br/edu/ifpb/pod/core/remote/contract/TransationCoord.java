package br.edu.ifpb.pod.core.remote.contract;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TransationCoord extends Remote {

    public void beginAll() throws RemoteException;

    public void commitAll() throws RemoteException;

    public void rollbackAll() throws RemoteException;
}