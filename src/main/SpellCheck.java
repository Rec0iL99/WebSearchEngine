package main;
import java.util.*;
import java.io.*;
import algorithms.Sequences;

public class SpellCheck {
	// creating the dictionary using EnglishWords.txt. Source: https://github.com/dwyl/english-words
	public static ArrayList<String> createDictionary(){
		ArrayList<String> dictionaryList = new ArrayList<String>();
		try {
			BufferedReader br =  new BufferedReader(new FileReader("/Users/joelmathew/eclipse-workspace/WebSearchEngine/src/EnglishWords.txt"));
			String line = br.readLine();
			while (line != null) {
				dictionaryList.add(line);
				line = br.readLine();
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return dictionaryList;
	}
	
	// provide suggestions if the word does not exist in the english dictionary
    public static ArrayList<String> giveSuggestions(String word) {
        ArrayList<String> wordSuggestions = new ArrayList<String>();
        
        try {
            int i=0;
            int distance=0;
            ArrayList<String> dictionary = new ArrayList<String>();
            dictionary = createDictionary();

            if (!dictionary.contains(word)) {
                for (i=0; i<dictionary.size(); i++) {
                	distance = Sequences.editDistance(word, dictionary.get(i));
                    if(distance > 0 && distance <= 2) {
                    	wordSuggestions.add(dictionary.get(i));
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return wordSuggestions;
    }
}
