import static org.junit.Assert.*;

import org.junit.Test;

public class PostfixEvaluatorTest {

	// Implement a sufficient number of tests to have
	// reasonable confidence in the correctness of your
	// PostfixEvaluator implementation.
	
	@Test
	public void evalTest1() {
		PostfixEvaluator p = new PostfixEvaluator("1");
		try {
			assertEquals(1.0, p.eval(),0);
		} catch (MalformedExpressionException e) {
			fail("Printed Exception");
		}
	}
	@Test
	public void evalTest2() {
		PostfixEvaluator p = new PostfixEvaluator("5 7 3 - *");
		try {
			assertEquals(20.0, p.eval(),0);
		} catch (MalformedExpressionException e) {
			fail("Printed Exception");
		}
	}
	@Test
	public void evalTest3() {
		PostfixEvaluator p = new PostfixEvaluator("5 7 * 3 -");
		try {
			assertEquals(32.0, p.eval(),0);
		} catch (MalformedExpressionException e) {
			fail("Printed Exception");
		}
	}
	@Test
	public void evalTest4() {
		PostfixEvaluator p = new PostfixEvaluator("2 3 4 * +");
		try {
			assertEquals(14.0, p.eval(),0);
		} catch (MalformedExpressionException e) {
			fail("Printed Exception");
		}
	}
	@Test
	public void evalTest5() {
		PostfixEvaluator p = new PostfixEvaluator("1 0 /");
		try {
			p.eval();
			fail("Did not throw exception when dividing by zero");
		} catch (IllegalArgumentException e) {
			assertEquals(true, true);
		} catch(MalformedExpressionException e){
			fail("Wrong exception thrown");
		}
	}
	@Test
	public void evalTest6() {
		PostfixEvaluator p = new PostfixEvaluator("2 3 4 *");
		try {
			p.eval();
			fail("Did not catch exception when it was a malformed equation");
		} catch (MalformedExpressionException e) {
			assertEquals(true, true);
		}
	}
	@Test
	public void evalTest7() {
		PostfixEvaluator p = new PostfixEvaluator("");
		try {
			p.eval();
			fail("Did not catch exception when it was a malformed equation");
		} catch (MalformedExpressionException e) {
			assertEquals(true, true);
		}
	}
	@Test
	public void evalTest8() {
		PostfixEvaluator p = new PostfixEvaluator("1 1 1 1 1 1 1 ++++++");
		try {
			assertEquals(7.0,p.eval(),0);
		} catch (MalformedExpressionException e) {
			fail("Caught exception when not needed");
		}
	}
	@Test
	public void evalTest9() {
		PostfixEvaluator p = new PostfixEvaluator("10 3 /");
		try {
			assertEquals(10.0/3, p.eval(),0);
		} catch (MalformedExpressionException e) {
			fail("Printed Exception");
		}
	}
	@Test
	public void evalTest10() {
		PostfixEvaluator p = new PostfixEvaluator("10 10 5 1 3 + * / -");
		try {
			assertEquals(9.5, p.eval(),0);
		} catch (MalformedExpressionException e) {
			fail("Printed Exception");
		}
	}

}
