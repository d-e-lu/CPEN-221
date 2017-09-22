package ca.ubc.ece.cpen221.mp3.graph;

import java.util.ArrayList;
import java.util.List;


import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class AdjacencyMatrixGraphNoDC implements Graph {
	
	private int VertexNumber = 0;
	private boolean[][] edges = new boolean[VertexNumber][VertexNumber];
	private final ArrayList<Vertex> vertices =  new ArrayList<Vertex>();
	
	
	/* (non-Javadoc)
	 * @see ca.ubc.ece.cpen221.mp3.staff.Graph#addVertex(ca.ubc.ece.cpen221.mp3.staff.Vertex) for specifications and purpose
	 * @see ca.ubc.ece.cpen221.mp3.staff.AdjacencyMatrixGraph if defensive copying is needed
	 * @Param v : add a reference of this Vertex to Graph
	 */
	@Override
	public void addVertex(Vertex v) {
		
		vertices.add(v);
		VertexNumber++;
		
		boolean[][] newGraph = new boolean[VertexNumber][VertexNumber];
		
		for(int row = 0; row < VertexNumber-1; row ++){
			for(int col = 0; col < VertexNumber -1; col++){
				newGraph[row][col] = edges[row][col];
			}
		}
		edges = newGraph;
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
		
		edges[row][col] = true;		
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
		
		return edges[row][col];
	}

	/* (non-Javadoc)
	 * @see ca.ubc.ece.cpen221.mp3.staff.Graph#getDownstreamNeighbors(ca.ubc.ece.cpen221.mp3.staff.Vertex)
	 * for specifications and purpose
	 * @Param v : a Vertex in Graph
	 * @returns a List with references to the all of v's downstream neighbors
	 */
	@Override
	public List<Vertex> getDownstreamNeighbors(Vertex v) {
		
		List<Vertex> downStream = new ArrayList<Vertex>();
		int row = vertices.indexOf(v);
		
		for(int i = 0; i < VertexNumber; i++){
			if(edges[row][i]){
				downStream.add(vertices.get(i));
			}
		}
		return downStream;
	}

	/* (non-Javadoc)
	 * @see ca.ubc.ece.cpen221.mp3.staff.Graph#getUpstreamNeighbors(ca.ubc.ece.cpen221.mp3.staff.Vertex)
	 * for specifications and purpose
	 * @params v : a Vertex in Graph
	 * @returns a List with references to all of v's upstream neighbors
	 */
	@Override
	public List<Vertex> getUpstreamNeighbors(Vertex v) {
		
		List<Vertex> upStream = new ArrayList<Vertex>();
		int col = vertices.indexOf(v);
		
		for(int i = 0; i < VertexNumber; i++){
			if(edges[i][col]){
				upStream.add(vertices.get(i));
			}
		}
		return upStream;
	}

	/* (non-Javadoc)
	 * @see ca.ubc.ece.cpen221.mp3.staff.Graph#getVertices()
	 * for specifications and purpose
	 * @returns a List with reference to all the Vertex in Graph
	 */
	@Override
	public List<Vertex> getVertices() {
		
		return vertices;
	}

}
