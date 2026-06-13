//COLLECTION FRAMEWORK CONTINUE
// Set
// Set is a collection that cannot contain duplicate elements
// find elements , insert element -> O(1)
// Map --> HashMap, LinkedHashMap, TreeMap, EnumMap
// Set --> HashSet(fast,unordered), LinkedHashSet(insertion order), TreeSet(sorted, no null allowed), EnumSet ---> neither of these are thread safe
// internal working:
// 1.Internally uses a HashMap to store elements.
// 2.Every element added to the HashSet is stored as a key in the map, and a dummy constant object (like PRESENT) is stored as the value.
//When you do:
// Set<String> set = new HashSet<>();
// set.add("Apple");
// Its internally like: map.put("Apple", PRESENT);
Set<Integer> set1 = new HashSet<>();
set1.add(12);
set1.add(1);
set1.add(1);
set1.add(67);
System.out.println(set1);
System.out.println(set1.contains(12));
System.out.println(set1.remove(67));
set1.clear();
System.out.println(set1.isEmpty()); // true
for(int i: set1){
    System.out.println(i);
}
//Collections.synchronizedMap(map) -> we can make any map synchronized
//Set<Integer> set2 = Collections.synchronizedSet(set1) -> we can make any set synchronized
//this means that all methods of set1 will get wrapped in synchronized block, so that only one thread can access it at a time
//so not a good practice thats why didnt learn this in map
Set<Integer> set1 =  new ConcurrentSkipListSet<>(); // for thread safety and sorted order
NavigableSet<Integer> set2 = new ConcurrentSkipListSet<>(); // ConcurrentSkipListSet extends NavigableSet so we can use it as well
Set<Integer> integers = Set.of(1, 2, 3,4,5,6,7,8,9,54,4323,545,4545); // unmodifiable

//CopyOnWriteSet
// Just Thread-Safe and not sorted
// Copy-On-Write Mechanism
// No Duplicate Elements
// Iterators Do Not Reflect Modifications similar to CopyOnWriteArrayList
CopyOnWriteArraySet<Integer> copyOnWriteSet = new CopyOnWriteArraySet<>();
ConcurrentSkipListSet<Integer> concurrentSkipListSet = new ConcurrentSkipListSet<>();
for (int i = 1; i <= 3; i++) {
    copyOnWriteSet.add(i);
    concurrentSkipListSet.add(i);
}
System.out.println("Initial CopyOnWriteArraySet: " + copyOnWriteSet); //[1, 2, 3]
System.out.println("Initial ConcurrentSkipListSet: " + concurrentSkipListSet); //[1, 2, 3]

System.out.println("\nIterating and modifying CopyOnWriteArraySet:");
for (Integer num : copyOnWriteSet) {
    System.out.println("Reading from CopyOnWriteArraySet: " + num);
    // Attempting to modify the set during iteration
    copyOnWriteSet.add(4);
// Iterating and modifying CopyOnWriteArraySet:
// Reading from CopyOnWriteArraySet: 1
// Reading from CopyOnWriteArraySet: 2
// Reading from CopyOnWriteArraySet: 3
}

System.out.println("\nIterating and modifying ConcurrentSkipListSet:");
for (Integer num : concurrentSkipListSet) {
    System.out.println("Reading from ConcurrentSkipListSet: " + num);
    // Attempting to modify the set during iteration
    concurrentSkipListSet.add(4);
// Iterating and modifying ConcurrentSkipListSet:
// Reading from ConcurrentSkipListSet: 1
// Reading from ConcurrentSkipListSet: 2
// Reading from ConcurrentSkipListSet: 3
// Reading from ConcurrentSkipListSet: 4
}

