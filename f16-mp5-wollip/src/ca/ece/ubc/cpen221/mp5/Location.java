package ca.ece.ubc.cpen221.mp5;

public class Location {
	
	private double longitude;
	private double latitude;
	
	public Location(double longitude, double latitude){
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public double getLongitude(){
		return this.longitude;
	}
	public double getLatitude(){
		return this.latitude;
	}
	public void setLongitude(double longitude){
		this.longitude = longitude;
	}
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	public void setLocation(double longitude, double latitude){
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public double[] getLocation(){
		double[] lolat = {this.longitude, this.latitude};
		return lolat;
	}

	
	public double distanceTo(Location location){
		double otherLong = location.getLongitude();
		double otherLat = location.getLatitude();
		
		double distance = Math.sqrt(Math.pow((this.longitude - otherLong),2) + Math.pow((this.latitude - otherLat),2));
		return distance;
	}
	public boolean locationIsSame(Location location){
		if(this.latitude == location.getLatitude() && this.longitude == location.getLongitude()){
			return true;
		}
		return false;
	}
	public String toString(){
		return String.format("(%f, %f)", longitude, latitude);
	}
}
