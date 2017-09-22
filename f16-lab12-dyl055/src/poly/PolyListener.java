// Generated from Poly.g4 by ANTLR 4.4

package poly;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PolyParser}.
 */
public interface PolyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PolyParser#poly}.
	 * @param ctx the parse tree
	 */
	void enterPoly(@NotNull PolyParser.PolyContext ctx);
	/**
	 * Exit a parse tree produced by {@link PolyParser#poly}.
	 * @param ctx the parse tree
	 */
	void exitPoly(@NotNull PolyParser.PolyContext ctx);
	/**
	 * Enter a parse tree produced by {@link PolyParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(@NotNull PolyParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link PolyParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(@NotNull PolyParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link PolyParser#sumterms}.
	 * @param ctx the parse tree
	 */
	void enterSumterms(@NotNull PolyParser.SumtermsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PolyParser#sumterms}.
	 * @param ctx the parse tree
	 */
	void exitSumterms(@NotNull PolyParser.SumtermsContext ctx);
}