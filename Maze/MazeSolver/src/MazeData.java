import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MazeData {
    public static final char ROAD = ' ';
    public static final char WALL = '#';

    // 入口和出口的坐标
    private int entranceX, entranceY;
    private int exitX, exitY;

    private int N, M;
    private char[][] maze;

    public boolean[][] visited;
    public boolean[][] path;

    public MazeData(String filename) {
        if (filename == null)
            throw new IllegalArgumentException("Filename cannot be null!");

        Scanner scanner = null;
        try {
            File file = new File(filename);
            if (!file.exists())
                throw new IllegalArgumentException("Filename cannot be null!");

            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");

            String nmline = scanner.nextLine();
            String[] nm = nmline.trim().split("\\s+");

            N = Integer.parseInt(nm[0]);
            M = Integer.parseInt(nm[1]);

            maze = new char[N][M];
            visited = new boolean[N][M]; // JAVA 8 后默认值为 false，但还是显示声明为 false 好点
            path = new boolean[N][M];

            for (int i = 0; i < N; i++) {
                String line = scanner.nextLine();

                // 保证每行有 M 个字符
                if (line.length() != M)
                    throw new IllegalArgumentException("Maze file " + filename + " 's row count may not match line's");

                for (int j = 0; j < M; j++) {
                    maze[i][j] = line.charAt(j);
                    visited[i][j] = false;
                    path[i][j] = false;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (scanner != null)
                scanner.close();
        }

        // 这里为了方便起见，直接固定死入口为（1,0）出口为（N-2,M-1）
        // 当然，中间的路径是随机的
        entranceX = 1;
        entranceY = 0;
        exitX = N - 2;
        exitY = M - 1;
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

    public char getMaze(int i, int j) {
        if (!inArea(i, j))
            throw new IllegalArgumentException("i or j is out of index in getmaze");

        return maze[i][j];
    }

    public boolean inArea(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public void print() {
        System.out.println(N + " " + M);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++)
                System.out.print(maze[i][j]);
            System.out.println();
        }
    }
}
