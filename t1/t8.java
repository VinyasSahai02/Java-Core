//MULTITHREADING CONTINUED
//Synchronization
// Synchronization in Java is a technique used to control access to shared resources in a multithreaded environment.
// It prevents multiple threads from accessing a critical section of code simultaneously, avoiding data inconsistency and race conditions.
class Counter {
    private int count = 0; // shared resource
    public void increment() {
        count++;
    }
    public int getCount() {
        return count;
    }
}
public class MyThread extends Thread {
    private Counter counter;
    public MyThread(Counter counter) {
        this.counter = counter;
    }
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter.increment();
        }
    }
    public static void main(String[] args) {
        Counter counter = new Counter();
        MyThread t1 = new MyThread(counter);
        MyThread t2 = new MyThread(counter);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        }catch (Exception e){}
        System.out.println(counter.getCount()); // Expected: 2000, Actual will be random <= 2000
        //results in a race condition when both threads try to increment the count variable concurrently.
        //Without synchronization, one thread might read the value of count before the other thread has finished writing its incremented value
        //This can lead to both threads reading the same value, incrementing it, and writing it back, effectively losing one of the increments.
    }
}
// Fix this using synchronization
class Counter {
    private int count = 0;
    public synchronized void increment() {
        count++;
    }
    //OR
    public void increment() {
        synchronized (this) { //synchronized block
            count++;
        }
    }
    //only one thread can execute this method at a time, which prevents the race condition. With this change, the output will consistently be 2000.
    public int getCount() {
        return count;
    }
}

//LOCKS
// synchronized keyword in Java provides basic thread-safety but has limitations:
//1. it locks the entire method or block, leading to potential performance issues.
//2. It lacks a try-lock mechanism, causing threads to block indefinitely, increasing the risk of deadlocks.
public class BankAccount {
    private int balance = 100;
    public synchronized void withdraw(int amount) {
        System.out.println(Thread.currentThread().getName() + " attempting to withdraw " + amount);
        if (balance >= amount) {
            System.out.println(Thread.currentThread().getName() + " proceeding with withdrawal");
            try {
                Thread.sleep(10000); // Simulate time taken to process the withdrawal
            } catch (InterruptException e) {}
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " completed withdrawal. Remaining balance: " + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " insufficient balance");
        }
    }
}
public class Main {
    public static void main(String[] args) {
        BankAccount sbi = new BankAccount();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                sbi.withdraw(50);
            }
        };
        Thread t1 = new Thread(task, "Thread 1");
        Thread t2 = new Thread(task, "Thread 2");
        //OR
        BankAccount sbi = new BankAccount();
        Thread t1 = new Thread(() -> sbi.withdraw(50));
        Thread t2 = new Thread(() -> sbi.withdraw(50));
        t1.start();
        t2.start();
// Thread 1 attempting to withdraw 50
// Thread 1 proceeding with withdrawal
// (Waits for 10 seconds due to sleep)
// Thread 1 completed withdrawal. Remaining balance: 50
// Thread 2 attempting to withdraw 50
// Thread 2 proceeding with withdrawal
// (Waits for 10 seconds due to sleep)
// Thread 2 completed withdrawal. Remaining balance: 0

        //t1 goes to sleep for 10 seconds
        //During this time, t2 cannot enter withdraw() because the method is synchronized.
        //Since t1 is still inside withdraw(), t2 is forced to wait until t1 finishes execution.
        //the entire withdraw method is blocked for t2 until t1 fully finishes the work including the sleep
        //t2 does not "know" that withdraw() is currently locked; it just remains blocked until it gets access.
    }
}
//Fix -> ReentrantLock() Implementation CLass
public class BankAccount {
    private int balance = 100;
    private final Lock lock = new ReentrantLock(); //lock is a interface

