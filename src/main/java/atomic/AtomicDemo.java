package atomic;

import java.util.concurrent.TimeUnit;

/**
 *
 * volatile  可见性
 * @Author qiangl
 * Created by qgl on 2018/8/10.
 */
public class AtomicDemo {

    private static boolean static_stop = false;

    public static void TestStaticStop() throws InterruptedException {
        Thread thread = new Thread(()->{
            int i = 0;
            while (!static_stop){
                i++;
            }
            System.out.println("thread"+Thread.currentThread().getName()+" 被终止，static_stop值被改变"+static_stop);
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        //主线程 改变了 stop的值
       // thread线程 对stop 没有可见性 线程无法终止
        static_stop = true;
    }

    public static void TestVolatileStop() throws InterruptedException {
        Thread thread = new Thread(() -> {
            int i=0;
            while (!volatie_stop){
                i++;
            }
            System.out.println("thread"+Thread.currentThread().getName()+" 被终止，volatile_stop值被改变"+volatie_stop);
        });
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        volatie_stop = true;
        //主线程 改变了volatie_stop的值 volatile 保证了可见性
        // thread线程 对volatie_stop 有了可见性 线程可以终止

    }


    private static volatile  boolean volatie_stop = false;
    public static void main(String[] args) throws InterruptedException {
        TestStaticStop();
        TestVolatileStop();
    }

}
