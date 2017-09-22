/** Exception thrown when an invalid arithmetic expression is encountered
 * by the program. The message field contains information about the
 * particular problem that was encountered.
 *
 * @see #getMessage()
 */

public class MalformedExpressionException extends Exception {

    /** Constructs a MalformedExpressionException with no message.
     */
    public MalformedExpressionException() {
        super();
    }



    /** Constructs a MalformedExpressionException with the detail message.
     */
    public MalformedExpressionException( String message ) {
        super( message );
    }
}