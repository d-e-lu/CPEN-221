package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ca.ece.ubc.cpen221.mp5.antlr.Query;

/*
 * Rep invariant: PORT is in between 0 and 65535
 * 				  resTable, reviewTable, userTable initialized before handling clients
 */
public class RestaurantDBServer {

	public static int PORT;
	private ServerSocket serverSocket;
	private static RestaurantDB db;
	private static TableInterface resTable;
	private static TableInterface reviewTable;
	private static TableInterface userTable;
	private static ArrayList<String> addresses = new ArrayList<String>();
	private static ArrayList<String> queryKeywords = new ArrayList<String>();
	public RestaurantDBServer(int port)throws IOException{
		this.serverSocket = new ServerSocket(port);
	}
	
	/**
	 * serve - Constantly checks for new clients to be accepted.
	 * 		   Creates a socket on a new thread to handle client.
	 * @throws IOException
	 */
	public void serve() throws IOException {
		while (true) {
			final Socket socket = serverSocket.accept();
		
			Thread handler = new Thread(new Runnable() {
				public void run() {
					try {
						try {
							handle(socket);
						} finally {
							socket.close();
						}
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			});
			handler.start();
		}
	}
	/**
	 * handle - Handles inputs and outputs between client and server.
	 * @param socket - socket of client
	 * @throws IOException
	 */
	private void handle(Socket socket) throws IOException {
		System.err.println("client connected");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		try {
		
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				System.err.println("request: " + line);
				try {
					String response = getData(line);
					System.out.println("Sending to client: " + response);
					out.print(response);
					out.flush();
				} catch (NumberFormatException e) {
					
					System.err.println("reply: err");
					out.println("err\n");
				}
			}
		} finally {
			out.close();
			in.close();
		}
		
	}
	/**
	 * getData - gets the data that the user wants and
	 * 			 returns it as a string if the database
	 * 			 contains it.
	 * @param request - request sent in by client
	 * @return response from server about request
	 */
	public static String getData(String request){
        String[] requestArr = request.split(" ");
        if(requestArr.length <= 1){
        	return "Not a valid request. Need to include item as well.";
        }
        System.out.println(request);
        switch(requestArr[0].toUpperCase()){
        case "RANDOMREVIEW":
        	return getRandomReview(requestArr);
        case "GETRESTAURANT":
        	return getRestaurant(requestArr[1]);
        case "ADDUSER":
        	return addUser(request.split(" ", 2)[1].trim());
        case "ADDRESTAURANT":
        	return addRestaurant(request.split(" ", 2)[1].trim());
        case "ADDREVIEW":
        	return addReview(request.split(" ", 2)[1].trim());
        case "QUERY":
        	return getQuery(request);
        default:
        	return "Not a valid request";
        }
	}
	/**
	 * 
	 * @param restaurantName - String array containing request at array[0] and
	 * 						   the restaurant name separated by whitespace in 
	 * 						   the following indices.
	 * @return response from server about request either containing data that user wants
	 * 		   or an error
	 */
	private static String getRandomReview(String[] restaurantName){
		StringBuilder builder = new StringBuilder();
	
		for (int i = 1; i < restaurantName.length;i++) {
		    if (builder.length() > 0) {
		        builder.append(" ");
		    }
		    builder.append(restaurantName[i]);
		}
		System.out.println(restaurantName);
		String name = builder.toString();
		name = name.trim();	
		System.out.println(name);
		String[] restaurants = resTable.getIDByField("name", name);
		
		if(restaurants != null){
			if(restaurants.length > 1) return "ERR: MULTIPLE_RESTAURANTS";
			else if(restaurants.length == 0) return "ERR: NO_RESTAURANTS_FOUND";
			else{
				if(resTable.get(restaurants[0], "business_id") instanceof Object){
					String resID = restaurants[0];
					String[] reviews = reviewTable.getIDByField("business_id", resID);
					int rand  = ThreadLocalRandom.current().nextInt(0, reviews.length);
					return reviewTable.getJSON(reviews[rand]);
				}
			}
		}
	
		return "ERR: NO_RESTAURANT_FOUND";
		//Manhattan Roast & Grill
		//WSTdK7y1ZDw9JU3NUpFj7g
	}
	/**
	 * getRestaurant - gets info about a restaurant
	 * @param id - business ID of restaurant
	 * @return a String in JSON format with info about the restaurant if it exists
	 */
	private static String getRestaurant(String id){
		String restaurant = resTable.getJSON(id);
		
		if(restaurant ==null){
			return "ERR: NO_RESTAURANT_FOUND";
		}
		return restaurant;
	}
	/**
	 * 
	 * @param json - a String in the JSON format to be added to the usertable in the database
	 * @return String whether adding a user was successful or not
	 * @effects - it may modify userTable by adding another row
	 */
	private static String addUser(String json){
		JSONParser s = new JSONParser();
		
		JSONObject o;
		try {
			ArrayList<String>ids = userTable.getIdSet();
			String id = getRandId(10);
			while(ids.contains(id)){
				id = getRandId(10);
			}
			o = (JSONObject) s.parse(json);
			
			String name = (String) o.get("name");
			JSONObject user = new JSONObject();
			
			JSONObject votes = new JSONObject();
			votes.put("cool", 0);
			votes.put("useful", 0);
			votes.put("funny", 0);
			
			
			user.put("url", "http://www.yelp.com/user_details?userid="+id);
			user.put("votes", votes);	
			user.put("review_count", 0);
			user.put("type", "user");
			user.put("user_id", id);
			user.put("name", name);
			user.put("average_stars", 0);
			System.out.println(user);
			if(userTable.addRow(user)){
				return "User added" + user;
			}
			return "ERR: User was not added";
		} catch (org.json.simple.parser.ParseException e) {
			return "ERR: INVALID_USER_STRING";
			
		}

	}
	private static String getRandId(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_-1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	/**
	 * 
	 * @param json - a String in the JSON format to be added to the restable in the database
	 * @return String whether adding a user was successful or not
	 * @effects - it may modify resTable by adding another row
	 */
	private static String addRestaurant(String json){
		if(addresses.isEmpty()){
			ArrayList<String> ids = resTable.getIdSet();
			for(String id : ids){
				String location  = (String) resTable.get(id, "full_address");
				addresses.add(location);
			}
		}
		JSONParser s = new JSONParser();
		System.out.println(json);
		try {
			JSONObject o = (JSONObject) s.parse(json);
			for(String address: addresses){
				if(o.get("full_address").equals(address)){
					return "ERR: DUPLICATE_RESTAURANT";
				}
			}
			System.out.println(o);
			if(resTable.addRow(o)){
				addresses.add((String) o.get("full_address"));
				return "Restaurant added" + o;
			}
			else{
				return "ERR: Restaurant was not added";
			}
			
		} catch (org.json.simple.parser.ParseException e) {
			return "ERR: INVALID_RESTAURANT_STRING";
			
		}
	}
	/**
	 * 
	 * @param json - a String in the JSON format to be added to the reviewtable in the database
	 * @return String whether adding a user was successful or not
	 * @effects - it may modify reviewTable by adding another row
	 */
	private static String addReview(String json){
		JSONParser s = new JSONParser();
		ArrayList<String> userIds = userTable.getIdSet();
		ArrayList<String> resIds = resTable.getIdSet();
		ArrayList<String> revIds = reviewTable.getIdSet();
	
		try {
			JSONObject o = (JSONObject) s.parse(json);
			if(o.get("user_id")==null || !userIds.contains(o.get("user_id"))){
				return "ERR: NO_SUCH_USER";
			}
			if(o.get("business_id")== null || !resIds.contains(o.get("business_id"))){
				return "ERR: NO_SUCH_RESTARAUNT";
			}
			if(o.get("text")==null){
				o.put("text","");
			}
			if(o.get("stars")==null){
				o.put("stars", 0);
			}
			JSONObject review = new JSONObject();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE,1);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			String formatted = format1.format(cal.getTime());
			String revId = getRandId(10);
			while(revIds.contains(revId)){
				revId = getRandId(10);
			}
			JSONObject votes = new JSONObject();
			votes.put("funny", 0);
			votes.put("useful", 0);
			votes.put("cool", 0);
			
			review.put("date", formatted);
			review.put("review_id", revId);
			review.put("user_id", o.get("user_id"));
			review.put("text", o.get("text"));
			review.put("stars", o.get("stars"));
			review.put("type", "review");
			review.put("business_id",o.get("business_id"));
			review.put("votes", votes);
			System.out.println(review);
			if(reviewTable.addRow(review)){
				return "Review added" + review;
			}
			return "ERR: Review was not added";
		} catch (org.json.simple.parser.ParseException e) {
			return "ERR: INVALID_REVIEW_STRING";
		}
		
	}
	private static String getQuery(String query){
		
		String queries = query.split(" ", 2)[1];
		Query q = new Query(resTable);
		ArrayList<String> restaurants = q.queryToIDs(queries);
		StringBuilder sb = new StringBuilder();
		for (String r : restaurants)
		{
		    sb.append(r);
		    sb.append("\n");
		}

		return sb.toString();
		
		
	}
	public static void main(String[] args) {
		String restaurants = "data" + File.separator + "restaurants.json";
		String reviews = "data" + File.separator + "reviews.json";
		String users = "data" + File.separator + "users.json";
		
		
		if(args.length == 4){
			PORT = Integer.parseInt(args[0]);
			restaurants = args[1];
			reviews = args[2];
			users = args[3];
		}
		else if(args.length == 1){
			PORT = Integer.parseInt(args[0]);
		}
		else {
			PORT = 4949;
		}
		
		System.out.println("Loading database...");
		db = new RestaurantDB(restaurants, reviews, users);
 		resTable = db.getTable("restaurants");
		reviewTable = db.getTable("reviews");
		userTable = db.getTable("users");
		try {
			System.out.println("Server has started");
			RestaurantDBServer server = new RestaurantDBServer(PORT);
			server.serve();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
}
