import java.awt.*;
import java.util.Stack;

// 控制器
public class AlgoVisualizer {
    private static final int DELAY = 2;
    private static int blockSize = 8;
    private static final int d[][] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    private MazeData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int N, int M) {
        data = new MazeData(N, M);
        int sceneHeight = data.N() * blockSize;
        int sceneWidth = data.M() * blockSize;

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Random Maze Generation  Visualization", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run() {
        setData(-1, -1);

        //dfs(data.getEntranceX(), data.getEntranceY() + 1);
        dfs2();

        setData(-1, -1);
    }

    private void dfs(int x, int y) {
        if (!data.inArea(x, y))
            throw new IllegalArgumentException("x,y are out of border of the maze!");
        data.visited[x][y] = true;

        // 注意，本个深搜的递归终止条件隐含在了下面的循环里
        for (int i = 0; i < 4; i++) {
            int newX = x + d[i][0] * 2;
            int newY = y + d[i][1] * 2;

            // 因为我们的迷宫一开始的样子是指定了的，所以不用判断哪个点是否为墙
            if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                // 打破两点中间的墙（因为迷宫初始是固定的）
                setData(x + d[i][0], y + d[i][1]);
                dfs(newX, newY);
            }
        }
    }

    // 非递归深搜
    private void dfs2() {
        Stack<Position> stack = new Stack<Position>();

        Position first = new Position(data.getEntranceX(), data.getEntranceY() + 1);
        stack.push(first);
        data.visited[first.getX()][first.getY()] = true;

        while (!stack.isEmpty()) {
            Position curPos = stack.pop();

            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0] * 2;
                int newY = curPos.getY() + d[i][1] * 2;

                if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                    stack.push(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    // 打破两点中间的墙（因为迷宫初始是固定的）
                    setData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }
    }

    private void setData(int x, int y) {
        if (data.inArea(x, y))
            data.maze[x][y] = MazeData.ROAD;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {
        int N = 101;
        int M = 101;

        AlgoVisualizer vis = new AlgoVisualizer(N, M);
    }
}
