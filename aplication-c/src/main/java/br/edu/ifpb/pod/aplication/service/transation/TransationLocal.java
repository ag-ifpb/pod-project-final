/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.aplication.service.transation;

import ag.ifpb.pod.rmi.core.DatastoreService;
import br.edu.ifpb.pod.aplication.service.DataServiceAdapter;
import br.edu.ifpb.pod.aplication.transation.convert.ConvertTeacherTO;
import br.edu.ifpb.pod.core.entity.TeacherTO;
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
public class TransationLocal implements br.edu.ifpb.pod.aplication.service.transation.Transation {

    private DataServiceAdapter serviceAdapter;
    private boolean gerenciado;

    public TransationLocal(DataServiceAdapter dataServiceAdapter) {
        this.serviceAdapter = dataServiceAdapter;
        this.gerenciado = false;
    }

    @Override
    public void begin() {
        this.gerenciado = true;
    }

    @Override
    public void commit() {
        TeacherTO toManager=serviceAdapter.getToManager();
        if (this.gerenciado && toManager != null) {
            try {
                System.setProperty("java.rmi.server.hostname", "localhost");
                Registry registry = LocateRegistry.getRegistry("200.129.71.228", 9090);
                DatastoreService service= (DatastoreService) registry.lookup("DatastoreService");
                service.createTeacher(ConvertTeacherTO.marshalling(toManager));
            } catch (RemoteException ex) {
                Logger.getLogger(DataServiceAdapter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotBoundException ex) {
                Logger.getLogger(TransationLocal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void rollback() {
        this.gerenciado = false;
    }
}
