package ca.ece.ubc.cpen221.mp5.statlearning;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public interface LeastSquaresRegression {

	/**
	 * Compute a feature function given a restaurant
	 *
	 * @param yelpRestaurant
	 * @return the value of the feature function
	 */
	public double lsrf(RestaurantDB db, String restaurant_id);
	
}
