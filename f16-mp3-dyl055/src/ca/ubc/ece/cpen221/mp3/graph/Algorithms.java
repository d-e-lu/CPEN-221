package ca.ubc.ece.cpen221.mp3.graph;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Random;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class Algorithms {
	
	
	public static void main(String args[]){
		
	
		try {
			Graph graph = GenerateGraph(new AdjacencyListGraph());
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * Purpose: to create an instance of Graph that is populated by Vertexs and edges
	 * @param object : the implementation of Graph that will be returned
	 * @return an object with the same class as the param
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Graph GenerateGraph(Object object) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Constructor<?> constructor = object.getClass().getConstructor();
		Graph graph = (Graph) constructor.newInstance();

		List<Vertex> vertices = new ArrayList<Vertex>();
		Random rnd = new Random();
		rnd.setSeed(0);
		
		for(int i = 0; i < 11; i++){
			Vertex v = new Vertex(Integer.toString(i));
			vertices.add(v);
			graph.addVertex(v);
		}
		for(int i = 0; i < 40; i++){
			Vertex rnd1 = vertices.get(rnd.nextInt(10));
			Vertex rnd2 = vertices.get(rnd.nextInt(10));
			
			if( rnd1.equals(rnd2) ){
				i--;
				continue;
			}
			//System.out.println(rnd1.toString() + " " + rnd2.toString());
			graph.addEdge(rnd1, rnd2);
		}
		return graph;
	}
	
	
	/**
	 * *********************** Algorithms ****************************
	 *
	 * Please see the README for the machine problem for a more detailed
	 * specification of the behavior of each method that one should implement.
	 */

	/**
	 * Purpose : find the distance between the start and end Vertex
	 * @param graph : The Graph that is to be searched
	 * @param a : The starting Vertex
	 * @param b : The ending Vertex
	 * @return the number of nodes required to reach the end Vertex from the search Vertex
	 * a -> d -> c -> b returns 3
	 */
	public static int shortestDistance(Graph graph, Vertex a, Vertex b) {
		// TODO: Implement this method and others
		if(a.equals(b)){
			return 0;
		}
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
		
		while( !toBeExplored.isEmpty() ){
			Collections.sort(toBeExplored);
			Vertex2 current = toBeExplored.get(0);
			toBeExplored.remove(current);
			Explored.add(current);
			currentDistance = current.getDistance() + 1;
			
			List<Vertex> neighbors = graph.getDownstreamNeighbors(current);
			for(Vertex v : neighbors){
				if(!Explored.contains(v)){
					if(v.equals(b)){
						return currentDistance;
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
			
		}
		// when nodes are not connected
		return -1;
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
	 * @param Graph : A Graph to be searched
	 * @return a Set of Lists of Vertexes
	 * that contains values that start at every single Vertex 
	 * and performs DFS while recording its path until
	 * every other vertex is reached
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
					if(!vList.contains(neighbor)){
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
	 * @param graph a implementation of graph with the desired graph to explore.
	 * @return  a Set of Lists of Vertexes
	 * that contains values that start at every single Vertex 
	 * and performs BFS while recording its path until
	 * every other vertex is reached
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
	 * Purpose :  Find the common Upstream Vertex of two Vertex in Graph
	 * @param graph : the Graph that contains the 2 Vertex
	 * @param a : the first Vertex
	 * @param b : the second Vertex
	 * @return A List of Vertex that contains all the common upstream Vertexs
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
		 * Purpose :  Find the common downstream Vertex of two Vertex in Graph
		 * @param graph : the Graph that contains the 2 Vertex
		 * @param a : the first Vertex
		 * @param b : the second Vertex
		 * @return A List of Vertex that contains all the common downstream Vertexs
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
