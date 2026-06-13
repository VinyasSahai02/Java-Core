//COLLECTIONS CONTINUED
//Iterable interface
//Iterator
//Any class that implements Iterable can be used in a for-each loop
ArrayList<Integer> list = new ArrayList<>();
for (int i : list) { //for each loop can be used here as ArrayList implements Iterable, and has defined the implementation of iterator() method
    System.out.println(i);
}
//the above code is translated internally by java to the following code -> basically code working in for-each loop
Iterator<Integer> iterator = list.iterator();
while (iterator.hasNext()) {
    System.out.println(iterator.next());
}
// Iterator Methods:
// hasNext() - Returns true if the iteration has more elements.
// next() - Returns the next element in the iteration.
// remove() - Removes the current element from the collection.
List<Integer> numbers = new ArrayList<>();
numbers.add(1);
numbers.add(2);
numbers.add(3);
for (Integer number : numbers) {
    if (number % 2 == 0) {
        numbers.remove(number); //This will throw ConcurrentModificationException
    }
    //For ConcurrentModificationException, the collection must be modified by the same thread that is iterating over it. If a different thread modifies the collection, it will throw ConcurrentModificationException.
    //we can use CopyOnWriteArrayList to avoid ConcurrentModificationException also and the element will be removed after the iteration is complete.
    //but if you want to remove the element while iterating, you can use Iterator.
}
Iterator<Integer> itr = numbers.iterator();
while (itr.hasNext()) {
    Integer number = itr.next();
    if (number % 2 == 0) {
        itr.remove();
    }
}
System.out.println(numbers); //Output: [1, 3]
//Use Iterator rather than for-each:
//1.for-each doesn’t allow modifying (remove) collection safely while iterating.
//2.Iterator helps avoid ConcurrentModificationException.
ListIterator<Integer> listIterator = numbers.listIterator(); //ListIterator is a sub-interface of Iterator and allows bidirectional traversal of the list -> has more methods than Iterator
while (listIterator.hasNext()){
    listIterator.next();
// listIterator.set(); -> replaces the last element returned by next() with the specified element
}

// Streams and Java 8 Basics
// Java 8 --> minimal code(if-else not req, loop in 1 statement rather than for-each), functional programing(function interface etc)
// Java 8 --> lambda expression, Streams, Date & Time API
// lambda expression
// lambda expression is an anonymous function ( no name, no return type, no access modifier )
Thread t1 = new Thread(() -> {
    System.out.println("Hello");
});
//is better than this
Thread t2 = new Thread(new Task());
Class Task implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello");
    }
}

//if there was a interface like this 
interface MathOperation {
    int operate(int a, int b);
}
//we would have to implement the interface like this
class Addition implements MathOperation {
    @Override
    public int operate(int a, int b) {
        return a + b;
    }
}
//and similarly for subtraction, multiplication, division etc.
//but we know that the interface is functional interface as there is only one single abstract method(SAM) in it
//so we can use lambda expression to implement the interface like this
@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}
MathOperation sumOperation = (a, b) -> a + b; //(a, b) -> {return a + b;} is also valid
MathOperation subtractOperation = (a, b) -> a - b;
int res = sumOperation.operate(1, 2);
System.out.println(res);

// Predicate --> Functional interface ( Boolean valued function )
//if you want to check something use this
Predicate<Integer> isEven = x -> x % 2 == 0; //predicate holds a condition, thats it
System.out.println(isEven.test(4)); //Test() is the abstract method in prdicate interface. using the boolean valued function -> can be used anywhere
Predicate<String> isWordStartingWithA = x -> x.toLowerCase().startsWith("a");
Predicate<String> isWordEndingWithT = x -> x.toLowerCase().endsWith("t");
Predicate<String> combine = isWordStartingWithA.and(isWordEndingWithT); //combining predicates using and() method. this is a default(not abstract or static) method in predicate interface.
// there arre other method also like or(), negate()-opposite of the condition
System.out.println(combine.test("Akshay"));

