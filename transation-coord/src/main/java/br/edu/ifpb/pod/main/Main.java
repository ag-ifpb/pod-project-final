/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifpb.pod.main;

import br.edu.ifpb.pod.core.remote.contract.TransationApp;
import br.edu.ifpb.pod.transation.coord.TransationCoord;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class Main {
    
    private static final String TRANS_COORD="TransCoord";
    private static final String TRANS_APP_A="TransAppA";
    private static final String TRANS_APP_B="TransAppB";
    private static final String TRANS_APP_C="TransAppC";
    
    private static TransationApp getTransationAppA() throws RemoteException, NotBoundException{
        Registry registry=LocateRegistry.getRegistry(9000);
        return  (TransationApp) registry.lookup(TRANS_APP_A);
    }
    
    private static TransationApp getTransationAppB() throws RemoteException, NotBoundException{
        Registry registry=LocateRegistry.getRegistry(9001);
        return (TransationApp) registry.lookup(TRANS_APP_B);
    }
    
    private static TransationApp getTransationAppC() throws RemoteException, NotBoundException{
        Registry registry=LocateRegistry.getRegistry(9002);
        return (TransationApp) registry.lookup(TRANS_APP_C);
    }
    
    public static void main(String[] args) {
        try {
            Registry registry=LocateRegistry.createRegistry(2010);
            registry.bind(TRANS_COORD, new TransationCoord(getTransationAppA(),getTransationAppB(),getTransationAppC()));
        } catch (RemoteException | AlreadyBoundException | NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
