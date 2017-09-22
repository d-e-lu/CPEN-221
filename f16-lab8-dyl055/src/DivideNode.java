import java.math.BigDecimal;
public class DivideNode extends Node{
	static final int numChildren = 2;

	/**
	 * Create a new node that represents a division operation. The two
	 * children of this node represent the two operands, although they may be
	 * subexpressions that need to be evaluated.
	 * 
	 * @param leftNode
	 * @param rightNode
	 */
	public DivideNode(Node leftNode, Node rightNode) {
		super(leftNode, rightNode);
	}

	/**
	 * Evaluate the divide operation by evaluating the two child nodes and
	 * then dividing their results.
	 * @return the quotient of the left and right subtrees
	 */
	public BigDecimal evaluate() {
		return super.getChild(0).evaluate()
				.divide(super.getChild(1).evaluate());
	}

	/**
	 * Return a character that represents the operation this node represents.
	 * 
	 * @return '/' for division.
	 */
	public char getOpName() {
		return '/';
	}

}
