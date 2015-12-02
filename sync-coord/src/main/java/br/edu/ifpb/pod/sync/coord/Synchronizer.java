/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.sync.coord;

import ag.ifpb.pod.rmi.core.DatastoreService;
import br.edu.ifpb.pod.core.remote.contract.TransationCoord;
import br.edu.ifpb.pod.sync.hash.GenerateHash;
import br.edu.ifpb.pod.sync.mirror.MirrorManager;
import br.edu.ifpb.pod.sync.repository.Repository;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class Synchronizer {

    private DatastoreService serviceA;
    private DatastoreService serviceB;
    private DatastoreService serviceC;
    private TransationCoord transationCoord;
    private MirrorManager mirrorManager;
    private Repository repository;

    public Synchronizer(DatastoreService serviceA, DatastoreService serviceB, DatastoreService serviceC, TransationCoord transationCoord) {
        this.serviceA = serviceA;
        this.serviceB = serviceB;
        this.serviceC = serviceC;
        this.transationCoord = transationCoord;
        mirrorManager = new MirrorManager();
        constructRepository();
        init();
    }

    private void constructRepository() {
        try {
            repository = new Repository();
            repository.setTeacherTOA(serviceA.listTeachers());
            repository.setTeacherTOB(serviceB.listTeachers());
            repository.setTeacherTOC(serviceC.listTeachers());
        } catch (RemoteException ex) {
            Logger.getLogger(Synchronizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init() {
        if (mirrorManager.getProperty("checksum") == null) {
            saveHashDB();
            saveHashEntity();
        }
    }

    private void saveHashEntity() {
        repository.getTeacherTOA().forEach(x -> {
            String hash = GenerateHash.generateMD5(x);
            mirrorManager.setProperty(String.valueOf(x.getCode()), hash);
        });
    }

    private void saveHashDB() {
        String hashDB = GenerateHash.generateMD5(repository.getTeacherTOA());
        mirrorManager.setProperty("checksum", hashDB);
    }

    public void sync() {
        if (haveChange()) {
            try {
                replicationA();
                replicationB();
                replicationC();
                repository.setTeacherTOA(serviceA.listTeachers());
                saveHashEntity();
                saveHashDB();
            } catch (RemoteException ex) {
                Logger.getLogger(Synchronizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void replicationA() {
        repository.getTeacherTOA().forEach(x -> {
            String code = String.valueOf(x.getCode());
            try {
                transationCoord.beginAll();
                if (!mirrorManager.exits(code)) {
                    serviceB.createTeacher(x);
                    serviceC.createTeacher(x);
                } else if (!GenerateHash.generateMD5(x).equals(mirrorManager.getProperty(code))) {
                    serviceB.updateTeacher(x);
                    serviceC.updateTeacher(x);
                }
                transationCoord.commitAll();
                transationCoord.rollbackAll();
            } catch (RemoteException ex) {
                Logger.getLogger(Synchronizer.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    private void replicationB() {
        repository.getTeacherTOB().forEach(x -> {
            String code = String.valueOf(x.getCode());
            if (!mirrorManager.getProperty(code).equals(GenerateHash.generateMD5(x))) {
                try {
                    serviceA.updateTeacher(x);
                    serviceC.updateTeacher(x);
                } catch (RemoteException ex) {
                    Logger.getLogger(Synchronizer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void replicationC() {
        repository.getTeacherTOC().forEach(x -> {
            String code = String.valueOf(x.getCode());
            if (!mirrorManager.getProperty(code).equals(GenerateHash.generateMD5(x))) {
                try {
                    serviceA.updateTeacher(x);
                    serviceB.updateTeacher(x);
                } catch (RemoteException ex) {
                    Logger.getLogger(Synchronizer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private boolean haveChange() {
        String checksum = mirrorManager.getProperty("checksum");

        String hashA = GenerateHash.generateMD5(repository.getTeacherTOA());
        String hashB = GenerateHash.generateMD5(repository.getTeacherTOB());
        String hashC = GenerateHash.generateMD5(repository.getTeacherTOC());

        return !checksum.equals(hashA) || !checksum.equals(hashB) || !checksum.equals(hashC);
    }

}
