public class MergeSortData {
    public int[] numbers;
    public int l, r; // 每次递归处理的区间
    public int mergeIndex;

    public MergeSortData(int N, int randomBound) {
        numbers = new int[N];

        for (int i = 0; i < numbers.length; i++)
            numbers[i] = (int) (Math.random() * randomBound) + 1;

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
