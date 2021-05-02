import java.util.Arrays;

public class InsertionSortData {
    public enum Type {
        Default,
        NearOrdered
    }

    private int[] numbers;
    public int orderedIndex = -1;
    public int currentIndex = -1;

    public InsertionSortData(int N, int randomBound, Type dataType) {
        numbers = new int[N];

        for (int i = 0; i < numbers.length; i++)
            numbers[i] = (int) (Math.random() * randomBound) + 1;

        // 生成近乎有序的数组
        if (dataType == Type.NearOrdered) {
            Arrays.sort(numbers);
            int swapTime = (int) (0.02 * N);
            for (int i = 0; i < swapTime; i++) {
                int a = (int) (Math.random() * N);
                int b = (int) (Math.random() * N);
                swap(a, b);
            }
        }
    }

    public InsertionSortData(int N, int randomBound) {
        this(N, randomBound, Type.Default);
    }

    public int N() {
        return numbers.length;
    }

    public int get(int i) {
        if (i < 0 || i >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort data");

        return numbers[i];
    }

    public void swap(int i, int j) {
        if (i < 0 || i >= numbers.length || j < 0 || j >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort data");

        int t = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = t;
    }
}
