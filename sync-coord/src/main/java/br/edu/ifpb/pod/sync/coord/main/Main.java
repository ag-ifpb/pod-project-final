/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.sync.coord.main;

import br.edu.ifpb.pod.sync.coord.timer.SyncSchedule;

/**
 * Classe principal que executa a aplicação que sincroniza os bancos de dados
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class Main {

    public static void main(String[] args) {

        //No construtor é passado o tempo que a sicnrozinação deve ser executada repetidamente, nesse caso epecifico foi passado 5 minutos
        new SyncSchedule(5 * 60);
    }

}
