/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.aplication.service;

import br.edu.ifpb.pod.core.entity.TeacherTO;
import br.edu.ifpb.pod.core.dao.DAO;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.EntityManager;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class DataService extends UnicastRemoteObject implements br.edu.ifpb.pod.core.remote.contract.DataService {

    private DAO<Integer, TeacherTO> dao;

    public DataService(EntityManager entityManager) throws RemoteException {
        this.dao = new DAO(entityManager);
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
    public Map<Integer, TeacherTO> listTeachers() throws RemoteException {
        List<TeacherTO> list = dao.findAll(TeacherTO.class);
        Map<Integer, TeacherTO> map = new TreeMap<>();
        list.forEach(x -> {
            map.put(x.getCode(), x);
        });
        return map;
    }

}
