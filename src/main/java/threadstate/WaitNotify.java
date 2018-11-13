package threadstate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public class WaitNotify {
    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread wait = new Thread(new Wait(),"waitThread");
        wait.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notify = new Thread(new Notify(),"NotifyThread");
        notify.start();

    }

    static class Wait implements Runnable{

        @Override
        public void run() {
            synchronized (lock){
                //当条件不满足时，继续wait，同时释放lock
                while (flag){
                    try {
                        System.out.println(Thread.currentThread() + " flag is true, wait @"
                                + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread() + " flag is false,running @"
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }


    static class Notify implements Runnable{

        @Override
        public void run() {
            //加锁，获取lock的Monitor
            synchronized (lock){
                System.out.println(Thread.currentThread() + "hold lock, notify @"+
                        new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag = false;
                ThreadStateDemo.SleepUtils.sencond(5);
            }
            synchronized (lock){
                System.out.println(Thread.currentThread() + "hold lock again,sleep @"
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));

                ThreadStateDemo.SleepUtils.sencond(5);
            }
        }
    }
}
