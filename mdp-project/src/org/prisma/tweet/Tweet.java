/**
 * 
 */
package org.prisma.tweet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author ommirandap
 *
 */
public class Tweet {

	/* Tweets values from tsv file
	 * Timestamp: Sat Nov 01 00:20:52 +0000 2014	
	 * userID: 90227660	
	 * tweetID: 528340863387459584	
	 * text: rt @carocarcamoh: pronto en @24horastvn la importancia de #losbaileschinos su tradicion, y su posible reconocimiento mundial cc @jumastorga...
	 * */
	
	private Date timestamp;
	private long tweetID;
	private long userID;
	private String text;
	
	public Tweet(String timestamp, String userID, String tweetID, String text){
		this.timestamp = processTime_date(timestamp);
		this.userID = Long.parseLong(userID);
		this.tweetID = Long.parseLong(tweetID);
		this.text = text;
	}
	
	public long getTweetID() {
		return tweetID;
	}

	public long getUserID() {
		return userID;
	}

	public String getText() {
		return text;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public Date processTime_date(String inputTimestamp){
		
		DateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
		inputFormat.setLenient(true);
		
		Date date;

		try {
			date = inputFormat.parse(inputTimestamp);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}
}
