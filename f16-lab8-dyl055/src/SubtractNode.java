import java.math.BigDecimal;

public class SubtractNode extends Node {

	static final int numChildren = 2;

	/**
	 * Create a new node that represents a subtraction operation. The two
	 * children of this node represent the two operands, although they may be
	 * subexpressions that need to be evaluated.
	 * 
	 * @param leftNode
	 * @param rightNode
	 */
	public SubtractNode(Node leftNode, Node rightNode) {
		super(leftNode, rightNode);
	}

	/**
	 * Evaluate the subtraction operation by evaluating the two child nodes and
	 * then adding their results.
	 * @return the difference of the left and right subtrees
	 */
	public BigDecimal evaluate() {
		return super.getChild(0).evaluate()
				.subtract(super.getChild(1).evaluate());
	}

	/**
	 * Return a character that represents the operation this node represents.
	 * 
	 * @return '-' for subtraction
	 */
	public char getOpName() {
		return '-';
	}

}
