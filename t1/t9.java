//MULTITHREADING CONTINUED
//Deadlock
//A deadlock occurs in concurrent programming when two or more threads are blocked forever, each waiting for the other to release a resource.
//This typically happens when threads hold locks on resources and request additional locks held by other threads.
//For example, Thread A holds Lock 1 and waits for Lock 2, while Thread B holds Lock 2 and waits for Lock 1. Since neither thread can proceed, they remain stuck in a deadlock state.
// Deadlock happen when 4 conditions are met:
//1. Mutual Exclusion: Only one thread can access a resource at a time
//2. Hold and Wait: A thread holding at least one resource is waiting to acquire additional resources held by other thread.
//3. No Preemption: Resources cannot be released unless the process holding them terminates.
//4. Circular Wait: There is a chain of processes, each waiting for a resource held by the next process in the chain.
*** SEE Example again you didnt understant it ***
class Pen {
    public synchronized void writeWithPenAndPaper(Paper paper) {
        System.out.println(Thread.currentThread().getName() + " is using pen " + this + " and trying to use paper " + paper);
        paper.finishWriting();
    }

    public synchronized void finishWriting() {
        System.out.println(Thread.currentThread().getName() + " finished using pen " + this);
    }
}
class Paper {
    public synchronized void writeWithPaperAndPen(Pen pen) {
        System.out.println(Thread.currentThread().getName() + " is using paper " + this + " and trying to use pen " + pen);
        pen.finishWriting();
    }

    public synchronized void finishWriting() {
        System.out.println(Thread.currentThread().getName() + " finished using paper " + this);
    }
}
class Task1 implements Runnable {
    private Pen pen;
    private Paper paper;

    public Task1(Pen pen, Paper paper) {
        this.pen = pen;
        this.paper = paper;
    }

    @Override
    public void run() {
        pen.writeWithPenAndPaper(paper); // thread1 locks pen and tries to lock paper
    }
}
class Task2 implements Runnable {
    private Pen pen;
    private Paper paper;

    public Task2(Pen pen, Paper paper) {
        this.pen = pen;
        this.paper = paper;
    }

    @Override
    public void run() {
        synchronized (pen){
            paper.writeWithPaperAndPen(pen); // thread2 locks paper and tries to lock pen
        }
    }
}
public class DeadlockExample {
    public static void main(String[] args) {
        Pen pen = new Pen();
        Paper paper = new Paper();

        Thread thread1 = new Thread(new Task1(pen, paper), "Thread-1");
        Thread thread2 = new Thread(new Task2(pen, paper), "Thread-2");

        thread1.start();
        thread2.start();
    }
}

//Thread Communication
// without proper Communication mechanism, threads might end up in inefficient busy-waiting states, leading to wastage of cpu resources and deadlocks
//done using 3 methods- wait, notify, notifyAll. These methods can only be called in a sync block
// wait() - Makes a thread wait until another thread notifies it.
// notify() - Wakes up a single waiting thread.
// notifyAll() - Wakes up all waiting threads.
class SharedResource {
    private int data;
    private boolean hasData;

    public synchronized void produce(int value) {
        while (hasData) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        data = value;
        hasData = true;
        System.out.println("Produced: " + value);
        notify();
    }

    public synchronized int consume() {
        while (!hasData){
            try{
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        hasData = false;
        System.out.println("Consumed: " + data);
        notify();
        return data;
    }
}
class Producer implements Runnable { // Producer Thread
    private SharedResource resource;
    public Producer(SharedResource resource) {
        this.resource = resource;
    }
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            resource.produce(i);
        }
    }
}
class Consumer implements Runnable { // Consumer Thread
    private SharedResource resource;
    public Consumer(SharedResource resource) {
        this.resource = resource;
    }
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            int value = resource.consume();
        }
    }
}
public class ThreadCommunication {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();
        Thread producerThread = new Thread(new Producer(resource));
        Thread consumerThread = new Thread(new Consumer(resource));

        producerThread.start();
        consumerThread.start();
        //OR 
        //no need to write producer and consumer classes
        Thread producerThread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                resource.produce(i);
            }
        });

        Thread consumerThread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                resource.consume();
            }
        });
        producerThread.start();
        consumerThread.start();
    }
}
//OR
class MessageBox {
    private String message;
    private boolean hasMessage = false;

    public synchronized String getMessage() { // Method for Consumer (waiting for message)
        while (!hasMessage) {
            try {
                wait(); // (Consumer) waits if there's no message available.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        hasMessage = false;
        notify(); // Notify producer that message was read
        return message;
    }

    public synchronized void putMessage(String message) { // Method for Producer (adding message)
        while (hasMessage) {
            try {
                wait(); //(Producer) waits if a message is not yet read.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.message = message;
        hasMessage = true;
        notify(); // Notify consumer that a new message is available
    }
}
public class ThreadCommunicationExample {
    public static void main(String[] args) {
        MessageBox box = new MessageBox();
        Thread producer = new Thread(() -> { // Producer Thread - Produces messages one by one and adds them to MessageBox.
            String[] messages = {"Hello", "How are you?", "Goodbye"};
            for (String msg : messages) {
                box.putMessage(msg);
                System.out.println("Produced: " + msg);
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            }
        });
        Thread consumer = new Thread(() -> { // Consumer Thread - Reads messages from MessageBox.
            for (int i = 0; i < 3; i++) {
                String msg = box.getMessage();
                System.out.println("Consumed: " + msg);
                try { Thread.sleep(1500); } catch (InterruptedException e) {}
            }
        });
        producer.start();
        consumer.start();
        // Produced: Hello
        // Consumed: Hello
        // Produced: How are you?
        // Consumed: How are you?
        // Produced: Goodbye
        // Consumed: Goodbye
    }
}

//Thread Safty
// a thread is safe when multiple threadas are trying to access a resource and unexpected results or race conditions do not occur.
// There are four ways to achieve Thread Safety in Java. These are: Synchronization ,Volatile Keyword ,Atomic Variable, Final Keyword

//Lamda Expressions
// A lambda expression is a short-cut way to write anonymous methods (functions) in Java.
// It’s used mainly to implement interfaces with a single abstract method (called functional interfaces).
interface Greeting {
    void sayHello();
}
Greeting g = new Greeting() { //without lambda expression
    @Override
    public void sayHello() {
        System.out.println("Hello!");
    }
};
Greeting g = () -> System.out.println("Hello!"); //with lambda expression

//Thread Pooling
//Thread Pooling is a technique where a group of worker threads are created in advance and reused to execute multiple tasks.
// Instead of creating a new thread every time (which is costly), we borrow a thread from the pool, use it, and return it.
// 🔹 Why use Thread Pooling?
// ✅ Better performance
// ✅ Avoid overhead of thread creation/destruction
// ✅ Avoid running out of system resources
// ✅ Helps manage concurrency efficiently
