package twitterAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import ca.ubc.ece.cpen221.mp3.graph.AdjacencyListGraph;
import ca.ubc.ece.cpen221.mp3.graph.Algorithms;
import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;


public class TwitterAnalysis {
	

	private static Set<Vertex> allVertices;
	private static Graph graph;
	private static List<String> outputs;
	private static Set<String> finishedQueries;

	/**
	 * Purpose: compute the output of the queries and store them at the output location
	 * 
	 * @param args
	 * args[0] should be the location of the queries
	 * args[1] should be the location of the output file
	 * 
	 * preCondition the queries should be the correct format at the specified location
	 */
	public static void main(String args[]){
		long startTime = System.currentTimeMillis();

		File queries = new File(args[0]);
		if(!queries.exists() || queries.isDirectory()){
			System.out.println("the queries file is not found");
			return;
		}
		finishedQueries = new HashSet<String>();
		outputs = new ArrayList<String>();
		allVertices = new HashSet<Vertex>();
		graph = new AdjacencyListGraph();

		File outputFile = new File(args[1]);
				
		readFile();


		runQueries(queries);
		
		try {
			Files.write(outputFile.toPath(), outputs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		System.out.println("Total run time: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
	/**
	 * Purpose: used to read queries and call on the appropriate function to compute them
	 * @param queries File that points to the query file location
	 * @throws IOException 
	 */

	private static void runQueries(File queries){
		// TODO Auto-generated method stub
		try {
			BufferedReader reader = new BufferedReader( new FileReader(queries) );
			String line = null;
			while((line = reader.readLine()) != null && line.length() != 0){

				if(finishedQueries.contains(line)){
					continue;
				}
				finishedQueries.add(line);

				String[] args = line.split(" ");
				switch(args[0]){
					case "commonInfluencers":
						commonInfluencers(args[1], args[2]);
						break;
					case "numRetweets":
						numRetweets(args[1], args[2]);
						break;
					default:
						System.out.println("unknown query" + args[0]);
						break;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}


	/**
	 * Purpose: find the common upstream Vertex of two Vertex
	 * @param arg1 the label of the first Vertex
	 * @param arg2 the label of the second Vertex
	 * Effects: adds to Outputs the output of this function
	 */
	private static void commonInfluencers(String arg1, String arg2) {

		Vertex v1 = new Vertex(arg1);
		Vertex v2 = new Vertex(arg2);
		
		List<Vertex> cUV = Algorithms.commonUpstreamVertices(graph, v1, v2);
		
		outputs.add("query: commonInfluencers " + arg1 + " " + arg2);
		outputs.add("<result>");
		
		Collections.sort(cUV, new Comparator<Vertex>(){
			@Override
			public int compare(Vertex v, Vertex y){
				return v.toString().compareTo(y.toString());
			}
		});
		for (Vertex v : cUV){
			outputs.add(v.toString());
		}
		
		
		
		outputs.add("</result>");
	}

	/**
	 * Purpose: Find the shortest distance between arg1 and arg2
	 * @param arg1 the label of the Vertex to begin at
	 * @param arg2 the label of the Vertex to end at
	 * Effects: adds the output of this function to Outputs
	 */
	private static void numRetweets(String arg1, String arg2) {
		// TODO Auto-generated method stub
		outputs.add("query: numRetweets " + arg1 + " " + arg2);
		outputs.add("<result>");
		outputs.add( Integer.toString(Algorithms.shortestDistance(graph, new Vertex(arg1), new Vertex(arg2))) );
		outputs.add("</result>");
	}

	/**
	 * Purpose: Read from the twitter data set and populate Graph with it
	 * Effects: modifies Graph by adding new Vertex and edges to it
	 */
	private static void readFile(){
		long startTime = System.currentTimeMillis();
		File connections = new File("datasets/twitter.txt");

		try {

			String line = null;
			
			BufferedReader reader = new BufferedReader( new FileReader(connections) );

			while((line = reader.readLine()) != null && line.length() != 0){
				String[] args = line.split(" -> ");
				Vertex v1 = new Vertex( args[0] );
				if( !allVertices.contains(v1) ){
					allVertices.add(v1);
					graph.addVertex(v1);
				}
				Vertex v2 = new Vertex( args[1] );
				if( !allVertices.contains(v2) ){
					allVertices.add(v2);
					graph.addVertex(v2);
				}
				graph.addEdge(v1, v2);
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Read time: " + (System.currentTimeMillis() - startTime) + "ms");
	}
}
