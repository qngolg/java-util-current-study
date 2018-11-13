package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 独占锁
 * 在同一时刻只能有一个线程获取到锁，
 * 而其他获取锁的线程只能处于同步队列等待
 * 只有获取锁的线程释放了锁，后续的线程才能获取到锁
 * @Author qiangl
 * Created by qgl on 2018/9/4.
 */
public class Mutex implements Lock {

    //静态内部类，自定义同步器，继承AQS（队列同步器）
    private static class Sync extends AbstractQueuedSynchronizer{
        //判断锁是否处于占用状态（getState()==1）
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        //当状态为0的时候获取锁
        public boolean tryAcquire(int acquires){
            //判断状态是不是0，如果是0 则将状态设置为1 占用该锁
            if(compareAndSetState(0,1)){
                //设置拥有锁的线程为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //释放锁
        @Override
        protected boolean tryRelease(int arg) {
            //如果状态为0，锁已被释放
            if(getState() == 0){
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
        //返回一个Condition,每个condition都包含了一个condition队列
        Condition newCondition(){
            return new ConditionObject();
        }
    }


    //所有锁的操作代理到Sync上
    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }


    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
