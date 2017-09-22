
public class CharSet {
    
    private String s;
    private char[] chars;
    // Rep invariant:
    //     s contains no repeated characters;
    //     s is sorted such that s[0] < s[1] < ... < s[s.length()-1]
    // Abstraction function:
    //     represents the set of characters found in s
    public CharSet(String s){
    	this.s = s;
    	this.chars = s.toCharArray();
    	
    }
    public char[] getChars(){
    	return chars;
    }
    @Override
    public boolean equals(Object obj) {
    	if(!(obj instanceof CharSet)){
    		return false;
    	}
    	CharSet objCharSet = (CharSet) obj;
    	for (int i = 0; i < this.chars.length; i++ ){
    		if(chars[i]!=objCharSet.getChars()[i]){
    			return false;
    		}
    	}
    	return true;
    }
    
    @Override
    public int hashCode() {
    	return s.hashCode();
    }

}