// Function (functional interface) --> work for you
//apply() method is the abstract method in function interface
Function<Integer, Integer> doubleIt = x -> 2 * x; //<Takes, Returns>
Function<Integer, Integer> tripleIt = x -> 3 * x;
System.out.println(doubleIt.apply(20)); // 40
System.out.println(doubleIt.andThen(tripleIt).apply(20)); //andThen() method is a default method in function interface. it takes the result of the first function and passes it to the second function. this is used for chaining functions.
System.out.println(tripleIt.andThen(doubleIt).apply(20)); // same as below
System.out.println(doubleIt.compose(tripleIt).apply(20)); // same as above
Function<Integer, Integer> identity = Function.identity(); //identity() method is a static method in function interface. so we need to call it using interfaceName.identity()
Integer res2 = identity.apply(5); //identity function returns the same value as input. this is used when you want to pass a function but dont want to change the value.
System.out.println(res2); // 5

// Consumer(functional interface)
// it takes an input and does not return anything. it is used for performing some operation on the input.
// it also has default methods like andThen() etc.
Consumer<Integer> print = x -> System.out.println(x); //not returning anything just using it to print the value
print.accept(51); // accept() is the abstract method in consumer interface.
List<Integer> list = Arrays.asList(1, 2, 3);
Consumer<List<Integer>> printList = x -> {
    for (int i : x) {
        System.out.println(i);
    }
};
printList.accept(list);

// Supplier(functional interface) 
// it does not take any input and returns a value. it is used for generating values.
Supplier<String> giveHelloWorld = () -> "Hello World";
System.out.println(giveHelloWorld.get());

// combined example
Predicate<Integer> predicate = x -> x % 2 == 0;
Function<Integer, Integer> function = x -> x * x;
Consumer<Integer> consumer = x -> System.out.println(x);
Supplier<Integer> supplier = () -> 100;

if (predicate.test(supplier.get())) {
    consumer.accept(function.apply(supplier.get())); //10000
}

// BiPredicate, BiConsumer, BiFunction
// these take 2 inputs
BiPredicate<Integer, Integer> isSumEven = (x, y) -> (x + y) % 2 == 0;
System.out.println(isSumEven.test(5, 5));
BiConsumer<Integer, String> biConsumer = (x, y) -> {
    System.out.println(x);
    System.out.println(y);
};
BiFunction<String, String, Integer> biFunction = (x, y) -> (x + y).length(); //<input, input, Returns>
System.out.println(biFunction.apply("a", "bc"));

// UnaryOperator, BinaryOperator(both extend Function)
UnaryOperator<Integer> a = x -> 2 * x; //same as Function<Integer, Integer> a = x -> 2 * x;
BinaryOperator<Integer> b = (x, y) -> x + y; //same as BiFunction<Integer, Integer, Integer> b = (x, y) -> x + y;

// Method reference --> use method without invoking & in place of lambda expression
List<String> students = Arrays.asList("Ram", "Shyam", "Ghanshyam");
students.forEach(x -> System.out.println(x)); //.forEach(Consumer)
//instead of using lambda expression we can use method reference like this -> sonarlint will give this suggestion
students.forEach(System.out::println); //replaced --> giving the method reference of println method of System class.
// this basically means that for each student go to System class then go to Out class then go to println method and print the student name

// Constructor reference
class MobilePhone{
    String name;
    public MobilePhone(String name) {
        this.name = name;
    }
}
List<String> names = Arrays.asList("A", "B", "C");
List<MobilePhone> mobilePhoneList = names.stream().map(x -> new MobilePhone(x)).collect(Collectors.toList()); //using lambda expression to create MobilePhone object
//instead of using lambda expression we can use constructor reference like this -> sonarlint will give this suggestion
List<MobilePhone> mobilePhoneList = names.stream().map(MobilePhone::new).collect(Collectors.toList());

