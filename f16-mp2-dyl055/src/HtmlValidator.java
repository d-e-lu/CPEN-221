import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/*
 * This is the HtmlValidator class.
 * You should implement this class.
 */
public class HtmlValidator {
	//current Queue of HtmlTags to be validated
	private Queue<HtmlTag> tags;
	
	
	/** Initializes tags with an empty LinkedList
	  * effects: create a new Html Queue object for this copy of HtmlTags
	  */
	public HtmlValidator(){
		this.tags = new LinkedList<HtmlTag>();
	}
	
	/** Initializes tags with a Queue of HtmlTags
	  * @param tags is a valid Queue of HtmlTags that will be copied onto
	  * 		  this Validator's Queue of HtmlTags
	  * effects: creates a HtmlValidator object with a copy of HtmlTags
	  */
	public HtmlValidator(Queue<HtmlTag> tags){
		this.tags = new LinkedList<HtmlTag>(tags);
	}
	
	/** This method adds the passed Html tag to the Queue of Html tags
	  * @throws an IllegalArgumentException if tag is null
	  * @param: tag is an HtmlTag to be added to the queue of tags
	  */
	public void addTag(HtmlTag tag) throws IllegalArgumentException{
		if(tag == null){
			throw new IllegalArgumentException();
		}
		else{
			tags.add(tag);
		}
		
	}
	
	/** This method returns the current queue of tags
	  * @returns tags which is the queue of HtmlTags
	  */
	public Queue<HtmlTag> getTags(){
		return tags;
	}

	/** This method removes any tags that matches the passed String
	  * @throws: IllegalArgumentException if the String passed is null
	  * @param: element is the String of the HtmlTag wanting to be
	  * 		   removed. Element shouldn't contain brackets < >, only
	  * 		   the String inside of them
	  */
	public void removeAll(String element) throws IllegalArgumentException{
		if (element == null){
			throw new IllegalArgumentException();
		}
		else{	
			Iterator<HtmlTag> i = tags.iterator();
			StringBuilder opener = new StringBuilder("<").append(element).append(">");
			StringBuilder closer = new StringBuilder("</").append(element).append(">");
			while(i.hasNext()){
				String br = i.next().toString();
				if (br.equals(opener.toString()) || br.equals(closer.toString())){
					i.remove();
				}
			}			
		}
	}
	
	/** This method parses the HtmlTags stored in tags into a readable Html format.
	  * It will also check for errors checking for unexpected and unclosed tags.
	  * @returns a multi-line string of the formatted HtmlTags
	  */
	public String validate(){
		MyStack stackedTags = new MyStack();
		StringBuffer formatted = new StringBuffer();
		int tabs = 0;
		for( HtmlTag tag : tags){
			if (tag.isSelfClosing()){
				if(tag.isOpenTag()){
					formatted = appendTabs(formatted, tabs);
					formatted.append(tag.toString());}
				else{
					formatted.append("ERROR unexpected tag: " + tag.toString());
				}
				
			}
			else{
				if(tag.isOpenTag()){
						formatted = appendTabs(formatted, tabs);
						tabs++;
						formatted.append(tag.toString());
						stackedTags.push(tag);
						
				}
				else{
					
					if(stackedTags.isEmpty() == false && tag.matches(stackedTags.peek())){
						tabs--;
						formatted = appendTabs(formatted, tabs);
						formatted.append(tag.toString());
						stackedTags.pop();
					}
					else{
						formatted.append("ERROR unexpected tag: " + tag.toString());
					}
				}
			}
			formatted.append("\n");
		}
		
		while(!stackedTags.isEmpty()){
			formatted.append("ERROR unclosed tag: "+stackedTags.pop().toString()+"\n");
		}
		
		return formatted.toString();
	}
	/** This method appends the tabs with the right amount of spacing
	  * @param sBuffer is a StringBuffer to be changed
	  * @param tabs is an integer counting the number of tabs needed
	  * @returns a StringBuffer with the formatted spacing
	  */
	private StringBuffer appendTabs(StringBuffer sBuffer, int tabs){
		for(int i = 0; i < tabs; i++){
			sBuffer.append("    ");
		}
		return sBuffer;
	}
	
}
