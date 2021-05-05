import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameData {
    private int maxTurn;
    private Board starterBoard; // 初始盘面
    private int N, M;

    private Board showBoard; // 真正要显示的盘面

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

            showBoard = new Board(starterBoard);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null)
                scanner.close();
        }
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
