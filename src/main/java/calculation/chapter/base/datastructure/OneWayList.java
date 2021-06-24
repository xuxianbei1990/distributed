package calculation.chapter.base.datastructure;

import lombok.Data;

import java.util.Objects;

/**
 * 单向链表
 * @author: xuxianbei
 * Date: 2021/6/23
 * Time: 16:50
 * Version:V1.0
 */
public class OneWayList<T> {

    private Node<T> head = new Node<>(null);

    private int size = 0;

    public int add(T value) {
        if (Objects.isNull(head.getValue())) {
            head.setValue(value);
            size++;
            return size;
        }
        Node temp = head;
        while (temp != null) {
            if (temp.getNext() == null) {
                temp.setNext(new Node(value));
                size++;
                return size;
            } else {
                temp = temp.getNext();
            }
        }
        return size;
    }

    public Node<T> get(int index) {
        Node temp = head;

        if (index == 0) {
            return temp;
        }
        if (index >= size) {
            return null;
        }

        for (int i = 1; i <= index; i++) {
            temp = temp.getNext();
        }
        return temp;
    }

    public void delete(int index) {
        Node temp = head.getNext();
        if (index == 0) {
            head.setNext(null);
            head = temp;
            size--;
        } else {
            Node pred = temp;
            for (int i = 1; i < index; i++) {
                pred = temp;
                temp = temp.getNext();
                if (temp == null) {
                    throw new IndexOutOfBoundsException();
                }
            }
            pred.setNext(temp.getNext());
            temp.setNext(null);
            size--;
        }
    }

    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        return "OneWayList{" +
                "head=" + head +
                ", size=" + size +
                '}';
    }

    @Data
    class Node<T> {
        private T value;
        private Node next;

        public Node(T value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        OneWayList<Integer> oneWayList = new OneWayList();
        oneWayList.add(1);
        oneWayList.add(0);
        oneWayList.add(4);
        oneWayList.add(2);
        oneWayList.add(3);
        System.out.println(oneWayList.toString());
        oneWayList.delete(3);
        System.out.println(oneWayList.toString());
        oneWayList.delete(2);
        System.out.println(oneWayList.toString());
        oneWayList.delete(0);
        System.out.println(oneWayList.toString());
        oneWayList.add(4);
        oneWayList.add(2);
        oneWayList.add(3);
        System.out.println(oneWayList.toString());
        System.out.println(oneWayList.get(4));
    }

}
