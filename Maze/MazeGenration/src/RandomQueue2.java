import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 随机队列，随机性更强
 * 底层数据结构：链表
 * 入队：随机入队首或者队尾
 * 出队：随机从队首或者队尾挑选元素
 */
public class RandomQueue2<E> {
    private LinkedList<E> queue;

    public RandomQueue2() {
        queue = new LinkedList<>();
    }

    public void add(E e) {
        if (Math.random() < 0.5)
            queue.addFirst(e);
        else
            queue.addLast(e);
    }

    public E remove() {
        if (size() == 0)
            throw new IllegalArgumentException("There's no element in random queue that can be removed");

        if (Math.random() < 0.5)
            return queue.removeFirst();
        else
            return queue.removeLast();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
