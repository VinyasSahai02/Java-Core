// JDK - java development kit 
// - consists of tools and libraries and JRE
// - provide an environment for developing Java applications
// - if you would like to develop a Java-based software application then along with JRE you may need some additional necessary tools, which is called JDK.
// contains - jre, java(interpreter), javac(compiler), jar(archiver)
// platform - dependent


// JRE - Java Runtime Environment
// - provides env to write java program
// - if you are only interested in running Java programs
// - includes libraries to run Java application + JVM
// - platform dependent


// JVM - Java Virtual Machine
// - provides a runtime environment in which Java bytecode can be executed
// jvm interprets java program :
// 1. when jvm gets the bytecode, it first parses it
// 2. then it runs line by line
// platform - independent

//GFG - HOW JVM WORKS (READ)

// WORKING :
// 1. Java Source File (e.g., Example.java): You write the Java program in a source file.
// 2.source file is compiled by the Java Compiler (part of JDK) into bytecode, which is stored in a .class file
// 3.bytecode is executed by the JVM (Java Virtual Machine), which interprets the bytecode and runs the Java program.
// 1.Class Loader: The JRE’s class loader loads the .class file containing the bytecode into memory.
// 2.Bytecode Verifier: The bytecode verifier checks the bytecode for security and correctness.
// 3.Interpreter: The interpreter reads the bytecode stream and executes the program
// 4.Execution: The program executes, making calls to the underlying hardware and system resources as needed.

// FLOW
// -java program compiles into bytecode(coded program that we cannot understand) , compilation is done by javac
// -jvm them converts bytecode into machine code(o's and 1's)
// * bytecode is platform independent while machine code is platform dependent ie. it is diff for mac,windows
// compiler - javac , interpreter - jvm , interpreter is slower then compiler
// * jvm also has a compiler(just in time compiler) ,if a programm is used frequently then JIT compiler compiles and stores it 

class Test{
    public static void main(String[] args){
        System.out.println("Hello");
    }
} 
//when i write javac t1.java in cmd - test.class file is created. this is the bytecode
//then write java Test in cmd - Hello gets printed. this is basically telling jvm to read bytecode. Test is class

//ide- integrated development environment (intelliJ IDEA)

//main is the method name and is the entry point of the program
// public - access modifier
// static - indicates that the method belongs to the class rather than an instance of the class
// void - method does not reutrn any value
// String[] args - this method accepts an array of strings as params. this is where the command line arguments can be passed to your program
class Test{
    public static void main(String[] args){
        System.out.println(args[0]); 
        //javac Test.java then java Test abcd
        //output - abcd

        System.out.println(args[0]);
        System.out.println(args[1]);
        //javac Test.java then java Test abcd efgh
        //output - abcd efgh
    }
} 
// - when jvm receives the bytecode, it calls Test.main() --> because it is public, w/o obj creation it is calling main so static
// System - class in java.lang package that provides access to the system ,including the console
// out - instance of PrintStream class w/i System class, represent standard output stream
// println - method used to print a line of text to the console


//PRIMITIVE DATA TYPES 
// whole numbers - byte, short(-32768 to 32767), int(write Integer to find), long - add an 'l' at the end of the number
// by default if not specified then int is taken
byte age = 23;
System.out.println(Byte.MIN_VALUE); // -128 (notice capital 'B') - to find range of data types
System.out.println(Byte.MAX_VALUE); // 127

// decimal numbers - float - 10.12413876f , double - 10.12413876
// by default if not specified then double is taken
// precision in float and double(approximately 15-16 significant decimal digits)
float price = 10.12413876f;
System.out.println(price); // 10.124138 - approximately 6-7 significant decimal digits
float price = 1.12413876f;
System.out.println(price); // 1.1241388

// characters - single quotes
char gender = 'M'; & char gender = 77;
System.out.println(gender); // M
// // char gets stored as unicode(16 bit) - 0 to 65535
// --> System.out.println((int) gender) - 77 & System.out.println((char) 77) - M
// * System.out.println((int) Character.MIN_VALUE); - 0 & System.out.println((int) Character.MAX_VALUE); - 65535
// * or char gender = '\u27A4'; (\u stands for unicode) - char can also store hexdecimal

// booleans - boolean isAvailable = true;
// *dont learn ranges

// byte (1 byte)
// short (2 bytes)
// int (4 bytes)
// long (8 bytes)
// float (4 bytes)
// double (8 bytes)
// char (2 bytes)
// boolean (1 bit, cannot be type cast)

//WHY IS CHAR 2 BYTES?
//In Java, char is designed to store Unicode characters, which can represent a wide range of languages and symbols worldwide.
//Unicode characters require at least 16 bits (2 bytes) to store most characters.
//In c++, it is 1byte because c++ uses ASCII instead of Unicode

//WHY JAVA CHOSE UNICODE BY DEFAULT?
//Globalization – Java was designed to work internationally, supporting multiple languages by default.
//Consistency – Java aims to be platform-independent, and UTF-16 ensures uniform character representation across all systems.
//Future-Proofing – ASCII was limited to 128 characters, but Unicode can store thousands of characters, including emojis and symbols.

