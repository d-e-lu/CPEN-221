# CPEN 221 / Lab 12: Lambdas + Grammars & Parsing

## Goals

This activity has two goals: (i) give you exposure to lambda functions feature in Java8 and connect to functional intefaces, and (ii) offer a brief introduction to parsing and parser generators with ANTLR. 

These two goals should help you make progress with MP5

## Task 1:  Lambda functions in Java8

Reading: The [Java tutorial] (https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html) is a good starting point. 

The following is an example for passing a lambda function as an argument and the relationship with functional interfaces. 

Imagine the following functional interface declaration.

```
    @FunctionalInterface
    public interface SimpleFuncInterface {
        public void doWork();
    }
```

Note 1: The annotation  [@FunctionalInterface] (https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html) annotation can be used to instruct the compiler to check that this is a valid Functional Interface. You'll use this annotation for the same reason you use @Override annotation  (If you add another abstract method to the interface then the compiler will flag it as an error). 

Note 2: An interface can extend another interface, and, in case the interface it is extending in functional and the new interface doesn’t declare any new abstract methods, then the new interface is also functional. A functional interface can have only one abstract method and any number of [default methods] (https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html). 

```
    @FunctionalInterface
    public interface ComplexFunctionalInterface extends SimpleFuncInterface {
        default public void doSomeWork(){
            System.out.println("Doing some work in interface impl...");
        }
        default public void doSomeOtherWork(){
            System.out.println("Doing some other work in interface impl...");
        }
    }
```

Assume this static method that receives a static intreface as an argument:

```
    public static void carryOutWork(SimpleFuncInterface sfi){
        sfi.doWork();
    }
```

It can be called in one of the following two ways: 

```
    // passing an instance of an annonymous class implementing the SimpleFuncInterface
    carryOutWork(new SimpleFuncInterface() {
      @Override
      public void doWork() {
        System.out.println("Do work in SimpleFun impl...");
      }
    });
 
    // ... OR passing a lambda 
    carryOutWork(() -> System.out.println("Do work in lambda exp impl..."));
```

