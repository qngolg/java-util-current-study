package countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 发令枪 测试并发线程
 *
 * @Author qiangl
 * Created by qgl on 2018/10/25.
 */
public class FaLingQiang {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch cd1 = new CountDownLatch(1);
        final CountDownLatch cd2 = new CountDownLatch(3);
        for(int i = 0;i<3;i++){

            Thread thread = new Thread(()-> {
                System.out.println("线程" + Thread.currentThread().getName()+"正在准备命令");
                try {
                    cd1.await();
                    System.out.println("线程"+Thread.currentThread().getName()+"已接收命令");
                    cd2.countDown();
                    Thread.sleep((long) (Math.random()*10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }


}
