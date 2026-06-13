//MAP CONTINUED
Map<K, V> (Interface)
├── SortedMap<K, V> (Interface)
│   └── NavigableMap<K, V> (Interface)
│       └── TreeMap<K, V> (Class)
//SortedMap (interface) extends Map
//TreeMap(internally it is RedBlack Tree) is the implementation class of SortedMap and NavigableMap
//makes sure that entries are sorted based on keys,either in natural order or by a specified comparator    
SortedMap<Integer, String> map = new TreeMap<>((a, b) -> b - a); //comparator to sort in descending order, asceding order is default
//time complexity of all operations is O(log n) as it is a tree based implementation
map.put(91, "Vivek");
map.put(99, "Shubham");
map.put(78, "Mohit");
map.put(77, "Vipul");
map.put(null, "Ravi"); //Error -> NullPointerException as null cannot be sorted
map.get(77); //Vipul
map.containsKey(78); //true
map.containsValue(77); //false
System.out.println(map); //{99=Shubham, 91=Vivek, 78=Mohit, 77=Vipul}
System.out.println(map.firstKey()); //99
System.out.println(map.lastKey()); //77
System.out.println(map.headMap(91)); //returns all the keys before 91(excluding) -> {99=Shubham}
System.out.println(map.tailMap(91)); //returns all the keys after 91(including) -> {91=Vivek, 78=Mohit, 77=Vipul}
System.out.println(map.subMap(99,91)); //returns all the keys between 99 and 91(excluding 91) -> {99=Shubham}
// ... so on many methods
//BST(O(n)) vs RedBlack Tree (O(log n))
1                   2(R)
\                   / \
 2                1(B) 3(B) 
  \
   3 
//NavigableMap 
//provides powerfull navigation options such as finding closest matching keys or retrieving the map in reverse order
NavigableMap<Integer, String> navigableMap =  new TreeMap<>();
navigableMap.put(1, "One");
navigableMap.put(5, "Five");
navigableMap.put(3, "Three");
System.out.println(navigableMap); //{1=One, 3=Three, 5=Five}
System.out.println(navigableMap.lowerKey(4)); //returns the highest key strictly less than given key or null if no value -> 3
System.out.println(navigableMap.ceilingKey(3)); //returns the least key greater than or equal to given key -> 3
System.out.println(navigableMap.higherEntry(1)); //returns the least key strictly greater than given key -> 3=Three
System.out.println(navigableMap.descendingMap()); //returns the map in reverse order -> {5=Five, 3=Three, 1=One}

//HashTable(legacy class, not really used and is replaced by ConcurrentHashMap)
//implements map
// Hashtable is synchronized
// no null key or value
// slower than HashMap
// only linked list in case of collision no tree structure
// all methods are synchronized
hashtable.put(1, "Apple");
hashtable.put(2, "Banana");
hashtable.put(3, "Cherry");
System.out.println(hashtable);
System.out.println("Value for key 2: " + hashtable.get(2));
System.out.println("Does key 3 exist? " + hashtable.containsKey(3));
hashtable.remove(1);
System.out.println("After removing key 1: " + hashtable);
hashtable.put(null, "value"); // Throws exception
hashtable.put(4, null);   // Throws exception
Hashtable<Integer, String> map = new Hashtable<>();
Thread thread1 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
        map.put(i, "Thread1");
    }
});
Thread thread2 = new Thread(() -> {
    for (int i = 1000; i < 2000; i++) {
        map.put(i, "Thread2");
    }
});
thread1.start();
thread2.start();
try {
    thread1.join();
    thread2.join();
} catch (InterruptedException e) {
    e.printStackTrace();
}
System.out.println("Final size of HashMap: " + map.size()); //size will be 2000 as it is synchronized and no data loss will happen

