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

    private SelectionSortData data;

    public void render(SelectionSortData data) {
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
            int w = canvasWidth / data.N();
            for (int i = 0; i < data.N(); i++) {
                if (i < data.orderdIndex)
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.DeepOrange);
                else
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Grey);

                if (i == data.currentCompareIndex)
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.LightBlue);

                if (i == data.currentMinIndex)
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);

                AlgoVisHelper.fillRectangle(g2d, i * w, canvasHeight - data.get(i), w - 1, data.get(i));
            }
        }

        @Override
        public Dimension getPreferredSize() {
            // 重写了这个函数后，创建 AlgoCanvas 时会自动设置我们传入的画布大小
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
