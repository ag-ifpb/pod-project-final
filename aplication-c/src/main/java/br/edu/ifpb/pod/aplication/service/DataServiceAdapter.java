/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.aplication.service;

import ag.ifpb.pod.rmi.core.DatastoreService;
import ag.ifpb.pod.rmi.core.TeacherTO;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class DataServiceAdapter extends UnicastRemoteObject implements DatastoreService {

    private DatastoreService service;
    private Transation transation;
    private TeacherTO toManager;

    public DataServiceAdapter() throws RemoteException {
        try {
            System.setProperty("java.rmi.server.hostname", "200.129.71.228");
            Registry registry = LocateRegistry.getRegistry("200.129.71.228",9090);
            this.service = (DatastoreService) registry.lookup("DatastoreService");
            this.transation=new Transation(toManager,service);
        } catch (NotBoundException | AccessException ex) {
            Logger.getLogger(DataServiceAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createTeacher(TeacherTO to) throws RemoteException {
        this.toManager=to;
    }

    @Override
    public void updateTeacher(TeacherTO to) throws RemoteException {
        this.toManager=to;
    }

    @Override
    public List<TeacherTO> listTeachers() throws RemoteException {
       return service.listTeachers();
    }

    public Transation getTransation() {
        return transation;
    }
    
    

    private class Transation implements br.edu.ifpb.pod.aplication.transation.Transation{
    
        private TeacherTO toManager;
        private DatastoreService service;
        private boolean gerenciado;
        
        protected Transation(TeacherTO to,DatastoreService service){
            this.toManager=to;
            this.service=service;
            this.gerenciado=false;
        }

        @Override
        public void begin(){
            this.gerenciado=true;
        }

        @Override
        public void commit(){
            if(this.gerenciado && toManager!=null){
                try {
                    service.createTeacher(toManager);
                } catch (RemoteException ex) {
                    Logger.getLogger(DataServiceAdapter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        @Override
        public void rollback(){
            this.gerenciado=false;
            this.toManager=null;
        }
    }

}
