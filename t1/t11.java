//Volatile Keyword
class Shared {
    public boolean flag = false;
    public void writer() {
        System.out.println("Writer thread made the flag true");
        flag = true;
    }
    public void reader() {
        while (!flag) {
            //do nothing
        }
        System.out.println("Flag is true");
    }
}
public static void main(String[] args) {
    Shared shared = new Shared();
    Thread writerThread = new Thread(() -> {
        try {
            Thread.sleep(1000); //doing this because we want to see the reader thread running first so that the reader thread gets stuck in while loop
            //after 1 second, writer thread will set the flag to true, so that the reader thread can exit the while loop
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        shared.writer();
    });
    Thread readerThread = new Thread(() -> shared.reader());
    writerThread.start();
    readerThread.start();
//Output: Writer thread made the flag true
//reader thread is still stuck in while loop . why ?
//The reason is that the flag variable is not declared as volatile. The Java Memory Model allows threads to cache variables, and the reader thread may be using a cached value of flag that is not updated when the writer thread changes it.
//To fix this, we can declare the flag variable as volatile. 
public volatile boolean flag = false;
//This ensures that the value of flag is always read from main memory, and not from a thread's local cache. This way, when the writer thread sets flag to true, the reader thread will see the updated value immediately.
//Output:
//Writer thread made the flag true
//Flag is true
}
//It does not guarantee atomicity, meaning operations like count++ (read-modify-write operations) can still result in inconsistent values.
//volatile guarantees visibility i.e. any write to a volatile variable is immediately visible to other threads.

//Atomic Classes
//Examples -> AtomicInteger, AtomicLong, AtomicReference, AtomicBoolean etc. They ensure atomicity without using locks
public class VolatileCounter{
    private int counter =0;
    // public synchronized void increment() {
    //     counter++;
    // }
    public void increment() {
        counter++;
    }
    public int getCounter() {
        return counter;
    }
    public static void main(String[] args) {
        VolatileCounter counter = new VolatileCounter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
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
        System.out.println("Final counter value: " + counter.getCounter());
    }
//earlier we saw that the ans will not be 2000
//we can get 2000 by making the methdod synchronized
//but this will make the code slow as it will take time to acquire the lock and release the lock
//what if we make the counter volatile -> it still will not print 2000
// so we use AtomicInteger instead of int
    private AtomicInteger counter = new AtomicInteger(0);
    public void increment() {
        counter.incrementAndGet();
    }
    public int getCounter() {
        return counter.get();
    }
// Use Atomic when: You need thread-safe counters or CAS (compare-and-set) logic. Faster than synchronized for simple use-cases.
}

| Feature               | synchronized                          | volatile                           | Atomic (e.g., AtomicInteger)           |
|-----------------------|----------------------------------------|-------------------------------------|-----------------------------------------|
| Thread Safety         | Yes (mutual exclusion)                | No (only visibility)                | Yes (for single variable operations)    |
| Atomicity             | Yes                                    | No                                  | Yes                                     |
| Visibility            | Yes                                    | Yes                                 | Yes                                     |
| Locking Mechanism     | Yes (uses intrinsic monitor lock)      | No                                  | No (uses low-level CAS operations)      |
| Performance           | Slower (blocking involved)             | Fast                                | Fast (non-blocking)                     |
| Granularity           | Works on blocks/methods (coarse)       | Works on variables only             | Works on variables only                 |
| Use Case              | Critical sections, multiple ops        | Flags, state indicators             | Counters, flags, atomic updates         |
| Example Use           | synchronized void method() { ... }     | volatile boolean flag;              | atomicInt.incrementAndGet();            |

