package br.edu.ifpb.pod.core.remote.contract;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TransationApp extends Remote {

    public void begin() throws RemoteException;

    public void commit() throws RemoteException;

    public void rollback() throws RemoteException;
}
