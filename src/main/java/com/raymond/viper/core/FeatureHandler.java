package com.raymond.viper.core;

import com.opencsv.CSVReader;
import com.raymond.viper.model.Constants;
import com.raymond.viper.util.MyMatrixUtils;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by Raymond on 2016/12/18.
 * import raw data into matrix
 */
public class FeatureHandler {
    public static final char DEFAULT_QUOTE_CHARACTER = '"';
    public static final char DEFAULT_SEPARATE_CHARACTER = ',';
    public static final char DEFAULT_ESCAPE_CHARACTER = '\\';
    private double aFeature[][];
    private double bFeature[][];

    public FeatureHandler() {
        aFeature= this.getMatrixDataFromCSV("VIPER/pca/1000/pca_a.csv");
        bFeature= this.getMatrixDataFromCSV("VIPER/pca/1000/pca_b.csv");
        //aFeature=this.createFeatureMatrix("VIPER/csv/VIPeR_cam_a.csv");
        //bFeature=this.createFeatureMatrix("VIPER/csv/VIPeR_cam_b.csv");
    }

    /**
     * get matrixData from file
     *
     * @param fileName relative to resource
     * @return
     */
    public double[][] getMatrixDataFromCSV(String fileName) {
        double feature[][] = new double[Constants.SET_SIZE][Constants.DIMENSIONS];
        CSVReader csvReader = null;
        try {
            ClassLoader classLoader = MyMatrixUtils.class.getClassLoader();
            File csvFile = new File(classLoader.getResource(fileName).getFile());
            csvReader = new CSVReader(new FileReader(csvFile),
                    DEFAULT_SEPARATE_CHARACTER, DEFAULT_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER);
            int rowIndex = 0;
            while (rowIndex < Constants.SET_SIZE) {
                String[] temp = csvReader.readNext();
                if (temp != null && temp.length == Constants.DIMENSIONS) {
                    for (int j = 0; j < Constants.DIMENSIONS; j++) {
                        feature[rowIndex][j] = Double.parseDouble(temp[j]);
                    }
                }
                rowIndex++;
            }
            csvReader.close();
        }catch (IOException e) {
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
        RealMatrix realMatrix= MatrixUtils.createRealMatrix(feature);
        return realMatrix.transpose().getData();
    }

    public double[][] createFeatureMatrix(String fileName){
        double[][] feature = new double[Constants.DIMENSIONS][Constants.SET_SIZE];
        CSVReader csvReader = null;
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File csvFile = new File(classLoader.getResource(fileName).getFile());
            csvReader = new CSVReader(new FileReader(csvFile),
                    DEFAULT_SEPARATE_CHARACTER, DEFAULT_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER);
            List<String[]> pixes = csvReader.readAll();
            int columnIndex = 0, index = 0, rowIndex = 0;
            while (index < pixes.size()) {
                if (index < Constants.DIMENSIONS * (columnIndex + 1)) {
                    String[] temp = pixes.get(index);
                    if (temp != null && temp.length > 0) {
                        feature[rowIndex][columnIndex] = Double.parseDouble(temp[0]);
                    } else {
                        feature[rowIndex][columnIndex] = 0;
                    }
                } else if (index == Constants.DIMENSIONS * (columnIndex + 1)) {
                    rowIndex = 0;
                    columnIndex++;
                }
                rowIndex++;
                index++;
            }
            csvReader.close();
        }catch (IOException e) {
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
        return feature;
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
