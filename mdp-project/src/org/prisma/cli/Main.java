/**
 * 
 */
package org.prisma.cli;

import java.util.Date;
import java.util.TimeZone;

import org.prisma.tweet.*;

/**
 * @author ommirandap
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String line = "Sat Nov 01 00:30:08 +0000 2014	3222731	528343193503674368	la academia revelo los 134 documentales que participan en la carrera por el oscar http://t.co/utqvthhecu http://t.co/amjorrcusd";
		String line2 = "Sat Nov 01 01:30:08 +0000 2014	3222731	528343193503674368	la academia revelo los 134 documentales que participan en la carrera por el oscar http://t.co/utqvthhecu http://t.co/amjorrcusd"; 
		Tweet t = processLine(line);
		Tweet t2 = processLine(line2);
		System.out.println(t.getText());
		System.out.println(t.getUserID());
		System.out.println(t.getTweetID());
		System.out.println("Date " + t.getTimestamp());
		
		Date d1 = t.getTimestamp();
		Date d2 = t2.getTimestamp();
		System.out.println(d1);
		System.out.println(d2);
		System.out.println(d1.compareTo(d2));
		
		
	}

	public static Tweet processLine(String line){
		
		String[] fields = line.split("\t");
		Tweet t = new Tweet(fields[0], fields[1], fields[2], fields[3], fields[4]);
		return t;
		
	}
	
}
