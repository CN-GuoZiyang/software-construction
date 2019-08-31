package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 朋友圈有向图
 * 
 * @author GuoZiyang
 * @date 2019/2/26
 */
public class FriendshipGraph {
	private List<Person> personList;
	private List<List<Integer>> personGraph;
	
	public FriendshipGraph() {
		personList = new ArrayList<>();
		personGraph = new ArrayList<>();
	}
	
	/**
	 * 在图中添加点（Person）
	 * 
	 * @param person 被添加点人
	 * @throws Exception 重复添加点异常
	 */
	public void addVertex(Person person) throws Exception {
		for(Person singlePerson : personList) {
			if(singlePerson.getName().equals(person.getName())) {
				throw new Exception("不可重复添加相同名称的Person！");
			}
		}
		personList.add(person);
		expandGraphByOne();
	}
	
	private void expandGraphByOne() {
		List<Integer> newLine = new ArrayList<>();
		for(int i = 0; i < personGraph.size() + 1; i ++) {
			newLine.add(0);
		}
		for(int i = 0; i < personGraph.size(); i ++) {
			personGraph.get(i).add(0);
		}
		personGraph.add(newLine);
	}
	
	/**
	 * 在图中添加一条有向边
	 * 
	 * @param person1 有向边起点
	 * @param person2 有向边终点
	 */
	public void addEdge(Person person1, Person person2) {
		int number1 = personList.indexOf(person1);
		int number2 = personList.indexOf(person2);
		personGraph.get(number1).set(number2, 1);
	}
	
	/**
	 * 获取两个点的最短距离
	 * 
	 * @param person1  起始点
	 * @param person2 目标点
	 * @return 返回最短距离，当两点无路时返回-1
	 */
	public int getDistance(Person person1, Person person2) {
		if(person1 == person2) {
			return 0;
		}
		int number1 = personList.indexOf(person1);
		int number2 = personList.indexOf(person2);
		return dfs(personGraph, number1, number2);
	}
	
	private int dfs(List<List<Integer>> graph, int start, int end) {
		ArrayList<Integer> queue = new ArrayList<>();
		boolean[] isVisited = new boolean[graph.size()];
		Map<Integer, Integer> lengthMap = new HashMap<>();
		
		int head = 0;
		int tail = 0;
		int count = 0;
		queue.add(start);
		tail ++;
		isVisited[start] = true;
		while(head < tail) {
			boolean flag = false;
			int current = queue.get(head);
			for(int i = 0; i < graph.size(); i ++) {
				if(graph.get(current).get(i) == 1 && !isVisited[i]) {
					queue.add(i);
					tail ++;
					isVisited[i] = true;
					lengthMap.put(i, count + 1);
					flag = true;
				}
				if(tail == graph.size()) {
					break;
				}
			}
			if(flag) {
				count ++;
			}
			head ++;
		}
		if(!lengthMap.containsKey(end)) {
			return -1;
		} else {
			return lengthMap.get(end);
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
