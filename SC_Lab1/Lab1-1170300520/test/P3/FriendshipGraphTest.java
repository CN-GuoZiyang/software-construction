package P3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FriendshipGraphTest {
	
	/**
	 * 用一个图测试getDistance()方法
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetDistance() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("A");
		Person b = new Person("B");
		Person c = new Person("C");
		Person d = new Person("D");
		Person e = new Person("E");
		Person f = new Person("F");
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		graph.addEdge(a, c);
		graph.addEdge(c, a);
		graph.addEdge(a, d);
		graph.addEdge(d, a);
		graph.addEdge(b, e);
		graph.addEdge(e, b);
		graph.addEdge(c, f);
		graph.addEdge(f, c);
		graph.addEdge(d, e);
		graph.addEdge(e, d);
		assertEquals(1, graph.getDistance(a, d));
		assertEquals(2, graph.getDistance(b, d));
		assertEquals(4, graph.getDistance(f, e));
		assertEquals(5, graph.getDistance(b, f));
	}
	
	/**
	 * 用重复添加测试添加时抛出异常
	 * 
	 * @throws Exception 重复添加异常
	 */
	@Test(expected = java.lang.Exception.class)
	public void testAddVertex() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("A");
		graph.addVertex(a);
		graph.addVertex(a);
	}
}
