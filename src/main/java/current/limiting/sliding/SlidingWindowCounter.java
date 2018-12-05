package current.limiting.sliding;

/**
 * Name
 *
 * @author xuxb
 * Date 2018-12-05
 * VersionV1.0
 * @description
 */
public class SlidingWindowCounter {

    private volatile SlotBaseCounter slotBaseCounter;
    private volatile int windowSize;
    private volatile int head;

    public SlidingWindowCounter(int windowSize) {
        resizeWindow(windowSize);
    }

    public synchronized void resizeWindow(int windowSize) {
        this.windowSize = windowSize;
        this.slotBaseCounter = new SlotBaseCounter(windowSize);
        this.head = 0;
    }

    public void increase() {
        slotBaseCounter.increaseSlot(head);
    }

    public int totalAndAdvance() {
        int total = totalCount();
        advance();
        return total;
    }

    public void advance() {
        int tail = (head + 1) % windowSize;
        slotBaseCounter.wipeSlot(tail);
        head = tail;
    }

    public int totalCount() {
        return slotBaseCounter.totalCount();
    }

    @Override
    public String toString() {
        return "total = " + totalCount() + " head = " + head + " >> " + slotBaseCounter;
    }
}

