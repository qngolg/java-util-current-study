package queue.blockQueue;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public class PrintProcessor extends Thread implements RequestProcessor {


    LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<>();
    private final RequestProcessor nextProcessor;

    public PrintProcessor(RequestProcessor requestProcessor){
        this.nextProcessor = requestProcessor;
    }

    public void run(){
        while (true){
            try {
                Request request = requests.take();
                System.out.println("print data:" + request.getName());
                nextProcessor.processRequest(request);
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
