/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifpb.pod.sync.repository;

import ag.ifpb.pod.rmi.core.TeacherTO;
import java.util.List;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class Repository {
    
    private List<TeacherTO> teacherTOA;
    private List<TeacherTO> teacherTOB;
    private List<TeacherTO> teacherTOC;
    
    public List<TeacherTO> getTeacherTOA() {
        return teacherTOA;
    }

    public void setTeacherTOA(List<TeacherTO> teacherTOA) {
        this.teacherTOA = teacherTOA;
    }

    public List<TeacherTO> getTeacherTOB() {
        return teacherTOB;
    }

    public void setTeacherTOB(List<TeacherTO> teacherTOB) {
        this.teacherTOB = teacherTOB;
    }

    public List<TeacherTO> getTeacherTOC() {
        return teacherTOC;
    }

    public void setTeacherTOC(List<TeacherTO> teacherTOC) {
        this.teacherTOC = teacherTOC;
    }

        
    
    
}
