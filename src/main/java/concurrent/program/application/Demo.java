package concurrent.program.application;

/**
 * User: xuxb
 * Date: 2018-09-11
 * Time: 17:38
 * Version:V1.0
 */
public class Demo {
    PrintProcessor printProcessor;

    public Demo() {
        SaveProcessor saveProcessor =  new SaveProcessor();
        saveProcessor.start();
        printProcessor = new PrintProcessor(saveProcessor);
        printProcessor.start();
    }

    public static void main(String[] args) {
        Request request = new Request();
        request.setName("xxb");
        new Demo().doTest(request);
    }

    public void doTest(Request request) {
        printProcessor.processorRequest(request);
    }
}
