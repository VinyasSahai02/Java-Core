//ARRAYS
int[] arr = new int[5]; // Creates an array of size 5, initialized with default values (0)
int arr[] = new int[5]; // OR
int[][] arr = new int[3][3]; // must specify rows and cols 
// arr is a reference variable stored in the stack. It holds the memory address (reference) of the actual array in the heap.
// new int[5] allocates a contiguous block of memory for 5 integers in the heap
// In Java, arrays are objects, so they are always stored in the heap.
// Primitive types (int a = 1;) are stored directly in the stack.

System.out.println(arr.length) //length is a property of array not a method in Java

//traversing an array
int[] arr = {1,2,3,4,5};
for(int i = 0; i < arr.length; i++) { 
    System.out.println(arr[i]);
}
for(int i : arr) { 
    System.out.println(i);
}
for(int i = 0; i < arr.length; i++) { 
    for(int j = 0; j < arr.length; j++) {  //j<arr[i].length also works
    System.out.println(arr[i][j]);
}}
for (int[] ints : arr) {
    for (int j = 0; j < ints.length; j++) {
        System.out.println(ints[j]);
    }
}

//* for max and min values -> Interger.MIN_VALUE and Integer.MAX_VALUE

//Jagged array
int[][] arr = new int[3][]; // 3 rows, but columns are not specified
// a b
// c d e
// f g 
for(int i = 0; i < arr.length; i++) { 
    for(int j = 0; j < arr[i].length; j++) {
    System.out.println(arr[i][j]);
}}
// if rows of diff lengths are not specified
int[][] arr = new int[3][];
arr[0] = new int[3]; // 3 columns in 1st row
arr[1] = new int[2]; // 2 columns in 2nd row
arr[2] = new int[4]; // 4 columns in 3rd row


// METHODS IN JAVA 
// basically user deifned functions
//Syntax - AccessModifier ReturnType MethodName(Parameters) { //method body }
public static void main(String[] args){
    int[] arr = {1,2,3,4,5};
    int sum= sumArray(arr);
    System.out.println(sum);
}
public static int sumArray(int[] arr) { //method
//static here is important as non-static method cannot be referenced from a static context
    int sum = 0;
    for(int i : arr) {
        sum += i;
    }
    return sum;
}

// OVERLOADING
public static int sum(int a, int b) {
    return a + b;
}
public static int sum(int a, int b, int c) {
    return a + b + c;
}
//if we want 4 variables then we have to write another func, but we can do this 
public static int sum(int ...arr) { //variable arguments -> multiple integers are taken as parameters but inside the function they are treated as an array
    int sum = 0;
    for(int i : arr) {
        sum += i;
    }
    return sum;
}
public static void main(String[] args){
    System.out.println(sum(1,2)); // 3
    System.out.println(sum(1,2,3)); // 6
    System.out.println(sum(1,2,3,4)); // 10
}
//similarly this can also be wriiten public static void main(String... args){}


public static void main(String[] args){
    int x =10;
    System.out.println(multilpyBy10(x));// 100
    System.out.println(x); // 10
}
public static int multilpyBy10(int a){ 
    return a*10;
}

public static void main(String[] args){
    String str = "vinyas";
    System.out.println(upper(str)); // VINYAS
    System.out.println(str); // vinyas
}
public static String upper(String str){
    return str.toUpperCase();
}
// str(reference) points to vinyas in the string pool 
// this reference gets copied in to upper(str)
// but since strings are immutable, str.toUpperCase() creates and returns a new string
// BUT NOW TRY SOMETHING DIFFERENT
class Cat{
    String name;
}
public static void main(String[] args){
    Cat c = new Cat(); // c is reference to the Cat object in the heap
    c.name = "kitty";
    Cat cat = upper(c); // cat references the same object as c
    System.out.println(cat.name); // KITTY
    System.out.println(c.name); // KITTY
    //Since both c and cat refer to the same object, the change affects both.
}
public static Cat upper(Cat cat){
// The reference of c (memory address) is passed to cat in the method.
// Now, cat and c both point to the same object in the heap.
    cat.name = cat.name.toUpperCase(); //modifies the same object
    return cat; // returning address of the object
}
//even we do this it still changes
public static void main(String[] args){
    Cat c = new Cat();
    c.name = "kitty";
    upper(c);
    System.out.println(c.name); // KITTY
}
public static void upper(Cat cat){
    cat.name = cat.name.toUpperCase();
}
// Unlike primitive types, which are passed by value, objects in Java are passed by reference


// PACKAGES and classPATH
//see notes in IntellijIdea practice folder


//OOPS
public class Car{
    String name;
    int price;
    public void display() {
        System.out.println("Name: " + name + ", Price: " + price);
    }
}
public class Main{
    public static void main(String[] args) {
        Car c1 = new Car();
        c1.name = "BMW";
        c1.price = 100000;
        c1.display();
    }
}

//encapsulation
//getters and setters
// in intelliJ right click , click on generate and generate getters and setters automatically
public class Car{
    private String name;
    public void setName(String name) {
        this.name = name;  // Refers to the current object's 'name' variable
        //this->name cannot be used in Java unlike in C++
        //this refers to the instance variable
    }
    public String getName() {
        return name;
    }
}
public class Main{
    public static void main(String[] args) {
        Car c1 = new Car();
        c1.setName("BMW");
        System.out.println(c1.getName());
    }
}

//inheritance
public class Animal {
    public void sound() {
        System.out.println("Animal makes a sound");
    }
}
public class Dog extends Animal {
    @Override //@something is called annotation
    //good practice to use them (will learn more about them later)
    public void sound() { //method overridding
        System.out.println("Dog barks");
    }
}
//java has single, multi-level, hierarchical but does not have multiple inheritance due to ambiguity problem 
//ambiguity problem can be solved by using interfaces

