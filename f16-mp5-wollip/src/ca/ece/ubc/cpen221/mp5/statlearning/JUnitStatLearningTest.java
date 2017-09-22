package ca.ece.ubc.cpen221.mp5.statlearning;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.TableInterface;
import ca.ece.ubc.cpen221.mp5.User;

public class JUnitStatLearningTest {
	
	@Test
	public void Clusters(){
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		List<Set<Restaurant>> km = (ArrayList<Set<Restaurant>>) Algorithms.kMeansClustering(3,database);
		assertFalse(km.isEmpty());
		String jsonString = Algorithms.convertClustersToJSON(km);
		
		JSONParser parse = new JSONParser();
		try {
			JSONArray jsonArray = (JSONArray) parse.parse(jsonString);
			assertEquals( database.getTable("restaurants").getIdSet().size(), jsonArray.size());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void LSRLGenerator(){
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		LeastSquaresRegression lsrl = Algorithms.getPredictor(new User(database.getTable("users").getIdSet().get(0)), database, new FeatureFunctionClass("longitude"));
		assertTrue( lsrl instanceof LeastSquaresRegression);
		assertTrue( Double.isFinite(lsrl.lsrf(database, database.getTable("restaurants").getIdSet().get(0)))  );
	}
	
	@Test
	public void LSRLBestPredicator(){
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		TableInterface restaurants = database.getTable("restaurants");
		TableInterface users = database.getTable("users");
		
		User user = new User(users.getIdSet().get(0));
		FeatureFunction feature1 = new FeatureFunctionClass("longitude");
		FeatureFunction feature2 = new FeatureFunctionClass("price");
		
		LeastSquaresRegression lsrl1 = Algorithms.getPredictor(user, database, feature1);
		LeastSquaresRegression lsrl2 = Algorithms.getPredictor(user, database, feature2);
		
		List<LeastSquaresRegression> lsrlList = new ArrayList<LeastSquaresRegression>();
		lsrlList.add(lsrl1);
		lsrlList.add(lsrl2);
		
		List<FeatureFunction> featureList = new ArrayList<FeatureFunction>();
		featureList.add(feature1);
		featureList.add(feature2);
		
		double rSquare1 = lsrl1.lsrf(database, null);
		double rSquare2 = lsrl2.lsrf(database, null);
		
		LeastSquaresRegression bestLsrl = Algorithms.getBestPredictor(user, database, featureList);
		
		String restaurant = restaurants.getIdSet().get(0);
		if( rSquare1 > rSquare2){
			assertEquals( lsrl1.lsrf(database, restaurant), bestLsrl.lsrf(database, restaurant), 0.0001);
		}else{
			assertEquals( lsrl2.lsrf(database, restaurant), bestLsrl.lsrf(database, restaurant), 0.0001);
		}
	}


}
