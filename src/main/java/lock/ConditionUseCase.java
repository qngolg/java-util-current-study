package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public class ConditionUseCase {


    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread thread = new Thread(()->{
            try {
                System.out.println("await线程 lock前");
                lock.lock();
                System.out.println("await线程 lock后 await前");
                condition.await();
                System.out.println("await线程 lock后 await后");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
                System.out.println("await线程解锁了");
            }

        },"await线程");
        thread.start();

        Thread thread1 = new Thread(()->{
            try {
                System.out.println("signal线程 lock钱");
                lock.lock();
                condition.signal();
                System.out.println("signal线程 signal后");
            }finally {
                lock.unlock();
                System.out.println("signal线程解锁了");
            }

        },"signal线程");
        thread1.start();
    }

}
