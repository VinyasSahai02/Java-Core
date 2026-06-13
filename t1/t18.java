// STREAMS CONTINUED
// Parallel Streams
public static void main(String[] args) {
    // A type of stream that enables parallel processing of elements
    // Allowing multiple threads to process parts of the stream simultaneously
    // This can significantly improve performance for large data sets
    // workload is distributed across multiple threads
    long startTime = System.currentTimeMillis();
    List<Integer> list = Stream.iterate(1, x -> x + 1).limit(20000).toList();
    List<Long> factorialsList = list.stream().map(x -> factorial(x)).toList();
    long endTime = System.currentTimeMillis();
    System.out.println("Time taken with sequential stream: " + (endTime - startTime) + " ms");

    startTime = System.currentTimeMillis();
    List<Integer> list = Stream.iterate(1, x -> x + 1).limit(20000).toList();
    factorialsList = list.parallelStream().map(ParallelStream::factorial).toList();
    endTime = System.currentTimeMillis();
    System.out.println("Time taken with parallel stream: " + (endTime - startTime) + " ms");

    // Parallel streams are most effective for CPU-intensive or large datasets where tasks are independent
    // They may add overhead for simple tasks or small datasets

    // Example - Cumulative Sum
    // [1, 2, 3, 4, 5] --> [1, 3, 6, 10, 15]
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
    AtomicInteger sum =  new AtomicInteger(0);
    // List<Integer> cumulativeSum = numbers.parallelStream().map(x -> {
    //     int i= x+sum; //Variable used in lambda expression should be final or effectively final. so use AtomicInteger
    //     sum=i;
    //     return i;
    // }).toList();
    List<Integer> cumulativeSum = numbers.parallelStream().map(x -> sum.addAndGet(x)).toList(); //lamda func is like this because of AtomicInteger
    System.out.println("Expected cumulative sum: [1, 3, 6, 10, 15]");
    System.out.println("Actual result with parallel stream: " + cumulativeSum);
    //this will not work with parallel stream as answer depends on previous answer
    // Parallel streams are not suitable for operations that depend on the order of elements or require shared mutable state
    List<Integer> cumulativeSum = numbers.stream().sequential().map(sum::addAndGet).toList();
    // sequential() converts the parallel stream back to a sequential stream
    //even without sequential() it will work
    System.out.println("Expected cumulative sum: [1, 3, 6, 10, 15]");
    System.out.println("Actual result with parallel stream: " + cumulativeSum);
}
private static long factorial(int n) {
    long result = 1;
    for (int i = 2; i <= n; i++) {
        result *= i;
    }
    return result;
}

//Collectors(utility class like Arrays, Collections, etc.)
// provides a set of methods to create common collectors
// 1. Collecting to a List
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
List<String> res = names.stream()
        .filter(name -> name.startsWith("A"))
        .collect(Collectors.toList());
System.out.println(res);

// 2. Collecting to a Set
List<Integer> nums = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
Set<Integer> set = nums.stream().collect(Collectors.toSet());
System.out.println(set);

// 3. Collecting to a Specific Collection
ArrayDeque<String> collect = names.stream().collect(Collectors.toCollection(() -> new ArrayDeque<>())); //toCollection(Supplier) -> supplier means kuch lega nhi, dega bass

// 4. Joining Strings
// Concatenates stream elements into a single String
String concatenatedNames = names.stream().map(String::toUpperCase).collect(Collectors.joining(", "));
System.out.println(concatenatedNames);

// 5. Summarizing Data
// Generates statistical summary (count, sum, min, average, max)
List<Integer> numbers = Arrays.asList(2, 3, 5, 7, 11);
IntSummaryStatistics stats = numbers.stream().collect(Collectors.summarizingInt(x -> x));
System.out.println("Count: " + stats.getCount());
System.out.println("Sum: " + stats.getSum());
System.out.println("Min: " + stats.getMin());
System.out.println("Average: " + stats.getAverage());
System.out.println("Max: " + stats.getMax());

// 6. Calculating Averages
Double average = numbers.stream().collect(Collectors.averagingInt(x -> x));
System.out.println("Average: " + average);

