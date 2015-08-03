/**
 * 
 */
package org.prisma.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.prisma.tweet.Tweet;

/**
 * @author ommirandap
 *
 */
public class CountWords {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		HashMap<String, ArrayList<Tweet>> counterHash = new HashMap<String, ArrayList<Tweet>>();
		
		ArrayList<Tweet> allTweets = TweetExtractor.ExtractTweets(args[0]);
		
		for (Tweet localTweet : allTweets) {
			if (!counterHash.containsKey(localTweet.getUserName()))
			{
				ArrayList<Tweet> tweetsSameAccount = new ArrayList<Tweet>();
				counterHash.put(localTweet.getUserName(), tweetsSameAccount);
			}
			else {
				counterHash.get(localTweet.getUserName()).add(localTweet);
			}
		 
		
		
	}
	}}
