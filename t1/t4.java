//STATIC-> primarily used for memory management
// allows methods, variables, blocks, and nested classes to belong to the class rather than instances (objects) of the class.

// A static variable belongs to the class, not the instance.
// It is shared among all objects of the class.
// It gets memory only once when the class is loaded.
class Example {
    static int count = 0; // Static variable (shared by all instances)
    //if it was not static then count=0 for all objs
    private int age;
    Example() {
        count++; 
    }
    public static int square(int x) {
        System.out.println(age); // ❌ Error: static method cannot access non-static variables or methods directly
        return x * x;
    }
    public static void main(String[] args) { //main method is static because JVM directly calls it without creating obj
        System.out.println(Example.count); // Output: 0
        Example obj1 = new Example(); // Count: 1
        Example obj2 = new Example(); // Count: 2
        System.out.println(Example.count); // Output: 2 ->static can be directly accessed by class name (dont need to create obj)
        System.out.println(Example.square(5)); // 25
    }
}
//this and super cannot be used as they refer to objects
//static methods are inherited but cannot be overridden
class Parent {
    static void hello() {
        System.out.println("Hello from Parent");
    }
}
class Child extends Parent {
    static void hello() {
        System.out.println("Hello from Child");
    }
}
public class Main {
    public static void main(String[] args) {
        Parent.hello();  // Output: Hello from Parent
        Child.hello();   // Output: Hello from Child

        Parent p = new Child();
        p.hello(); // Output: Hello from Parent (method hiding, not overriding)
        //Even though p is a Child object, the static method call is based on the reference type, not the object. This is called method hiding, not overriding.
    }
}

//static block 
class Demo {
    static int value;
    static {
        value = 100;
        System.out.println("Static block executed. Value = " + value);
        // A static block executes once when the class is loaded.
        // Used for initializing static variables.
    }
    public static void main(String[] args) {
        System.out.println("Main method executed.");
    }
    //Static block executed. Value = 100  
    // Main method executed
}


//FINAL -> used to restrict modification
// Once assigned, a final variable cannot be changed.
// It must be initialized either at declaration or in the constructor
class Test {
    final int MAX_VALUE; // ❌ Error: Must be initialized here or in the constructor
    final int MAX_VALUE = 100; // ✅ Initialized here
    Test() {
        MAX_VALUE = 200; // ✅ Initialized in the constructor
    }
    public static final int MAX_VALUE;
    static{
        MAX_VALUE = 100; // ✅ Initialized in a static block but needs to be declared static
    }
    public void display() {
        // MAX_VALUE = 200;  ❌ Error: Cannot modify a final variable
        System.out.println("MAX_VALUE: " + MAX_VALUE);
    }
}

// If a method is marked final, it cannot be overridden in child classes.
class Parent {
    final void show() {
        System.out.println("This is a final method.");
    }
}
class Child extends Parent {
    // void show() { ❌ Error: Cannot override a final method
    //     System.out.println("Trying to override.");
    // }
}

//If a class is marked final, it cannot be extended (inherited)
final class Animal {
    void makeSound() {
        System.out.println("Animal makes a sound.");
    }
}
// class Dog extends Animal { ❌ Error: Cannot inherit from final class
// }


//INTERFACE
// interface- blueprint for class , class- blueprint for object
// contains only abstract methods (before Java 8) and static or default methods (from Java 8 onwards)
// cannot create objects of an interface so does not contain constructor also
public interface Animal {
    void makeSound(); // (abstract method) Methods in interfaces are public and abstract by default. so writing public abstract void makeSound() is redundant
    int VALUE = 100; //Variables in interfaces are public, static, and final by default.
}
class Dog implements Animal { //implements rather than extends
    @Override
    public void makeSound() {
        System.out.println("Dog barks");
    }
}
public class Main {
    public static void main(String[] args) {
        Dog myDog = new Dog(); // Using interface reference
        myDog.makeSound(); // Dog barks
        System.out.println(Dog.VALUE); // 100
        System.out.println(Animal.VALUE); // 100
    }
}

//From Java 8 onwards, interfaces can have default and static methods with implementations.
//default method - Used to provide a default implementation, so classes don’t have to override it.
public interface Animal {
    public void eat();
    default void sleep() { // Default method with implementation
        System.out.println("Sleeping...");
        //since default method can be accessed by objects of implementing classes, this keyword can be used here
        this.eat();
    }
}
public class Dog implements Animal {
    @Override
    public void eat() {
        System.out.println("Eating...");
    }
}
public class Main {
    public static void main(String[] args) {
        Animal.sleep(); // ❌ Error: Unlike static methods, default methods require an object to be executed because they can use this, access instance-specific behavior like eat(), etc.
        Dog d = new Dog();
        d.sleep(); // Sleeping... Eating...
        //default methods can be accessed without overriding them implementing classes
    }
}
//static method - Can be called directly using the interface name.
//Static methods cannot be overridden by implementing classes (belong to the interface, not to implementing classes).
public interface Animal {
    static void info() { // Static method inside an interface
        System.out.println("This is an animal interface");
    }
}
public class Dog implements Animal {
    // static void info() { // ✅ Hiding, NOT overriding
    //     System.out.println("This is Dog class (method hiding)");
    // }
    //Static methods in interfaces are not inherited
}
public class Main {
    public static void main(String[] args) {
        Animal.info(); // Output: This is an animal interface
        Dog.info(); // ❌ Error : Static methods in interfaces are not inherited by implementing classes.
        // If you try Dog.info(); without defining a static method inside Dog, you'll get a compile-time error
        // This is because static methods belong to the interface, not to the implementing class.
    }
}

//Interfaces can achieve multiple inheritance
public interface Animal {
    void eat();
}
public interface Pet {
    void play();
}
class Dog implements Animal, Pet {
    public void eat() {
        System.out.println("Dog eats");
    }
    public void play() {
        System.out.println("Dog plays");
    }
}
public class Main {
    public static void main(String[] args) { //main method can be written in an interface as it is static
        Dog d = new Dog();
        d.eat();  // Dog eats
        d.play(); // Dog plays
    }
}

//diff btw classes and interfaces
| Feature               | Class                                                     | Interface                                                    |
|-----------------------|----------------------------------------------------------|--------------------------------------------------------------|
| Keyword               | `class` keyword is used.                                  | `interface` keyword is used.                                 |
| Instantiation         | Objects of a class can be created.                        | Cannot be instantiated directly. Must be implemented by a class. |
| Inheritance           | Does not support multiple inheritance.                    | Supports multiple inheritance.                               |
| Inheritance Mechanism | `extends` keyword is used to inherit a class.            | `implements` keyword is used by a class to implement an interface. |
|                       |                                                          | An interface can inherit another interface using `extends`.  |
| Constructors          | Can have constructors.                                    | Cannot have constructors.                                    |
| Methods               | Can have abstract, concrete, or both.                      | Before Java 8: Only abstract methods (default).             |
|                       |                                                          | From Java 8: Can have default and static methods.           |
| Access Specifiers     | Methods and variables can be `public`, `private`,`protected`, or `default`. | Methods are public by default.                              |
| Variables             | Can have static, final, or instance variables.             | All variables are public, static, and final (constants).     |
| Purpose               | Defines an object's behavior and state.                    | Defines a contract that multiple classes must follow.       |
