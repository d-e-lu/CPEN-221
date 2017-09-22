package ca.ece.ubc.cpen221.mp5;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;


public class DatabaseTest {
	

	@Test
	public void DatabaseInit(){
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface reviews = database.getTable("reviews");
		TableInterface users = database.getTable("users");
		TableInterface restaurants = database.getTable("restaurants");
		assertNotEquals(reviews, null);
		assertNotEquals(users, null);
		assertNotEquals(restaurants, null);
	}
	
	@Test
	public void TableGetIds(){
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface reviews = database.getTable("reviews");
		
		ArrayList<String> reviewIDs = reviews.getIdSet();
		assertNotEquals(reviewIDs, null);
	}
	
	@Test
	public void TableGetFields1(){
		// invalid Id
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface reviews = database.getTable("reviews");
		String[] fields = {"date", "stars"};
		Object[] os = reviews.get("does not exisit", fields);
		for(Object o : os){
			assertEquals(o, null);
		}
	}
	
	@Test
	public void TableGetFields2(){
		// invalid fields
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface reviews = database.getTable("reviews");
		String[] fields = {"doesnt exist", "doesnt exist2"};
		Object[] os = reviews.get("__hd9lzOjpvG5o3MV_ow-Q", fields);
		for(Object o : os){
			assertEquals(o, null);
		}
		assertEquals( os.length, fields.length );
		
	}
	
	
	@Test
	public void TableGetFields3(){
		// valid
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface reviews = database.getTable("reviews");
		String[] fields = {"date", "stars"};
		Object[] os = reviews.get("__hd9lzOjpvG5o3MV_ow-Q", fields);
		assertTrue( os[1] instanceof Long);
		assertEquals( String.valueOf(os[1]) , "4");
		assertTrue( os[0] instanceof String);
		assertEquals(String.valueOf(os[0]), "2009-09-12");
		assertEquals( os.length, fields.length );
	}
	
	@Test
	public void TableGetField1(){
		// invalid Id
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface reviews = database.getTable("reviews");
	
		Object o = reviews.get("does not exist", "date");
		
		assertEquals(o, null);
		
	}
	
	@Test
	public void TableGetField2(){
		// invalid fields
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface reviews = database.getTable("reviews");
		
		Object o = reviews.get("__hd9lzOjpvG5o3MV_ow-Q", "does not exist");
		
		assertEquals(o, null);		
	}
	
	
	@Test
	public void TableGetField3(){
		// valid
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface reviews = database.getTable("reviews");
	
		Object o = reviews.get("__hd9lzOjpvG5o3MV_ow-Q", "date");
		assertTrue( o instanceof String);
		assertEquals( String.valueOf(o), "2009-09-12");
		
	}
	
	@Test
	public void TableGetRows1(){
		// valid
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface reviews = database.getTable("reviews");
		Map<String, Object> row = reviews.getRow("__hd9lzOjpvG5o3MV_ow-Q");
	
		assertEquals( String.valueOf(row.get("date")), "2009-09-12");
		assertEquals( String.valueOf(row.get("type")), "review");
	}
	
	@Test
	public void TableGetRows2(){
		// invalid id
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface reviews = database.getTable("reviews");
		Map<String, Object> row = reviews.getRow("does not exist");
	
		assertEquals( row.get("date"), null);
		assertEquals( row.get("type"), null);
	}
	
	@Test
	public void  TableGetJson1(){
		// valid id
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface users = database.getTable("users");
		String jsonString = users.getJSON("__05rytNjsye9MBhqB0DMA");
		System.out.println(jsonString);
		JSONParser parse = new JSONParser();
		try {
			JSONObject row = (JSONObject) parse.parse(jsonString);
			
			assertEquals( String.valueOf(row.get("type")), "user");
			assertEquals( String.valueOf(row.get("user_id")), "__05rytNjsye9MBhqB0DMA");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void  TableGetJson2(){
		// invalid id
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface users = database.getTable("users");
		String jsonString = users.getJSON("does not exist");
		System.out.println(jsonString);
		JSONParser parse = new JSONParser();
		try {
			JSONObject row = (JSONObject) parse.parse(jsonString);
			
			assertEquals( String.valueOf(row.get("type")), "null");
			assertEquals( String.valueOf(row.get("user_id")), "null");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void TableAddRow(){
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface users = database.getTable("users");
		JSONParser parse = new JSONParser();
		JSONObject json;
		long time = System.currentTimeMillis();
		try {
			json = (JSONObject) parse.parse("{\"votes\": {\"funny\": 35, \"useful\": 21, \"cool\": 14},"+
					"\"review_count\":" + time + ", \"type\": \"user\", \"user_id\": \"" + time + "\", \"name\": \"Chris M.\"," +
					"\"average_stars\": 3.89655172413793}");

			assertFalse(users.addRow(json));
			json = (JSONObject) parse.parse("{\"url\": \"afwe\", \"votes\": {\"funny\": null, \"useful\": 21, \"cool\": 14},"+
					"\"review_count\":" + time + ", \"type\": \"user\", \"user_id\": \"" + time + "\", \"name\": \"Chris M.\"," +
					"\"average_stars\": 3.89655172413793}");
			assertFalse( users.addRow(json));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void TableUltimateTest(){
		// going to add a user to the database
		// and then find the user based on their id and by the value of their fields
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		
		TableInterface users = database.getTable("users");
		JSONParser parse = new JSONParser();
		JSONObject json;
		try {
			long time = System.currentTimeMillis();
			json = (JSONObject) parse.parse("{\"url\": \"gXog\", \"votes\": {\"funny\": 35, \"useful\": 21, \"cool\": 14},"+
					"\"review_count\":" + time + ", \"type\": \"user\", \"user_id\": \"" + time + "\", \"name\": \"Chris M.\"," +
					"\"average_stars\": 3.89655172413793}");
			users.addRow(json);
			assertTrue( users.getIdSet().contains( String.valueOf(time) ) );
			String[] ids = users.getIDByField("review_count", String.valueOf(time));
			assertEquals( 1 , ids.length );
			assertEquals( String.valueOf(time) , ids[0]);
		} catch (ParseException e) {
			
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}

	}
}
