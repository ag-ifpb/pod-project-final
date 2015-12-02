/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifpb.pod.core.remote.contract;

import ag.ifpb.pod.rmi.core.TeacherTO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public interface SyncLocal extends Remote{

    public void syncEntity(List<TeacherTO> teacherTOs) throws RemoteException;
    
}