    public void withdraw(int amount) {
        System.out.println(Thread.currentThread().getName() + " attempting to withdraw " + amount);
        try {
            if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) { //thread tries to acquire the lock within 1 second (1000 ms). if another thread already holds the lock, this thread will wait for up to 1000ms.
                if (balance >= amount) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " proceeding with withdrawal");
                        Thread.sleep(3000); // Simulate time taken to process the withdrawal
                        balance -= amount;
                        System.out.println(Thread.currentThread().getName() + " completed withdrawal. Remaining balance: " + balance);
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock.unlock(); // Always unlock to release the lock
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + " insufficient balance");
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " could not acquire the lock, will try later");
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
public class Main {
    public static void main(String[] args) {
        BankAccount sbi = new BankAccount();
        Thread t1 = new Thread(() -> sbi.withdraw(50));
        Thread t2 = new Thread(() -> sbi.withdraw(50));
        t1.start();
        t2.start();
// Thread 1 attempting to withdraw 50
// Thread 1 proceeding with withdrawal
// Thread 2 attempting to withdraw 50
// Thread 2 could not acquire the lock, will try later
// (Waits for 3 seconds due to sleep)
// Thread 1 completed withdrawal. Remaining balance: 50

        // Thread 1 (t1) starts first and calls withdraw(50).
        // It acquires the lock since no other thread is holding it.
        // It prints "Thread 1 proceeding with withdrawal".
        // It sleeps for 3000ms (3 seconds).
        // Thread 2 (t2) starts almost immediately after t1 and calls withdraw(50).
        // t2 tries to acquire the lock using lock.tryLock(1000, TimeUnit.MILLISECONDS).
        // Since t1 already holds the lock, t2 will wait for up to 1000ms to acquire it.
        // However, t1 sleeps for 3000ms, meaning t2 will NOT get the lock within 1000ms.
        // t2 fails to acquire the lock
    }
}
//Understanding the Catch Block Thread.currentThread().interrupt();
//Interrupting a thread means that we are signaling the thread to stop what it is doing and handle the interruption
//However, interruption in Java does not forcefully stop a thread. Instead, it sets a flag called the interrupted status on the thread.
//When you call Thread.currentThread().interrupt();, it sets the interrupted status of the current thread to true.
//This means:
// 1. The thread itself does not stop immediately.
// 2. The thread can check its interrupted status using Thread.interrupted() and decide what to do.
//3. Some blocking operations like sleep(), wait(), or join() will throw an InterruptedException when a thread is interrupted while waiting.
//when an InterruptedException is caught, the interrupted status of the thread is cleared (set to false).
//So, if we want to keep the interrupted status, we must manually set it again by calling Thread.currentThread().interrupt();

// What Happens After Thread.currentThread().interrupt();?
// 1. If the thread is sleeping or waiting (Thread.sleep(), wait(), join()):
//    If the thread was interrupted before calling Thread.sleep() or wait(), those methods will throw InterruptedException immediately.
//    If the exception was caught and Thread.currentThread().interrupt(); was called again, the thread still keeps running but now has the interrupt flag set to true.
// 2. If the thread is doing normal work (not sleeping/waiting):
//    The thread continues execution as usual.
//    However, if some code checks Thread.interrupted() or isInterrupted(), it can decide to gracefully stop the thread.
public class InterruptExample {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                System.out.println("Thread is running...");
                Thread.sleep(5000); // Simulating long-running task
                System.out.println("Thread finished work!");
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted!");
                Thread.currentThread().interrupt(); // Set interrupt flag again
            }
            // Checking interrupt flag
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Thread is still marked as interrupted. Exiting gracefully...");
            } else {
                System.out.println("Thread is NOT interrupted. Continuing work...");
            }
        });
        t1.start();
        Thread.sleep(1000); // Wait a bit before interrupting
        t1.interrupt(); // Interrupt the thread
    }
// Thread is running...
// Thread was interrupted!
// Thread is still marked as interrupted. Exiting gracefully...
}


//Reentrant lock
// the same thread can acquire the lock multiple times without getting stuck in a deadlock.(self deadlock)
public class ReentrantExample {
    private final Lock lock = new ReentrantLock();

    public void outerMethod() {
        lock.lock(); // First time acquiring the lock
        try {
            System.out.println("Outer method");
            innerMethod(); // Calls another method that also locks
        } finally {
            lock.unlock(); // Releases the second lock
        }
    }
    public void innerMethod() {
        lock.lock(); // Acquiring the lock again (same thread)
        try {
            System.out.println("Inner method");
        } finally {
            lock.unlock(); // Releases the second lock
        }
    }
    public static void main(String[] args) {
        ReentrantExample example = new ReentrantExample();
        example.outerMethod();
    }
// method1() locks first.
// Inside method1(), we call method2(), which also locks.
// Since the same thread (t1) owns the lock, it is allowed to acquire it again.
// Finally, we unlock twice to release it.
}

// Methods of ReentrantLock
// 1.lock(): ->thread waits indefinitely until the lock is available.
// Acquires the lock, blocking the current thread until the lock is available. It would block the thread until the lock becomes available, potentially leading to situations where a thread waits indefinitely.
// If the lock is already held by another thread, the current thread will wait until it can acquire the lock.

// 2.tryLock() -> does not wait indefinitely
// Tries to acquire the lock without waiting. Returns true if the lock was acquired, false otherwise.
// This is non-blocking, meaning the thread will not wait if the lock is not available.

// 3.tryLock(long timeout, TimeUnit unit)
// Attempts to acquire the lock, but with a timeout. If the lock is not available, the thread waits for the specified time before giving up. It is used when you want to attempt to acquire the lock without waiting indefinitely.
// It allows the thread to proceed with other work if the lock isn't available within the specified time. This approach is useful to avoid deadlock scenarios and when you don't want a thread to block forever waiting for a lock.
// Returns true if the lock was acquired within the timeout, false otherwise.

// 4.unlock()
// Releases the lock held by the current thread.
// Must be called in a finally block to ensure that the lock is always released even if an exception occurs.