//Queue (Interface)
Queue<Integer> queue1 = new LinkedList<>(); //queue is an interface, so we need to use a class that implements it -> cannot create an object of an interface directly.
//Linked List implements List, Deque. Deques extends Queue -> so we can write this
queue1.add(1);
System.out.println(queue1.size());
//Alternative methods to remove elements from the queue
System.out.println(queue1.remove()); //1,  throws exception if empty
System.out.println(queue1.poll()); // returns null if empty
//Alternative methos to get top element from the queue
System.out.println(queue1.element());  // throws exception if empty
System.out.println(queue1.peek());
//Alternative methods to add elements to the queue
Queue<Integer> queue2 =  new ArrayBlockingQueue<>(2); //bounded queue of size 2 -> size will not increase
System.out.println(queue2.add(1)); // true
System.out.println(queue2.offer(2)); // true
System.out.println(queue2.add(3)); // throws exception 
System.out.println(queue2.offer(3)); // false

//Priority Queue
// not thread safe
// part of the Queue interface
// orders elements based on their natural ordering (for primitives lowest first)
// does not allow null elements
// Operations like add(), poll() -> O(log n) , peek() -> O(1)
// internal working -> PriorityQueue is implemented as a min-heap(like a tree where every node is less than its children -> so the smallest element is at the top) by default (for natural ordering)
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.add(15);
pq.add(10);
pq.add(30);
pq.add(5);
System.out.println(pq.peek()); // 5 -> natural ordering of primitives ascending order
System.out.println(pq); // not sorted -> [5, 10, 30, 15]
while (!pq.isEmpty()){
    System.out.println(pq.poll()); // 5, 10, 15, 30
}
// custom comparator for customised ordering
PriorityQueue<Integer> pq1 = new PriorityQueue<>((a, b) -> b - a);
pq1.add(10);
pq1.add(5);
pq1.add(20);
while (!pq1.isEmpty()) {
    System.out.println(pq1.poll()); // prints: 20, 10, 5
}

//Deque (Double Ended Queue)
// Blocks threads
//  allows insertion and removal of elements from both ends
//  versatile than regular queues and stacks because they support all the operations of both
/*
    INSERTION METHODS
    addFirst(E e): Inserts the specified element at the front.
    addLast(E e): Inserts the specified element at the end.
    offerFirst(E e): Inserts the specified element at the front if possible.
    offerLast(E e): Inserts the specified element at the end if possible.

    REMOVAL METHODS
    removeFirst(): Retrieves and removes the first element.
    removeLast(): Retrieves and removes the last element.
    pollFirst(): Retrieves and removes the first element, or returns null if empty.
    pollLast(): Retrieves and removes the last element, or returns null if empty.

    EXAMINATION METHODS
    getFirst(): Retrieves, but does not remove, the first element.
    getLast(): Retrieves, but does not remove, the last element.
    peekFirst(): Retrieves, but does not remove, the first element, or returns null if empty.
    peekLast(): Retrieves, but does not remove, the last element, or returns null if empty.
    
    STACK METHODS
    push(E e): Adds an element at the front (equivalent to addFirst(E e)).
    pop(): Removes and returns the first element (equivalent to removeFirst())..
*/
Deque<Integer> deque1 = new ArrayDeque<>(); //faster than stack when used as stack and faster than linked list when used as queue
Deque<Integer> deque = new LinkedList<>(); // used when insertion, deletion somewhere in middle
// ArrayDeque:
// Internally uses a circular array -> no need to shift elements, just shift head and tail
// faster iteration, low memory, no null allowed
// not thread safe
// LinkedList:
// Each element is a node object (value + 2 pointers)
// Insertion/removal still O(1), but slower in practice due to object allocation and pointer chasing
// null allowed
// not thread safe
deque1.addFirst(10); // head--
deque1.addLast(20); // tail++
deque1.offerFirst(5);
deque1.offerLast(25);
System.out.println(deque1); // 5, 10, 20, 25
System.out.println("First Element: " + deque1.getFirst()); // Outputs 5
System.out.println("Last Element: " + deque1.getLast());   // Outputs 25
deque1.removeFirst(); // Removes 5
deque1.pollLast();    // Removes 25
for (int num : deque1) {
    System.out.println(num); // Current Deque: [10, 20]
}

