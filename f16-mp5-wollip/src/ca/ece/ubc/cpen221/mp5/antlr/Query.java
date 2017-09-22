package ca.ece.ubc.cpen221.mp5.antlr;

import java.io.File;
import java.util.ArrayList;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.TableInterface;

public class Query {
	public static void main(String args[]){
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		// getting individual json files back
		TableInterface restaurants = database.getTable("restaurants");
		//q.queryToIDs("category(Pakistani)")
		Query q = new Query(restaurants);
		for( String s : q.queryToIDs("in(Telegraph Ave) && (category(Chinese) || category(Italian)) && price(1..2)"))
			System.out.println("id: " + s);
		
	
		
	}
	
	private TableInterface restaurants;
	
	// Note that range is inclusive of both the upper bounds and lower bounds.
	public Query(TableInterface restaurants){
		this.restaurants = restaurants;
	}
	
	public ArrayList<String> queryToIDs(String query){
		ANTLRInputStream input = new ANTLRInputStream(query);
		QueryLexer lexer = new QueryLexer(input);
		TokenStream tokens = new CommonTokenStream(lexer);
		QueryParser parser = new QueryParser(tokens);
		ParseTree tree = parser.or();
		//System.out.println(tree.toStringTree(parser));
		
		ParseTreeWalker walker = new ParseTreeWalker();
		QueryListener listener = new AntlrQueryListener(restaurants);
		walker.walk(listener, tree);
		return ((AntlrQueryListener) listener).getIDs() ;
	}
}
