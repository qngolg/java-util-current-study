package threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {

        /**
         * 线程池的设计原则
         * 当核心线程数都用满的时候，剩下的线程会存入线程队列中去，
         * 当等待队列满的时候，线程池会创建新的线程，（此时最大线程数没到）
         * 当创建的线程数量大于最大线程数的时候 线程池会封锁入口，不允许新的线程进入
         *
         *
         * int corePoolSize, 核心线程数  设计思路 （（线程等待时间+线程CPU时间）/线程CPU时间 ）* CPU数目
         * int maximumPoolSize, 最大线程数
         * long keepAliveTime,  闲置线程存活时间
         * TimeUnit unit,   时间单位
         * BlockingQueue<Runnable> workQueue 等待队列
          */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,20,30,TimeUnit.SECONDS,new ArrayBlockingQueue<>(20));





    }
}
