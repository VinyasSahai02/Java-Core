//Exceutors Framework
//used to create thread pools
// Introduced in Java 5 as part of the java.util.concurrent package to simplify the development of concurrent applications by abstracting away many of the complexities involved in creating and managing threads.
// It will help in- Avoiding Manual Thread management, Resource management, Scalability, Thread reuse, Error handling
// 🔹 Core Components of Executors Framework
// Executor -> The base interface — runs submitted tasks
// ExecutorService -> Subinterface — adds methods for managing lifecycle and task results
// ScheduledExecutorService -> Executes tasks after a delay or periodically
// Executors -> A utility class to create executor services

//without multithreading
public static long factorial(int n) {
    try{
        Thread.sleep(1000); // Simulating a time-consuming task
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    if (n <= 1) return 1;
        return n * factorial(n - 1);
}
public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    for(int i = 1; i < 10; i++) {
        System.out.println(factorial(i));
    }
    system.out.println("Time taken: " + (System.currentTimeMillis() - startTime)); //around 9000ms
}

// with multithreading
public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    for(int i = 1; i < 10; i++) {
        int finalI = i; // Final variable for lambda expression
        Thread thread = new Thread(() -> {
            long result = factorial(finalI); //if we put "i" in factorial(i) it will give error as variable in lamda exp should be final 
            //lets say some thread does not start, but when it starts it takes a different value of i, so we have to make it final
            System.out.println(result);
        });
        thread.start();
    }
    system.out.println("Time taken: " + (System.currentTimeMillis() - startTime));
}
//but this code will not wait for all threads to finish before printing time taken, we fix this by
public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    Thread[] threads = new Thread[9]; // Array to hold threads or // to store thread references
    for(int i = 1; i < 10; i++) {
        int finalI = i;
        Thread thread = new Thread(() -> {
            long result = factorial(finalI);
            System.out.println(result);
        });
        threads[i-1]=thread
        thread.start();
    }
    for (Thread thread : threads) { // Wait for all threads to finish
        thread.join();
    }
    system.out.println("Time taken: " + (System.currentTimeMillis() - startTime)); //almost 1000ms
}

//with multithreading we are manually creating threads and also not reusing threads, here comes in the Executor Framework
//ExecutorService
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    ExecutorService executor = Executors.newFixedThreadPool(9); // Create a thread pool with 9 threads
    //we can also put 3 in Executors.newFixedThreadPool(3), this means that now 3 thread will take 3 numbers each
    for(int i = 1; i < 10; i++) {
        int finalI = i;
        executor.submit(() -> {
            long result = factorial(finalI);
            System.out.println(result);
        });
    }
    executor.shutdown(); // Shutdown the executor after all tasks are submitted
    // we cannot do executor.submit() again after the shutdown statement
    try{
        executor.awaitTermination(10, TimeUnit.SECONDS); // Wait for 10sec for all tasks to finish, without this timetaken will print first
        //for unlimited waiting
        //ExecutorService class has a lot of other func like shutdownNow(), isShutdown(), isTerminated(), invokeAll(), invokeAny(), etc 
        while(!executor.awaitTermination(10, TimeUnit.MILISECONDS)){
            System.out.println("Waiting for tasks to finish...");
        }
    }
    catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    system.out.println("Time taken: " + (System.currentTimeMillis() - startTime)); //almost 1000ms
}
// the above func can also be written using Executor class-
public static void main(String[] args) {
    Executor executor = Executors.newFixedThreadPool(9);
    for(int i = 1; i < 10; i++) {
        int finalI = i;
        executor.execute(() -> {
            long result = factorial(finalI);
            System.out.println(result);
        });
    }
}

//the Executor class has void execute(Runnable obj) method
// the ExecutorService class has Future<?> submit(Runnable obj) method
// execute(Runnable task) — fire and forget, no return value, no exception handling, cannot retrieve result
// submit(Callable or Runnable) — returns a Future so you can retrieve the result or handle exceptions
//in the ExecutorService class, if we do Alt+Enter on the submit func, we get
public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(9);
    for(int i = 1; i < 10; i++) {
        int finalI = i;
        Future<?> future = executor.submit(() -> {
            long result = factorial(finalI);
            System.out.println(result);
            //if the method written inside returns something, then we can get that using future
            //if we want to check if the inside func is successful or not, we can use future
            //if we want to wait for the inside func computation then we can use future
        });
    }
}
//simple example
public static void main(String[] args) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(()->{return 42;})
    //when we Alt+Enter, this will happen
    Future<Integer> future = executorService.submit(() -> 42);
    System.out.println(future.get()); // 42 -> get() waits until the task is done and returns the result

    Future<?> future = executorService.submit(() -> System.out.println("Hello from thread!"));
    if(future.isDone()){ //future also has multiple methods
        System.out.println("Task is done!");
    }
    System.out.println(future.get()); // Hello null
    
    // if the above code is wriiten as this
    Future<?> future = executorService.submit(() -> System.out.println("Hello from thread!"));
    System.out.println(future.get());
    if(future.isDone()){
        System.out.println("Task is done!");
    } // Hello null Task is done!
    executorService.shutdown();
}

