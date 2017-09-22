package ca.ubc.ece.cpen221.mp3.tests;


import static java.nio.file.Files.readAllLines;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import twitterAnalysis.TwitterAnalysis;

public class TwitterTest {
	/*
	 * Tests the input queries and checks it with the expected output
	 * by comparing the list of strings that are given in the output
	 */
	@Test
	public void test1() throws IOException{
		String queries = new String("datasets/queries.txt");
		String output = new String("datasets/output.txt");

		String[] args = {queries, output};
		
		TwitterAnalysis.main(args);

		List<String> f1 = readAllLines(new File(output).toPath());
		List<String> f2 = readAllLines(new File("datasets/expected.txt").toPath());
		
		assertEquals(f1,f2);
		
	}

}
