package test.raymond.viper;

import com.raymond.viper.core.DataHandler;
import com.raymond.viper.core.Main;
import com.raymond.viper.core.TestHandler;
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

    @Test
    public void testGetLMatrix() {
        TestHandler testHandler = new TestHandler(false);
        double[][] lMatrix = testHandler.getLMatrix();
        System.out.println(lMatrix.length);
    }

    @Test
    public void testFinalAccuracy() {
        Main main = new Main();
        main.main(null);
    }

    @Test
    public void testTestAccuracy() {
        TestHandler testHandler = new TestHandler();
        double accuracy = testHandler.testAccuracy();
        System.out.println(accuracy);
    }
}
