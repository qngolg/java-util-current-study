package threadstate;

import java.util.concurrent.TimeUnit;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public class Shutdown {


    public static void main(String[] args) throws InterruptedException {


        Runner one = new Runner();
        Thread thread = new Thread(one,"CountThread");
        thread.start();
        //睡眠1秒，main线程对CountThread进行中断，使得CountThread能够感知中断而结束线程
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();
        Runner two = new Runner();
        Thread thread1 = new Thread(two,"CountThread1");
        thread1.start();
        //睡眠1秒，通过cancle()方法进行中断
        TimeUnit.SECONDS.sleep(1);
        two.cancle();

    }

    static class Runner implements  Runnable{

        private long i;
        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()){
                i++;
            }
            System.out.println("count i="+ i);
        }

        public void cancle(){
            on = false;
        }
    }
}
