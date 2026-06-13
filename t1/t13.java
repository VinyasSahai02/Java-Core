//COLLECTIONS FRAMEWORK
//a collection is simply an object that represents a group of objects known as elements
//the collection framework provides a set of interfaces and classes that help in managing group of objects
//before introduction of Collection Framework in JDK 1.2, java used classes like vector, stack, hashtable, arrays to store objects and manipulate them
// Drawbacks of these classes:
//1. Inconsistency: each class has a diff way of managing collections
//2. Interoperability: classes were not designed to work together seamlessly
//3. no common interface: counldnt write generic algo

//collection framework is built around interfaces. some of them are:
//1. Collection: root interface for other collection types. it defines basic operations like add, remove, clear, size, iterator etc.
//2. List: an ordered collection (also known as a sequence) that contains duplicate elements. it is implemented by ArrayList, LinkedList, Vector, Stack
//3. Set: a collection that does not allow duplicate elements. it is implemented by HashSet, LinkedHashSet, TreeSet
//4. Map: an object that maps keys to values. it does not allow duplicate keys. it is implemented by HashMap, LinkedHashMap, TreeMap, Hashtable
//5. Queue: a collection designed for holding elements prior to processing. it is implemented by PriorityQueue, LinkedList, ArrayDeque
//6. Deque: a double-ended queue that allows elements to be added or removed from both ends. it is implemented by ArrayDeque, LinkedList
//**Hierarchy == Iterable-> Collection -> List, Set, Queue

//LIST INTERFACE
//used when we want:
//ordered collection of elements(also known as sequence)
//allow duplicate elements
//index based access
 
// Arraylist Implementation Class
//dynamic size unlike arrays
ArrayList<Integer> list = new ArrayList<>(); OR List<Integer> list = new ArrayList<>(); //(cannot access methods of ArrayList, only access methods that are in List interface)
//before adding size = 0 but capacity(default) = 10
ArrayList<Integer> list = new ArrayList<>(100); //initial capacity 100
/*Field field = ArrayList.class.getDeclaredField("elementData");
field.setAccessible(true);
Object[] elementData = (Object[]) field.get(list);
System.out.println("ArrayList capacity: " + elementData.length); */ -> to print capacity just for knowledge
list.add(1); // 0
list.add(5); // 1
list.add(80); // 2
list.add(2, 50); // (index,element) -> adds element and shifts elements to the right
//arraylist is implemented as array of object references. when you add elements to an Arraylist, you're essentially storing these elements in this internal array
//when we add these things happen-> 
//1.check capacity(if the internal array has space)
//2.resize if needed(resize and copy elements in new array)
  //2a.Initial capacity- by default 10
  //2b.Growth Factor- when the array is full, new array is created with a size 1.5 times the old array. This growth factor balances memory efficiency and resizign cost
  //2c.Copying Elements- all elements from old array is copied to the new array(O(n)).
//3.add the element
list.get(2); // 50
list.size(); // 4
for(int i = 0; i < list.size(); i++){
    System.out.println(list.get(i)); //1 5 50 80
}
for(int x: list){
    System.out.println(x);
}
System.out.println(list); // [1,5,50,80]
System.out.println(list.contains(5));
list.remove(2); //index
list.remove("Apple") OR list.reomve(Integer.valueOf(1)) //value -> removes first occurence
// 1.Check Bounds- first check it the index is within the valid range
// 2.Remove Element- remove the element at the specified index and shift all elements to the left
// 3.Resize- decrement size by 1 
//automatic capacity decrease does not happen-> do it using list.trimToSize() -> reduces the capacity to the current size of the list
list.set(1, 100); //index, element -> replaces element

List<String> list1 = Arrays.asList("Monday", "Tuesday", "Wednesday"); //create List not ArrayList(ALT+ENTER to see) on the fly dut to which it is fixed size list, cannot add or remove elements
list.set(1, "Thursday"); //valid
List<String> list2 = new ArrayList<>(list1); 
list2.add("Friday"); //now valid
List<Integer> list = List.of(1,2,3) //not modifiable -> cannot replace also

List<Integer> list = new ArrayList<>();
list.add(1); 
list.add(2);
list.add(3); // 0 1 2
List<Intefer> list1 = List.of(4,5,6,7,8);
list.addAll(list1);//1 2 3 4 5 6 7 8

