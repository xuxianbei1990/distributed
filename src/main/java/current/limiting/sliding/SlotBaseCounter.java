package current.limiting.sliding;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Name
 *
 * @author xuxb
 * Date 2018-12-05
 * VersionV1.0
 * @description
 */
public class SlotBaseCounter {
    private int slotSize;
    private AtomicInteger[] slotCounter;

    public SlotBaseCounter(int slotSize) {
        slotSize = slotSize < 1 ? 1 : slotSize;
        this.slotSize = slotSize;
        this.slotCounter = new AtomicInteger[slotSize];
        for (int i = 0; i < this.slotSize; i++) {
            slotCounter[i] = new AtomicInteger(0);
        }
    }

    public void increaseSlot(int slotSize) {
        slotCounter[slotSize].incrementAndGet();
    }

    public void wipeSlot(int slotSize) {
        slotCounter[slotSize].set(0);
    }

    public int totalCount() {
        return Arrays.stream(slotCounter).mapToInt(slotCounter -> slotCounter.get()).sum();
    }

    @Override
    public String toString() {
        return Arrays.toString(slotCounter);
    }
}

