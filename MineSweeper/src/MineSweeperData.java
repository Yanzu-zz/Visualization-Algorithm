public class MineSweeperData {
    public static final String blockImageURL = "resources/block.png";
    public static final String flagImageURL = "resources/flag.png";
    public static final String mineImageURL = "resources/mine.png";

    public static String numberImageURL(int num) {
        if (num < 0 || num > 8)
            throw new IllegalArgumentException("The switched number must between 0 to 8");
        return "resources/" + num + ".png";
    }

    private int N, M;
    private boolean[][] mines;
    private int[][] numbers; // 存储某个格子周围 8 个棋盘格子有多少个雷
    public boolean[][] open; // 棋盘是否被点击
    public boolean[][] flags; // 标记玩家右键棋盘是小旗子的位置

    public MineSweeperData(int N, int M, int mineNumber) {
        if (N <= 0 || M <= 0)
            throw new IllegalArgumentException("Mine sweeper size is invalid!");

        if (mineNumber <= 0 || mineNumber > N * M)
            throw new IllegalArgumentException("Mine number is larger than the size which appointed");

        this.N = N;
        this.M = M;
        mines = new boolean[N][M]; // java 默认初始布尔数组是false
        numbers = new int[N][M];
        open = new boolean[N][M];
        flags = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                mines[i][j] = false;
                numbers[i][j] = 0;
                open[i][j] = false;
                flags[i][j] = false;
            }
        }

        generateMines(mineNumber);
        calculateNumbers();
    }

    private void generateMines(int mineNumber) {
        // 先顺序摆放
        for (int i = 0; i < mineNumber; i++) {
            int x = i / M;
            int y = i % M;
            mines[x][y] = true;
        }

        // 接着随机交换
        // 这里我们用 Fisher-Yates-Knuth 洗牌算法进行随机埋雷（这个算法公平）
        // 重点是把二维坐标转化为一维坐标
        for (int i = N * M - 1; i >= 0; i--) {
            int iX = i / M;
            int iY = i % M;

            int randNumber = (int) (Math.random() * (i + 1));
            int randX = randNumber / M;
            int randY = randNumber % M;

            swap(iX, iY, randX, randY);
        }
    }

    // 计算周围格子中有多少个雷的辅助函数
    private void calculateNumbers() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (mines[i][j]) {
                    numbers[i][j] = -1;
                    continue;
                }

                numbers[i][j] = 0; // 提前设置 0，让下方的判断条件不会在这里 + 1（因为明确这里不是雷了）
                // 遍历周围 8个格子
                for (int ii = i - 1; ii <= i + 1; ii++)
                    for (int jj = j - 1; jj <= j + 1; jj++)
                        if (inArea(ii, jj) && mines[ii][jj])
                            numbers[i][j]++;
            }
        }
    }

    private void swap(int x1, int y1, int x2, int y2) {
        boolean t = mines[x1][y1];
        mines[x1][y1] = mines[x2][y2];
        mines[x2][y2] = t;
    }

    public int N() {
        return N;
    }

    public int M() {
        return M;
    }

    public boolean inArea(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public boolean isMine(int x, int y) {
        if (!inArea(x, y))
            throw new IllegalArgumentException("x or y is out or border of the mines");

        return mines[x][y];
    }

    public int getNumber(int x, int y) {
        if (!inArea(x, y))
            throw new IllegalArgumentException("x or y is out or border of the mines");

        return numbers[x][y];
    }

    public void open(int x, int y) {
        if (!inArea(x, y))
            throw new IllegalArgumentException("x or y is out or border of the mines");
        if (isMine(x, y))
            throw new IllegalArgumentException("Cannot open an mine block!");

        // floodfill 算法，这里的边界就是数字
        open[x][y] = true;
        if (numbers[x][y] > 0)
            return;
        // 接着是扩散操作，即向 (x, y) 点的八个方向深搜
        for (int i = x - 1; i <= x + 1; i++)
            for (int j = y - 1; j <= y + 1; j++)
                if (inArea(i, j) && !open[i][j] && !mines[i][j])
                    open(i, j);
    }
}
