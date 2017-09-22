Lab 7: Equality
====


## Principles of Software Construction

<table class="table table-striped">
<tr><th width="33%">Safe from bugs</th><th>Easy to understand</th><th>Ready for change</th></tr>
<tr><td>
  Correct today and correct in the unknown future.
</td><td>
  Communicating clearly with future programmers, including future you.
</td><td>
  Designed to accommodate change without rewriting.
</td></tr>
</table>

## Equality

The goal of this lab activity is to reinforce the notion of equality of values in a datatype.

### Views of equality

**Reference equality**: are two objects the same object in memory?

**Object equality**: do two objects represent the same value?

How do we know if two objects are *equal*, that they represent the same value?

**Using an abstraction function** *AF: R &rarr; A*, two objects *a* and *b* are equal iff *AF(a) = AF(b)*.
This relies on our notion of equality between abstract objects.

**Using observational equality**, two objects are equal if there are no observations we can make on them that yield different results.

**Using an equivalence relation**, we must define a relation *E* that is:

reflexive:
: *E(t,t)* for all *t*

symmetric:
: *E(t,u) = E(u, t)*

transitive:
: if *E(t, u)* and *E(u, v)*, then *E(t, v)*

Since abstract data types are defined by their operations, we see that observational equality and abstraction-function equality should lead us to the same conclusions.
And if our abstract notion of equality is not an equivalence relation, we must repair it before it can be implemented.

#### Example: durations

Let's take a look at this simple `Duration` class:

```java
public class Duration {

  private final int mins;
  private final int secs;
  // rep invariant: mins >= 0, secs >= 0
  // abstraction function: 
  //    represents a span of time of mins minutes and secs seconds

  /** Make a duration lasting for m minutes and s seconds */
  public Duration(int m, int s) {
    mins = m;
    secs = s;
  }

  /** @return length of this duration in seconds */
  public long getLength() {
    return mins*60 + secs;
  }
}
```

Which of the following values should be considered equal?

```java
Duration d1 = new Duration(1, 2);
Duration d2 = new Duration(1, 3);
Duration d3 = new Duration(0, 62);
Duration d4 = new Duration(1, 2);
```

Discuss. Think in terms of both the abstraction-function definition of equality, and the observational equality definition.


#### Java's `==` vs. `equals()`

Like many languages, Java has two different operations for testing equality, with different semantics.

+ The `==` operator compares references. More precisely, it tests *referential* equality. Two references are `==` if they point to the same storage in memory. In terms of the snapshot diagrams we've been drawing, two references are `==` if their arrows point to the same object bubble.
+ The `equals()` operator compares object contents -- in other words, object equality, in the sense that we've been talking about.


### Implementing equality

