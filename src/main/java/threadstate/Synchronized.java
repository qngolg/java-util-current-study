package threadstate;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public class Synchronized {


    public static void main(String[] args) {

        //同步代码块，对Synchronized.class对象进行加锁
        synchronized (Synchronized.class){

        }
    }

    //同步方法
    public static synchronized void m(){

    }
}
