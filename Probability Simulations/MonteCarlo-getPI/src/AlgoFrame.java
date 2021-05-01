import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

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

    private MonteCarloPiData data;

    public void render(MonteCarloPiData data) {
        // 这里的代码很烂，每次都要重新绘制 points 里的所有点
        // 但这里我要学习的是算法可视化，所以关于优化 java gui 的代码就不重点写了
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
            Circle circle = data.getCircle();
            AlgoVisHelper.setStrokeWidth(g2d, 3);
            AlgoVisHelper.strokeCircle(g2d, circle.getX(), circle.getY(), circle.getR());

            for (int i = 0; i < data.getPointsNumber(); i++) {
                Point p = data.getPoint(i);

                if (circle.contain(p))
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);
                else
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Green);

                AlgoVisHelper.fillCircle(g2d, p.x, p.y, 3);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            // 重写了这个函数后，创建 AlgoCanvas 时会自动设置我们传入的画布大小
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