Java defines a method on all [`Object`](http://docs.oracle.com/javase/7/docs/api/index.html?java/lang/Object.html)s: [`public boolean equals(Object obj)`](http://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#equals(java.lang.Object)).

To implement equality, your type must *override* this existing method.
If you're not sure what this means, try the [Java tutorial](http://docs.oracle.com/javase/tutorial/).

The Java equality contract also has a requirement about hashing objects.
The method [`public int hashCode()`](http://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#hashCode()) returns an integer hash value that is used to implement efficient operations in hash tables.

Objects that are equal must return the same hash code.

 + First, note that the reverse is *not* true: having the same hash code does *not* imply that two objects are equal.
 
 + Second, note the important consequence: **when you override `equals`, you must override `hashCode()`.**

The equality contract in Java requires that:

+ `equals` must define an equivalence relation
  + and a non-null object must never equal null

+ `equals` must be consistent over time

+ `hashCode` must produce the same result for objects that are equal

#### Example: durations, part 2

Let's try to implement the `equals` method for our `Duration` class. Here's a first try:


```java
public class Duration {
  
  // ...
  
  // problematic definition of equals()
  public boolean equals(Duration that) {
    return this.getLength() == that.getLength();
  }
}
```

There's a subtle problem here. Why doesn't this work? What happens when we try this code:

```java
Duration d1 = new Duration(1, 2);
Duration d2 = new Duration(1, 2);
Object o2 = d2;
d1.equals(d2); // --> true
d1.equals(o2); // --> false
```

What happened here was that we overloaded the `equals()` method, because the method signature was not identical to `Object`'s -- so we actually have to `equals()` methods in `Duration`: an implicit `equals(Object)` inherited from `Object`, and the new `equals(Duration)`.

Here's the right way to implement this method:

```java
// @Override asserts that superclass has this method
// compile-time error if it does not
@Override 
public boolean equals(Object thatObject) {
  if ( ! (thatObject instanceOf Duration)) return false;
  Duration thatDuration = (Duration) thatObject;
  return this.getLength() == thatDuration.getLength();
}
```

And now this fixes our original problem.

#### Example: equality in `CharSet`

![CharSet](https://www.dropbox.com/s/vwmofuqhm8kjx3m/CharSet.png?dl=1)

Skeleton source code [here](https://github.com/CPEN-221/lab7/tree/master/CharSet): https://github.com/CPEN-221/lab7/tree/master/CharSet

Consider a simple data type to represent a set of characters. The diagram above illustrates this abstract data type with one particular choice of representation. (You should however use the representation indicated in the skeleton code. The diagram illustrates one possible representation.)

With your partner, implement the `equals()` and `hashCode()` functions for the **`CharSet`** datatype.

Discuss.

### Equality for a recursive ADT

Skeleton source code [here](https://github.com/CPEN-221/lab7/tree/master/Tournament): https://github.com/CPEN-221/lab7/tree/master/Tournament

As the leader of your local [chess boxing](http://en.wikipedia.org/wiki/Chess_boxing) club, you want to write a program for running tournaments.

First, we'll need `Player`s in our tournaments.
They have only three operations:

Player :
: `String` &times; int &rarr; `Player`

skill :
: `Player` &rarr; int

toString :
: `Player` &rarr; `String`

The `Player` creator takes a `String` name and integer [skill rating](http://en.wikipedia.org/wiki/Elo_rating_system); we'll implement it with a constructor.
`skill` and `toString` are observers that return the player's skill rating and name, respectively.

#### Java's `toString`

`toString` is a method on all Java objects. Use the `@Override` annotation on `toString` to make sure you are overriding `Object`'s implementation.

This way, you can write:

```java
Player player1 = new Player("Da5id", 0);
System.out.println("Player 1: " + player1);
```

... and see `Player 1: Da5id` as output.

If you anticipate multiple players with the same name, you might include the skill rating in `toString`'s return as well.

With your partner, implement `Player`.

It should be a single class with two fields, one constructor, and two methods.

Now we can think about the `Tournament` data type. Its most important operation is:

winner :
: `Tournament` &rarr; `Player`

Given a tournament, `winner` computes the **winner** of the tournament, defined as a player with skill rating greater than or equal to the skill of every other player in the tournament.

![((Alice vs. Bob) vs. Eve) vs. (Yolanda vs. Zach)](https://www.dropbox.com/s/el2bla6sggd0f3l/bracket.png?dl=1)

Let's start with a single-elimination tournament like the one on the right.

Since the tournament has an odd number of players, Eve drew a *bye*.

We can see how the `winner` operation is defined recursively: the winner of the tournament is the winner of (the winner of (the winner of Alice vs. Bob) vs. Eve) vs. (the winner of Yolanda vs. Zach).

With your partner, design and implement the `Tournament` data type.

**Use a recursive implementation.**
How will you represent the leaves of the tournament tree?

Write down the recursive data type definition.

Write a recursive definition for `winner`, test it, and implement it.

Implementing an operation recursively by:

0. **specifying it in the ADT interface**, and
0. **implementing it in each of the concrete ADT variants**

... is called the *interpreter pattern*.
It's not a terribly clear name, but there are other ways to implement operations on recursive data types, so it's a useful to know the name of this pattern.

Discuss your designs and implementations.

![((Alice 98 vs. Bob 76) vs. Eve 87) vs. (Yolanda 43 vs. Zach 54)](https://www.dropbox.com/s/skpov426eciiv9j/bracket-skilled.png?dl=1)

Your implementation should have no trouble deciding the winner of our tournament above, given the skill of each player.

#### Equality

With your partner, implement the `equals` and `hashCode` functions for both the [**`Player`** and **`Tournament`** classes](./tournament.html).

Discuss. 

Was your implementation recursive? What if we tried to implement `equals()` (and `hashCode()`) for `Tournament` by just looking at the winner?

Let's think about the **structural equality** of Tournaments:

+ A wins vs. (B wins vs. C) **NOT** equal to  (A wins vs. B) vs. C
+ Does our notion of equality satisfy this intuition?

Examine the three views of equality. Does your definition satisfy each of these?

1. Using the **abstraction function**.
2. Using the **equivalence relation** definition.
3. Using **observational equality**.

What if the only operation we have is the `winner` operation? What if we have others?

Also, there's a handy Eclipse tool worth looking at: *Source &rarr; Generate hashCode() and equals()...*!

## How we define the notion of equality in our datatypes

+ Many views of equality: reference equality vs. object equality.
+ Object equality should be an equivalence relation (reflexive, symmetric, transitive).
+ The abstraction function is the basis for equality in immutable datatypes.
+ Equality in two objects implies that they have the same hash code.
  + When you override `equals()`, you must override `hashCode()`.

## Submission

You need to submit the following:

+ your implementation of equality for `CharSet`;
+ a textfile `tournament.txt` with the recursive datatype definition for `Tournament`;
+ a `partners.txt` file to indicate your lab partner.
