/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    @Test
    @Override public void testAdd() {
    	Graph<String> graph = new ConcreteVerticesGraph<>();
    	assertTrue(graph.add("hello"));
    	assertTrue(graph.add("world"));
    	assertFalse(graph.add("hello"));
    }
    
    @Test
    @Override public void testSet() {
    	Graph<String> graph = new ConcreteVerticesGraph<>();
    	graph.add("jack");
    	graph.add("rose");
    	graph.add("tom");
    	graph.set("jack", "rose", 1);
    	graph.set("rose", "tom", 1);
    	graph.set("hello", "world", 1);
    	graph.set("hello", "world", -1);
    	graph.set("hello", "world", -1);
    	assertEquals("jack==>rose\nrose==>tom", graph.toString());
    }
    
    @Test
    @Override public void testRemove() {
    	Graph<String> graph = new ConcreteVerticesGraph<>();
    	graph.add("jack");
    	assertTrue(graph.remove("jack"));
    	assertFalse(graph.remove("jack"));
    }
    
    @Test
    @Override public void testVertices() {
    	Graph<String> graph = new ConcreteVerticesGraph<>();
    	graph.add("jack");
    	assertTrue(graph.vertices().contains("jack"));
    	graph.remove("jack");
    	assertEquals(Collections.EMPTY_SET, graph.vertices());
    }
    
    @Test
    @Override public void testSources() {
    	Graph<String> graph = new ConcreteVerticesGraph<>();
    	graph.add("jack");
    	graph.add("rose");
    	graph.set("jack", "rose", 1);
    	assertTrue(graph.sources("rose").containsKey("jack"));
    	graph.remove("jack");
    	assertFalse(graph.sources("rose").containsKey("jack"));
    }
    
    @Test
    @Override public void testTargets() {
    	Graph<String> graph = new ConcreteVerticesGraph<>();
    	graph.add("jack");
    	graph.add("rose");
    	graph.set("jack", "rose", 1);
    	assertTrue(graph.targets("jack").containsKey("rose"));
    	graph.remove("rose");
    	assertFalse(graph.targets("jack").containsKey("rose"));
    }
    
    @Test
    public void testGetIndex() {
    	ConcreteVerticesGraph<String> graph = new ConcreteVerticesGraph<>();
    	graph.add("jack");
    	assertEquals(0, graph.getIndex("jack"));
    	assertEquals(-1, graph.getIndex("rose"));
    }
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // tests for ConcreteVerticesGraph.toString()
    @Test
    @Override public void testToString() {
    	Graph<String> graph = new ConcreteVerticesGraph<>();
    	graph.add("jack");
    	graph.add("rose");
    	graph.add("tom");
    	graph.set("jack", "rose", 1);
    	graph.set("rose", "tom", 1);
    	assertEquals("jack==>rose\nrose==>tom", graph.toString());
    }
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   TODO
    
    // tests for operations of Vertex
    @Test
    public void testVertex() {
    	Vertex<String> vertex = new Vertex<>("hello");
    	vertex.setWeight("world", 3);
    	vertex.setWeight("world", 2);
    	assertTrue(vertex.getTargetMap().containsKey("world"));
    	assertEquals("hello", vertex.getLabel());
    }
    
}
