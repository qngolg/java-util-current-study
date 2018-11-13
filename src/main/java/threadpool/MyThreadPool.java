package threadpool;

/**
 * 线程池的实现
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public interface MyThreadPool<Job extends Runnable>{

    //执行一个job,这个job要实现Runnable
    void excute(Job job);
    //关闭线程池
    void shutdown();
    //增加工作者线程
    void addWorkers(int num);
    //减少工作者线程
    void removeWorker(int num) throws Exception;
    //得到正在等待执行的任务数量
    int getJobSize();

}
