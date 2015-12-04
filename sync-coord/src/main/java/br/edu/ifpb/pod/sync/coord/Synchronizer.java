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
 * Classe que sincroniza os dados entre os bancos A (PostgreSQL), B(MySQL) e C
 * (Google Datastore)
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
        initMirror();
    }

    /**
     * método que insere os dados de todos os bancos no repositorio de entidades
     * de cada banco de dado
     */
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

    /**
     * método que salva o espelho de dado caso não tenha nada escrito no arquivo
     */
    private void initMirror() {
        if (mirrorManager.getProperty("checksum") == null) {
            saveHashDB();
            saveHashEntity();
        }
    }

    /**
     * salva o checksum de cada entidade no espelho do banco
     */
    private void saveHashEntity() {
        repository.getTeacherTOA().forEach((y, x) -> {
            String hash = GenerateHash.generateMD5(x);
            mirrorManager.setProperty(String.valueOf(x.getCode()), hash);
        });
    }

    /**
     * salva o checksum do bancos de dados sincronizado no espelho do banco
     */
    private void saveHashDB() {
        String hashDB = GenerateHash.generateMD5(repository.getTeacherTOA());
        mirrorManager.setProperty("checksum", hashDB);
    }

    /**
     * sincroniza os dados entre os banco de dados A (PostgreSQL),B (MySQL),C
     * (Google Datastore)
     */
    public void sync() {
        boolean haveChange = haveChange();
        boolean erro = false;
        if (haveChange) {
            try {
                replicationA();
                replicationB();
                replicationC();
            } catch (Exception ex) {
                erro = true;
            }
            if (!erro) {
                mirrorManager.removeAll();
                saveHashEntity();
                saveHashDB();
            }
        }
    }

    /**
     * sincroniza os dados do banco A com os demais bancos de dados
     *
     * @throws RemoteException
     */
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

    /**
     * sincroniza os dados do banco B com os demais bancos de dados
     *
     * @throws RemoteException
     */
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

    /**
     * sincroniza os dados do banco C com os demais bancos de dados
     *
     * @throws RemoteException
     */
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

    /**
     * verifica se possui alguma mudança nos bancos de dados de acordo com
     * espelho do banco
     *
     * @return
     */
    private boolean haveChange() {
        String checksum = mirrorManager.getProperty("checksum");

        String hashA = GenerateHash.generateMD5(repository.getTeacherTOA());
        String hashB = GenerateHash.generateMD5(repository.getTeacherTOB());
        String hashC = GenerateHash.generateMD5(repository.getTeacherTOC());

        return !checksum.equals(hashA) || !checksum.equals(hashB) || !checksum.equals(hashC);
    }

}
