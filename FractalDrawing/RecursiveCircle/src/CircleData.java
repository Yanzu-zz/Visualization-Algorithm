public class CircleData {
    private int startX, startY, startR;
    private int depth;
    private int step;

    /**
     * 圆形
     *
     * @param x    圆形坐标
     * @param y
     * @param r    圆的半径
     * @param d    递归的深度
     * @param step 下一个圆与上个圆半径的步长，即下个圆的半径比这个圆少多少
     */
    public CircleData(int x, int y, int r, int d, int step) {
        this.startX = x;
        this.startY = y;
        this.startR = r;
        this.depth = d;
        this.step = step;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getStartR() {
        return startR;
    }

    public int getDepth() {
        return depth;
    }

    public int getStep() {
        return step;
    }
}
