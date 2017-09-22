import java.math.BigDecimal;

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
