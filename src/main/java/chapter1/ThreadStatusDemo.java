package chapter1;

/**
 * @Author qiangl
 * Created by qgl on 2018/8/10.
 */
public class ThreadStatusDemo {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"time-wait").start();
    }
}
