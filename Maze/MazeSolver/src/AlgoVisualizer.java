import java.awt.*;

public class AlgoVisualizer {
    private static int DELAY = 3;
    private static int blockSize = 8;
    // 数组可以走的方向，这里只有四个方向：上下左右
    private static final int d[][] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    private MazeData data;
    private AlgoFrame frame;

    public AlgoVisualizer(String mazeFile) {
        // 初始化数据
        data = new MazeData(mazeFile);
        int sceneWidth = data.M() * blockSize;
        int sceneHeight = data.N() * blockSize;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Maze Solver Visualization", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run() {
        setData(-1, -1, false);

        // 开始走迷宫
        if (!go(data.getEntranceX(), data.getEntranceY()))
            System.out.println("THis maze has NO solution!");

        setData(-1, -1, false);
    }

    // 从 （x,y） 点开始找迷宫出口
    private boolean go(int x, int y) {
        if (!data.inArea(x, y))
            throw new IllegalArgumentException("x,y are out of index in maze");

        data.visited[x][y] = true;
        setData(x, y, true);

        // 递归结束条件
        if (x == data.getExitX() && y == data.getExitY())
            return true;

        for (int i = 0; i < 4; i++) {
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            // 确保下个点没越界并且是一条路而且之前没走过
            if (data.inArea(newX, newY) &&
                    data.getMaze(newX, newY) == MazeData.ROAD &&
                    !data.visited[newX][newY]) {
                // 如果找到了出口就可以终止了，不需要继续遍历下去
                if (go(newX, newY))
                    return true;
            }
        }
        // 对（x, y）这个点四个方向都尝试一遍，然而没有路时就要取消掉这个点是出口路径
        setData(x, y, false);

        return false;
    }

    private void setData(int x, int y, boolean isPath) {
        if (data.inArea(x, y))
            data.path[x][y] = isPath;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {
        String mazeFile = "maze_101_101.txt";

        AlgoVisualizer vis = new AlgoVisualizer(mazeFile);
    }
}
