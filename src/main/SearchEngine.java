package main;
import java.util.*;
import java.io.*;
import algorithms.*;

public class SearchEngine {

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		String crawlFrom = "https://www.cnn.com";
		WebCrawler.websiteToCrawl(5, crawlFrom, new ArrayList<String>());
		final File htmlpages = new File("/Users/joelmathew/eclipse-workspace/WebSearchEngine/src/HTMLFiles");
		for (final File files : htmlpages.listFiles()) 
		{
			WebCrawler.htmlToTextConversion(files);
		}
		
		System.out.println("--Web crawling and converting files done--\n");
		
		boolean quit = false;
		char searchEngineChoice = 'Y';
		
		while (quit == false) {
			switch (searchEngineChoice) {
				case 'y':
				case 'Y':
					System.out.println("---------------------------------------------------------------News Search Engine----------------------------------------------------------------------");
					System.out.println("What news search key are you currently searching for?");
					String searchedKey = scanner.nextLine();
					
					System.out.println("\n");
					
					ArrayList<String> dictionary = new ArrayList<String>();
					// converting the english words dictionary into an arraylist
					dictionary = SpellCheck.createDictionary();
					
					
					ArrayList<String> suggestions = new ArrayList<String>();
					
					if (dictionary.contains(searchedKey)) {
						// no need to print anything
					} else {
						System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
						System.out.println("Oops, looks like you mispelled the word");
						suggestions = SpellCheck.giveSuggestions(searchedKey);
						
						System.out.println("Did you mean to search for any of the following?");
						for (String suggestion : suggestions) {
							System.out.println(suggestion);
						}
						System.out.println("");
						
						if (suggestions.size() > 0) {
							System.out.println("Do you want to search for any of the suggested spell checked words or continue with your original search key? [Y/N]");
							char userChoice = scanner.next().charAt(0);
							scanner.nextLine();
							
							switch (userChoice) {
								case 'y':
								case 'Y':
									System.out.println("Enter a word from the suggested spell checked words: \n");
									searchedKey = scanner.nextLine();
									break;
								case 'n':
								case 'N':
								default:
									System.out.println("Sure, we will continue searching for the \"" + searchedKey + "\"");
									break;
							}
						}
					}
					
					WordSuggestion.suggest(searchedKey);
					
					// pattern matching
					// reading the whole folder for converted html to txt files
					File folder = new File("/Users/joelmathew/eclipse-workspace/WebSearchEngine/src/HTMLToTextFiles");
					File[] htmlToTxtFiles = folder.listFiles();
					Map<String, Integer> numberOfOccurences = new HashMap<String, Integer>();
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
					List<Map.Entry<String, Integer>> sortedNumberOfOccurences = new ArrayList<>(numberOfOccurences.entrySet());

			        // Step 2: Sort the list of tuples in descending order of the count.
			        Collections.sort(sortedNumberOfOccurences, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

			        // Step 3: Print the dict in descending order of the count.
			        //System.out.println("\nFiles with occurrences of " + userInput + " in descending order of count:");
			        System.out.println("");
			        if (numberOfOccurences.size() == 0) {
			        	System.out.println("Your search key \"" + searchedKey + "\" did not match any documents.");
			        } else {
			        	System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
			            System.out.println("Searched Key \t\t Page Rank \t\t Page");
			            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
			            for (Map.Entry<String, Integer> entry : sortedNumberOfOccurences) {
			            	System.out.println(searchedKey + " \t\t " + entry.getValue() + " \t\t\t " + entry.getKey());
			            }
			            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");

			        }
			        break;
			}
			
			System.out.println("");
			System.out.println("Do you want to continue using the search engine? [Y/N]");
			searchEngineChoice = scanner.next().charAt(0);
			
			if (searchEngineChoice == 'y' || searchEngineChoice == 'Y') {
				quit = false;
			} else {
				System.out.println("---------------------------------------------------------------Closed----------------------------------------------------------------------");
				quit = true;
			}
			
			scanner.nextLine();
			
		}
		scanner.close();
	}
		

}
