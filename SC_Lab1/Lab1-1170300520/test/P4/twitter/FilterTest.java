/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

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
	 * Test writtenBy() method using a list of tweets and a username
	 */
	@Test
	public void testWrittenBy() {
		List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), "Google");
		assertEquals("expected singleton list", 1, writtenBy.size());
		assertTrue("expected list to contain tweet", writtenBy.contains(tweet4));
		List<Tweet> writtenBy2 = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), "BillGates");
		assertEquals("expected empty list", 0, writtenBy2.size());
	}
	
	/*
	 * Test inTimespan() method using a list of tweets and a timespan
	 */
	@Test
	public void testInTimespan() {
		Instant testStart = Instant.parse("2018-01-01T09:00:00Z");
		Instant testEnd = Instant.parse("2019-03-01T21:21:00Z");
		List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), new Timespan(testStart, testEnd));
		assertFalse("expected non-empty list", inTimespan.isEmpty());
		assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet3, tweet4)));
		assertEquals("expected size", 2, inTimespan.size());
	}

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testWrittenByMultipleTweetsSingleResult() {
		List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");

		assertEquals("expected singleton list", 1, writtenBy.size());
		assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
	}

	@Test
	public void testInTimespanMultipleTweetsMultipleResults() {
		Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
		Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");

		List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));

		assertFalse("expected non-empty list", inTimespan.isEmpty());
		assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
		assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
	}
	
	/*
	 * test containing() method
	 */
	@Test
	public void testContaining() {
		List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));

		assertFalse("expected non-empty list", containing.isEmpty());
		assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
		assertEquals("expected same order", 0, containing.indexOf(tweet1));
	}

	/*
	 * Warning: all the tests you write here must be runnable against any Filter
	 * class that follows the spec. It will be run against several staff
	 * implementations of Filter, which will be done by overwriting (temporarily)
	 * your version of Filter with the staff's version. DO NOT strengthen the spec
	 * of Filter or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own that
	 * you have put in Filter, because that means you're testing a stronger spec
	 * than Filter says. If you need such helper methods, define them in a different
	 * class. If you only need them in this test class, then keep them in this test
	 * class.
	 */

}
