Machine Problem 1: My Guitar Hero
===
... or get familiar with `Queue`s and produce some noise.

> This machine problem is due September 23 by 11:59 p.m. and is worth 3% of your course grade. _This is an individual assignment._

This programming assignment will give you practice with **queues, interfaces, objects, and arrays of objects.**  You are going to implement two classes that allow us to simulate a guitar.  

We will be using two utility classes known as `StdAudio` and `StdDraw`.  You don’t have to understand the details of these utility classes, but [documentation](http://introcs.cs.princeton.edu/java/stdlib/) is available if you are interested.

### Short Introduction: `Queue`

An important datatype that you will use in this assignment is a queue. A queue is collection that allows one to insert and remove items. Items are removed in a "first-in first-out" order.

The [standard Java Queue](https://docs.oracle.com/javase/tutorial/collections/interfaces/queue.html) is an interface. This means that one can define an object reference to be of type `Queue` but one has to create an object of this type using a class or type that *implements* the `Queue` interface.

For example, we could define `integerQueue` like this

```java
Queue<Integer> integerQueue;
```

... but we cannot create a queue using

```java
integerQueue = new Queue<Integer>( );
```

`LinkedList` is one of many implementations of the `Queue` interface. So we can do the following to create a queue:

```java
Queue<Integer> integerQueue = new LinkedList<Integer>( );
```

After this step, with `integerQueue`, you should only use methods that are part of the `Queue` interface. `add( )`, `remove( )` and `peek( )` are usually sufficient.

*We will discuss in greater detail why interfaces exist soon enough, but you will need this basic understanding of Java usage to complete this assignment.*


### The Essence of this Machine Problem: Simulating a Guitar

#### Plucking the string

When a guitar string is plucked, the string vibrates and creates sound. The length of the string determines its fundamental frequency of vibration. We model a guitar string by sampling its displacement (a real number between -1/2 and +1/2) at N equally spaced points (in time), where N equals the sampling rate (44,100) divided by the fundamental frequency (rounded to the nearest integer).  We store these displacement values in a structure that we will refer to as a ring buffer.

The excitation of the string can contain energy at any frequency. We simulate the excitation by filling the ring buffer with white noise: set each of the N sample displacements to a random real number between -1/2 and +1/2.

![Vibrations](https://www.dropbox.com/s/zkw2bht3xgvrnoh/image002.jpg?dl=1)

#### The Resulting Vibrations

After the string is plucked, the string vibrates. The pluck causes a displacement which spreads wave-like over time. The Karplus-Strong algorithm simulates this vibration by maintaining a ring buffer of the N samples: the algorithm repeatedly deletes the first sample from the ring buffer and adds to the end of the ring buffer the average of the first two samples, scaled by an energy decay factor of 0.996.

![Ring Buffer](https://www.dropbox.com/s/x8pv6u1jhuxtcld/image006.jpg?dl=1)

#### Why It Works

The two primary components that make the Karplus-Strong algorithm work are the ring buffer feedback mechanism and the averaging operation.
* The ring buffer feedback mechanism. The ring buffer models the medium (a string tied down at both ends) in which the energy travels back and forth. The length of the ring buffer determines the fundamental frequency of the resulting sound. Sonically, the feedback mechanism reinforces only the fundamental frequency and its harmonics (frequencies at integer multiples of the fundamental). The energy decay factor (.996 in this case) models the slight dissipation in energy as the wave makes a roundtrip through the string.
* The averaging operation. The averaging operation serves as a gentle low pass filter (which removes higher frequencies while allowing lower frequencies to pass, hence the name). Because it is in the path of the feedback, this has the effect of gradually attenuating the higher harmonics while keeping the lower ones, which corresponds closely with how actually plucked strings sound.

#### Part 1: The `GuitarString` Class

In the first part of the assignment, you will implement a class called `GuitarString` that models a vibrating guitar string of a given frequency.  The `GuitarString` object will need to keep track of a ring buffer.  You are to implement the ring buffer as a queue using the `Queue<E>` interface and the `LinkedList<E>` implementation.  You are not allowed to use other data structures to solve this problem.  You must solve it with a queue.

You can choose to implement a separate `RingBuffer` class using Java’s native `LinkedList<E>` implementation of the `Queue<E>` interface and use an object of the `RingBuffer` class in your implementation of `GuitarString`.

The `GuitarString` class should have the following public methods.

* `GuitarString( double frequency )`: Constructs a guitar string of the given frequency.  It creates a ring buffer of the desired capacity N (sampling rate divided by frequency, rounded to the nearest integer), and initializes it to represent a guitar string at rest by enqueueing N zeros.  The sampling rate is specified by the constant `StdAudio.SAMPLE_RATE`.  If the frequency is less than or equal to 0 or if the resulting size of the ring buffer would be less than 2, your method should throw an `IllegalArgumentException`.
* `GuitarString( double[] init )`: Constructs a guitar string and initializes the contents of the ring buffer to the values in the array.  If the array has fewer than two elements, your constructor should throw an `IllegalArgumentException`.  This constructor is used only for testing purposes.
* `void pluck( )`: This method should replace the N elements in the ring buffer with N random values between -0.5 inclusive and +0.5 exclusive (i.e. -0.5 <= value < 0.5).
* `void tic( )`: This method should apply the Karplus-Strong update once.  It should delete the sample at the front of the ring buffer and add to the end of the ring buffer the average of the first two samples, multiplied by the energy decay factor (0.996).  Your class should include a public constant for the energy decay factor.
* `double sample( )`: This method should return the current sample (the value at the front of the ring buffer).

You will be provided with a testing program that you can use to verify that your class has the basic functionality that is required.  The testing program will not check to make sure that you are using a queue and that you are checking for appropriate exceptions to throw and that you are using the queue efficiently.

It is difficult in commenting the `GuitarString` class to know what constitutes an implementation detail and what is okay to discuss in client comments.  Assume that a client of the `GuitarString` class is familiar with the concept of a ring buffer. The fact that we are implementing it as a queue is an implementation detail.  So do not mention how you implement the ring buffer.  But you can discuss the ring buffer itself and the changes that your methods make to the state of the ring buffer (e.g., moving values from the front to the back of the ring buffer).

Normally we would encourage you to write a single constructor and to use the “`this(…)`” notation to have one constructor call another.  That won’t be possible for the `GuitarString` class because the two constructors are completely different.

Because you are using Java’s queue structure to implement the `GuitarString`, you will need to include this import declaration at the beginning of the class: `import java.util.*;`

#### Part 2: The `Guitar221` Class

In the second part of the assignment, you are going to build on the `GuitarString` class to write a class that keeps track of a musical instrument with multiple strings.  There could be many possible guitar objects with different kinds of strings.  As a result, we introduce an interface known as Guitar that each guitar object implements.

You are being provided with a sample class called `GuitarLite.java` that implements the Guitar interface.  Once you have verified that your `GuitarString` class passes the testing program, you can play the `GuitarLite` instrument.  It has only two strings: _a_ and _c_.  Keep in mind that `GuitarLite` does not have a main method.  There is a separate class called `GuitarHero` that has `main` (the initial version constructs a `GuitarLite` object).

In this second part of the assignment, your task is to make a variation of `GuitarLite` known as `Guitar221`.  It will model a guitar with 37 different strings.  Because it has so many strings, we will want to keep track of them in a data structure.  Your `Guitar221` objects should each keep track of an array of 37 `GuitarString` objects.

The `Guitar221` class has a total of 37 notes on the chromatic scale from 110Hz to 880Hz.  We will use the following string to map keys typed by the user to positions in your array of strings.  The i<sup>th</sup> character of this string should correspond to the i<sup>th</sup> character of your array:

`”q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' “`

This "keyboard" arrangement imitates a piano keyboard, making playing songs a little easier for people used to a piano keyboard. The "white keys" are on the `qwerty` and `zxcv` rows and the "black keys" on the `12345` and `asdf` rows of the keyboard, as in the drawing below.

![Keyboard Layout](https://www.dropbox.com/s/7shc5qz5e1n0qs1/image008.jpg?dl=1)

You are being provided a skeleton version of the `Guitar221` class that includes this string defined as a constant called `KEYBOARD`.  The i<sup>th</sup> character of the string corresponds to a frequency of _440 × 2<sup>(i - 24)/12</sup>_, so that the character “q” is 110Hz, “i” is 220Hz, “v” is 440Hz, and “ ” (space) is 880Hz.

In working on this second part of the assignment, you are generalizing the code that you will find in `GuitarLite`.  Because that instrument has just two strings, it uses two separate fields.  Your instrument has 37 strings, so it uses an array of strings.  Each of the operations defined in the interface needs to be generalized from using two specific strings to using an array of strings.  For example, the sample method returns the sum of the current samples.  `GuitarLite` does this by adding together two numbers.  Your version will have to use a loop to find the sum of all 37 samples.

A description of the Guitar interface methods appears below.
```
public interface Guitar {
    // plays the given note if possible by plucking an appropriate string;
    // the pitch uses a chromatic scale where Concert-A has a pitch of 12
    public void playNote(int pitch);

    // returns whether there is a string that corresponds to this character
    public boolean hasString(char key);

    // plucks the string for this character
    public void pluck(char key);

    // returns the current sound (sum of all strings)
    public double sample();

    // advances the simulation by having each string tic forward
    public void tic();

    // optional method that returns the number of times tic has been called;
    // returns -1 if not implemented
    public int time();
}
```

The `GuitarLite` class is not well documented and does not handle illegal keys.  Your `Guitar221` class should include complete comments.  The pluck method should throw an `IllegalArgumentException` if the key is not one of the 37 keys it is designed to play.  Recall that strings have an `indexOf` method that you might find helpful.  In some of the `String` documentation you will see that some methods take a parameter of type `CharSequence`.  You can think of this as being a parameter of type `String`.

As noted in the interface, the method called `time( )` is optional.  It is not implemented in the `GuitarLite` class, but you should implement it in the `Guitar221` class.

In writing the `playNote( )` method for `Guitar221`, notice that concert-A is assumed to have a pitch of 12.  As noted earlier, the character “v” is concert-A, which has index 24 in the keyboard string.  You can convert from a pitch value to an index in your string by adding 12 to the pitch value.  Remember that not every value of pitch can be played by any given guitar.  If it can’t be played, it is ignored.  It should not cause your code to throw an exception.

As mentioned earlier, the ring buffer in your `GuitarString` class should be implemented as a queue.  You are allowed to use any of the methods defined in the `Queue` interface.  In particular, you are allowed to use the peek method that allows you to examine the value at the front of the queue without removing it.  The `GuitarString` class would be inefficient if you didn’t have the ability to peek at the front of the queue.

To generate random real numbers, you can either call the `Math.random( )` method or you can construct a `Random` object and call its `nextDouble( )` method.  Both methods return a random real value _n_ such that _0 <= n < 1_.

You are given debugging and testing programs for `Guitar221`. (See [MP1 Debug](https://github.com/CPEN-221/mp1-debug) and [MP1 Play](https://github.com/CPEN-221/mp1-play).)  You can test your implementation using these programs. You are also provided with some sample inputs to help in this process.

### Submitting Your Work

Submit your work by pushing your work to the Github repository that was created for you for this machine problem.

In terms of correctness, your class must provide all of the functionality described above and must satisfy all of the constraints mentioned in this writeup.  In terms of style, we will be grading on aspects such as your use of comments, good variable names, consistent indentation, minimal fields and good coding style to implement these operations.

### Credits
* This machine problem is a variation of an assignment posed at Princeton University.
* Credits to [Robert Sedgewick](http://www.cs.princeton.edu/~rs/) and [Kevin Wayne](http://www.cs.princeton.edu/~wayne/contact/).
