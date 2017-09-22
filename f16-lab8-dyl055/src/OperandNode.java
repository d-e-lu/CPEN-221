import java.math.BigDecimal;

public class OperandNode extends Node {

	static final int numChildren = 0;
	private BigDecimal value;

	public OperandNode(BigDecimal val) {
		super( );
		value = val;
	}

	public BigDecimal evaluate() {
		return value;
	}
	
	public char getOpName( ) {
		return ' ';
	}

}
