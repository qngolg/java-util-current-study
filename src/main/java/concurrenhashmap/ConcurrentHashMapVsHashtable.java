package concurrenhashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author qiangl
 * Created by qgl on 2018/8/22.
 */
public class ConcurrentHashMapVsHashtable {

    private static int input_number = 100000;

    public static void main(String[] args) throws InterruptedException {
//        TestCurrentHashMapVSHashTable();
        testCurrentHashMapThreadSafe();
    }

    /**
     * 测试concurrentHashMap的线程安全性
     * @throws InterruptedException
     */
    public static void testCurrentHashMapThreadSafe() throws InterruptedException {
        final HashMap<String,String> map = new HashMap<>();
        final ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<>();
        Thread t = new Thread(()->{
            for(int i=0;i<1000;i++){
                new Thread(()->{
                    map.put(UUID.randomUUID().toString(),"a");
                }).start();
            }
        });
        t.start();
        t.join();
        Thread.sleep(10000);
        System.out.println(map.size());
    }


    /**
     * 测试currentHashMap vs HashTable
     * test  currentHashMap vs  HashTable
     */
    public static void TestCurrentHashMapVSHashTable() throws InterruptedException {
        //        Map<Integer, String> map = new Hashtable<>(12 * input_number);

        Map<Integer,String> map  = new ConcurrentHashMap<Integer, String>(12 * input_number);
        long begin = System.currentTimeMillis();
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i=0;i<10;i++){
            service.execute(new InputWorker(map,i));
        }
        service.shutdown();
        service.awaitTermination(1,TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        System.out.println("span time = " + ( end - begin) + ",map size = " + map.size());
    }


    private static class InputWorker implements Runnable{

        private static Random random = new Random(System.currentTimeMillis());

        private final Map<Integer,String> map ;
        private final int flag;

        private InputWorker(Map<Integer, String> map, int flag) {
            this.map = map;
            this.flag = flag;
        }

        @Override
        public void run() {

            int input = 0;
            while (input < input_number){
                int x = random.nextInt();
                if(!map.containsKey(x)){
                    map.put(x,"Alex wang" + x);
                    input++;
                }
            }
            System.out.println("InputWorker " + flag + " is over");
        }
    }
}