// 7. Counting Elements
Long count = numbers.stream().collect(Collectors.counting());
//OR
Long count = numbers.stream().count(); //count() is a terminal operation that returns the number of elements in the stream
System.out.println("Count: " + count);

// 8. Grouping Elements
List<String> words = Arrays.asList("hello", "world", "java", "streams", "collecting");
System.out.println(words.stream().collect(Collectors.groupingBy(String::length))); //groupingBy(Classifier) -> classifier means ki iske according group karde  {4=[java], 5=[hello, world], 7=[streams], 10=[collecting]}
System.out.println(words.stream().collect(Collectors.groupingBy(String::length, Collectors.joining(", ")))); // (Classifier, Collector) (length ke according group karne ke baad her group pe kuch karna hai) -> {4=java, 5=hello, world, 7=streams, 10=collecting}
System.out.println(words.stream().collect(Collectors.groupingBy(String::length, Collectors.counting()))); // {4=1, 5=2, 7=1, 10=1}
TreeMap<Integer, Long> treeMap = words.stream().collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.counting())); //grouping according to length , mapFactory(gives specific map implementation) , Collector(how to collect the elements)
System.out.println(treeMap); // {4=1, 5=2, 7=1, 10=1}

// 9. Partitioning Elements
//  Partitions elements into two groups (true and false) based on a predicate
System.out.println(words.stream().collect(Collectors.partitioningBy(x -> x.length() > 5))); // partitioningBy(Predicate) -> {false=[hello, world, java], true=[streams, collecting]}

// 10. Mapping and Collecting
// Applies a mapping function before collecting
System.out.println(words.stream().collect(Collectors.mapping(x -> x.toUpperCase(), Collectors.toList())));

// 11. toMap

// Example 1: Collecting Names by Length
List<String> l1 = Arrays.asList("Anna", "Bob", "Alexander", "Brian", "Alice");
System.out.println(l1.stream().collect(Collectors.groupingBy(String::length)));

// Example 2: Counting Word Occurrences
String sentence = "hello world hello java world";
System.out.println(Arrays.stream(sentence.split(" ")).collect(Collectors.groupingBy(x -> x, Collectors.counting())));
//my solution 
sentence.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting()))
//this will not work as sentence is a String, not a Stream. So you can't call .stream() on a String directly.
// so we turn the sentence into a stream of words

/*NOTE:
You cannot call .stream() directly on any type that does not implement the java.util.Collection interface. Like ->
String - It's a CharSequence, not a Collection
Primitive arrays - Like int[], double[] — they're not Collection types
Map<K, V> - Map itself doesn't have .stream() — but its views do (entrySet(), etc.)
Optional<T> - It's not a collection — use .stream() only from Java 9 onwards
*/

// Example 3: Partitioning Even and Odd Numbers
List<Integer> l2 = Arrays.asList(1, 2, 3, 4, 5, 6);
System.out.println(l2.stream().collect(Collectors.partitioningBy(x -> x % 2 == 0)));

// Example 4: Summing Values in a Map
Map<String, Integer> items = new HashMap<>();
items.put("Apple", 10);
items.put("Banana", 20);
items.put("Orange", 15);
System.out.println(items.values().stream().reduce(Integer::sum));
System.out.println(items.values().stream().collect(Collectors.summingInt(x -> x)));

// Example 5: Creating a Map from Stream Elements
List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry");
System.out.println(fruits.stream().collect(Collectors.toMap(x -> x.toUpperCase(), x -> x.length())));

// Example 6:
List<String> words2 = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple");
System.out.println(words2.stream().collect(Collectors.toMap(k -> k, v -> 1, (x, y) -> x + y))); // {apple=3, banana=2, orange=1}

//Primitive Streams
Integer numbers = {1, 2, 3, 4, 5};
Stream<Integer> stream = Arrays.stream(numbers); //Alt+Enter gives this
int[] numbers = {1, 2, 3, 4, 5};
IntStream stream = Arrays.stream(numbers); //Alt+Enter gives this IntStream(Interface) -> sequence of primitive int valued elements
System.out.println(IntStream.range(1, 5).boxed().collect(Collectors.toList())); // range(int startInclusive, int endExclusive) -> [1, 2, 3, 4]
// boxed() -> converts IntStream to Stream<Integer>
System.out.println(IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList()));// rangeClosed(int startInclusive, int endInclusive) -> [1, 2, 3, 4, 5]

