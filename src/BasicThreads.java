/*
Exercise 1: (2) Implement a Runnable. Inside run( ), print a message,
and then call yield( ). Repeat this three times, and then return from run( ).
Put a startup message in the constructor and a shutdown message when
the task terminates. Create a number of these tasks and drive them using threads.
 */
public class BasicThreads {
    public static void main(String[] args){
        for (int i = 1; i <=3; i++){
            new Thread(new ConcurrentTask(i)).start();
        }
    }

}

class ConcurrentTask implements Runnable {
    public int id;

    public ConcurrentTask(int id) {
        this.id = id;
        System.out.println("Thread starting. ID: " + id);
    }

    @Override
    public void run(){
        for (int i = 1; i <= 3; i++) {
            System.out.println("Task " + id + " running, pass " + i);
            Thread.yield();
        }
        System.out.println("Task shutting down. ID: " + id);
    }
}
