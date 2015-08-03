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
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.prisma.tweet.Tweet;

/**
 * @author ommirandap
 *
 */
public class IndexTweets {

	public enum FieldNames {
		TIMESTAMP, USERID, TWEETID, TEXT, USERNAME
	}
	
	public final static int TICKS = 10000;
	
	public static void main(String []args) throws IOException{
		
		final Path indexDirPath = Paths.get(args[1]);
		
		InputStream is = new FileInputStream(args[0]);
		BufferedReader br = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8));
		indexTweets(br, indexDirPath);
		br.close();
	}
	
	
	public static void indexTweets(BufferedReader input, Path indexDir) throws IOException{
		
		/* TODO Stopwords
		String sw[] = {"desde", "la", "de"};
		List<String> c = Arrays.asList(sw);
		CharArraySet stopwords = new CharArraySet(c, true);
		Analyzer analyzer = new StandardAnalyzer();		
		*/

		Directory dir = FSDirectory.open(indexDir);
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		iwc.setOpenMode(OpenMode.CREATE);
		
		IndexWriter writer = new IndexWriter(dir, iwc);
		
		String line;
		long linesReaded = 0;
		while((line = input.readLine())!=null){
			linesReaded++;
			if (linesReaded % TICKS == 0)
				System.err.println(linesReaded + " lines already read");
			
			Tweet headline = tweetFromLine(line);
			if(headline != null){
				addTweetToIndex(headline, writer);
			}
		}
		
		writer.close();

	}


	private static void addTweetToIndex(Tweet headline, IndexWriter writer) throws IOException {
		
		Document doc = new Document();
		Field timestamp = new LongField(FieldNames.TIMESTAMP.name(), headline.getTimestamp().getTime(), Field.Store.YES);
		Field tweetID = new LongField(FieldNames.TWEETID.name(), headline.getTweetID(), Field.Store.NO);
		Field userName = new StringField(FieldNames.USERNAME.name(), headline.getUserName(), Store.YES);
		Field userID = new LongField(FieldNames.USERID.name(), headline.getUserID(), Field.Store.NO);
		Field tweet = new TextField(FieldNames.TEXT.name(), headline.getText(), Field.Store.YES);

		doc.add(tweet);
		doc.add(tweetID);
		doc.add(userName);
		doc.add(userID);
		doc.add(timestamp);
		
		writer.addDocument(doc);
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