// 5.lockInterruptibly()
// Acquires the lock unless the current thread is interrupted. This is useful when you want to handle interruptions while acquiring a lock.
-
// **Using lock.lock():
// t2 will wait indefinitely for t1 to release the lock.
// Even if t2.interrupt() is called after 10 sec, t2 will NOT exit—it will keep waiting.
// **Using lock.lockInterruptibly():
// t2 will wait for t1 to finish and release the lock.
// If t2.interrupt() is called before it acquires the lock, t2 will immediately exit with an InterruptedException.


/**Even though we are using ReentrantLock, it does not prevent race conditions in scheduling —-> it only ensures mutual exclusion (i.e., only one thread can hold the lock at a time).
synchronized keyword Prevent Race Conditions only in terms of mutual exclusion, NOT execution order—just like ReentrantLock
Basically does not guerentee fairness**/

//Faireness of Locks
public class FairnessLockExample {
    // private final Lock lock = new ReentrantLock(); // Non-fair lock (default)
    private final Lock lock = new ReentrantLock(true);
    public void accessResource() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " acquired the lock.");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName() + " released the lock.");
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        FairnessLockExample example = new FairnessLockExample();
        Thread thread1 = new Thread(() -> example.accessResource(), "Thread 1");
        Thread thread2 = new Thread(() -> example.accessResource(), "Thread 2");
        Thread thread3 = new Thread(() -> example.accessResource(), "Thread 3");
        thread1.start();
        thread2.start();
        thread3.start();
// Thread 2 acquired the lock.
// Thread 2 released the lock.
// Thread 1 acquired the lock.
// Thread 1 released the lock.
// Thread 3 acquired the lock.
// Thread 3 released the lock.
// -->random execution order.

// Thread 1 acquired the lock.
// Thread 1 released the lock.
// Thread 2 acquired the lock.
// Thread 2 released the lock.
// Thread 3 acquired the lock.
// Thread 3 released the lock.
//-->fair lock ensures that the longest waiting thread gets the lock next
//--> although the order might change in case of fair lock also
    }
}
// 🔹 Use a Fair Lock (true) when:
// Starvation must be avoided (e.g., banking transactions, databases).
// Predictable execution order is needed.
// 🔹 Use an Unfair Lock (false) when:
// Performance is critical (less queuing overhead).
// Starvation is not a concern.

// ** We Can Also Maintain Order Like this
// thread1.start();
// Thread.sleep(50);
// thread2.start();
// Thread.sleep(50);
// thread3.start();

//Read Write Lock
// Using a regular ReentrantLock or synchronized, only one thread can access the resource at a time, even for read-only operations.
// A Read-Write Lock is a special type of lock that allows:
// ✅ Multiple threads to read at the same time (when no thread is writing).
// ✅ Only one thread to write at a time (exclusive lock).
// ReadWriteLock lets:
// 1. Multiple readers access the resource simultaneously.
// 2. Writers wait until all readers finish before they can proceed.
public class ReadWriteCounter {
    private int count = 0; //shared resource
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public void increment() {
        // Only one thread can hold writeLock at a time.
        // Readers cannot access count while a writer is writing.
        writeLock.lock();
        try {
            count++;
            Thread.sleep(1000); //Stimulate write delay
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }
    }

    public int getCount() {
        // Multiple threads can hold readLock simultaneously.
        // If a writer is active, readers must wait.
        readLock.lock();
        try {
            return count;
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadWriteCounter counter = new ReadWriteCounter();
        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println(Thread.currentThread().getName() + " read: " + counter.getCount());
                }
            }
        };

        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    counter.increment();
                    System.out.println(Thread.currentThread().getName() + " incremented");
                }
            }
        };

        Thread writerThread = new Thread(writeTask, "Writer");
        Thread readerThread1 = new Thread(readTask, "Reader-1");
        Thread readerThread2 = new Thread(readTask, "Reader-2");

        writerThread.start();
        readerThread1.start();
        readerThread2.start();

        writerThread.join();
        readerThread1.join();
        readerThread2.join();

        System.out.println("Final count: " + counter.getCount());
        // Writer incremented
        // Reader-1 read: 1
        // Reader-2 read: 1
        // Writer incremented
        // Reader-2 read: 2
        // Reader-1 read: 2
        // Reader-2 read: 3
        // Writer incremented
        // Reader-1 read: 3
        // Final count: 3
    }
}

// When to Use ReadWriteLock?
// ✅ When reads are more frequent than writes (e.g., cache, database reads).
// ❌ If writes are frequent, ReadWriteLock may not improve performance (because writes block all reads).

/**In multi-threading, .join() ensures that the main thread (or any other thread calling .join()) waits for the specified thread to complete execution before moving forward.
Where to Use .join()?
You should use .join() when:
1.You need to wait for threads to finish before proceeding
Example: If you want to print the final count after all threads have finished updating it, you must wait for them to complete.
Without .join(), the main thread might finish execution while other threads are still running, leading to incorrect results.
2.You want to synchronize multiple threads' execution order
Example: If a task in one thread depends on the results of another thread, .join() ensures that the first thread finishes before the dependent thread starts.**/
