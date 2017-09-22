package ca.ece.ubc.cpen221.mp5;

public interface DatabaseInterface {
	/*
	 * returns the table with the given name
	 */
	public TableInterface getTable(String table);
}
