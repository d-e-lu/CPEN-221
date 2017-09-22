CPEN 221: Lab 9
Introduction to Concurrency
====


## Objectives

The goal of this lab exercise is to explore message passing between Java threads. This activity may take longer than two hours so be prepared for that. Discussions with fellow students and your TA will help.

This activity *also* introduces `grep`, which is an extremely useful tool for pattern matching, and the _producer-consumer_ design pattern.

## Reading
+ Java tutorial on [thread objects](https://docs.oracle.com/javase/tutorial/essential/concurrency/threads.html)
+  Example on [data races](https://github.com/CPEN-221/example13)

The example on data races shows one how to create new `Thread`s. You could begin there and move on to the lab activity. See the [simple case of `Factorial` in the example source code](https://github.com/CPEN-221/example13).

## Concurrency

Concurrency is implemented within a Java program using [`Thread`](http://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html)s. When you start a Java program, `main` runs in an initial thread provided by the JVM.

To start a new thread of execution:

 - Implement the [`Runnable`](http://docs.oracle.com/javase/7/docs/api/java/lang/Runnable.html) interface, which requires you to implement a `run()` method
   - Depending on your implementation choices, this might be a class with state and methods
   - Or it might be an anonymous class with only a few method calls (and often a loop and `try`/`catch`)
 - Create a new Thread instance with your Runnable
 - [`start()`](http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#start%28%29) the thread
 - Never call `run()` &mdash; calling `run()` will *not* start a new thread of execution

Notice that `Thread` has both instance methods (which operate on that thread) and static methods (which operate on the current thread).

Once you have a started `Thread` instance:

 - Use [`interrupt()`](http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#interrupt()) to set the "interrupted" flag and possibly throw an `InterruptedException` within the thread
   - Combine with `try`/`catch` and checking [`interrupted()`](http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#interrupted()) to terminate the thread gracefully
 - Use [`join()`](http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()) to wait for a thread to terminate (blocking whatever thread called `join` until it does)
 - *Never* ever call `stop()`

## Implementing "grep"

[`grep`](http://en.wikipedia.org/wiki/Grep) is a command-line tool that searches for matches to a string in a set of files.
For example, in the project directory for this lab activity, we can search for lines in the starting code that contain the word `join`:

```
$ grep -r join src
src/grep/Grep.java:            producer.join();
src/grep/Grep.java:            consumer.join();
src/grep/Grep.java:            producer.join();
src/grep/Grep.java:            consumer.join();
```

We're going to implement a version of *grep* that searches the `HTML` source code of web pages.  We want our version to use multiple threads running concurrently &mdash; downloading pages and searching lines for matches.


## Implementing "grep" badly &mdash; race conditions

First let's recall why this is hard.  Suppose we try to use an `ArrayList` to store all the lines we download, and a counter to count how many matches we found:

```java
        List<String> lines = new ArrayList<String>();
        int numMatches = 0;
```


And then we create a bunch of threads that work by grabbing random lines from this list and searching them:

```java
        public void run() {
            while (lines.size() > 0) {
                int i = (int) (Math.random() * lines.size());
                String line = lines.get(i);
                lines.remove(i);
                if (line.contains(substring)) {
                    ++numMatches;
                }
            }
        }
```

- What can go wrong?
- It turns out that `remove()` actually returns the removed element, so you don't need the `get()` call at all.  Will this help?
- It turns out that there's a threadsafe list wrapper, `Collections.synchronizedList()`, that makes it safer to access a list from multiple threads.  We'll use it in the code we write next.  But will it help in this case?


## Concurrent Queues

Rather than have unsafe data structures like `ArrayList` shared between threads, you are **strongly** encouraged to use thread-safe queues to pass data between threads in the *producer-consumer* pattern.

You may also find [`ConcurrentMap`](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentMap.html)s useful. In general, while merely using the high-level concurrency tools in Java does *not* make your code thread-safe, it can make designing and reasoning about thread-safe implementations much easier.

What is a [`Queue`](http://docs.oracle.com/javase/8/docs/api/java/util/Queue.html)?

- FIFO (first-in-first-out)
- `add` vs. `offer`
- `remove` vs. `poll`
- `element` vs. `peek`

And what is a [`BlockingQueue`][BlockingQueue]?

- `put(E)` and `offer(E, long, TimeUnit)` vs. `add(E)`/`offer(E)`
- `take()` and `poll(long, TimeUnit)` vs. `remove()`/`poll()`

The [Java API documentation for `BlockingQueue`][BlockingQueue] mentions several useful details:

- thread safety
- poison pills
- a code template for producer-consumer

  [BlockingQueue]: http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/BlockingQueue.html

Note that Java also provides several other queue variants, including priority queues, `Deque`s and `BlockingDeque`s.

## Implementing "grep" Using the Producer-Consumer Pattern

Now we're going to implement a version of grep that uses the [producer-consumer pattern](http://en.wikipedia.org/wiki/Producer–consumer_problem).

We'll implement a **`Producer`** that (1) downloads the text of a page and (2) adds each line of text to queue.

And we'll implement a **`Consumer`** that (1) retrieves lines of text from the queue, (2) checks each line to see if it contains the string, and (3) adds matching lines to a result list.

Our grep implementation will use *multiple producers* &mdash; one per URL &mdash; and *multiple consumers*.

### Step 1

- What does `Producer` need to do?
- What does `Consumer` need to do?
- What parameters do you need to pass in to the constructor?

**Stuck trying to read the contents of a URL?**

```java
BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
String line;
while ((line = in.readLine()) != null) { /* do something with line */ }
```

### Step 2

+ How can we tell the consumers to stop waiting for new lines after the producers are done?

**Stuck trying to tell the consumers to stop?**

Try creating *poison pills*: special elements on the queue that signal a consumer to end its work:

```java
class Consumer implements Runnable {
    // ...
    public void run() {
        // ...
        while (true) {
            Line line = /* take a line off the queue */
            if (/* line is a poison pill */) { break; }
            /* continue with normal processing */
        }
    }
}
```

## Discussion Questions

- When do producer threads stop?
- When do consumer threads stop?
- What objects are shared by multiple threads?
  Why is the sharing safe?
  Why are we safe from deadlocks?
- How could we stop consumer threads without using poison pills?

### Using Message-Passing to Design Concurrent Programs

+ The producer/consumer pattern common design pattern in message passing: producers generate messages, consumers receive the messages, and they are connected by a queuing communication channel
+ [`BlockingQueue`](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/BlockingQueue.html) is a Java class for implementing message-passing
+ Don't share any mutable data between message-passing threads; instead, each mutable object should be owned by one thread
  + This approach has a name: **confinement**.

## Submitting Your Work

+ Submit your work to Github.
+ Follow the usual guidelines such as using a `partners.txt` file.
