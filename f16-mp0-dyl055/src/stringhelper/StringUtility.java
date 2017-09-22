package stringhelper;

import java.util.Arrays;
public class StringUtility {

    /* Consult the methods that are declared in class String.
     * Visit docs.oracle.com/javase/8/docs/api/java/lang/String.html. 
     * Some important methods are charAt, length(), trim, substring, 
     * indexOf, isEmpty, lastIndexOf, startsWith, endsWith, split.
     */

    /** Return true iff str is a palindrome: it reads the same 
     * backwards and forwards.
     *
     * Examples: For str = "", return true
     *           For str = "ab", return false
     *           For str = "aba", return true.
     *           For str = "abba", return true.
     *           For str = "Madam, I'm Adam", return false.
     *           For str = "MadamImAdam", return false.
     *           For str = "madamimadam", return true.  
     * 
     * @param str is not null
     * @return true if str is a palindrome and false otherwise
     */

    public static boolean isPalindrome(String str) {
    	String reversedString = "";
    	
        for(int i = str.length() - 1; i >= 0; i--){
        	reversedString = reversedString + str.charAt(i);
        }
        
    	if(str.equals(reversedString)){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    /**  Return the number of times query occurs as a substring of 
     * mainString (the different occurrences may overlap).
     * Precondition:    query is not null and
     *                  query is not the empty string "".
     * Examples: For src = "ab", query = "b", return 1.
     *           For src = "Luke Skywalker", query = "ke", return 2.
     *           For src = "abababab", query = "aba", return 3.
     *           For src = "abc", query = "", return 4 
     *
     * @param src is not null and neither is it the empty string
     * @return a count of the number of times query appears in 
     *          mainString
     */
    public static int countOccurrences(String mainString, String query) 
    { 
    	if(query.isEmpty()){
    		return -1;
    	}
        int total = 0;
        for(int i = 0; i < mainString.length() - query.length() + 1; i++){
        	if(mainString.substring(i, i+query.length()).equals(query)){
        		total++;
        	}
        }
        return total;
    }

    /** 
     * Suppose a string represents somebody's full name. 
     * The first word in the string is the first name, 
     * the last word is the last name, and any words in between are 
     * middle names. The string may have an arbitary amount of 
     * whitespace (blank spaces) between words and at the beginning or 
     * the end. Return a string that is a nicely formatted name in the 
     * format Lastname, Firstname [Middle Initials]. In the string that 
     * this method returns, the first character of the last name, the 
     * first character of the first name and the middle initials must be
     * capitalized; all other characters must be in lower case.

     * Some examples:
     * sathish gopalakrishnan should result in Gopalakrishnan, Sathish
     * Matei Radu Ripeanu should result in Ripeanu, Matei R.
     * John Ronald reuel Tolkien should result in Tolkien, John R. R.
     * Arvind should result in Arvind (this is a special case when there
     * is only one word in the string) */
    
    public static String formatName(String nameStr) {
        nameStr = nameStr.trim();
    	
    	String[] names = nameStr.split("\\W+");

    	int numberOfNames = names.length;
    	String formattedString = "";
    	if(numberOfNames == 1){
    		formattedString = nameStr.substring(0, 1).toUpperCase() + nameStr.substring(1).toLowerCase();
    	}
    	else if(numberOfNames == 2){
    		formattedString = formattedString.concat(names[1].substring(0, 1).toUpperCase() + names[1].substring(1).toLowerCase() + " ");
    		formattedString = formattedString.concat(names[0].substring(0, 1).toUpperCase() + names[0].substring(1).toLowerCase());
    	}
    	else{
    		formattedString = formattedString.concat(names[numberOfNames-1].substring(0, 1).toUpperCase() + names[numberOfNames-1].substring(1).toLowerCase() + ", ");
    		formattedString = formattedString.concat(names[0].substring(0, 1).toUpperCase() + names[0].substring(1).toLowerCase() + " ");
    		for(int i = 1; i < numberOfNames - 1; i++){
    			formattedString = formattedString.concat(Character.toString(names[i].charAt(0)).toUpperCase() + ". ");
    		}
    		
    	}
    	
    	return formattedString;
    	
    }
    
    
    /** Return true iff s1 and s2 are anagrams of each other.
     * An anagram of a string is another string that has the same
     * characters, but possibly in a different order. 
     * Like other methods of this class, this method too is
     * case-sensitive, so 'a' and 'A' are considered different 
     * characters.
     *
     * Examples: For s1 = "mary", s2 = "army", return true.
     *           For s1 = "tom marvolo riddle", 
     *               s2 = "i am lordvoldemort", return true.
     *           For s1 = "tommarvoloriddle", 
     *               s2 = "i am lordvoldemort", return false.
     *           For s1 = "foo", s2 = "bar", return false.  
     * 
     * @param s1 and s2 are not null
     * @return true if s2 is an anagram of s1, and false otherwise
     */
    public static boolean anagrams(String s1, String s2) {
    	char[] charArray1 = s1.toCharArray();
    	char[] charArray2 = s2.toCharArray();
    	Arrays.sort(charArray1);
    	Arrays.sort(charArray2);
    	
    	s1 = new String(charArray1);
    	s2 = new String(charArray2);
    	if(s1.equals(s2)){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    /** An encoding of the string `aaassddddffg` is the string 
     * `3a2s4d2f1g`. Along these lines, `zzz56yyy` would be encoded as 
     * `3z15163y`. Assuming this encoding method, an encoded string is 
     * a *sequence* of digit-character pairs. Implement a method to 
     * decode a string (given the encoded version). 
     * (What should you do if a string is not in the correct format? 
     * For now, assume that all test strings will conform to the 
     * expected format.)
     * 
     * @param encstr Is in the appropriate encoded format
     * @return a String that has been decoded from the special format
     */
    public static String decode(String encstr) {

    	String encoded = "";
    	int count = 0;
    	char current = encstr.charAt(0);
    	encstr = encstr.trim();
    	
    	for(int i = 0; i < encstr.length(); i++){
    		char currentChar = (char) encstr.charAt(i);
    		
    		if (current != currentChar){
    			encoded = encoded.concat(Integer.toString(count) + current);
    			count = 1;
    			current = currentChar;
    		}
    		else{
    			count++;
    		}
    	}
    	encoded = encoded.concat(Integer.toString(count) + current);
    	
        return encoded;
    }
}
