package ca.ubc.ece.cpen221.mp3.graph;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;


import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class AdjacencyMatrixGraph implements Graph {

	/*
	public static void main(String args[]){
		long startTime = System.currentTimeMillis();
		AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
		Vertex v = new Vertex(Integer.toString(0));
		graph.addVertex( v );
		for(int i = 1; i < 60000; i++){
			Vertex v1 = new Vertex(Integer.toString(i));
			Vertex v2 = new Vertex(Integer.toString(i - 1));
			graph.addVertex( v1 );
			graph.addEdge(v1, v2);
		}
		
		System.out.println(graph.getVertices().size());
		System.out.println(graph.getDownstreamNeighbors(new Vertex("15")));
		System.out.println(graph.getUpstreamNeighbors(v));
		System.out.println(graph.edgeExists(new Vertex("0"), new Vertex("15")));
		System.out.println(graph.edgeExists(new Vertex("1"), new Vertex("0")));
		System.out.println("run time: " + (System.currentTimeMillis() - startTime));
	}
	*/
	
	private int VertexNumber = 0;
	private boolean[][] connections = new boolean[10000][10000];
	private ArrayList<BitSet> edges;
	private final ArrayList<Vertex> vertices =  new ArrayList<Vertex>();
	
	

	/* (non-Javadoc)
	 * @see ca.ubc.ece.cpen221.mp3.staff.Graph#addVertex(ca.ubc.ece.cpen221.mp3.staff.Vertex)
	 * @Param v : add a copy of this Vertex to Graph
	 */

	@Override
	public void addVertex(Vertex v) {
		
		vertices.add(new Vertex(v.getLabel()));
		VertexNumber++;
		if(VertexNumber == 10000){
			edges = new ArrayList<BitSet>();
			for(int row = 0; row < VertexNumber; row++){
				edges.add(new BitSet(80000));
				for(int col = 0; col < VertexNumber; col ++){
					if(connections[row][col]){
						edges.get(row).set(col);
					}	
				}
			}
			for( int i = VertexNumber; i < 80000; i++){
				edges.add(new BitSet(80000));
			}
			connections = null;
		}		
	}
	
	

	/* (non-Javadoc)
	 * @see ca.ubc.ece.cpen221.mp3.staff.Graph#addEdge(ca.ubc.ece.cpen221.mp3.staff.Vertex, ca.ubc.ece.cpen221.mp3.staff.Vertex)
	 * for specifications and purpose
	 * @Param v1 : a Vertex in Graph
	 * @Param v2 : a Vertex in Graph
	 */
	@Override
	public void addEdge(Vertex v1, Vertex v2) {
		
		int row = vertices.indexOf(v1);
		int col = vertices.indexOf(v2);
		if(VertexNumber < 10000){
			connections[row][col] = true;
		}else{
			//System.out.println(row * 8000 + col);
			edges.get(row).set(col);
		}
				
	}

	/* (non-Javadoc)
	 * @see ca.ubc.ece.cpen221.mp3.staff.Graph#edgeExists(ca.ubc.ece.cpen221.mp3.staff.Vertex, ca.ubc.ece.cpen221.mp3.staff.Vertex)
	 * for specifications and purpose
	 * @Param v1 : a Vertex in Graph
	 * @Param v2: a Vertex in Graph
	 * @returns true iff an edge from v1 to v2 exists
	 */
	@Override
	public boolean edgeExists(Vertex v1, Vertex v2) {
		
		int row = vertices.indexOf(v1);
		int col = vertices.indexOf(v2);
		
		if(VertexNumber < 10000){
			return connections[row][col];
		}else{
			return edges.get(row).get(col);
		}
		
	}

	/* (non-Javadoc)
	 * @see ca.ubc.ece.cpen221.mp3.staff.Graph#getDownstreamNeighbors(ca.ubc.ece.cpen221.mp3.staff.Vertex)
	 * for specifications and purpose
	 * @Param v : a Vertex in Graph
	 * @returns a List with copies to the all of v's downstream neighbors
	 */
	@Override
	public List<Vertex> getDownstreamNeighbors(Vertex v) {
		
		List<Vertex> downStream = new ArrayList<Vertex>();
		int row = vertices.indexOf(v);
		
		if(VertexNumber < 10000){
			for(int i = 0; i < VertexNumber; i++){
				if(connections[row][i]){
					downStream.add(new Vertex(vertices.get(i).getLabel()));
				}
			}
		}else{
			for(int col = 0; col < VertexNumber; col++){
				if(edges.get(row).get(col)){
					downStream.add(new Vertex(vertices.get(col).getLabel()));
				}
			}
		}
		
		return downStream;
	}

	/* (non-Javadoc)
	 * @see ca.ubc.ece.cpen221.mp3.staff.Graph#getUpstreamNeighbors(ca.ubc.ece.cpen221.mp3.staff.Vertex)
	 * for specifications and purpose
	 * @Param v : a Vertex in Graph
	 * @returns a List with copies to the all of v's upstream neighbors
	 */
	@Override
	public List<Vertex> getUpstreamNeighbors(Vertex v) {
		
		List<Vertex> upStream = new ArrayList<Vertex>();
		int col = vertices.indexOf(v);
		if(VertexNumber < 10000){
			for(int i = 0; i < VertexNumber; i++){
				if(connections[i][col]){
					upStream.add(new Vertex(vertices.get(i).getLabel()));
				}
			}
		}else{
			for(int i = 0; i < VertexNumber; i++){
				if(edges.get(i).get(col)){
					upStream.add(new Vertex(vertices.get(i).getLabel()));
				}
			}
		}
		
		return upStream;
	}

	/* (non-Javadoc)
	 * @see ca.ubc.ece.cpen221.mp3.staff.Graph#getVertices()
	 * for specifications and purpose
	 * @returns a List with copy to all the Vertex in Graph
	 */
	@Override
	public List<Vertex> getVertices() {
		
		List<Vertex> returnList = new ArrayList<Vertex>();
		for(Vertex v: vertices){
			returnList.add(new Vertex(v.getLabel()));
		}
		return returnList;
	}

}
