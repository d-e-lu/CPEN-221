package ca.ece.ubc.cpen221.mp5;

public class User {
	private String name;
	public User(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
	
	public String getName(){
		return this.toString();
	}
	
	public String getId(){
		return this.toString();
	}
}
