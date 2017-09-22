package ca.ece.ubc.cpen221.mp5.statlearning;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public interface FeatureFunction {

	/**
	 * 
	 * @param rdb
	 *            the database object representing the yelp dataset
	 * @param restaurant_id
	 *            the restaurant for which we want to extract a particular
	 *            feature
	 * @return the desired feature
	 */
	public double getFeature(RestaurantDB rdb, String restaurant_id);

}