//Streams(Interface)
//up until now we were building the base to learn Streams
public static void main(String[] args) {
    // feature introduced in Java 8
    // process collections of data in a functional and declarative manner
    // Simplify Data Processing
    // Embrace Functional Programming
    // Improve Readability and Maintainability
    // Enable Easy Parallelism(without deadling with complexicity of multithreading)

    // What is stream ?
    // a sequence of elements supporting functional and declarative programing
    // Streams provide a powerful way to process collections of data declaratively (think what to do, not how to do it).
    // does not store data, just processes it.

    //// How to Use Streams ?
    // Source -> intermediate operations -> terminal operation

    // Streams are one-time use only.
    // Once a terminal operation is performed, the stream is consumed.
    // Avoid using them with mutable shared state in parallel streams.

    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5); //if we want to do declarative, functional programming on any collection we need to convert it to stream first
    //without stream
    int count = 0;
    for (Integer number : numbers) {
        if (number % 2 == 0) {
            count++;
        }
    }
    System.out.println(count); //Output: 2
    // Stream<Integer> stream = numbers.stream(); //creating a stream from the collection
    //With Stream
    System.out.println(numbers.stream().filter(x -> x % 2 == 0).count()); //Output: 2

    //// Creating Streams
    // 1. From collections
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
    Stream<Integer> stream = list.stream();
    // 2. From Arrays
    String[] array = {"a", "b", "c"};
    Stream<String> stream1 = Arrays.stream(array);
    // 3. Using Stream.of()
    Stream<String> stream2 = Stream.of("a", "b");
    // 4. Infinite streams
    Stream.generate(() -> 1); //generate(Supplier<T> s) -> creates an infinite stream of the same value. Supplier does not take anything and returns a value.
    Stream.generate(() -> 1).limit(10); //limit() method is used to limit the number of elements in the stream.
    Stream.iterate(1, x -> x + 1); //iterate(Seed<T> seed, UnaryOperator<T> f) -> creates an infinite stream of elements generated by the function. Seed is the starting point and f is the function to generate the next element.
    Stream.iterate(1, x -> x + 1).limit(10).collect(Collectors.toList()); //1 2 3 4 5 6 7 8 9 10
    //collect() method is used to collect the elements of the stream into a collection. Collectors is a utility class that provides various methods to collect the elements of the stream.
    //we basically converted out stream to a list using collect() method and Collectors.toList() method.
}

// Intermediate operations transform a stream into another stream
// They are lazy, meaning they don't execute until a terminal operation is invoked.
// 1. filter(Predicate)
List<String> list = Arrays.asList("Akshit", "Ram", "Shyam", "Ghanshyam", "Akshit");
Stream<String> filteredStream = list.stream().filter(x -> x.startsWith("A"));
// no filtering at this point
long res = list.stream().filter(x -> x.startsWith("A")).count();
System.out.println(res);

// 2. map(Function) - You want to transform each item independently.
Stream<String> stringStream = list.stream().map(String::toUpperCase); // .map(x -> x.toUpperCase()) is also valid

// 3. sorted
Stream<String> sortedStream = list.stream().sorted(); //natural order
Stream<String> sortedStreamUsingComparator = list.stream().sorted((a, b) -> a.length() - b.length()); //comparator

// 4. distinct
System.out.println(list.stream().filter(x -> x.startsWith("A")).distinct().count());

// 5. limit
System.out.println(Stream.iterate(1, x -> x + 1).limit(100).count()); // 1 to 100 --> 100

// 6. skip
System.out.println(Stream.iterate(1, x -> x + 1).skip(10).limit(100).count()); // 11 to 110 --> 100x

// 7. peek
// Performs an action on each element as it is consumed
// similar to forEach but it is an intermediate operation
Stream.iterate(1, x -> x + 1).skip(10).limit(100).peek(System.out::println).count();

// 8. flatMap
// Handle streams of collections, lists, or arrays where each element is itself a collection -> collection of collection
// Flatten nested structures (e.g., lists within lists) so that they can be processed as a single sequence of elements
// Transform and flatten elements at the same time.
List<List<String>> listOfLists = Arrays.asList(
    Arrays.asList("apple", "banana"),
    Arrays.asList("orange", "kiwi"),
    Arrays.asList("pear", "grape")
);
System.out.println(listOfLists.get(1).get(1)); //kiwi
System.out.println(listOfLists.stream().flatMap(x -> x.stream()).map(String::toUpperCase).toList()); //[APPLE, BANANA, ORANGE, KIWI, PEAR, GRAPE]
List<String> sentences = Arrays.asList(
    "Hello world",
    "Java streams are powerful",
    "flatMap is useful"
);
System.out.println(sentences
        .stream()
        .flatMap(sentence -> Arrays.stream(sentence.split(" "))) //sentence.split(" ") returns an array
        .map(String::toUpperCase)
        .toList()); //[HELLO, WORLD, JAVA, STREAMS, ARE, POWERFUL, FLATMAP, IS, USEFUL]

