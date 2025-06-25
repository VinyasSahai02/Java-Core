//MULTITHREADING
// program- set of instructions written in a programming language that tells the computer to perform some task
// process- instance of program that is being executed. when a program runs, OS creates a process to manage its execution
// thread - smallest unit of execution within a process. process can have multiple threads, which share the same resources but can run independently
//Example of thread- chrome use multiple threads for different tabs

//Multitasking
// ability of an operating system to execute multiple processes or threads concurrently.
// OS performing multitaking assign different tasks to differnt cores, which is more efficient than assigning all tasks to a single core
// On single core CPU this is done through time sharing, rapidly switching between different tasks
// On multi core CPU, true parallel execution occurs, with taks distributed across cores
//Os Scheduler balances the load, ensure efficient and responsive system performance

//Multithreading
//ability to execute multiple threads concurrently within a single process
//enhances efficiency of multitasking by breaking down tasks into smaller sub-taks/threads. these threads can be processed simultaneously
// single-core- threads and processes are managed by OS scheduler through time slicing and context switching to create a illusion of simultaneous execution
// multi-core- threads can be executed in parallel on different cores, with OS scheduler distributing tasks across cores for optimal performance
// Advantages of Multithreading
// ✔ Improved performance: Multiple tasks execute simultaneously.
// ✔ Efficient CPU utilization: Threads share CPU resources effectively.
// ✔ Better responsiveness: UI applications remain responsive while performing background tasks.
// ✔ Simplifies complex processes: Parallel execution makes large programs efficient.

//Time Slicing
//divides CPU time into smaller intervals called quanta
//OS scheduler allocates these quata to diff processes and threads, ensuring each gats a fair share of CPU time
//this prevents any single process from monopolizing the CPU and allows for responsive multitasking

//Context Switching
//process of saving the state of a currently running process/thread and loading the state of the next process/thread to be executed
// when a processes/threads quata expires, OS scheduler performs a context switch to move the CPU's focus to another process/thread
//this allows multiple processes/threads to share the CPU, giving appreance of simultaneous execution on a single core CPU or improving performance on multi-core CPU

| Feature         | Multithreading                                    | Multitasking                                    |
|-----------------|---------------------------------------------------|-------------------------------------------------|
| Definition      | Multiple threads run within the same process.     | Multiple processes run simultaneously.          |
| Execution Unit  | Thread (smaller unit of a process).               | Process (independent execution unit).           |
| Resource Sharing| Threads share memory and resources of the process.| Processes have separate memory and resources.   |
| Communication   | Threads communicate via shared memory.            | Processes use Inter-Process Communication (IPC).|
| Overhead        | Less overhead as threads share memory.            | More overhead due to context switching.         |
| Speed           | Faster due to shared resources.                   | Slower as each process requires separate memory.|
| Example         | Within a single project(application), a team(process) of employees(threads) work on diff parts of the project at the same time, collaborating and dharing resources | OfficeManager(OS) assigns diff employees(Process) to work on diffent projects(application) simul, each employee works on a diff project independently

//Java multithreading allows programs to run multiple tasks in parallel, improving performance.
//single core- Java multithreading is managed by JVM and OS, which switch btw threads to give illution of concurrency
//multi core- java multithreading can take full advantage of available cores. JVM distributes threads across cores for true parallel execution of threads
//Java supports multithreading through its java.lang.Thread class and java.lang.Runnable interface
// when a Java program starts, one thread begins running immediately, which is the main thread. This thread is responsible for executing the main method of a program
public static void main(String[] args){
    System.out.println("Hello World!"); 
    System.out.println(Thread.currentThread().getName());//main
}

//Ways to create a thread in java
//1. By extending the Thread class
public class World extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
public class Main {
    public static void main(String[] args) {
        World world = new World();
        world.start(); //Thread-0  -> just the name of the thread
    }
}
//new class world is created that extends Thread
//run method id overridden to define the code that constituted the new thread
//start method is called to initiate the new thread

