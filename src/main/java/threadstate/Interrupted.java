package threadstate;

import java.util.concurrent.TimeUnit;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public class Interrupted {

    public static void main(String[] args) throws InterruptedException {

        //sleepThread不停的尝试睡眠
        Thread sleepThread = new Thread(new SleepRunnable(),"sleepThread");
        sleepThread.setDaemon(true);

        //busyThread不停的运行
        Thread busyThread = new Thread(new BusyRunner(),"busyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();
        //休眠5秒，让sleepThread和busyThread充分运行
        TimeUnit.SECONDS.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        //防止sleepThread和busyThread立刻退出
        ThreadStateDemo.SleepUtils.sencond(2);
    }


    static class SleepRunnable implements Runnable{

        @Override
        public void run() {
            while (true){
                ThreadStateDemo.SleepUtils.sencond(10);
            }
        }
    }


    static class BusyRunner implements  Runnable{
        @Override
        public void run() {
            while (true){
            }
        }
    }
}
