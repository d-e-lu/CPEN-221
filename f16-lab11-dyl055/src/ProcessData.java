import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class ProcessData {
	
	
	
	public static void main(String[] args){
		long numUsers = 0;
		OptionalDouble averageConnectivity = null;
		OptionalLong maxReviews =null;
		OptionalDouble averageReviews = null;
		long friendshipLinks = 0;
		
        //read file into stream, try-with-resources
        Map<String,YelpUser> mUsers = new HashMap<String,YelpUser>();
        try (Stream<String> stream = Files.lines(Paths.get("yelp_dataset_challenge_academic_dataset/yelp_academic_dataset_user.json"))) {        	
        	
        	stream.map(JsonSimpleExample->getYelpUser(JsonSimpleExample)).
            forEach(yelpUser -> mUsers.put(yelpUser.userID, yelpUser));
        	
        	
        	maxReviews = mUsers.values().stream().mapToLong(s -> s.numReviews).max();
        	averageReviews = mUsers.values().stream().mapToLong(s -> s.numReviews).average();
        	averageConnectivity = mUsers.values().stream().mapToLong(s -> s.sFriendsList.size()).average();
        	friendshipLinks = mUsers.values().stream().mapToLong(s -> s.sFriendsList.size()).sum();
        	
        	
        	mUsers.values().stream().filter(s -> s.numReviews <10);
        	
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
        numUsers = mUsers.size();
        System.out.println();
        System.out.println("Number of users = " + numUsers);
        System.out.println("Average Connectivity = " + averageConnectivity.getAsDouble());
        System.out.println("Average number of reviews = " + averageReviews.getAsDouble());
        System.out.println("Max number of reviews = " + maxReviews.getAsLong());
        System.out.println("Number of Friendship Links = " + friendshipLinks);
	}
	
	
    private static YelpUser getYelpUser (String jText) {
        YelpUser y = new YelpUser();
        try { 
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(jText);
            JSONObject jsonObject = (JSONObject) obj;

            y.name = (String) jsonObject.get("name");
            y.userID = (String) jsonObject.get("user_id");
            y.numReviews = (Long) jsonObject.get("review_count");
            y.sFriendsList = new ArrayList<String>();

            // loop array - might consume a lot of memory
            JSONArray friends = (JSONArray) jsonObject.get("friends");
            Iterator<String> iterator = friends.iterator();
            while (iterator.hasNext()) {
                  y.sFriendsList.add(iterator.next());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return y;
    }

}
