CPEN 221 / Lab 10
Multithreading and Synchronization
=====

### Thread Safety and Synchronization

The goal of this lab activity is to review the use of synchronization to create thread-safe datatypes. We will practice locking disciplines that avoid deadlock with multiple locks, and practice making thread safety arguments.

### Bad thread safety arguments

Consider the case of an `EditBuffer`, which represents a mutable sequence of characters:

```java
public class SimpleBuffer implements EditBuffer {
    private String text;
    // Rep invariant: text != null
    // Abstraction function: represents the sequence text[0],...,text[text.length()-1]
    // Thread safety argument:
    //   text is an immutable (and hence threadsafe) String,
    //       so this object is also threadsafe
```

You can see more source code related to this example as [Example 14](https://github.com/CPEN-221/example14) on GitHub.

**Discussion**

- Why is this argument invalid?
- Write a reasonable method to include in `SimpleBuffer` that would exhibit concurrency bugs.


And here, we'd like to implement a threadsafe mutable graph datatype:

```java
public class Graph {
    private final Set<Node> nodes = Collections.synchronizedSet(new HashSet<Node>());
    private final Map<Node,Set<Node>> edges =
                Collections.synchronizedMap(new HashMap<Node,Set<Node>>());
    // Rep invariant:
    //   nodes, edges != null
    //   for all x, y such that y is a member of edges.get(x),
    //       x and y are both members of nodes
    // Abstraction function:
    //   represents a directed graph whose nodes are the set of nodes
    //       and whose edges are the set (x,y) such that y is a member of edges.get(x)
    // Thread safety argument:
    //   - nodes and edges are final, so those variables are immutable and threadsafe
    //   - nodes and edges point to threadsafe set and map data types
```

**Discussion**
- Why is this argument invalid?
- Write a reasonable method to include in `Graph` that would exhibit concurrency bugs.


### Locks

A **lock** is an abstraction that allows at most one thread to *own* it at a time.
Locks have two operations:

- *acquire*: the acquiring thread becomes the owner of the lock
- *release*: relinquish ownership to allow another thread to own the lock

**Discussion**

+ If a thread *B* tries to acquire a lock currently held by thread *A*, what happens to thread *B*? What happens to thread *A*?

In Java, *every* `Object` &mdash; i.e., every instance of every type &mdash; has an associated lock.
Locks are acquired and released using the keyword `synchronized`:

- a **synchronized block** acquires and releases the specified lock when entering and exiting the block
- a **synchronized method** acquires and releases the lock on `this` when calling and returning from the method

**Discussion**
- What happens when a thread tries to acquire a lock it already owns?
- What happens to the lock when we exit a synchronized block or method by throwing an exception?

#### Of painters and painting

```java
public class Painter {

    private Brush brush;
    private Palette palette;

    public Painter(Brush brush) {
        this.brush = brush;
        this.palette = new Palette();
    }

    public void pick() {
        synchronized (palette) {
            ** picking **
        }
    }

    public void paint() {
        synchronized (brush) {
            ** painting **
        }
    }

    public void mix() {
        synchronized (this) {
            ** mixing **
        }
    }

    public synchronized void frame() {
        ** framing **
    }
```


Suppose we have one `Painter`, and several threads:

**Discussion**
+ If two threads are calling different methods on the same `Painter`, can that painter be:
	+ picking and painting simultaneously?
	+ painting and mixing simultaneously?
	+ mixing and framing simultaneously?

+ Suppose we create two `Painter`s who share a single `Brush`. If we work with each different `Painter` on a different thread, can they be:
	+ picking simultaneously?
	+ painting simultaneously?
	+ mixing simultaneously?
	+ framing simultaneously?

+ What if the two `Painter`s share two different `Brush` objects that are `.equals` to one another?

+ If we work with `Painter`s on different threads using distinct but equal brushes, can they be painting simultaneously?

### Deadlock

Suppose we have a `Wizard` class with methods to add and remove friends.

```java
public class Wizard {

    private final String name;
    private final Set<Wizard> friends;
    // Rep invariant:
    //   name, friends != null
    //   friend links are bidirectional: for all f in friends, f.friends contains this
    // Concurrency argument:
    //   threadsafe by monitor pattern:
    //       all access to rep is guarded by the lock on this object

    // ...

    public synchronized void friend(Wizard that) {
        if (friends.add(that)) { that.friend(this); }
    }

    public synchronized void defriend(Wizard that) {
        if (friends.remove(that)) { that.defriend(this); }
    }
```

Note how `friend` and `defriend` preserve the rep invariant. However, this code quickly leads to deadlocks:

```java
    // thread A                 // thread B
    harry.friend(snape);        snape.defriend(harry);
```


#### Lock ordering

One way to prevent deadlock is to put an **ordering on the locks** that need to be acquired simultaneously, and ensuring that all code acquires the locks in that order.

**Discussion**
- Why is acquiring all locks in a consistent order a sufficient condition to avoid deadlock?
- Is it a necessary condition?
- How strict do we need to be with this policy in order to retain the no-deadlock guarantee?

In the case of `Wizard`s, we can order them by name.
That works for a world where people are uniquely named, but otherwise we might need to generate an ordering &mdash; for example, by generating a unique ID number for each object.

**Suppose `Painter` above has a collection of `Brush`es instead of a single brush**, and the `paint` operation must lock a *subset* of the brushes depending on what the painter happens to be painting.

**Discussion**
+ Sketch an implementation of multi-brush `Painter` that uses lock ordering to avoid concurrent painting deadlocks.

#### Coarse-grained locking

Another way to prevent deadlock is to guard all of data that were previously locked individually behind a single **coarse-grained lock**.

**Discussion**
+ What penalty do we immediately pay for coarse-grained locking?

In the case of `Wizard`s, we can place them in a `Castle`, and use the lock on the single shared `Castle` to protect the rep.


+ What must we add to the specification or rep invariant of `Wizard` if we use this approach?
+ What happens if we attempt cross-castle friending?


**How can we avoid having to use these locking disciplines?**

- Immutability
- Use of thread-safe datatypes *with non-blocking operations*
- Message-passing, e.g. with `BlockingQueue`s, and confinement
- *Other ideas?*


### Argue for safety

If you are writing code that shares data with multiple threads, document why the sharing is safe for concurrency.

 - Are data confined? Immutable? Threadsafe?
 - Even if we use immutable or threadsafe types, do we require synchronization of some activities between multiple threads?

**If you are implementing a thread-safe datatype, document why the datatype is safe for use by multiple concurrent clients.**

 - Do we meet the strong definition of immutability? Use immutable references? Immutable types? Other threadsafe types? Do we use synchronization?
 - **We must argue for why the rep invariant is preserved.** This, of course, requires a documented rep invariant, and it requires no rep exposure.

#### Argue for liveness

If you are writing code that manipulates multiple locks, document why that code will not encounter deadlocks.

 - In fact, this is not limited to locks: as we saw with message-passing and network buffers, any time we have multiple blocking operations (or operations that may block under some condition) in dependent modules, we need to argue for the absence of deadlocks.


Let's look at another version of our painterly example where `Painter`s have multiple `Brush`es:

```java
public class Painter {

    private Set<Brush> brushes;
    private Palette palette;

    public Painter(Set<Brush> brushes) {
        this.brushes = brushes;
        this.palette = new Palette();
    }

    public void pick() {
        ** picking **
    }

    public void paint() {
        ** painting **
    }

    public void mix() {
        ** mixing **
    }
```

**Suppose we want to make `Painter`s threadsafe.**
Make the necessary changes to the code and write an argument using the following strategies:

- `Painter` is immutable  
  *remember that we must meet a stronger standard of immutability*

- `Painter` implements the monitor pattern  
  *see the notes for the definition of monitor*

- All access to the rep is protected by the lock on `brushes`  
  *similar argument to the monitor approach, but different code*

- `Brush` and `Palette` are threadsafe, no locks are used  
  *what other threadsafe types will be useful?*

```java
public class Main {

    public static void main(String[] args) {

        int painters = 10;

        for (int ii = 0; ii < painters; ii++) {
            new Thread(new Runnable() {
                public void run() {
                    // get a Painter
                    // do some paint()ing
                }
            }).start();
        }
    }
```

**Suppose we want to make `Main` threadsafe.**
Make the necessary changes to the code and write an argument given the following:


- All threads share one threadsafe `Painter`  
  *we'll start easy*

- All threads share **one non-threadsafe** `Painter`  
  *we'll need to implement synchronization*

- Each thread uses a **different non-threadsafe** `Painter`  
  *if it's not threadsafe, we'll need confinement*

**If we use the `Main` above with the multiple-shared-brushes `Painter` class**, and `Painter` documents that painting requires locks on the brushes, then we might also argue for why we will not encounter deadlock, e.g.:

- different `Painter`s use different `Brush`es
- we comply with `Painter`'s locking discipline, for example by giving brushes to all painters in the same order


### Submission

Submit a **text file** with the answers to the discussion questions in this activity.

You do not need to submit any code but you can include code in the text file to help in the discussions and to further your own understanding. (Add a `partners.txt` file as always.)

> Ask the teaching assistant for clarifications on how threading works. If the lab session does not provide sufficient time then ask on Piazza.

### Document Thread Safety Arguments

**Whenever you introduce concurrency, document a clear thread safety argument in your code.**

As the saying goes:

> Some programmers, when confronted with a problem, think, "I know, I'll use threads." now And they problems. have two

Also:

> Some programmers, when confronted with a concurrency problem, think, "I know, I'll use locks." And now they hav

**You cannot solve concurrency problems by throwing more code at them.** You certainly can't solve them by sprinkling `synchronized` all over the place... best case, you eliminate all the parallelism. More likely: race conditions &amp; deadlocks.