//2. By implementing the Runnable interface
public class World implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
public class Main {
    public static void main(String[] args) {
        World world = new World();
        Thread t1 = new Thread(world);
        t1.start(); //Thread 0
    }
}
//new class world is created that ectends Thread
//run method id overridden to define the code that constituted the new thread
//a thread obj is created by passing the instance of World
//start method is called on the Thread obj to initiate the new thread

| Feature        | Extending Thread Class                  | Implementing Runnable Interface         |
|----------------|-----------------------------------------|-----------------------------------------|
| Inheritance    | Cannot extend another class.            | Can extend another class.               |
| Memory Usage   | More memory used (Thread object).       | Less memory used (only Runnable object).|
| Flexibility    | Less flexible (tied to Thread).         | More flexible (can be shared among threads).|
| Preferred?     | Not recommended (use only when modifying Thread behavior). | ✅ Best practice (preferred approach). |
// **1st point- Class A extends Class B and we waant to create thread of A then, we cannot use Thread Class as (public class A extends B, Thread) is not correct due to multiple inheritence
// So we have to use Runnable interface (public class A extends B implements Runnable) to create thread of A.**
// **2nd point - memory is same in most cases as we are creating thread obj in both cases
//but Runnable: More decoupled and reusable — you separate the task (Runnable) from the thread (Thread). Thread: The task and the thread are tied together in one class.

//Thread Life Cycle
// 1. New: Thread is created but not yet started. It is in the new state until the start() method is called.
// 2. Runnable: After the start() method is called thread is in runnable state. It is ready to run, but it is not running yet. The thread scheduler chooses a thread from the runnable queue to run.
// 3. Running: Thread is currently executing.
// 4. Blocked: Thread is waiting for a resource (such as a lock, a condition variable, or a socket) to become available.
// 5. Waiting: Thread is waiting for another thread to perform a particular action. This can be due to various reasons such as waiting for a resource, waiting for a notification, or waiting for a certain condition to become true.
// 6. Timed Waiting: Thread is waiting for a certain amount of time to perform a particular action.
// 7. Terminated: Thread has completed execution. It is in the terminated state and cannot be resumed.
public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("RUNNING"); //manually typing runnign state as it actually does not exist in Thread class
        try {
            Thread.sleep(2000); // simulates some work being done by the thread
            //t1 thread sleeps for 2 seconds, making it enter the TIMED_WAITING state.
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        // sleep() might cause exception which is handled using try catch
        //we cannot simply add in the func signeture as run() does not throw error and we are overriding it so we cannot throw error also
    }
    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread(); //new thread has not started executing yet. Main thread is executing this
        System.out.println(t1.getState()); // NEW 
        t1.start();
        System.out.println(t1.getState()); // RUNNABLE
        Thread.sleep(1000); // main thread sleeps for 1 second -> this give a chance for the new thread to execute
        //sleep method might cause InterruptedException, so it is handled in the function signeture
        // When the main thread wakes up, t1 is still sleeping, so the output is TIMED_WAITING.
        System.out.println(t1.getState()); //TIMED WAITING
        t1.join(); // waits for the thread to finish execution -> main thread will wait until t1 finishes
        System.out.println(t1.getState()); //TERMINATED
    }
}

//Thread Methods
//1. start() - starts the thread and calls the run() method
//2. run() - contains the code to be executed by the thread
//3. sleep() - pauses the thread for a specified time (in milliseconds)
class MyThread extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep(1000); // Pause for 1 second
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println(i);
        }
    }
}
public class Main {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        t1.start(); //Prints 1 2 3 4 5 with a 1-second delay between numbers.
    }
}

//4. join() - waits for the thread to finish before continuing with the main thread
class MyThread extends Thread {
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(i);
        }
    }
}
public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        t1.start();
        t1.join(); // Wait for t1 to finish before starting t2
        t2.start();
    }
}

