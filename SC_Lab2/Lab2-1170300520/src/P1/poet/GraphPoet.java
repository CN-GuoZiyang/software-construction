/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 * 
 * @author Guo Ziyang
 */
public class GraphPoet {
    /**
     * the graph that contains the relation between edges
     */
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   the abstraction of a poem generator based on a certain corpus string 
    // Representation invariant:
    //   the graph restore the relation of the words from the corpus
    // Safety from rep exposure:
    //   all fields are priavte and final
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
    	checkRep();
    	BufferedReader reader = new BufferedReader(new FileReader(corpus));
    	List<String> corpusList = new ArrayList<>();
    	String line = null;
    	while((line = reader.readLine()) != null) {
    		for(String word : line.split("\\s")) {
    			corpusList.add(word.toLowerCase());
    		}
    	}
    	reader.close();
    	Iterator<String> iterator = corpusList.iterator();
    	while(iterator.hasNext()) {
    		graph.add(iterator.next());
    	}
    	for(int i = 0; i < corpusList.size() - 1; i ++) {
    		String sourceString = corpusList.get(i);
    		String targetString = corpusList.get(i + 1);
    		Map<String, Integer> targetsMap = graph.targets(sourceString);
    		if(targetsMap.containsKey(targetString)) {
    			graph.set(sourceString, targetString, targetsMap.get(targetString) + 1);
    		} else {
    			graph.set(sourceString, targetString, 1);
    		}
    	}
    }
    
    /**
     * check the representation invariant
     */
    public void checkRep() {
    	assert graph != null;
    }
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
    	checkRep();
    	String[] inputArray = input.split("\\s");
    	List<String> resultList = new ArrayList<>();
    	for(int i = 0; i < inputArray.length - 1; i ++) {
    		resultList.add(inputArray[i]);
    		String sourceString = inputArray[i].toLowerCase();
    		String targetString = inputArray[i + 1].toLowerCase();
    		Map<String, Integer> bridgeCandidates = new HashMap<>();
    		Map<String, Integer> targetMap = graph.targets(sourceString);
    		Map<String, Integer> sourceMap = graph.sources(targetString);
    		for(String middleString : targetMap.keySet()) {
    			if(sourceMap.containsKey(middleString)) {
    				bridgeCandidates.put(middleString, sourceMap.get(middleString) + targetMap.get(middleString));
    			}
    		}
    		if(bridgeCandidates.size() == 0) {
    			if(i == inputArray.length - 2) {
    				resultList.add(inputArray[i + 1]);
    			}
    			continue;
    		} else {
    			Set<Entry<String, Integer>> entrySet = bridgeCandidates.entrySet();
    			int largestWeight = 0;
    			String bridgeString = null;
    			Iterator<Entry<String, Integer>> iterator = entrySet.iterator();
    			while(iterator.hasNext()) {
    				Entry<String, Integer> tempEntry = iterator.next();
    				if(tempEntry.getValue() > largestWeight) {
    					largestWeight = tempEntry.getValue();
    					bridgeString = tempEntry.getKey();
    				}
    			}
    			resultList.add(bridgeString);
    			if(i == inputArray.length - 2) {
    				resultList.add(inputArray[i + 1]);
    			}
    		}
    	}
    	return resultList.stream().collect(Collectors.joining(" "));
    }
    
    @Override public String toString() {
    	return graph.toString();
    }
    
}
