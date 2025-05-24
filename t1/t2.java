// DIFF NTW println, print, printf

// System.out.println("Hello")
// System is a class that has system related utility methods(methods that interact with system run time env)
// out is a static member of System class
// System.out. --> a lot of methods present
// to print we can use println, print, printf 

//in 'ln' new line char is added after every statement
//println(1,2) - error --> takes only 1 parameter
System.out.println("Hello");
System.out.println("world")
System.out.println();
System.out.println('V');
//Hello
//world
// 
//V

// in print no new line char is added after every statement
System.out.print("Hello");
System.out.print("world")
System.out.print(); //error --> print method needs an argument
System.out.print('V');
//HelloworldV

// in printf no new line char is added after every statement
System.out.printf("Hello");
System.out.printf("world")
System.out.printf(); // error
System.out.printf('V'); // error --> printf takes only string as first argument
//HelloWorld

int a=1;
int b=2;
String c = "Sum";
System.out.println(a + b + c) //3Sum
System.out.println(c + a + b); //Sum12
System.out.println(c + "of " + a + " and " + b + ": " + (a + b));
System.out.print(c + "of " + a + " and " + b + ": " + (a + b));
System.out.printf(c + "of " + a + " and " + b + ": " + (a + b));
//Sum of 1 and 2: 3
//Sum of 1 and 2: 3Sum of 1 and 2: 3
//can also write with printf like this
System.out.printf("%s of %d and %d: %d", c, a, b, a+b); //Sum of 1 and 2: 3
char d='V';
float e=1.2f;
System.out.printf("%c %f", d, e); //V 1.200000


//STRINGS
// ways to create strings
// 1. Using String literal
String c = "Hello";
String d = "Hello";
System.out.println(c==d); //true-> same object reference (we are not comparing values here)
// What Happens in Memory?
// "Hello" is stored once in the String Pool (inside Heap memory). and c points to this pooled string.
// instead of creating a new "Hello" object, Java reuses the same object from the String Pool.
// Both c and d point to the same object in the String Pool.
// Since c == d compares references, and both refer to the same object, it returns true.

// 2. Using new Keyword
String a = new String("Hello");
String b = new String("Hello");
System.out.println(a==b); //false --> a and b are pointing to different objects
// new String("Hello") forces Java to create a new object in the Heap (outside the String Pool).
// Even though a and b store the same "Hello" value, they are different objects in memory.
// Since a == b compares memory references (not values), it returns false.

// String Methods
String c = "Hello";
String d = "Hello";
System.out.println(c.equals(d)); //true --> compares values
String d = "hello"
System.out.println(c.equalsIgnoreCase(d)); //true --> compares values ignoring case

String c = "remote";
String d = "car";
int i = c.compareTo(d);
System.out.println(i); // 15 --> compares the ASCII values of the strings (r-c =15)
String d = "ramote";
int i = d.compareTo(c);
System.out.println(i); // (a - e = -4)
String d = "REMOTE"
int i = c.compareToIgnoreCase(d); // 0 --> compares the values ignoring case

String str = "Vinyas Sahai";
System.out.println(str.substring(7)); //Sahai
System.out.println(str.substring(0, 4)); //Viny
System.out.println(str.subSequence(7, 12)); //Sahai

String str = "   Vinyas Sahai   ";
System.out.println(str.trim()); //Vinyas Sahai -> remove spaces from start and end
System.out.println(str.replace("Sahai", "Sharma")); //Vinyas Sharma
System.out.println(str.replace('i', 'o')); //Vonyas Sahao
System.out.println(str.contains("Sah")) // true
System.out.println(str.contains("S")) // true

String str = "";
System.out.println(str.isEmpty()); //true
String str = " ";
System.out.println(str.isEmpty()); //false
System.out.println(str.isBlank()); //true

int a =10;
String str = String.valueOf(a);
System.out.println(str); //10 -> converts int to string

//till now we were using object methods
//here we are using String class method
String formattedString = String.format("My name is %s and I am %d years old", "Vinyas", 22);
System.out.println(formattedString); //My name is Vinyas and I am 22 years old

//many such methods are present --> see doc

// Why Are Strings Immutable in Java?
//strings in java cannot be modified
// Any operation that appears to modify a String actually creates a new object, leaving the original string unchanged.
// This is because Java stores String objects in a special memory area called the String Pool,
// immutability helps with memory optimization and security.

String str1 = "Hello"; //"Hello" is stored in the String Pool, and str1 refers to it.
str1.concat(" World"); // Attempting to modify str1
System.out.println(str1); // Output: Hello (unchanged)

// Correct way to change the string
str1 = str1.concat(" World"); 
System.out.println(str1); // Output: Hello World

String s1 = "Java";
String s2 = "Java"; // Reuses the same object
s1 = "Python"; // Creates a new string, does not modify "Java"
System.out.println(s2); // Still prints "Java"
// If Strings were mutable, s2 would also change to "Python", which is incorrect.

// Strings are widely used in security-sensitive areas like database connections, file paths, and encryption keys.
// If String were mutable, hackers could modify them and compromise security.
String password = "mySecret";
password = "hacked"; // If String was mutable, this could be changed externally!

// Since Strings cannot be modified, multiple threads can read the same string without synchronization issues.
// If String were mutable, one thread modifying a string could cause unexpected results for others.

// to use mutable string we use things like StringBuilder which we will learn later


// CONDITIONAL STATEMENTS
// if-else, switch
// from java 7 onwards we can use strings in switch case
//byte, char, short, int, String, enum are allowed in switch case
//long, float, double, boolean are not allowed in switch case
// using break is important
String b = "Hello";
switch (b) {
    case "Hello":
        System.out.println("b is Hello");
        break;
    case "World":
        System.out.println("b is World");
        break;
    default:
        System.out.println("b is neither Hello nor World");
        break;
}

int a = 2;
switch (a) {
    case 1:
    case 2:
    case 3:
        System.out.println("a is 1 or 2 or 3");
        break;
    case 4:
        System.out.println("a is 4");
        break;
    default:
        System.out.println("a is neither 1 nor 2");
        break;
}// a is 1 or 2 or 3


//LOOPS