/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2019-02-28T17:15:00Z");
    private static final Instant d4 = Instant.parse("2019-03-01T17:27:00Z");
    private static final Instant d5 = Instant.parse("2015-10-30T12:40:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "realDonaldTrump", "I will be interviewed by @seanhannity at 9:00 P.M. on @FoxNews. Enjoy!", d3);
    private static final Tweet tweet4 = new Tweet(4, "Google", "This #BlackHistoryMonth, we took a journey through stories that have helped shape the American experience. Tap to explore #TheJourneyOfUs.", d4);
    private static final Tweet tweet5 = new Tweet(5, "BarackObama", "Passionate student organizers came together at @OFA's Campus Organizing Summit. Find out more:", d5);
    
    /*
     * Test getTimespan() method using a list of tweets 
     */
    @Test
    public void testGetTimespan() {
    	Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
    	assertEquals("testGetTimespan() getStart error", d5, timespan.getStart());
    	assertEquals("testGetTimespan() getStart error", d4, timespan.getEnd());
    }
    
    /*
     * Test getMentionedUsers() method using a list of tweets
     */
    @Test
    public void testGetMentionedUsers() {
    	Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
    	assertEquals(3, mentionedUsers.size());
    	assertTrue(mentionedUsers.contains("seanhannity"));
    	assertTrue(mentionedUsers.contains("FoxNews"));
    	assertTrue(mentionedUsers.contains("OFA"));
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
