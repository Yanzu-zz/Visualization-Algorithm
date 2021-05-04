import java.util.ArrayList;

/**
 * 随机队列
 * 底层数据结构：数组
 * 入队：把新元素放入数组
 * 出队：从数组中随机选择一个元素
 */
public class RandomQueue<E> {
    private ArrayList<E> queue;

    public RandomQueue() {
        queue = new ArrayList<E>();
    }

    public void add(E e) {
        queue.add(e);
    }

    public E remove() {
        if (size() == 0)
            throw new IllegalArgumentException("There's no element in random queue that can be removed");

        int randomIndex = (int) (Math.random() * queue.size());
        E randomElement = queue.get(randomIndex);

        // 将随机索引位置的元素与队尾元素交换，然后删除
        // 这样就达成了随机出队，又保持了数组元素位置的连续性
        int lastIndex = queue.size() - 1;
        queue.set(randomIndex, queue.get(lastIndex));
        queue.remove(lastIndex);

        return randomElement;
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
