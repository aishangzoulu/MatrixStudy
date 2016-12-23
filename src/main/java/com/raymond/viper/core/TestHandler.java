package com.raymond.viper.core;

import com.raymond.viper.model.Constants;
import com.raymond.viper.util.MyMatrixUtils;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * Created by Raymond on 2016/12/19.
 * test accuracy
 */
public class TestHandler {
    private FeatureHandler featureHandler;
    private LabelHandler labelHandler;

    public TestHandler(boolean createFeatureHandler) {

    }

    public TestHandler() {
        this.featureHandler = new FeatureHandler();
        this.labelHandler = new LabelHandler();
    }

    /**
     * test accuracy
     *
     * @return
     */
    public double testAccuracy() {
        //use L*xi and compare to L*xj
        double[][] matrixData = this.getLMatrix();
        RealMatrix matrixL = MatrixUtils.createRealMatrix(matrixData);
        double[][] aFeature = this.featureHandler.getaFeature();
        double[][] bFeature = this.featureHandler.getbFeature();
        int errorCount = 0;
        for (int aIndex = 0; aIndex < Constants.SET_SIZE; aIndex++) {
            if (aIndex % 50 == 0) {
                System.out.println("progress:" + aIndex);
            }
            RealVector vectorI = MyMatrixUtils.convert2RealVector(aFeature, aIndex);
            RealVector vector = matrixL.preMultiply(vectorI);
            RealVector vectorJ = MyMatrixUtils.convert2RealVector(bFeature, aIndex);
            double distance = vector.getDistance(matrixL.preMultiply(vectorJ));
            int bIndex = 0;
            for (bIndex = 0; bIndex < Constants.SET_SIZE; bIndex++) {
                if (bIndex != aIndex) {
                    RealVector tempVector = MyMatrixUtils.convert2RealVector(bFeature, bIndex);
                    double tempDistance = vector.getDistance(matrixL.preMultiply(tempVector));
                    if (tempDistance < distance) {
                        //System.out.println(aIndex + "," + bIndex);
                        errorCount++;
                        break;
                    }
                }
            }
            if(bIndex==Constants.SET_SIZE){
                System.out.println(aIndex + "," + bIndex);
            }
        }
        System.out.println(Constants.SET_SIZE - errorCount);
        double accuracy = (Constants.SET_SIZE - errorCount) / (double)Constants.SET_SIZE;
        return accuracy;
    }

    /**
     * test accuracy
     *
     * @return
     */
    public double testAccuracy(RealMatrix matrixL) {
        //use L*xi and compare to L*xj
        double[][] aFeature = this.featureHandler.getaFeature();
        double[][] bFeature = this.featureHandler.getbFeature();
        int errorCount = 0;
        for (int aIndex = 0; aIndex < Constants.SET_SIZE; aIndex++) {
            if (aIndex % 50 == 0) {
                System.out.println("progress:" + aIndex);
            }
            RealVector vectorI = MyMatrixUtils.convert2RealVector(aFeature, aIndex);
            RealVector vector = matrixL.operate(vectorI);
            RealVector vectorJ = MyMatrixUtils.convert2RealVector(bFeature, aIndex);
            double distance = vector.getDistance(matrixL.operate(vectorJ));
            int bIndex = 0;
            for (bIndex = 0; bIndex < Constants.SET_SIZE; bIndex++) {
                if (bIndex != aIndex) {
                    RealVector tempVector = MyMatrixUtils.convert2RealVector(bFeature, bIndex);
                    double tempDistance = vector.getDistance(matrixL.operate(tempVector));
                    if (tempDistance < distance) {
                        //System.out.println(aIndex + "," + bIndex);
                        errorCount++;
                        break;
                    }
                }
            }
            if(bIndex==Constants.SET_SIZE){
                System.out.println(aIndex + "," + bIndex);
            }
        }
        System.out.println(Constants.SET_SIZE - errorCount);
        double accuracy = (Constants.SET_SIZE - errorCount) / (double)Constants.SET_SIZE;
        return accuracy;
    }

    /**
     * get L matrix
     *
     * @return
     */
    public double[][] getLMatrix() {
        double[][] matrixL = MyMatrixUtils.getMatrixDataFromCSV("VIPER/pca/result_pca_L.csv");
        //double[][] matrixL = MyMatrixUtils.getMatrixDataFromCSV("VIPER/lmatrix.csv");
        return matrixL;
    }

}
