/*
 * Implementation of a simple stack for HtmlTags.
 * You should implement this class.
 */

import java.util.ArrayList;

public class MyStack {
	// An ArrayList to hold HtmlTag objects.
	private ArrayList<HtmlTag> stack_internal;

	/*
	 * Create an empty ArrayList called stack_internal
	 */
	public MyStack( ) {
		this.stack_internal = new ArrayList<HtmlTag>( );
	}

	/**
	  * Push a tag onto the top of the stack.
	  * @param tag which is a valid HtmlTag that will be added to the stack
	  */
	public void push( HtmlTag tag ) {
		this.stack_internal.add(tag);
	}

	/**
	  * Removes the tag at the top of the stack.
	  * @throws an IllegalArgumentException if the stack is empty.
	  * @returns the HtmlTag that was most recently added that is
	  * still inside the ArrayList and removes it
	  */
	public HtmlTag pop( ) throws IllegalArgumentException{
			if(this.stack_internal.isEmpty())
				throw new IllegalArgumentException();
			else{
				return this.stack_internal.remove(stack_internal.size()-1);	
			}
	}

	/**
	  * Looks at the object at the top of the stack but
	  * does not actually remove the object.
	  * @throws IllegalArgumentException if the stack is empty.
	  * @returns the HtmlTag that was most recently added that
	  * is still inside the ArrayList
	  */
	public HtmlTag peek( ) throws IllegalArgumentException{
			if(this.stack_internal.isEmpty())
				throw new IllegalArgumentException();
			
			else{
				return this.stack_internal.get(stack_internal.size()-1);	
			}

	}

	/**
	  * Tests if the stack is empty.
	  * @returns true if stack is empty, false otherwise
	  */
	public boolean isEmpty( ) {
		return this.stack_internal.isEmpty();
	}
}
