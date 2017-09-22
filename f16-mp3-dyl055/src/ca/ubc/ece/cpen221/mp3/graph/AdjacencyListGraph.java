package ca.ubc.ece.cpen221.mp3.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class AdjacencyListGraph implements Graph{
	
	private List<Vertex> vertexes = new ArrayList<Vertex>();
	private Map<Vertex, ArrayList<Vertex>> edges = new HashMap<Vertex, ArrayList<Vertex>>();
		
	/**
	 * @param: v is a Vertex that will be added to the internal List Vertexes
	 * effects: maps the vertex v to an empty arraylist for future edges
	 */
	public void addVertex(Vertex v){
		vertexes.add(new Vertex(v.toString()));
		edges.put(v, new ArrayList<Vertex>());
	}
	/**
	 * @param: v1 is a Vertex that we will add an edge from
	 * @param: v2 is a Vertex that we will add an edge to
	 * requires: v1 and v2 are already added to vertexes
	 * effects: will only add an edge if v1 doesn't already have
	 * 			an edge with v2
	 */
	public void addEdge(Vertex v1, Vertex v2){
		if(!edges.get(v1).contains(v2)){
			edges.get(v1).add(v2);
		}
	}
	/**
	 * @param: v1 is a vertex that we are checking if there is an edge from
	 * @param: v2 is a vertex that we are checking if there is an edge to
	 * @return: returns true if there is an edge from v1 to v2
	 */
	public boolean edgeExists(Vertex v1, Vertex v2){
		
		return edges.get(v1).contains(v2);
	}
	/**
	 * @param: v is the Vertex that we are getting the downstream neighbors from
	 * @return: a copy of the List of vertexes that are connected downstream from v
	 */
	public List<Vertex> getDownstreamNeighbors(Vertex v){
		
		List<Vertex> downstream = new ArrayList<Vertex>();
		for(Vertex d : edges.get(v)){
			downstream.add(new Vertex(d.getLabel()));
		}
		
		return downstream;
	}
	/**
	 * @param: v is a Vertex that we are getting the upsream neighbors from
	 * @return: a copy of the List of vertexes that are connected upstream from v
	 */
	public List<Vertex> getUpstreamNeighbors(Vertex v){
		
		List<Vertex> upstream = new ArrayList<Vertex>();
		
		for(Vertex ver : edges.keySet()){
			if(edges.get(ver).contains(v)){
				upstream.add(new Vertex(ver.getLabel()));
			}
		}
		
		return upstream;
	}
	/**
	 * @return: a copyt of the list of vertexes that are internally stored
	 */
	public List<Vertex> getVertices(){
		List<Vertex> verticies = new ArrayList<Vertex>();
		for(Vertex v : vertexes){
			verticies.add(new Vertex(v.getLabel()));
		}
		return verticies;
	}

}
