//MAP (does not extend collection -> seperate interface)

//HashMap
//implements Map interface
//no particular order of elements -> onordered
//null can be used as key(only once) and value in HashMap -> map.put(null, "Nitin");
//not synchronized -> not thread safe
//Offers const time performance (O(1)) for basic operations like get and put, assuming the hash function disperses the elements properly.
//The time complexity for get and put operations is O(1) on average, but can degrade to O(n) in the worst case if many collisions occur.
//After Java 8, O(log n) as BST
 public static void main(String[] args) {
    HashMap<Integer, String> map = new HashMap<>();
    map.put(31, "Shubham");
    map.put(11, "Akshit");
    map.put(2, "Neha");
    map.put(2, "Mehul"); //replacing value of key 2-> replaced Neha with Mehul
    System.out.println(map);

    String student = map.get(31);
    System.out.println(student); // Shubham
    String s = map.get(69);
    System.out.println(s); // null

    System.out.println(map.containsKey(2)); //true
    System.out.println(map.containsValue("Shubham")); //true

    Set<Integer> keys = map.keySet(); // [31, 11, 2]
    for (int i : keys) {
        System.out.println(map.get(i)); // Shubham  Akshit  Mehul
    }

    Set<Map.Entry<Integer, String>> entries = map.entrySet();
    for (Map.Entry<Integer, String> entry : entries) {
        System.out.println(entry.getKey() + " : " + entry.getValue()); // 31 : Shubham  11 : Akshit  2 : Mehul
        entry.setValue(entry.getValue().toUpperCase());
    }
    System.out.println(map); //{2=MEHUL, 11=AKSHIT, 31=SHUBHAM}

    // map.remove(31); removes the key 31 and its value
    boolean res = map.remove(31, "Nitin");
    System.out.println("REMOVED ? :" + res); //nothing is reomoved as Nitin does not exist
}

//Internal Structure of HashMap
//Basic Components of Hashmap
//1. Hash Function:
// It is an algorithm takes the key and returns a fixed sized string of bytes(hashcode/hashvalue/hash) which is used to determine the index of the bucket where the key-value pair will be stored.
// the primary purpose of the hash function is to map data of arbitary size to data of fixed size.
// The hash function should distribute keys uniformly across the buckets to minimize collisions.
// Hash Function -> 1.Deterministic: The same input will always produce the same output. 2.Fixed Output Size: Regardless of the input size, hashcode has a consistent size(32bit, 64bit). 3.Efiicient Computation: the hash func should compute the hashcode quickly
//2. Buckets: A bucket is a container that holds the key-value pairs, think of buckets as cells in a list(array). Each bucket can hold multiple key-value pairs in case of collisions. In Java, a bucket is typically implemented as a linked list or a balanced tree (in case of many collisions).
//3. Key: The key is the unique identifier for the value in the map. It can be of any object type, but it must implement the hashCode() and equals() methods properly to ensure correct behavior in the HashMap.
//4. Value: The value is the data associated with the key. It can be of any object type and can be null.

//How Data is Stored In HashMap
//1.Hashing the Key: Key is passed through a hash func to generate unique hashcode. Hashcode helps determine where the key-value pair will be stored in the bucket array.
//2.Index Calculation: The hashcode is then used to calculate the index of the bucket using (int index = hashcode % arraySize). the index decides which bucket will hold this pair
// For Example-> if array size is 16(default), hashcode will be divided by 16, and the remainder will be the index.
//3.Storing in the bucket: The key-value pair is stored in the calculated bucket. Each bucket can hold multiple key value pairs(collision handling).

//How Hashmap retrieves data
//when we call get(key), the following steps are performed:
//1.Hashing the Key: similar to insertion, the key is hashed using the same hash func to calculate its hashcode
//2.Finding the index: hashcode is used to find the index of the bucket where the key value pair is stored
//3.Searching the bucket: once the correctbutcket is found, it checks for the key in that bucket. if it finds the key, it returns the associated value

