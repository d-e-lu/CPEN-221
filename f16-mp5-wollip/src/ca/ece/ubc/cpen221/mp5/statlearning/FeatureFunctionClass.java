package ca.ece.ubc.cpen221.mp5.statlearning;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.TableInterface;

public class FeatureFunctionClass implements FeatureFunction{

	private String feature;
	/*
	 * valid features include:
	 * longitude
	 * latitude
	 * stars
	 * price
	 */
	public FeatureFunctionClass(String feature){
		this.feature = feature;
	}
	/*
	 * (non-Javadoc)
	 * @see ca.ece.ubc.cpen221.mp5.statlearning.FeatureFunction#getFeature(ca.ece.ubc.cpen221.mp5.RestaurantDB, java.lang.String)
	 * returns the feature after searching it up in the database
	 */
	@Override
	public double getFeature(RestaurantDB rdb, String restaurant_id) {
		TableInterface restaurant = rdb.getTable("restaurants");
		if( !restaurant.getIdSet().contains(restaurant_id)){
			System.out.print("restaurant does not exist");
			return Double.NaN;
		}
		return ((Number)restaurant.get(restaurant_id, feature)).doubleValue();
	}

}
