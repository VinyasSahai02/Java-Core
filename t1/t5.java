//INNER CLASSES
// 4 types of inner classes:
// 1. Member Inner class
// A non-static inner class inside an outer class.
// It can access all members of the outer class, including private ones.
//use this when -> You don’t want the inner class to be used independently and When the inner class should always be associated with an instance of the outer class.
public class Outer {
    private String message = "Hello from Outer!";

    class Inner { //member class
        void display() {
            System.out.println(message); // Can access outer class private members
        }
    }
    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner(); // Creating inner class object with the help of instance of Outer class
        inner.display(); // Output: Hello from Outer!
    }
}
//without inner class this would look like 
public class Outer {
    private String message = "Hello from Outer!";
    public String getMessage() {
        return message;
    }
}
class Inner {
    private Outer outer; // Reference to Outer class
    Inner(Outer outer)  { // Constructor to accept Outer class instance
        this.outer = outer;
    }
    void display() {
        System.out.println(outer.getMessage()); // Accessing Outer class's private member via getter
    }
}
public class Main {
    public static void main(String[] args) {
        Outer outer = new Outer();  // Creating instance of Outer class
        Inner inner = new Inner(outer); // Passing Outer instance to Inner
        inner.display();  // Output: Hello from Outer!
    }
}

// 2. Static Nested class
// Declared static inside an outer class.
// It cannot access non-static members of the outer class.
//Use this when -> The inner class can function independently and You want better memory efficiency since a static class doesn’t require an instance of the outer class.
class Outer {
    static String message = "Hello from Outer!";
    static class Inner {
        void display() {
            System.out.println(message);
        }
    }
    public static void main(String[] args) {
        //In inner memmber class, inner class is associated with instance of outer class
        //But here Static Inner class belong to the outer class rather than its instance
        Outer.Inner inner = new Outer.Inner(); // No need to create Outer class object
        inner.display(); // Output: Hello from Outer!
    }
}

// 3. Local Inner class
// Defined inside a method.
// Cannot have static members.
// Use this when: The inner class is only needed inside a specific method and You want to restrict the scope of the class to the method.
//Useful in encapsulation
class Outer {
    void outerMethod() {
        class LocalInner { //this class can access private members of outer class
            void display() {
                System.out.println("Inside Local Inner Class");
            }
        }
        LocalInner inner = new LocalInner();
        inner.display();
    }
    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.outerMethod(); // output: Inside Local Inner Class
    }
}

// 4. Anonymous Inner class
// A class without a name.
// Used when we need to implement an interface or class without creating its implementation class or subclass
//Use this when-> You need a class for one-time use. Mostly used for callbacks, event handling, and threads.
abstract class Animal {
    abstract void sound();
}
public class Main {
    public static void main(String[] args) {
        Animal dog = new Animal() { // Anonymous Inner Class
        @Override
            public void sound() {
                System.out.println("Dog barks");
            }
        };
        dog.sound();
    }
}


//EXCEPTIONS
// types of error-> Syntax(syntax hee galat hai), Logical, Runtime(divide by 0)
//exception handling handles runtime errors so that normal flow of application can be maintained
//exception is an event that disrupts normal flow of program. IT IS AN OBJECT WHICH IS THROWN AT RUNTIME

//Types of Exceptions
// 1. Checked exceptions
// these exceptions are checked at compile-time by the compiler.
// The compiler forces you to handle these exceptions using try-catch or throws
// ClassNotFoundException: Throws when the program tries to load a class at runtime but the class is not found because it’sbelong not present in the correct location or it is missing from the project.
// InterruptedException: Thrown when a thread is paused and another thread interrupts it.
// IOException: Throws when input/output operation fails
// InstantiationException: Thrown when the program tries to create an object of a class but fails because the class is abstract, an interface, or has no default constructor.
// SQLException: Throws when there’s an error with the database.
// FileNotFoundException: Thrown when the program tries to open a file that doesn’t exist

// 2. Unchecked exceptions
// these exceptions are not checked at compile-time.
// The compiler does not force handling them.
// ArithmeticException: It is thrown when there’s an illegal math operation.
// ClassCastException: It is thrown when you try to cast an object to a class it does not belongThis to.
// NullPointerException: It is thrown when you try to use a null object (e.g. accessing its methods or fields)
// ArrayIndexOutOfBoundsException: ThisThis occurs when we try to access an array element with an invalid index.
// ArrayStoreException: Thishandle happens when you store an object of the wrong type in an array.
// IllegalThreadStateException: It is thrown when a thread operation is not allowed in its current state

//3. Errors
// Errors indicate serious problems that should not be handled
// Example: StackOverflowError, OutOfMemoryError.

class Geeks {
    public static void main(String[] args){
        int n = 10;
        int m = 0;
        try {
            int ans = n / m; //"exception in thread, java.lang.ArithematicException: / by zero"
            System.out.println("Answer: " + ans);
        }
        catch (ArithmeticException e) {//object of ArithematicException is created and passed to catch block
            System.out.println(e); //java.lang.ArithmeticException: / by zero
            //this output gets printed because ArithematicException class runs its toString(obj) method
            //whenever we print an obj, its class runs toString() method
            Student student = new Student()
            System.out.println(student); //Student@6d06d69c
            //this output gets printed because Student class runs its toString(obj) method
        }
        finally {
            System.out.println(
                "Program continues after handling the exception.");
                //this will always run, whether try runs or catch runs or both run
                //this is used to free resources like closing files, database connections, etc.
        }
    }
}

