package egovy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.ejml.simple.SimpleMatrix;

public class NhanTo {
    public static void main(String args[]) {    
        new NhanTo();
    }
    
    private static final Double EPSILON = 1e-9;
    
    private Integer noAlternatives;
    private Integer noCriteria;
    private Map<Integer, String> alternatives;
    private Map<Integer, String> criteria;
    private SimpleMatrix criteriaWeights;
    private SimpleMatrix caWeights;  // Criteria weights with respect to Alternatives
    private SimpleMatrix alternativeWeights;  // The result
    
    public NhanTo() {
        // Init properties
        alternatives = new HashMap<Integer, String>();
        criteria = new HashMap<Integer, String>();
        
        try {
            readInput();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        alternativeWeights = caWeights.transpose().mult(criteriaWeights);
        for(Integer i = 0; i < noAlternatives; i++) {
            System.out.println(alternatives.get(i) + ": " + alternativeWeights.get(i, 0));
        }
    }
    
    private void readInput() throws FileNotFoundException, SumWeightException {
        Scanner sc = new Scanner(new File("input.txt"));
        
        // Read alternatives
        noAlternatives = sc.nextInt(); sc.nextLine();
        for(Integer i = 0; i < noAlternatives; i++) {
            String alternativeName = sc.nextLine();
            alternatives.put(i, alternativeName);
        }
        
        // Read criteria and weights
        noCriteria = sc.nextInt(); sc.nextLine();
        criteriaWeights = new SimpleMatrix(noCriteria, 1);
        caWeights = new SimpleMatrix(noCriteria, noAlternatives);
        for(Integer i = 0; i < noCriteria; i++) {
            String criteriaName = sc.nextLine();
            criteria.put(i, criteriaName);            
            
            Double criteriaWeight = sc.nextDouble();
            criteriaWeights.set(i, 0, criteriaWeight);
            
            for(Integer j = 0; j < noAlternatives; j++) {
                Double caWeight = sc.nextDouble();
                caWeights.set(i, j, caWeight);
            }
            if (i < noCriteria - 1) sc.nextLine();
        }
        
        Double sum = 0.0;
        for(Integer i = 0; i < noCriteria; i++) {
            sum += criteriaWeights.get(i, 0);
        }
        if (Math.abs(sum - 1.0) > EPSILON) {
            throw new SumWeightException("Sum of criteria weights must equal 1.0");
        }
        
    }
}
