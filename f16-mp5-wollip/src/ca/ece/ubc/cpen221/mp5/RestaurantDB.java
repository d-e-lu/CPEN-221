package ca.ece.ubc.cpen221.mp5;

// TODO: This class represents the Restaurant Database.
// Define the internal representation and
// state the rep invariant and the abstraction function.

// SQL like. A table is represented as a folder in the filesystem
// a row in a table is a file in a folder

public class RestaurantDB {
	
	/**
	 * Create a database from the Yelp dataset given the names of three files:
	 * <ul>
	 * <li>One that contains data about the restaurants;</li>
	 * <li>One that contains reviews of the restaurants;</li>
	 * <li>One that contains information about the users that submitted reviews.
	 * </li>
	 * </ul>
	 * The files contain data in JSON format.
	 *
	 * @param restaurantJSONfilename
	 *            the filename for the restaurant data
	 * @param reviewsJSONfilename
	 *            the filename for the reviews
	 * @param usersJSONfilename
	 *            the filename for the users
	 */

	/*
	 * creates a database with 3 tables. 
	 * Where each table correspond to a jsonfile
	 */
	private DatabaseInterface db;
	public RestaurantDB(String restaurantJSONfilename, String reviewsJSONfilename, String usersJSONfilename) {
			
		db = new Database(restaurantJSONfilename, reviewsJSONfilename, usersJSONfilename);
	}
	
	/*
	 * returns the table based on the name of the table
	 * restaurants
	 * users
	 * reviews 
	 */
	public TableInterface getTable(String table){
		return db.getTable(table);
	}	
}
