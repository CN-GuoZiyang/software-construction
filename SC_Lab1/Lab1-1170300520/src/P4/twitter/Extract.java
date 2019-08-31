/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
    	Instant startInstant = tweets.get(0).getTimestamp();
    	Instant endInstant = tweets.get(0).getTimestamp();
    	for(Tweet tweet : tweets) {
    		Instant tempInstant = tweet.getTimestamp();
    		if(tempInstant.isBefore(startInstant)) {
    			startInstant = tempInstant;
    		} else if(tempInstant.isAfter(endInstant)) {
    			endInstant = tempInstant;
    		}
    	}
    	return new Timespan(startInstant, endInstant);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
    	String patternStr = "(?<![A-Za-z0-9_-])@[A-Za-z0-9_-]{1,140}(?![A-Za-z0-9_-])";
    	Set<String> resultSet = new HashSet<>();
    	Pattern pattern = Pattern.compile(patternStr);
    	for(Tweet tweet : tweets) {
    		Matcher matcher = pattern.matcher(tweet.getText());
    		while(matcher.find()) {
    			resultSet.add(matcher.group().substring(1));	
    		}
    	}
    	return resultSet;
    }

}