//Handling Collisions
//same input will always produce the same output but 2 diff inputs can produce same output
//diff keys can generate the same index(collision) -> Javs's hashmap uses Linked List for this
//when a key-value pair is retrieved, hashmap traverses the linked list, checking each key until it finds a match.
//Java version 8 and later, if the number of elements in a bucket exceeds a certain threshold (default is 8), the linked list is converted to a balanced BST.
//This reduces the time complexity of search operations from O(n) to O(log n) in case of many collisions.

//Hahsmap Resizing(Rehashing)
//Hashmap has an internal array size(default 8). when the number of key-value pairs exceeds the load factor (default 0.75), the hashmap resizes itself.
//The new size is typically double the old size. This process is called rehashing.
//default size is 16, so when more than (16 * 0.75) = 12 elements are added, the hashmap resizes itself to 32(double of 16)
HashMap<Integer, String> map = new HashMap<>(17,0.5f); // we can give initial capacity and load factor
//After the size is increased, all existing pairs are rehashed(their positions are recalculated) and placed into the new array

//How is an element found in the Linked List of a bucket
//when we do map.get(key), hashcode is generated and index is calculated, then we go to that particular bucket and traverse the Linked List to find the value associated with the key
//we check the key of each node in LL using equals() method -> hashCode() – to find the right bucket, equals() – to compare keys inside the bucket
//CASE 1- HashMap with primitive/wrapper types or Strings as keys
Map<String, Integer> map = new HashMap<>();
map.put("Shubham", 90); //hashcode1 --> index1
map.put("Neha", 80); //hashcode2 --> index2
map.put("Shubham",98) // hashcode1 --> index1 --> equals --> replace
// String class overrides equals() and hashCode(). Java sees that both keys are equal and replaces the old value.
System.out.println(map.get("key")); // ✅ Output: value2
//CASE 2- HashMap with custom objects as keys
class Person {
    private String name;
    private int id;
    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    @Override
    public int hashCode() { 
        return Objects.hash(name, id);
    }
    @Override
    public boolean equals(Object obj) { //when we are comparing nodes, we receive an object as key as HashMap<Person,String>
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Person other = (Person) obj;
        return id == other.getId() && Objects.equals(name, other.getName());
        //we can also do name.equals(other.getName()) for String NAME, but name might be null, so we use Objects.equals() to avoid NullPointerException
    }
}
HashMap<Person, String> map = new HashMap<>();
Person p1 = new Person("Alice", 1);
Person p2 = new Person("Bob", 2);
Person p3 = new Person("Alice", 1);

map.put(p1, "Engineer"); // hashcode1 --> index1
map.put(p2, "Designer"); // hashcode2 --> index2
map.put(p3, "Manager"); // hashcode3 --> index3
//since we used new keyword, hashcode of 3rd and 1st will be diff, even though they are the same person
// since hashcode is diff their index will also be diff-> so stored in diff places
//If you don’t override the default equals() and hashCode(), then Java uses default Object.equals(), which compares object references — not actual content.
System.out.println("HashMap Size: " + map.size()); //3 but it should be 2
System.out.println("Value for p1: " + map.get(p1)); // Engineer
System.out.println("Value for p3: " + map.get(p3)); // Manager
//That means this will fail:
new Student(1, "Alice") != new Student(1, "Alice") // even though the data is same they are considered diff objects in memory

//After overriding 
map.put(p1, "Engineer"); // hashcode1 --> index1
map.put(p2, "Designer"); // hashcode2 --> index2
map.put(p3, "Manager"); // hashcode1 --> index1 --> equals --> replace
System.out.println("HashMap Size: " + map.size()); //2
System.out.println("Value for p1: " + map.get(p1)); // Manager
System.out.println("Value for p3: " + map.get(p3)); // Manager
/**NOTE-> when ever we make custom class, make sure to override the equals() and hashcode()**/

//Time Complexity of HashMap
put(key,value) -> O(1) on average case (direct bucket access), O(log n) in worst case (when buckets convert to Red-Black tree after exceeding collision threshold)
get(key) -> O(1) on average case (direct bucket access), O(log n) in worst case (when searching within a treeified bucket)
remove(key) -> O(1) on average case (direct bucket access), O(log n) in worst case (when removing from a treeified bucket)
containsKey(key) -> O(1) on average case (direct bucket access), O(log n) in worst case (when searching within a treeified bucket)
containsValue(value) -> O(n) in worst case (need to check all buckets and their elements)
size() -> O(1) (keeps track of size internally)

