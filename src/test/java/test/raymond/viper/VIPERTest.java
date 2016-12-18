package test.raymond.viper;

import com.raymond.viper.core.DataHandler;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Raymond on 2016/12/18.
 */
public class VIPERTest {
    @Test
    public void testGetCollections(){
        DataHandler dataHandler =new DataHandler();
        Set sameSet = dataHandler.getISet();
        Iterator iterator = sameSet.iterator();
        while(iterator.hasNext()){
            Object next = iterator.next();
            System.out.println(next.toString());
        }
        System.out.println(sameSet.size());
    }

    @Test
    public void testGetSegmaSSet(){
        DataHandler dataHandler =new DataHandler();
        dataHandler.calculate12();
    }

    @Test
    public void testOutput(){
        try (PrintWriter out = new PrintWriter("F://result.txt")) {
            out.println("ceshi");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
