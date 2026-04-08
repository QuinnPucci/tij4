/*
Exercise 15: (1) Create a class with three methods containing critical sections
that all synchronize on the same object.
Create multiple tasks to demonstrate that only one of these methods can run at a time.
Now modify the methods so that each one synchronizes on a different object and
show that all three methods can be running at once.
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreeCriticalMethods {

    // These are the lock objects.
    // A synchronized block needs some object to "lock on."
    // Here, each method is using a DIFFERENT lock object.
    //
    // That means:
    // - cm1() locks criticalObj
    // - cm2() locks criticalObj2
    // - cm3() locks criticalObj3
    //
    // Since they are different locks, the three methods do NOT block each other.
    // That is why all three critical sections can run at the same time.
    private Integer criticalObj = new Integer(0),
            criticalObj2 = new Integer(1),
            criticalObj3 = new Integer(2);

    public void cm1() {

        // synchronized(criticalObj) means:
        // "Before entering this block, a thread must acquire the lock
        // associated with criticalObj."
        //
        // If another thread already holds that SAME lock, this thread must wait.
        //
        // Important:
        // Threads only block each other if they synchronize on the SAME object.
        synchronized(criticalObj) {

            // Once inside this block, this thread has exclusive access
            // to the code protected by criticalObj.
            System.out.println("Starting critical section of cm1");

            // Sleep is just here to make the timing visible.
            // The thread still HOLDS the lock while sleeping.
            // That means no other thread can enter a synchronized block
            // using this same lock object until sleep finishes and the block ends.
            try {
                Thread.sleep(1000);
            } catch (Exception e) { }

            System.out.println("Ending critical section of cm1");

            // When execution leaves this synchronized block,
            // the lock on criticalObj is automatically released.
        }
    }

    public void cm2() {

        // This method synchronizes on a DIFFERENT object: criticalObj2
        // So even if cm1() is currently running, cm2() can still enter,
        // because it is asking for a different lock.
        synchronized(criticalObj2) {
            System.out.println("Starting critical section of cm2");

            try {
                Thread.sleep(1000);
            } catch (Exception e) { }

            System.out.println("Ending critical section of cm2");
        }
    }

    public void cm3() {

        // Same idea again:
        // cm3() synchronizes on criticalObj3, which is separate
        // from the locks used in cm1() and cm2().
        //
        // Because all three methods use different lock objects,
        // they are allowed to run concurrently.
        synchronized(criticalObj3) {
            System.out.println("Starting critical section of cm3");

            try {
                Thread.sleep(1000);
            } catch (Exception e) { }

            System.out.println("Ending critical section of cm3");
        }
    }

    public static void main(String args[]) {

        // One shared ThreeCriticalMethods object.
        // All tasks will call methods on this same object.
        final ThreeCriticalMethods test = new ThreeCriticalMethods();

        // Cached thread pool:
        // The executor manages worker threads for us.
        // We submit tasks, and the executor decides what threads run them.
        ExecutorService exec = Executors.newCachedThreadPool();

        // Submit first task:
        // this task calls cm1()
        exec.execute(new Thread() {
            public void run() { test.cm1(); }
        });

        // Submit second task:
        // this task calls cm2()
        exec.execute(new Thread() {
            public void run() { test.cm2(); }
        });

        // Submit third task:
        // this task calls cm3()
        exec.execute(new Thread() {
            public void run() { test.cm3(); }
        });

        // Tell the executor:
        // "Do not accept any new tasks after this point."
        // Already submitted tasks are still allowed to finish.
        exec.shutdown();
    }
}