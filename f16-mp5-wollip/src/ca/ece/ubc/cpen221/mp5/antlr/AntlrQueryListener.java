package ca.ece.ubc.cpen221.mp5.antlr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import ca.ece.ubc.cpen221.mp5.TableInterface;
import ca.ece.ubc.cpen221.mp5.antlr.QueryParser.AddContext;
import ca.ece.ubc.cpen221.mp5.antlr.QueryParser.AtomContext;
import ca.ece.ubc.cpen221.mp5.antlr.QueryParser.CategoryContext;
import ca.ece.ubc.cpen221.mp5.antlr.QueryParser.InContext;
import ca.ece.ubc.cpen221.mp5.antlr.QueryParser.NameContext;
import ca.ece.ubc.cpen221.mp5.antlr.QueryParser.NumbersContext;
import ca.ece.ubc.cpen221.mp5.antlr.QueryParser.OrContext;
import ca.ece.ubc.cpen221.mp5.antlr.QueryParser.PriceContext;
import ca.ece.ubc.cpen221.mp5.antlr.QueryParser.RatingContext;

public class AntlrQueryListener implements QueryListener{

	private Stack<ArrayList<String>> idStack = new Stack<ArrayList<String>>();
	private TableInterface restaurants;
	private int numberLow, numberHigh;
	
	public AntlrQueryListener(TableInterface restaurants){
		this.restaurants = restaurants;
	}
	
	public ArrayList<String> getIDs(){
		if( idStack.size() != 1){
			System.out.println(" there is a problem with the ids stack");
		}
		return idStack.pop();
	}
	
	@Override
	public void enterEveryRule(ParserRuleContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitEveryRule(ParserRuleContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitErrorNode(ErrorNode arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitTerminal(TerminalNode arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterOr(OrContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitOr(OrContext ctx) {
		// TODO Auto-generated method stub
		if( ctx.getChildCount() != 1){
			System.out.println( "OR: Doing Something ");
			int numberOfChildren = (ctx.getChildCount()+1)/2;
			System.out.println("\tThere is " + numberOfChildren +  " children");
			System.out.println("\told stack size: " + idStack.size());
			Set<String> uniques = new HashSet<String>();
			
			for(int i = 0; i < numberOfChildren; i++){
				uniques.addAll( idStack.pop() );
			}
	
			ArrayList<String> returnChild = new ArrayList<String>(uniques);
			idStack.push(returnChild);
			System.out.println("\tnew stack size: " + idStack.size());
		}
	}

	@Override
	public void enterAdd(AddContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAdd(AddContext ctx) {
		// TODO Auto-generated method stub
		if( ctx.getChildCount() != 1){
			System.out.println( "AND: Doing something");
			int numberOfChildren = (ctx.getChildCount()+1)/2;
			System.out.println("\tThere is " + numberOfChildren +  " children");
			System.out.println("\told stack size: " + idStack.size());
			ArrayList<ArrayList<String>> childrenGroups = new ArrayList<ArrayList<String>>();
			ArrayList<String> returnChild = new ArrayList<String>();
			ArrayList<String> mainChild = idStack.pop();
			
			for(int i = 1; i < numberOfChildren; i++){
				childrenGroups.add( idStack.pop() );
			}
			for( String child : mainChild){
				
				boolean and = true;
				for( ArrayList<String> childGroup : childrenGroups){
					if( !childGroup.contains(child) ){
						and = false;
						break;
					}
				}
				if( and ){
					returnChild.add(child);
				}
			}
			
			idStack.push(returnChild);
			System.out.println("\tnew stack size: " + idStack.size());
		}
	}

	@Override
	public void enterAtom(AtomContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAtom(AtomContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterIn(InContext ctx) {
		// TODO Auto-generated method stub
		System.out.println("IN: searching in restaurants/neighborhoods for " + ctx.getChild(1).toString());
		
		String[] searchResult = restaurants.getIDByField("neighborhoods" , ctx.getChild(1).toString() );
		ArrayList<String> ids = null;
		if(searchResult.length == 0){
			ids = new ArrayList<String>();
		}else{
			ids = new ArrayList<String>(Arrays.asList(searchResult));
		}
				
		idStack.push(ids);			
	}

	@Override
	public void exitIn(InContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterCategory(CategoryContext ctx) {
		// TODO Auto-generated method stub
		System.out.println("CATEGORY: searching in restaurants/categories for " + ctx.getChild(1).toString());
		
		String[] searchResult = restaurants.getIDByField("categories" , ctx.getChild(1).toString() );
		ArrayList<String> ids = null;
		if(searchResult.length == 0){
			ids = new ArrayList<String>();
		}else{
			ids = new ArrayList<String>(Arrays.asList(searchResult));

		}
				
		idStack.push(ids);	
	}

	@Override
	public void exitCategory(CategoryContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterPrice(PriceContext ctx) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void exitPrice(PriceContext ctx) {
		// TODO Auto-generated method stub
		System.out.println("PRICE: adding to stack");
		ArrayList<String> ids = new ArrayList<String>();
		
		System.out.println("\twe are adding the following numbers");
		
		int i = numberLow;
		do{
			System.out.println("\t\t" + i);
			String[] searchResult = restaurants.getIDByField("price" , String.valueOf(i) );
			if(searchResult.length != 0)
				ids.addAll( Arrays.asList( searchResult) );
			i++;
		}while( i <= numberHigh);
		
		ids.add( ctx.getChild(1).toString() );		
		idStack.push(ids);	
	}

	@Override
	public void enterRating(RatingContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitRating(RatingContext ctx) {
		// TODO Auto-generated method stub
		System.out.println("RATING: adding to stack");
		ArrayList<String> ids = new ArrayList<String>();
		
		System.out.println("\twe are adding the following numbers");
		for(double i = numberLow; i <= numberHigh; i+=0.5 ){
			System.out.println("\t\t" + i);
			String[] searchResult = restaurants.getIDByField("stars" , String.valueOf(i) );
			if(searchResult != null && searchResult.length != 0 ){
				ids.addAll( Arrays.asList( searchResult) );
			}
		}
		ids.add( ctx.getChild(1).toString() );
		idStack.push(ids);	
	}

	@Override
	public void enterName(NameContext ctx) {
		// TODO Auto-generated method stub
		System.out.println("NAME: searching in restaurants/name for " + ctx.getChild(1).toString());
		
		String[] searchResult = restaurants.getIDByField("name" , ctx.getChild(1).toString() );
		ArrayList<String> ids = null;
		if(searchResult.length == 0){
			ids = new ArrayList<String>();
		}else{
			ids = new ArrayList<String>(Arrays.asList(searchResult));
		}
				
		idStack.push(ids);
	}

	@Override
	public void exitName(NameContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterNumbers(NumbersContext ctx) {
		// TODO Auto-generated method stub
		
		if(ctx.getChildCount() == 1){
			
			numberLow = Integer.parseInt(ctx.getText());
			numberHigh = numberLow;
			
		}else{
			numberLow = Integer.parseInt(ctx.getChild(0).getText());
			numberHigh = Integer.parseInt(ctx.getChild(2).getText());			
		}
		//System.out.println("numberLow: " + numberLow + " numberHigh: " + numberHigh);
	}

	@Override
	public void exitNumbers(NumbersContext ctx) {
		// TODO Auto-generated method stub
		
	}
}
