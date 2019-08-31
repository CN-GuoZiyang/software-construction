/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 * @author Guo Ziyang
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    // 	a set of source vertices of the graph 
    // Representation invariant:
    // 	vertices is a no-null List
    // Safety from rep exposure:
    // 	all fields are private and final
    
    public ConcreteVerticesGraph() {}
    
    /**
     * check the representation invariant
     */
    public void checkReq() {
    	assert vertices != null;
    }
    
    @Override public boolean add(L vertex) {
    	checkReq();
    	if(this.hasVertex(vertex)) {
    		return false;
    	}
    	Vertex<L> tempVertex = new Vertex<>(vertex);
    	return vertices.add(tempVertex);
    }
    
    @Override public int set(L source, L target, int weight) {
    	checkReq();
    	this.add(source);
    	this.add(target);
    	Vertex<L> sourceVertex = vertices.get(this.getIndex(source));
    	if(weight > 0) {
    		return sourceVertex.setWeight(target, weight);
    	} else if(weight <= 0 && sourceVertex.getTargetMap().containsKey(target)) {
    		int tempWeight = sourceVertex.getTargetMap().get(target);
    		sourceVertex.getTargetMap().remove(target);
    		return tempWeight;
    	} else {
    		return 0;
    	}
    }
    
    @Override public boolean remove(L vertex) {
    	checkReq();
    	if(hasVertex(vertex)) {
    		vertices.remove(this.getIndex(vertex));
    		Iterator<Vertex<L>> iterator = vertices.iterator();
    		while(iterator.hasNext()) {
    			Vertex<L> tempVertex = iterator.next();
    			Map<L, Integer> targetMap = tempVertex.getTargetMap();
    			if(targetMap.containsKey(vertex)) {
    				targetMap.remove(vertex);
    			}
    		}
    		return true;
    	} else {
    		return false;
    	}
    }
    
    @Override public Set<L> vertices() {
    	checkReq();
    	return vertices.stream().map(Vertex::getLabel).collect(Collectors.toSet());
    }
    
    @Override public Map<L, Integer> sources(L target) {
    	checkReq();
    	Map<L, Integer> sourcesMap = new HashMap<>();
    	Iterator<Vertex<L>> iterator = vertices.iterator();
    	while(iterator.hasNext()) {
    		Vertex<L> tempVertex = iterator.next();
    		if(tempVertex.getTargetMap().containsKey(target)) {
    			sourcesMap.put(tempVertex.getLabel(), tempVertex.getTargetMap().get(target));
    		}
    	}
    	return sourcesMap;
    }
    
    @Override public Map<L, Integer> targets(L source) {
    	checkReq();
    	return vertices.get(this.getIndex(source)).getTargetMap();
    }
    
    /**
     * judge whether a vertex is in the graph
     * 
     * @param vertex the vertex to be judged
     * @return whether the vertex is in the graph
     */
    public boolean hasVertex(L vertex) {
    	Iterator<Vertex<L>> iterator = vertices.iterator();
    	while(iterator.hasNext()) {
    		if(vertex.equals(iterator.next().getLabel())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * get the index of the given vertex in the vertex list
     * 
     * @param vertex the given vertex
     * @return the index of the vertex
     */
    public int getIndex(L vertex) {
    	for(int i = 0; i < vertices.size(); i ++) {
    		if(vertex.equals(vertices.get(i).getLabel())) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    @Override public String toString() {
    	checkReq();
    	return vertices.stream()
    			.filter(vertice->!vertice.getTargetMap().isEmpty())
    			.map(Vertex::toString)
    			.collect(Collectors.joining("\n"));
    }
    
}

/**
 * the abstraction of an edge
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 * 
 * @author Guo Ziyang
 */
class Vertex<L> {
    /**
     * the name of the vertex
     */
    private final L label;
    /**
     * the map which contains the target vertex and the weight of the vertex
     */
    private final Map<L, Integer> targetMap = new HashMap<>();
    
    // Abstraction function:
    //   the label of source vertex and the target vertex and length connecting to it
    // Representation invariant:
    //   vertex should be not null and target vertex should NOT be itself
    //   the value of the map should be above zero 
    // Safety from rep exposure:
    //   all the fields are private and final
    
    public Vertex(final L label) {
    	this.label = label;
    }
    
    /**
     * check the representation invariant
     */
    private void checkRep() {
    	assert label != null;
        Iterator<Map.Entry<L, Integer>> it = targetMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<L, Integer> entry = it.next();
            assert entry.getKey() != null;
            assert entry.getKey() != label;
            assert entry.getValue() >= 0;
        }
    }
    
    /**
     * get the label of the vertex
     * 
     * @return the label of the vertex
     */
    public L getLabel() {
    	checkRep();
    	return label;
    }
    
    /**
     * get the target map of the vertex
     * 
     * @return target map
     */
    public Map<L, Integer> getTargetMap() {
    	checkRep();
    	return targetMap;
    }
    
    /**
     * set the weight between the vertex and the given vertex
     * 
     * @param target the given vertex
     * @param weight the weight of the edge
     * @return the previous weight of the edge 
     */
    public int setWeight(L target, int weight) {
    	checkRep();
    	if(targetMap.containsKey(target)) {
    		int temp = targetMap.get(target);
    		targetMap.put(target, weight);
    		return temp;
    	} else {
    		targetMap.put(target, weight);
    		return 0;
    	}
    }
    
    @Override public String toString() {
    	checkRep();
    	List<String> resultList = new ArrayList<>();
    	Iterator<Entry<L, Integer>> iterator = targetMap.entrySet().iterator();
    	while(iterator.hasNext()) {
    		Entry<L, Integer> temp = iterator.next();
    		resultList.add(this.getLabel().toString() + "==>" + temp.getKey().toString());
    	}
    	return resultList.stream().collect(Collectors.joining("\n"));
    }
}
