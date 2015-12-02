/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.sync.coord.timer;

import ag.ifpb.pod.rmi.core.DatastoreService;
import br.edu.ifpb.pod.core.remote.contract.TransationCoord;
import br.edu.ifpb.pod.sync.coord.Synchronizer;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class SyncSchedule {

    private final Timer timer;

    public SyncSchedule(int seconds) {
        this.timer = new Timer();
        timer.schedule(new SyncTimer(), seconds * 1000);
    }

    private class SyncTimer extends TimerTask {
        
        private DatastoreService serviceA;
        private DatastoreService serviceB;
        private DatastoreService serviceC;
        private TransationCoord transationCoord;
        private Synchronizer sync;

        public SyncTimer() {
            try {
                this.serviceA=getService("DataServiceA", 10000);
                this.serviceB=getService("DataServiceB", 10001);
                this.serviceC=getService("DataServiceC", 10002);
                this.transationCoord=getTransation("TransCoord", 9003);
                this.sync=new Synchronizer(serviceA, serviceB, serviceC, transationCoord);
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(SyncSchedule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private DatastoreService getService(String servico, int port) throws RemoteException, NotBoundException {
            Registry registry = LocateRegistry.createRegistry(port);
            return (DatastoreService) registry.lookup(servico);
        }
        
        private TransationCoord getTransation(String servico, int port) throws RemoteException, NotBoundException{
             Registry registry = LocateRegistry.createRegistry(port);
            return (TransationCoord) registry.lookup(servico);
        }

        @Override
        public void run() {
            this.sync.sync();
            System.out.println("Olá tudo bem");
        }

    }
}