//5. setPriority() - sets the priority of the thread (1-10, 1 is lowest and 10 is highest). The default priority is 5. Higher priority threads are executed before lower priority threads.
class MyThread extends Thread {
    public MyThread(String name) {
        super(name); // Set the thread name
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Priority: " + Thread.currentThread().getPriority());
    }
}
public class Main {
    public static void main(String[] args) {
        MyThread t1 = new MyThread("Low Priority Thread");
        MyThread t2 = new MyThread("Normal Priority Thread");
        MyThread t3 = new MyThread("High Priority Thread");

        t1.setPriority(Thread.MIN_PRIORITY); // Priority = 1
        t2.setPriority(Thread.NORM_PRIORITY); // Priority = 5 (default)
        t3.setPriority(Thread.MAX_PRIORITY); // Priority = 10

        t1.start();
        t2.start();
        t3.start();
    }
    // High Priority Thread Priority: 10
    // Normal Priority Thread Priority: 5
    // Low Priority Thread Priority: 1
//order of execution is NOT guaranteed.
//Higher priority increases the LIKELIHOOD of a thread running earlier or more frequently, but it does not guarantee execution order.
}

//6. interrupt() - interrupts a thread that is in a blocked or waiting state.
//It sets the interrupted status of the thread to true and throws an InterruptedException if the thread is currently sleeping or waiting.
//If a thread is sleeping, waiting, or blocked, calling interrupt() will throw an InterruptedException, which can be caught and handled
//If a thread is running normally, interrupt() simply sets the interrupt flag (Thread.interrupted()) to true, and the thread must check this flag manually.
class MyThread extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("Thread is running...");
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted!");
        }
    }
}
public class Main {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        t1.start();
        t1.interrupt(); 
        //Thread interrupted!
    }
}

//7. Yield() - allows a thread to temporarily pause its execution and give other threads a chance to run.
class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " - " + i);
            if (i == 3) {
                Thread.yield(); // Yielding the thread
            }
        }
    }
}
public class Main {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        t1.start();
        t2.start();
// Thread-0 - 1
// Thread-0 - 2
// Thread-0 - 3
// Thread-1 - 1
// Thread-0 - 4
// Thread-1 - 2
// Thread-1 - 3
// Thread-0 - 5
// Thread-1 - 4
// Thread-1 - 5
}}

// 8. setDeamon - marks a user thread as a daemon thread.
// Daemon threads are background threads that automatically terminate when all user (non-daemon) threads finish execution.
// JVM creates 2 types of thread - user thread and daemon thread
// JVM keeps running as long as at least one user thread is alive.
// When all user threads finish, the JVM shuts down, leadign to automatic termination of all daemon threads.
// Why Does JVM Kill Daemon Threads?
//1. Daemon threads only support user threads; they don’t have critical tasks.
//2. JVM assumes their work is non-essential.
//3. If all user threads finish, JVM has no reason to stay alive.
//4. This prevents wasting system resources.
class MyThread extends Thread {
    @Override
    public void run() {
        while(true){ //infinte loop
            System.out.println("hello world")
        }
    }
}
public class Main {
    public static void main(String[] args) {
        MyThread deamonThread = new MyThread();
        deamonThread.setDaemon(true);
        MyThread t1 = new MyThread();
        t1.start(); 
        deamonThread.start();
        System.out.println("Main thread is finished");
    // t1.start(); starts a user thread, which continuously prints "hello world".
    // deamonThread.start(); starts a daemon thread, which also prints "hello world".
    // Main thread prints "Main thread is finished" and then exits.
    // Since t1 (a user thread) is still running, the JVM does not exit.
    // Both t1 and deamonThread keep printing "hello world" infinitely.

        MyThread deamonThread = new MyThread();
        deamonThread.setDaemon(true);
        deamonThread.start();
        System.out.println("Main thread is finished"); // "Main thread is finished
    // Daemon thread starts running, but since there are no user threads left, the JVM shuts down immediately.
    // The daemon thread does not keep running because daemon threads stop when the JVM exits.
    }
}
//USES OF DEAMON THREADS
// Garbage Collector	Frees unused memory when the JVM is running
// Finalizer Thread	Cleans up objects before garbage collection
// Auto-Save Threads	Saves work periodically (e.g., text editors)
// Logging Threads	    Writes logs in the background

