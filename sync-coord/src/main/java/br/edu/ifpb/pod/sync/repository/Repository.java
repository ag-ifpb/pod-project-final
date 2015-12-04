/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.sync.repository;

import br.edu.ifpb.pod.core.entity.TeacherTO;
import java.util.Map;

/**
 * Classe que contém os respositorios das entidades de cada banco de dados
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class Repository {

    private Map<Integer, TeacherTO> teacherTOA;
    private Map<Integer, TeacherTO> teacherTOB;
    private Map<Integer, TeacherTO> teacherTOC;

    public Map<Integer, TeacherTO> getTeacherTOA() {
        return teacherTOA;
    }

    public void setTeacherTOA(Map<Integer, TeacherTO> teacherTOA) {
        this.teacherTOA = teacherTOA;
    }

    public Map<Integer, TeacherTO> getTeacherTOB() {
        return teacherTOB;
    }

    public void setTeacherTOB(Map<Integer, TeacherTO> teacherTOB) {
        this.teacherTOB = teacherTOB;
    }

    public Map<Integer, TeacherTO> getTeacherTOC() {
        return teacherTOC;
    }

    public void setTeacherTOC(Map<Integer, TeacherTO> teacherTOC) {
        this.teacherTOC = teacherTOC;
    }

}
