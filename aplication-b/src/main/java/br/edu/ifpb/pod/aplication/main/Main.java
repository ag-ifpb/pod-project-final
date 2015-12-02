/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifpb.pod.aplication.main;

import br.edu.ifpb.pod.aplication.service.DataService;
import br.edu.ifpb.pod.aplication.transation.TransationAppB;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class Main {
    
    private static final String PERSISTENCE_UNIT="br.edu.ifpb.pod_aplication-b_jar_1.0-SNAPSHOTPU";
    private static final String TRANS_APP_B="TransAppB"; 
    private static final String DATA_SERVICE_B="DataServiceB";
    
    
    public static void main(String[] args) {
        try {
            EntityManager em=Persistence.createEntityManagerFactory(PERSISTENCE_UNIT).createEntityManager();
            
            Registry registry=LocateRegistry.createRegistry(9001);
            registry.bind(TRANS_APP_B, new TransationAppB(em.getTransaction()));
            registry=LocateRegistry.createRegistry(10001);
            registry.bind(DATA_SERVICE_B, new DataService(em));
            
            System.out.println("Servidor B iniciado");
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
