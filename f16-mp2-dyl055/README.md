**CPEN 221 / Machine Problem 2**

Validating HTML
===

## Logistics

+ This is a two-week-long machine problem, and will be due **October 7 by 11:59 p.m.**
+ This MP will be worth 3% of your final grade, and is an individual assignment.

## Background

Web pages are written in a language called the `Hypertext Markup Language`, or `HTML`. An HTML file consists of text surrounded by markings called tags. Tags give information to the text, such as formatting (bold, italic, etc.) or layout (paragraph, table, list). Some tags specify comments or information about the document (header, title, document type).

A tag consists of a named element between less-than `<` and greater-than `>` symbols. For example, the tag for making text bold uses the element `b` and is written as `<b>`. Many tags apply to a range of text, in which case a pair of tags is used: an opening tag indicating the start of the range and a closing tag indicating the end of the range. A closing tag has a `/` slash after its `<` symbol, such as `</b>`. So to make some text bold on a page, one would put the text to be bold between opening and closing `b` tags, `<b>`like this`</b>`. Tags can be nested to combine effects, `<b><i>`bold italic`</i></b>`. Some tags, such as the `br` tag for inserting a line break or `img` for inserting an image, do not cover a range of text and are considered to be "self-closing." Self-closing tags do not need a closing tag; for a line break, only a tag of `<br>` is needed. Some web developers write self-closing tags with an optional `/` before the `>`, such as `<br />`.

The distinction between a tag and an element can be confusing. A tag is a complete token surrounded by `<>` brackets, which could be either an opening or closing tag, such as `<title>` or `</head>`. An element is the text inside the tag, such as title or head. Some tags have attributes, which are additional information in the tag that comes after the element. For example, the tag `<img src="cat.jpg">` specifies an image from the file `cat.jpg`. The element is `img`, and the rest of the text such as `src` are attributes. In this assignment we will ignore attributes and focus just on elements and tags.

