package queue.blockQueue;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * 接口：queue 子接口：BlockingQueue
 * 实现类：ConcurrentLinkedQueue LinkedBlockingQueue ArrayBlockingQueue
 *
 * 通过队列BlockQueue实现简单的生产者和消费者
 * @Author qiangl
 * Created by qgl on 2018/10/25.
 */
public class ProducerAndConsumer {


    //生产者
    static class Producer implements Runnable{

        private final BlockingQueue queue;

        Producer(BlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {

            for (int i = 1;i<10;i++){
                queue.offer(String.valueOf("生产消息："+i));
                Iterator a = queue.iterator();
                while (a.hasNext()){
                    System.out.print(a.next() + " ");
                }
                System.out.println("生产消息："+i + " 此时queue的大小为："+queue.size());
            }
        }

        String produce(int i){
            for (i=1;i<10;i++){
                return String.valueOf("生产消息---->"+i);
            }
            return null;
        }
    }

    static class Consumer implements Runnable{

        private final BlockingQueue queue;

        Consumer(BlockingQueue queue) {
            this.queue = queue;
        }


        @Override
        public void run() {

            while (true){
                try {
                    Thread.sleep(2000);
                    Iterator iterator = queue.iterator();
                    while (iterator.hasNext()){
                        System.out.println(iterator.next() +"  ");
                    }
                    consume((String) queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        void consume(String msg){
            System.out.println(Thread.currentThread().getName()+":消费了消息："+msg);
        }
    }

    public static void main(String[] args) {
        BlockingQueue q = new LinkedBlockingQueue();
        Producer p = new Producer(q);
        Consumer c1 = new Consumer(q);
//        Consumer c2 = new Consumer(q);
        new Thread(p).start();
        new Thread(c1).start();
    }

}
