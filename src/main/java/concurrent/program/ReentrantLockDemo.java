package concurrent.program;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: xuxb
 * Date: 2018-09-11
 * Time: 12:59
 * Version:V1.0
 */
class PrintNumber implements Runnable {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final Condition[] PARRAY = new Condition[3];

    private final Integer printNumber;

    private volatile static Integer incNumber = 1;

    static {
        Integer.valueOf(59);
        //打印1的条件
        PARRAY[0] = LOCK.newCondition();
        //打印2的条件
        PARRAY[1] = LOCK.newCondition();
        //打印3的条件
        PARRAY[2] = LOCK.newCondition();
    }

    PrintNumber(Integer pn) {
        this.printNumber = pn;
    }

    void print() {
        LOCK.lock();
        try {
            //否决条件
            while (!incNumber.equals(printNumber)) {
                //挂起不符合条件的线程
                Integer temp = printNumber - 1;
                PARRAY[temp].await();
            }
            //action
            System.out.println(incNumber);
            incNumber++;
            if (incNumber > 3) {
                incNumber = 1;
            }
            //通知下个打印的线程 激活
            Integer temp = incNumber - 1;
            System.out.println("temp" + temp);
            PARRAY[temp].signalAll();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public void run() {

        print();

    }
}


public class ReentrantLockDemo {
    public static void main(String[] args) {
        new Thread(new PrintNumber(1)).start();
        new Thread(new PrintNumber(2)).start();
        new Thread(new PrintNumber(3)).start();
    }
}