//Terminal operations
List<Integer> list = Arrays.asList(1, 2, 3);
// 1. collect
list.stream().skip(1).collect(Collectors.toList()); // 
list.stream().skip(1).collect(Collectors.toSet()); //to set .... etc and may more
list.stream().skip(1).toList(); //Alternatively, you can use the toList() -> accumulate the elements into a List and the List is unmodifiable

// 2. forEach(Consumer)
list.stream().forEach(x -> System.out.println(x));

// 3. reduce(BinaryOperator<Integer> accumulator) : Combines elements to produce a single result
Optional<Integer> optionalInteger = list.stream().reduce(Integer::sum); //or .reduce((a, b) -> a + b);
System.out.println(optionalInteger.get());

// 4. count

// 5. anyMatch(Predicate), allMatch(Predicate), noneMatch(Predicate)
boolean b = list.stream().anyMatch(x -> x % 2 == 0);
System.out.println(b); // true -> when any element matches the condition
boolean b1 = list.stream().allMatch(x -> x > 0);
System.out.println(b1); // true -> when all elements match the condition
boolean b2 = list.stream().noneMatch(x -> x < 0);
System.out.println(b2); // true -> when no element matches the condition

// 6. findFirst, findAny
System.out.println(list.stream().findFirst().get()); //findFirst() returns the first element in the stream if present, otherwise returns an empty Optional. -> 1
//System.out.println(list.stream().findAny()); // Optional[1]
System.out.println(list.stream().findAny().get());

// 7. toArray() - Converts the stream to an array
Object[] array = Stream.of(1, 2, 3).toArray();

// 8. min / max - Returns the minimum or maximum element in the stream based on a comparator
System.out.println("max: " + Stream.of(2, 44, 69).max(Comparator.naturalOrder())); // Optional[69]
System.out.println("min: " + Stream.of(2, 44, 69).min(Comparator.naturalOrder())); // Optional[2]
System.out.println("max: " + Stream.of(2, 44, 69).max((o1, o2) -> o2 - o1)); // o2 - o1 -> min , o1 - o2 -> max

// 9. forEachOrdered
List<Integer> numbers0 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
System.out.println("Using forEach with parallel stream:");
numbers0.parallelStream().forEach(System.out::println); // 10 7 1 3 2 4 5 6 8 9
System.out.println("Using forEachOrdered with parallel stream:");
numbers0.parallelStream().forEachOrdered(System.out::println); // 1 2 3 4 5 6 7 8 9 10

// Example: Filtering and Collecting Names
List<String> names = Arrays.asList("Anna", "Bob", "Charlie", "David");
System.out.println(names.stream().filter(x -> x.length() > 3).toList()); // [Anna, Charlie, David]

// Example: Squaring and Sorting Numbers
List<Integer> numbers = Arrays.asList(5, 2, 9, 1, 6);
System.out.println(numbers.stream().map(x -> x * x).sorted().toList()); // [1, 4, 25, 36, 81]

// Example: Summing Values
List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
System.out.println(integers.stream().reduce(Integer::sum).get()); // 15

// Example:  Counting Occurrences of a Character
String sentence = "Hello world";
System.out.println(sentence.chars().filter(x -> x == 'l').count()); // 3

//Streams dont execute until a terminal operation is invoked. This is called lazy evaluation.
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
Stream<String> stream = names.stream()
        .filter(name -> {
            System.out.println("Filtering: " + name);
            return name.length() > 3;
        });

System.out.println("Before terminal operation");
List<String> result = stream.collect(Collectors.toList()); // terminal operation
System.out.println("After terminal operation");
System.out.println(result);
// Before terminal operation
// Filtering: Alice
// Filtering: Bob
// Filtering: Charlie
// Filtering: David
// After terminal operation
// [Alice, Charlie, David]

// Streams cannot be reused after a terminal operation has been called
Stream<String> stream = names.stream();
stream.forEach(System.out::println); // Alice Bob Charlie David
List<String> list1 = stream.map(String::toUpperCase).toList(); // exception

// stateful & stateless