//WIDENING CONVERSION (inplicit casting)
// Happens automatically when converting a smaller data type into a larger data type.
// No data loss occurs because the larger type can hold all values of the smaller type.
// (byte → short → int → long → float → double) --> conversion order
byte b = 10;       // 1 byte
short s = b;       // byte -> short  --> 10
int i = s;         // short -> int  --> 10
long l = i;        // int -> long  --> 10
float f = l;       // long -> float  --> 10.0
double d = f;      // float -> double  --> 10.0

char c = 'A';      // Character 'A' (ASCII value 65)
int charToInt = c; // char -> int (ASCII value is stored) --> 65

//NARROWING CONVERSION
// Happens when converting a larger data type to a smaller one.
// Requires explicit casting using (type), otherwise, a compilation error occurs.
// May result in data loss if the value exceeds the target type’s range.
//(double → float → long → int → short → byte) --> conversion order
double d = 10.99;  // 8 bytes
float f = (float) d;  // double -> float  --> 10.99
long l = (long) f;  // float -> long  --> 10 (.99 is lost)
int i = (int) l;  // long -> int  --> 10
short s = (short) i;  // int -> short  --> 10
byte b = (byte) s;  // short -> byte  --> 10

int num = 97;
char c = (char) num; // int -> char (97 is 'a' in ASCII)  --> a

//example
byte a=5;
a= a+1; // ❌ Not allowed! - because a+1 is int and int cannot be stored in byte
a= (byte) (a+1); 
a+=1; // ✔️ compound assignment operatos(+=, -=, *=, /=, %=) perform implicit casting

*boolean flag = true;
// int num = (int) flag; // ❌ Not allowed!


//VARIABLE NAMING
// variable name is case sensitive -> amount and amOunt are different
// variable name can contain letters, digits, underscore, and dollar sign -> my_name, my$name, myname123
// starting can be with a letter, underscore, or dollar sign -> _myname, $myname, myname
// variable name cannot start with a digit -> 123myname ❌
// keywords cannot be used as variable names -> int class = 10 ❌
// CONVENTION - camelCase - first letter of the first word is lowercase and the first letter of the subsequent words is uppercase -> myName, myAge, myAddress
// * variable name should be meaningful and descriptive -> int x = 10; ❌ int age = 10; ✔️

//TYPES OF VARIABLES
// 1. Local Variables - declared inside a method, constructor, or block
// stored in stack memory
public class Example {
    public void show() {
        int num = 10; // Local variable
        System.out.println("Number: " + num);
    }
}
// 2. Instance Variables - declared inside a class but outside any method. 
// belongs to the object of the class. accessed thru objects
// each object has its own copy of the instance variable
// stored in heap memory
public class Person {
    String name; // Instance variable
    int age;
    public void display() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}
// 3. Static Variables - declared with the static keyword in a class.
// Shared among all objects
// stored in the method area
// created at the start of program execution and destroyed automatically when execution ends.
// can access them using class name(recommended) or object name(not recommended)
public class Employee {
    static String company = "Google"; // Static variable
    public static void showCompany() { // Make method static
        System.out.println("Company: " + Employee.company);
    }
    public static void main(string[] args) {
        System.out.println("Company: " + Employee.company); //accessed using class name
        Employee.showCompany(); //calling method using class name
    }
}


//BITWISE OPERATORS
// operands - byte, short, int, long
// & - AND operator - if both bits are 1 then 1 else 0 (like multiply)
// --> 4&5 => 100 & 101 => 100 = 4

// | - OR operator - if any of the bits is 1 then 1 else 0 (like add)
// --> 4|5 => 100 | 101 => 101 = 5

// ^ - XOR operator - if both bits are different then 1 else 0
// --> 4^5 => 100 ^ 101 => 001 = 1

// ~ - NOT operator - flips the bits
// --> ~4 => ~100 => 011 = -5

// << - Left Shift operator - shifts the bits to the left by n positions
// --> 5<<1 => 101 << 1 => 1010 = 10
// 1010<<1 => 10100 = 20 (left mai shift hoje ja rahe hai aur right mai 0 add ho rahe hai)

// >> - Right Shift operator - shifts the bits to the right by n positions
// --> 5>>1 => 101 >> 1 => 10 = 2 
// 1 gets discarded and 0 gets added to the left kyuki lets say 3 blocks hai 
//1st block - 1, 2nd block - 0, 3rd block - 1 
//shifting ke baad 1st block - 0, 2nd block - 1, 3rd block - 0
//1st ka 2nd mai chala gaya and 2nd ka 3rd mai chala gaya 
// IF NUMBER IS NEGATIVE THEN 1 GETS ADDED TO THE LEFT SIDE (warna 0 add hota hai)
// --> -5>>1 => -101 >> 1 => -110 = -3

// >>> - Unsigned Right Shift operator - shifts the bits to the right by n positions and fills the leftmost bits with 0
// --> 5>>>1 => 101 >>> 1 => 010 = 2