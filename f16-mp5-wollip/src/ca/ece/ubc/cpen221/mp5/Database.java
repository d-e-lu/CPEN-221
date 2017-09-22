package ca.ece.ubc.cpen221.mp5;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Database implements DatabaseInterface{

	
	private File workSpace;
	private Map<String, Table> tables = new HashMap<String, Table>();

	/*
	 * dataBase is the file path of where the database is going to created
	 * please make sure that databaseName is a folder.
	 */
	public Database(String dataBaseName ){
		workSpace = new File( dataBaseName );	
	}
	
	/*
	 * This is the database constructor that is being used by restaurantDB
	 */
	public Database(String restaurantJSONfilename, String reviewsJSONfilename, String usersJSONfilename) {
		this("database");
		String[] s = { restaurantJSONfilename, reviewsJSONfilename, usersJSONfilename };
		if( workSpace.exists() && workSpace.isDirectory() ){
			loadWorkSpace(workSpace, s);					// if the database exist already (the second time you are running the program, then we will just load the database
		}else{
			System.out.println("no database detected, creating a new one");
			newWorSpace(workSpace, s);						// creates a new database because the old one was deleted or there was never one
		}		
	}
	
	/*
	 * (non-Javadoc)
	 * @see ca.ece.ubc.cpen221.mp5.DataBaseInterface#getTable(java.lang.String)
	 * Param table = the name of the table you want to get
	 * returns the table
	 * returns null if a table by that name is not found is not found
	 */
	@Override
	public TableInterface getTable(String table) {
		return tables.get(table);
	}
	
	/*
	 * Loads an existing database
	 * and make sure all the tables are in there
	 */
	private void loadWorkSpace(File folder, String[] tableNames){
		File[] tables = folder.listFiles();
		Set<String> databaseNames = new HashSet<String>();
		for( File table : tables) {
			databaseNames.add(table.getName());
		}
		for( String tableName : tableNames){
			String name = tableName;
			if(tableName.contains("restaurants.json")){
				name = "restaurants";
			}else if ( tableName.contains("reviews.json")){
				name = "reviews";
			}else if (tableName.contains("users.json")){
				name = "users";
			}
			if( !databaseNames.contains( name )){   // this database is not correct, so we are going to delete it and make a new one
				System.out.println("this database is invalid\n\twe are creating a new database\n\tthis might take a while");
				clearWorkSpace(folder);
				newWorSpace(folder, tableNames);
				return;
			}	
		}
		System.out.println("we are loading an existing database" );
		for( File table : tables){
			if( table.isDirectory() ){
				Table t = new Table(table);
				this.tables.put(table.getName(), t);
			}
		}
		
	}
	
	/*
	 * Creates a new database
	 * and initialize the tables
	 */
	private void newWorSpace(File workSpace, String[] tableNames){
		createWorkSpace(workSpace);

		for( String tableName : tableNames){
			String name = tableName;
			
			if(tableName.contains("restaurants.json")){
				name = "restaurants";
			}else if ( tableName.contains("reviews.json")){
				name = "reviews";
			}else if (tableName.contains("users.json")){
				name = "users";
			}
			
			System.out.println("initializing table: " + name);
			Table t = createTable(workSpace, name);
			t.init(tableName);
				
			tables.put(name, t);
		}
	}
	
	/*
	 * recursively and safely delete an existing database
	 */
	private static void clearWorkSpace( File folder ){
		System.out.println("deleting invalid database");
		if( folder.exists() ){
			File[] files = folder.listFiles();
			if( files != null){
				for( int i = 0; i < files.length; i++){
					if(files[i].isDirectory()){
						clearWorkSpace(files[i]);
					}else{
						files[i].delete();
					}
				}
			}
		}
		
		folder.delete();
	}
	
	/*
	 * Create the folder at the location define in folder
	 */
	private static void createWorkSpace( File folder){
		if(folder.exists()){
			System.out.println("the folder already exist. errrrrrooooooooorrrrrr");
		}else{
			folder.mkdirs();
		}
	}

	/*
	 * Initialize a table within the database
	 */
	private Table createTable( File workspace, String folder){
		Table table = new Table(workspace.getAbsolutePath() + File.separator + folder);
		(new File(table.location())).mkdirs();
		return table;
	}
}

