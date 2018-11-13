package chapter1;

import java.util.concurrent.TimeUnit;

/**
 * @Author qiangl
 * Created by qgl on 2018/8/10.
 */
public class ThreadInterruptDemo {

    //Thread.interrupted();  唤醒中断线程
//    public static void main(String[] args) throws InterruptedException {
//
//        Thread thread= new Thread(() -> {
//            while (true){
//                boolean in = Thread.currentThread().isInterrupted();
//                if(in){
//                    System.out.println("before" + in);
//                    Thread.interrupted();
//                    System.out.println("after:"+Thread.currentThread().isInterrupted());
//                }
//            }
//        });
//        thread.start();
//        TimeUnit.SECONDS.sleep(1);
//        thread.interrupt(); //中断
//    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("after :" + Thread.currentThread().isInterrupted());
                }
            }
        });

        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();
        TimeUnit.SECONDS.sleep(1);
    }
}
