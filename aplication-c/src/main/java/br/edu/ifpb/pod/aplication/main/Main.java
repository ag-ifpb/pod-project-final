/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.aplication.main;

import br.edu.ifpb.pod.aplication.service.DataServiceAdapter;
import br.edu.ifpb.pod.aplication.remote.transation.TransAppC;
import br.edu.ifpb.pod.aplication.service.transation.TransationLocal;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Disponibiliza os serviços distribuidos de manipulação de entidades e
 * transação do banco de dados da aplicação que utiliza o banco C (Google Datastore)
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class Main {

    private static final String TRANS_APP_C = "TransAppC";
    private static final String DATA_SERVICE_C = "DataServiceC";

    /**
     * Método principal que disponibiliza os serviços distribuidos 
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            DataServiceAdapter serviceAdapter = new DataServiceAdapter();
            Registry registry = LocateRegistry.createRegistry(10002);
            registry.bind(DATA_SERVICE_C, serviceAdapter);
            registry = LocateRegistry.createRegistry(9002);
            registry.bind(TRANS_APP_C, new TransAppC(new TransationLocal(serviceAdapter)));
            System.out.println("Servidor C iniciado");
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
