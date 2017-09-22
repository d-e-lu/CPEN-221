/** A Token holds either a String name or a Double value. It is used by
 * the Scanner while reading the input.
 *
 * @see Scanner
 */

public class Token {

    /** The name of a variable token. */
    private String name;

    /** The value of an integer token. */
    private Double value;

    /** Whether this Token is an integer or a variable.
     * Invariant: 
     * <ul>
     * <li> <code>isInteger</code> means <code>value</code> has a non-null
     *      value
     * <li> not <code>isInteger</code> means <code>name</code> has a
     *      non-null value
     * </ul>
     */
    private boolean isDouble;

    /** Constructs a Token with a string value.
     *
     * @param s the string value of the new token
     */
    public Token( String s ) {
        isDouble = false;
        name = s;
    }



    /** Constructs a Token with a double value.
     *
     * @param x the double value of the new token
     */
    public Token( double x ) {
        isDouble = true;
        value = new Double( x );
    }

    /** Returns the name of this Token. Precondition: this Token should
     * represent a String, not a double.
     *
     * @return the name of this Token.
     */
    public String getName() {
        return name;
    }



    /** Returns the value of this Token. Precondition: this Token should
     * represent a double, not a string.
     *
     * @return the value of this Token.
     */
    public Double getValue() {
        return value;
    }



    /** Checks whether this Token contains a double.
     *
     * @return true if this token has a non-null value
     */
    public boolean isDouble() {
        return isDouble;
    }



    /** Checks whether this Token contains a variable.
     *
     * @return true if this token has a non-null name
     */
    public boolean isVariable() {
        return ! isDouble;
    }



    /** Checks whether this Token's name equals <code>s</code>.
     *
     * @param s the string to compare to
     * @return true if this Token's name equals <code>s</code> and false
     * if not
     */
    public boolean equals(String s) {
        return (! isDouble && name.equals(s));
    }



    /** Checks whether this Token's value equals <code>i</code>.
     *
     * @param other the Double to compare to
     * @return true if this Token's value equals <code>i</code> and false
     * if not
     */
    public boolean equals(Double other) {
        return (isDouble && value.doubleValue() == other.doubleValue());
    }



    /** Returns a nice string representation of this Token.
     *
     * @return a string representing this Token
     */
    public String toString() {
        return isDouble ? value.toString() : name;
    }
}
