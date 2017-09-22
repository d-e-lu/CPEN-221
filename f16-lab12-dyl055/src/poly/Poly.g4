grammar Poly;

// This puts "package poly;" for all generated Java files. .
@header {
package poly;
}

// This adds code to the generated lexer and parser. 
// DO NOT CHANGE THESE LINES 
@members {
    // This method makes the lexer or parser stop running if it encounters
    // invalid input and throw a RuntimeException.
    public void reportErrorsAsExceptions() {
        //removeErrorListeners();
        
        addErrorListener(new ExceptionThrowingErrorListener());
    }
    
    private static class ExceptionThrowingErrorListener 
                                              extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                Object offendingSymbol, int line, int charPositionInLine,
                String msg, RecognitionException e) {
            throw new RuntimeException(msg);
        }
    }
}


/*
 * These are the lexical rules. They define the tokens used by the lexer.
 *  Antlr requires tokens to be CAPITALIZED, like START_ITALIC, and TEXT.
 */
VAR : [Xx];
MUL : '*';
POW : '^';
PLUS : '+';
MIN : '-';
NUM : [0-9]+;
WHITESPACE : [ \t\r\n]+ -> skip ;

/*
 * These are the parser rules. They define the structures used by the parser.
 * Antlr requires grammar nonterminals to be lowercase, 
 */
poly : sumterms EOF ;
sumterms : sumterms PLUS term | sumterms MIN term | term ; 
term : NUM MUL VAR POW NUM | NUM MUL VAR | VAR POW NUM | VAR | NUM ;
