//Generics
// Without Generics
    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add("Hello");
        list.add(123);
        list.add(3.14);
        String str = (String) list.get(0); //(string) is required as list.get() gives object type and we want to store it in string type
        String str1 = (String) list.get(1); //we have int but we cast into string but shows no error
    }
    //compile time error will not be shown but runtime error will be shown
    // Exception in thread "main" java.lang.ClassCastException: class java.lang.Integer cannot be cast to class java.lang.String (java.lang.Integer and java.lang.String are in module java.base of loader 'bootstrap') at com.engineeringdigest.corejava.Main.main(Main.java:13)
// Above code has 3 major issues:
// 1.No Type safety
// 2.Manual casting
// 3.No Compile Time checking
public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Hello");
        list.add(12); //error
        String s = list.get(0);
}

//another example
public class Box {
    private Object value;
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
}
public static void main(String[] args) {
    Box box = new Box();
    box.setValue(1);
    String i = (String) box.getValue(); // EXCEPTION at runtime -> //ClassCastException: java.lang.Integer cannot be cast to java.lang.String
    //so we need to add generic to object in Box class
    //before that we need to learn generic types
    System.out.println(i);
}
//Generic Types
//allow you to define a class,interface or method with a placeholder for the type of data it operates on.
//Generic Class
public class Box<T>{
    private T value;
    public T getValue() {
        return value;
    }
    public void setValue(T value) {
        this.value = value;
    }
}
public static void main(String[] args) {
    Box<Integer> box = new Box<>();
    box.setValue(1);
    int i = box.getValue();
    System.out.println(i);
}
// Generics help us write more flexible and reusable code.
// For example, without generics, we would have to write multiple versions of the same class to handle different data types, leading to code duplication
class Pair<K, V> { //class can have more than one parameter
    private K key;
    private V value;
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    public K getKey() {
        return key;
    }
    public V getValue() {
        return value;
    }
}
public static void main(String[] args) {
    Pair<String, Integer> pair = new Pair<>("Age", 30);
    System.out.println("Key: " + pair.getKey());   // Prints: Key: Age
    System.out.println("Value: " + pair.getValue()); // Prints: Value: 30
}
//While you can name type parameters anything, the convention is to use single letters that describe the purpose of the type parameter:
// T: Type, E: Element (used in collections), K: Key (used in maps), V: Value (used in maps), N: Number

//Generic Interface
interface Container<T> {
    void add(T item);
    T get();
}
//When you implement a generic interface, you need to specify the type for the generic parameter, or you can continue to make the implementation generic by using type parameters.
//Implementing with a specific type:
class StringContainer implements Container<String> {
    private String item;
    @Override
    public void add(String item) {
        this.item = item;
    }
    @Override
    public String get() {
        return item;
    }
}
//Implementing a generic interface generically:
class GenericContainer<T> implements Container<T> {
    private T item;
    @Override
    public void add(T item) {
        this.item = item;
    }
    @Override
    public T get() {
        return item;
    }
}
//Generic Interfaces with Multiple Type Parameters
interface Pair<K, V> {
    K getKey();
    V getValue();
}
class KeyValuePair<K, V> implements Pair<K, V> {
    private K key;
    private V value;
    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    @Override
    public K getKey() {
        return key;
    }
    @Override
    public V getValue() {
        return value;
    }
}

//Bounded Type Parameters
//you can use bounded type parameters in generic interfaces to restrict the types that can be used as arguments.
interface NumberContainer<T extends Number> { //T is restricted to subclasses of Number, so only numeric types like Integer, Double, etc., can be used.
    void add(T item);
    T get();
}
class IntegerContainer implements NumberContainer<Integer> {
    private Integer item;
    @Override
    public void add(Integer item) {
        this.item = item;
    }
    @Override
    public Integer get() {
        return item;
    }
}//Attempting to implement the NumberContainer interface with a non-numeric type (like String) would result in a compile-time error.

//Generics in Enums
//Enums are inherently type-safe. You cannot assign a value to an enum that is not part of the defined constants.
enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
}
Day day = Day.MONDAY; // Type-safe
// Day day = "MONDAY"; // Compile-time error
//However, enums alone do not support generics. To add type parameters to an enum, you need to define generic methods or use enums with generic classes or interfaces.


//Generic Constructors
public class Box<T>{
    private T value;
    public Box(T value) { //Generic constructor
        this.value = value;
    }
    //if the class is not generic but we want to create a generic constructor, we can do that too
    public <T> Box(T value) { //Generic constructor
        this.value = (T) value; //Type casting to T
    }
}

