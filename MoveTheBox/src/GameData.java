import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class GameData {
    private int maxTurn;
    private int N, M;
    private Board starterBoard; // 初始盘面
    private Board showBoard; // 真正要显示的盘面

    // 用哈希查找表解决重叠子问题
    // 当然，我们这里的数据量和移动步数可能过小，重叠子问题可能都不会出现
    // 因此还可能不如不判断重叠子问题速度快，主要还是为了学习去重剪枝
    private HashSet<Board> searchBoards;
    // 箱子能移动的方向，注意，当一个箱子上面是空时向上移动是没意义的，因为重力会使它下落
    // 而一个箱子上面有箱子时，它和上面的交换位置等同于上面的箱子与它向下交换，故这里就不添加向上移动的方向
    // 而左右不能省略的原因是 解决问题可能需要与左右边缘的空位交换位置
    private static final int d[][] = {{1, 0}, {0, 1}, {0, -1}};

    // 读取盘面信息
    public GameData(String filename) {
        if (filename == null)
            throw new IllegalArgumentException("Filename cannot be null!");

        Scanner scanner = null;

        try {
            File file = new File(filename);
            if (!file.exists())
                throw new IllegalArgumentException("file doesn't exist!");

            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");

            // 读取该局游戏至多允许挪动几步
            String turnline = scanner.nextLine();
            this.maxTurn = Integer.parseInt(turnline);

            // 把 box 初始状态放入动态数组
            ArrayList<String> lines = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }

            starterBoard = new Board(lines.toArray(new String[lines.size()]));
            N = starterBoard.N();
            M = starterBoard.M();
            starterBoard.print();

            showBoard = new Board(starterBoard);
            searchBoards = new HashSet<Board>();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null)
                scanner.close();
        }
    }

    public boolean solve() {
        if (maxTurn < 0)
            return false;

        searchBoards.add(starterBoard);
        return solve(starterBoard, maxTurn);
    }

    // 通过盘面 board，使用 turn 次 move，解决 move the box 问题
    private boolean solve(Board board, int turn) {
        if (board == null || turn < 0)
            throw new IllegalArgumentException("Illegal arguments in solve function!");

        boolean isWin = board.isWin();
        // 当次数用完，检查所有箱子是否被消除了
        if (turn == 0)
            return isWin;
        // 当次数没用完，但是已经把箱子给消除完了，也返回胜利
        if (isWin)
            return true;

        // 当没解决问题时进行搜索解决问题
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                if (board.getData(x, y) != Board.EMPTY) {
                    // 寻找新的点进行移动消除操作
                    for (int i = 0; i < d.length; i++) {
                        int newX = x + d[i][0];
                        int newY = y + d[i][1];

                        if (inArea(newX, newY)) {
                            String swapString = String.format("swap (%d, %d) and (%d, %d)", x, y, newX, newY);
                            Board nextBoard = new Board(board, board, swapString);

                            // 进行移动操作
                            nextBoard.swap(x, y, newX, newY);
                            // 注意，这里进行了移动可能会导致箱子的消除和掉落，故进行相应操作
                            nextBoard.run();

                            // 当移动后的盘面之前没发生过时才进行移动消除操作
                            if (!searchBoards.contains(nextBoard)) {
                                searchBoards.add(nextBoard);
                                if (solve(nextBoard, turn - 1))
                                    return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public int N() {
        return N;
    }

    public int M() {
        return M;
    }

    public Board getShowBoard() {
        return showBoard;
    }

    public boolean inArea(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}
