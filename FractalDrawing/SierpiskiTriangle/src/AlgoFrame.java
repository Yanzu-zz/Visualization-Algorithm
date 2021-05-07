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
            // RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // g2d.addRenderingHints(hints);

            // 具体绘制
            drawFractal(g2d, 0, canvasHeight, canvasWidth, 0);
        }

        private void drawFractal(Graphics2D g, int Ax, int Ay, int side, int depth) {
            // 递归到底情况
            if (side <= 1) {
                AlgoVisHelper.setColor(g, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillRectangle(g, Ax, Ay, 1, 1);
                return;
            }
            // 知道一个点，计算正三角形的其它两个点（注意，这是屏幕坐标系）
            int Bx = Ax + side, By = Ay;
            int h = (int) (Math.sin(60.0 * Math.PI / 180.0) * side);
            int Cx = Ax + side / 2, Cy = Ay - h;
            if (depth == data.depth) {
                AlgoVisHelper.setColor(g, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillTriangle(g, Ax, Ay, Bx, By, Cx, Cy);
                return;
            }

            // 没到递归边界，继续递归绘制
            // 子递归三角形坐标
            int AB_centerx = (Ax + Bx) / 2;
            int AB_centery = (Ay + By) / 2;
            int AC_centerx = (Ax + Cx) / 2;
            int AC_centery = (Ay + Cy) / 2;
            // int BC_centerx = (Bx + Cx) / 2;
            // int BC_centery = (By + Cy) / 2;

            drawFractal(g, Ax, Ay, side / 2, depth + 1); // 左下角三角形
            drawFractal(g, AC_centerx, AC_centery, side / 2, depth + 1); // 上面三角形
            drawFractal(g, AB_centerx, AB_centery, side / 2, depth + 1); // 右下角三角形
        }

        @Override
        public Dimension getPreferredSize() {
            // 重写了这个函数后，创建 AlgoCanvas 时会自动设置我们传入的画布大小
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
