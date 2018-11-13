package queue.blockQueue;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public class SaveProcessor extends Thread implements RequestProcessor {


    LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<>();

    @Override
    public void run() {
        while (true){
            try {
                Request request = requests.take();
                System.out.println("begin save request info:" + request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void processRequest(Request request) {
        requests.add(request);
    }
}