IntStream.of(1, 2, 3); //just like Stream.of()

DoubleStream doubles = new Random().doubles(5);
// System.out.println(doubles.sum());
// System.out.println(doubles.min());
// System.out.println(doubles.max());
// System.out.println(doubles.average());
// doubles.summaryStatistics() -> gives count, sum, min, max, average together
// doubles.mapToInt(x -> (int) (x + 1)); -> returns an IntStream consisting of the results of applying the given function to the elements of this stream( in this case increasing each element by 1)
// ... and many more methods
System.out.println(doubles.boxed().toList()); //list of random 5 doubles
IntStream intStream = new Random().ints(5);
System.out.println(intStream.boxed().toList()); //list of random 5 ints

// STRING vs STRINGBUILDER vs STRINGBUFFER
//Strings are thread safe as they are immutable
String Str1 = "Hello";
str1.concat("World");
System.out.println(str1); //Hello -> String is immutable, so it doesn't change the original string
String str1 = new String("Hello");
str1.concat("World");
System.out.println(str1); //Hello -> //even if we dont take string literal, it will not change the original string

//However
String str1 = "Hello";
String str2 = str1.concat("World");
System.out.println(str2); // HelloWorld -> str2 is a new string object created by concatenating str1 and "World"
//but if there is a lot of work and we are creating new strings everythime it is not efficient
// so we use StringBuilder or StringBuffer

//StringBuilder
// mutable
// method chaining
// not thread safe
StringBuilder sb = new StringBuilder("Hello");
sb.append("World!"); //modifying the same object, not creating a new string
System.out.println(sb); //HelloWorld
// sb.append("World").append("!").reverse(); // method chaining -> all methods in this return a method reference to the same object, so we can chain them together
String str = sb.toString(); // converts StringBuilder to String -> str is immutable now
sb.insert(1,"Java"); // HJavaelloWorld!
sb.replace(1,3,"REPLACE"); // HREPLACEvaelloWorld!
sb.delete(1,10); // HelloWorld!
sb.reverse(); // !dlroWolleH
sb.charAt(0); // '!' -> returns char at index 0
sb.length(); // 12
sb.substring(0, 5); // !dlro
// ... and many more methods

// Intenal working of StringBuilder
// has a char array of 16 capacity by default
// when we add more than 16 characters, it creates a new array of double the size and copies the old array to the new array

//StringBuffer
//thread safe -> operations are slower than StringBuilder
//same internal working as StringBuilder

//EXAMPLE
class Task extends Thread{
    // private StringBuilder sb;
    private StringBuffer sb;
    // public Task(StringBuilder sb) {
    //     this.sb = sb;
    // }
    public Task(StringBuffer sb) {
        this.sb = sb;
    }
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            sb.append("A");
        }
    }
}
public static void main(String[] args) {
    // StringBuilder sb = new StringBuilder();
    StringBuffer sb = new StringBuffer();
    Task t1 = new Task(sb);
    Task t2 = new Task(sb);
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(sb.length());
    //1802 -> StringBuilder is not thread safe, so it can cause data inconsistency
    //2000 -> StringBuffer is thread safe, so it will not cause data inconsistency
}

//DIFFERENCE BETWEEN STRING, STRINGBUILDER AND STRINGBUFFER
| Feature           | String                          | StringBuilder                     | StringBuffer                      |
|------------------|----------------------------------|-----------------------------------|-----------------------------------|
| Mutability       | Immutable                        | Mutable                           | Mutable                           |
| Thread Safety    | Yes                              | No                                | Yes                               |
| Performance      | Slow due to immutablility        | Fast (no synchronization)         | Slower (due to synchronization)   |
| Use Case         | Constant and small strings       | Single Threaded apps              | Multithreaded Apps                |
| Introduced In    | Java 1.0                         | Java 1.5                          | Java 1.0                          |
| Storage          | String Pool(for literals)        | Heap memory                       | Heap memory                       |