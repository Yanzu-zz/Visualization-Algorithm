import javax.swing.*;
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
            frame = new AlgoFrame("Mine Sweeper", sceneWidth, sceneHeight);
            frame.addMouseListener(new AlgoMouseListener());

            new Thread(this::run).start();
        });
    }

    // 执行的动画逻辑
    private void run() {
        setData(false, -1, -1);
    }

    private void setData(boolean isLeftClicked, int x, int y) {
        if (data.inArea(x, y)) {
            if (isLeftClicked) {
                // 点到雷了，Game Over
                if (data.isMine(x, y)) {
                    data.open[x][y] = true;
                    JOptionPane.showMessageDialog(null, "Opps, Game Over!");
                } else {
                    data.open(x, y);
                }
            } else {
                data.flags[x][y] = !data.flags[x][y];
            }
        }

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoMouseListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            // 我们这个程序的显示区域是从程序的 nav 栏开始的，所以会有一个偏移量
            e.translatePoint(
                    -(int) (frame.getBounds().width - frame.getCanvasWidth()),
                    -(int) (frame.getBounds().height - frame.getCanvasHeight())
            );

            Point pos = e.getPoint();
            int w = frame.getCanvasWidth() / data.M();
            int h = frame.getCanvasHeight() / data.N();
            // 得到点击对应的格子的行数和列数
            int x = pos.y / h;
            int y = pos.x / w;

            // 判断是左键还是右键点击
            setData(SwingUtilities.isLeftMouseButton(e), x, y);
        }
    }

    public static void main(String[] args) {
        int N = 20;
        int M = 20;
        int mineNumber = 40;

        AlgoVisualizer visualizer = new AlgoVisualizer(N, M, mineNumber);
    }
}
