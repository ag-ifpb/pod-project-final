/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.core.remote.contract;

import br.edu.ifpb.pod.core.entity.TeacherTO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public interface DataService extends Remote{

    void createTeacher(TeacherTO to) throws RemoteException;

    void updateTeacher(TeacherTO to) throws RemoteException;

    Map<Integer,TeacherTO> listTeachers() throws RemoteException;
}