//Class Hierarchy of Exception Handling
//All exception and error types are subclasses of the class Throwable, which is the base class of the hierarchy.
            Object
              │
         Throwable
         /       \
   Exception    Error
       │          |  
       |          Virtual Machine Error
       │          Linage Error
Checked & Unchecked Exceptions
//in the above example, ArithmeticException->RuntimeException->Exception->Throwable
//ArithmeticException constructor is called, then runtimeexception constructor is called, then Exception constructor is called, then Throwable constructor is called
//Throwable class constructor asigns message to a class variable and then runs the toString() method
catch (Exception e) { //ArithematicException is a subclass of Runtime, Exception, Throwable, it can be written like this too
            System.out.println(e);
            Student student = new Student()
            System.out.println(student); 
}

//Multiple catch blocks
try{
    int a = 10, b = 0;
    int c = a / b;
    System.out.println("Result: " + c);
} catch (ArithmeticException e) {
    System.out.println("Arithmetic Exception: " + e.getMessage());
} catch (NullPointerException e) {
    System.out.println("Null Pointer Exception: " + e.getMessage());
} catch (Exception e) { //parent class cannot be at the top
    System.out.println("Exception: " + e.getMessage());
}

//stack trace
// A stack trace in Java is a report that shows the sequence of method calls that led to an exception or error.
// It helps debug and trace the exact location where an issue occurred in the program
public class Main {
    public static void methodA() {
        methodB();
    }
    public static void methodB() {
        int result = 10 / 0; // ArithmeticException (Division by zero)
    }
    public static void main(String[] args) {
        methodA();
    }
}
//Output Stack Trace
Exception in thread "main" java.lang.ArithmeticException: / by zero //type of exception
    at Main.methodB(Main.java:8) //error happened at line 8 in methodB().
    at Main.methodA(Main.java:4) //methodB() was called by methodA(), which is at line 4
    at Main.main(Main.java:12)  //methodA() was called by main() at line 12
//  method calls in reverse order (from the method where the error occurred to the main() method).

//to print a stack trace
public class Main {
    public static void main(String[] args) {
        try {
            int num = 10 / 0;
        } catch (Exception e) {
            e.getStackTrace();
            //Alt + enter
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                System.out.println(stackTrace[i]);
            }
            //OR
            e.printStackTrace(); //same output as above
        }
    }
}

//throw keyword
// Used to explicitly throw an exception.
public class Main {
    public static void checkAge(int age) {
        if (age < 18) {
            throw new IllegalArgumentException("Age must be 18 or above");
        }
        System.out.println("You are allowed to vote");
    }
    public static void main(String[] args) {
        checkAge(16); // Throws an exception
    }
}
// throws Keyword (Declare Exceptions)
//Used in method signatures to indicate that a method might throw an exception.
public class Main {
    public static void readFile() throws IOException {
        FileReader file = new FileReader("test.txt");
        file.read();
    }
    public static void main(String[] args) {
        try {
            readFile();
        } catch (IOException e) {
            System.out.println("File not found!");
        }
    }
}

public static void method1() throws FileNotFoundException {
    throw new FileNotFoundException(); //Checked exceptions (FileNotFoundException) must be declared (throws) or handled using try catch
}
public static void method1() {
    throw new FileNotFoundException(); // Compiler error
}
public static void method1() {
    throw new ArithmeticException(); //Unchecked exceptions (ArithmeticException) do not require throws and can be thrown directly.
}

//try with resources
// automatically close resources like files, sockets, or database connections
//introduced in Java 7 to eliminate the need for manually closing resources in the finally block.
// see example to learn more

//Custom Exceptions
public class BankAccount{
    private double balance;
    public BankAccount(double amount) {
        this.balance = amount;
    }
    public void withdraw(double amount) throws InsufficientFundsException{
        if(amount > balance){
            throw new InsufficientFundsException(amount);
        }
        balance -= amount;
    }
}
public class InsufficientFundsException extends Exception {
    private double amount;
    public InsufficientFundsException(double amount) {
        super("What are you doing? You don't have enough money!");  // Calls parent Exception constructor
        this.amount = amount;
    }
    public double getAmount() {
        return amount;
    }
    //if you dont want to use constructor for custom exception
    //you can also override toString() method in custom exception class like this:
    //comment super in constructor
    @Override
    public String toString(){
        return "What are you doing? You don't have enough money!"
    }
    //alothough using the constructor approach is recommended
}
public static void main(String[] args){
    BankAccount account = new BankAccount(1000);
    try {
        account.withdraw(1500); // Throws custom exception
    } catch (InsufficientFundsException e) {
        System.out.println(e.getMessage()); // Output: What are you doing? You don't have enough money!
        // System.out.println(e); // --> in case to toString() Output: What are you doing? You don't have enough money!
        e.getAmount(); // Output: 1500.0
    }
}