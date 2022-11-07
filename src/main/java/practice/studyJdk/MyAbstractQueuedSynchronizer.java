package practice.studyJdk;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;

/**
 * @author: xuxianbei
 * Date: 2020/4/30
 * Time: 14:18
 * Version:V1.0
 */
public abstract class MyAbstractQueuedSynchronizer {

    private volatile int state;

    private transient volatile Node tail;

    private transient volatile Node head;

    protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * 得到
     * 为什么这里是final 因为这个流程是固定且不可改变的
     *
     * @param arg
     */
    public final void acquire(int arg) {
        if (!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) {
            selfInterrupt();
        }
    }

    private void selfInterrupt() {
        Thread.currentThread().interrupt();
    }

    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (; ; ) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null;
                    failed = false;
                    return interrupted;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                        parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    private void cancelAcquire(Node node) {
        if (node == null)
            return;

        node.thread = null;

        Node pred = node.prev;
        while (pred.waitStatus > 0)
            node.prev = pred = pred.prev;

        Node predNext = pred.next;
        node.waitStatus = Node.CANCELLED;

        //如果是最后一个节点， 把之前的节点设置为末尾
        if (node == tail && compareAndSetTail(node, pred)) {
            compareAndSetNext(pred, predNext, null);
        } else {
            int ws;
            if (pred != head && ((ws = pred.waitStatus) == Node.SIGNAL ||
                    (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) &&
                    pred.thread != null) {
                Node next = node.next;
                if (next != null && next.waitStatus <= 0)
                    compareAndSetNext(pred, predNext, next);
            } else {
                unparkSuccessor(node);
            }
            node.next = node;
        }
    }

    public final boolean release(int arg) {
        if (tryAcquire(arg)) {
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);
            return true;
        }
        return false;
    }

    private void unparkSuccessor(Node node) {
        int ws = node.waitStatus;
        if (ws < 0)
            compareAndSetWaitStatus(node, ws, 0);
        Node s = node.next;
        if (s == null || s.waitStatus > 0) {
            s = null;
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }
        if (s != null)
            LockSupport.unpark(s.thread);
    }

    private void compareAndSetNext(Node pred, Node predNext, Object o) {

    }

    private boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }

    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL)
            return true;
        if (ws > 0) {
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }

    private static boolean compareAndSetWaitStatus(Node pred, int ws, int signal) {
        return false;
    }


    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }

    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        Node pred = tail;
        if (pred != null) {
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node;
    }

    private boolean compareAndSetTail(Node pred, Node node) {
        return false;
    }

    private Node enq(Node node) {
        for (; ; ) {
            Node t = tail;
            if (t == null) {
                if (compareAndSetHead(new Node())) {
                    tail = head;
                }
            }
        }
    }

    private final boolean compareAndSetHead(Node update) {
        return true;
    }

    protected final int getState() {
        return state;
    }

    static final class Node {
        static final Node STARED = new Node();
        //专用
        static final Node EXCLUSIVE = null;
        static final int CANCELLED = 1;
        static final int SIGNAL = -1;
        static final int CONDITION = -2;
        volatile Node prev;
        volatile Node next;
        Node nextWaiter;
        volatile int waitStatus;
        volatile Thread thread;

        Node() {    // Used to establish initial head or SHARED marker
        }

        Node(Thread thread, Node mode) {     // Used by addWaiter
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread, int waitStatus) { // Used by Condition
            this.waitStatus = waitStatus;
            this.thread = thread;
        }

        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }
    }
    public class ConditionObject implements Condition, java.io.Serializable {

        private transient Node firstWaiter;
        private transient Node lastWaiter;
        private static final int THROW_IE    = -1;
        private static final int REINTERRUPT =  1;

        @Override
        public void await() throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            //释放锁
            int savedState = fullyRelease(node);
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                //线程状态是waiting
                LockSupport.park(this);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }

            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
        }
        private int checkInterruptWhileWaiting(Node node) {
            return 0;
        }

        private boolean isOnSyncQueue(Node node) {
            if (node.waitStatus == Node.CONDITION || node.prev == null)
                return false;
            return false;
        }

        private int fullyRelease(Node node) {
            boolean failed = true;
            try {
                int savedState = getState();
                if (release(savedState)) {
                    failed = false;
                    return savedState;
                } else {
                    throw new IllegalMonitorStateException();
                }
            } finally {
                if (failed)
                    node.waitStatus = Node.CANCELLED;
            }
        }

        private Node addConditionWaiter() {
            Node t = lastWaiter;
            // If lastWaiter is cancelled, clean out.
            if (t != null && t.waitStatus != Node.CONDITION) {
                unlinkCancelledWaiters();
                t = lastWaiter;
            }
            Node node = new Node(Thread.currentThread(), Node.CONDITION);
            if (t == null)
                firstWaiter = node;
            else
                t.nextWaiter = node;
            lastWaiter = node;
            return node;
        }

        private void unlinkCancelledWaiters() {

        }

        @Override
        public void awaitUninterruptibly() {

        }

        @Override
        public long awaitNanos(long nanosTimeout) throws InterruptedException {
            return 0;
        }

        @Override
        public boolean await(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public boolean awaitUntil(Date deadline) throws InterruptedException {
            return false;
        }

        @Override
        public void signal() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignal(first);
        }

        private void doSignal(Node first) {
            do {
                if ( (firstWaiter = first.nextWaiter) == null)
                    lastWaiter = null;
                first.nextWaiter = null;
            } while (!transferForSignal(first) &&
                    (first = firstWaiter) != null);
        }

        final boolean transferForSignal(Node node) {
            /*
             * If cannot change waitStatus, the node has been cancelled.
             */
            if (!compareAndSetWaitStatus(node, Node.CONDITION, 0))
                return false;

            /*
             * Splice onto queue and try to set waitStatus of predecessor to
             * indicate that thread is (probably) waiting. If cancelled or
             * attempt to set waitStatus fails, wake up to resync (in which
             * case the waitStatus can be transiently and harmlessly wrong).
             */
            Node p = enq(node);
            int ws = p.waitStatus;
            if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL))
                LockSupport.unpark(node.thread);
            return true;
        }

        private boolean isHeldExclusively() {
            return false;
        }

        @Override
        public void signalAll() {

        }
    }
}
