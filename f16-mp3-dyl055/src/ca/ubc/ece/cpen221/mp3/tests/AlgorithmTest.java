package ca.ubc.ece.cpen221.mp3.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ca.ubc.ece.cpen221.mp3.graph.*;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;


public class AlgorithmTest {
	@Test
	public void GenerateGraph1(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyListGraph() );
			assertTrue( graph.getClass() == ( new AdjacencyListGraph() ).getClass() );
			assertEquals( 11, graph.getVertices().size()  );
			assertTrue( graph.edgeExists( new Vertex("1") , new Vertex("0") ) );
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void GenerateGraph2(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyListGraphNotDC() );
			assertTrue( graph.getClass() == ( new AdjacencyListGraphNotDC() ).getClass() );
			assertEquals( 11, graph.getVertices().size() );
			assertTrue( graph.edgeExists( new Vertex("1") , new Vertex("0") ) );
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void GenerateGraph3(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyMatrixGraph() );
			assertTrue( graph.getClass() == ( new AdjacencyMatrixGraph() ).getClass() );
			assertEquals( 11, graph.getVertices().size()  );
			assertTrue( graph.edgeExists( new Vertex("1") , new Vertex("0") ) );
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void GenerateGraph4(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyMatrixGraphNoDC() );
			assertTrue( graph.getClass() == ( new AdjacencyMatrixGraphNoDC() ).getClass() );
			assertEquals( 11, graph.getVertices().size() );
			assertTrue( graph.edgeExists( new Vertex("1") , new Vertex("0") ) );
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//check if closest distance work make sure the algorithm doesnt go backwards
	@Test 
	public void shortestDistance1(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyListGraph() );
			int distance = Algorithms.shortestDistance(graph, new Vertex("1"), new Vertex("0"));
			assertEquals( 1 , distance);
			 distance = Algorithms.shortestDistance(graph, new Vertex("0"), new Vertex("1"));
			assertFalse( distance == 1);			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// when nodes are not connected
	@Test
	public void shortestDistance2(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyListGraph() );
			int distance = Algorithms.shortestDistance(graph, new Vertex("10"), new Vertex("0"));
			assertEquals(-1, distance);

			distance = Algorithms.shortestDistance(graph, new Vertex("0"), new Vertex("10"));
			assertEquals(-1, distance);
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void shortestDistance3(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyListGraph() );
			int distance = Algorithms.shortestDistance(graph, new Vertex("6"), new Vertex("0"));
			assertEquals(1, distance);

			distance = Algorithms.shortestDistance(graph, new Vertex("6"), new Vertex("1"));
			assertEquals(3, distance);
			
			distance = Algorithms.shortestDistance(graph, new Vertex("6"), new Vertex("2"));
			assertEquals(2, distance);
			
			distance = Algorithms.shortestDistance(graph, new Vertex("6"), new Vertex("3"));
			assertEquals(1, distance);
			
			distance = Algorithms.shortestDistance(graph, new Vertex("6"), new Vertex("4"));
			assertEquals(3, distance);
			
			distance = Algorithms.shortestDistance(graph, new Vertex("6"), new Vertex("5"));
			assertEquals(2, distance);
			
			distance = Algorithms.shortestDistance(graph, new Vertex("6"), new Vertex("6"));
			assertEquals(0, distance);
			
			distance = Algorithms.shortestDistance(graph, new Vertex("6"), new Vertex("7"));
			assertEquals(2, distance);
			
			distance = Algorithms.shortestDistance(graph, new Vertex("6"), new Vertex("8"));
			assertEquals(2, distance);
			
			distance = Algorithms.shortestDistance(graph, new Vertex("6"), new Vertex("9"));
			assertEquals(3, distance);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void commonDownTest1(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyListGraph() );
			List<Vertex> output = Algorithms.commonDownstreamVertices(graph, new Vertex("7"), new Vertex("8"));
			List<Vertex> expected = new ArrayList<Vertex>();
			expected.add(new Vertex("1"));
			expected.add(new Vertex("2"));
			
			assertEquals(expected.size(), output.size());
			assertTrue(expected.contains(output.get(0)));
			assertTrue(expected.contains(output.get(1)));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void commonDownTest2(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyListGraph() );
			List<Vertex> output = Algorithms.commonDownstreamVertices(graph, new Vertex("5"), new Vertex("6"));
			List<Vertex> expected = new ArrayList<Vertex>();
			expected.add(new Vertex("3"));
			expected.add(new Vertex("0"));
			
			assertEquals(expected.size(), output.size());
			assertTrue(output.contains(expected.get(0)));
			assertTrue(output.contains(expected.get(1)));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void commonDownTest3(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyListGraph() );
			List<Vertex> output = Algorithms.commonDownstreamVertices(graph, new Vertex("5"), new Vertex("10"));
			assertEquals(0, output.size());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void commonUpTest1(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyListGraph() );
			List<Vertex> output = Algorithms.commonUpstreamVertices(graph, new Vertex("3"), new Vertex("4"));
			List<Vertex> expected = new ArrayList<Vertex>();
			expected.add(new Vertex("5"));
			expected.add(new Vertex("9"));
			
			assertEquals(expected.size(), output.size());
			assertTrue(expected.contains(output.get(0)));
			assertTrue(expected.contains(output.get(1)));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void commonUpTest2(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyListGraph() );
			List<Vertex> output = Algorithms.commonUpstreamVertices(graph, new Vertex("8"), new Vertex("0"));
			List<Vertex> expected = new ArrayList<Vertex>();
			expected.add(new Vertex("1"));
			expected.add(new Vertex("3"));
			expected.add(new Vertex("5"));
			
			assertEquals(expected.size(), output.size());
			assertTrue(output.contains(expected.get(0)));
			assertTrue(output.contains(expected.get(1)));
			assertTrue(output.contains(expected.get(2)));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void commonUpTest3(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyListGraph() );
			List<Vertex> output = Algorithms.commonUpstreamVertices(graph, new Vertex("5"), new Vertex("10"));
			assertEquals(0, output.size());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void depthTest1(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyMatrixGraph() );
			Set<List<Vertex>> output = Algorithms.depthFirstSearch(graph);
			
			assertEquals(11, output.size());
			for(List<Vertex> l : output){
				
				for(int index = 1; index < l.size(); index++){
					Vertex v = l.get(index);
					boolean flag = true;
					for(int i = 0; i < l.indexOf(v); i++){
						if( graph.getDownstreamNeighbors(l.get(i)).contains(v) ){
							flag = false;
							break;
						}
					}
					if(flag){
						assertFalse(true);
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void breadthTest1(){
		try {
			Graph graph = Algorithms.GenerateGraph( new AdjacencyMatrixGraph() );
			Set<List<Vertex>> output = Algorithms.breadthFirstSearch(graph);
			
			assertEquals(11, output.size());
			for(List<Vertex> l : output){
				
				for(int index = 1; index < l.size(); index++){
					Vertex v = l.get(index);
					boolean flag = true;
					for(int i = 0; i < l.indexOf(v); i++){
						if( graph.getDownstreamNeighbors(l.get(i)).contains(v) ){
							flag = false;
							break;
						}
					}
					if(flag){
						assertFalse(true);
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
