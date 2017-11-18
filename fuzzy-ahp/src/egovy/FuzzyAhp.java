package egovy;

import egovy.FuzzyMatrix.Triplet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.ejml.simple.SimpleMatrix;

public class FuzzyAhp {
    
    private int nNodes;
    private int nAlts;
    Map<Integer, Node> nodesMap, altsMap;
    private ArrayList<Integer> leafNodeIds;
    Node result;
    
    public FuzzyAhp() {
        nNodes = 0;
        nodesMap = new HashMap<Integer, Node>();
        altsMap = new HashMap<Integer, Node>();
        leafNodeIds = new ArrayList<Integer>();
        result = null;
    }
    
    
    public void build(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        
        // Read all nodes id and label
        nNodes = sc.nextInt();
        for(int i = 0; i < nNodes; i++) {
            Integer nodeId = sc.nextInt(); sc.nextLine();
            String label = sc.nextLine();
            nodesMap.put(nodeId, new Node(nodeId, label));
        }
        
        // Read all alternatives id and label
        // Must be continuous Id starting from 0
        nAlts = sc.nextInt();
        for(int i = 0; i < nAlts; i++) {
            Integer altId = sc.nextInt(); sc.nextLine();
            String label = sc.nextLine();
            altsMap.put(altId, new Node(altId, label));
        }
        
        // Build the graph (relation) between nodes
        int noParentNodes = sc.nextInt();
        for(int i = 0; i < noParentNodes; i++) {
            Integer parentId = sc.nextInt();
            Node parent = getNode(parentId);
            
            Integer noChild = sc.nextInt();
            for(int j = 0; j < noChild; j++) {
                Integer childId = sc.nextInt();
                Node child = getNode(childId);
                
                parent.addChild(child);
                child.setParent(parent);
            }
        }
        
        // Read the Pairwise comparing the criteria with respect to the goal
        int noMatrices = sc.nextInt();
        for(int i = 0; i < noMatrices; i++) {
            // The parent of pairwise nodes
            Integer parentId = sc.nextInt();
            Node parent = getNode(parentId);
            
            /* Construct the pairwise fuzzy matrix */
            int size = parent.getNumChildren();
            FuzzyMatrix pairwise = new FuzzyMatrix(size);  // fill with 1s 

            Integer noCell = sc.nextInt();  // Sufficient number of values to build pairwise fuzzy matrix
            for(int j = 0; j < noCell; j++) {
                Integer c1Id = sc.nextInt();  // Criteria (node) 1 ID
                Integer c2Id = sc.nextInt();  // Criteria (node) 2 ID
                Integer localId1 = parent.getLocalId(c1Id);
                Integer localId2 = parent.getLocalId(c2Id);
                
                Triplet triplet = new Triplet();
                triplet.set(0, sc.nextDouble());
                triplet.set(1, sc.nextDouble());
                triplet.set(2, sc.nextDouble());
                
                pairwise.set(localId1, localId2, triplet);     // Pairwise[i][j] = s 
                pairwise.set(localId2, localId1, triplet.inverse()); // Pairwise[j][j] = 1/s
            }
            
            parent.setPairwise(pairwise);
        }
        
        // Read the pairwise comparing the Alternatives with respect to the Criteria
        noMatrices = sc.nextInt();
        for(int i = 0; i < noMatrices; i++) {
            Integer nodeId = sc.nextInt();  // The criteria id 
            Node node = getNode(nodeId);
            
            int size = getNumAlternatives();
            FuzzyMatrix pairwise = new FuzzyMatrix(size);
            
            Integer noCell = sc.nextInt();
            for(int j = 0; j < noCell; j++) {
                Integer altId1 = sc.nextInt();  // Restriction: [0, size)
                Integer altId2 = sc.nextInt();  // Ditto
                
                Triplet triplet = new Triplet();
                triplet.set(0, sc.nextDouble());
                triplet.set(1, sc.nextDouble());
                triplet.set(2, sc.nextDouble());
                
                pairwise.set(altId1, altId2, triplet);
                pairwise.set(altId2, altId1, triplet.inverse());
            }
            
            node.setPairwiseAlt(pairwise);
        }   
        
        // Store leaf node ids
        for(Map.Entry<Integer, Node> entry : nodesMap.entrySet()) {
            Node node = entry.getValue();
            if (node.getNumChildren() == 0) {
                leafNodeIds.add(entry.getKey());
            }
        }
        
        WeightComputer.getFuzzyWeight(getNode(0).getPairwise());
    }
    
