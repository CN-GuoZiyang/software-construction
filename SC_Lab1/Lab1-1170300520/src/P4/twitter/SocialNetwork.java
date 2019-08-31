/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
    	/*
    	 * A @-mentioned B is the evidence of that A following B
    	 */
    	Map<String, Set<String>> resultMap = new HashMap<>();
    	for(Tweet tweet : tweets) {
    		String author = tweet.getAuthor();
    		if(resultMap.get(author) == null) {
    			resultMap.put(author, new HashSet<String>());
    		}
    		Set<String> set = resultMap.get(author);
    		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet));
    		set.addAll(mentionedUsers);
    		for(String mentionedUser : mentionedUsers) {
    			if(resultMap.get(mentionedUser) == null) {
    				resultMap.put(mentionedUser, new HashSet<String>());
    			}
    		}
    	}
    	/*
    	 * A and B #-talked about the same topic also means that they may follow each other
    	 */
    	String patternStr = "(?<![A-Za-z0-9_-])#[A-Za-z0-9_-]+(?![A-Za-z0-9_-])";
    	Pattern pattern = Pattern.compile(patternStr);
    	Set<String> topicSet = new HashSet<>();
    	for(Tweet tweet : tweets) {
    		Matcher matcher = pattern.matcher(tweet.getText());
    		while(matcher.find()) {
    			topicSet.add(matcher.group());	
    		}
    	}
    	for(String topic : topicSet) {
    		Set<String> userSet = new HashSet<>();
    		for(Tweet tweet : tweets) {
    			if(tweet.getText().contains(topic)) {
    				if(resultMap.get(tweet.getAuthor()) == null) {
    	    			resultMap.put(tweet.getAuthor(), new HashSet<String>());
    	    		}
    				userSet.add(tweet.getAuthor());
    			}
    		}
    		List<String> userList = new ArrayList<>(userSet);
    		for(int i = 0; i < userList.size(); i ++) {
    			String user1 = userList.get(i);
    			for(int j = i + 1; j < userList.size(); j ++) {
    				String user2 = userList.get(j);
    				resultMap.get(user1).add(user2);
    				resultMap.get(user2).add(user1);
    			}
    		}
    	}
    	return resultMap;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
    	Map<String, Integer> influenceMap = new HashMap<>();
    	/*
    	 * 添加所有作者
    	 */
    	for(String username : followsGraph.keySet()) {
    		influenceMap.put(username, 0);
    	}
    	/*
    	 * 添加所有被follow者
    	 */
    	for(Set<String> followSet : followsGraph.values()) {
    		Iterator<String> iterator = followSet.iterator();
    		while(iterator.hasNext()) {
    			String followedUsername = iterator.next();
    			if(!influenceMap.containsKey(followedUsername)) {
    				influenceMap.put(followedUsername, 0);
    			}
    		}
    	}
    	/*
    	 * 遍历所有的username以计数
    	 */
    	for(String username : influenceMap.keySet()) {
    		for(Set<String> followSet : followsGraph.values()) {
        		if(followSet.contains(username)) {
        			influenceMap.put(username, influenceMap.get(username) + 1);
        		}
        	}
    	}
    	List<Map.Entry<String, Integer>> maplist = new ArrayList<>(influenceMap.entrySet());
    	Collections.sort(maplist, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
    	List<String> resultList = new ArrayList<>();
    	for(Map.Entry<String, Integer> entry : maplist) {
    		resultList.add(entry.getKey());
    	}
    	Collections.reverse(resultList);
    	return resultList;
    }

}
