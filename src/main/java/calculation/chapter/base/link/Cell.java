package calculation.chapter.base.link;

/**
 * @author: xuxianbei
 * Date: 2021/6/24
 * Time: 11:53
 * Version:V1.0
 */
public class Cell<E> {
    private Cell<E> next;
    private E element;

    public Cell(E element) throws Exception {
        this.element = element;
        int x = 0;
        System.out.print('1');
        assert false : "sky";

    }

    public Cell(E element, Cell<E> next) {
        this.element = element;
        this.next = next;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public void setNext(Cell<E> next) {
        this.next = next;
    }

    public Cell<E> getNext() {
        return next;
    }
}
