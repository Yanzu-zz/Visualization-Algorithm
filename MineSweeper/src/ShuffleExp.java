public class ShuffleExp {
    private int N;
    private int n, m;

    // 在 n x n 的扫雷盘上，有 m 个雷，并进行 N 次洗牌模拟算法
    public ShuffleExp(int N, int n, int m) {
        if (N <= 0)
            throw new IllegalArgumentException("N must be larger than 0!");
        if (n < m)
            throw new IllegalArgumentException("n must be greater than or equal to m!");

        this.N = N;
        this.n = n;
        this.m = m;
    }

    public void run() {
        // 在第 i 个位置出现过雷的次数（java 初始化为 0）
        int[] freq = new int[n];

        int arr[] = new int[n];
        for (int i = 0; i < N; i++) {
            reset(arr); // 先重置牌
            shuffle(arr);// 接着洗牌
            // 然后统计
            // 记录每个位置上的雷的次数，以方便后序统计
            for (int j = 0; j < n; j++)
                freq[j] += arr[j];
        }

        for (int i = 0; i < n; i++)
            System.out.println(i + ": " + (double) freq[i] / N); // 将出现的次数变为概率
    }

    // 重新将 牌数组 变为有序
    private void reset(int[] arr) {
        for (int i = 0; i < m; i++)
            arr[i] = 1;

        for (int i = m; i < n; i++)
            arr[i] = 0;
    }

    private void shuffle(int[] arr) {
        // Fisher-Yates-Knuth 洗牌算法
        for (int i = 0; i < n; i++) {
            // 从 [i, n) 区间中随机选择元素
            int x = (int) (Math.random() * (n - i)) + i;
            swap(arr, i, x);
        }
    }

    private void swap(int[] arr, int x, int y) {
        int t = arr[x];
        arr[x] = arr[y];
        arr[y] = t;
    }

    public static void main(String[] args) {
        int N = 10000000;
        int n = 10;
        int m = 5;

        ShuffleExp exp = new ShuffleExp(N, n, m);
        exp.run();
    }
}
