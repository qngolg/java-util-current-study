package chapter1;

import java.util.concurrent.*;

/**
 * @Author qiangl
 * Created by qgl on 2018/8/10.
 */
public class CallableDemo implements Callable
{


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        CallableDemo callableDemo = new CallableDemo();
        Future<String> future = executorService.submit(callableDemo);
        String rs = future.get();
        System.out.println(rs);
        executorService.shutdown();

    }

    public Object call() throws Exception {
        return "Stirng" +1;
    }
}
