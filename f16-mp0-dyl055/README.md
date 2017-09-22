CPEN 221 - MP0 - Fall 2016
===
**Learn How To Manipulate Strings**

## Important Notes
* Due September 16, by 11:59 p.m.
* This is an *individual* assignment.
* This assignment has a weight of 3% towards your final *raw* grade.
* _This is a (somewhat) gentle introduction to the material._ Our acceleration will be quite high. Please spend time understanding Java and trying out examples on your own.

## Background

This assignment is principally concerned with `String` manipulation in Java. Some of the learning objectives of this assignment are:

* to gain familiarity with Java's `String` methods, as well as conditional statements, loops and methods;
* get comfortable using Eclipse, Git and GitHub.

A critical ability we would like to see in CPEN 221 students is that of reading and learning from Java documentation. For this assignment, we do expect that you will read the [`String`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) documentation from the Oracle Java website and use relevant methods that are provided by the `String` class to complete the assigned tasks.

## Main Tasks

In this assignment, you will implement methods for the following string manipulation tasks.

### Check if a string is a palindrome

A [palindrome](https://en.wikipedia.org/wiki/Palindrome) is a string that reads the same even after it is reversed. Some popular examples are *redivider*, *civic*, *kayak* and *noon*. Longer phrases such as *madamimadam* and *madam i madam* are also palindromes. For the purposes of this assignment, we will be case-sensitive: *Kayak* is not a palindorme while *kayak* and *KayaK* are palindromes.

### Count the number of times a query string occurs in a string

* Suppose our source string is `Luke Skywalker` and the query string is `ke` then the method should return 2.
* For source string `Able was I ere I saw Elba` and the query string `I`, the method should return 2.
* For source string `abababab` and the query string `aba`, the method should return 3.
* For source string `blah` and the query string `bug`, the method should return 0.

### Format a name

Suppose a string represents somebody's full name. The first word in the string is the first name, the last word is the last name, and any words in between are middle names. The string may have an arbitary amount of whitespace (blank spaces) between words and at the beginning or the end. Return a string that is a nicely formatted name in the format `Lastname, Firstname [Middle Initials]`. In the string that this method returns, the first character of the last name, the first character of the first name and the middle initials must be capitalized; all other characters must be in lower case.

Some examples:

* `sathish   gopalakrishnan` should result in `Gopalakrishnan, Sathish`
* `  Matei Radu Ripeanu` should result in `Ripeanu, Matei R.`
* [`  John Ronald   reuel Tolkien    `](https://en.wikipedia.org/wiki/J._R._R._Tolkien) should result in `Tolkien, John R. R.`
* [`Arvind`](https://en.wikipedia.org/wiki/Arvind_(computer_scientist)) should result in `Arvind` (this is a special case when there is only one word in the string)


### Are two words anagrams of each other?

You have to implement a method that takes two strings as input arguments and returns `true` if and only if the two strings are anagrams of each other. An anagram of a string `s` is a permutation of the characters in the string `s`.

Some examples:

* `evil` and `live` are anagrams (palindromes are a special case of anagrams)
* `i am lord voldemort` and `tom marvolo riddle` are anagrams of each other but `iamlordvoldemort` and `tom marvolo riddle` are not because of the difference in spaces.
* `foo` and `bar` are not anagrams of each other.

### Decoding strings

An encoding of the string `aaassddddffg` is the string `3a2s4d2f1g`. Along these lines, `zzz56yyy` would be encoded as `3z15163y`. Assuming this encoding method, an encoded string is a *sequence* of digit-character pairs, with the digit representing how many times the following character occurs in the original, unencoded, string. Implement a method to decode a string (given the encoded version). (What should you do if a string is not in the correct format? For now, assume that all test strings will conform to the expected format.)

## How To Get Started

### Grabbing the Starter Code

1. Use this [https://classroom.github.com/assignment-invitations/ba244f30d53884c1c4196e6ff580a706](https://classroom.github.com/assignment-invitations/ba244f30d53884c1c4196e6ff580a706) link to create a new private repository in the `CPEN-221` GitHub organization. You will have read/write access to this repository and the repository will be initialized with this `README` as well as with the starter code. The repository that will be created will have a URL that will be something like `https://github.com/CPEN-221/f16-mp0-GSathish` (except that `GSathish` will be replaced by your GitHub username).

1. You will start work by obtaining the initial code provided in this GitHub repository. To do so, open up a terminal (on a Linux or Mac OS X system) or Git Bash (on a Windows system), and then change your working directory to the workspace directory for the Eclipse Workspace. You can use the `cd` command to achieve this.

1. Once you have changed your working directory to the Eclipse workspace directory, you can clone the repository that was created for you using the `git clone` command. For example:
``git clone https://github.com/CPEN-221/f16-mp0-GSathish``
(Remember to replace `GSathish` with your GitHub username.)

### Opening a new project in Eclipse

Once you have cloned the repository, you can create a new Eclipse project for this machine problem.

You do this by starting Eclipse and then selecting the following menu item: `File -> New -> Java Project`. You will be asked to provide a project name, and if you had cloned files to the correct location then you can simply enter `f16-mp0-<your username>` (replace `<your username>` appropriately) as the project name and Eclipse will auto-complete the remaining entries in the `New Project` window, and you can select `Finish`.

### Implementing the required methods

When you open up the project in Eclipse, in the Package Explorer frame you should see a [package](https://docs.oracle.com/javase/tutorial/java/package/packages.html): `stringhelper`.

You will have to implement the required methods in this package. The exact file where you will write code is `StringUtility.java`. The methods that you will have to implement will have a comment that starts with `TODO`. You may also notice that Eclipse marks up these comments in a special manner near the line numbers.

Implement the methods requested. You should find this part of the task easy if you are comfortable with iterative structures (`for` loops, for example) and conditional structures (`if` statements) in a language like C. Remember to read the Java documentation for `String`.

## Submitting your work

To submit source code, from the `f16-mp0-<your username>` directory, use the following `git` commands:

```git
git add src/*
git commit -m "MP0 submission"
git push
```

> Note that the `git` commands in the previous step were provided as an example. You should get comfortable with using `git` as the situation demands.
