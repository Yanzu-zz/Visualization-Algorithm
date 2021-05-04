public class MazeData {
    public static final char ROAD = ' ';
    public static final char WALL = '#';

    private int N, M;
    public char[][] maze;
    public boolean[][] visited;
    public boolean[][] inMist; // 没生成路径的区域用迷雾遮挡
    public boolean[][] isPath; // 解迷宫最终的路径

    private int entranceX, entranceY;
    private int exitX, exitY;

    public MazeData(int N, int M) {
        if (N % 2 == 0 || M % 2 == 0)
            throw new IllegalArgumentException("Our Maze Generalization Algorithm needed appointed odd N and M");

        this.N = N;
        this.M = M;
        maze = new char[N][M];
        visited = new boolean[N][M];
        inMist = new boolean[N][M];
        isPath = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (i % 2 == 1 && j % 2 == 1)
                    maze[i][j] = ROAD;
                else
                    maze[i][j] = WALL;

                visited[i][j] = false;
                inMist[i][j] = true; // 初始时所有点都是在迷雾中
                isPath[i][j] = false;
            }
        }

        /*entranceX = 0;
        while (true) {
            int t = (int) (Math.random() * (N - 1));
            if (t % 2 == 0)
                continue;
            entranceY = t;
            break;
        }
        while (true) {
            int t = (int) (Math.random() * (M - 1));
            if (t % 2 == 0)
                continue;
            exitX = t;
            break;
        }
        exitY = N - 1;*/
        entranceX = 1;
        entranceY = 0;
        exitX = N - 2;
        exitY = M - 1;

        maze[entranceX][entranceY] = ROAD;
        maze[exitX][exitY] = ROAD;
    }

    public boolean inArea(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public char getMaze(int x, int y) {
        return maze[x][y];
    }

    public int N() {
        return N;
    }

    public int M() {
        return M;
    }

    public int getEntranceX() {
        return entranceX;
    }

    public int getEntranceY() {
        return entranceY;
    }

    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
    }

    public void openMist(int x, int y) {
        if (!inArea(x, y))
            throw new IllegalArgumentException("x or y is out of index in inMist Array");

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (inArea(i, j))
                    inMist[i][j] = false;
            }
        }
    }
}
