package br.edu.ifpb.pod.core.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "professor")
public class TeacherTO implements Serializable,Comparable<TeacherTO> {

    @Id
    @Column(name = "codigo")
    private int code;
    @Column(name = "nome",length = 40,nullable = false)
    private String name;
    @Column(name = "abreviacao",length = 14,nullable = false)
    private String abbrev;
    @Column(name = "ativo")
    private boolean active=true;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "TeacherTO{" + "code=" + code + ", name=" + name + ", abbrev=" + abbrev + ", active=" + active + '}';
    }

    @Override
    public int compareTo(TeacherTO o) {
        return this.getCode()-o.getCode();
    }
    
    
}
