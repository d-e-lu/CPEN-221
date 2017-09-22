package ca.ece.ubc.cpen221.mp5.statlearning;

import java.awt.geom.Point2D;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.TableInterface;
import ca.ece.ubc.cpen221.mp5.User;

public class LSRLAbstraction implements LeastSquaresRegression{
		
	private RestaurantDB database;
	private TableInterface reviews;
	private TableInterface restaurants;
	private FeatureFunction featureFunction; 
	private String user_id;
	private boolean firstTime = true;
	private Double[] stats;
	/*
	 * restaurant fields can be the following strings
	 * stars
	 * price
	 * latitude
	 * longitude
	 */
	public LSRLAbstraction(String restaurantField, User u) {
		featureFunction = new FeatureFunctionClass(restaurantField);
		this.user_id = u.getId();
	}
	
	/*
	 * This is the constructor we will be using.
	 * It is initialized with FeatureFunction and User
	 */
	public LSRLAbstraction(FeatureFunction featureFunction, User u) {
		this.featureFunction = featureFunction;
		this.user_id = u.getId();
	}

	/*
	 * (non-Javadoc)
	 * @see ca.ece.ubc.cpen221.mp5.statlearning.LeastSquaresRegression#lsrf(ca.ece.ubc.cpen221.mp5.RestaurantDB, java.lang.String)
	 * Takes in the database and restaurant_id of interest.
	 * if restaurant_id == null, the return value will be the Rsquare value
	 * the first time running will take a bit longer because we need to review the user's previous's rating
	 * returns Double.Nan if anything goes wrong.
	 */
	@Override
	public double lsrf(RestaurantDB db, String restaurant_id) {
		if( firstTime ){
			this.database = db;
			reviews = database.getTable("reviews");
			restaurants = database.getTable("restaurants");
			if( reviews == null || restaurants == null ){
				System.out.println("Failed to retrieve the relavent information");
				return Double.NaN;
			}
			
			Point2D.Double[] datas = getPastData(user_id);
			
			stats = getStats(datas);
			firstTime = false;
		}
		
		if( restaurant_id == null){
			return stats[7];
		}
		
		Double x = getRestaurantData(restaurant_id);
		if( x == Double.NaN ){
			System.out.println( " there is something wrong with the restaurant");
			return Double.NaN;
		}
		return stats[5] * x + stats[6];
	}
	
	/**
	 * return an array of that contains stats
	 * index   	|   stat
	 * 0		|	mean(x)
	 * 1		!	mean(y)
	 * 2		|	Sxx = sum(xi - mean(X))^2
	 * 3		|	Syy = sum(yi - mean(y))^2
	 * 4		|	Sxy = sum( (xi-mean(x))/(yi - mean(y))
	 * 5		|	b = Sxy/Sxx
	 * 6		|	a = mean(y) - b*mean(x)
	 * 7		|	R^2 = Sxy^2 / (Sxx Syy)
	 * if any value is undefined, it will be set to 0.
	 * in the event that datas.length = 0 or is null, then we will return an array of 0 except for "a" which will equal 2.5
	 */
	private Double[] getStats(Point2D.Double[] datas){
		Double[] stats = new Double[8];
		
		//Mean
		double mx = 0;
		double my = 0;
		int dataPoint = 0;
		for( Point2D.Double data : datas){
			if( data != null){
				mx += data.getX();
				my += data.getY();
				dataPoint++;
			}
		}
		if( dataPoint == 0){
			for( int i = 0; i < stats.length; i++) stats[i] = 0.0;
			stats[5] = 2.5;
			return stats;
		}
		mx /= dataPoint;
		my /= dataPoint;
		stats[0] = mx;
		stats[1] = my;
		
		//Sxx Syy Sxy
		double sxx = 0, syy = 0, sxy = 0;
		for( Point2D.Double data : datas){
			if( data != null){
				sxx += Math.pow(data.getX() - mx, 2);
				syy += Math.pow(data.getY() - my, 2);
				// if both delta x and delta y = 0; then it will be 0 instead of NaN
				// will have a problem if (data.get(y) - my = 0
				sxy += (data.getX() - mx == 0) ? 0 : (data.getX() - mx) / (data.getY() - my);
			}
		}
		stats[2] = sxx;
		stats[3] = syy;
		stats[4] = sxy;
		
		stats[5] = sxy/sxx; 
		stats[6] = (stats[5] == Double.NaN || Double.isInfinite(stats[5])) ? my : my - stats[5] * mx;
		stats[7] = sxy * sxy / sxx / syy;	
		
		for( int i = 0; i < stats.length ; i++){
			stats[i] = ( !Double.isFinite(stats[i]) ) ? 0 : stats[i];
		}
		return stats;	
	}
	
	/*
	 * Retrieves previous rating given by the user and the rating of the restaurant.
	 * The information is returned as a Point2D.Double array.
	 * if the information about a review or restaurant, this will result in a null element within the array
	 */
	private Point2D.Double[] getPastData(String user){
		String[] reviewIDs = reviews.getIDByField("user_id", user);
		String[] reviewfields = {"stars", "business_id"};
		
		Point2D.Double[] data = new Point2D.Double[reviewIDs.length];
		
		for( int i = 0; i < reviewIDs.length; i++){
			Object[] info = reviews.get(reviewIDs[i], reviewfields);
			
			if( info[0] != null && info[1] != null){
				double rating = ((Number)info[0]).doubleValue();
				
				String restaurant = String.valueOf(info[1]);
				
				double o = getRestaurantData( restaurant );
				if( o != Double.NaN ){
					double indicator = ((Number)o).doubleValue();
					data[i] = new Point2D.Double(indicator, rating);
				}
			}
		}
		
		return data;
	}	

	/*
	 * Just a wrapper clase for getting features of restaurants.
	 */
	private double getRestaurantData( String restaurant){
		return  featureFunction.getFeature(database, restaurant);
	}

	
}
