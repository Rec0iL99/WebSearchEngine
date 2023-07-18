package main;
import java.util.*;
import java.io.*;
import algorithms.*;

public class SearchEngine {

	public static void main(String[] args) throws IOException {
		// web crawler logic will go here
		String crawlFrom = "https://en.wikipedia.org";
		WebCrawler.websiteToCrawl(5, crawlFrom, new ArrayList<String>());
		final File htmlpages = new File("/Users/joelmathew/eclipse-workspace/WebSearchEngine/src/HTMLFiles");
		for (final File files : htmlpages.listFiles()) 
		{
			WebCrawler.htmlToTextConversion(files);
		}
		
		System.out.println("Done converting all the files");
		
		System.out.println("--------------------------------Job Search Engine--------------------------------");
		System.out.println("What job are you currently searching for?");
		Scanner scanner = new Scanner(System.in);
		String searchedKey = scanner.nextLine();
		
		System.out.println("\n");
		
		ArrayList<String> dictionary = new ArrayList<String>();
		// converting the english words dictionary into an arraylist
		dictionary = SpellCheck.createDictionary();
		
		
		ArrayList<String> suggestions = new ArrayList<String>();
		
		if (dictionary.contains(searchedKey)) {
			System.out.print("word found --- test");
			
		} else {
			System.out.println("Word searched not found");
			suggestions = SpellCheck.giveSuggestions(searchedKey);
			
			System.out.println(suggestions.toString());
			
			// could find only one suggestion
			if(suggestions.size() == 0) {
    			System.out.println("Your search - " + searchedKey + " - did not match any documents.");
    		} else if (suggestions.size() == 1) {
				System.out.println("Did you mean to search for \"" + suggestions.get(0) + "\"?");
			} else if (suggestions.size() >= 2){
    			System.out.println("Did you mean to search for \"" + suggestions.get(0) + "\" or \"" + suggestions.get(1)  + "\"?");
    		}
		}
		
		// autosuggestion code goes here
		// Trie logic
		WordSuggestion.suggest(searchedKey);
		
		// pattern matching
		// reading the whole folder for converted html to txt files
		File folder = new File("/Users/joelmathew/eclipse-workspace/WebSearchEngine/src/HTMLToTextFiles");
		File[] htmlToTxtFiles = folder.listFiles();
		Hashtable<String, Integer> numberOfOccurences = new Hashtable<String, Integer>();
		BoyerMoore booyerMoore = new BoyerMoore(searchedKey.toLowerCase());
		
		for (File file : htmlToTxtFiles) {
			if (file.isFile()) {
            	BufferedReader inputStream = null;
            	String line;
            	try {
            		inputStream = new BufferedReader(new FileReader(file));
            		int count=0;
            		while ((line = inputStream.readLine()) != null) {
            			while(true) {
            				int offset=booyerMoore.search(line.toLowerCase());
            				if (offset!=line.length()) {
            					count++;
            					line=line.substring(offset+searchedKey.length());	        		
                    	  }
                    	  if (offset==line.length()) {
                    		  break;
                    	  }
                      }
                  }
                  
                  if(count>0) {
                	numberOfOccurences.put(file.getName(), count);
                  	System.out.println("\nThe number of occurrences of "+searchedKey+" in file "+file.getName()+" are "+count);
                  }
              }catch(IOException e) {
              	System.out.println(e);
              }
              finally {
                  if (inputStream != null) {
                      inputStream.close(); // throws IOException
                  }
              }
            }
        }
		
	}
		

}
