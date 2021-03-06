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

    MineSweeperData data;

    public void render(MineSweeperData data) {
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
            int w = canvasWidth / data.M();
            int h = canvasHeight / data.N();

            for (int i = 0; i < data.N(); i++) {
                for (int j = 0; j < data.M(); j++) {
                    // 查看 ij 处是否被玩家点开了
                    if (data.open[i][j]) {
                        if (data.isMine(i, j))
                            AlgoVisHelper.putImage(g2d, j * w, i * h, MineSweeperData.mineImageURL);
                        else
                            AlgoVisHelper.putImage(g2d, j * w, i * h, MineSweeperData.numberImageURL(data.getNumber(i, j)));
                    } else {
                        if (data.flags[i][j])
                            AlgoVisHelper.putImage(g2d, j * w, i * h, MineSweeperData.flagImageURL);
                        else
                            AlgoVisHelper.putImage(g2d, j * w, i * h, MineSweeperData.blockImageURL);
                    }
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            // 重写了这个函数后，创建 AlgoCanvas 时会自动设置我们传入的画布大小
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
