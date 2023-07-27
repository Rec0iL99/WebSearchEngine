package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebCrawler {
	public static Document requestAccess(String url, ArrayList<String> v) {
		try {
			Connection conToUrl = Jsoup.connect(url);
			Document doc = conToUrl.get();
			
			if(conToUrl.response().statusCode() == 200) {
				System.out.println("Link: " + url);
				System.out.println(doc.title());
				String title = doc.title();
				String modifiedTitle = title.replaceAll("[:*?\"<>|]", " ");
				URL urlToWrite = new URL(url);
				BufferedReader reader = new BufferedReader(new InputStreamReader(urlToWrite.openStream()));
				BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/joelmathew/eclipse-workspace/WebSearchEngine/src/HTMLFiles/"+modifiedTitle+".html"));
				String line;
				while ((line = reader.readLine()) != null){
					writer.write(line);
				}
				reader.close();
				writer.close();
				v.add(url);
				return doc;
			}
			return null;
		}
		catch(IOException excep) {
			return null;
		}
	}
	
	public static void websiteToCrawl(int level, String url, ArrayList<String> visited) {
		if (level <= 5) { //decide how many layers of crawling must be done
			Document doc = requestAccess(url, visited);
			if (doc!=null) {
				for(Element link : doc.select("a[href]")) {
					String nextLink = link.absUrl("href");			
					if(visited.contains(nextLink)== false) {
						websiteToCrawl(level++, nextLink, visited);
					}
				}
			}
		}
	}
	
	public static void htmlToTextConversion(final File files) {
		try {
			FileWriter filewriter;{
	    		File webpage = new File("/Users/joelmathew/eclipse-workspace/WebSearchEngine/src/HTMLFiles/"+files.getName());
	    		Document d = Jsoup.parse(webpage,"UTF-8");
	    		filewriter=new FileWriter("/Users/joelmathew/eclipse-workspace/WebSearchEngine/src/HTMLToTextFiles/"+files.getName()+".txt");    
	    		filewriter.write(d.text());  
	    		filewriter.close();    
	    	}
		}
		catch (Exception e){
		   System.out.println(e);
		}
	}
}