//LinkedHashMap
//extends HashMap and implements Map interface
//not thread safe
//maintains insertion order of elements -> ordered
//Internal Structure-> HashMap + doubly linked list
//linkedhashmap is little slower due to overhead of maintaining the doubly linked list, consumes more memory than HashMap also
LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>(11, 0.3f, true); //(initial capacity, load factor, access order -> true for access order & false(default) for insertion order)
linkedHashMap.put("Orange", 10);
linkedHashMap.put("Apple", 20);
linkedHashMap.put("Guava", 13);
for (Map.Entry<String, Integer> entry : linkedHashMap.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue()); // Orange: 10, Apple: 20, Guava: 13
}
//when access order is true, whatever we access is moved to the end of the doubly linked list, so the order of elements will change
//when we use put or get, the element is moved to the end of the list
linkedHashMap.get("Apple");
for (Map.Entry<String, Integer> entry : linkedHashMap.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue()); // Orange: 10, Guava: 13, Apple: 20
}
//element is moved to the end because lets say we have a lot of elements so it becomes difficult to find the least recently used element
//so we can use access order to find the least recently used element easily as it will be at the start, so we can remove it easily -> LRU Cache
//removing elements from cache -> if we dont do this then cache will become full

HashMap<String, Integer> hashMap = new HashMap<>(); //some more things about hashmap
LinkedHashMap linkedHashMap1 = new LinkedHashMap(hashMap);
hashMap.put("Shubham", 91);
hashMap.put("Bob", 80);
hashMap.put("Akshit", 78);
Integer res = hashMap.getOrDefault("Vipul", 0); //(key, default value) -> if key is not present, it will return the default value
hashMap.putIfAbsent("Shubham", 92); // if key is not present, it will add the key-value pair, else it will do nothing

//LRU Cache
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private int capacity;
    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
    public static void main(String[] args) {
        LRUCache<String, Integer> studentMap = new LRUCache<>(3);
        studentMap.put("Bob", 99);
        studentMap.put("Alice", 89);
        studentMap.put("Ram", 91);
        studentMap.put("Vipul", 89);
        System.out.println(studentMap); //{Alice=89, Ram=91, Vipul=89} -> Bob is removed as it is the least recently used element
    }
}

