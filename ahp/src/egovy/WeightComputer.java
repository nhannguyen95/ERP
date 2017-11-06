/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egovy;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.simple.SimpleMatrix;

/**
 *
 * @author apple
 */
public class WeightComputer {
    public static SimpleMatrix vectorRieng(SimpleMatrix pairwise) {
        // squareMatrix = pairwise * pairwise
        SimpleMatrix squaredMatrix = pairwise.mult(pairwise);
        
        // sumRowsMatrix = sumRows(squareMatrix)
        DMatrixRMaj tmp = new DMatrixRMaj(squaredMatrix.numRows(), 1);
        CommonOps_DDRM.sumRows(squaredMatrix.getDDRM(), tmp);
        SimpleMatrix sumRowsMatrix = SimpleMatrix.wrap(tmp);
        
        // weights = sumRowsMatrix / sum(sumRowsMatrix)
        Double sum = sumRowsMatrix.elementSum();
        SimpleMatrix weights = sumRowsMatrix.divide(sum);
        
        return weights;
    }
    
    public static SimpleMatrix chuanHoa(SimpleMatrix pairwise) {
        // sumColsMatrix = sumCols(pairwise)
        DMatrixRMaj tmp = new DMatrixRMaj(1, pairwise.numCols());
        CommonOps_DDRM.sumCols(pairwise.getDDRM(), tmp);
        SimpleMatrix sumColsMatrix = SimpleMatrix.wrap(tmp);
        
        // normMatrix = pairwise / sumColsMatrix
        SimpleMatrix normMatrix = new SimpleMatrix(pairwise);
        for(int i = 0; i < normMatrix.numRows(); i++) {
            for(int j = 0; j < normMatrix.numCols(); j++) {
                Double oldValue = normMatrix.get(i, j);
                Double newValue = oldValue / sumColsMatrix.get(0, j);
                normMatrix.set(i, j, newValue);
            }
        }
        
        // sumRowsMatrix = sumRows(normMatrix)
        tmp = new DMatrixRMaj(normMatrix.numRows(), 1);
        CommonOps_DDRM.sumRows(normMatrix.getDDRM(), tmp);
        SimpleMatrix sumRowsMatrix = SimpleMatrix.wrap(tmp);
        
        // weights = sumRowsMatrix / sum(sumRowsMatrix)
        Double sum = sumRowsMatrix.elementSum();
        SimpleMatrix weights = sumRowsMatrix.divide(sum);
        
        return weights;
    }
}
