import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorTester {
    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 1; i <=3; i++){
            exec.execute(new ConcurrentTask(i));
        }
        exec.shutdown();
    }
}