//ConcurrentHashMap(Class)
//implements ConcurrentMap interface which extends Map
//Null keys and values are NOT allowed
//Thread Synchronization is also a limitation in HashTable as only one thread can read or write at a time
//ConcurrentHashMap is a better alternative to HashTable as it allows concurrent access to the map by multiple threads
ConcurrentHashMap<String, Integer> map =  new ConcurrentHashMap<>();
// 🧠 Java 7 Internal Working:
// - Uses **Segment-based locking** (default 16 segments)
// - Each segment is a smaller independent HashMap with its own lock
// - Allows **concurrent access** to different segments
// - ✅ Read: No locking unless a write is occurring in that segment
// - ✅ Write: Locks only the segment being modified

// 🔄 Java 8 Internal Working:
// - Removed segment structure
// - Uses a **bucket array + synchronized blocks + CAS (Compare-And-Swap)**
// - Read operations are mostly lock-free
// - Write operations use CAS and synchronized only when necessary (e.g., resizing or high collisions)

// 💡 Compare-And-Swap (CAS) Explained:
// - Thread A last saw x = 45
// - Thread A wants to update x to 50
// - If x is still 45 → update succeeds (atomic)
// - If x is changed by another thread → update fails → retry
// - Retry continues until it succeeds
// there may be a case when this may happen infinitely, in this case thread waits for a while(Short Random Time) and then retries

// 🔸 HashMap Resizing:
// - Triggered when size exceeds capacity * load factor (default load factor = 0.75)
// - Doubles the size of the internal array
// - Entire table is rehashed and data is copied to new array
// - ❌ Not thread-safe → must be used with external synchronization
// - ❌ Not incremental → resizing blocks the entire operation
// - ❌ Risk of data loss, infinite loops, or corrupted state in multi-threaded use

// 🔸 ConcurrentHashMap Resizing (Java 8+): (Locking during resizing)
// - Also triggered based on load factor
// - ✅ Resizing is **incremental** and **thread-safe**
// - Only one thread initiates resize by creating a new table
// - Other threads help via **cooperative resizing**
// - Uses a **transfer index** → divides rehashing work among threads
// - Each bucket, once moved, is replaced with a **ForwardingNode**
// - Threads encountering a forwarding node redirect to the new table
// - ✅ No global locking → high performance even during resizing
//  similarly locking happens during collisions but lock is applied only on that bucket and not on the whole map

//ConcurrentSkipListMap
//thread-safe and non-blocking alternative to TreeMap(basically a concurrent version of TreeMap)
//used if we want to store things in a sorted manner and synchronized manner (MAP -> SORTED -> THREAD SAFE -> CONCURRENTSKIPLISTMAP)
//SkipList -> probabilistic data structure that allows for fast search, insertion, and deletion operations
//It is similar to a sorted linked list but with multiple layers that "skip" over portions of the list to provide faster access to elements
// Lower levels provide full traversal. Upper levels allow "skipping" over nodes to reduce lookup time.
Level 3:  1 ─────────────────────────>5
Level 2:  1 ──────────> 3 ───────────>5
Level 1:  1 ───> 2 ───> 3 ───> 4 ───> 5
// Lookup, insert, and delete are done in O(log n) time (similar to balanced trees).
//it is similar to Red Black Tree in terms of time Complexicity but we are not using Tree here because -> Complex and expensive to synchronize and Requires rotations and rebalancing

//EnumMap
//implements Map interface
//null key is not allowed, null value is allowed
enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}
public static void main(String[] args) {
    // array of size same as enum
    // [_,"Gym",_,_,_,_,_]
    // no hashing
    // ordinal/index is used
    // FASTER THAN HASHMAP
    // MEMORY EFFICIENT
    Map<Day, String> map = new HashMap<>(); //shows that convert HashMap to EnumMap
    //If all the keys in a Map are values from a single enum, it is recommended to use an EnumMap as the specific implementation.
    //An EnumMap, which has the advantage of knowing all possible keys in advance is more efficient compared to other implementations,
    // as it can use a simple array of known size(as we know how many elemnts are there in enum) as its underlying data structure
    Map<Day, String> map = new EnumMap<>(Day.class);
    map.put(Day.TUESDAY, "Gym"); //there is no hashcode related to the key Day.TUESDAY, it just gets mapped to the index of the TUESDAY in the enum
    map.put(Day.MONDAY, "Walk");
    String s = map.get(Day.TUESDAY);
    System.out.println(map); //{MONDAY=Walk, TUESDAY=Gym}
}

