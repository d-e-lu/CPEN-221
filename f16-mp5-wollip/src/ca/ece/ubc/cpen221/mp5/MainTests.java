package ca.ece.ubc.cpen221.mp5;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class MainTests {
	String restaurants = "data" + File.separator + "restaurants.json";
	String reviews = "data" + File.separator + "reviews.json";
	String users = "data" + File.separator + "users.json";
	RestaurantDB db = new RestaurantDB(restaurants, reviews, users);
	TableInterface resTable = db.getTable("restaurants");
	TableInterface reviewTable = db.getTable("reviews");
	TableInterface userTable = db.getTable("users");
	
	//Tests must be run independantly from main function of restaurantDBServer
	@Test
	public void DatabaseTest1(){
		RestaurantDBServer server;
		String x = "";
		try {
			server = new RestaurantDBServer(4949);
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						server.serve();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t.start();
			assertTrue(server.getData("RANDOMREVIEW").equals("Not a valid request. Need to include item as well."));
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		
	}
	@Test
	public void DatabaseTest2(){
		RestaurantDBServer server;
		String x = "";
		try {
			server = new RestaurantDBServer(4949);
			server.serve();
			x = server.getData("RANDOMREVIEW Manhattan Roast & Grill");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		assertFalse(x.equals("ERR: NO_RESTAURANT_FOUND"));
	}
	@Test
	public void DatabaseTest3(){
		RestaurantDBServer server;
		String x = "";
		try {
			server = new RestaurantDBServer(4949);
			server.serve();
			x = server.getData("adduser {\"name\": \"Dylan\"}");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		assertFalse(x.equals("ERR: INVALID_USER_STRING"));
		assertFalse(x.equals("ERR: User was not added"));
	}
	@Test
	public void DatabaseTest4(){
		RestaurantDBServer server;
		String x = "";
		try {
			server = new RestaurantDBServer(4949);
			server.serve();
			x = server.getData("adduser {\"name\": \"Dylan\"}");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		assertFalse(x.equals("ERR: INVALID_USER_STRING"));
		assertFalse(x.equals("ERR: User was not added"));
	}
	@Test
	public void DatabaseTest5(){
		RestaurantDBServer server;
		String x = "";
		String y = "";
		try {
			server = new RestaurantDBServer(4949);
			server.serve();
			x = server.getData("adduser {\"name\": \"Dylan\"}");
			y = server.getData("addreview {\"name\": \"Dylan\"}");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		assertTrue(y.equals("Not a valid request"));
	}
	@Test
	public void DatabaseTest6(){
		RestaurantDBServer server;
		String x = "";
		try {
			server = new RestaurantDBServer(4949);
			server.serve();
			x = server.getData("getrestaurant WSTdK7y1ZDw9JU3NUpFj7g");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		assertFalse(x.equals("ERR: NO_RESTAURANT_FOUND"));
	}
	@Test
	public void DatabaseTest7(){
		RestaurantDBServer server;
		String x = "";
		try {
			server = new RestaurantDBServer(4949);
			server.serve();
			x = server.getData("adduser {\"name\": \"Dylan\", \"text\":\"Hello is this working?\"}");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		assertFalse(x.equals("ERR: INVALID_USER_STRING"));
		assertFalse(x.equals("ERR: User was not added"));
	}
	@Test
	public void DatabaseTest8(){
		RestaurantDBServer server;
		String x = "";
		try {
			server = new RestaurantDBServer(4949);
			server.serve();
			x = server.getData("");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		assertTrue(x.equals("Not a valid request"));
	}
	@Test
	public void DatabaseTest9(){
		RestaurantDBServer server;
		String x = "";
		try {
			server = new RestaurantDBServer(4949);
			server.serve();
			x = server.getData("addRestaurant {\"business_id\":\"sasfdaafsda\",\"full_address\":\"123 main street\"");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		assertTrue(x.startsWith("Restaurant added"));
	}
	@Test
	public void DatabaseTest10(){
		RestaurantDBServer server;
		String x = "";
		try {
			server = new RestaurantDBServer(4949);
			server.serve();
			x = server.getData("adduser {\"business_id\":\"sasfdaafsda\",\"full_address\":\"2518-A Durant AveTelegraph AveBerkeley, CA 94704\"");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		assertTrue(x.equals("ERR: DUPLICATE_RESTAURANT"));
		
	}

}
