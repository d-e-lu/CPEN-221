import java.math.BigDecimal;

public class ExponentialNode extends Node{
	static final int numChildren = 2;

	/**
	 * Create a new node that represents a exponential operation. The two
	 * children of this node represent the two operands, although they may be
	 * subexpressions that need to be evaluated.
	 * 
	 * @param leftNode
	 * @param rightNode
	 */
	public ExponentialNode(Node leftNode, Node rightNode) {
		super(leftNode, rightNode);
	}

	/**
	 * Evaluate the exponential operation by evaluating the two child nodes and
	 * then exponentiating their results.
	 * @return the exponential of the left and right subtrees
	 */
	public BigDecimal evaluate() {
		return super.getChild(0).evaluate()
				.pow(super.getChild(1).evaluate().intValue());
	}

	/**
	 * Return a character that represents the operation this node represents.
	 * 
	 * @return '^' for exponentiation.
	 */
	public char getOpName() {
		return '^';
	}


}
