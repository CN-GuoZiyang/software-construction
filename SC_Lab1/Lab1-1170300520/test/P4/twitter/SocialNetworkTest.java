/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {
	
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2019-02-28T17:15:00Z");
	private static final Instant d4 = Instant.parse("2019-03-01T17:27:00Z");
	private static final Instant d5 = Instant.parse("2015-10-30T12:40:00Z");

	private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much? @Google", d1);
	private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype @realDonaldTrump #BlackHistoryMonth", d2);
	private static final Tweet tweet3 = new Tweet(3, "realDonaldTrump", "I will be interviewed by @seanhannity at 9:00 P.M. on @FoxNews. Enjoy! @BarackObama", d3);
	private static final Tweet tweet4 = new Tweet(4, "Google", "This #BlackHistoryMonth, we took a journey through stories that have helped shape the American experience. @BarackObama Tap to explore #TheJourneyOfUs.", d4);
	private static final Tweet tweet5 = new Tweet(5, "BarackObama", "Passionate student organizers came together at @OFA's Campus Organizing Summit. Find out more on @Google:", d5);
	
	/*
	 * Test guessFollowsGraph() using a list of tweets
	 */
    @Test
    public void testGuessFollowsGraph() {
    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
    	assertEquals(8, followsGraph.size());
    	assertTrue(followsGraph.get("Google").contains("BarackObama"));
    }
    
    /*
     * Test influencers() using a list of tweets
     */
    @Test
    public void testInfluencers() {
    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
    	List<String> influencers = SocialNetwork.influencers(followsGraph);
    	assertEquals(0, influencers.indexOf("Google"));
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
