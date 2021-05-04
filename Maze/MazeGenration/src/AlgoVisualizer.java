import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Stack;

// 控制器
public class AlgoVisualizer {
    private static final int DELAY = 1;
    private static int blockSize = 8;
    private static final int d[][] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    private MazeData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int N, int M) {
        data = new MazeData(N, M);
        int sceneHeight = data.N() * blockSize;
        int sceneWidth = data.M() * blockSize;

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Random Maze Generation Visualization", sceneWidth, sceneHeight);
            // 生成完迷宫后可以按 空格键 开始走迷宫
            frame.addKeyListener(new AlgoKeyListener());

            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run() {
        setData(-1, -1);

        // dfs(data.getEntranceX(), data.getEntranceY() + 1);
        // dfs2();
        randomArrayGenerateMaze();

        setData(-1, -1);
    }

    private void dfs(int x, int y) {
        if (!data.inArea(x, y))
            throw new IllegalArgumentException("x,y are out of border of the maze!");
        data.visited[x][y] = true;
        data.openMist(x, y);

        // 注意，本个深搜的递归终止条件隐含在了下面的循环里
        for (int i = 0; i < 4; i++) {
            int newX = x + d[i][0] * 2;
            int newY = y + d[i][1] * 2;

            // 因为我们的迷宫一开始的样子是指定了的，所以不用判断哪个点是否为墙
            if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                // 打破两点中间的墙（因为迷宫初始是固定的）
                data.openMist(newX, newY);
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

    // 广搜我就不写了
    // 直接上随机队列生成迷宫
    private void randomArrayGenerateMaze() {
        // RandomQueue<Position> queue = new RandomQueue<Position>();
        RandomQueue2<Position> queue = new RandomQueue2<Position>(); // 随机性更强的随机队列

        Position first = new Position(data.getEntranceX(), data.getEntranceY() + 1);
        queue.add(first);
        data.visited[first.getX()][first.getY()] = true;
        data.inMist[first.getX()][first.getY()] = false;
        data.openMist(first.getX(), first.getY());

        while (!queue.isEmpty()) {
            Position curPos = queue.remove();

            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0] * 2;
                int newY = curPos.getY() + d[i][1] * 2;

                if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                    queue.add(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    data.inMist[newX][newY] = false;
                    // 打破两点中间的墙（因为迷宫初始是固定的）并且把迷雾取消
                    data.openMist(newX, newY);
                    setData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }
    }

    // 从 （x,y） 点开始找迷宫出口，递归方法
    private boolean solveMaze(int x, int y) {
        if (!data.inArea(x, y))
            throw new IllegalArgumentException("x,y are out of index in maze");

        data.visited[x][y] = true;
        setPathData(x, y, true);

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
                if (solveMaze(newX, newY))
                    return true;
            }
        }
        // 对（x, y）这个点四个方向都尝试一遍，然而没有路时就要取消掉这个点是出口路径
        setPathData(x, y, false);

        return false;
    }

    private void setData(int x, int y) {
        if (data.inArea(x, y))
            data.maze[x][y] = MazeData.ROAD;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private void setPathData(int x, int y, boolean isPath) {
        if (data.inArea(x, y))
            data.isPath[x][y] = isPath;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoKeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == ' ') {
                for (int i = 0; i < data.N(); i++)
                    for (int j = 0; j < data.M(); j++)
                        data.visited[i][j] = false;
                new Thread(() -> {
                    if(!solveMaze(data.getEntranceX(), data.getEntranceY()))
                        System.out.println("This Maze has no resolution!");
                }).start();
            }
        }
    }

    public static void main(String[] args) {
        int N = 101;
        int M = 101;

        AlgoVisualizer vis = new AlgoVisualizer(N, M);
    }
}
