/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.sync.coord;

import br.edu.ifpb.pod.core.entity.TeacherTO;
import br.edu.ifpb.pod.core.remote.contract.DataService;
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

    private DataService serviceA;
    private DataService serviceB;
    private DataService serviceC;
    private TransationCoord transationCoord;
    private MirrorManager mirrorManager;
    private Repository repository;

    public Synchronizer(DataService serviceA, DataService serviceB, DataService serviceC, TransationCoord transationCoord) {
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
        repository.getTeacherTOA().forEach((y, x) -> {
            String hash = GenerateHash.generateMD5(x);
            mirrorManager.setProperty(String.valueOf(x.getCode()), hash);
        });
    }

    private void saveHashDB() {
        String hashDB = GenerateHash.generateMD5(repository.getTeacherTOA());
        mirrorManager.setProperty("checksum", hashDB);
    }

    public void sync() {
        boolean haveChange = haveChange();
        boolean erro = false;
        if (haveChange) {
            try {
                replicationA();
                replicationB();
                replicationC();
            } catch (Exception ex) {
                ex.printStackTrace();
                erro = true;
            }
            if (!erro) {
                mirrorManager.removeAll();
                saveHashEntity();
                saveHashDB();
            }
        }
    }

    private void replicationA() throws RemoteException {
        for (TeacherTO teacher : repository.getTeacherTOA().values()) {
            String code = String.valueOf(teacher.getCode());
            try {
                if (!mirrorManager.exits(code)) {
                    transationCoord.beginAll();
                    serviceB.createTeacher(teacher);
                    serviceC.createTeacher(teacher);
                    transationCoord.commitAll();
                } else if (!GenerateHash.generateMD5(teacher).equals(mirrorManager.getProperty(code))) {
                    transationCoord.beginAll();
                    serviceB.updateTeacher(teacher);
                    serviceC.updateTeacher(teacher);
                    transationCoord.commitAll();
                }
            } catch (Exception ex) {
                transationCoord.rollbackAll();
                ex.printStackTrace();

            }

        }
    }

    private void replicationB() throws RemoteException {
        for (TeacherTO teacher : repository.getTeacherTOB().values()) {
            String code = String.valueOf(teacher.getCode());
            if (mirrorManager.exits(code) && !mirrorManager.getProperty(code).equals(GenerateHash.generateMD5(teacher))) {
                try {
                    transationCoord.beginAll();
                    serviceA.updateTeacher(teacher);
                    repository.getTeacherTOA().replace(teacher.getCode(), teacher);
                    serviceC.updateTeacher(teacher);
                    transationCoord.commitAll();
                } catch (Exception e) {
                    transationCoord.rollbackAll();
                    e.printStackTrace();
                }
            }
        }
    }

    private void replicationC() throws RemoteException {
        for (TeacherTO teacher : repository.getTeacherTOC().values()) {
            String code = String.valueOf(teacher.getCode());
            if (mirrorManager.exits(code) && !mirrorManager.getProperty(code).equals(GenerateHash.generateMD5(teacher))) {
                try {
                    transationCoord.beginAll();
                    serviceA.updateTeacher(teacher);
                    repository.getTeacherTOA().replace(teacher.getCode(), teacher);
                    serviceB.updateTeacher(teacher);
                    transationCoord.commitAll();
                } catch (Exception e) {
                    transationCoord.rollbackAll();
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean haveChange() {
        String checksum = mirrorManager.getProperty("checksum");

        String hashA = GenerateHash.generateMD5(repository.getTeacherTOA());
        String hashB = GenerateHash.generateMD5(repository.getTeacherTOB());
        String hashC = GenerateHash.generateMD5(repository.getTeacherTOC());

        return !checksum.equals(hashA) || !checksum.equals(hashB) || !checksum.equals(hashC);
    }

}
