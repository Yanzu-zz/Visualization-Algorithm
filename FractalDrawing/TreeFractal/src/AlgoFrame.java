import javax.swing.*;
import java.awt.*;

// 视图层
public class AlgoFrame extends JFrame {
    private int canvasWidth, canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight) {
        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);
        pack();

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public AlgoFrame(String title) {
        this(title, 1024, 768);
    }

    public int getCanvasWidth() {
        return this.canvasWidth;
    }

    public int getCanvasHeight() {
        return this.canvasHeight;
    }

    private FractalData data;

    public void render(FractalData data) {
        this.data = data;
        repaint(); // 重新刷新画布中的物品，然后再执行一遍 paintComponet
    }

    // 本类的画板类
    private class AlgoCanvas extends JPanel {
        public AlgoCanvas() {
            // JPanel 默认开启双缓存，这里只是看一下 ^_^
            super(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            // 打开抗锯齿
            RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.addRenderingHints(hints);

            // 具体绘制
            drawFractal(g2d, canvasWidth / 2, canvasHeight, canvasHeight / 2, 0, 0);
        }

        private void drawFractal(Graphics2D g, double x1, double y1, double side, double angle, int depth) {
            double side_2 = side / 2;
            int strokeWidth = (data.depth - depth) + 1;
            AlgoVisHelper.setStrokeWidth(g,strokeWidth);

            // 递归到底情况
            if (side_2 <= 0)
                return;
            if (depth == data.depth) {
                double x2 = x1 - side * 2 * Math.sin(angle * Math.PI / 180.0);
                double y2 = y1 - side * 2 * Math.cos(angle * Math.PI / 180.0);
                AlgoVisHelper.setColor(g, AlgoVisHelper.Indigo);
                AlgoVisHelper.drawLine(g, x1, y1, x2, y2);
                return;
            }

            // 递归没到底，继续绘制
            double x2 = x1 - side_2 * 2 * Math.sin(angle * Math.PI / 180.0);
            double y2 = y1 - side_2 * 2 * Math.cos(angle * Math.PI / 180.0);
            AlgoVisHelper.setColor(g, AlgoVisHelper.Indigo);
            AlgoVisHelper.drawLine(g, x1, y1, x2, y2);

            // 左侧分叉
            drawFractal(g, x2, y2, side_2, angle + data.splitAngle / 2, depth + 1);
            // 右侧分叉
            drawFractal(g, x2, y2, side_2, angle - data.splitAngle / 2, depth + 1);
        }

        @Override
        public Dimension getPreferredSize() {
            // 重写了这个函数后，创建 AlgoCanvas 时会自动设置我们传入的画布大小
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
