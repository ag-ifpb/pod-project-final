/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.sync.hash;

import br.edu.ifpb.pod.core.entity.TeacherTO;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que possui os serviços responsáveis por gerar os checksum dos bancos
 * de dados e das entidades
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class GenerateHash {

    public static String generateMD5(List<Object> objects) {
        StringBuilder concat = new StringBuilder();
        objects.forEach(x -> {
            concat.append(x.toString());
        });
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            byte[] messageDigest = algorithm.digest(concat.toString().getBytes("UTF-8"));
            return new String(messageDigest, "UTF-8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(GenerateHash.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String generateMD5(Map<Integer, TeacherTO> objects) {
        StringBuilder concat = new StringBuilder();
        objects.forEach((y, x) -> {
            concat.append(x.toString());
        });
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            byte[] messageDigest = algorithm.digest(concat.toString().getBytes("UTF-8"));
            return new String(messageDigest, "UTF-8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(GenerateHash.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String generateMD5(Object o) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            byte[] messageDigest = algorithm.digest(o.toString().getBytes("UTF-8"));
            return new String(messageDigest, "UTF-8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(GenerateHash.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
