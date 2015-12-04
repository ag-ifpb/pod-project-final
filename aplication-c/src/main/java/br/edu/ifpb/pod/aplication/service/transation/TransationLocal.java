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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que contém os serviços necessários para executar transações na
 * manipulação das entidades que utilizam o banco Google Datastore
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class TransationLocal implements br.edu.ifpb.pod.aplication.service.transation.Transation {

    private DataServiceAdapter serviceAdapter;
    private boolean gerenciado;
    private DatastoreService service;

    public TransationLocal(DataServiceAdapter dataServiceAdapter) {
        try {
            this.serviceAdapter = dataServiceAdapter;
            this.gerenciado = false;
            System.setProperty("java.rmi.server.hostname", "localhost");
            Registry registry = LocateRegistry.getRegistry("200.129.71.228", 9090);
            this.service = (DatastoreService) registry.lookup("DatastoreService");
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(TransationLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void begin() {
        this.gerenciado = true;
        this.serviceAdapter.setToManager(new LinkedList<>());
    }

    @Override
    public void commit() {
        List<TeacherTO> to = serviceAdapter.getToManager();
        for (TeacherTO toManager : to) {
            if (this.gerenciado && toManager != null) {
                try {
                    service.createTeacher(ConvertTeacherTO.marshalling(toManager));
                } catch (RemoteException ex) {
                    Logger.getLogger(DataServiceAdapter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void rollback() {
        this.serviceAdapter.setToManager(new LinkedList<>());
        this.gerenciado = false;
    }
}