//BlockingQueue(Interface)
//the only thread safe queue
// wait for queue to become non-empty (take) or not full (put)
// simplify concurrency problems like producer-consumer
// standard queue --> operations done immediately
    //if empty --> we want to remove -> fails ( no waiting )
    //if full --> we want to add -> fails( no waiting )
// Blocking queue
    // put -->  Blocks if the queue is full until space becomes available
    // take --> Blocks if the queue is empty until an element becomes available
    // offer --> Waits for space to become available, up to the specified timeout
class Producer implements Runnable {
    private BlockingQueue<Integer> queue;
    private int value = 0;
    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Producer produced: " + value);
                queue.put(value++);
                Thread.sleep(1000);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer interrupted");
            }
        }
    }
}
class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;
    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Integer value = queue.take();
                System.out.println("Consumer consumed: " + value);
                Thread.sleep(2000);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer interrupted");
            }
        }
    }
}
public class BockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        // A bounded, blocking queue backed by circular array
        // low memory overhead
        // uses a single lock for both enqueue and dequeue operations (producers and comsumers block each other)
        // more threads --> problem as frequent blocking
        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));
        producer.start();
        consumer.start();
// Producer produced: 0
// Consumer consumed: 0
// Producer produced: 1
// Consumer consumed: 1
// Producer produced: 2
// Producer produced: 3
// Consumer consumed: 2
// Producer produced: 4
// Producer produced: 5
// Consumer consumed: 3 ...

        BlockingQueue<Integer> queue1 =  new LinkedBlockingQueue<>();
        // optionally bounded backed by LinkedList
        // Uses two separate locks for enqueue and dequeue operations
        //no lock on put if unbounded
        //take blocked if queue is empty
        // Higher concurrency between producers and consumers
        
        BlockingQueue<String> queue2 = new PriorityBlockingQueue<>(11, Comparator.reverseOrder());
        // unbounded
        // capacity by default is 11
        // Binary Heap as array and can grow dynamically
        // Head is based on their natural ordering or a provided Comparator like priority queue
        // take() blocks if empty, but put() does not block (because unbounded).
        queue2.add("apple");
        queue2.add("banana");
        queue2.add("cherry");
        System.out.println(queue2); // [cherry, apple, banana] 
        
        BlockingQueue<Integer> queue3 =  new SynchronousQueue<>();
        // each insert operation must wait for a corresponding remove operation by another thread and vice versa.
        //basically put is blocked until another thread calls take and vice versa
        // it cannot store elements, capacity of at most one element
        Thread producer = new Thread(() -> {
            try {
                System.out.println("Producer is waiting to transfer...");
                queue3.put("Hello from producer!");
                System.out.println("Producer has transferred the message.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Producer was interrupted.");
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                System.out.println("Consumer is waiting to receive...");
                String message = queue3.take();
                System.out.println("Consumer received: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Consumer was interrupted.");
            }
        });
        producer.start();
        consumer.start();
// Producer is waiting to transfer...
// Consumer is waiting to receive...
// Producer has transferred the message.
// Consumer received: Hello from producer!

        BlockingQueue<DelayedTask> delayQueue = new DelayQueue<>(); //DelayQueue req us to pass a param that extends Delayed interface -> so we need to make a class for the work we want to do
        // Thread-safe unbounded blocking queue
        // Elements can only be taken from the queue when their delay has expired -> basically take will block until the delay has expired
        // Useful for scheduling tasks to be executed after a certain delay
        // internally priority queue
        delayQueue.put(new DelayedTask("Task1", 5, TimeUnit.SECONDS));
        delayQueue.put(new DelayedTask("Task2", 3, TimeUnit.SECONDS));
        delayQueue.put(new DelayedTask("Task3", 10, TimeUnit.SECONDS));
        while (!delayQueue.isEmpty()) {
            DelayedTask task = delayQueue.take(); // Blocks until a task's delay has expired
            System.out.println("Executed: " + task.getTaskName() + " at " + System.currentTimeMillis());
            //Executed : Task2 at 1691234567890  --> delay was least so it was executed first, priority queue
            //Executed : Task1 at 1691234567895
            //Executed : Task3 at 1691234567900
        }

        class DelayedTask implements Delayed {
            private final String taskName;
            private final long startTime;
            public DelayedTask(String taskName, long delay, TimeUnit unit) {
                this.taskName = taskName;
                this.startTime = System.currentTimeMillis() + unit.toMillis(delay);
            }
            @Override
            public long getDelay(TimeUnit unit) {
                long remaining = startTime - System.currentTimeMillis();
                return unit.convert(remaining, TimeUnit.MILLISECONDS);
            }
            @Override
            public int compareTo(Delayed o) {
                if (this.startTime < ((DelayedTask) o).startTime) {
                    return -1;
                }
                if (this.startTime > ((DelayedTask) o).startTime) {
                    return 1;
                }
                return 0;
            }
            public String getTaskName() {
                return taskName;
            }
        }
}}

