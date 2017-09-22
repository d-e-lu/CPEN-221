package ca.ece.ubc.cpen221.mp5.statlearning;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import ca.ece.ubc.cpen221.mp5.Location;
import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.TableInterface;
import ca.ece.ubc.cpen221.mp5.User;

public class Algorithms {
	
	public static void main(String args[]){
		RestaurantDB database = new RestaurantDB("data" + File.separator + "restaurants.json", "data" + File.separator + "reviews.json", "data" + File.separator + "users.json");
		TableInterface reviews = database.getTable("reviews");
		TableInterface users = database.getTable("users");
		TableInterface restaurants = database.getTable("restaurants");
		
		reviews.getIDByField("user_id", "whocares");
		reviews.getIDByField("business_id", "whocares");	
	}
	/**
	 * Use k-means clustering to compute k clusters for the restaurants in the
	 * database.
	 *
	 * @param db - restaurant database to get the restaurants from
	 * @return a list of sets of restaurants where each set is a cluster
	 */
	public static List<Set<Restaurant>> kMeansClustering(int k, RestaurantDB db) {
		 
		Map<Location, Set<Restaurant>> clusters = new HashMap<Location, Set<Restaurant>>();
		Set<Restaurant> initialRestaurants = new HashSet<Restaurant>();
		
		TableInterface resTable = db.getTable("restaurants");
		ArrayList<String> ids = resTable.getIdSet();
		
		double maxLong = -Double.MAX_VALUE;
		double maxLat = -Double.MAX_VALUE;
		double minLong = Double.MAX_VALUE;
		double minLat = Double.MAX_VALUE;
		
		for (String id : ids){
			String[] lola = {"longitude", "latitude", "name"};
			Object[] point = resTable.get(id, lola );
			double longitude = (double)point[0];
			double latitude = (double)point[1];
			Location loc = new Location(longitude,latitude);
			
			if(longitude > maxLong) maxLong = longitude;
			if(longitude < minLong) minLong = longitude;
			if(latitude > maxLat) maxLat = latitude;
			if(latitude < minLat) minLat = latitude;
			
			initialRestaurants.add( new Restaurant(id, loc, String.valueOf(point[2])) );
		}
		
		Location[] randLocations = getRandomLocations(k, maxLong, maxLat, minLong, minLat);
		//put all clusters in one location
		clusters.put(randLocations[0], initialRestaurants);
		for(int i = 1; i < randLocations.length; i++){
			clusters.put(randLocations[i], new HashSet<Restaurant>());
		}
		
		boolean finished = false;
		
		while(!finished){
			
			for (Map.Entry<Location, Set<Restaurant>> entry : clusters.entrySet()) {
			    entry.setValue(new HashSet<Restaurant>());
			}
			finished = true;
			
			//Check closest restaurants to cluster
		
			for(Restaurant r :initialRestaurants){
				ArrayList<Location> locations = new ArrayList<Location>();
				locations.addAll(clusters.keySet());
				Location rLocation = r.getLocation();
				Location clusterLocation = locations.get(0);
				double minDis = rLocation.distanceTo(clusterLocation);
				for(int i = 1; i < locations.size(); i++){
					double distance = rLocation.distanceTo(locations.get(i));
					if(distance < minDis){
						minDis = distance;
						clusterLocation = locations.get(i);
					}
				}
				clusters.get(clusterLocation).add(r);
			}
			
			//Change Location of centroids
			
			for(Location x : clusters.keySet()){
				if(!clusters.get(x).isEmpty()){
					double longTotal = 0;
					double latTotal = 0;
					int numRes = clusters.get(x).size();
					for(Restaurant r : clusters.get(x)){
						longTotal += r.getLocation().getLongitude();
						latTotal += r.getLocation().getLatitude();
					}
			
					double avgLong = longTotal/(double)numRes;
					double avgLat = latTotal/(double)numRes;
			
					if(!x.locationIsSame(new Location(avgLong, avgLat))){
						finished = false;
						x.setLocation(avgLong, avgLat);
					}
				}
			}
		}
		
		return new ArrayList<Set<Restaurant>>(clusters.values());
	}
	/**
	 * getRandomLocations - gets random locations on the map
	 * 		  within a set range of latitudes and longitudes
	 * 		  and returns the same amount of locations as
	 * 		  amount of clusters passed in
	 * 
	 * @param k - number of clusters
	 * @param maxLong - maximum longitude
	 * @param maxLat - maximum latitude
	 * @param minLong - minimum longitude
	 * @param minLat - minimum latitude
	 * 
	 * 
	 * @return a Location array of random locations on map
	 */
	private static Location[] getRandomLocations(int k, double maxLong, double maxLat, double minLong, double minLat){
		Random random = new Random();
		Location[] locArray = new Location[k];
		for(int i = 0; i < k; i++){
			double longitude = minLong + (maxLong - minLong) * random.nextDouble();
			double latitude = minLat + (maxLat- minLat) * random.nextDouble();
			locArray[i] = new Location(longitude, latitude);
		}
		return locArray;
		
	}
	
	public static String convertClustersToJSON(List<Set<Restaurant>> clusters) {
		StringBuilder json = new StringBuilder();
		boolean comma = false;
		int cluster = 0;
		json.append("[");
		for(Set<Restaurant> set : clusters){
			for( Restaurant r : set.toArray( new Restaurant[ set.size() ])){
				if(comma) json.append(",");
				else comma = true;
				
				json.append("{\"x\": " + r.getLocation().getLatitude() + ",\"y\": " + r.getLocation().getLongitude() + ", \"name\": \"" + r.getName() + "\", \"cluster\": " + cluster + ", \"weight\": " + 1 + "}");
			}
			cluster++;
		}
		json.append("]");
		
		return json.toString();
	}

	
	// here we are abusing notation a bit when passing a function to getPredictor. 
	//This is a way to pass a function that obtains, say, latitude or longitude of a restaurant. 
	//You can improve on this is you like. It is the implementation of getBestPredictor that matters.
	public static LeastSquaresRegression getPredictor(User u, RestaurantDB db, FeatureFunction featureFunction) {
		LeastSquaresRegression function = new LSRLAbstraction(featureFunction, u);
		return function;
	}

	public static LeastSquaresRegression getBestPredictor(User u, RestaurantDB db, List<FeatureFunction> featureFunctionList) {
		ArrayList<LeastSquaresRegression> lsrls = new ArrayList<LeastSquaresRegression>();
		featureFunctionList.forEach(featureFunction -> lsrls.add( getPredictor(u, db, featureFunction)));
		Collections.sort(lsrls, new Comparator<LeastSquaresRegression>(){
			@Override
			public int compare(LeastSquaresRegression o1, LeastSquaresRegression o2) {
				// TODO Auto-generated method stub
				return (int) (- o1.lsrf(db, null) + o2.lsrf(db, null));
			}			
		});
		
		for( LeastSquaresRegression l : lsrls){
			System.out.println(l.lsrf(db, null));
		}
		return lsrls.get(0);
	}
}
