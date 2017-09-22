package ca.ubc.ece.cpen221.mp3.tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;


import ca.ubc.ece.cpen221.mp3.graph.AdjacencyListGraph;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;
public class ListDCTest {
	
	/*
	 * Tests the graphs add Vertex function function
	 * and the getVertices function to see if they
	 * are working correctly
	 */
	@Test
	public void test1(){
		
		Graph matrix = new AdjacencyListGraph();
		Vertex v = new Vertex("v1");
		ArrayList<Vertex> aL = new ArrayList<Vertex>();
		aL.add(v);
		matrix.addVertex(v);
		assertEquals(matrix.getVertices(), aL);
		
	}
	/*
	 * Tests the addEdge function of the graph and checks that
	 * the edge is actually created between two vertices
	 */
	@Test
	public void test2(){
		
		Graph matrix = new AdjacencyListGraph();
		Vertex v = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		
		matrix.addVertex(v);
		matrix.addVertex(v2);
		
		matrix.addEdge(v, v2);
		assertTrue(matrix.edgeExists(v, v2));
		
	}
	/*
	 * This tests that the edgeExists function returns false
	 * when there are no edges between vertices
	 */
	@Test
	public void test3(){
		
		Graph matrix = new AdjacencyListGraph();
		Vertex v = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		
		matrix.addVertex(v);
		matrix.addVertex(v2);
		assertFalse(matrix.edgeExists(v, v2));
		
	}
	/*
	 * This test tests the getDownstreamNeighbors of the graph.
	 * It compares it with the expected output of the function
	 */
	@Test
	public void test4(){
		
		Graph matrix = new AdjacencyListGraph();
		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		Vertex v3 = new Vertex("v3");
		Vertex v4 = new Vertex("v4");
		Vertex v5 = new Vertex("v5");
		Vertex v6 = new Vertex("v6");
		Vertex v7 = new Vertex("v7");
		
		
		matrix.addVertex(v1);
		matrix.addVertex(v2);
		matrix.addVertex(v3);
		matrix.addVertex(v4);
		matrix.addVertex(v5);
		matrix.addVertex(v6);
		matrix.addVertex(v7);
		
		matrix.addEdge(v1, v2);
		matrix.addEdge(v1, v3);
		matrix.addEdge(v2, v4);
		matrix.addEdge(v2, v5);
		matrix.addEdge(v3, v6);
		matrix.addEdge(v3, v7);
		
		ArrayList<Vertex> aDown = new ArrayList<Vertex>();
		aDown.add(v2);
		aDown.add(v3);
		assertEquals(matrix.getDownstreamNeighbors(v1), aDown);
		
		ArrayList<Vertex> bDown = new ArrayList<Vertex>();
		bDown.add(v4);
		bDown.add(v5);
		assertEquals(matrix.getDownstreamNeighbors(v2), bDown);
		
		ArrayList<Vertex> cDown = new ArrayList<Vertex>();
		cDown.add(v6);
		cDown.add(v7);
		assertEquals(matrix.getDownstreamNeighbors(v3), cDown);
		
		
	}
	/*
	 * This test tests the getUpstreamNeighbors of the graph.
	 * It compares it with the expected output of the function
	 */
	@Test
	public void test5(){
		
		Graph matrix = new AdjacencyListGraph();
		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		Vertex v3 = new Vertex("v3");
		Vertex v4 = new Vertex("v4");
		Vertex v5 = new Vertex("v5");
		Vertex v6 = new Vertex("v6");
		Vertex v7 = new Vertex("v7");
		
		
		matrix.addVertex(v1);
		matrix.addVertex(v2);
		matrix.addVertex(v3);
		matrix.addVertex(v4);
		matrix.addVertex(v5);
		matrix.addVertex(v6);
		matrix.addVertex(v7);
		
		matrix.addEdge(v1, v2);
		matrix.addEdge(v1, v3);
		matrix.addEdge(v2, v4);
		matrix.addEdge(v2, v5);
		matrix.addEdge(v3, v6);
		matrix.addEdge(v3, v7);
		
		ArrayList<Vertex> bUp = new ArrayList<Vertex>();
		bUp.add(v1);
		assertEquals(matrix.getUpstreamNeighbors(v2), bUp);
		
		ArrayList<Vertex> cUp = new ArrayList<Vertex>();
		cUp.add(v1);
		assertEquals(matrix.getUpstreamNeighbors(v3), cUp);
		
		ArrayList<Vertex> dUp = new ArrayList<Vertex>();
		dUp.add(v2);
		assertEquals(matrix.getUpstreamNeighbors(v4), dUp);
		
	}
	/*
	 * This test checks the getVertices returns the correct output
	 */
	@Test
	public void test6(){
		Graph matrix = new AdjacencyListGraph();
		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		Vertex v3 = new Vertex("v3");
		Vertex v4 = new Vertex("v4");
		
		
		matrix.addVertex(v1);
		matrix.addVertex(v2);
		matrix.addVertex(v3);
		matrix.addVertex(v4);
		
		List<Vertex> v = new ArrayList<Vertex>();
		v.add(v1);
		v.add(v2);
		v.add(v3);
		v.add(v4);
		
		assertEquals(matrix.getVertices(), v);
		
		
	}
	/*
	 * This test is for more verification of the
	 * getDownstream neighbors function by testing
	 * it with expected output
	 */
	@Test
	public void test7(){
		
		Graph matrix = new AdjacencyListGraph();
		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		Vertex v3 = new Vertex("v3");
		Vertex v4 = new Vertex("v4");
		Vertex v5 = new Vertex("v5");
		Vertex v6 = new Vertex("v6");
		Vertex v7 = new Vertex("v7");
		
		
		matrix.addVertex(v1);
		matrix.addVertex(v2);
		matrix.addVertex(v3);
		matrix.addVertex(v4);
		matrix.addVertex(v5);
		matrix.addVertex(v6);
		matrix.addVertex(v7);
		
		matrix.addEdge(v1, v2);
		matrix.addEdge(v1, v3);
		matrix.addEdge(v1, v4);
		matrix.addEdge(v1, v5);
		matrix.addEdge(v1, v6);
		matrix.addEdge(v1, v7);
		
		ArrayList<Vertex> aDown = new ArrayList<Vertex>();
		aDown.add(v2);
		aDown.add(v3);
		aDown.add(v4);
		aDown.add(v5);
		aDown.add(v6);
		aDown.add(v7);
		assertEquals(matrix.getDownstreamNeighbors(v1), aDown);
		
		
	}
	/*
	 * This function is for more verification of the
	 * getUpstreamNeighbors by testing it with the 
	 * expected output
	 */
	@Test
	public void test8(){
		Graph matrix = new AdjacencyListGraph();
		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		Vertex v3 = new Vertex("v3");
		Vertex v4 = new Vertex("v4");

		
		
		matrix.addVertex(v1);
		matrix.addVertex(v2);
		matrix.addVertex(v3);
		matrix.addVertex(v4);

		
		matrix.addEdge(v1, v2);
		matrix.addEdge(v1, v3);
		matrix.addEdge(v1, v4);

		
		ArrayList<Vertex> bUp = new ArrayList<Vertex>();
		bUp.add(v1);
		assertEquals(matrix.getUpstreamNeighbors(v2), bUp);
		
		ArrayList<Vertex> cUp = new ArrayList<Vertex>();
		cUp.add(v1);
		assertEquals(matrix.getUpstreamNeighbors(v3), cUp);
		
		ArrayList<Vertex> dUp = new ArrayList<Vertex>();
		dUp.add(v1);
		assertEquals(matrix.getUpstreamNeighbors(v4), dUp);
		
		
	}
	/*
	 * This test is for more verification the the edgeExists function
	 * and getDownstreamNeighbors works for Vertexes linked to each other
	 */
	@Test
	public void test9(){
		Graph matrix = new AdjacencyListGraph();
		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		matrix.addVertex(v1);
		matrix.addVertex(v2);
		matrix.addEdge(v1, v2);
		matrix.addEdge(v2, v1);
		
		assertEquals(matrix.edgeExists(v1, v2), true);
		assertEquals(matrix.edgeExists(v2, v1), true);
		assertEquals(matrix.getDownstreamNeighbors(v1), Arrays.asList(v2));
		assertEquals(matrix.getDownstreamNeighbors(v2), Arrays.asList(v1));
	}
	/*
	 * This function tests that the addEdge function doesn't work when
	 * you try to add an edge with a vertex that doesn't exist
	 */
	@Test
	public void test10()throws ArrayIndexOutOfBoundsException{
		Graph matrix = new AdjacencyListGraph();
		try{
			matrix.addEdge(new Vertex("a"), new Vertex("b"));
			fail("Didn't throw exception");
		}catch(Exception e){}
	}
	/*
	 * This test checks that when we add an edge from v1 to v2
	 * the edgeExists function returns false for an edge from v2
	 * to v1
	 */
	@Test
	public void test11(){
		
		Graph matrix = new AdjacencyListGraph();
		Vertex v = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		
		matrix.addVertex(v);
		matrix.addVertex(v2);
		
		matrix.addEdge(v, v2);
		assertFalse(matrix.edgeExists(v2, v));
		
	}
	/*
	 * This function tests for references
	 * With defensive copying we expect that
	 * when we change a vertex label, the 
	 * Vertex in the graph will still have the
	 * original vertex label
	 */
	@Test
	public void test12(){
		
		Graph matrix = new AdjacencyListGraph();
		Vertex v = new Vertex("v1");
		
		matrix.addVertex(v);
		v.setLabel("v2");
		
		assertNotEquals(matrix.getVertices(), Arrays.asList(v));

	}
	/*
	 * The rest of these tests test how fast our program can run
	 * with different implementations for graphs with over 10000 Vertexes
	 */
	@Test
	public void test13(){
		Graph matrix = new AdjacencyListGraph();
		for(int i = 0; i < 70000; i++){
			matrix.addVertex(new Vertex(Integer.toString(i)));
		}
	}

	
	@Test
	public void test2b(){
		
		Graph matrix = new AdjacencyListGraph();
		for(int i = 0; i < 11000; i++){
			matrix.addVertex(new Vertex(Integer.toString(i)));
		}
		Vertex v = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		
		matrix.addVertex(v);
		matrix.addVertex(v2);
		
		matrix.addEdge(v, v2);
		assertTrue(matrix.edgeExists(v, v2));
		
	}
	@Test
	public void test3b(){
		
		Graph matrix = new AdjacencyListGraph();
		for(int i = 0; i < 11000; i++){
			matrix.addVertex(new Vertex(Integer.toString(i)));
		}
		Vertex v = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		
		matrix.addVertex(v);
		matrix.addVertex(v2);
		assertFalse(matrix.edgeExists(v, v2));
		
	}
	@Test
	public void test4b(){
		
		Graph matrix = new AdjacencyListGraph();
		for(int i = 0; i < 11000; i++){
			matrix.addVertex(new Vertex(Integer.toString(i)));
		}
		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		Vertex v3 = new Vertex("v3");
		Vertex v4 = new Vertex("v4");
		Vertex v5 = new Vertex("v5");
		Vertex v6 = new Vertex("v6");
		Vertex v7 = new Vertex("v7");
		
		
		matrix.addVertex(v1);
		matrix.addVertex(v2);
		matrix.addVertex(v3);
		matrix.addVertex(v4);
		matrix.addVertex(v5);
		matrix.addVertex(v6);
		matrix.addVertex(v7);
		
		matrix.addEdge(v1, v2);
		matrix.addEdge(v1, v3);
		matrix.addEdge(v2, v4);
		matrix.addEdge(v2, v5);
		matrix.addEdge(v3, v6);
		matrix.addEdge(v3, v7);
		
		ArrayList<Vertex> aDown = new ArrayList<Vertex>();
		aDown.add(v2);
		aDown.add(v3);
		assertEquals(matrix.getDownstreamNeighbors(v1), aDown);
		
		ArrayList<Vertex> bDown = new ArrayList<Vertex>();
		bDown.add(v4);
		bDown.add(v5);
		assertEquals(matrix.getDownstreamNeighbors(v2), bDown);
		
		ArrayList<Vertex> cDown = new ArrayList<Vertex>();
		cDown.add(v6);
		cDown.add(v7);
		assertEquals(matrix.getDownstreamNeighbors(v3), cDown);
		
		
	}
	@Test
	public void test5b(){
		
		Graph matrix = new AdjacencyListGraph();
		for(int i = 0; i < 11000; i++){
			matrix.addVertex(new Vertex(Integer.toString(i)));
		}
		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		Vertex v3 = new Vertex("v3");
		Vertex v4 = new Vertex("v4");
		Vertex v5 = new Vertex("v5");
		Vertex v6 = new Vertex("v6");
		Vertex v7 = new Vertex("v7");
		
		
		matrix.addVertex(v1);
		matrix.addVertex(v2);
		matrix.addVertex(v3);
		matrix.addVertex(v4);
		matrix.addVertex(v5);
		matrix.addVertex(v6);
		matrix.addVertex(v7);
		
		matrix.addEdge(v1, v2);
		matrix.addEdge(v1, v3);
		matrix.addEdge(v2, v4);
		matrix.addEdge(v2, v5);
		matrix.addEdge(v3, v6);
		matrix.addEdge(v3, v7);
		
		ArrayList<Vertex> bUp = new ArrayList<Vertex>();
		bUp.add(v1);
		assertEquals(matrix.getUpstreamNeighbors(v2), bUp);
		
		ArrayList<Vertex> cUp = new ArrayList<Vertex>();
		cUp.add(v1);
		assertEquals(matrix.getUpstreamNeighbors(v3), cUp);
		
		ArrayList<Vertex> dUp = new ArrayList<Vertex>();
		dUp.add(v2);
		assertEquals(matrix.getUpstreamNeighbors(v4), dUp);
		
	}

	
	@Test
	public void test7b(){
		
		Graph matrix = new AdjacencyListGraph();
		for(int i = 0; i < 11000; i++){
			matrix.addVertex(new Vertex(Integer.toString(i)));
		}
		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		Vertex v3 = new Vertex("v3");
		Vertex v4 = new Vertex("v4");
		Vertex v5 = new Vertex("v5");
		Vertex v6 = new Vertex("v6");
		Vertex v7 = new Vertex("v7");
		
		
		matrix.addVertex(v1);
		matrix.addVertex(v2);
		matrix.addVertex(v3);
		matrix.addVertex(v4);
		matrix.addVertex(v5);
		matrix.addVertex(v6);
		matrix.addVertex(v7);
		
		matrix.addEdge(v1, v2);
		matrix.addEdge(v1, v3);
		matrix.addEdge(v1, v4);
		matrix.addEdge(v1, v5);
		matrix.addEdge(v1, v6);
		matrix.addEdge(v1, v7);
		
		ArrayList<Vertex> aDown = new ArrayList<Vertex>();
		aDown.add(v2);
		aDown.add(v3);
		aDown.add(v4);
		aDown.add(v5);
		aDown.add(v6);
		aDown.add(v7);
		assertEquals(matrix.getDownstreamNeighbors(v1), aDown);
		
		
	}
	@Test
	public void test8b(){
		Graph matrix = new AdjacencyListGraph();
		for(int i = 0; i < 11000; i++){
			matrix.addVertex(new Vertex(Integer.toString(i)));
		}
		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		Vertex v3 = new Vertex("v3");
		Vertex v4 = new Vertex("v4");

		
		
		matrix.addVertex(v1);
		matrix.addVertex(v2);
		matrix.addVertex(v3);
		matrix.addVertex(v4);

		
		matrix.addEdge(v1, v2);
		matrix.addEdge(v1, v3);
		matrix.addEdge(v1, v4);

		
		ArrayList<Vertex> bUp = new ArrayList<Vertex>();
		bUp.add(v1);
		assertEquals(matrix.getUpstreamNeighbors(v2), bUp);
		
		ArrayList<Vertex> cUp = new ArrayList<Vertex>();
		cUp.add(v1);
		assertEquals(matrix.getUpstreamNeighbors(v3), cUp);
		
		ArrayList<Vertex> dUp = new ArrayList<Vertex>();
		dUp.add(v1);
		assertEquals(matrix.getUpstreamNeighbors(v4), dUp);
		
		
	}
	@Test
	public void test9b(){
		Graph matrix = new AdjacencyListGraph();
		for(int i = 0; i < 11000; i++){
			matrix.addVertex(new Vertex(Integer.toString(i)));
		}
		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		matrix.addVertex(v1);
		matrix.addVertex(v2);
		matrix.addEdge(v1, v2);
		matrix.addEdge(v2, v1);
		
		assertEquals(matrix.edgeExists(v1, v2), true);
		assertEquals(matrix.edgeExists(v2, v1), true);
		assertEquals(matrix.getDownstreamNeighbors(v1), Arrays.asList(v2));
		assertEquals(matrix.getDownstreamNeighbors(v2), Arrays.asList(v1));
	}
	@Test
	public void test10b()throws ArrayIndexOutOfBoundsException{
		Graph matrix = new AdjacencyListGraph();
		for(int i = 0; i < 11000; i++){
			matrix.addVertex(new Vertex(Integer.toString(i)));
		}
		try{
			matrix.addEdge(new Vertex("a"), new Vertex("b"));
			fail("Didn't throw exception");
		}catch(Exception e){}
	}
	@Test
	public void test11b(){
		
		Graph matrix = new AdjacencyListGraph();
		for(int i = 0; i < 11000; i++){
			matrix.addVertex(new Vertex(Integer.toString(i)));
		}
		Vertex v = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		
		matrix.addVertex(v);
		matrix.addVertex(v2);
		
		matrix.addEdge(v, v2);
		assertFalse(matrix.edgeExists(v2, v));
		
	}

	@Test
	public void test12b(){
		
		Graph matrix = new AdjacencyListGraph();
		for(int i = 0; i < 11000; i++){
			matrix.addVertex(new Vertex(Integer.toString(i)));
		}
		Vertex v = new Vertex("v1");
		
		matrix.addVertex(v);
		v.setLabel("v2");
		
		assertNotEquals(matrix.getVertices(), Arrays.asList(v));

	}

}