Object[] array = list.toArray(); //converts to list to array of objects
Integer[] array = list.toArray(new Integer[0]); //converts to list to array of Integers
Collections.sort(list) OR list.sort(null) // sorts the list in ascending order
Collections.sort(list, Collections.reverseOrder()) // sorts the list in descending order
// Addtion and Remove -> O(n) because it may require shifting elements


//Comparator(interface)
//Comparator is a functional interface, so to use this we need a implementation class or lambda expression. It is used to define custom sorting logic
class StringLengthComparator implements Comparator<String>{
    @Override
    public int compare(String o1, String 02) {
        return o1.length() - o2.length(); //ascending order
        //return o2.length() - o1.length(); //descending order
    }
// public int compare(T o1, T o2)
// Returns:
// -1 if o1 < o2 -> o1 comes before o2
// 0 if o1 == o2
// 1 if o1 > o2 -> o1 comes after o2
}
List<String> words = Arrays.asList("banana", "apple", "date");
words.sort(new StringLengthComparator()); //Comparator - sort based on length
// OR Collections.sort(words, new StringLengthComparator());
System.out.println(words); // [date, apple, banana]
//OR
List<String> words = Arrays.asList("banana", "apple", "date");
words.sort((o1, o2) -> o1.length() - o2.length()); //using lambda expression it is easier
System.out.println(words); // [date, apple, banana]
//another example
class Student implements Comparable<Student>{
    private String name;
    private double gpa;
    public Student(String name, double gpa) {
        this.name = name;
        this.gpa  = gpa;
    }
    public String getName() { return name; }
    public double getGpa()  { return gpa; }
}
public class Main {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Charlie", 3.5));
        students.add(new Student("Bob", 3.7));
        students.add(new Student("Alice", 3.5));
        students.add(new Student("Akshit", 3.9));
        students.sort(null); //Student class does not implement Comparable<Student>, so Java has no idea how to sort them by default.
        for (Student s : students) { //error -> equals, hashcode, toString, compareTo does not exist right now and class does not implement comparable
            System.out.println(s.getName() + ": " + s.getGpa());
        }

