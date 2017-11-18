/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egovy;

import egovy.FuzzyMatrix.Triplet;
import java.util.Arrays;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.simple.SimpleMatrix;

public class WeightComputer {
    
    // References: 1311.2886.pdf
    public static SimpleMatrix getFuzzyWeight(FuzzyMatrix pairwise) {
//        for(int i = 0; i < pairwise.numRows(); i++) {
//            for(int j = 0; j < pairwise.numCols(); j++) {
//                Triplet t = pairwise.get(i, j);
//                System.out.print(t.get(0) + "," + t.get(1) + "," + t.get(2) + "   ");
//            }
//            System.out.print("\n");
//        }
        
        // Converts pairwise of matrix of fuzzy triplet to geometric mean 
        SimpleMatrix geometricMean = new SimpleMatrix(pairwise.numRows(), 3);
        for(int row = 0; row < geometricMean.numRows(); row++) {
            SimpleMatrix product = new Triplet(1);
            for(int col = 0; col < pairwise.numCols(); col++) {
                product = product.elementMult(pairwise.get(row, col));
            }
            product = product.elementPower(1.0 / pairwise.numRows());
            geometricMean.setRow(row, 0, product.getDDRM().data);
        }
//        System.out.println(geometricMean);
        
        SimpleMatrix increasingReverse = computeSumByCol(geometricMean);
        increasingReverse = increasingReverse.elementPower(-1);
        Arrays.sort(increasingReverse.getDDRM().data);
//        System.out.println(increasingReverse);
        
        SimpleMatrix fuzzyWeights = new SimpleMatrix(geometricMean);
        for(int row = 0; row < geometricMean.numRows(); row++) {
            SimpleMatrix rowVector = fuzzyWeights.extractVector(true, row);
            rowVector = rowVector.elementMult(increasingReverse);
            fuzzyWeights.setRow(row, 0, rowVector.getDDRM().data);
        }
//        System.out.println(fuzzyWeights);
        
        SimpleMatrix normalizedWeights = computeAvgByRow(fuzzyWeights);
//        System.out.println(normalizedWeights);
        normalizedWeights = normalizedWeights.divide(normalizedWeights.elementSum());
//        System.out.println(normalizedWeights);
        
        return normalizedWeights;
    }
        
    public static SimpleMatrix computeAvgByRow(SimpleMatrix matrix) {
        SimpleMatrix avgRows = computeSumByRow(matrix);
        avgRows = avgRows.divide(matrix.numCols());
        return avgRows;
    }
    
    public static SimpleMatrix computeSumByRow(SimpleMatrix matrix) {
        DMatrixRMaj tmp = new DMatrixRMaj(matrix.numRows(), 1);
        CommonOps_DDRM.sumRows(matrix.getDDRM(), tmp);
        return SimpleMatrix.wrap(tmp);
    }
        
    public static SimpleMatrix computeSumByCol(SimpleMatrix matrix) {
        DMatrixRMaj tmp = new DMatrixRMaj(1, matrix.numCols());
        CommonOps_DDRM.sumCols(matrix.getDDRM(), tmp);
        return SimpleMatrix.wrap(tmp);
    }
}
