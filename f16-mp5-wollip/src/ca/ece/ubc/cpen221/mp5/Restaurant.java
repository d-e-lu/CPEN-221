package ca.ece.ubc.cpen221.mp5;

/*
 * a simple restaurant class to store information like
 * the business id
 * the location in terms of longitude and latitude
 * the name of the restaurant
 */
public class Restaurant {
	private String id;
	Location location;
	private String name;
	
	public Restaurant(String id, Location location){
		this.id = id;
		this.location = location;
		this.name = id;
	}
	
	public Restaurant(String id, Location location, String name){
		this(id,  location);
		this.name = name;
	}
	
	public String toString(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	public Location getLocation(){
		return this.location;
	}
}
