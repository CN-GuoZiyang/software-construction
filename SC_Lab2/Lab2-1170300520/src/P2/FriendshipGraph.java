package P2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import P1.graph.ConcreteVerticesGraph;
import P1.graph.Graph;

/**
 * the 2nd version of the implement of FriendshipGraph
 * 
 * @author Guo Ziyang
 */
public class FriendshipGraph {
	private Graph<Person> graph = new ConcreteVerticesGraph<>();
	private Map<Person, Boolean> visited = new HashMap<>();
	private Map<Person, Integer> depthMap = new HashMap<>();
	
	public void checkRep() {
		assert graph != null;
		assert visited != null;
		assert depthMap != null;
	}
	
	/**
	 * add a vertex to the graph
	 * 
	 * @param person
	 */
	public void addVertex(Person person) {
		checkRep();
		graph.add(person);
	}
	
	/**
	 * add a directed edge to the specified people in the graph
	 * 
	 * @param person1
	 * @param person2
	 */
	public void addEdge(Person person1, Person person2) {
		checkRep();
		graph.set(person1, person2, 1);
	}
	
	/**
	 * get the shortest distance between the given two people
	 * 
	 * @param person1
	 * @param person2
	 * @return the distance
	 */
	public int getDistance(Person person1, Person person2) {
		checkRep();
		if(person1 == person2) {
			return 0;
		}
		visited.clear();
		Iterator<Person> iterator = graph.vertices().iterator();
		while(iterator.hasNext()) {
			visited.put(iterator.next(), false);
		}
		Queue<Person> queue = new LinkedList<>();
		queue.add(person1);
		depthMap.put(person1, 1);
		Person tempPerson = null;
		Boolean flag = false;
		while(!queue.isEmpty()) {
			tempPerson = queue.remove();
			visited.put(tempPerson, true);
			if(tempPerson == person2) {
				flag = true;
				return depthMap.get(tempPerson);
			}
			iterator = graph.targets(tempPerson).keySet().iterator();
			if(graph.targets(tempPerson).containsKey(person2)) {
				flag = true;
				break;
			} else {
				while(iterator.hasNext()) {
					Person temp = iterator.next();
					if(visited.get(temp) == false) {
						queue.add(temp);
						depthMap.put(temp, depthMap.get(tempPerson) + 1);
					}
				}
			}
		}
		if(flag == false) {
			return -1;
		} else {
			return depthMap.get(tempPerson);
		}
	}
	
	public static void main(String[] args) throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		//should print 1
		System.out.println(graph.getDistance(rachel, ben));
		//should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		//should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		//should print -1
	}

}
