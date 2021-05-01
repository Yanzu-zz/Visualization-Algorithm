import java.awt.*;

// 数据层
public class Circle {
    public int x, y; // 圆心的坐标（x, y）
    private int r; // 初始化圆的半径后就不能改变了
    public int vx, vy; // 速度
    public boolean isFilled = false; // 是否是实心圆

    public Circle(int x, int y, int r, int vx, int vy) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.vx = vx;
        this.vy = vy;
    }

    public int getR() {
        return r;
    }

    public void move(int minx, int miny, int maxx, int maxy) {
        x += vx;
        y += vy;

        // 移动的时候才需要边界监测
        checkCollision(minx, miny, maxx, maxy);
    }

    // 监测碰撞
    public void checkCollision(int minx, int miny, int maxx, int maxy) {
        if (x - r < minx) { // 碰到左边缘
            x = r; // 碰到了就紧贴左边缘
            vx = -vx; // 接着运动方向变反
        } // 下面逻辑相同
        if (x + r >= maxx) { // 碰到右边缘
            x = maxx - r;
            vx = -vx;
        }

        if (y - r < miny) { // 碰到上边缘
            y = r;
            vy = -vy;
        }
        if (y + r >= maxy) { // 碰到下边缘
            y = maxy - r;
            vy = -vy;
        }
    }

    public boolean contain(Point p) {
        return (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y) <= r * r;
    }
}
