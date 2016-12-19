package com.raymond.viper.util;

import com.opencsv.CSVReader;
import com.raymond.viper.model.Constants;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Vector;

/**
 * Created by Raymond on 2016/12/18.
 */
public class MyMatrixUtils {
    public static final char DEFAULT_QUOTE_CHARACTER = '"';
    public static final char DEFAULT_SEPARATE_CHARACTER = ',';
    public static final char DEFAULT_ESCAPE_CHARACTER = '\\';

    public static Vector convert2Vector(double feature[][], int columnIndex) {
        Vector vector = new Vector<Double>();
        for (int i = 0; i < feature.length; i++) {
            vector.add(feature[i][columnIndex]);
        }
        return vector;
    }

    public static RealVector convert2RealVector(double feature[][], int columnIndex) {
        double temp[] = new double[feature.length];
        for (int i = 0; i < feature.length; i++) {
            temp[i] = feature[i][columnIndex];
            //vector.append(feature[i][columnIndex]);
        }
        RealVector vector = new ArrayRealVector(temp);
        return vector;
    }

    public static RealMatrix convert2RealMatrix(double feature[][], int columnIndex) {
        double matrixData[][] = new double[feature.length][1];
        for (int i = 0; i < feature.length; i++) {
            matrixData[i][0] = feature[i][columnIndex];
            //vector.append(feature[i][columnIndex]);
        }
        RealMatrix matrix = MatrixUtils.createRealMatrix(matrixData);
        return matrix;
    }

    public static RealMatrix convert2RealMatrix(ArrayRealVector realVector) {
        double[] dataRef = realVector.getDataRef();
        double matrixData[][] = new double[dataRef.length][1];
        for (int i = 0; i < dataRef.length; i++) {
            matrixData[i][0] = dataRef[i];
            //vector.append(feature[i][columnIndex]);
        }
        RealMatrix matrix = MatrixUtils.createRealMatrix(matrixData);
        return matrix;
    }

    /**
     * get matrixData from file
     *
     * @param fileName relative to resource
     * @return
     */
    public static double[][] getMatrixDataFromCSV(String fileName) {
        double feature[][] = new double[Constants.DIMENSIONS][Constants.SET_SIZE];
        CSVReader csvReader = null;
        try {
            ClassLoader classLoader = MyMatrixUtils.class.getClassLoader();
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
        } catch (Exception ex) {

        }
        return feature;
    }
}
