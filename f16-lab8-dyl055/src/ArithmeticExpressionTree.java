import java.math.BigDecimal;

/**
 * Represents an arithmetic expression in a binary tree structure. Each internal
 * node represents an operator, and its subtrees represent operands. All
 * operators are binary, so every operator has both a left and a right operand.
 * Leaf nodes represent either integers or variables.
 * 
 * @see Node
 */
public class ArithmeticExpressionTree {

	/** The root node of the tree. */
	private Node expression;

	/**
	 * Takes a string containing an arithmetic expression and creates a tree
	 * representing that expression. Precondition: <code>expStr</code> must be
	 * fully parenthesized and have whitespace around each operator. For
	 * example, "(a * (5 / n))".
	 * 
	 * @param expStr
	 *            a string representation of the expression.
	 * @throws MalformedExpressionException
	 *             if <code>expStr</code> is not a fully-parenthesized,
	 *             whitespace-separated expression.
	 */
	public ArithmeticExpressionTree(String expStr)
			throws MalformedExpressionException {
		try {
			// Call a recursive helper method to create the tree.
			expression = createTree(new Scanner(expStr));
		} catch (NullPointerException e) {
			throw new MalformedExpressionException(
					"Input expression ended prematurely");
		}
	}

	/**
	 * Private constructor: creates a new ExpressionTree with root node
	 * <code>root</code>. Used by the <code>simplify()</code> method to create
	 * the simplified tree.
	 * 
	 * @param root
	 *            the root of the new tree.
	 */
	private ArithmeticExpressionTree(Node root) {
		expression = root;
	}

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

	/**
	 * Prints out this expression, fully parenthesized.
	 */
	public void printExpression() {
		// Call a recursive helper method to perform the actual printing.
		printExpression(expression);
		System.out.println();
	}

	/**
	 * Recursive helper method for printExpression(). Prints out the expression
	 * in the subtree rooted at <code>root</code>, fully parenthesized.
	 * 
	 * @param root
	 *            the root of the expression tree to print.
	 */
	private void printExpression(Node root) {
		// If the node is a leaf, just print the value.
		if (root.isLeaf()) {
			System.out.print(root.evaluate());

			// Otherwise, print an opening parenthesis, the operands and
			// operator, and a closing parenthesis.
		} else {
			System.out.print("(");
			printExpression(root.getChild(0));
			System.out.print(" " + root.getOpName() + " ");
			printExpression(root.getChild(1));
			System.out.print(")");
		}
	}

}