        students.sort((o1, o2) -> {
            if (o2.getGpa() - o1.getGpa() > 0) {
                return 1;
            } else if (o2.getGpa() - o1.getGpa() < 0) {
                return -1;
            } else {
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (Student s : students) { //now it works
            System.out.println(s.getName() + ": " + s.getGpa());
            //OUTPUT : (when else just return 0;)
            // Akshit: 3.9
            // Bob: 3.7
            // Charlie: 3.5
            // Alice: 3.5
            //charlie comes before because it is defined eariler in line 141

            //OUTPUT : 
            // Akshit: 3.9
            // Bob: 3.7
            // Alice: 3.5
            // Charlie: 3.5
        }
        // Comparator<Student> comparator = Comparator.comparing(Student::getGpa).reversed().thenComparing(Student::getName); -> THIS IS JAVA 8 (will learn later)
    }
}


//LinkedList Implementation Class
//this is doubly by defalut
LinkedList<Integer> linkedList = new LinkedList<>(); 
linkedList.add(1);
linkedList.add(2);
linkedList.add(3);
//linkedlist VS arraylist
//1. Insertion and Deletion: LinkedList is better for insertion and deletion of elements because it does not require shifting elements.
//2. Random Access: ArrayList is better for random access of elements because it uses an array as its underlying data structure. LinkedList requires traversing the list to access an element.
//3. Memory Overhead: LinkedList has more memory overhead than ArrayList because it stores references to the next and previous elements in addition to the element itself.
linkedList.get(1); // (index) => 2 -> o(n) --> direct get is allowed in this case but if we make our own custom LL then it wont be possible
linkedList.addFirst(0); // o(1) as doubly
linkedList.addLast(4); // o(1) as doubly
linkedList.getFirst(); // 0
linkedList.getLast(); // 4
linkedList.removeFirst(); // 1 2 3 4
linkedList.reomveLast(); // 1 2 3
linkedList.remove(2); //(index) => 1 2
linkedList.removeIf(x -> x % 2 == 0) //removes all even numbers
linkedList.add(2, 5); //adds 5 at index 2

//creating linkedlist on the fly
LinkedList<String> animals = new LinkedList<>(Arrays.asList("Dog", "Cat", "Elephant"));
LinkedList<String> animalsRemove = new LinkedList<>(Arrays.asList("Dog", "Lion"));
animals.removeAll(animalsRemove); //removes all elements in the list from the linkedlist


//Vector Implementation Class
//Legacy Class -> introduced before collection framework
//it is synchronized -> thread safe
// not recommended for single threaded scenarios -> use ArrayList instead
// but for thread safty use vector
//Features:
//1. Dynamic Array: Vector is a dynamic array that can grow and shrink in size as needed just like Arraylist
//2. Synchronized: All methods in Vector are synchronized, making it thread-safe. This means that multiple threads can access a Vector object without causing data inconsistency or corruption.
//3. Legacy Class: Vector is a legacy class that was part of the original Java 1.0 release
//4. Resizing Mechanism: When current capacity is exceeded, it doubles its size by default(or increases bu a specific capacity if provided)
//5. Random Access: Similar to arrays and ArrayList, Vector allows random access to elements using index
Vector<Integer> list = new Vector<>(); //default capacity 10
Vector<Integer> list = new Vector<>(5); //initial capacity 5
list.add(1);
list.add(1);
list.add(1);
list.add(1);
list.add(1);
System.out.println(list.capacity()); //5
list.add(1);
System.out.println(list.capacity()); //10

Vector<Integer> list = new Vector<>(2, 3); //initial capacity 2, increment by 3
list.add(1);
list.add(1);
System.out.println(list.capacity()); //2
list.add(1);
System.out.println(list.capacity()); //5
list.add(1);
list.add(1);
System.out.println(list.capacity()); //5
list.add(1);
System.out.println(list.capacity()); //8

Vector<Integer> vector1 = new Vector<>(); // (collection) things like arraylist, linkedlist, stack, hashtable, etc. can be passed as a collection to vector constructor
LinkedList<Integer> linkedList = new LinkedList<>();
linkedList.add(1);
linkedList.add(2);
linkedList.add(3);
Vector<Integer> vector1 = new Vector<>(linkedList);
System.out.println(vector1); // [1, 2, 3]
for(int i = 0; i < vector1.size(); i++){
    System.out.println(vector1.get(i));
}
vector1.clear(); //has all common methds like remove, add, get etc.
System.out.println(vector1); //[]

//Synchronization in Vector
//ensures only one thread can access the vector at a time
//makes it thread safe but can introduce performance overhead because synchronization adds locking and unloakcing costs
Vector<Integer> list = new Vector<>();
Thread t1 = new Thread(() -> {
for (int i = 0; i < 1000; i++) {
    list.add(i);
}
});
Thread t2 = new Thread(() -> {
for (int i = 0; i < 1000; i++) {
    list.add(i);
}
});
t1.start();
t2.start();
try {
t1.join();
t2.join();
} catch (InterruptedException e) {
e.printStackTrace();
}
System.out.println("Size of list: " + list.size()); // Output: 2000


//Stack Implementation Class
//extends vector class-> inherits all methods of dynamic array but is constrained by the stacks LIFO nature, also synchronized
Stack<Integer> stack = new Stack<>();
stack.push(1);
stack.push(2);
stack.push(3);
stack.push(4);
stack.push(5);
// stack.add(6) -> valid since it extends vector 
System.out.println(stack); // [1, 2, 3, 4, 5]
Integer removedElement = stack.pop(); // [5]
System.out.println(stack); // [1, 2, 3, 4]
Integer peek = stack.peek(); //[4]
System.out.println(peek);
System.out.println(stack); // [1, 2, 3, 4]
System.out.println(stack.isEmpty());
System.out.println(stack.size()); // 4
int search = stack.search(3); // returns the 1-based position of the element from the top of the stack, if not found returns -1
System.out.println(search); //2
// 4
// 3
// 2
// 1

// linkedlist as stack -> possible because it is doubly
LinkedList<Integer> linkedList = new LinkedList<>();
linkedList.addLast(1);
linkedList.addLast(2);
linkedList.addLast(3);
linkedList.getLast();// peek
linkedList.removeLast(); //pop
linkedList.size();
linkedList.isEmpty();

// arraylist as stack 
ArrayList<Integer> arrayList = new ArrayList<>();
arrayList.add(1);
arrayList.add(2);
arrayList.add(3);
arrayList.get(arrayList.size() - 1); // peek
arrayList.remove(arrayList.size() - 1); // pop


//CopyOnWriteArrayList 
// as stack and vector are synchronized, things are locked and unlocked, so to enhance performance CopyOnWriteArrayList is used
public static void main(String[] args) {
    CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
    // "Copy on Write" means that whenever a write operation
    // like adding or removing an element
    // instead of directly modifying the existing list
    // a new copy of the list is created, and the modification is applied to that copy
    // This ensures that other threads reading the list while it’s being modified are unaffected.

    // Read Operations: Fast and direct, since they happen on a stable list without interference from modifications.
    // Write Operations: A new copy of the list is created for every modification.
    //                  The reference to the list is then updated so that subsequent reads use this new list.

    // use this when -> write less , read more
    // List<String> shoppingList = new ArrayList<>();
    List<String> shoppingList = new CopyOnWriteArrayList<>();
    shoppingList.add("Milk");
    shoppingList.add("Eggs");
    shoppingList.add("Bread");
    System.out.println("Initial Shopping List: " + shoppingList);

    for (String item : shoppingList) {
        System.out.println(item);
        // Try to modify the list while reading
        if (item.equals("Eggs")) {
            shoppingList.add("Butter");
            System.out.println("Added Butter while reading.");
        }
    }
// Initial Shopping List: [Milk, Eggs, Bread]
// Milk
// Eggs
// Added Butter while reading.
// Bread
    // System.out.println("Updated Shopping List: " + shoppingList); //Error -> ConcurrentModificationException for ArrayList
    System.out.println("Updated Shopping List: " + shoppingList); // [Milk, Eggs, Bread, Butter]
//Iterator will NOT reflect modifications made during iteration.
// When the iterator is created (behind the scenes in the for-each loop), CopyOnWriteArrayList creates a copy of the array.
// That copy includes: ["Milk", "Eggs", "Bread"]
// Now, inside the loop:
// It reads Milk → prints it ✅ It reads Eggs → prints it ✅
// → Then adds Butter to the original list, but the iterator is still reading from the original copy (["Milk", "Eggs", "Bread"]), so: It reads Bread ✅
// It does not see Butter, even though it was added.

// - CopyOnWriteArrayList allows safe modification during iteration.
// - Behind the scenes, when the iterator is created (for example, in a for-each loop), 
//   CopyOnWriteArrayList creates a **snapshot (a copy)** of the array at that moment.
// - The iterator works over this snapshot and **does NOT reflect modifications** 
//   (like add/remove) made after the iterator is created.
// - For example, if you add an item to the list inside the loop, the iterator will 
//   continue looping over the copied snapshot (frozen at the start of the loop), 
//   even though the underlying list (the live list) has been updated.
//   So, the new item (like "Butter") is added to the real list, 
//   but the ongoing iteration only sees the **copied array** created at the start of the loop.

// - Important:
//     - Each modification (e.g., .add()) creates a **new internal copy** of the array.
//     - This makes CopyOnWriteArrayList memory-heavy and inefficient for frequent writes, 
//       but very safe and fast for read-heavy scenarios.

//EXAMPLE
    List<String> sharedList = new CopyOnWriteArrayList<>();
    sharedList.add("Item1");
    sharedList.add("Item2");
    sharedList.add("Item3");
    Thread readerThread = new Thread(() -> {
        try {
            while (true) {
                // Iterate through the list
                for (String item : sharedList) {
                    System.out.println("Reading item: " + item);
                    Thread.sleep(100); // Small delay to simulate work
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in reader thread: " + e);
        }
    });
    Thread writerThread = new Thread(() -> {
        try {
            Thread.sleep(500); // Delay to allow reading to start first
            sharedList.add("Item4"); //while loop is continuously running in reader thread and we have tried to add while reading is happening
            System.out.println("Added Item4 to the list.");

            Thread.sleep(500);
            sharedList.remove("Item1");
            System.out.println("Removed Item1 from the list.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
    readerThread.start();
    writerThread.start();
}