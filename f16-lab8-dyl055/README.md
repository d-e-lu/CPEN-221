CPEN 221: Lab 8
Evaluating Arithmetic Expressions using (Binary) Trees
====


This lab activity demonstrates how to convert a provided arithmetic expression into an arithmetic expression tree. The example only works with fully parenthesized expressions with only three operations: addition, subtraction and multiplication.

The purpose of this example is to demonstrate the following:
+ how to create simple _subtypes_ using _subclassing_ in Java (`extends`);
+ how to parse a simple arithmetic expression;
+ how to construct a binary tree;
+ how to exploit the recursive nature of a binary tree;
+ how to evaluate an arithmetic expression from its binary tree representation.

> This lab activity involves infix arithmetic expression evaluation. This contrasts with postfix expression evaluation that used a stack only.

### Suggested Reading and Viewing
+ Tutorial on [subclasses](http://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html) in Java
+ [Binary trees](https://dl.dropboxusercontent.com/u/567187/EECE%20210/Java/9-BinaryTrees.pdf)
+ Videos on binary trees: [Video 1](http://media.pearsoncmg.com/aw/aw_reges_bjp_2/videoPlayer.php?id=c17-1), [Video 2](http://media.pearsoncmg.com/aw/aw_reges_bjp_2/videoPlayer.php?id=c17-2), [Video 3](http://www.youtube.com/watch?v=FKvL3Duawv8)

### Lab Tasks

The provided implementation only supports three arithmetic operations: addition, subtraction and multiplication. 

You will have to add support for the following arithmentic operations:

1. division (`/`), and
1. exponentiation (`^`). 

### Examining the Source Code

#### Creating a binary tree

**A recursive datatype**

A binary tree is a recursive datatype. Such datatypes are built using the fact that most modern object-oriented programming languages allow an object to include references to other objects  of the _same type_. This gives rise to the _recursive_ nature of the datatype because the datatype is defined in terms of other references to the same datatype.

In this example, the `Node` class is defined as follows:

```java
public abstract class Node {

	// How many children does this node have? 
	// We are mostly dealing with either two children or none.
	private int numChildren;
	
	// References to the child nodes.
	Node[] childNodes;
	…
}
```

A `Node` contains an array of references to other `Node` objects.

To create a `Node` with two children, the constructor is as follows:

```java
	/**
	 * A constructor that takes two child nodes and creates a node.
	 * @param leftChild
	 * @param rightChild
	 */
	public Node(Node leftChild, Node rightChild) {
		this.numChildren = 2;
		childNodes = new Node[2];
		childNodes[0] = leftChild;
		childNodes[1] = rightChild;
	}
```

When a every node has at most two children, and the children are not references to other `Node` objects that are higher up (earlier generation `Node`s) in the same structure, we get a _binary tree_.

**Subtypes, subclasses and inheritance**

The `abstract` `Node` class is `extend`ed by other classes that represent specific parts of an arithmetic expression. Note that it is not possible to create objects of an `abstract` class but one can create objects of a concrete subclass and refer to them using the parent class. An example of `extend`ing a class is the `PlusNode` class:

```java
public class PlusNode extends Node {

	/**
	 * Create a new node that represents an addition operation. The two children
	 * of this node represent the two operands, although they may be
	 * subexpressions that need to be evaluated.
	 * 
	 * @param leftNode
	 * @param rightNode
	 */
	public PlusNode(Node leftNode, Node rightNode) {
		super(leftNode, rightNode);
	}

	/**
	 * Evaluate the addition operation by evaluating the two child nodes and
	 * then adding their results.
	 * @return the sum of the left and right subtrees
	 */
	public BigDecimal evaluate() {
		return super.getChild(0).evaluate().add(super.getChild(1).evaluate());
	}

	/**
	 * Return a character that represents the operation this node represents.
	 * 
	 * @return '+' for addition
	 */
	public char getOpName() {
		return '+';
	}
}
```

**From expression to tree**

A binary tree representing an arithmetic expression is created given a `String` that represents the arithmetic expression. The code that achieves this is in the following method that is part of `ArithmeticExpressionTree.java`.

```java
	/**
	 * Recursive helper method of the public constructor. Reads an expression
	 * specification from the scanner until the parentheses (if any) are matched
	 * and creates the corresponding tree. Note: <code>createTree</code> does
	 * not read until the end of the input, only to the end of the current
	 * subexpression.
	 * 
	 * @param scanner
	 *            the token stream representing the part of the expression that
	 *            has not been read yet.
	 * @throws MalformedExpressionException
	 *             if the input expression is not valid.
	 */
	private Node createTree(Scanner scanner)
			throws MalformedExpressionException {
		// Read the next token from the scanner.
		Token nextTok = scanner.getToken();
		// If it's null, then the expression must be wrong.
		if (nextTok == null) {
			throw new MalformedExpressionException(
					"Input expression ended prematurely");
		}

		// Otherwise, check if it's the beginning of a subexpression.
		if (nextTok.equals("(")) {
			// It's a subexpression; eat the "(" token.
			scanner.eatToken();

			// Read the operator and the operands and create left and right
			// subtrees for the operands.
			Node leftExpr = createTree(scanner);
			Token opTok = scanner.getToken();

			// If opTok is null, then the expression is wrong.
			if (opTok == null) {
				throw new MalformedExpressionException(
						"Input expression ended prematurely");
			}

			String op = opTok.getName();
			// Eat the operator token.
			scanner.eatToken();
			Node rightExpr = createTree(scanner);

			// Make sure the next token is ")"; otherwise, the input is
			// bad.
			scanner.useToken(")");

			// Create the root of a subtree to represent the expression
			// just read.
			switch (op) {
			case "+":
				return new PlusNode(leftExpr, rightExpr);
			case "-":
				return new SubtractNode(leftExpr, rightExpr);
			case "*":
				return new MultiplyNode(leftExpr, rightExpr);
			default:
				throw new MalformedExpressionException();
			}

		} else {
			// The expression is just a single value; create a leaf node
			// for it.
			BigDecimal value;
			if (nextTok.isInteger()) {
				value = nextTok.getValue();
			} else {
				throw new MalformedExpressionException();
			}

			// Eat the integer/variable token.
			scanner.eatToken();
			return new OperandNode(value);
		}
	}
```

#### Evaluating an arithmetic expression

Once an arithmetic expression has been represented as a binary tree, it is easy to evaluate the expression.

Exploiting the recursive nature of a tree, the code is simply the following (in `ArithmeticExpressionTree.java`):

```java
	/**
	 * Returns the result of evaluating this expression tree. Precondition: this
	 * expression tree must consist only of integer values (no variables), with
	 * no division by zero, and every operator must be valid.
	 * 
	 * @return the integer result of evaluating this expression tree.
	 * @throws MalformedExpressionException
	 *             if the expression contains a variable or if the operator is
	 *             invalid.
	 * @throws ArithmeticException
	 *             if an attempt to divide by 0 occurs.
	 */
	public BigDecimal eval() throws MalformedExpressionException,
			ArithmeticException {
		// Call a recursive helper method to perform the actual evaluation.
		return expression.evaluate();
	}
```

#### Test code

```java
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
```

### To Think About

+ Can you remove the restriction that the arithmetic expression provided as input is fully parenthesized?
+ Can you add unary operators such as the unary `-`?
+ Can you add more features such as the _logarithm_ function?
+ Can you produce the postfix expression from the arithmetic expression tree?

## Submission

+ Submit your implementation using Github. 
+ As always, include your name and that of your partner in a file named `partners.txt`.
+ Only one person in a team needs to submit work.