//Garbage Collection
class Phone {
    String brand;
    String model;
    public Phone(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }
    @Override
    public String toString() {
        return "Phone{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
public static void main(String[] args) {
    Phone phone = new Phone("Samsung", "Galaxy S21"); //phone is a strong reference to the object(new Phone("Samsung", "Galaxy S21")) created in the heap
    System.out.println(phone); // Phone{brand='Samsung', model='Galaxy S21'}
    phone = null; //phone is now null -> no part of your program is referencing an object anymore, it's considered "eligible for garbage collection", but the object is still occupying memory in the heap
    System.out.println(phone); // null
    //JVM automaitcally destroys the memory which has no reference to it, meaning it cannot be used and is just existing -> Garbage Collection -> Automatic GC will only happen when reference is put to null
    // Manually -> System.gc() OR Runtime.getRuntime().gc() //suggests JVM to run garbage collector, but not needed as JVM does it automatically

    WeakReference<Phone> phoneWeakReference = new WeakReference<>(new Phone("Apple", "16 pro max")); //weak reference
    System.out.println(phoneWeakReference.get()); // Phone{brand='Apple', model='16 pro max'}
    System.gc(); //without this JVm will not destroy memory
    try {
        Thread.sleep(10000);
    } catch (Exception ignored) {
    }
    System.out.println(phoneWeakReference.get()); //null
}
//WeakHashMap
//extends AbstractMap and implements Map interface
//in weakhashmap, the keys are weakly referenced, meaning they can be garbage collected(automatically) if there are no strong references to them
//values are strongly referenced, so only the key's absence makes the entry eligible for GC.
public static void main(String[] args) {
    WeakHashMap<Object, String> map = new WeakHashMap<>();
    Object key1 = new Object();
    Object key2 = new Object();

    map.put(key1, "Strong Key");
    map.put(key2, "Weak Key");
    System.out.println("Before GC: " + map); //Before GC: {java.lang.Object@<id>=Strong Key, java.lang.Object@<id>=Weak Key}

    key2 = null; // No more strong reference to key2
    System.gc(); // Suggest JVM to run garbage collection
    // Wait a bit for GC to happen
    try { Thread.sleep(1000); } catch (Exception e) {}
    System.out.println("After GC: " + map); //After GC:  {java.lang.Object@<id>=Strong Key}
    //if we use strings as keys, they will not be garbage collected as they are strongly referenced in the string pool
}
//better example
class Image {
    private String name;
    public Image(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
}
public class WeakHashMapDemo {
    public static void main(String[] args) {
        WeakHashMap<String, Image> imageCache = new WeakHashMap<>();
        loadCache(imageCache);
        System.out.println(imageCache);
        System.gc();
        simulateApplicationRunning();
        System.out.println("Cache after running (some entries may be cleared): " + imageCache);
    }
    public static void loadCache(Map<String, Image> imageCache) {
        String k1 = new String("img1");
        String k2 = new String("img2");
        // String k1 = "img1"; -> "img1" is stored in the string pool, and the JVM always keeps a strong reference to it — so the key will never be garbage collected
        imageCache.put(k1, new Image("Image 1"));
        imageCache.put(k2, new Image("Image 2"));
    }
    private static void simulateApplicationRunning() {
        try {
            System.out.println("Simulating application running...");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//IdentityHashMap
//extends AbstractMap and implements Map interface 
//uses == (reference equality) instead of .equals() to compare keys meaning duplicate keys are allowed if references differ
public class IdentityHashMapDemo {
    public static void main(String[] args) {
        Map<String, Integer> map = new IdentityHashMap<>();
        // Map<String, Integer> map1 = new HashMap<>();
        String key1 = new String("Akshit");
        String key2 = new String("Akshit");
        //String class has a hashcode method but IdentityHashMap does not use it.
        //IdentityHashMap use hashcode method written inside Object class in which we use memory addresses to generate hashcode.
        //whenever we use new keyword, it creates a new object in the heap memory and gives a different memory address, meaning address of key1 and key2 are different. so diff hashcode also

        //String class overrides .equals() and .hashCode(), so two different String objects with same content are equal in HashMap, but not equal in IdentityHashMap.
        // IdentityHashMap uses System.identityHashCode(), which returns a hash based on memory address, not content.
        // That's why in IdentityHashMap, both keys are considered different.
        map.put(key1, 90);
        map.put(key2, 92);
        System.out.println(map); //{Akshit=90, Akshit=92} -> gives different hashcode for both keys
        map1.put(key1, 90);
        map1.put(key2, 92);
        // System.out.println(map1); {Akshit=92} -> gives same hashcode for both keys 
    }
}

//Comparable
//Comparable lets objects compare themselves to others.
public class Student implements Comparable<Student> {
    String name;
    double gpa;
    public Student(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
    }
    @Override
    public int compareTo(Student other) {
        if (this.gpa == other.gpa) {
            return this.name.compareTo(other.name); // Sort by name if GPA is the same
        }
        return Double.compare(this.gpa, other.gpa); // Sort by GPA
    }
}
public class ComparableDemo {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student("Charlie", 3.5));
        list.add(new Student("Bob", 3.7));
        list.add(new Student("Alice", 3.5));
        list.add(new Student("Akshit", 3.9));
        list.sort(null);
        System.out.println(list);
    }
}
| Feature             | Comparable                     | Comparator                          |
|---------------------|--------------------------------|-------------------------------------|
| Method              | compareTo(T o)                 | compare(T o1, T o2)                 |
| Where to define     | Inside the class               | Outside the class                   |
| Used for            | Natural ordering (default sort)| Custom sorting logic                |
| Flexibility         | Less flexible                  | More flexible                       |
| Multiple sort logic | ❌ Only one                    | ✅ Can define many comparators     |
