package calculation.chapter.base.link;


class SingleLinkQuene<E> {
    protected Cell<E> head;
    protected Cell<E> tail;


    public void add(E item) throws Exception {
        Cell<E> cell = new Cell<E>(item);
        if (tail == null)
            head = tail = cell;
        else {
            tail.setNext(cell);
            tail = cell;
        }
    }

    public E remove() {
        if (head == null)
            return null;
        Cell<E> cell = head;
        head = head.getNext();
        if (head == null)
            tail = null;
        return cell.getElement();
    }
}

public class GenericityDemo {
    public static void main(String[] args) throws Exception {
        Cell<String> cell = new Cell<String>("Hellow");
        SingleLinkQuene<String> queue =
                new SingleLinkQuene<String>();
        queue.add("hellow");
        queue.add("Workd");
        String hello = queue.remove();
//		queue.add(133);
    }


}
