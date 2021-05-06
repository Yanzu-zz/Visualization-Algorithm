public class Board {
    public static char EMPTY = '.';

    private int N, M; // 行，列
    private char[][] data; // 初始数据

    // 当解决问题后，通过这两个记录变量来往前推得到解
    private Board preBoard = null; // 记录当前 board 是从哪个 board 中来的
    private String swapString = ""; // 当前的 board 是通过上一个 Board 怎样的操作得到的

    public Board(String[] lines) {
        if (lines == null)
            throw new IllegalArgumentException("lines cannot be null in Board");

        N = lines.length;
        if (N == 0)
            throw new IllegalArgumentException("lines cannot be empty in Board");
        M = lines[0].length();

        data = new char[N][M];
        for (int i = 0; i < N; i++) {
            if (lines[i].length() != M)
                throw new IllegalArgumentException("All line's length must be identical!");

            for (int j = 0; j < M; j++)
                data[i][j] = lines[i].charAt(j);
        }
    }

    public Board(Board board, Board preBoard, String swapString) {
        if (board == null)
            throw new IllegalArgumentException("Board cannot be null!");
        this.N = board.N;
        this.M = board.M;
        this.data = new char[N][M];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                this.data[i][j] = board.data[i][j];

        this.preBoard = preBoard;
        this.swapString = swapString;
    }

    public Board(Board board) {
        this(board, null, "");
    }

    public boolean isWin() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                if (data[i][j] != EMPTY)
                    return false;

        // 当确定解决问题后，我们就能拿到解决问题的步骤路径了
        printSwapInfo();
        return true;
    }

    public void swap(int x1, int y1, int x2, int y2) {
        if (!inArea(x1, y1) || !inArea(x2, y2))
            throw new IllegalArgumentException("x,y are out of index in swap!");

        char t = data[x1][y1];
        data[x1][y1] = data[x2][y2];
        data[x2][y2] = t;
    }

    // 交换箱子位置后会发生的一系列后果
    public void run() {
        // 要先处理掉落，才到消除
        // 但是注意，消除过程中，也会产生掉落，故这是个循环的过程
        // 这种逻辑和 do...while 完全吻合
        do {
            drop();
        } while (match());
    }

    // 处理掉落的逻辑就是遍历每一列从底向上把箱子进行下落操作
    private void drop() {
        for (int j = 0; j < M; j++) {
            int cur = N - 1; // cur表示如果上面有箱子要进行下落操作，那个这个箱子该落在哪
            // 这个逻辑就算底下全是箱子也能跑通~
            for (int i = N - 1; i >= 0; i--)
                if (data[i][j] != EMPTY)
                    swap(i, j, cur--, j);
        }
    }

    private static final int[][] d = {{0, 1}, {1, 0}}; // 右侧和下侧方向数组

    // 只对一个点的右侧和下侧进行消除标记，因为左侧和上侧已经在对前面的点进行操作了（这也是一种剪枝，当然还可以进一步优化）
    // 另，每个点只看连续的 3 个点，因为即使有 100 个点是连续的，后序遍历到下方的点时会慢慢标记上
    // 所以此时不进行消除操作而是标记操作是对的，不然下面没法看了
    private boolean match() {
        boolean[][] tag = new boolean[N][M]; // java 默认初始全是 false
        boolean isMatch = false; // 判断是否有进行消除操作

        // 先标记
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                // 当遍历到的点不为空位时，进行标记操作
                if (data[x][y] != EMPTY) {
                    for (int i = 0; i < d.length; i++) {
                        int newX1 = x + d[i][0]; // 右边或下边的第一个格子的横坐标
                        int newY1 = y + d[i][1]; // 右边或下边的第一个格子的纵坐标
                        int newX2 = newX1 + d[i][0]; // 右边或下边的第二个格子的横坐标
                        int newY2 = newY1 + d[i][1]; // 右边或下边的第二个格子的纵坐标

                        // 当符合我们的标记逻辑时
                        if (inArea(newX1, newY1) && inArea(newX2, newY2)
                                && data[newX1][newY1] == data[x][y]
                                && data[newX2][newY2] == data[x][y]) {
                            tag[x][y] = tag[newX1][newY1] = tag[newX2][newY2] = true;
                            isMatch = true;
                        }
                    }
                }
            }
        }

        // 再删除
        if (isMatch) {
            for (int x = 0; x < N; x++)
                for (int y = 0; y < M; y++)
                    if (tag[x][y])
                        data[x][y] = EMPTY;
        }

        return isMatch;
    }

    public int N() {
        return N;
    }

    public int M() {
        return M;
    }

    public char getData(int x, int y) {
        if (!inArea(x, y))
            throw new IllegalArgumentException("x, y are out of index in getData");
        return data[x][y];
    }

    public boolean inArea(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public void print() {
        for (int i = 0; i < N; i++)
            System.out.println(String.valueOf(data[i]));
    }

    // 打印出解决问题的对应步骤
    public void printSwapInfo() {
        // 这里可以用栈来辅助打印，但为了加强递归的用法，这里用递归写法
        // 只需要三行，大家仔细体会下！
        if (preBoard != null)
            preBoard.printSwapInfo(); // 当有记录前一个盘面信息时，一直找到最初状态
        System.out.println(swapString); // 找到最初状态盘面后进行顺序打印
    }
}
