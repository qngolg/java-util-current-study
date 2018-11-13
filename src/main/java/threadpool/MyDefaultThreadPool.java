package threadpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public class MyDefaultThreadPool <Job extends Runnable> implements MyThreadPool{




    //线程最大限制数
    private static final int MAX_WORKER_NUMBERS = 10;
    //线程池默认的数量
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    //线程池最小的数量
    private static final int MIN_WORKER_NUMBERS = 1;
    //工作列表，将会向里面插入工作
    private final LinkedList<Job> jobs = new LinkedList<>();
    //工作者列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());
    //工作者线程的数量
    private int workerNum = DEFAULT_WORKER_NUMBERS;
    //线程编号
    private AtomicLong threadNum = new AtomicLong();

    public MyDefaultThreadPool(){
        //初始化线程工作者
        initializeWorkers(DEFAULT_WORKER_NUMBERS);
    }

    public MyDefaultThreadPool(int num){
        workerNum = num > MAX_WORKER_NUMBERS?MAX_WORKER_NUMBERS:num;
        initializeWorkers(workerNum);
    }

    private void initializeWorkers(int num) {
        for (int i=0;i<num;i++){
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker,"ThreadPool-Worker-"+threadNum.incrementAndGet());
            thread.start();
        }
    }



    @Override
    public void excute(Runnable job) {
        if(job  != null){
            //添加一个工作，然后进行通知
            synchronized (jobs){
                jobs.addLast((Job) job);
                jobs.notify();
            }
        }

    }

    @Override
    public void shutdown() {
        for (Worker worker:workers){
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs){
            if(num + this.workerNum > MAX_WORKER_NUMBERS){
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initializeWorkers(num);
            this.workerNum += num;
        }

    }

    @Override
    public void removeWorker(int num) throws Exception {
        synchronized (jobs){
            if(num >= this.workerNum){
                throw new Exception("beyond workNum");
            }
            int count = 0;
            //按照给定的数量停止worker
            while (count < num){
                Worker worker = workers.get(count);
                if(workers.remove(worker)){
                    worker.shutdown();
                    count ++;
                }
            }
            this.workerNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }


    //工作者，负责消费任务
    class Worker implements Runnable{

        //是否工作
        private volatile  boolean running = true;

        @Override
        public void run() {
            while (running){
                Job job = null;
                synchronized (jobs){
                    //如果工作者列表是空的，那就wait
                    while (jobs.isEmpty()){
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            //感知到外部对WorkerThread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }
                if(job != null){
                    job.run();
                }
            }
        }

        public void shutdown(){
            running = false;
        }
    }
}
