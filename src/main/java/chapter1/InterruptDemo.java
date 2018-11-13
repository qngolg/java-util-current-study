package chapter1;

import java.util.concurrent.TimeUnit;

/**
 * @Author qiangl
 * Created by qgl on 2018/8/10.
 */
public class InterruptDemo {

    private static int i;

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() ->{
           while (!Thread.currentThread().isInterrupted()){
               i++;
           }
            System.out.println(i);
        },"interruptDemo");
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt(); //设置interrupt表示为true
        System.out.println(thread.isInterrupted());
    }
}
