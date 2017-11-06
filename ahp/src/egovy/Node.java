/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package egovy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.ejml.simple.SimpleMatrix;

/**
 *
 * @author apple
 */
public class Node {
    private Integer nodeId;
    private String label;
    private ArrayList<Node> children;
    private Map<Integer, Integer> globToLocal;  /* Used to convert the global graph id of the node to the children id of a parent.
                                                 * The local id starts from 0.
                                                 *
                                                 * E.x: a node's ID is 3 globally, but is 0 in its parent's children list.
                                                 */ 
    private Node parent;
    private SimpleMatrix pairwise;    // Pairwise (with respect to the goal) matrix of its children (criterias), only in parent nodes.
    private SimpleMatrix pairwiseAlt; // Pairwise (with respect to the alternatives) matrix, only in leaf nodes.
    private SimpleMatrix weight;      // Correspond to pairwise
    private SimpleMatrix weightAlt;   // Correspond to pairwiseAlt;
    private Double globalPriority;
    private Double localPriority;
    
    public Node() {
        children = new ArrayList<Node>();
        globToLocal = new HashMap<Integer, Integer>();
        nodeId = null;
        label = null;
        parent = null;
        pairwise = null;
        weight = null;
        weightAlt = null;
        globalPriority = 0.0;
        localPriority = 0.0;
    }
    
    public Node(Integer nodeId, String label) {
        this();
        this.nodeId = nodeId;
        this.label = label;
    }
    
    public void addChild(Node child) {
        // Map the global Id of child to local id of "this" children list
        Integer curSize = getNumChildren();
        globToLocal.put(child.getId(), curSize);
        
        // Then add the child to "this" children list
        this.children.add(child);
    }
    
    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public Integer getNumChildren() {
        return this.children.size();
    }
    
    public Integer getId() {
        return this.nodeId;
    }
    
    public Integer getLocalId(Integer globalId) {
        return globToLocal.get(globalId);
    }
    
    public void setPairwise(SimpleMatrix pairwise) {
        this.pairwise = pairwise;
    }
    
    public SimpleMatrix getPairwise() {
        return this.pairwise;
    }
    
    public void setGlobalPriority(Double priority) {
        this.globalPriority = priority;
    }
    
    public void setLocalPriority(Double priority) {
        this.localPriority = priority;
    }
    
    public Double getLocalPriority() {
        return this.localPriority;
    }
    
    public Double getGlobalPriority() {
        return this.globalPriority;
    }
    
    public ArrayList<Node> getChildren() {
        return this.children;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public void setPairwiseAlt(SimpleMatrix pairwise) {
        this.pairwiseAlt = pairwise;
    }
    
    public void setWeight(SimpleMatrix weight) {
        this.weight = weight;
    }
    
    public void setWeightAlt(SimpleMatrix weightAlt) {
        this.weightAlt = weightAlt;
    }
    
    public SimpleMatrix getPairwiseAlt() {
        return this.pairwiseAlt;
    }
    
    public SimpleMatrix getWeightAlt() {
        return this.weightAlt;
    }
}
