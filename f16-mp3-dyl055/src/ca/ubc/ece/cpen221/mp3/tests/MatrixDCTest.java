package ca.ubc.ece.cpen221.mp3.tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ca.ubc.ece.cpen221.mp3.graph.AdjacencyMatrixGraph;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class MatrixDCTest {
	@Test
	public void test1(){
		
		Graph matrix = new AdjacencyMatrixGraph();
		Vertex v = new Vertex("v1");
		ArrayList<Vertex> aL = new ArrayList<Vertex>();
		aL.add(v);
		matrix.addVertex(v);
		assertEquals(matrix.getVertices(), aL);
		
	}
	
	@Test
	public void test2(){
		
		Graph matrix = new AdjacencyMatrixGraph();
		Vertex v = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		
		matrix.addVertex(v);
		matrix.addVertex(v2);
		
		matrix.addEdge(v, v2);
		assertTrue(matrix.edgeExists(v, v2));
		
	}
	@Test
	public void test3(){
		
		Graph matrix = new AdjacencyMatrixGraph();
		Vertex v = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		
		matrix.addVertex(v);
		matrix.addVertex(v2);
		assertFalse(matrix.edgeExists(v, v2));
		
	}
	@Test
	public void test4(){
		
		Graph matrix = new AdjacencyMatrixGraph();
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
	public void test5(){
		
		Graph matrix = new AdjacencyMatrixGraph();
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
	public void test6(){
		Graph matrix = new AdjacencyMatrixGraph();
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
	
	@Test
	public void test7(){
		
		Graph matrix = new AdjacencyMatrixGraph();
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
	public void test8(){
		Graph matrix = new AdjacencyMatrixGraph();
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
	public void test9(){
		Graph matrix = new AdjacencyMatrixGraph();
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
	public void test10()throws ArrayIndexOutOfBoundsException{
		Graph matrix = new AdjacencyMatrixGraph();
		try{
			matrix.addEdge(new Vertex("a"), new Vertex("b"));
			fail("Didn't throw exception");
		}catch(Exception e){}
	}
	@Test
	public void test11(){
		
		Graph matrix = new AdjacencyMatrixGraph();
		Vertex v = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		
		matrix.addVertex(v);
		matrix.addVertex(v2);
		
		matrix.addEdge(v, v2);
		assertFalse(matrix.edgeExists(v2, v));
		
	}
	//Checks references
	//Should change whether graph implementation uses defensive copying
	@Test
	public void test12(){
		
		Graph matrix = new AdjacencyMatrixGraph();
		Vertex v = new Vertex("v1");
		
		matrix.addVertex(v);
		v.setLabel("v2");
		
		assertNotEquals(matrix.getVertices(), Arrays.asList(v));

	}
	@Test
	public void test13(){
		Graph matrix = new AdjacencyMatrixGraph();
		for(int i = 0; i < 70000; i++){
			matrix.addVertex(new Vertex(Integer.toString(i)));
		}
	}

	
	@Test
	public void test2b(){
		
		Graph matrix = new AdjacencyMatrixGraph();
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
		
		Graph matrix = new AdjacencyMatrixGraph();
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
		
		Graph matrix = new AdjacencyMatrixGraph();
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
		
		Graph matrix = new AdjacencyMatrixGraph();
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
		
		Graph matrix = new AdjacencyMatrixGraph();
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
		Graph matrix = new AdjacencyMatrixGraph();
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
		Graph matrix = new AdjacencyMatrixGraph();
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
		Graph matrix = new AdjacencyMatrixGraph();
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
		
		Graph matrix = new AdjacencyMatrixGraph();
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

}
