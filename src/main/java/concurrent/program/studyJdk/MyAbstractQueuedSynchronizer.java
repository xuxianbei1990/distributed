package concurrent.program.studyJdk;

import sun.misc.Unsafe;

/**
 * @author: xuxianbei
 * Date: 2020/4/30
 * Time: 14:18
 * Version:V1.0
 */
public abstract class MyAbstractQueuedSynchronizer {

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

        }
    }

    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (; ; ) {
                final Node p = node
            }
        }
    }

    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        Node pred = tail;
        if (pred != null) {

        }
        enq(node);
        return node;
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

    static final class Node {
        static final Node STARED = new Node();
        //专用
        static final Node EXCLUSIVE = null;
        static final int CANCELLED = 1;
        static final int SIGNAL = -1;
        static final int CONDIDION = -2;
        volatile Node prev;
        Node nextWaiter;
        volatile Thread thread;

        Node() {    // Used to establish initial head or SHARED marker
        }

        Node(Thread thread, Node mode) {     // Used by addWaiter
            this.nextWaiter = mode;
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

}
