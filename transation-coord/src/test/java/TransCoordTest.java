/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.edu.ifpb.pod.core.remote.contract.TransationCoord;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class TransCoordTest {
    
    private TransationCoord transationCoord;
    
    public TransCoordTest() {
        try {
            Registry registry=LocateRegistry.getRegistry(2010);
            transationCoord=(TransationCoord)registry.lookup("TransCoord");
            transationCoord.beginAll();
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(TransCoordTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Before
    public void setUp() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
//     @Test
     public void test() {
         
         assertNotNull(transationCoord);
     
     }
}