//super keyword
class Animal {
    String name = "Generic Animal";
    public Animal() {  // Animal(String name) --> parameterised constructor
        System.out.println("Animal constructor called");
    }
    public void sound() {
        System.out.println("Animal makes a sound");
    }
}
class Dog extends Animal {
    String name = "Dog";
    public Dog() {
        super(); // Calls the parent class constructor (must be the first statement)
        //super(); can only be used inside a constructor
        //In case of a parameterized constructor --> super("generic animal")
        System.out.println("Dog constructor called");
    }
    public void sound() {
        super.sound(); // Calls the sound() method of the parent class
        //// This does not need to be the first statement as it is calling a method not a constructor
        System.out.println("Dog barks");
    }
    public void printNames() { 
        //super can also access parent class variables
        System.out.println("Child class name: " + name);       // Dog
        System.out.println("Parent class name: " + super.name); // Generic Animal
    }
}
public class Main {
    public static void main(String[] args) {
        Dog d = new Dog();
        d.sound();
    }
// Animal constructor called
// Dog constructor called
// Animal makes a sound
// Dog barks
}

//polymorphism
// allows methods to do different things based on the object that it is acting upon
// even though method name and signature might be same
// compile time polymorphism -> method overloading
// run time polymorphism -> 
// method overriding (dynamic method dispatch) (upcasting)
public class Animal {
    public void sound() {
        System.out.println("Animal makes a sound");
    }
}
public class Dog extends Animal {
    @Override
    public void sound() {
        System.out.println("Dog barks");
    }
    public void wagTail() {
        System.out.println("Dog wags tail");
    }
}
public class Main {
    public static void main(String[] args) {
        Aniaml newAnimal = new Animal();
        newAnimal.sound(); // Animal makes a sound

        Animal newDog = new Dog(); //even after using Animal refernece rether than Dog reference it does not show error
        newDog.sound(); // Dog barks
        //The compiler checks if newDog (of type Animal) has a sound() method. Since sound() exists in Animal, there is no compilation error.
        // newDog is declared as Animal, but it stores an object of type Dog.
        // Even though newDog has type Animal, at runtime, Java will call Dog's sound() method.
        // This is because Java determines which method to call based on the actual object type (not reference type).
        // newDog.wagTail(); ❌ ERROR: Compiler only sees methods of Animal!
    }
}

//abstraction
// Abstract class
public abstract class Animal {
    public abstract void makeSound(); // Abstract method (no implementation)
    public void sleep() { // Concrete method (has implementation)
        System.out.println("Sleeping...");
    }
    //abstract classes should not have public constructors
}
public class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Dog barks");
    }
}
public class Main {
    public static void main(String[] args) {
        Dog myDog = new Dog();
        myDog.makeSound(); // Output: Dog barks
        myDog.sleep(); // Output: Sleeping...
        Animal myAnimal = new Animal(); // ❌ ERROR: Cannot instantiate abstract class
    }
}


//ACCESS MODIFIERS
// public - accessible from anywhere
// private - accessible only within the same class
// protected - accessible within the same package and in classes that extend the parent class that contains the protected 
// default - accessible within the same package

| Context \ Access Modifier       | private | default (no modifier) | protected | public |
|---------------------------------|---------|----------------------|-----------|--------|
| Same class                      | Yes     | Yes                  | Yes       | Yes    |
| Same Package                    | No      | Yes                  | Yes       | Yes    |
| Subclass (same package)         | No      | Yes                  | Yes       | Yes    |
| Subclass (different package)    | No      | No                   | Yes       | Yes    |
| Different Package (non-subclass)| No      | No                   | No        | Yes    |

//USE CASES OF PRIVATE CONSTRUCTOR
//1. if a constructor of a class is private then obj cannot be created
//   but if there is a method in the class that we want to access
//   then without obj creation we can access that method by className.Method, given that the method has static keyword
//   Usually such a class is named as Utils

//2. if we want that only 1 obj should be created of a particular class
public class School {
    private static School instance;
    private School(){

    }
    public static School getInstance(){
        if(instance == null){
            instance = new School();
        }
        return instance;
    }
}
public class Main {
    public static void main(String[] args) {
        School s1 = School.getInstance();//instance is null so constructor runs
        School s2 = School.getInstance();//instance is not null so instance gets returned
        System.out.println(s1 == s2); // true
    }
}

//a class can only be public or default
class School{//default

}
//cannot create obj outside package

//protected
public class Animal{
    private String name;
    protected String sound;
    public Animal(String name, String sound){
        this.name = name;
        this.sound = sound;
    }
    public void makeSound(){
        System.out.println(name + " makes a sound " + sound);
    }
    protected void changeSound(String newSound){
        this.sound = newSound;
    }
}
public class Dog extends Animal{
    public Dog(String name){
        super(name, "barks");
    }
    public void wagTail(){
        System.out.println(getName() + "is wagging its tail");
    }
    private String getName(){
        return getclass().getSimpleName();
        // getClass() → Gets the runtime class of an object.
        // getSimpleName() → Returns the class name as a String.
    }
    public void setDogSound(String newSound){
        changeSound(newSound);//setDogSound("howls"), which internally calls changeSound("howls").
    }
}
public class Main{
    public static void main(String[] args){
        Dog d = new Dog("Tommy");
        d.makeSound(); //Tommy makes a sound barks
        //d.changeSound("howls");// ❌ Error: Main class does not directly extends Animal class 
        // if Main class is in same package it will not show error 
        d.setDogSound("howls"); // works as a wrapper to call changeSound()
        d.makeSound(); //Tommy makes a sound howls
        d.wagTail(); //Dog is wagging its tail
    }
}