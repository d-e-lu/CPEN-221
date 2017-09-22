// Generated from Query.g4 by ANTLR 4.5.3
package ca.ece.ubc.cpen221.mp5.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link QueryParser}.
 */
public interface QueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link QueryParser#or}.
	 * @param ctx the parse tree
	 */
	void enterOr(QueryParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#or}.
	 * @param ctx the parse tree
	 */
	void exitOr(QueryParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#add}.
	 * @param ctx the parse tree
	 */
	void enterAdd(QueryParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#add}.
	 * @param ctx the parse tree
	 */
	void exitAdd(QueryParser.AddContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(QueryParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(QueryParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#in}.
	 * @param ctx the parse tree
	 */
	void enterIn(QueryParser.InContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#in}.
	 * @param ctx the parse tree
	 */
	void exitIn(QueryParser.InContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#category}.
	 * @param ctx the parse tree
	 */
	void enterCategory(QueryParser.CategoryContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#category}.
	 * @param ctx the parse tree
	 */
	void exitCategory(QueryParser.CategoryContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#price}.
	 * @param ctx the parse tree
	 */
	void enterPrice(QueryParser.PriceContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#price}.
	 * @param ctx the parse tree
	 */
	void exitPrice(QueryParser.PriceContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#rating}.
	 * @param ctx the parse tree
	 */
	void enterRating(QueryParser.RatingContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#rating}.
	 * @param ctx the parse tree
	 */
	void exitRating(QueryParser.RatingContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(QueryParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(QueryParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#numbers}.
	 * @param ctx the parse tree
	 */
	void enterNumbers(QueryParser.NumbersContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#numbers}.
	 * @param ctx the parse tree
	 */
	void exitNumbers(QueryParser.NumbersContext ctx);
}