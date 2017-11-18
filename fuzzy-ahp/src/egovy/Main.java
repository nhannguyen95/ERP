package egovy;

import egovy.FuzzyMatrix.Triplet;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        FuzzyAhp fahp = new FuzzyAhp();
        
        // Read the Hierarchy structure and pairwise matrix values
        // into Ahp object
        fahp.build("fuzzy-ahp-data.txt");
                
        fahp.analyze();
        
        // Debug
        fahp.print();
    }
    
}
