/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egovy;

import org.ejml.simple.SimpleMatrix;

public class FuzzyMatrix {
    Triplet matrix[][];
    
    public FuzzyMatrix(int size) {
        matrix = new Triplet[size][size];
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++) 
                matrix[i][j] = new Triplet(1);
        
    }
    
    public void set(int row, int col, Triplet triplet) {
        matrix[row][col] = triplet;
    }
    
    public Triplet get(int row, int col) {
        return matrix[row][col];
    }
    
    public Integer numRows() {
        return matrix.length;
    }
    
    public Integer numCols() {
        assert(numRows() > 0);
        return matrix[0].length;
    }
    
    public static class Triplet extends SimpleMatrix {
        public Triplet() {
            super(1, 3);
        }
        
        public Triplet(double initValue) {
           super(1, 3, true, new double[]{initValue, initValue, initValue});
        }
        
        public Triplet inverse() {
            Triplet inv = new Triplet();
            inv.set(0, 1.0 / get(2));
            inv.set(1, 1.0 / get(1));
            inv.set(2, 1.0 / get(0));
            return inv;
        }
        
        public void set(int i, double v) {
            set(0, i, v);
        }
        
        public double get(int i) {
            return get(0, i);
        }
    }
    
}
