import java.util.Stack;

/**
 * 
 * @author Sathish Gopalakrishnan
 * 
 * This class contains a method to evaluate an arithmetic expression
 * that is in Postfix notation (or Reverse Polish Notation).
 * See <a href="https://en.wikipedia.org/wiki/Reverse_Polish_notation">Wikipedia</a>
 * for details on the notation.
 *
 */
public class PostfixEvaluator {
	
	private String arithmeticExpr;
	
	/**
	 * This is the only constructor for this class.
	 * It takes a string that represents an arithmetic expression
	 * as input argument.
	 * 
	 * @param expr is a string that represents an arithmetic expression 
	 * <strong>in Postfix notation</strong>.
	 */
	public PostfixEvaluator( String expr ) {
		arithmeticExpr = expr;
	}
	
	/**
	 * This method evaluates the arithmetic expression that 
	 * was passed as a string to the constructor for this class.
	 * 
	 * @return the value of the arithmetic expression
	 * @throws MalformedExpressionException if the provided expression is not
	 * 	a valid expression in Postfix notation
	 */
	double eval( ) throws MalformedExpressionException {
		// TODO: Implement this method.
		// The code provided here is for illustration only, and
		// can be deleted when you write your implementation.

		// Using a stack makes it very simple to evaluate the
		// arithmetic expression.
		// See http://docs.oracle.com/javase/8/docs/api/java/util/Stack.html
		
		// Use the Scanner to get the elements (tokens) in the
		// arithmetic expression.
		Stack<Double> tokens = new Stack<Double>();
		Scanner scanner = new Scanner(arithmeticExpr);
		
	
		while(!scanner.isEmpty()){
			
			Token currToken = scanner.getToken();
			
			if(currToken.isDouble()){
				tokens.push(currToken.getValue());
			}
			else{
				if(tokens.size() < 2){
					throw new MalformedExpressionException();
				}
				else{
					double secondNum = tokens.pop();
					double firstNum = tokens.pop();
				
					switch(currToken.getName()){
					case("+"):
						tokens = add(tokens, firstNum, secondNum);
						break;
					case("-"):
						tokens = subtract(tokens, firstNum, secondNum);
						break;
					case("*"):
						tokens = multiply(tokens, firstNum, secondNum);
						break;
					case("/"):
						tokens = divide(tokens, firstNum, secondNum);
						break;
					default:
						System.out.println("Unexpected Token");
					}	
				}
			
			}
			scanner.eatToken();
		}
		
		// now process the token, etc.
		// You should read the implementation of the Token class
		// to determine what methods you can and should use.
		
		// It is sufficient to support the four basic operations:
		// addition, subtraction, multiplication & division.
		if(tokens.size() != 1){
			throw new MalformedExpressionException();
		}
		else{
			return tokens.pop();
		}
		
	}
	/** adds two numbers together and pushes it onto the stack tokens
	  * @param tokens is the stack that will add the new token onto
	  * @param first is the first number
	  * @param second is the second number
	  * @returns the Stack tokens with the added element
	  * effects: modifies the stack tokens by adding a number to it
	  */
	private Stack<Double> add(Stack<Double> tokens, double first, double second){
		double num = first + second;
		tokens.push(num);
		return tokens;
	}
	/** subtracts the first number by the second number and adds it to the stack tokens
	  * @param tokens is the stack that will add the new token onto
	  * @param first is the number that will be subtracted from
	  * @param second is the number that will subtract the first by
	  * @returns the Stack tokens with the added element
	  * effects: modifies the stack tokens by adding a number to it
	  */
	private Stack<Double> subtract(Stack<Double> tokens, double first, double second){
		double num = first - second;
		
		tokens.push(num);
		return tokens;
	}
	/** multiplies the first number by the second number adds it to the stack tokens
	  * @param tokens is the stack that will add the new token onto
	  * @param first is the first number to multiply
	  * @param second is the second number to multiply
	  * @returns the Stack tokens with the added element
	  * effects: modifies the stack tokens by adding a number to it
	  */
	private Stack<Double> multiply(Stack<Double> tokens, double first, double second){
		double num = first * second;
		tokens.push(num);
		return tokens;
	}
	/** divides the first number by the second number adds it to the stack tokens
	  * @param tokens is the stack that will add the new token onto
	  * @param first is the numerator of the division expression
	  * @param second is the denominator of the division expression
	  * @returns the Stack tokens with the added element
	  * @throws IllegalArgumentException when the second number is zero
	  * effects: modifies the stack tokens by adding a number to it
	  */
	private Stack<Double> divide(Stack<Double> tokens, double first, double second)throws IllegalArgumentException{
		if (second == 0){
			throw new IllegalArgumentException();
		}
		double num = first/second;
		tokens.push(num);
		return tokens;
	}
	
}