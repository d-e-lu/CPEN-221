import static org.junit.Assert.*;

import org.junit.Test;

public class MyStackTest {
	
	@Test
	public void ifEmptyTest(){
		MyStack s = new MyStack();
		assertTrue( s.isEmpty());
		
		HtmlTag tag = new HtmlTag("p");
		s.push(tag);
		assertFalse(s.isEmpty());
	}

	@Test
	public void pushAndPopTest(){
		MyStack s = new MyStack();
		HtmlTag hTag = new HtmlTag("h");
		HtmlTag pTag = new HtmlTag("p");
		
		//Test when stack is empty
		try{
			s.pop();
			fail("Didn't throw exception while trying to pop empty stack");
		}catch(Exception e){}
		
		//Test when stack is full
		s.push(hTag);
		s.push(pTag);
		assertEquals(pTag, s.pop());
		assertEquals(hTag, s.pop());

	}
	@Test
	public void peekTest(){
		MyStack s = new MyStack();
		HtmlTag hTag = new HtmlTag("h");
		HtmlTag pTag = new HtmlTag("p");
		
		try{
			s.peek();
			fail("Didn't throw exception while trying to peek empty stack");
		}catch(Exception e){}
		
		s.push(hTag);
		s.push(pTag);
		assertEquals(pTag, s.peek());
		assertEquals(pTag, s.peek());	
	}
	

}