//ImmutableMap
Map<String, Integer> map1 = new HashMap<>();
map1.put("A", 1);
map1.put("B", 2);
Map<String, Integer> map2 = Collections.unmodifiableMap(map1); //returns an unmodifiable view of the specified map -> Java 8 and below
System.out.println(map2);
// map2.put("C", 3); throws exception
//but i can change map1, and we have 2 copies of the same map
//so Map.of() was introduced in Java 9, is used to create an immutable map, which is a map that cannot be modified after it is created.
Map<String, Integer> map3 = Map.of("Shubham", 98, "Vivek", 89);
map3.put("Akshit", 88); //throws UnsupportedOperationException as it is immutable
//Map.of() can take a maximum of 10 entries, if we want to add more than 10 entries then we can use Map.ofEntries()
Map<String, Integer> map4 = Map.ofEntries(Map.entry("Akshit", 99), Map.entry("Vivek", 99));

/*
==================== Java Map Types — Usage & Null Handling ====================

Map Type             | Use When...                                                                                   | Null Keys | Null Values
---------------------|-----------------------------------------------------------------------------------------------|-----------|-------------
HashMap              | ✅ Fast, general-purpose, single-threaded map.                                                | ✅        | ✅
                     | ❌ No ordering guarantee.                                                                      |
                     | ⚠️ Not thread-safe.                                                                            |

LinkedHashMap        | ✅ Maintain insertion/access order.                                                           | ✅        | ✅
                     | 🔁 Used in LRU caches.                                                                         |
                     | ⚠️ Not thread-safe.                                                                            |

WeakHashMap          | ✅ Keys should be GC’ed when no strong refs exist.                                            | ✅        | ✅
                     | 🎯 Good for caching/listeners.                                                                 |
                     | ⚠️ Only keys are weakly referenced.                                                            |

IdentityHashMap      | ✅ Keys compared using `==` instead of `equals()`.                                             | ✅        | ✅
                     | ⚠️ Rare use case. Used in frameworks or serialization.                                        |

TreeMap              | ✅ Need sorted keys (natural/custom order).                                                   | ❌        | ✅
                     | ✅ Range queries supported.                                                                    |
                     | ⚠️ Not thread-safe.                                                                            |

SortedMap (interface)| ✅ Want sorted ordering of keys.                                                              | ❌        | ✅
                     | 🔁 TreeMap or ConcurrentSkipListMap implement this.                                           |

NavigableMap (intf)  | ✅ Need advanced navigation: `floorKey()`, `ceilingKey()` etc.                                 | ❌        | ✅
                     | 🔁 TreeMap or ConcurrentSkipListMap implement this.                                           |

Hashtable            | ⚠️ Legacy thread-safe map.                                                                    | ❌        | ❌
                     | ❌ Use ConcurrentHashMap instead.                                                              |

ConcurrentMap (intf) | ✅ Thread-safe map with concurrent reads/writes.                                              | ❌        | ✅
                     | 🔁 ConcurrentHashMap / ConcurrentSkipListMap are implementations.                             |

ConcurrentHashMap    | ✅ Best for multi-threaded apps. High concurrency, non-blocking reads.                        | ❌        | ✅ (partial)
                     | 🎯 Replaces Hashtable.                                                                         |
                     | ❌ Null keys are not allowed; null values discouraged.                                         |

ConcurrentSkipListMap| ✅ Thread-safe + sorted keys. Skip list based.                                                | ❌        | ✅
                     | 🎯 For concurrent sorted map needs.                                                            |

EnumMap              | ✅ Fastest for enum keys. Maintains natural enum order.                                       | ❌        | ✅
                     | ⚠️ Only works with enums.                                                                      |

ImmutableMap         | ✅ Map must not change. Read-only.                                                            | ❌        | ❌
                     | 🎯 For config/constants. Thread-safe.                                                          |
                     | 🔁 Use `Map.of()` / `Collections.unmodifiableMap()`                                            |

*/
