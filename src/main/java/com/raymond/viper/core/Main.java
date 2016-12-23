package com.raymond.viper.core;

import com.raymond.viper.model.Constants;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Created by Raymond on 2016/12/20.
 */
public class Main {
    public static void main(String[] args) {
        DataHandler dataHandler = new DataHandler();
        double[][] segmaISet = dataHandler.getSegmaISet();
        double[][] segmaSSet = dataHandler.getSegmaSSet();
        RealMatrix matrixI= MatrixUtils.createRealMatrix(segmaISet);
        RealMatrix matrixS= MatrixUtils.createRealMatrix(segmaSSet);
        RealMatrix realMatrix=matrixS.subtract(matrixI);
        EigenDecomposition eigenDecomposition=new EigenDecomposition(realMatrix);
        RealMatrix matrixL = eigenDecomposition.getV();
        double[][] matrixLData = matrixL.getData();
        double[][] matrixNewLData=new double[Constants.DIMENSIONS][Constants.DIMENSIONS];
        for(int i=0;i<Constants.DIMENSIONS;i++){
            for(int j=0;j<Constants.DIMENSIONS;j++){
                matrixNewLData[i][j]=matrixLData[i][j];
            }
        }
        RealMatrix matrix=MatrixUtils.createRealMatrix(matrixNewLData);
        TestHandler testHandler=new TestHandler();
        testHandler.testAccuracy(matrix);
    }
}