| BlockingQueue Type       | Bounded?    | Thread-Safety | Internal Structure | Blocking Behavior                        | Use Case                                             |
|--------------------------|-------------|----------------|--------------------|------------------------------------------|-----------------------------------------------------|
| ArrayBlockingQueue       | Yes         | ✅ Thread-safe | Circular Array     | Blocks on full put or empty take         | Fixed-size queues where size is known in advance   |
| LinkedBlockingQueue      | Optional    | ✅ Thread-safe | Linked Nodes       | Blocks on empty (take), optionally on full (put) | High throughput, loosely bounded/unbounded |
| PriorityBlockingQueue    | No(Unbounded) | ✅ Thread-safe | Heap (Array)     | Blocks on empty (take), never on put     | Priority-based task scheduling                     |
| SynchronousQueue         | No(Capacity 0) | ✅ Thread-safe | No storage      | put waits for take, take waits for put   | Hand-off tasks between threads (1-to-1 exchange)   |
| DelayQueue               | No(Unbounded) | ✅ Thread-safe | PriorityQueue    | take blocks until delay expires          | Task scheduling with delay                         |

//ConcurrentLinkedQueue
// Internal -> linked node structure
// in BlockingQueue, threads block each other under certain conditions(used when threads need to coordinate with each other)
// 1.If a consumer tries to take() from an empty queue, it waits (blocks).
// 2.If a producer tries to put() into a full queue (like ArrayBlockingQueue), it waits (blocks).
// there are senarios where we want to avoid blocking and just want to add/remove(access) elements from the queue
// this is done using ConcurrentLinkedQueue(implementation of Queue interface that supports lock-free, thread safe operations)
// unbounded
// uses compare-and-Swap (CAS) algorithm for thread safety
ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
Thread consumer = new Thread(() -> { // Consumer thread (polls repeatedly, does not block)
    while (true) {
        String item = queue.poll(); // returns null if empty
        if (item != null) {
            System.out.println("Consumer got: " + item);
        } else {
            System.out.println("Queue is empty, moving on...");
        }
        try {
            Thread.sleep(1000); // wait before checking again
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
});
Thread producer = new Thread(() -> { // Producer thread (adds item after delay)
    try {
        Thread.sleep(3000); // simulate delay
        queue.add("Task A");
        System.out.println("Producer added Task A");
    } catch (Exception e) {
        e.printStackTrace();
    }
});
consumer.start();
producer.start();
// Queue is empty, moving on...
// Queue is empty, moving on...
// Queue is empty, moving on...
// Producer added Task A
// Consumer got: Task A

//ConcurrentLinkedDeque
// internal -> linked node structure
// non-blocking, thread-safe double-ended queue
// CAS
ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
Thread producer = new Thread(() -> { // Producer Thread - adds to tail
    for (int i = 1; i <= 5; i++) {
        deque.offerLast("Task " + i);
        System.out.println("Produced: Task " + i);
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }
});
Thread consumer = new Thread(() -> { // Consumer Thread - removes from head
    while (true) {
        String task = deque.pollFirst();
        if (task != null) {
            System.out.println("Consumed: " + task);
        }
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
    }
});
producer.start();
consumer.start();