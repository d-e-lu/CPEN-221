package ca.ubc.ece.cpen221.mp3.graph;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class AlgorithmsD {

	/**
	 * *********************** Algorithms ****************************
	 *
	 * Please see the README for the machine problem for a more detailed
	 * specification of the behavior of each method that one should implement.
	 */

	/**
	 * This is provided as an example to indicate that this method and other
	 * methods should be implemented here.
	 *
	 * You should write the specs for this and all other methods.
	 *
	 * @param graph
	 * @param a
	 * @param b
	 * @return
	 */
	public static int shortestDistance(Graph graph, Vertex a, Vertex b) {
		// TODO: Implement this method and others
		class Vertex2 extends Vertex implements Comparable<Vertex2>{
			 
		
			private Vertex vertex;
			private int distance = Integer.MAX_VALUE;
			 
			public Vertex2(String label) {
				super(label);
				// TODO Auto-generated constructor stub
			}
			 
			public void setDistance(int distance){
				 this.distance = distance;
			}
			 
			public Vertex getVertex(){
				 return vertex;
			}
			
			public int getDistance(){
				return distance;
			}
			
			
			@Override
			public int compareTo(Vertex2 vertex2) {
				// TODO Auto-generated method stub
				return distance - vertex2.distance;
			}
		}
		
		ArrayList<Vertex2> toBeExplored = new ArrayList<Vertex2>();
		ArrayList<Vertex2> Explored = new ArrayList<Vertex2>();
		/*
		for(Vertex v: graph.getVertices()){
			Vertex2 v2 = new Vertex2(v.getLabel());
			toBeExplored.add(v2);
			if(v2.equals(a)){
				v2.setDistance(0);
			}
		}
		*/
		Vertex2 v2 = new Vertex2(a.getLabel());
		v2.setDistance(0);
		toBeExplored.add(v2);
		
		int currentDistance = 0;
		boolean notFound = true;
		while( notFound ){
			Collections.sort(toBeExplored);
			Vertex2 current = toBeExplored.get(0);
			toBeExplored.remove(current);
			Explored.add(current);
			currentDistance = current.getDistance() + 1;
			
			List<Vertex> neighbors = graph.getDownstreamNeighbors(current);
			for(Vertex v : neighbors){
				if(!Explored.contains(v)){
					if(v.equals(b)){
						notFound = false;
						break;
					}
					int index = toBeExplored.indexOf(v);
					if(index != -1){
						if(toBeExplored.get(index).getDistance() > currentDistance){
							toBeExplored.get(index).setDistance(currentDistance);
						}
					}else{
						
						v2 = new Vertex2(v.getLabel());
						v2.setDistance(currentDistance);
						toBeExplored.add(v2);
						
					}
				}				
			}
			if(toBeExplored.isEmpty()){
				return -1;
			}
		}
		// it should never come here
		return currentDistance;
	}

	/**
	 * Perform a complete depth first search of the given
	 * graph. Start with the search at each vertex of the
	 * graph and create a list of the vertices visited.
	 * Return a set where each element of the set is a list
	 * of elements seen by starting a DFS at a specific
	 * vertex of the graph (the number of elements in the
	 * returned set should correspond to the number of graph
	 * vertices).
	 *
	 * @param
	 * @return
	 */
	public static Set<List<Vertex>> depthFirstSearch(Graph graph) {
		
		
		List<Vertex> vertices = graph.getVertices();
		Set<List<Vertex>> dfs = new HashSet<List<Vertex>>();
		
		for(Vertex v: vertices){
			List<Vertex> vList = new ArrayList<Vertex>();
			Stack<Vertex> depth = new Stack<Vertex>();
			depth.add(v);
			
			while(!depth.isEmpty()){
				Vertex currentV = depth.pop();
				vList.add(currentV);
				List<Vertex> downstream = graph.getDownstreamNeighbors(currentV);
				for(Vertex neighbor : downstream){
					if(depth.contains(neighbor)){
						depth.remove(neighbor);
					}
					if(!vList.contains(neighbor)&&!depth.contains(neighbor)){
						depth.add(neighbor);
					}
				}
			}
			dfs.add(vList);
		}
		
		
		return dfs;

	}
	
	
	
	/**
	 * Perform a complete breadth first search of the given
	 * graph. Start with the search at each vertex of the
	 * graph and create a list of the vertices visited.
	 * Return a set where each element of the set is a list
	 * of elements seen by starting a BFS at a specific
	 * vertex of the graph (the number of elements in the
	 * returned set should correspond to the number of graph
	 * vertices).
	 *
	 * @param
	 * @return
	 */
	public static Set<List<Vertex>> breadthFirstSearch(Graph graph) {
		// TODO: Implement this method
		List<Vertex> vertices = graph.getVertices();
		Set<List<Vertex>> returnValues = new HashSet<List<Vertex>>();
		
		for(Vertex v : vertices){
			List<Vertex> currentList = new ArrayList<Vertex>();
			Queue<Vertex> toBeExplored = new LinkedList<Vertex>();
			toBeExplored.add(v);
			
			while(!toBeExplored.isEmpty()){
				Vertex currentVertex = toBeExplored.poll();
				currentList.add(currentVertex);
				List<Vertex> downStreamNeighbors = graph.getDownstreamNeighbors(currentVertex);
				
				for(Vertex neighbor: downStreamNeighbors){
					if(!currentList.contains(neighbor) && !toBeExplored.contains(neighbor)){
						toBeExplored.add(neighbor);
					}
				}
				
			}
			
			returnValues.add(currentList);
			
		}
		return returnValues; // this should be changed
	}

	/**
	 * You should write the spec for this method
	 */
	 public static List<Vertex> commonUpstreamVertices(Graph graph, Vertex a, Vertex b) {
		 // TODO: Implement this method
		 List<Vertex> aList = graph.getUpstreamNeighbors(a);
		 List<Vertex> bList = graph.getUpstreamNeighbors(b);
		 List<Vertex> retList = new ArrayList<Vertex>();
		 
		 for(Vertex A : aList){
			 if(bList.contains(A)){
				 retList.add(A);
			 }
		 }
 		return retList; // this should be changed
	 }

	 /**
 	 * You should write the spec for this method
 	 */
 	 public static List<Vertex> commonDownstreamVertices(Graph graph, Vertex a, Vertex b) {
 		 // TODO: Implement this method
 		List<Vertex> aList = graph.getDownstreamNeighbors(a);
		 List<Vertex> bList = graph.getDownstreamNeighbors(b);
		 List<Vertex> retList = new ArrayList<Vertex>();
		 
		 for(Vertex A : aList){
			 if(bList.contains(A)){
				 retList.add(A);
			 }
		 }
		return retList; // this should be changed
 	 }
}
