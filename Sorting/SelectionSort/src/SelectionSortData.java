public class SelectionSortData {
    private int[] numbers;
    public int orderdIndex = -1; // 从 [0...orderdIndex] 表示已经拍好序的数组元素
    public int currentMinIndex = -1; // 当前轮中找到的最小元素的索引
    public int currentCompareIndex = -1; // 当前正在遍历比较的元素索引

    public SelectionSortData(int N, int randomBound) {
        numbers = new int[N];

        for (int i = 0; i < N; i++)
            numbers[i] = (int) (Math.random() * randomBound) + 1;
    }

    public int N() {
        return numbers.length;
    }

    public int get(int index) {
        if (index < 0 || index >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort Data.");

        return numbers[index];
    }

    // 交换
    public void swap(int i, int j) {
        if (i < 0 || i >= numbers.length || j < 0 || j >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort Data.");

        int t = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = t;
    }
}
