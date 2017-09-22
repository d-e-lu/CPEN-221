/*
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MostCommonWord {
    
    
    // Split the input string into words
	/*
	 * @param text String of words to be split
	 * @return an array of words from text, split by spaces
	 * requires: text not empty
	 * effects: returns a where none of a[i] contain spaces, 
	 * 			do not return empty strings
	 
	
    public static String[] splitIntoWords(String text) {
        String[] words = text.split(" ");
        
        int index = 0;
        for(String word : words){
        	if(word.length() == 0){
        		words = ArrayUtils.removeElement(words, index);
        	}
        	index++;
        	
        }
        
        return words;
    	
    }
    
    
    // Count how many times each input word appears
    /*
     * @param words string array of words
     * @return Map of string to integers
     * requires: words not empty
     * effects: returns map of string to integers
     
    public static Map<String, Integer> countOccurrences(String[] words) {
    	HashMap<String, Integer> map = new HashMap<String, Integer>();
    	
    	
    	for (word in words){
    		if (word is in map){
    			map.set(word, map.get(word) + 1);	
    		}
    		else{
    			map.set(word, 1);
    		}
    	}
    	return map;
    	
    	
    }
    
    
    // Find the input word with the highest frequency count
    /*
     * @param freqs maps string word to integer occurrences
     * @return string of most common word
     * requires: map not empty
     * effects: if there are more than one max occurences,
     * 			returns first string sorted alphabetically,
     * 			otherwise returns occurence with greatest 
     * 			integer
     * 
     
    public static String findMax(Map<String, Integer> freqs) {
        max = freqs.get(first word somehow)
        ArrayList<String> maxList = new ArrayList<String>();
        
        
        for(entry in freq.entryset){
        	if(entry.value > max){
        		maxList = new ArrayList<String>();
        		maxList.add(entry.key)
        		max = entry.value
        		
        	}
        	else if(entry.value == max){
        		maxList.add(entry.key)
        	}
        }
        Collection.sort(maxList, String.CASE_INSENSITIVE_ORDER);
        
        return maxList.get(0);
    }
    
    
    
    
    /**
     * Find the most common word in a string of text.
     * @param s string of words.
     * @return word that occurs most often in s.
     * requires: s is not empty, words in s are separated by spaces
     * effects: if count of words are the same, returns the
     * 			alphabetically first word
     
    public static String mostCommonWord(String s) {
    	return findMax(countOccurrences(splitIntoWords(s)));
    }
    
}
*/