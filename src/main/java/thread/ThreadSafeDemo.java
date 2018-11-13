package thread;

import java.util.concurrent.CountDownLatch;

/**
 * @Author qiangl
 * Created by qgl on 2018/8/29.
 */
public class ThreadSafeDemo {

    static int a = 0;
    public static void main(String[] args) throws InterruptedException {

        int size = 1000000;
        CountDownLatch latch = new CountDownLatch(size);
        for (int i =0 ; i<size ;i++){
            new Thread(()->{
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a++;
            }).start();
            latch.countDown();
        }
        Thread.sleep(10000);
        System.out.println(a);

    }

}
