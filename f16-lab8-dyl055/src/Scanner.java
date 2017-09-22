import java.io.*;

/** Splits a string representing an expression into tokens. The tokens are
 * read one at a time. Three different methods are used to access the
 * tokens:
 * <ul>
 * <li> <code>getToken()</code> returns the value of the current token
 *      and does not move to the following token.
 * <li> <code>eatToken()</code> moves to the next token in the stream
 *      without looking at the current token.
 * <li> <code>useToken( String tok )</code> compares the current token to
 *      <code>tok</code> and prints an error message if they are not 
 *      identical. It also returns the current token and moves to the next
 *      token in the list.
 * </ul>
 */

public class Scanner {

    /** Where I read my input.*/
    private StreamTokenizer input;

    /** Invariant: curToken always contains the next token; otherwise,
     * there is no more input. */
    private Token curToken;



    /** Creates me, reading my input from s.
     *
     * @param s the string to tokenize
     */
    public Scanner(String s) {
        // Construct a tokenizer on the input string.
        input = new StreamTokenizer( new StringReader( s ));

        // Set some parameters so that we read arithmetic expressions
        // properly.
        input.ordinaryChar('/');
        input.parseNumbers();

        // Read the first token.
        curToken = readNextToken();
    }



    /** Returns and uses the current token; it had better be equal to tok.
     * If it is not, then a warning message is printed. Calling useToken()
     * multiple times steps through the tokens in the input.
     *
     * @param tok what the current token should be
     * @return the current token
     */
    public Token useToken(String tok) {
        // If the next token is not what it should be, print a warning message.
        Token t = curToken;
        if (!t.equals(tok)) {
            System.out.println("Line " + input.lineno() +
                ": Expected \"" + tok + "\" but saw \"" +
                t.toString() + "\"");
        }

        // Move to the next token and return the (old) current one.
        curToken = readNextToken();
        return t;
    }

    /** Returns the current token from the input, but doesn't use it yet.
     * Calling getToken() multiple times does not change the state of
     * the scanner.
     *
     * @return the current token in the string
     * @see #useToken(String)
     */
    public Token getToken() {
        return curToken;
    }



    /** Moves to the next token in the stream. Unlike useToken(), it does
     * not consider the value of the current token and does not return
     * anything. Unlike getToken(), eatToken() does change the state of the
     * scanner.
     *
     * @see #useToken(String)
     * @see #getToken()
     */
    public void eatToken() {
        curToken = readNextToken();
    }



    /** Checks whether all the tokens in this Scanner have been read.
     *
     * @return true if there are more tokens left, and false if not.
     */
    public boolean isEmpty() {
        return (curToken == null);
    }



    /** Returns the next token from the input scanner.
     *
     * @return the next token in the string
     * @see #useToken(String)
     * @see #getToken()
     * @see #eatToken()
     */
    private Token readNextToken() {
        try {
            // Look at the next token from the input.
            int c = input.nextToken();
            switch (c) {

                // We've hit the end of file, so there is no next token --
                // return null.
                case StreamTokenizer.TT_EOF: {
                    return null;
                }

                // The token is a "word" (variable) -- return a token with
                // the variable in its "name" field.
                case StreamTokenizer.TT_WORD: {
                    return new Token( input.sval );
                }

                // The token is an integer -- return a token with the
                // integer in its "value" field.
                case StreamTokenizer.TT_NUMBER: {
                    return new Token( (int)(input.nval) );
                }

                // The token is neither a word nor an integer -- return a
                // token with the string in its "name" field.
                default: {
                    return new Token((char)(input.ttype) + "" );
                }
            }

        // If there was a problem, print an error message and return the
        // previous token.
        } catch (IOException e) {
            System.err.println("Input problem!");
            e.printStackTrace();
            return curToken;
        }
    }

}
