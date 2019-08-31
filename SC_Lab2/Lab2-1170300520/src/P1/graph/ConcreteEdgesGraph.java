/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 * 
 * @author Guo Ziyang
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   describe a graph using a set of vertices and a list of edges
    // Representation invariant:
    //   vertices is a set, the content of which cannot be repetitive
    //   edges is a list of edges, the number of which is limited by the number of vertices
    // Safety from rep exposure:
    //   all fields are private and final, to get the field one can invoke getters
    
    public ConcreteEdgesGraph() {}
    
    /**
     * check the representation invariant
     */
    private void checkRep() {
		assert edges.size() <= vertices.size() * (vertices.size() - 1);
	}
    
    @Override public boolean add(L vertex) {
    	checkRep();
        if(vertices.contains(vertex)) {
        	return false;
        } else {
			vertices.add(vertex);
			return true;
		}
    }
    
    @Override public int set(L source, L target, int weight) {
    	checkRep();
    	if(!vertices.contains(source)) {
    		vertices.add(source);
    	}
    	if(!vertices.contains(target)) {
    		vertices.add(target);
    	}
    	boolean findEdge = false;
    	Iterator<Edge<L>> iterator = edges.iterator();
    	Edge<L> tempEdge = null;
    	while (iterator.hasNext()) {
			tempEdge = iterator.next();
			if(source.equals(tempEdge.getSource()) && target.equals(tempEdge.getTarget())) {
				findEdge = true;
				break;
			}
		}
    	if(findEdge) {
    		if(weight > 0) {
    			edges.remove(tempEdge);
    			edges.add(new Edge<L>(source, target, weight));
    			return tempEdge.getWeight();
    		} else {
				edges.remove(tempEdge);
				return tempEdge.getWeight();
			}
    	} else {
    		if(weight > 0) {
    			edges.add(new Edge<L>(source, target, weight));
    			return 0;
    		} else {
    			return 0;
    		}
    	}
    }
    
    @Override public boolean remove(L vertex) {
    	checkRep();
        if(vertices.contains(vertex)) {
        	vertices.remove(vertex);
        	Iterator<Edge<L>> iterator = edges.iterator();
        	while(iterator.hasNext()) {
        		Edge<L> edge = iterator.next();
        		if(vertex.equals(edge.getSource()) || vertex.equals(edge.getTarget())) {
        			iterator.remove();
        		}
        	}
        	return true;
        } else {
        	return false;
        }
    }
    
    @Override public Set<L> vertices() {
    	checkRep();
        return vertices;
    }
    
    @Override public Map<L, Integer> sources(L target) {
    	checkRep();
        Iterator<Edge<L>> iterator = edges.iterator();
        Map<L, Integer> resultMap = new HashMap<>();
        while(iterator.hasNext()) {
        	Edge<L> tempEdge = iterator.next();
        	if(target.equals(tempEdge.getTarget())) {
        		resultMap.put(tempEdge.getSource(), tempEdge.getWeight());
        	}
        }
        return resultMap;
    }
    
    @Override public Map<L, Integer> targets(L source) {
    	checkRep();
    	Iterator<Edge<L>> iterator = edges.iterator();
        Map<L, Integer> resultMap = new HashMap<>();
        while(iterator.hasNext()) {
        	Edge<L> tempEdge = iterator.next();
        	if(source.equals(tempEdge.getSource())) {
        		resultMap.put(tempEdge.getTarget(), tempEdge.getWeight());
        	}
        }
        return resultMap;
    }
    
    @Override public String toString() {
    	return edges.stream().map(Edge::toString).collect(Collectors.joining("\n"));
    }
    
}

/**
 * the abstraction of the edge
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 * 
 * @author Guo Ziyang
 */
class Edge<L> {
    
	/**
	 * the source of the edge
	 */
    private final L source;
    /**
     * the target of the edge
     */
    private final L target;
    /**
     * the weight of the edge
     */
    private final int weight;
    
    // Abstraction function:
    //   the start, end and distance of an edge
    // Representation invariant:
    //   source and target should be not null and different
    //   weight should be above zero
    // Safety from rep exposure:
    //   all fields are private and final, to get the field one can invoke getters
    //   checkRep() should be invoked before other methods invoked
    
    public Edge(final L source, final L target, final int weight) {
    	this.source = source;
    	this.target = target;
    	this.weight = weight;
    }
    
    /**
     * check the representation invariant
     */
    private void checkRep() {
    	assert source != null;
    	assert target != null;
    	assert target != source;
    	assert weight >= 0;
    }
    
    /**
     * get the source of the edge
     * @return the source of the edge
     */
    public L getSource() {
    	checkRep();
    	return source;
    }
    
    /**
     * get the target of the edge
     * @return the target of the edge
     */
    public L getTarget() {
    	checkRep();
    	return target;
    }
    
    /**
     * get the weight of the edge
     * @return the weight of the edge
     */
    public int getWeight() {
    	checkRep();
    	return weight;
    }
    
    @Override public String toString() {
    	return source.toString() + "==>" + target.toString();
    }
    
}
