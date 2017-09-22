import static org.junit.Assert.*;

import org.junit.Test;
import java.math.BigDecimal;

public class ArithmeticExpressionTreeTest {

	@Test
	public void test1() {
		String expr = "((1+2)+3)";
		ArithmeticExpressionTree myTree;
		try {
			myTree = new ArithmeticExpressionTree(expr);
			assertEquals(myTree.eval(), new BigDecimal(6.0));
		}
		catch( MalformedExpressionException e ) {
			fail("Exception!");
		}
		
	}

	@Test
	public void test2() {
		String expr = "((1+2)*3)";
		ArithmeticExpressionTree myTree;
		try {
			myTree = new ArithmeticExpressionTree(expr);
			assertEquals(myTree.eval(), new BigDecimal(9.0));
		}
		catch( MalformedExpressionException e ) {
			fail("Exception!");
		}
		
	}
	
	@Test
	public void test3() {
		String expr = "((1+2)/3)";
		ArithmeticExpressionTree myTree;
		try {
			myTree = new ArithmeticExpressionTree(expr);
			fail("Should have thrown an exception because we have not implemented divide yet!");
		}
		catch( Exception e ) {
			assertEquals(1,1);
		}
		
	}
	
}
