/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.edu.ifpb.pod.aplication.service.DataServiceAdapter;
import br.edu.ifpb.pod.core.remote.contract.DataService;
import java.rmi.RemoteException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class DatastoreServiceTest {
    
    private DataService dataService;
    
    public DatastoreServiceTest() throws RemoteException {
        this.dataService=new DataServiceAdapter();
    }
    
    @Before
    public void setUp() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void testConnection() {
         assertNotNull(dataService);
     }
}
