/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.aplication.transation.convert;

import ag.ifpb.pod.rmi.core.DatastoreService;
import ag.ifpb.pod.rmi.core.TeacherTO;

/**
 * Converte a entidade Professor para entidade usada no serviços distribuido {@link DatastoreService} 
 * para a entidade utilizada nessa aplicação e vice-versa
 * 
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class ConvertTeacherTO {

   
    public static TeacherTO marshalling(br.edu.ifpb.pod.core.entity.TeacherTO to) {
        TeacherTO teacher = new TeacherTO();
        teacher.setCode(to.getCode());
        teacher.setName(to.getName());
        teacher.setAbbrev(to.getAbbrev());
        teacher.setActive(to.isActive());
        return teacher;
    }

    public static br.edu.ifpb.pod.core.entity.TeacherTO unmarshalling(TeacherTO to) {
        br.edu.ifpb.pod.core.entity.TeacherTO teacher = new br.edu.ifpb.pod.core.entity.TeacherTO();
        teacher.setCode(to.getCode());
        teacher.setName(to.getName());
        teacher.setAbbrev(to.getAbbrev());
        teacher.setActive(to.isActive());
        return teacher;
    }
}
