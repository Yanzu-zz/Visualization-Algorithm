import java.awt.*;
import java.awt.geom.Ellipse2D;

// 工具类
public class AlgoVisHelper {
    private AlgoVisHelper() {
    }

    // 为图形上下文环境 g2d 设置相应的 宽w 的画笔线条
    public static void setStrokeWidth(Graphics2D g2d, int w) {
        int strokeWidth = w;
        // 设置画笔在作画时的图形边缘平滑
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    public static void setColor(Graphics2D g2d, Color color) {
        g2d.setColor(color);
    }

    /**
     * 在（x, y）上绘制一个半径为 r 的空心的圆形
     *
     * @param g2d 上下文
     * @param x   圆心x 坐标
     * @param y   圆心y 坐标
     * @param r   圆的半径
     */
    public static void strokeCircle(Graphics2D g2d, int x, int y, int r) {
        Ellipse2D circle = new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
        g2d.draw(circle);
    }

    // 画一个实心圆，参数同上
    public static void fillCircle(Graphics2D g2d, int x, int y, int r) {
        Ellipse2D circle = new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
        g2d.fill(circle);
    }

    public static void pause(int t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            System.out.println("Error in sleeping.");
        }
    }
}
