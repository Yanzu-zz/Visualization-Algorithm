import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

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

    private GameData data;

    public void render(GameData data) {
        this.data = data;
        repaint(); // 重新刷新画布中的物品，然后再执行一遍 paintComponet
    }

    // 本类的画板类
    private class AlgoCanvas extends JPanel {
        // 给每种不同的箱子设立一种颜色
        private HashMap<Character, Color> colorMap;
        private ArrayList<Color> colorList;

        public AlgoCanvas() {
            // JPanel 默认开启双缓存，这里只是看一下 ^_^
            super(true);

            colorMap = new HashMap<Character, Color>();

            colorList = new ArrayList<Color>();
            colorList.add(AlgoVisHelper.LightGreen);
            colorList.add(AlgoVisHelper.Purple);
            colorList.add(AlgoVisHelper.Red);
            colorList.add(AlgoVisHelper.Blue);
            colorList.add(AlgoVisHelper.Lime);
            colorList.add(AlgoVisHelper.DeepOrange);
            colorList.add(AlgoVisHelper.Amber);
            colorList.add(AlgoVisHelper.Teal);
            colorList.add(AlgoVisHelper.Brown);
            colorList.add(AlgoVisHelper.BlueGrey);
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

            Board showBoard = data.getShowBoard();
            for (int i = 0; i < showBoard.N(); i++) {
                for (int j = 0; j < showBoard.M(); j++) {
                    char c = showBoard.getData(i, j);
                    if (c != Board.EMPTY) {
                        if (!colorMap.containsKey(c)) {
                            int sz = colorMap.size();
                            colorMap.put(c, colorList.get(sz));
                        }

                        Color color = colorMap.get(c);
                        AlgoVisHelper.setColor(g2d, color);
                        AlgoVisHelper.fillRectangle(g2d, j * h + 2, i * w + 2, w - 4, h - 4);

                        // 画完箱子后画对应的坐标
                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.White);
                        String text = String.format("(%d, %d)", i, j);
                        AlgoVisHelper.drawText(g2d, text, (j * h) + (h / 2), (i * w) + (w / 2));
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
