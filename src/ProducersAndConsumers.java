/*
Exercise 24: (1) Solve a single-producer, single-consumer problem using wait( ) and notifyAll( ).
The producer must not overflow the receiver’s buffer, which can happen if the producer is faster than the consumer.
If the consumer is faster than the producer, then it must not read the same data more than once.
Do not assume anything about the relative speeds of the producer or consumer.
 */

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class ProducersAndConsumers {

    public static void main(String[] args) {
        // Create one shared Restaurant object.
        // Both the producer (Chef) and consumer (waitPerson) will work with it.
        Restaurant rest = new Restaurant();

        // Create a thread for each worker.
        // t1 runs the Chef's run() method.
        // t2 runs the Consumer's run() method.
        Thread t1 = new Thread(rest.chef);
        Thread t2 = new Thread(rest.waitPerson);

        // ---------- Test 1 ----------
        // Producer is faster than consumer.
        // This is testing whether the queue fills up properly,
        // and whether the Chef waits instead of overflowing the buffer.
        System.out.println("Test 1: chef makes meals faster");
        rest.chef.speed = 1;        // Chef makes a meal every 1 second
        rest.waitPerson.speed = 3;  // Consumer eats every 3 seconds
        t1.start();
        t2.start();

        // Wait until both threads finish before starting the next test.
        // join() means: "main thread, do not continue until this thread ends."
        try {
            t1.join();
            t2.join();
        }
        catch (Exception e) { }

        // ---------- Test 2 ----------
        // Consumer is faster than producer.
        // This checks whether the consumer waits correctly instead of
        // trying to consume meals that do not exist.
        System.out.println();
        System.out.println("Test 2: waitPerson eats meal faster");

        // Reset shared/static values for a fresh second test.
        Meal.counter = 0;
        rest.chef.speed = 3;          // Chef slower now
        rest.waitPerson.speed = 2;    // Consumer faster now
        rest.waitPerson.mealsAte = 0; // Reset consumer meal count

        // Start the Chef first.
        new Thread(rest.chef).start();

        // Wait a while so the Chef can build up a couple of meals first.
        // This just helps demonstrate a different timing pattern.
        try {
            TimeUnit.SECONDS.sleep(6);
        }
        catch (Exception e) { }

        // Now start the Consumer.
        new Thread(rest.waitPerson).start();
    }
}

class Restaurant {
    // MAX_MEAL is the capacity of the shared buffer.
    // The queue should never hold more than 3 meals at a time.
    final public static int MAX_MEAL = 3;

    // Shared buffer between producer and consumer.
    // Chef adds Meal objects here.
    // Consumer removes Meal objects from here.
    public LinkedList<Meal> queue = new LinkedList<Meal>();

    public Chef chef;
    public Consumer waitPerson;

    public Restaurant() {
        // Both workers receive the same Restaurant object
        // so they share the same queue.
        chef = new Chef(this);
        waitPerson = new Consumer(this);
    }
}

class Chef implements Runnable {
    public Restaurant restaurant;
    public int speed;

    public Chef(Restaurant r) {
        this.restaurant = r;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {

                // ---------------- WAIT CONDITION ----------------
                // This synchronized(this) block means:
                // "Lock the Chef object before checking/changing a condition
                // related to Chef waiting."
                //
                // If the queue is already full, Chef should stop producing.
                // Otherwise, Chef could overflow the buffer.
                synchronized(this) {
                    if (restaurant.queue.size() == Restaurant.MAX_MEAL) {
                        System.out.println("Max. numbers of meal reached, chef will wait");

                        // wait() does two things:
                        // 1. puts this thread to sleep
                        // 2. releases the lock on "this" so another thread can wake it later
                        //
                        // Chef will stay blocked here until another thread calls
                        // notifyAll() on this same Chef object.
                        wait();
                    }
                }

                // ---------------- PRODUCE A MEAL ----------------
                // Synchronize on the shared queue before modifying it.
                // This prevents Chef and Consumer from changing the queue at the same time.
                synchronized(restaurant.queue) {
                    Meal meal = new Meal();
                    restaurant.queue.offer(meal); // add meal to end of queue
                    System.out.println("Made " + meal);
                }

                // ---------------- WAKE THE CONSUMER ----------------
                // After producing, Chef wakes the Consumer.
                // Why? Because the Consumer may be waiting for food.
                //
                // Important rule:
                // wait() and notifyAll() must happen on the SAME monitor object.
                // Since Consumer waits on "this" inside Consumer.run(),
                // Chef must notify on restaurant.waitPerson.
                synchronized(restaurant.waitPerson) {
                    restaurant.waitPerson.notifyAll();
                }

                // Stop condition for this demo.
                if (Meal.counter >= 10) {
                    System.out.println("10 meals made, chef stopped");
                    break;
                }

                // Pause to simulate production time.
                TimeUnit.SECONDS.sleep(speed);
            }
        }
        catch (InterruptedException e) {
            System.out.println("Chef interrupted!");
        }
    }
}

class Consumer implements Runnable {
    public int mealsAte = 0;
    public int speed;
    public Restaurant restaurant;

    public Consumer(Restaurant r) {
        this.restaurant = r;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {

                // ---------------- WAIT CONDITION ----------------
                // Lock the Consumer object before checking whether it should wait.
                //
                // If the queue is empty, Consumer must stop and wait.
                // Otherwise it would try to read a meal that does not exist.
                synchronized(this) {
                    if (restaurant.queue.size() == 0) {
                        System.out.println("No meal in queue, waitPerson will wait");

                        // Consumer sleeps here until another thread
                        // calls notifyAll() on this Consumer object.
                        wait();
                    }
                }

                // ---------------- CONSUME A MEAL ----------------
                // Lock the shared queue before removing from it.
                // This keeps queue access safe between producer and consumer.
                synchronized(restaurant.queue) {
                    Meal meal = restaurant.queue.poll(); // remove from front of queue
                    mealsAte++;
                    System.out.println("Ate " + meal);
                }

                // ---------------- WAKE THE CHEF ----------------
                // After eating a meal, Consumer wakes the Chef.
                // Why? Because the queue may now have space again.
                //
                // Chef waits on "this" inside Chef.run(),
                // so Consumer must notify on restaurant.chef.
                synchronized(restaurant.chef) {
                    restaurant.chef.notifyAll();
                }

                // Stop condition for this demo.
                if (mealsAte >= 10) {
                    System.out.println("10 meals ate, waitPerson stopped");
                    break;
                }

                // Pause to simulate consuming time.
                TimeUnit.SECONDS.sleep(speed);
            }
        }
        catch (InterruptedException e) {
            System.out.println("Consumer interrupted!");
        }
    }
}

class Meal {
    // Static counter shared by all Meal objects.
    // Each new Meal gets the next number.
    public static int counter = 0;

    private final int orderNum;

    public Meal() {
        orderNum = counter++;
    }

    public String toString() {
        return "Meal " + orderNum;
    }
}