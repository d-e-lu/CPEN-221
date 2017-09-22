import java.math.BigDecimal;

public class MultiplyNode extends Node {

	static final int numChildren = 2;

	/**
	 * Create a new node that represents a multiplication operation. The two
	 * children of this node represent the two operands, although they may be
	 * subexpressions that need to be evaluated.
	 * 
	 * @param leftNode
	 * @param rightNode
	 */
	public MultiplyNode(Node leftNode, Node rightNode) {
		super(leftNode, rightNode);
	}

	/**
	 * Evaluate the multiply operation by evaluating the two child nodes and
	 * then multiplying their results.
	 * @return the product of the left and right subtrees
	 */
	public BigDecimal evaluate() {
		return super.getChild(0).evaluate()
				.multiply(super.getChild(1).evaluate());
	}

	/**
	 * Return a character that represents the operation this node represents.
	 * 
	 * @return '*' for multiplication.
	 */
	public char getOpName() {
		return '*';
	}

}
