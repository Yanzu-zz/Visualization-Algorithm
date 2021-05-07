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
            drawFractal(g2d, 0, canvasHeight - 3, canvasWidth, 0, 0);
        }

        private void drawFractal(Graphics2D g, double x1, double y1, double side, double angle, int depth) {
            // 递归到底情况
            if (side <= 0)
                return;
            if (depth == data.depth) {
                double x2 = x1 + side * Math.cos(angle * Math.PI / 180.0);
                double y2 = y1 - side * Math.sin(angle * Math.PI / 180.0);
                AlgoVisHelper.setColor(g, AlgoVisHelper.Indigo);
                AlgoVisHelper.drawLine(g, x1, y1, x2, y2);
                return;
            }

            // 没递归到底，绘制图形
            double side_3 = side / 3;
            // 第一段递归线绘制
            double x2 = x1 + side_3 * Math.cos(angle * Math.PI / 180.0);
            double y2 = y1 - side_3 * Math.sin(angle * Math.PI / 180.0); // 使用减法是因为这是屏幕坐标系
            drawFractal(g, x1, y1, side_3, angle, depth + 1);

            // 第二段递归线绘制
            double x3 = x2 + side_3 * Math.cos((angle + 60.0) * Math.PI / 180.0);
            double y3 = y2 - side_3 * Math.sin((angle + 60.0) * Math.PI / 180.0); // 使用减法是因为这是屏幕坐标系
            drawFractal(g, x2, y2, side_3, angle + 60.0, depth + 1);

            // 第三段递归线绘制
            double x4 = x3 + side_3 * Math.cos((angle - 60.0) * Math.PI / 180.0);
            double y4 = y3 - side_3 * Math.sin((angle - 60.0) * Math.PI / 180.0); // 使用减法是因为这是屏幕坐标系
            drawFractal(g, x3, y3, side_3, angle - 60.0, depth + 1);

            // 第四段递归线绘制
            // 这里不需要计算了，因为是直线
            drawFractal(g, x4, y4, side_3, angle, depth + 1);
        }

        @Override
        public Dimension getPreferredSize() {
            // 重写了这个函数后，创建 AlgoCanvas 时会自动设置我们传入的画布大小
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
