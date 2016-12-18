package test.raymond.viper;

import com.raymond.viper.core.DataHandler;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Raymond on 2016/12/18.
 */
public class VIPERTest {
    @Test
    public void testGetISet() {
        DataHandler dataHandler = new DataHandler();
        Set sameSet = dataHandler.getISet();
        Iterator iterator = sameSet.iterator();
        System.out.println(sameSet.size());
        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next.toString());
        }
    }

    @Test
    public void testGetSegmaISet() {
        DataHandler dataHandler = new DataHandler();
        dataHandler.getSegmaISet();
    }

    @Test
    public void testGetSegmaSSet() {
        DataHandler dataHandler = new DataHandler();
        dataHandler.getSegmaSSet();
    }
}