    public void analyze() {
        /* Compute weight for pairwise matrices with respect to goal */
        // Init
        Node goalNode = getGoalNode();
        goalNode.setGlobalPriority(1.0);
        goalNode.setLocalPriority(1.0);
        
        computeNodePriority(goalNode);
        
        /* Compute weight for pairwise matrices with respect to criteria */
        for(int i = 0; i < leafNodeIds.size(); i++) {
            Integer leafNodeId = leafNodeIds.get(i);
            Node leafNode = getNode(leafNodeId);
            
            // weightAlt is a row vector of size (number of alternatives, 1)
            SimpleMatrix weightAlt = WeightComputer.getFuzzyWeight(leafNode.getPairwiseAlt());
            weightAlt = weightAlt.scale(leafNode.getLocalPriority());
            leafNode.setWeightAlt(weightAlt);
        }
        
        /* Compute the weight for all alternatives */
        for(int i = 0; i < leafNodeIds.size(); i++) {
            Integer leafNodeId = leafNodeIds.get(i);
            Node leafNode = getNode(leafNodeId);
            
            // weightAlt is a row vector of size (number of alternatives, 1)
            SimpleMatrix weightAlt = leafNode.getWeightAlt();
            for(int j = 0; j < weightAlt.numRows(); j++) {
                Node alternative = getAlternative(j);
                alternative.setGlobalPriority(alternative.getGlobalPriority() + weightAlt.get(j, 0));
            }
        }
        
        /* The answer is the alternative whose weight(priority) is highest */
        Double maxWeight = 0.0;
        for(int i = 0; i < nAlts; i++) {
            Node alternative = getAlternative(i);
            
            if (alternative.getGlobalPriority() > maxWeight) {
                maxWeight = alternative.getGlobalPriority();
                result = alternative;
            }
        }
    }
    
    private void computeNodePriority(Node node) {
        // Ignore leaf nodes
        if (node.getNumChildren() == 0) {
            return;
        }
        
        // Computing weights (priority) vector using Fuzzy method
        FuzzyMatrix pairwise = node.getPairwise();
        SimpleMatrix weight = WeightComputer.getFuzzyWeight(pairwise);  // Column vector
        node.setWeight(weight);  // Unused storage
        
        ArrayList<Node> children = node.getChildren();
        for(int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            child.setGlobalPriority(weight.get(i, 0));
            child.setLocalPriority(child.getGlobalPriority() * node.getLocalPriority());
            
            computeNodePriority(child);
        }
    }
    
    public Node getNode(Integer id) {
        return nodesMap.get(id);
    }
    
    public Node getAlternative(Integer id) {
        return altsMap.get(id);
    }
    
    public Integer getNumAlternatives() {
        return altsMap.size();
    }
    
    private Node getGoalNode() {
        return this.nodesMap.get(0);
    }
    
    public void print() {
        String description = "";
        String format = "";
        
        System.out.println("AHP HIERARCHY");
        print(getGoalNode(), 0);
        
        System.out.println("\nLEAF NODE AND LOCAL PRIORITY (WEIGHT)");
        for(int i = 0; i < leafNodeIds.size(); i++) {
            Integer leafNodeId = leafNodeIds.get(i);
            Node leafNode = getNode(leafNodeId);
            
            description = String.format("%-20s %.3f", 
                                    leafNode.getLabel(), 
                                    leafNode.getLocalPriority());
            System.out.println(description);
        }
        
        System.out.println("\nALTERNATIVES AND THEIR PRIORITY");
        Double sum = 0.0;  
        
        for(int i = 0; i < nAlts; i++) {
            Node alternative = getAlternative(i);
            sum += alternative.getGlobalPriority();  // This should summed up to 1.0
            
            description = String.format("%-20s %.3f", 
                                    alternative.getLabel(), 
                                    alternative.getGlobalPriority());
            System.out.println(description);
        }
        description = String.format("%-20s %.3f", "Sum (should be 1.0)", sum);
        System.out.println(description);
        
        System.out.println("\nBEST CHOICE");
        System.out.println(result.getLabel());        
    }
    
    private void print(Node node, Integer depth) {
        String description = String.format("%" + ((depth > 0) ? depth * 2 : "") + "s"
                + "%d. %s, global: %.3f, local: %.3f", "", depth, node.getLabel(), node.getGlobalPriority(), node.getLocalPriority());
        System.out.println(description);
        
        for(Node child : node.getChildren()) {
            print(child, depth + 1);
        }
    }
 }
