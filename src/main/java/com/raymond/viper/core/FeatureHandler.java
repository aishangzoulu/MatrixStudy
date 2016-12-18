package com.raymond.viper.core;

import com.opencsv.CSVReader;
import com.raymond.viper.model.Constants;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by Raymond on 2016/12/18.
 * 特征矩阵
 */
public class FeatureHandler {
    public static final char DEFAULT_QUOTE_CHARACTER = '"';
    public static final char DEFAULT_SEPARATE_CHARACTER = ',';
    public static final char DEFAULT_ESCAPE_CHARACTER = '\\';
    private double aFeature[][];
    private double bFeature[][];

    public FeatureHandler() {
        aFeature = new double[Constants.DIMENSIONS][Constants.SET_SIZE];
        bFeature = new double[Constants.DIMENSIONS][Constants.SET_SIZE];
        CSVReader csvReader = null;
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File csvFile = new File(classLoader.getResource("VIPER/csv/VIPeR_cam_a.csv").getFile());
            csvReader = new CSVReader(new FileReader(csvFile),
                    DEFAULT_SEPARATE_CHARACTER, DEFAULT_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER);
            List<String[]> pixes = csvReader.readAll();
            int columnIndex = 0, index = 0, rowIndex = 0;
            while (index < pixes.size()) {
                if (index < Constants.DIMENSIONS * (columnIndex + 1)) {
                    String[] temp = pixes.get(index);
                    if (temp != null && temp.length > 0) {
                        aFeature[rowIndex][columnIndex] = Double.parseDouble(temp[0]);
                    } else {
                        aFeature[rowIndex][columnIndex] = 0;
                    }
                } else if (index == Constants.DIMENSIONS * (columnIndex + 1)) {
                    rowIndex = 0;
                    columnIndex++;
                }
                rowIndex++;
                index++;
            }
            csvReader.close();

            csvFile = new File(classLoader.getResource("VIPER/csv/VIPeR_cam_b.csv").getFile());
            csvReader = new CSVReader(new FileReader(csvFile),
                    DEFAULT_SEPARATE_CHARACTER, DEFAULT_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER);
            pixes = csvReader.readAll();
            columnIndex = 0;
            index = 0;
            rowIndex = 0;
            while (index < pixes.size()) {
                if (index < Constants.DIMENSIONS * (columnIndex + 1)) {
                    String[] temp = pixes.get(index);
                    if (temp != null && temp.length > 0) {
                        bFeature[rowIndex][columnIndex] = Double.parseDouble(temp[0]);
                    } else {
                        bFeature[rowIndex][columnIndex] = 0;
                    }
                } else if (index == Constants.DIMENSIONS * (columnIndex + 1)) {
                    rowIndex = 0;
                    columnIndex++;
                }
                rowIndex++;
                index++;
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csvReader != null) {
                try {
                    csvReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public double[][] getaFeature() {
        return aFeature;
    }

    public void setaFeature(double[][] aFeature) {
        this.aFeature = aFeature;
    }

    public double[][] getbFeature() {
        return bFeature;
    }

    public void setbFeature(double[][] bFeature) {
        this.bFeature = bFeature;
    }
}
