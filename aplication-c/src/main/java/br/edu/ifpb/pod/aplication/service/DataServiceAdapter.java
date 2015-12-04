/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.aplication.service;

import ag.ifpb.pod.rmi.core.DatastoreService;
import br.edu.ifpb.pod.aplication.transation.convert.ConvertTeacherTO;
import br.edu.ifpb.pod.core.entity.TeacherTO;
import br.edu.ifpb.pod.core.remote.contract.DataService;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Oferece os serviços de manipulação da entidade Professor no banco de dados da
 * aplicação que utiliza o banco <b>C</b> (Google Datastore)
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class DataServiceAdapter extends UnicastRemoteObject implements DataService {

    private DatastoreService service;
    private List<TeacherTO> toManager;

    public DataServiceAdapter() throws RemoteException {
        try {
            System.setProperty("java.rmi.server.hostname", "localhost");
            Registry registry = LocateRegistry.getRegistry("200.129.71.228", 9090);
            this.service = (DatastoreService) registry.lookup("DatastoreService");
            this.toManager = new LinkedList<>();
        } catch (NotBoundException | AccessException ex) {
            Logger.getLogger(DataServiceAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Salva a entidade professor no banco de dados
     *
     * @param to
     * @throws RemoteException
     */
    @Override
    public void createTeacher(TeacherTO to) throws RemoteException {
        this.toManager.add(to);
    }

    /**
     * Atualiza a entidade professor no banco de dados
     *
     * @param to
     * @throws RemoteException
     */
    @Override
    public void updateTeacher(TeacherTO to) throws RemoteException {
        this.toManager.add(to);
    }

    /**
     * Retorna todas as entidades de Professor no banco de dados
     * 
     * @return
     * @throws RemoteException 
     */
    @Override
    public Map<Integer, TeacherTO> listTeachers() throws RemoteException {
        Map<Integer, TeacherTO> map = new TreeMap<>();
        List<ag.ifpb.pod.rmi.core.TeacherTO> list = service.listTeachers();
        list.forEach(x -> {
            TeacherTO teacher = ConvertTeacherTO.unmarshalling(x);
            map.put(teacher.getCode(), teacher);
        });
        return map;
    }

    public List<TeacherTO> getToManager() {
        return toManager;
    }

    public void setToManager(List<TeacherTO> toManager) {
        this.toManager = toManager;
    }

}
