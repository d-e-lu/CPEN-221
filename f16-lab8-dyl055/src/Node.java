import java.math.BigDecimal;

/**
 * 
 * This is an abstract class for representing an element of computation in an
 * arithmetic expression. It could represent an operator or an operand.
 * 
 * If a Node represents an operator then it could represent a unary or binary
 * operator, and this would determine how many child nodes it has. If a node
 * represents an operand then it will have no child nodes.
 * 
 * @author Sathish Gopalakrishnan
 * 
 */
public abstract class Node {

	// How many children does this node have?
	// We are mostly dealing with either two children or none.
	private int numChildren;

	// References to the child nodes.
	Node[] childNodes;

	/**
	 * A constructor that takes two child nodes and creates a node.
	 * 
	 * @param leftChild
	 * @param rightChild
	 */
	public Node(Node leftChild, Node rightChild) {
		this.numChildren = 2;
		childNodes = new Node[2];
		childNodes[0] = leftChild;
		childNodes[1] = rightChild;
	}

	/**
	 * The default constructor takes no arguments and returns a leaf node with
	 * no children.
	 */
	public Node() {
		numChildren = 0;
	}

	/**
	 * Given an index return the child node from the list of child nodes.
	 * 
	 * @param index
	 *            the index for the child node needed
	 * @return the child node at the given index
	 * @throws IllegalArgumentException
	 *             if an invalid index is provided
	 */
	public Node getChild(int index) throws IllegalArgumentException {
		if (index < 0 || index >= numChildren)
			throw new IllegalArgumentException();
		return childNodes[index];
	}

	/**
	 * A method to check if the node is a leaf node. A leaf node has no
	 * children.
	 * 
	 * @return true if this is a leaf node, and return false otherwise.
	 */
	public boolean isLeaf() {
		return (numChildren == 0);
	}

	/**
	 * An abstract method to return a character representing the operation at
	 * this node. This method needs to be defined by classes that extend Node.
	 * 
	 * @return the operation this node represents.
	 */
	public abstract char getOpName();

	/**
	 * Evaluate this node, i.e., evaluate the arithmetic expression represented
	 * by this node.
	 * 
	 * @return the value of the arithmetic expression represented by this node
	 */
	public abstract BigDecimal evaluate();
}