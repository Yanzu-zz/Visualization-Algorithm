import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// 控制器
public class AlgoVisualizer {
    private static int DELAY = 5;
    private static final int blockSize = 32;

    private MineSweeperData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int N, int M, int mineNumber) {
        // 初始化数据
        data = new MineSweeperData(N, M, mineNumber);
        int sceneWidth = M * blockSize;
        int sceneHeight = N * blockSize;

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Mine Sweeper Visualization", sceneWidth, sceneHeight);
            frame.addMouseListener(new AlgoMouseListener());

            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 执行的动画逻辑
    private void run() {
        setData();
    }

    private void setData() {
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoMouseListener extends MouseAdapter {
    }

    public static void main(String[] args) {
        int N = 20;
        int M = 20;
        int mineNumber = 40;

        AlgoVisualizer visualizer = new AlgoVisualizer(N, M, mineNumber);
    }
}