Note that [`java.util.function`] (http://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html) package likely has the functional interface you need.  For the questions below you may find the `Function` functional intefrace useful. 


#### TODO(1)

* (easy) Define (i.e., write the code for) a method that takes two arguments, first a function foo of type T -> R and, second, and argument x of type T, and returns f(x).  

* (harder) Define a method that takes as an argument an integer `added` and returns a function that receives an integer X and returns X + adder

* (even harder) Define a method that takes as an argument a function foo: U -> V and a function bar: V -> W and returns a function compose, such that compose(x) = g(f(x))

For each of the points above you also need to demonstrate how calling works and how you'll use results. 



## Task 2: Grammars and Parsing using ANTLR

The goal of this second task is to go over the concepts of grammars and parsing, as well as to give you experience using the parser generator ANTLR needed for MP5.

Our running example will parse and evaluate a simple polynomial. Lets start with expressions which only contain polynomials in 'X' (uppercase or lowercase). For example:

```
    2*X + 1
    X^3 + X^2 + X + 100
    x^5 - 7 * x^2 + 1
```

How do we parse these expressions? Remember that we divide the procedure into three steps: 
+ characters &rarr; **Lexer** &rarr; tokens
+ tokens &rarr; **Parser** &rarr; concrete syntax tree
+ concrete syntax tree &rarr; **AST creation** &rarr; abstract syntax tree

What will be the inputs and outputs of each of these blocks while parsing input string `" X^3 + X^2 + X + 100"`? 


#### Step 1. Design the Grammar for our Polynomial Expressions

Definitions in the grammar take the following form:

    <name> : <definition>;

Take a look at the `Poly.g4` file containing a grammar we will provide to ANTLR in order to generate a parser.

**Key points to notice**: 

 - How to tell parser generator to skip white spaces  
   `WHITESPACE : [ \t\r\n]+ -> skip ;`
 - Difference between lexical rules and parser rules. Lexical rules define the tokens while parser rules define the semantics
 - CAPITALIZED vs. lowercase names. CAPITALIZED names are used for lexical rules, lowercase names are used for parser rules, which refer to lexical terminals and other parser nonterminals
 - Root rule ends with EOF because we process the entire input sequence at once
 - The `.g4` file contains a bunch of boilerplate definitions (`@header` and `@members`) which you should not need to modify!

#### Step 2. Generate Lexer and Parser using ANTLR 

Compile the `Poly.g4` file to automatically generate a lexer and parser by following these instructions: 

 - Open up a terminal or a command prompt, and navigate to directory containing `Poly.g4`
 - Then run `java`, providing the correct path to ANTLR: `java -jar path/to/antlr.jar Poly.g4`

Key points to remember: 

 - Run ANTLR from the directory containing the `.g4` file
 - Automatically generated files will have names starting with the name of the `.g4` file. 
 - **You should not edit the automatically-generated files.**
   ANTLR will overwrite them every time you run it.
   **Any changes you have made to these files will be lost.**

What's the output from ANTLR for our Poly grammar?

- `PolyLexer.java`
- `PolyParser.java`
- `PolyListener.java` and `PolyBaseListener.java`
- `Poly.tokens` and `PolyLexer.tokens`

#### Step 3. Use the ANTLR-generated Lexer and Parser to Construct a Concrete Syntax Tree

Let's look at `PolyFactory.java`, which already contains an implementation for `parse` method that takes a `String` as input and returns a `Poly`.  Here are the processing steps (also highlighted as comments in the code): 

 - Create a character stream from the input
 - Push the stream through the lexer
 - Create a token stream
 - Push that token stream through the parser
 - Ask the parser for the root grammar production (the one containing EOF)  
   Note: you could also ask the parser to parse smaller fragments by calling the appropriate method, e.g. in this case `term()` or `sumterms()`

At this point we have a concrete parse tree, and we need to create an abstract syntax tree (AST).

#### Step 4: Debugging the Parse Tree

Before that one may need to understand/debug the parse tree. That is, make sure it has the structure we expect.

1. Print it to the console 
2. Inspect it with the GUI
3. Walk down the tree with a listener 

ANTLR provides a way to do depth-first traversal of the parse tree in order to, for example, print out debugging info, or generate an AST.

 - `PolyListener` is the interface we must implement in order to do the traversal
 - `PolyBaseListener` is a convenience class which provides empty implementations of all the *callback methods*
 - We can create a subclass of `PolyBaseListener` and override only the methods we care about with our own implementation

Look at `PolyListener_PrintEverything`:

 - Running the code, we see what we would expect from a depth-first traversal
 - Using the context object passed to the callback, we can learn about the contents of the tree

*Callback methods* are called callbacks because we write some code, but do not call our method directly &mdash; instead, we pass our listener object to a `ParseTreeWalker`, and it "calls us back" when something we care about happens, e.g. it reaches a particular kind of node in the parse tree.

#### Step 5. Build an AST 
 
Since our grammar is fairly simple, rather than building an AST the code (implemented in `PolyListener_PolyCreator` class) builds directly a representation for a polynomial.   

 - The callback `exitTerm`, called after parsing a Concrete Syntax Tree term, identifies a polynomial term and adds it internal list.
 - The callback `exitSumterms` deals with the plus/minus operations between terms
 - The callback `exitPoly` does nothing
 - The `getPoly` method processes the internal datastructure (a degenerated AST) that accumulates state during parsing, builds a `Poly` and returns it. The `Poly` datatype returned has `toString` and `eval` methods.



### TODO: 

* (Simple): Reduce the polynomial to a canonical form.  The parser should return `3 * X + 1` for an input of `X + 2X + 1`
* (Simple): Change the grammar and everything else needed to parse polynomials that have floading point coefficients (for now only integers are accepted) 
* (Simple): Rather than returning a `Poly` return a function that represents the Poly.  For example, for a String input `"X^3 + 1"` your `getPoly` method will return a function `(double x) -> x*x*x + 1` 
* (Harder): Extend all this to parse string that describes a two varaible polynomial.  The variables can have any names.  
* (Even Harder):  There is a bug in the grammar:  It does not accept polynomial that starts with a first term with a negative coefficient.  That is, it accepts `1 - X` but it does not accept `-X + 1`.  Fix it!
