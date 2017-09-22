CPEN 221: Lab 11
====
Big-data processing, map-reduce like computing --> Java8 lambdas, stream processing

## Objectives
 
Getting some familiarity with expressing big-data processing, map-reduce like computing, and functions as a first-class construct. More concretely, for Java 8, this means playing with *lambdas* and *stream processing*. 

## Dataset
 
You will work with the user-related data the latest [Yelp Academic Dataset] (https://www.yelp.com/academic_dataset). You might even want to think about a good problem for their $5000 challenge award! 

This data is in [JSON format] (http://www.json.org/).  The user data includes information on user names, summaries about their reviews, and the social network the users form (i.e., friendships).   (Note: for MP5 we may ask you to process a different subset of the dataset that will include reviews and restaurants).

## Tasks 

You have three tasks:
 
1. Load the dataset,
2. code some processing of the dataset using Java8 streams, and 
3. measure runtime to gain an intuition on overheads and as to where parallelism might help. 

The following gives you a few pointers on how to make progress with the above tasks, what additional materials to read, and what are the questions you may want to ask.  This activity may take longer than two hours and, at the same time, gives you a head start with MP5. Discussions with fellow students and your TA will help.


### Step 1: Loading the dataset. 

There are multiple libraries to parse JSON files.  One that's fairly popular and easy to use is [json-simple] (https://code.google.com/archive/p/json-simple).   You need to first [download] (https://code.google.com/archive/p/json-simple/downloads) and install the library.  (MP5 suggests another library, using either is fine.)

Once you've installed the JSON-simple library you can parse the file.  There are numerous online [tutorials] (https://www.tutorialspoint.com/json/json_java_example.htm) on how to use it. To get you started, to parse this simple file presenting a simple JSON object with three attributes: 

```
{
	"age":100,
	"name":"mkyong.com",
	"messages":["msg 1","msg 2","msg 3"]
} 
```

The following code snippet can be used:

```
	import java.io.FileNotFoundException;
	import java.io.FileReader;
	import java.io.IOException;
	import java.util.Iterator;
	import org.json.simple.JSONArray;
	import org.json.simple.JSONObject;
	import org.json.simple.parser.JSONParser;
	import org.json.simple.parser.ParseException;

	JSONParser parser = new JSONParser();

	try {
		Object obj = parser.parse(new FileReader("c:\\test.json"));
		JSONObject jsonObject = (JSONObject) obj;

		String name = (String) jsonObject.get("name");
		System.out.println(name);

		long age = (Long) jsonObject.get("age");
		System.out.println(age);

		// loop array
		JSONArray msg = (JSONArray) jsonObject.get("messages");
		Iterator<String> iterator = msg.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
```



### Step 2: Simple processing with streams.

Read the [Java processing with streams] (http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html) tutorial for a quick introduction.   

As a start, you might want to look at the following code snippet that creates a stream that reads each line from a file and then processes it (it parses each line to return a YelpUser, then adds it to a collection of users).  (Note the [try-with-resources](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html) idiom used below. 
)

```
    	//read file into stream, try-with-resources
    	Map<String,YelpUser> mUsers = new HashMap<String,YelpUser>();
    	try (Stream<String> stream = Files.lines(Paths.get(myFile))) {
    		stream.map(JsonSimpleExample::getYelpUser)
    					.forEach(yelpUser -> mUsers.put(yelpUser.userID, yelpUser));
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
```

The (unoptimized) code to parse some of the user data will could look like this: 

```
    private static YelpUser getYelpUser (String jText) {
    	YelpUser y = new YelpUser();
    	try { 
    		JSONParser parser = new JSONParser();
    		Object obj = parser.parse(jText);
    		JSONObject jsonObject = (JSONObject) obj;

    		y.name = (String) jsonObject.get("name");
    		y.userID = (String) jsonObject.get("user_id");
    		y.numReviews = (Long) jsonObject.get("review_count");
    		y.sFriendsList = new ArrayList<String>();

    		// loop array - might consume a lot of memory
    		// JSONArray friends = (JSONArray) jsonObject.get("friends");
    		// Iterator<String> iterator = friends.iterator();
    		// while (iterator.hasNext()) {
    		// 	    y.sFriendsList.add(iterator.next());
    		// }
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	return y;
    }
```


Your task is to implement some simple processing that answers the following queries:  

* (simple) How many users are there?  What's the average connectivity in the friendship graph?  What's the average/max number of reviews a user has entered? How many friendship links are there?  

* (medium complexity) Compute data for a histogram that has on X axis number of users and on Y axis, how many users have created the corresponding number of reviews (e.g., 1 million users with between 1-9 reviews, 100k with 10-99 reviews, etc).  

* (complex):  Is there a relationship between friendship in the user graph and reviewing behaviour (e.g., to friends tend to rate the same number of restaurants,  do they give the same reviews, can you detect 'crowd' behaviour)?  

Constraint: you should be able to implement all these queries with less than 10 lines of code and  without using for/while loops. 

More introductory readings: [Part II] (http://www.oracle.com/technetwork/articles/java/architect-streams-pt2-2227132.html) of the tutorial above might felp for the more advanced tasks and [IBM tutorials] (https://www.ibm.com/developerworks/views/global/libraryview.jsp?series_title_by=Java+Streams) on Java streams.


### Step 3: Gaining some insights about overheads and benefits of parallelism. 

For each type of query try to understand where the main overheads are:  reading the data, parsing it to create Java objects, or processing and aggregating it?   Where does the parallelism offered by Java8 streams help? 

Here is a simple way to estimate runtime: 

```
    long startTime = System.nanoTime();
    /// ... your processing
    System.out.println("Runtime: " + (System.nanoTime() - startTime) / 1000000 + "ms");
```

## To submit

Submit: 

1. Your code that answers some of the questions above, and
2. a text file that briefly describes your conclusions regarding runtime overheads. (Add a partners.txt file as always.)

Ask the teaching assistant for clarifications. If the lab session does not provide sufficient time then ask on Piazza.
