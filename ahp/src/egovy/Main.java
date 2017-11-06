package egovy;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Ahp ahp = new Ahp();
        
        // Read the Hierarchy structure and pairwise matrix values
        // into Ahp object
        ahp.build("test/ahp-data-2.txt");
        
        ahp.analyze();
        
        // Debug
        ahp.print();
    }
    
}
