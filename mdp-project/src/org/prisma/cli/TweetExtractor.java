/**
 * 
 */
package org.prisma.cli;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.prisma.tweet.*;

/**
 * @author ommirandap
 *
 */
public class TweetExtractor {

	private static final long TICKS = 10000;


	static public ArrayList<Tweet> ExtractTweets(String tweetFile) throws IOException{
		
		InputStream is = new FileInputStream(tweetFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8));
		
		ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
		
		String line;
		long linesReaded = 0;
		while((line = br.readLine())!=null){
			linesReaded++;
			if (linesReaded % TICKS == 0)
				System.err.println(linesReaded + " lines already read");
		
			Tweet headline = tweetFromLine(line);
			if(headline != null){
				tweetList.add(headline);
			}
		}
	
		br.close();

		return tweetList;
		
		
	}


public static Tweet tweetFromLine(String line){
	
	String[] fields = line.split("\t");
	if (fields.length == 5) {
		Tweet t = new Tweet(fields[0], fields[1], fields[2], fields[3], fields[4]);
		return t;
	}

	return null;
	
}


}