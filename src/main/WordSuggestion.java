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
        System.out.println("\nSimilar searches:");
        int count = 0;
        for (String s : st.keysWithPrefix(searchedKey)) {
        	System.out.println(s);
//            StdOut.println(s);
            count++;
            if (count >= 5) {
                break;
            }
        }
	}
}
