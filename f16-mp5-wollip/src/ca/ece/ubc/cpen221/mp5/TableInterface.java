package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONObject;

public interface TableInterface {
	public enum Type{
		Double, Long, String, Boolean, Null
	}
	
	public static TableInterface Table(String filelocation){
		return Table(filelocation);		
	}
	
	public String[] getIDByField(String searchField, String match);
	
	public void init(String JSONfile);
	
	public ArrayList<String> getIdSet();
	
	public Object[] get(String ID, String[] fields);
	
	public Object get(String ID, String field);
	
	public Map<String, Object> getRow(String ID);
	
	public String location();
	
	public boolean addRow(JSONObject json);
	
	public String getJSON(String id);
}
