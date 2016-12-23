package com.raymond.viper.core;

import com.raymond.viper.model.Constants;
import com.raymond.viper.util.MyMatrixUtils;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Raymond on 2016/12/18.
 * calculate Segma(S)-Segma(I)
 */
public class DataHandler {
    private FeatureHandler featureHandler;
    private LabelHandler labelHandler;

    public DataHandler() {
        this.featureHandler = new FeatureHandler();
        this.labelHandler = new LabelHandler();
    }

    public Set getSSet() {
        Set sameSet = new HashSet<>();
        for (int i = 0; i < Constants.SET_SIZE; i++) {
            sameSet.add(i + "_" + i);
        }
        return sameSet;
    }

    public Set getISet() {
        Set iSet = new HashSet<>();
        LabelHandler labelHandler = new LabelHandler();
        String aArray[] = labelHandler.getaArray();
        String bArray[] = labelHandler.getbArray();
        double[][] aFeature = this.featureHandler.getaFeature();
        double[][] bFeature = this.featureHandler.getbFeature();
        for (int i = 0, aLength = aArray.length; i < aLength; i++) {
            RealVector aVector = MyMatrixUtils.convert2RealVector(aFeature, i);
            RealVector bVector = MyMatrixUtils.convert2RealVector(bFeature, i);
            double distance = aVector.getDistance(bVector);
            for (int j = 0, bLength = bArray.length; j < bLength; j++) {
                if (j != i) {
                    RealVector tempBVector = MyMatrixUtils.convert2RealVector(bFeature, j);
                    double tempDistance = aVector.getDistance(tempBVector);
                    if (tempDistance < distance) {
                        iSet.add(i + "_" + j);
                    }
                }
            }
        }
        return iSet;
    }

    public double[][] getSegmaISet() {
        Set iSet = this.getISet();
        Iterator iterator = iSet.iterator();
        RealMatrix resultMatrix = null;
        int index = 0;
        while (iterator.hasNext()) {
            index++;
            if (index % 100 == 0) {
                System.out.println(index);
            }
            String key = (String) iterator.next();
            String[] temp = key.split("_");
            int aIndex = Integer.parseInt(temp[0]);
            int bIndex = Integer.parseInt(temp[1]);
            double[][] aFeature = this.featureHandler.getaFeature();
            double[][] bFeature = this.featureHandler.getbFeature();
            RealVector vectorI = MyMatrixUtils.convert2RealVector(aFeature, aIndex);
            RealVector vectorJ = MyMatrixUtils.convert2RealVector(bFeature, aIndex);
            RealVector vectorL = MyMatrixUtils.convert2RealVector(bFeature, bIndex);
            //calculate wij
            double vectorIJNorm = vectorI.subtract(vectorJ).getL1Norm();
            if (vectorIJNorm == 0) {
                continue;
            }
            double norm = vectorI.subtract(vectorL).getL1Norm() / vectorIJNorm;
            double wij = Math.pow(Math.E, -norm);
            //calculate matrix
            RealVector subtractVector = vectorI.subtract(vectorL);
            RealMatrix tempMatrix = MyMatrixUtils.convert2RealMatrix((ArrayRealVector) subtractVector);
            RealMatrix transposeMatrix = tempMatrix.transpose();
            RealMatrix multiplyMatrix = tempMatrix.multiply(transposeMatrix);
            if (resultMatrix == null) {
                resultMatrix = multiplyMatrix.scalarMultiply(wij);
            } else {
                resultMatrix.add(multiplyMatrix.scalarMultiply(wij));
            }
        }
        double[][] result = resultMatrix.getData();
        StringBuilder stringBuilder = new StringBuilder();
        //NumberFormat formatter = new DecimalFormat("#0.000000");
        for (int i = 0; i < Constants.DIMENSIONS; i++) {
            for (int j = 0; j < Constants.DIMENSIONS; j++) {
                stringBuilder.append(result[i][j] + ",");
            }
            stringBuilder.append("_");
            stringBuilder.append(System.getProperty("line.separator"));
        }
        try (PrintWriter out = new PrintWriter("result_pca_i.txt")) {
            out.println(stringBuilder.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public double[][] getSegmaSSet() {
        Set sSet = this.getSSet();
        Iterator iterator = sSet.iterator();
        RealMatrix resultMatrix = null;
        int index = 0;
        while (iterator.hasNext()) {
            index++;
            if (index % 100 == 0) {
                System.out.println(index);
            }
            String key = (String) iterator.next();
            String[] temp = key.split("_");
            int aIndex = Integer.parseInt(temp[0]);
            double[][] aFeature = this.featureHandler.getaFeature();
            double[][] bFeature = this.featureHandler.getbFeature();
            RealVector vectorI = MyMatrixUtils.convert2RealVector(aFeature, aIndex);
            RealVector vectorJ = MyMatrixUtils.convert2RealVector(bFeature, aIndex);
            //calculate matrix
            RealVector subtractVector = vectorI.subtract(vectorJ);
            RealMatrix tempMatrix = MyMatrixUtils.convert2RealMatrix((ArrayRealVector) subtractVector);
            RealMatrix transposeMatrix = tempMatrix.transpose();
            RealMatrix multiplyMatrix = tempMatrix.multiply(transposeMatrix);
            if (resultMatrix == null) {
                resultMatrix = multiplyMatrix;
            } else {
                resultMatrix.add(multiplyMatrix);
            }
        }
        double[][] result = resultMatrix.getData();
        //NumberFormat formatter = new DecimalFormat("#0.000000");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Constants.DIMENSIONS; i++) {
            for (int j = 0; j < Constants.DIMENSIONS; j++) {
                stringBuilder.append(result[i][j] + ",");
            }
            stringBuilder.append("_");
            stringBuilder.append(System.getProperty("line.separator"));
        }
        try (PrintWriter out = new PrintWriter("result_pca_s.txt")) {
            out.println(stringBuilder.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
