package calculation.chapter.ArrayCode;

import java.util.*;

public class NestedIterator implements Iterator<Integer> {

    LinkedList<NestedInteger> list = new LinkedList();

    public NestedIterator(List<NestedInteger> nestedList) {
        for (NestedInteger value : nestedList) {
            if (value.isInteger()) {
                list.add(value);
            } else {
                NestedInteger loop = value;
                process(loop, list);
            }
        }
    }

    private void process(NestedInteger loop, LinkedList<NestedInteger> list) {
        if (loop.isInteger()) {
            list.add(loop);
            return;
        }
        List<NestedInteger> next = loop.getList();
        for (NestedInteger nested : next) {
            process(nested, list);
        }
    }

    @Override
    public Integer next() {
        NestedInteger nested = list.pop();
        return nested.getInteger();
    }

    @Override
    public boolean hasNext() {
        return list.isEmpty();
    }

    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> mapKey = new HashMap();
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>((a, b)-> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1] );
        for (int i: nums){
            if (!mapKey.containsKey(i)){
                mapKey.put(i, 1);
            } else {
                mapKey.put(i, mapKey.get(i) + 1);
            }
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = mapKey.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, Integer> entryset= iterator.next();
            if (queue.size() < k){
                queue.add(new int[]{entryset.getValue(), entryset.getKey()}) ;
            } else {
                int[] value1 = queue.peek();
                if (value1[0] < entryset.getValue()) {
                    queue.poll();
                    queue.add(new int[]{entryset.getValue(), entryset.getKey()}) ;
                }
            }
        }

        int[] ans = new int[k];
        for (int i = 0; i < k; i++){
            ans[i] = queue.poll()[1];
        }
        return ans;
    }

    public static void main(String[] args) {
        int t = (int)Math.random() * 3;
        List<NestedInteger> list = new ArrayList<>();
        list.add(new NestedIntegerImpl());
        NestedIterator nestedIterator  = new NestedIterator(list);
        nestedIterator.topKFrequent(new int[]{4,1,-1,2,-1,2,3}, 2);


    }
}
