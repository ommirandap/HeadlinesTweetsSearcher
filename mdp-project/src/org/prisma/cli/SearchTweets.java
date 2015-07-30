package org.prisma.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;


public class SearchTweets {

	private static final int DOCS_PER_PAGE = 15;

	static public void main(String []args) throws IOException, ParseException{
		
		
		Path f = Paths.get(args[0]);
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(f));
		IndexSearcher searcher = new IndexSearcher(reader);
		StandardAnalyzer analyzer = new StandardAnalyzer();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		
		while (true) {
			
			System.out.println("Enter a keyword search phrase:");
			String line = br.readLine();

			if(line != null){
				line = line.trim().toLowerCase();
				
				if(!line.isEmpty()){
					
					ScoreDoc []results = searchTweets(line, searcher, analyzer);
					showResults(results, searcher);
					
				}
			}
			else
				break;
		}
		
		reader.close();
		analyzer.close();
		br.close();
		
	}
	
	
	public static ScoreDoc[] searchTweets(String query, IndexSearcher searcher, StandardAnalyzer analyzer) throws ParseException, IOException{
		
		String field = IndexTweets.FieldNames.TEXT.name();
		QueryParser parser = new QueryParser(field, analyzer);
		Query q = parser.parse(query);
		
		TopDocs result = searcher.search(q, DOCS_PER_PAGE);
		ScoreDoc[] hits = result.scoreDocs;
				
		return hits;
		
	}
	
	public static void showResults(ScoreDoc []docResults, IndexSearcher searcher) throws IOException{
		
		
		ArrayList<String[]> results = new ArrayList<String[]>();
		
		for (int i = 0; i < docResults.length; i++) {
			Document doc = searcher.doc(docResults[i].doc);
			String text = doc.get(IndexTweets.FieldNames.TEXT.name());
			String userID = doc.get(IndexTweets.FieldNames.USERID.name());
			results.add(new String[]{userID, text});
		}
		
		for (String[] strings : results) {
			System.out.printf("USERID: %s\t TEXT: %s\n", strings[0], strings[1]);
		}
		
		
		
	}
	
}
