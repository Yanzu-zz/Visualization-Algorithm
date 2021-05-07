import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// 控制器
public class AlgoVisualizer {
    private static int DELAY = 10;

    private FractalData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int maxDepth, int side) {
        // 初始化数据
        data = new FractalData(maxDepth);
        int sceneWidth = 3 * side + 3;
        int sceneHeight = side;

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Koch Snowflake", sceneWidth, sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());

            new Thread(this::run).start();
        });
    }

    // 执行的动画逻辑
    private void run() {
        setData(data.depth);
    }

    private void setData(int depth) {
        if (depth >= 0)
            data.depth = depth;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            char ch = e.getKeyChar();
            if (ch >= '0' && ch <= '9') {
                int depth = ch - '0';
                setData(depth);
            }
        }
    }

    public static void main(String[] args) {
        int maxDepth = 6;
        int side = 300;

        AlgoVisualizer visualizer = new AlgoVisualizer(maxDepth, side);
    }
}
