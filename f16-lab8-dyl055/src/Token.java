import java.math.BigDecimal;

/** A Token holds either a String name or an Integer value. It is used by
 * the Scanner while reading the input.
 *
 * @see Scanner
 */

public class Token {

    /** The name of a variable token. */
    private String name;

    /** The value of an integer token. */
    private BigDecimal value;

    /** Whether this Token is an integer or a variable.
     * Invariant: 
     * <ul>
     * <li> <code>isInteger</code> means <code>value</code> has a non-null
     *      value
     * <li> not <code>isInteger</code> means <code>name</code> has a
     *      non-null value
     * </ul>
     */
    private boolean isInteger;



    /** Constructs a Token with a string value.
     *
     * @param s the string value of the new token
     */
    public Token( String s ) {
        isInteger = false;
        name = s;
    }



    /** Constructs a Token with an integer value.
     *
     * @param i the integer value of the new token
     */
    public Token( int i ) {
        isInteger = true;
        value = new BigDecimal( i );
    }



    /** Returns the name of this Token. Precondition: this Token should
     * represent a String, not an integer.
     *
     * @return the name of this Token.
     */
    public String getName() {
        return name;
    }



    /** Returns the value of this Token. Precondition: this Token should
     * represent an integer, not a string.
     *
     * @return the value of this Token.
     */
    public BigDecimal getValue() {
        return value;
    }



    /** Checks whether this Token contains an integer.
     *
     * @return true if this token has a non-null value
     */
    public boolean isInteger() {
        return isInteger;
    }



    /** Checks whether this Token contains a variable.
     *
     * @return true if this token has a non-null name
     */
    public boolean isVariable() {
        return ! isInteger;
    }



    /** Checks whether this Token's name equals <code>s</code>.
     *
     * @param s the string to compare to
     * @return true if this Token's name equals <code>s</code> and false
     * if not
     */
    public boolean equals(String s) {
        return (! isInteger && name.equals(s));
    }



    /** Checks whether this Token's value equals <code>i</code>.
     *
     * @param i the integer to compare to
     * @return true if this Token's value equals <code>i</code> and false
     * if not
     */
    public boolean equals(Integer tok) {
        return (isInteger && value.intValue() == tok.intValue());
    }



    /** Returns a nice string representation of this Token.
     *
     * @return a string representing this Token
     */
    public String toString() {
        return isInteger ? value.toString() : name;
    }
}
