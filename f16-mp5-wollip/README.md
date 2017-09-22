CPEN 221 / Machine Problem 5

Restaurants, Queries and Statistical Learning
===

This machine problem is designed to allow you to explore multiple aspects of software construction:
+ managing complex ADTs;
+ multithreading and the client-server pattern;
+ query parsing and execution.

In addition to these aspects, the problem also touches upon rudimentary methods for statistical inference and learning.

### Background

For this machine problem, you will work with an excerpt from the [Yelp Academic Dataset](https://www.yelp.com/academic_dataset). Specifically, you will work with data (in [JSON](https://en.wikipedia.org/wiki/JSON) format) on restaurants, and this data includes information about some restaurants, reviews of the restaurants, and user information (for those contributing reviews).

You will use the dataset to create and maintain a simple in-memory database with restaurants, users and reviews. (Since the Yelp Academic Dataset does not contain details of business near UBC we are using information for restaurants near UC Berkeley or UCB!)

The given dataset is in the JSON format and you can use [JSON Processing project](https://jsonp.java.net) implementation of a framework for working with JSON in Java 8. You will see that the JSON Processing project suggests that you use a build manager called Maven for obtaining the relevant libraries and including them in your project. Alternatively, you can download the JAR files and include them in your `CLASSPATH` for compilation and for execution.

### Part I: Building a `RestaurantDB` datatype

The first part of this machine problem is to build a `RestaurantDB` datatype that loads the given Yelp dataset into memory and then supports some operations on the dataset.

At the minimum, this datatype should have a constructor that takes three `String`s as arguments: these `Strings` represent filenames. The first file is the list of restaurants, the second file is the list of reviews and the third file is the user list.

### Part II: Statistical Learning

> Look at the skeleton code in the package for `statlearning`.

In this part of the machine problem you will implement two approaches to statistical machine learning: one is an instance of unsupervised learning and the second is an instance of supervised learning. Statistical learning is an exciting area for computing today!

#### k-means Clustering

Suppose you are given a set of (x, y) coordinates, you may sometimes want to group the points into _k_ clusters such that no point is closer to the center point (centroid) of a cluster other than the one to which it is assigned. In the case of restaurants, this approach may allow us to group restaurants that are in the same neighbourhood even without knowing anything about the neighbourhoods in a city. _A similar approach is used to group similar products on online shopping services such as Amazon._

The k-means algorithm finds k centroids within a dataset that each correspond to a cluster of inputs. To do so, k-means clustering begins by choosing k centroids at random, then alternates between the following two steps:

1. Group the restaurants into clusters, where each cluster contains all restaurants that are closest to the same centroid.
2. Compute a new centroid (average position) for each non-empty cluster.

This [visualization](http://tech.nitoyon.com/en/blog/2013/11/07/k-means/) is a good way to understand how the algorithm works.

For the k-means clustering algorithm, you should implement a method that returns a `List` of `Set`s: each `Set` representing a cluster of restaurants. You should also implement a method that converts such a `List` to JSON format as illustrated by the JSON file `voronoi.json` in the directory `visualize`.

You can run the provided visualization method using `python` (Python 3) and the visualization is called a [Voronoi tesselation](https://en.wikipedia.org/wiki/Voronoi_diagram).

> One can visualize the tessalation produced by k-means clustering by writing the JSON formatted cluster information to `voronoi.json` in the `visualize` directory and then launch `visualize.py` as follows: `python3 visualize.py`
> For the curious, you can also see some Javascript in action here.

#### Least Squares Regression

As an instance of supervised learning, you will implement an algorithm for predicting the rating that a user may give to a restaurant.

By analyzing a user's past ratings, we can try to predict what rating the user might give to a new restaurant.

To predict ratings, you will implement simple least-squares linear regression, a widely used statistical method that approximates a relationship between some input feature (such as price) and an output value (the rating) with a line. The algorithm takes a sequence of input-output pairs and computes the slope and intercept of the line that minimizes the mean of the squared difference between the line and the outputs.

Implement the `getPredictor` method, which takes a user and a feature function (as well as the `RestaurantDB`), and returns a _function_ that predicts the users ratings.

One method of computing these values is by calculating the sums of squares, S<sub>xx</sub>, S<sub>yy</sub>, and S<sub>xy</sub>:

+ S<sub>xx</sub> = Σ<sub>i</sub> (x<sub>i</sub> - mean(x))<sup>2</sup>
+ S<sub>yy</sub> = Σ<sub>i</sub> (y<sub>i</sub> - mean(y))<sup>2</sup>
+ S<sub>xy</sub> = Σ<sub>i</sub> (x<sub>i</sub> - mean(x))(y<sub>i</sub> - mean(y))

After calculating the sums of squares, the regression coefficients, and R<sup>2</sup> (`r_squared`), which is an estimate of the quality of the predictor, are defined as follows:

+ b = S<sub>xy</sub> / S<sub>xx</sub>
+ a = mean(y) - b * mean(x)
+ R<sup>2</sup> = S<sub>xy</sub><sup>2</sup> / (S<sub>xx</sub> S<sub>yy</sub>)

Also implement the `getBestPredictor` method that takes a user and a list of feature functions and returns the _best_ predictor function (the one that results in the highest R<sup>2</sup> value).

Consider the following feature functions for this machine problem:
+ restaurant price scale
+ restaurant mean rating
+ restaurant location: latitude
+ restaurant location: longitude

In this machine problem, we will use a **functional interface** to pass and return functions but we could have also considered using [lambdas that Java 8 supports](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html).

> To pass and return functions in this machine problem, you can have classes that implement the interface `LeastSquaresRegression` which contains a single method to be implemented `lsrf`. Different implementations of the interface will allow for different functions `lsrf`.

### Part III: A RestaurantDB Server

In the next part of this machine problem, you should implement a multi-threaded server application, `RestaurantDBServer` that wraps a RestaurantDB instance.

One should be able to start the server from the command line using

```
java ca.ece.ubc.cpen221.mp5.RestaurantDBServer 4949
```

where `4949` is the port number at which the server should listen for connection requests. The server should use the command line argument to decide which port number to bind to.

The server should be able to handle more than one connection at the same time (and hence the need for multithreading).

### Part IV: Handling Simple Requests

The server should be able to handle some simple requests from a client that connects to it.

Here are five simple requests that you should implement:

+ `RANDOMREVIEW <restaurant name>`: If a client sends a string that begins with `RANDOMREVIEW` then the next word in that string should be interpreted as a restaurant name and the server should return (in JSON format, using the Yelp standard) a random review for that restaurant. If there is to such restaurant, the server should return the string `ERR: NO_RESTAURANT_FOUND`. If the restaurant name matches more than one restaurant then the server should return `ERR: MULTIPLE_RESTAURANTS`. (Note that the restaurant name is not wrapped in `< >`. The use of `< >` is to indicate that the command should be followed by a required argument.)
+ `GETRESTAURANT <business id>`: To this request, the server should respond with the restaurant details in JSON format for the restaurant that has the provided business identifier. If there is no such restaurant then one should use the error message as above.
+ `ADDUSER <user information>`: This request is a string that begins with the keyword (in our protocol), `ADDUSER`, followed by user details in JSON, formatted as suited for the Yelp dataset. Since we are adding a new user the JSON formatted information will contain only the user's name. So the JSON string may look like this `{"name": "Sathish G."}`. The server should interact with the RestaurantDB to create a new user, generate a new userid (it does not have to be in the Yelp userid format, you can use your own format for new users), generate a new URL (although it is a dummy URL) and then acknowledge that the user was created by responding with a more complete JSON string such as:
  `{"url": "http://www.yelp.com/user_details?userid=42", "votes": {}, "review_count": 0, "type": "user", "user_id": "42", "name": "Sathish G.", "average_stars": 0}`. If the argument to the `ADDUSER` command is invalid (not JSON format, missing name, etc.) then the server would respond with the message `ERR: INVALID_USER_STRING`. Note that the server can create a new user if the argument to this command is a valid JSON string and has a field called `name` but also has other information (which can be ignored).
+ `ADDRESTAURANT <restaurant information>`: This command has structure similar to the `ADDUSER` command in that the JSON string representing a restaurant should have all the necessary details to create a new restaurant except for details such as `business_id` and `stars`. The valid error messages (as needed) are `ERR: INVALID_RESTAURANT_STRING` and `ERR: DUPLICATE_RESTAURANT` (if a new restaurant is added at the same location as another restaurant).
+ `ADDREVIEW <review information>`: The last simple command to implement is an `ADDREVIEW` command which has the same principle as the other commands. The possible error codes are `ERR: INVALID_REVIEW_STRING`, `ERR: NO_SUCH_USER` and `ERR: NO_SUCH_RESTAURANT`.

Remember that when multiple clients are making such requests to change the database you will need to deal with potential data races and other concurrency-related conflicts.

### Part V: Structured Queries

The final part of this machine problem is to support structured queries over the database you have constructed. The request-response pattern will be handled by the `RestaurantDBServer` as was the case with "simple" requests earlier.

We would like to process queries such as "list all restaurants in a neighbourhood that serve Chinese food and have moderate ($$) price."

In our request-response model, the request would begin with the keyword `QUERY` followed by a string that represents the query.

A query string may be: `in(Telegraph Ave) && (category(Chinese) || category(Italian)) && price(1..2)`. This query string represents a query to obtain a list of Chinese and Italian restaurants in the Telegraph Avenue neighbourhood that have a price range of 1-2.

For the query string above, the server would respond with a list of restaurants in JSON notation. If no restaurants match the query (for any reason) then the server should respond with `ERR: NO_MATCH`. If no query string is sent or if the query is ill-formed then the response should be `ERR: INVALID_QUERY`.

The grammar for the query language looks something like this:

```
<orExpr> ::= <andExpr>(<or><andExpr>)*
<andExpr> ::= <atom>(<and><atom>)*
<atom> ::= <in>|<category>|<rating>|<price>|<name>|<LParen><orExpr><RParen>
<or> ::= "||"
<and> ::= "&&"
<in> ::= "in" <LParen><string><RParen>
<category> ::= "category" <LParen><string><RParen>
<name> ::= "name" <LParen><string><RParen>
<rating> ::= "rating" <LParen><range><RParen>
<price> ::= "price" <LParen><range>|<singlePrice><RParen>
<range> ::= [1-5]..[1-5]
<singlePrice> ::= [1-5]
<LParen> ::= "("
<RParen> ::= ")"
```

### Grading Guidelines

We will grade your work using the following _approximate_ breakdown of the aspects of required.

We will use the following approximate rubric to grade your work:		

| Task | Grade Contribution |		
|:----|---:|		
| k-means Clustering | 20% |		
| Least Squares Regression | 20% |
| Database Implementation: Simple Requests | 35% |		
| Database Implementation: Structured Queries | 25% |

Functionality apart, we will evaluate your submissions carefully along the following dimensions:
+ code style;
+ clear specifications for methods;
+ implementation of unit tests;
+ code-level comments as appropriate;
+ comments the indicate clearly what the representation invariants and abstraction functions are for the datatypes you create.

You may lose up to 40% of your score for not following some of the above non-functional requirements.

### Hints

- Use example code we have provided to implement a multi-threaded server.
- You can use a parser generator (such as ANTLR) for parsing queries (or roll your own). But it is worth learning how to use a parser generator.
- There are several easier tasks you can accomplish before focusing on structured queries. **Use your time wisely.**
- When you complete this assignment, you would have implemented an approximation of a relational database. In the relational database world, a row is analogous to an instance of a datatype while a table definition is analogous to a datatype definition.
