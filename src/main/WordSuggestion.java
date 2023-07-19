package main;
import algorithms.TrieST;
import java.io.*;
import java.util.*;

public class WordSuggestion {
	public static void suggest(String searchedKey) throws IOException {
//		Scanner sc = new Scanner(System.in);
//		String ip = sc.nextLine();
		// list that holds strings of a file
		List<String> listOfStrings
			= new ArrayList<String>();
	
		// load data from file
		BufferedReader bf = new BufferedReader(
			new FileReader("/Users/joelmathew/eclipse-workspace/WebSearchEngine/src/EnglishWords.txt"));
	
		// read entire line as string
		String line = bf.readLine();
	
		// checking for end of file
		while (line != null) {
			listOfStrings.add(line);
			line = bf.readLine();
		}
	
		// closing bufferreader object
		bf.close();
	
		// storing the data in arraylist to array
		String[] array
			= listOfStrings.toArray(new String[0]);
		
		TrieST<Integer> st = new TrieST<Integer>();
        for (int i = 0; i < array.length; i++) {
            //String key = In.readString();
            st.put(array[i], i);
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
        int count = 0;
        ArrayList<String> words = new ArrayList<String>();
        for (String s : st.keysWithPrefix(searchedKey)) {
        	words.add(s);
//        	System.out.println(s);
//            StdOut.println(s);
            count++;
            if (count >= 5) {
                break;
            }
        }
        
        if (words.size() == 0) {
        	System.out.print("We could not find similar search terms");
        } else {
        	System.out.println("\nHere are some similar search terms you could search for in the future: ");
        	for (String word : words) {
        		System.out.println(word);
        	}
        }
        System.out.println("\n");
	}
}
