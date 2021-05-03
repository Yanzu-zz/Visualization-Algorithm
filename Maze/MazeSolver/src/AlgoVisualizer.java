import java.awt.*;
import java.util.LinkedList;
import java.util.Stack;

public class AlgoVisualizer {
    private static int DELAY = 1;
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

        // 开始走迷宫（递归深度优先搜索走法）
        //if (!dfs(data.getEntranceX(), data.getEntranceY()))
        //    System.out.println("This maze has NO solution!");

        // 开始走迷宫（非递归深度优先搜索走法）
        //dfs2();

        // 开始走迷宫（广度优先搜索）
        bfs();

        setData(-1, -1, false);
    }

    // 从 （x,y） 点开始找迷宫出口，递归方法
    private boolean dfs(int x, int y) {
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
                if (dfs(newX, newY))
                    return true;
            }
        }
        // 对（x, y）这个点四个方向都尝试一遍，然而没有路时就要取消掉这个点是出口路径
        setData(x, y, false);

        return false;
    }

    // 从 （x,y） 点开始找迷宫出口，非递归方法
    private void dfs2() {
        Stack<Position> stack = new Stack<Position>();

        // 处理入口
        Position entrance = new Position(data.getEntranceX(), data.getEntranceY());
        stack.push(entrance);
        data.visited[entrance.getX()][entrance.getY()] = true;

        // 下面就是当栈非空时，一直深搜查找
        boolean isSolved = false;
        while (!stack.isEmpty()) {
            Position curPos = stack.pop();
            setData(curPos.getX(), curPos.getY(), true);

            if (curPos.getX() == data.getExitX() && curPos.getY() == data.getExitY()) {
                isSolved = true;
                // 当找到出口后，就从当前出口位置向前寻找对的路径
                // 这是非递归和动态规划思想常用的方法
                findPath(curPos);
                break;
            }

            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0];
                int newY = curPos.getY() + d[i][1];

                if (data.inArea(newX, newY)
                        && !data.visited[newX][newY]
                        && data.getMaze(newX, newY) == MazeData.ROAD) {
                    stack.push(new Position(newX, newY, curPos));
                    data.visited[newX][newY] = true;
                }
            }
        }

        if (!isSolved)
            System.out.println("The maze has no solution!");
    }

    private void bfs() {
        LinkedList<Position> queue = new LinkedList<Position>();

        Position entrance = new Position(data.getEntranceX(), data.getEntranceY());
        queue.addLast(entrance);
        data.visited[entrance.getX()][entrance.getY()] = true;

        boolean isSolved = false;
        while (queue.size() != 00) {
            Position curPos = queue.pop();
            setData(curPos.getX(), curPos.getY(), true);

            if (curPos.getX() == data.getExitX() && curPos.getY() == data.getExitY()) {
                isSolved = true;
                // 当找到出口后，就从当前出口位置向前寻找对的路径
                // 这是非递归和动态规划思想常用的方法
                findPath(curPos);
                break;
            }

            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0];
                int newY = curPos.getY() + d[i][1];

                if (data.inArea(newX, newY)
                        && !data.visited[newX][newY]
                        && data.getMaze(newX, newY) == MazeData.ROAD) {
                    queue.addLast(new Position(newX, newY, curPos));
                    data.visited[newX][newY] = true;
                }
            }
        }

        if (!isSolved)
            System.out.println("The maze has no solution!");
    }

    // 从迷宫终点开始找相应的走迷宫路线
    private void findPath(Position des) {
        Position cur = des;
        while (cur != null) {
            data.result[cur.getX()][cur.getY()] = true;
            cur = cur.getPrev();
        }
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
