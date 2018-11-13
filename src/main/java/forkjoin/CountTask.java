package forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public class CountTask extends RecursiveTask<Integer> {

    private static final int THERSHOLD = 2;
    private int start;
    private int end;

    public CountTask(int start,int end){
        this.start = start;
        this.end = end;
    }


    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THERSHOLD;
        if(canCompute){
            for(int i = start;i<= end;i++){
                sum += i;
            }
        }else {
            //如果任务大于阈值，就分裂成两个子任务计算
            int middle = (start + end) /2;
            CountTask leftTask = new CountTask(start,middle);
            CountTask rightTask = new CountTask(middle+1,end);
            //执行子任务 每个子任务在调用fork方法时，又会进入compute方法
            leftTask.compute();
            rightTask.compute();
            //等待子任务执行完，并得到其结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static  void testForkJoin() throws ExecutionException, InterruptedException {
        //建立forkJoinPool线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //创建线程计算任务
        CountTask task = new CountTask(1,6);
        //Future获取线程结果
        Future<Integer> result = forkJoinPool.submit(task);
        //通过get()方法获取总的结果
        System.out.println(result.get());
    }

    public static void main(String[] args) throws Exception {
        testForkJoin();


    }
}
