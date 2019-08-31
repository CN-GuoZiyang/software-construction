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
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 * 
 * @author Guo Ziyang
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    @Test
    @Override public void testAdd() {
    	Graph<String> graph = new ConcreteEdgesGraph<>();
    	assertTrue(graph.add("hello"));
    	assertTrue(graph.add("world"));
    	assertFalse(graph.add("hello"));
    }
    
    @Test
    @Override public void testSet() {
    	Graph<String> graph = new ConcreteEdgesGraph<>();
    	graph.add("jack");
    	graph.add("rose");
    	graph.add("tom");
    	graph.set("jack", "rose", 1);
    	graph.set("rose", "tom", 1);
    	graph.set("hello", "world", 2);
    	graph.set("hello", "world", 1);
    	graph.set("hello", "world", -1);
    	graph.set("a", "b", -2);
    	assertEquals("jack==>rose\nrose==>tom", graph.toString());
    }
    
    @Test
    @Override public void testRemove() {
    	Graph<String> graph = new ConcreteEdgesGraph<>();
    	graph.add("jack");
    	assertTrue(graph.remove("jack"));
    	assertFalse(graph.remove("jack"));
    }
    
    @Test
    @Override public void testVertices() {
    	Graph<String> graph = new ConcreteEdgesGraph<>();
    	graph.add("jack");
    	assertTrue(graph.vertices().contains("jack"));
    	graph.remove("jack");
    	assertEquals(Collections.EMPTY_SET, graph.vertices());
    }
    
    @Test
    @Override public void testSources() {
    	Graph<String> graph = new ConcreteEdgesGraph<>();
    	graph.add("jack");
    	graph.add("rose");
    	graph.set("jack", "rose", 1);
    	assertTrue(graph.sources("rose").containsKey("jack"));
    	graph.remove("jack");
    	assertFalse(graph.sources("rose").containsKey("jack"));
    }
    
    @Test
    @Override public void testTargets() {
    	Graph<String> graph = new ConcreteEdgesGraph<>();
    	graph.add("jack");
    	graph.add("rose");
    	graph.set("jack", "rose", 1);
    	assertTrue(graph.targets("jack").containsKey("rose"));
    	graph.remove("rose");
    	assertFalse(graph.targets("jack").containsKey("rose"));
    }
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    // TODO
    
    // tests for ConcreteEdgesGraph.toString()
    @Test
    @Override public void testToString() {
    	Graph<String> graph = new ConcreteEdgesGraph<>();
    	graph.add("jack");
    	graph.add("rose");
    	graph.add("tom");
    	graph.set("jack", "rose", 1);
    	graph.set("rose", "tom", 1);
    	assertEquals("jack==>rose\nrose==>tom", graph.toString());
    }
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   TODO
    
    // tests for operations of Edge
    @Test
    public void testEdge() {
    	Edge<String> edge = new Edge<>("jack", "rose", 2);
    	assertEquals("jack", edge.getSource());
    	assertEquals("rose", edge.getTarget());
    	assertEquals(2, edge.getWeight());
    }
    
}