If you are curious about HTML, there are tutorials such as [http://www.w3schools.com/html/](http://www.w3schools.com/html/).

## Validating HTML

One problem on the web is that many web developers make mistakes in their HTML code. All tags that cover a range must eventually be closed, but some developers forget to close their tags. Also, whenever a tag is nested inside another tag, `<b><i>`like this`</i></b>`, the inner tag (i for italic, here) must be closed before the outer tag is closed. So the following tags are not valid HTML, because the </i> should appear first: `<b><i>`this is invalid`</b></i>`

Below is an example of a valid HTML file, with its tags in bold. `<!-- and -->` are comment tags.

```html
<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" >
<!-- This is a comment  -->
<html>
	<head>
		<title>Turtles are cool </title>
		<meta http-equiv="Content-Type" content="text/html" >
		<link href="style.css" type="text/css"  />
	</head>

	<body>
		<p>Turtles swim in the  <a href="http://ocean.com/" >ocean </a>.</p>
		<p>Some turtles are over 100 years old. Here is a picture of a turtle: 			
		<img src="images/turtle.jpg" width="100" height="100"  />
		</p>
	</body>
</html>
```

In this machine problem, you will develop a tool to examine HTML files and determine if it represents a valid sequence of tags. Your validator will use stacks and queues to match tags. We will provide starter code that will read HTML pages from files and break them apart into tags for you; you must implement the functionality to match tags.

### Task 1: Implementing a Stack

You will need to use a stack to implement the tag matching algorithm. A stack is a data structure that is used to maintain a last-in first-out (LIFO) sequence of objects. An outline of the `MyStack` class is provided in this GitHub repository (in the file `MyStack.java`).

Add the implementation for all the methods related to the stack. You can use an ArrayList as the internal list. (Java includes a built-in stack implementation but for this machine problem you have to create your own implementation; you cannot use the Stack that is part of `java.util`.)

### Task 2: HTML Validation

You will implement a class named `HtmlValidator`. You must use your stack implementation and the `Queue` implementation from `java.util`. Your class must have the constructors/methods below. It must be possible to call the methods multiple times in any order and get the correct results each time. Several methods interact with `HtmlTag` objects, described later. Unless otherwise specified, you may not create auxiliary data structures (such as arrays, lists, stacks, queues) in your implementation.

### Constructors

+ `public HtmlValidator()`
+ `public HtmlValidator(Queue<HtmlTag> tags)`

Your class should have two constructors. The first should initialize your validator to store an empty queue of HTML tags. The second should initialize your validator with an entirely separate copy of the queue that was passed in. If the caller passes a queue to your constructor, after your constructor is done executing, the client’s queue should have the same state as when it was passed in, and any subsequent modifications to one queue should not be reflected in the other.

The queue for the page shown previously would contain the tags below. Further tags can be added by calling `addTag`.

`front [<!doctype>, <!-- -->, <html>, <head>, <title>, </title>, <meta>, <link>, </head>, <body>, <p>, <a>, </a>, </p>, <p>, <img>, </p>, </body>, </html>] back`

If the queue passed is null, you should throw an `IllegalArgumentException`. An empty queue (size 0) is allowed. The constructors are allowed to construct a queue to store your validator's tags if necessary.

### Other methods

+ `public void addTag(HtmlTag tag)`: In this method you should add the given tag to the end of your validator's queue. If the tag passed is null, you should throw an IllegalArgumentException.
+ `public Queue<HtmlTag> getTags()`:  In this method you should return your validator's queue of HTML tags. The queue contains all tags that were passed to the constructor (if any) in their proper order; it should also reflect any changes made such as adding tags with `addTag` or removing tags with `removeAll`.
+ `public void removeAll(String element)`: In this method you should remove from your validator's queue any tags that match the given element. For example, if your validator is constructed using the tags from the page shown previously and `removeAll("p")` were called on it, your queue would be modified to contain the following tags. Notice that all `<p>` and `</p>` tags have been removed: `front [<!doctype>, <!-- -->, <html>, <head>, <title>, </title>, <meta>, <link>, </head>, <body>, <a>, </a>, <img>, </body>, </html>] back` If the element passed does not exactly match any tags (such as an empty string), your queue should not be modified. You may not use any auxiliary collections such as extra stacks or queues, though you can create simple variables. If the element passed is null, you should throw an IllegalArgumentException.
+ `public String validate()`: In this method you should return a `String` that is an indented text representation of the HTML tags in your queue. In this `String`, each tag is on its own line. Every opening tag that requires a closing tag increases the level of indentation of following tags by four spaces until its closing tag is reached. The output for the HTML file earlier in the problem statement would be:

```html
<!doctype>
<!-- -->
<html>
	<head>
		<title>
		</title>
		<meta>
		<link>
	</head>
	<body>
		<p>
			<a>
			</a>
		</p>
		<p>
			<img>
		</p>
	</body>
</html>
```

To generate the output, analyze your queue of tags with a stack. The central idea of the algorithm is that when you see an opening tag that is not self-closing, you should push it onto a stack and increase your indentation. When you see a closing tag, you should pop the top element from the stack and decrease your indentation. You may use a single temporary stack (in addition to your validator's queue of tags) to compute the result. You may not use any other collections although you can create as many simple variables as you like. You will also handle errors as described below.

### Error Handling

Your `validate` method should add error messages to the output string if you encounter either of the following conditions in the HTML:

+ A closing tag that does not match the most recently opened tag (or if there are no open tags at that point);

+ Reaching the end of the HTML input with any tags still open that were not properly closed.

For example, the following HTML is valid: `<p><b>bold text <i>bold and italic text</i> just bold again</b> <br/> more </p>`

But the following HTML is not valid, because the `</b>` appears before the `</i>`: `<p><b> bold text <i>bold and italic text</b> just italic</i> neither</p>`

The following HTML is also not valid, because the `<html>` tag is never closed: `<html><body> <b><i>bold italic</i></b> normal text</body>`

Suppose the previous short HTML file were modified to add several errors, as follows: a deleted `</title>` tag, an added second `</head>` tag, an added `</br>` tag and a deleted `</body>` tag:

```html
<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- This is a comment -->

<html>

	<head>
		<title>Turtles are cool

		<meta http-equiv="Content-Type" content="text/html">

		<link href="style.css" type="text/css" rel="stylesheet" />
	</head>
	</head>
	<body>
	<p>Turtles swim in the <a href="http://ocean.com>ocean</a>.</p>

	</br>
		<p>Some turtles are over 100 years old. Here is a picture of a turtle:
			<img src="images/turtle.jpg" width="100" height="100">
		</p>
</html>
```

The resulting output for this invalid file should be the following:

```html
<!doctype>
<!-- -->
<html>
	<head>
		<title>
			<meta>
			<link>
ERROR unexpected tag: </head>
ERROR unexpected tag: </head>
			<body>
				<p>
					<a>
					</a>
				</p>
ERROR unexpected tag: </br>
				<p>
					<img>
				</p>
ERROR unexpected tag: </html>
ERROR unclosed tag: <body>
ERROR unclosed tag: <title>
ERROR unclosed tag: <head>
ERROR unclosed tag: <html>
```

The reason that there are two error messages for `</head>` are because neither `</head>` tag seen matches the most recently opened tag at the time, which is `<title>`. The four unclosed tags at the end represent the fact that those four tags did not have a closing tag in the right place (or, in some cases, no closing tag at all).

The tag `</br>` is unexpected because `<br>` is self-closing (that is, `isSelfClosing()` returns true when called on any `HtmlTag` object representing a `br` tag. See below for `HtmlTag`). Self-closing tags are always opening tags.

Because of the simplicity of our algorithm, a single mistake in the HTML can result in multiple error messages. Near the end of the file is a `</html>` tag, but this is not expected because body, title, and head were never closed. So the algorithm produces a string with many errors, such as saying that the html tag is unclosed, though the underlying problem is that the body tag was never closed. Also notice that an unexpected closing tag does not change the indentation level of the output.

The complete validation algorithm: Examine each tag from the queue, and if it is an opening tag that requires a closing tag, push it onto a stack and increase indentation. If it is a closing tag, compare it to the tag on top of the stack. If the two tags match, pop the top tag of the stack and decrease indentation. If they don't match, it is an error. Any tags remaining on the stack at the end are errors.

### Specifications and JUnit Tests

Write specifications for methods in the `HtmlValidator` class as well as the `MyStack` class and then write unit tests for these methods using `JUnit`. When writing specifications, you must write the requires and effects aspects of the specification in English and then using Java’s documentation conventions that use the `@param`, `@return` and `@throws` clauses. When writing unit tests, you should cover as many situations as possible.

The other files included in the repository should not be changed but you should read the code to understand their implementation and purpose.

+ `HtmlTag.java`: Objects that represent HTML tags that you need to process.
+ `ValidatorMain.java`: A basic testing harness to run your validation mechanism.

An `HtmlTag` object corresponds to a tag such as `<p>` or `</table>`. You do not ever need to construct `HtmlTag` objects in your code, but you will process them from your queue. Each object has the following methods:

+ `public String getElement()`: Returns this HTML tag's element name, such as "table".
+ `public boolean isOpenTag()`:	Returns true if this tag is an opening tag, such as `<p>` or `<b>`. Self-closing tags like <br /> will return true as well.
+ `public boolean isSelfClosing()`:	Returns true if this element does not require a closing tag, which is the case for elements such as `br` and `img`.
+ `public boolean matches(HtmlTag other)`:Returns true if this tag and the given tag have the same element but opposite types, such as `<body>` and `</body>`.
+ `public HtmlTag clone()`: Returns a reference to an object that is clone of `this` HtmlTag object.
+ `public String toString()`:	Returns a string representation of this HTML tag, such as `"<p>"` or `"</table>"`.There may be some confusion about the difference between an `HtmlTag` object and a `String`. An `HtmlTag` is related to a `String` in that it stores an element as a `String` inside of it; and in that a tag has a natural representation as a `String` by surrounding its element with `<` and `>` brackets. But the `HtmlTag` object is more useful than a bare `String` in that it has the methods above. So you can ask it if it is an opening/closing tag or whether it matches another tag without needing to manually trim away `<` and `>` characters. It may seem like additional work to have to figure out how `HtmlTag` objects work, when `Strings` are more familiar. But, part of becoming a mature software developer is becoming comfortable with pre-written code that is provided to you by others, and using it to solve part of a larger overall task.

You are also provided with some sample HTML files and the expected output for these files in the `testcases` directory.

## Development Strategy and Hints

We suggest the following development strategy for solving this program:

+ Create the class and declare method stubs (full header but blank body). If necessary, return a "dummy" value.
+ Get your code to run in the `ValidatorMain` program, though the output will be incorrect.
+ Implement the bodies of the constructors, getTags, and add methods.  
+ Write the `removeAll( )` method.  
+ Write an initial version of the `validate` algorithm described previously that assumes the page is valid and does not worry about errors. Get the overall algorithm, output, and indentation working for valid HTML.
+ Add the `validate` code that looks for errors.
+ `null`: The value `null` is a special value that indicates the lack of an object; a reference that does not refer to any object. When a given method's spec says, "if foo is null, do X," it means that you should test: `if (foo == null) { X }`. In particular, a `null` queue is not the same as an empty queue; a `null` string is not the same as the empty string, `“”`; and a `null HtmlTag` is not the same as an `HtmlTag` with a `null` element (which will not occur anyway, since `HtmlTag` throws an exception if you try to create one with a null element).
+ The `validate` method is the toughest. It can be hard to handle all of the errors properly and to get the indentation just right. Remember that the indentation increases when an opening tag is seen, and the indentation decreases when a valid expected closing tag is seen. Those should be the only two times you ever need to adjust your indentation.
+ Avoid redundancy and repeated logic as much as possible in your code.
+ Properly encapsulate your objects by making fields private. Avoid unnecessary fields; use fields to store important data of your objects but not to store temporary values only used within a single method. Initialize fields in constructors only.
+ Follow good general style guidelines such as: appropriately using control structures like loops and if/else statements; avoiding redundancy using techniques such as methods, loops, and if/else factoring; properly using indentation, good variable names, and proper types; and not having any lines of code longer than 80 characters.
+ Comment descriptively at the top of your class, each method, and on complex sections of your code. Comments should explain each method's behavior, parameters, return, pre/post-conditions, and any exceptions thrown. Write descriptive comments that explain error cases, and details of the behavior that would be important to the client.
+ If you do not already follow a well-established style guideline for source code then you should consider the code conventions for Java. It is also good practice to keep your methods short. Methods with more than about 60 lines of code inhibit readability.

## References

* ArrayList: http://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html
* Queue: http://docs.oracle.com/javase/7/docs/api/java/util/Queue.html
* Stacks: http://en.wikipedia.org/wiki/Stack_(abstract_data_type)
* JUnit: http://www.vogella.com/tutorials/JUnit/article.html

## Submission

Complete the implementation as described. Add comments and specifications. This is how you become proficient at these activities.

Submit your work by pushing your work to the Github repository that was created for you for this machine problem.

In terms of correctness, your class must provide all of the functionality described above and must satisfy all of the constraints mentioned in this writeup. In terms of style, we will be grading on aspects such as your use of comments, good variable names, consistent indentation, minimal fields and good coding style to implement these operations.
