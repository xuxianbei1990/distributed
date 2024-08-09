package calculation.chapter.ArrayCode;

import java.util.*;

public class FreqStack {


    Map<Integer, Integer> bp;
    Map<Integer, List<Integer>> storeys;
    int maxLevel;

    public FreqStack() {
        bp = new HashMap<>();
        storeys = new HashMap<>();
        maxLevel = 0;
    }

    public void push(int val) {
        int level = 0;
        if (!bp.containsKey(val)) {
            bp.put(val, 1);
            level = 1;
        } else {
            level = bp.get(val);
            level++;
            bp.put(val, level);
        }
        List<Integer> list = storeys.get(level);
        if (list == null) {
            storeys.put(level, new ArrayList());
            storeys.get(level).add(val);
        } else {
            list.add(val);
        }
        maxLevel = storeys.size();
    }

    public int pop() {

        if (maxLevel < 1) {
            return -1;
        }
        List<Integer> header = storeys.get(maxLevel);
        int result = header.get(header.size() - 1);
        header.remove(header.size() - 1);
        bp.put(result, bp.get(result) - 1);
        if (header.size() == 0) {
            storeys.remove(maxLevel);
        }
        maxLevel = storeys.size();
        return result;
    }

    public static void main(String[] args) {
        FreqStack freqStack = new FreqStack();
        freqStack.push(1);
        freqStack.push(1);
        freqStack.push(1);
        freqStack.push(2);
        freqStack.pop();
        freqStack.pop();
        freqStack.push(2);
        freqStack.push(2);
        freqStack.push(1);
        System.out.println(freqStack.pop());
        freqStack.pop();
        freqStack.pop();

    }
}