//Generic Methods
public class GenericMethodExample {
    public <T> void printArray(T[] array) {  // Generic method
    //for multiple type-> public <T, U> void printTwoItems(T item1, U item2){}
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
    //we can overload generic methods
    public <T> void printArray(T[] array, String prefix) { } // Overloaded generic method
    public static void main(String[] args) {
        GenericMethodExample example = new GenericMethodExample();
        
        Integer[] intArray = {1, 2, 3, 4, 5};
        String[] stringArray = {"A", "B", "C", "D"};

        example.printArray(intArray);   // Output: 1 2 3 4 5
        example.printArray(stringArray); // Output: A B C D
    }
}
//generic methods on enums
enum Operation{
    ADD, SUBTRACT, MULTIPLY, DIVIDE;
    public <T extends Number> double apply(T a, T b){
        switch (this) {
            case ADD:
                return a.doubleValue() + b.doubleValue();
            case SUBTRACT:
                return a.doubleValue() - b.doubleValue();
            case MULTIPLY:
                return a.doubleValue() * b.doubleValue();
            case DIVIDE:
                return a.doubleValue() / b.doubleValue();
            default:
                throw new UnsupportedOperationException("Operation not supported");
        }
    }
}
public static void main(String[] args) {
    Operation.ADD.apply(5, 3);
}

// Wildcards in Generics
// wildcards(?) are special kind of type arguments that can be used in method arguments or class definitions to represent an unknown type
//they allow for more flexible and dynamic code by letting the type be specified later or be more loosely defined
public void printList(List<?> list) { //used when type is not important
// a method that can accept collections of different types (e.g., List<String>, List<Integer>) without being tied to a specific type.
    for (Object element : list) {
        System.out.println(element);
    }
}
//One key limitation when using wildcards is that they are generally used in a read-only context and when we are not returning anything
// This means that while you can read elements from the collection, you cannot modify it in a type-safe manner.
public void copy(List<?> source, List<?> destination) {
    for (Object element : source) {
        destination.add(element); //Error
    }
}
ArrayList<?> list = new ArrayList<>();
list.add("Hello"); //Error
list.add(null) //no error

//Upper Bounded Wildcards
// <? extends SomeClass> - This means that the type parameter can be any type that is SomeClass or a subclass of SomeClass.
public static double sum(List<? extends Number> numbers) { //calculates the sum of numbers in a list. You want this method to work for any type of numbers like Integer, Double, etc.
    // List<? extends Number> allows the sum method to only accept a List of any type that extends Number, such as Integer, Double, Float, etc. -> so restrict the type of args a method can accept
    double total = 0.0;
    for (Number number : numbers) {
        total += number.doubleValue();
    }
    return total;
}

//Lower Bounded Wildcards
// <? super SomeClass> - This means that the type parameter can be any type that is SomeClass or a superclass of SomeClass.
// For example, List<? super Integer> can accept Integer, Number, or Object (since Integer is a subclass of Number, which is a subclass of Object).
public static void addNumbers(List<? super Integer> list) {}
public static void addNumbers(List<? super Number> list) {
    list.add(10); //You can add objects of the specified type or any of its subclasses.
    list.add(1.5)
}
//When using lower-bounded wildcards, you lose the ability to retrieve elements of a specific type because you don’t know the exact type at runtime.
public static void processList(List<? super Number> list) {
    // We cannot retrieve a specific type from the list.
    Number num = list.get(0); // ❌ Compile-time error
    Object obj = list.get(0);  // This is the only safe type to retrieve.
}

//How Generics Work (Type Erasure)
// Generics in Java are implemented through a process called type erasure.
// This means that the generic types are replaced with their bounds or Object during compilation, and the resulting bytecode contains only ordinary classes, methods, and fields.
//OR
// the process by which the compiler removes generic type information after checking it at compile time. This means generic types are not available at runtime — the Java Virtual Machine (JVM) operates on non-generic types only.
//Consider the following generic class Box<T> and a specific instantiation where T is replaced with String:
public class Box<T> {
    private T item;
    public void setItem(T item) {
        this.item = item;
    }
    public T getItem() {
        return item;
    }
}
public class Main {
    public static void main(String[] args) {
        Box<String> stringBox = new Box<>();
        stringBox.setItem("Hello, Generics!");
        String item = stringBox.getItem();
        System.out.println(item);
    }
}
//When you instantiate Box<String>, the Java compiler checks the type at compile-time and ensures that at compile time only String objects are assigned to the Box.
//However, during compilation, this type information (T = String) is erased
// the generic type T is replaced with its bound, which defaults to Object if no explicit bound is specified.
//After type erasure, the generic class Box<T> is transformed by the compiler into the following code at Runtime:
public class Box { //basically bytecode
    private Object item;  // `T` is replaced with `Object`
    //if it was <? extends Number> then it will be replaced with Number not object
    public void setItem(Object item) {
        this.item = item;
    }
    public Object getItem() {
        return item;
    }
}
public class Main {
    public static void main(String[] args) {
        Box stringBox = new Box();
        stringBox.setItem("Hello, Generics!");
        String item = (String) stringBox.getItem();
        System.out.println(item);
    }
}
//At runtime, there is no knowledge of the type T being String. The class Box only knows that it stores an Object, and all operations on the generic type are done as if it were an Object. 
// However, the code that interacts with Box<String> still works safely because the compiler has enforced the correct type usage at compile-time.

//Generic Exceptions
// Java does not allow generic exceptions.
