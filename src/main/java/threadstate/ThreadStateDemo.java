package threadstate;

import java.util.concurrent.TimeUnit;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public class ThreadStateDemo {

    public static void main(String[] args) {

        new Thread(new TimeWaiting(),"timeWaiting").start();;
        new Thread(new Waiting(),"waiting").start();

        //使用Blocked线程
        new Thread(new Blocked(),"BlockedThread-1").start();
        new Thread(new Blocked(),"BlockedThread-2").start();

    }

    static class TimeWaiting implements Runnable{

        @Override
        public void run() {
            while (true){
                SleepUtils.sencond(1000);
            }
        }
    }


    static class Waiting implements Runnable{

        @Override
        public void run() {
            while (true){
                synchronized (Waiting.class){
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }


    static class Blocked implements Runnable{

        @Override
        public void run() {
            synchronized (Blocked.class){
                while(true){
                    SleepUtils.sencond(100);
                }
            }
        }
    }


    static class SleepUtils{
        public static final void sencond(long seconds){

            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
