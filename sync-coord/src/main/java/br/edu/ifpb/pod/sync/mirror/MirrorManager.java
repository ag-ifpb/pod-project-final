/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.sync.mirror;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que manipula o gerenciamento do arquivo espelho dos banco de dados
 * sincronizados
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class MirrorManager {

    private Properties properties;
    private static final Path PATH = Paths.get("src/main/resources/mirror.properties");

    public MirrorManager() {
        try {
            this.properties = new Properties();
            if (!Files.exists(PATH)) {
                Files.createFile(PATH);
            }
            properties.load(new FileInputStream(PATH.toFile()));
        } catch (IOException ex) {
            Logger.getLogger(MirrorManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void save() {
        try {
            properties.store(new FileOutputStream(PATH.toFile()), null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MirrorManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MirrorManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        save();
    }

    public void remove(String key) {
        properties.remove(key);
        save();
    }

    public void setPropertiesMap(Map<String, String> map) {
        properties.putAll(map);
        save();
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public boolean exits(String key) {
        return properties.containsKey(key);
    }

    public void removeAll() {
        properties.clear();
        save();
    }

}
