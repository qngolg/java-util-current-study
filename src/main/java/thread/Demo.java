package thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 5个线程 3个先执行 2个后执行 5种实现
 * 第一种：锁+共享变量；第二种：single：第三种：join；第四种：atomic
 * @Author qiangl
 * Created by qgl on 2018/8/28.
 */
public class Demo {

    public static void  control1() throws InterruptedException {
        Thread t1 = new Thread(()-> System.out.println("这是线程1"));
        Thread t2 = new Thread(()-> System.out.println("这是线程2"));
        Thread t3 = new Thread(()-> System.out.println("这是线程3"));
        Thread t4 = new Thread(()-> System.out.println("这是线程4"));
        Thread t5 = new Thread(()-> System.out.println("这是线程5"));

        t1.start();
        t2.start();
        t3.start();
        t1.join();t2.join();t3.join(); //join方法 Waits for this thread to die. 等待该线程终止再继续让下执行
        t4.start();
        t5.start();
    }


    //利用CountDownLatch实现
    public static void methodByCountDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Thread t1 = new Thread(()-> {System.out.println("这是线程1");countDownLatch.countDown();});
        Thread t2 = new Thread(()-> {System.out.println("这是线程2");countDownLatch.countDown();});
        Thread t3 = new Thread(()-> {System.out.println("这是线程3");countDownLatch.countDown();});
        Thread t4 = new Thread(()-> System.out.println("这是线程4"));
        Thread t5 = new Thread(()-> System.out.println("这是线程5"));

        t1.start();t2.start();t3.start();
        countDownLatch.await();
        t4.start();t5.start();
    }

    public static void methodByThreadPool(){

        //依次加入线程池，按线程池运行
//        ExecutorService executor = new ThreadPoolExecutor(3,3,3,TimeUnit.SECONDS,
//                new ArrayBlockingQueue<>(2));
//        executor.execute(()-> System.out.println("这是线程1"));
//        executor.execute(()-> System.out.println("这是线程2"));
//        executor.execute(()-> System.out.println("这是线程3"));
//        executor.execute(()-> System.out.println("这是线程4"));
//        executor.execute(()-> System.out.println("这是线程5"));

        ExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.submit(()-> System.out.println("这是线程1"));
        service.submit(()-> System.out.println("这是线程2"));
        service.submit(()-> System.out.println("这是线程3"));
        service.submit(()-> System.out.println("这是线程4"));
        service.submit(()-> System.out.println("这是线程5"));
        service.shutdown();

    }

    // 利用原子变量atomic 与countDownLacth原理相同
    public static void methodByAtomic() throws InterruptedException {
        AtomicInteger a = new AtomicInteger(0);
        Thread t1 = new Thread(()-> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("这是线程1");a.getAndIncrement();});
        Thread t2 = new Thread(()-> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("这是线程2");a.getAndIncrement();});
        Thread t3 = new Thread(()-> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("这是线程3");
            a.getAndIncrement();
        });
        Thread t4 = new Thread(()-> {
            System.out.println("这是线程4");
        });
        Thread t5 = new Thread(()-> {
            System.out.println("这是线程5");
        });

        t1.start();t2.start();t3.start();
        while (true){
            if(a.get() == 3){
                t4.start();t5.start();
                break;
            }
        }
    }

    //通过Lock condition实现
    public static void methodByCondition(){

        Lock lock = new ReentrantLock();
        Condition c1 = lock.newCondition();

        Thread t1 = new Thread();

    }


    public static void main(String[] args) throws InterruptedException {

        //方法1 join
//        control1();
        //方法2 CountDownLatch
//        methodByCountDownLatch();
        //方法3 线程池
//          methodByThreadPool();
        //方法4 原子变量
        methodByAtomic();
    }
}
