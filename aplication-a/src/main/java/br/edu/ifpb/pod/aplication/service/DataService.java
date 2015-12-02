/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifpb.pod.aplication.service;

import ag.ifpb.pod.rmi.core.DatastoreService;
import ag.ifpb.pod.rmi.core.TeacherTO;
import br.edu.ifpb.pod.core.dao.DAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class DataService extends UnicastRemoteObject implements DatastoreService{
    
    private DAO<Integer,TeacherTO> dao;

    public DataService(EntityManager entityManager) throws RemoteException{
        this.dao=new DAO(entityManager);
    }

    @Override
    public void createTeacher(TeacherTO to) throws RemoteException {
        dao.persist(to);
    }

    @Override
    public void updateTeacher(TeacherTO to) throws RemoteException {
        dao.update(to);
    }

    @Override
    public List<TeacherTO> listTeachers() throws RemoteException {
        return dao.findAll(TeacherTO.class);
    }
    
    

}
