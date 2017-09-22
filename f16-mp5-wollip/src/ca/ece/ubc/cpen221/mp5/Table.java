package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Table implements TableInterface{
	/*
	 * Table is a section of the database.
	 * It stores just one set of information
	 * for example it can store all the information from
	 * reviews.json
	 * restaurants.json
	 * users.json
	 */
	
	private enum Type{
		Double, Long, String, Boolean, Array, Null
	}
	
	private String filelocation;	
	private String idField;
	private ArrayList<String> meta = new ArrayList<String>();
	private ArrayList<Type> type = new ArrayList<Type>();
	private ArrayList<String> IdSet = new ArrayList<String>();
	private Map<String, Map<String, File>> relations = new HashMap<String, Map<String, File>>();
	
	
	// loading an existing table that was create from the previous instance of this program.
	// folder should be the location of table that was create by this program.
	public Table(File folder){
		this(folder.getPath());
		System.out.println("loading table " + folder.getName());
		File[] rows = folder.listFiles();
		
		
		for( File r : rows){  //just going through the existing database
			
			if( r.getName().compareTo("___meta.txt") == 0 ){  // getting information for meta and type
				
				try {
					Stream<String> stream = Files.lines( Paths.get(r.getAbsolutePath()) );
					Iterator<String> i = stream.iterator();
					if( i.hasNext() ) idField = i.next();
					
					while( i.hasNext() ){
						String[] s = i.next().split(" ");
						meta.add(s[0]);
					
						
						
						switch(s[1]){
						case "String":
							type.add(Type.String);
							break;
						case "Boolean":
							type.add(Type.Boolean);
							break;
						case "Long":
							type.add(Type.Long);
							break;
						default:   // this should only happen if someone tempered with the files within the existing database
							System.out.println( "Create RestaurantDB -> Create Table -> load exisiting data -> reading __meta__ -> figuring out type");
							System.out.println( s[1] + " of the input string is not a known format");
						case "Array":
							type.add( Type.Array);
							break;
						case "Double":
							type.add(Type.Double);
							break;
						}
					}
					
					stream.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				//for(String s: meta)System.out.println(s);
			}else if( r.isDirectory() ){ 	// this the relational database that is created when using getIDByfield function
				
				File[] folders = r.listFiles();
				for( File f1 : folders){
					if( f1.isDirectory() ){
						Map<String, File> relation = new HashMap<String, File>();
						File[] f1s = f1.listFiles();
						for( File f2 : f1s){
							relation.put( f2.getName(), f2);
						}
						relations.put(f1.getName(), relation);
					}else{
						System.out.println("There is a weird file in the relation part of the database");
					}
				}
			}else{ // creating a list of the all the data points currently in the database
				IdSet.add(r.getName());
			}
		}
	}
	
	
	// we are going to create a table 
	// filelocation is the location it will be in.
	// we will create a folder at this location and 
	public Table(String filelocation){
		this.filelocation = filelocation;
		File f = new File(filelocation + File.separator + "relations");
		if ( !f.exists()){
			f.mkdirs();
		}
	}
	
	//we are going to use the jsonfile to create a new table.
	//JSONfile is the location of the JSONfile
	public void init(String JSONfile){
		if( JSONfile.equals("data" + File.separator + "restaurants.json")){ // need to add figure out which field should be used for identifyind data.
			idField = "business_id";
		}else if( JSONfile.equals("data" + File.separator + "users.json")){
			idField = "user_id";
		}else if( JSONfile.equals("data" + File.separator + "reviews.json")){
			idField = "review_id";
		}																					
		
		try {
			Stream<String> stream = Files.lines( Paths.get(JSONfile) );
			JSONParser parse = new JSONParser();
			ArrayList<JSONObject> json = new ArrayList<JSONObject>();
			stream.forEach(s -> {
				try {
					json.add( (JSONObject) parse.parse(s) );
				} catch (ParseException e) {
					e.printStackTrace();
				}
			});
			createMeta(json.get(0));			
			
			File outputFile = new File( filelocation + File.separator + "___meta.txt");
			ArrayList<String> outputs = new ArrayList<String>();
			outputs.add(idField);
			for( int i = 0; i < meta.size(); i++){
				outputs.add( meta.get(i) + " " + String.valueOf(type.get(i)));
			}
			try {
				Files.write(outputFile.toPath(), outputs);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for( JSONObject o : json){
				parse(o);
			}
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// create the expected format of future jsonobjects based on this object
	private void createMeta(JSONObject j){
		createMetaHelper(j, "");
	}
	
	private void createMetaHelper(JSONObject j, String header){
		@SuppressWarnings("unchecked")
		Set<String> set = j.keySet();
		for(String s : set){
			Object o = j.get(s);
			if( o instanceof JSONObject){
				createMetaHelper((JSONObject) o, header + s + "-|>");
			}else{
				
				if(header != ""){
					meta.add( header + s);					
				}else{
					meta.add(s);
				}
				if(o instanceof Double){
					type.add(Type.Double);
				}else if( o instanceof Long){
					type.add(Type.Long);
				}else if( o instanceof Boolean){
					type.add(Type.Boolean);
				}else if( o instanceof String){
					type.add(Type.String);
				}else if( o instanceof JSONArray){
					type.add(Type.Array);
				}else{
					System.out.println("cant figure out type of o");
					System.out.println(o.getClass());
					type.add(Type.Null);
				}
					
			}
				
		}
	}
	
	private void parse(JSONObject json){
		parseHelper(json, false);
	}
	
	// add this json object to the table.
	private void parseHelper(JSONObject json, boolean addToRelations){
		ArrayList<String> write = new ArrayList<String>();
		String ID = (String) json.get(idField);
		
		for(String s : meta){
			Object o;
			
			if( s.contains("-|>") ){
				String[] s1 = s.split("-|>");
				o = json.get(s1[0]);
				JSONObject j = (JSONObject) o;
				o = j.get(s1[2]);
				
			}else{
				o = json.get(s);
			}
			
			if( o instanceof JSONArray ){
				JSONArray a = (JSONArray) o;
				String r = "";
				for( int i = 0; i < a.size(); i++){
					if(r == ""){
						r = (String) a.get(i);
					}else{
						r = r + "+++" + (String) a.get(i);
					}
				}
				o = r;
			}
			
			if(o instanceof JSONObject || o instanceof JSONArray){
				System.out.println("error is parsing in table");
			}
			
			if(addToRelations && relations.containsKey(s)){
				Map<String, File> relation = relations.get(s);
				
				String[] values = String.valueOf(o).split("\\+\\+\\+");
				for(String value : values){
					System.out.println("value: " + value + ID);
					value = value.replace("/", "&slash&");
					if( relation.containsKey(value) ){
						System.out.println("appending" + value + ID);
						
						try {
					        @SuppressWarnings("resource")
							BufferedWriter bw = new BufferedWriter(new FileWriter(relation.get(value), true));
							bw.write(ID);
							bw.newLine();
							bw.flush();
					     } catch (IOException ioe) {
					    	 ioe.printStackTrace();
					     }
					}else{
						System.out.println("attempting to add new index an existing relational table" + ID + value);
						File outputFile = new File( filelocation + File.separator + "relations" + File.separator + s + File.separator + value);
						try {
							Files.write(outputFile.toPath(), ID.getBytes());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						relation.put(value, outputFile);
					}
				}
			}
			
			write.add( String.valueOf(o).replaceAll("(\\r|\\n)", "") );
		}
		for(Entry<String, Map<String, File>> e : relations.entrySet()){
			Object value = json.get(e.getKey());
			if( value instanceof JSONArray){
				
			}else{
				
			}
		}
		File outputFile = new File( filelocation + File.separator +  ID);
		try {
			Files.write(outputFile.toPath(), write);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IdSet.add(ID);
	}
	
	// return a list of all the different data ids in this table
	public synchronized ArrayList<String> getIdSet(){
		return IdSet;
	}
	
	/*
	 * (non-Javadoc)
	 * @see ca.ece.ubc.cpen221.mp5.TableInterface#get(java.lang.String, java.lang.String[])
	 * always return an array of equal length with fields
	 * the index of the value of the field is equal to the index of the field
	 * if the fields is not valid, the corresponding output will be null
	 * if id is invalid, array of null will be returned
	 */
	public Object[] get(String id, String[] fields){
		Object[] os = new Object[fields.length];
		File f = new File(filelocation + File.separator + id);
		if( !IdSet.contains(id) || !f.exists() || !f.isFile()){
			return os;
		}
		try {
			Stream<String> stream = Files.lines( Paths.get(f.getAbsolutePath()) );
			String[] lines = stream.toArray(size -> new  String[size]);
			
			for(int i = 0; i < fields.length; i++){
				int index = meta.indexOf(fields[i]);
				if( index == -1 ) continue;
				String o = lines[index];
				switch( type.get(index) ){
				case Double:
					os[i] =  Double.parseDouble(o);
					break;
				case Long:
					os[i] = Long.parseLong(o);
					break;
				case Boolean:
					os[i] = Boolean.parseBoolean(o);
					break;
				case String:
				case Array:
				default:
					os[i] = o;
				}
			}
			stream.close();
		} catch (IOException e) {
			System.out.println(" failed to open the file??!?! ");
			e.printStackTrace();
		}
		return os;
	}
	
	/*
	 * (non-Javadoc)
	 * @see ca.ece.ubc.cpen221.mp5.TableInterface#get(java.lang.String, java.lang.String)
	 * returns a null if id or field is not valid
	 */
	public Object get(String id, String field){
		String[] s = {field};
		return this.get(id, s)[0];
	}
	
	/*
	 * (non-Javadoc)
	 * @see ca.ece.ubc.cpen221.mp5.TableInterface#getRow(java.lang.String)
	 * return a map of all the fields of the data with the given ID
	 * the values of the map will be null if the ID is invalid.
	 */
	@Override
	public Map<String, Object> getRow(String ID) {
		Map<String, Object> row = new HashMap<String, Object>();
		Object[] os = this.get(ID, meta.toArray(new String[meta.size()]));
		for( int i = 0; i < meta.size(); i++){
			row.put(meta.get(i), os[i]);
		}
		return row;
	}
	
	/*
	 * (non-Javadoc)
	 * @see ca.ece.ubc.cpen221.mp5.TableInterface#getJSON(java.lang.String)
	 * returns the a JSONObject in the form of a string
	 * There is no guarantee about the order of the fields, but the data type for each field will be the same
	 */
	public String getJSON( String id){
		boolean comma = false;
		StringBuilder json = new StringBuilder("{");
		Map<String, Map<String, Object>> internalObjects= new HashMap<String, Map<String, Object>>();
		
		for( Entry<String, Object> e : getRow(id).entrySet()){
			if( e.getKey().contains("-|>")){
				if (internalObjects.containsKey( e.getKey().split("-|>")[0] )){
					Map<String, Object> temp = internalObjects.get( e.getKey().split("-|>")[0] );
					temp.put(e.getKey().split("-|>")[2], e.getValue());
				}else{
					Map<String, Object> temp = new HashMap<String, Object>();
					temp.put(e.getKey().split("-|>")[2], e.getValue());
					internalObjects.put(e.getKey().split("-|>")[0], temp);
				}
			}else{
				if(comma){
					json.append(",");
				}else{
					comma = true;
				}
				
				if( type.get(meta.indexOf(e.getKey())) == Type.Array ){
					json.append( "\"" + e.getKey() + "\": [");
					boolean internalComma = false;
					for( String s : ((String) e.getValue()).split("\\+\\+\\+")){
						if(internalComma){
							json.append(",");
						}else{
							internalComma = true;
						}
						json.append( "\"" + s + "\"");
					}
					json.append("]");
				}else if ( type.get(meta.indexOf(e.getKey())) == Type.String) {
					json.append( "\"" + e.getKey() + "\": \"" + e.getValue() + "\"" );	
				}else{
					json.append( "\"" + e.getKey() + "\": " + e.getValue());
				}
				
			}
			
		}
		
		for(Entry<String, Map<String, Object>> e : internalObjects.entrySet()){
			if(comma){
				json.append(",");
			}else{
				comma = true;
			}
			json.append( "\"" + e.getKey() + "\": " + getJSONObjectHelper(e.getValue()));
		}
		json.append("}");
		return json.toString();
	}
	
	private String getJSONObjectHelper(Map<String, Object> stuff){
		boolean comma = false;
		StringBuilder json = new StringBuilder("{");
		for(Entry<String, Object> e : stuff.entrySet()){
			if(comma){
				json.append(",");
			}else{
				comma = true;
			}
			json.append( "\"" + e.getKey() + "\": " + e.getValue());
		}
		json.append("}");
		return json.toString();
	}
	
	public String toString(){
		return filelocation;
	}
	
	/*
	 * (non-Javadoc)
	 * @see ca.ece.ubc.cpen221.mp5.TableInterface#location()
	 * return file location
	 */
	public String location(){
		return this.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see ca.ece.ubc.cpen221.mp5.TableInterface#addRow(org.json.simple.JSONObject)
	 * returns false if json is not in the expected format.
	 */
	@Override

	public synchronized boolean addRow(JSONObject json) {
		for( String field : meta){
			
			if(field.contains("-|>")){
				String[] fieldArr = field.split(Pattern.quote("-|>"));
				field = fieldArr[0];
				String rest = fieldArr[1];

				JSONObject obj = (JSONObject) json.get(field);

				if(null ==  obj.get(rest)){
					return false;
				}
			}
			else if( null == json.get(field)){
				return false;
			}
		}
		parseHelper(json, true);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see ca.ece.ubc.cpen221.mp5.TableInterface#getIDByField(java.lang.String, java.lang.String)
	 * if this is the first time searching by the field,
	 * it may take a while
	 * because we are creating a relational table
	 * return null if there is no match
	 */

	public synchronized String[] getIDByField(String searchField, String match){
		match = match.replace("/", "&slash&");
		if( relations.containsKey(searchField) ){

			Map<String, File> relation = relations.get(searchField);
			
			if( relation.containsKey(match) ){
	
				
				File f = relation.get(match);
				try{
					Stream<String> stream = Files.lines( Paths.get(f.getAbsolutePath()) );
					String[] lines = stream.toArray(size -> new String[size]);
					stream.close();
					return lines;
				}catch( IOException e){
					e.printStackTrace();
				}
				
			}else{
				//addToRelationTable();
			}
		}
		else{
			createRelationDatabase(searchField);
			return getIDByField(searchField, match);
			
		}
		
		return null;
	}
	
	private void createRelationDatabase(String field){
		System.out.println("creating new relation table");
		String relationLocation = filelocation + File.separator + "relations" + File.separator + field;
		System.out.println("at location: " + relationLocation);
		File f = new File(relationLocation);
		f.mkdirs();
		Map<Object, ArrayList<String>> relation = new HashMap<Object, ArrayList<String>>();
		
		if( type.get( meta.indexOf(field)) == Type.Array){
			for( String id : IdSet){
				String o = (String) this.get(id, field);
				String[] values = o.split("\\+\\+\\+");
				for(String value : values){
					value = value.replace("/", "&slash&");
					//System.out.println(value);
					if( relation.containsKey(value) ){
						ArrayList<String> a = relation.get(value); // this arraylist contains ids with all the same field
						a.add(id);
					}else{
						ArrayList<String> a = new ArrayList<String>();
						a.add(id);
						relation.put(value, a);
					}
				}
			}
		}else{
			for(String id : IdSet){
				Object o = this.get(id, field);
				if( relation.containsKey(o) ){
					ArrayList<String> a = relation.get(o); // this arraylist contains ids with all the same field
					a.add(id);
				}else{
					ArrayList<String> a = new ArrayList<String>();
					a.add(id);
					relation.put(o, a);
				}
			}
		}
		
		Map<String, File> m = new HashMap<String, File>();
		for(Entry<Object, ArrayList<String>> e : relation.entrySet()){
			File entry = new File(relationLocation + File.separator + String.valueOf(e.getKey()));
			try {
				Files.write(entry.toPath(), e.getValue());
			} catch (IOException efea) {
				// TODO Auto-generated catch block
				efea.printStackTrace();
				System.out.println("attempting to write file: " + entry.toPath());
			}
			m.put(String.valueOf(e.getKey()), entry);
		}
		
		relations.put(field, m);
	}
}
