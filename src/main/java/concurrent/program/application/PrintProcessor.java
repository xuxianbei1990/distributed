package concurrent.program.application;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * User: xuxb
 * Date: 2018-09-11
 * Time: 17:18
 * Version:V1.0
 */
public class PrintProcessor extends Thread implements RequestProcessor {

    LinkedBlockingQueue<Request> linkedBlockingQueue = new LinkedBlockingQueue<>();

    private final RequestProcessor nextProcessor;

    public PrintProcessor(RequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //没有会阻塞，poll 直接返回，remove 没有会异常
                Request request = linkedBlockingQueue.take();
                System.out.println("print dat:" + request.getName());
                nextProcessor.processorRequest(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void processorRequest(Request request) {
        linkedBlockingQueue.add(request);
    }
}
