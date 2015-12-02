/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.ifpb.pod.sync.mirror;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class MirrorManager {

    private Properties properties;
    private static final Path PATH=Paths.get("src/main/resources/mirror.properties");

    public MirrorManager() {
        try {
            this.properties=new Properties();
            if(!Files.exists(PATH)){
                Files.createFile(PATH);
            }
            properties.load(new FileInputStream(PATH.toFile()));
        } catch (IOException ex) {
            Logger.getLogger(MirrorManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setProperty(String key,String value){
        properties.setProperty(key, value);
    }
    
    public void remove(String key){
        properties.remove(key);
    }
    
    public void setPropertiesMap(Map<String,String> map){
        properties.putAll(map);
    }
    
    public String getProperty(String key){
        return properties.getProperty(key);
    }
    
    public boolean exits(String key){
        return properties.containsKey(key);
    }
    
}
