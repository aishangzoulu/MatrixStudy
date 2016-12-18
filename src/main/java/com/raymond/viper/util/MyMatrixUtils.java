package com.raymond.viper.util;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.Vector;

/**
 * Created by Raymond on 2016/12/18.
 */
public class MyMatrixUtils {
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
}
