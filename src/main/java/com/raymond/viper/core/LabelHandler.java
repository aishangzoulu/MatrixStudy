package com.raymond.viper.core;

import com.raymond.viper.model.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Raymond on 2016/12/18.
 * 照片名
 */
public class LabelHandler {
    private String aArray[];
    private String bArray[];

    public LabelHandler() {
        aArray = new String[Constants.SET_SIZE];
        bArray = new String[Constants.SET_SIZE];
        int index = 0;
        ClassLoader classLoader = getClass().getClassLoader();
        File labelFile = new File(classLoader.getResource("VIPER/csv/VIPeR_cam_a_label.txt").getFile());
        try (BufferedReader br = new BufferedReader(new FileReader(labelFile))) {
            for (String line; (line = br.readLine()) != null; ) {
                aArray[index++] = line;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        index = 0;
        labelFile = new File(classLoader.getResource("VIPER/csv/VIPeR_cam_b_label.txt").getFile());
        try (BufferedReader br = new BufferedReader(new FileReader(labelFile))) {
            for (String line; (line = br.readLine()) != null; ) {
                bArray[index++] = line;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String[] getaArray() {
        return aArray;
    }

    public void setaArray(String[] aArray) {
        this.aArray = aArray;
    }

    public String[] getbArray() {
        return bArray;
    }

    public void setbArray(String[] bArray) {
        this.bArray = bArray;
    }
}