//Callable and Runnable in submit() method
// When you use Runnable, you’re just asking the thread to perform a task. It doesn't return a result, but you can still get a Future<?> to track the task status (e.g., whether it's done)
ExecutorService executor = Executors.newSingleThreadExecutor();
Runnable task = () -> {
    System.out.println("Runnable task is running...");
};
Future<?> future = executor.submit(task); // We can wait for it to complete
future.get();  // returns null (Runnable doesn’t return result)

//Callable<V> is like a Runnable but it returns a value and can throw exceptions.
ExecutorService executor = Executors.newSingleThreadExecutor();
Callable<Integer> task = () -> {
    Thread.sleep(1000);
    return 42; // Result
};
Future<Integer> future = executor.submit(task);
System.out.println("Result: " + future.get()); // prints: Result: 42

| Feature                | Runnable                         | Callable<V>                      |
|------------------------|----------------------------------|----------------------------------|
| Return Type            | void                             | V (generic return type)          |
| Method to Override     | run()                            | call()                           |
| Can Throw Exception?   | No (only unchecked exceptions)   | Yes (checked exceptions allowed) |
| Returns a Result?      | No                               | Yes                              |
| Used With              | Thread, ExecutorService          | ExecutorService                  |
| submit() Return Type   | Future<?>                        | Future<V>                        |

//there is 1 more submit method for ExecutorService-> submit(Runnable task, V result)
//here future.get() will return the result passed in the submit method, not the result of the task -> since Runnable does not return anything, we can use this

//Another example -> Some more methods of ExecutorService class
ExecutorService executor = Executors.newFixedThreadPool(2);
Future<Integer> future = executor.submit(()-> 1+2);
System.out.println("Sum is: " + future.get()); // Sum is: 3\
executor.shutdown();
System.out.println(executor.isShutdown()); // true
// System.out.println(executor.isTerminated()); // false ->returns true if all tasks have completed following shut down
Thread.sleep(1);
System.out.println(executor.isTerminated()); // true -> earlier was returning false beacuse we were not giving it time

Callable<Integer> callable1 = () -> 1;
Callable<Integer> callable2 = () -> 2;
Callable<Integer> callable3 = () -> 3;
List<Callable<Integer>> list = Arrays.asList(callable1, callable2, callable3); //->aslist convert array to list
List<Future<Integer>> futures = executor.invokeAll(list); //-> invokeAll() executes all task and returns a list of Futures holding there results and status
for(Future<Integer> future : futures) {
    System.out.println(future.get());
}
System.out.println("hello"); // this will be printed in the end by default because invokeAll() block the main thread until all tasks are completed -> 1 2 3 Hello
//invokeAll(list, timeout, timeUnits) -> this will wait for the given time and then return the result of the tasks that have completed within that time, if not completed then wont print anything
//invokeAny(list) -> this will return the result(NOT THE FUTURE) of the first task that completes successfully, if all tasks fail then it will throw an exception, if no task is given then it will throw an exception


//ScheduledExecutorService
//this class extends executorService
// used to schedule tasks to run: after a delay, or periodically at a fixed rate or with a fixed delay.
public static void main(String[] args) {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> scheduledFuture = scheduler.schedule(() -> { //takes both Runnable and Callable
        System.out.println("done after 10 seconds");
    }, 10, TimeUnit.SECONDS);
    scheduler.shutdown();

    //scheduleAtFixedRate(task,initialDelay,period,unit) -> this will run the task at a fixed rate, if the task takes more time than the given time then it will not wait for the task to finish and will start the next task
    scheduler.scheduleAtFixedRate(() -> {
        System.out.println("running every 3 seconds");
    }, 0, 3, TimeUnit.SECONDS);
    scheduler.schedule(() -> {
        System.out.println("Shutting down scheduler...");
        scheduler.shutdown();
    }, 10, TimeUnit.SECONDS);
    //we cant just do scheduler.shutdown() because there's a very high chance the repeating task won’t run even once and the scheduler Immediately shuts down
    
    //scheduleWithFixedDelay(task,initialDelay,delay,unit) -> this will run the task at a fixed rate, if the task takes more time than the given time then it will wait for the remaining time of the previous task before starting the next task
    scheduler.scheduleWithFixedDelay(() -> {
        System.out.println("running every 3 seconds with delay");
    }, 0, 3, TimeUnit.SECONDS);
    scheduler.schedule(() -> {
        System.out.println("Shutting down scheduler...");
        scheduler.shutdown();
    }, 10, TimeUnit.SECONDS);

    // scheduleAtFixedRate() -> Ignores task duration, tries to keep a fixed rate
    // scheduleWithFixedDelay() -> Always waits for task to finish, then delays
}

//Executor.newCachedThreadPool()
// Creates a thread pool with:
// 1.An unbounded number of threads (limited by system resources).
// 2.Reuses idle threads (if available).
// 3.Creates new threads as needed if no existing thread is free.
// 4.Removes threads that are idle for 60 seconds.
//Use this when -> Execute many short tasks, Don't need a fixed number of threads.
//dont use when -> You want to limit concurrency (use newFixedThreadPool() instead). You have long-running tasks (it might create too many threads → resource exhaustion).


Topics Left
//CountDownLatch
//Cyclic Barrier
//CompletableFuture
