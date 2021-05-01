import java.awt.*;

// 不带可视化算法，用蒙特卡洛模拟算法模拟出 PI 的值
public class MonteCarloPiDataWithoutRendering {
    private int squareSide;
    private int N;
    private int outputInterval = 10000;

    public MonteCarloPiDataWithoutRendering(int squareSide, int N) {
        if (squareSide <= 0 || N <= 0)
            throw new IllegalArgumentException("squareSide and N must larger than 0!");

        this.squareSide = squareSide;
        this.N = N;
    }

    public void setOutputInterval(int interval) {
        if (interval <= 0)
            throw new IllegalArgumentException("Interval must above than 0");

        this.outputInterval = interval;
    }

    public void run() {
        int t = squareSide / 2;
        Circle circle = new Circle(t, t, t);
        MonteCarloPiData data = new MonteCarloPiData(circle);

        for (int i = 0; i < N; i++) {
            if (i % this.outputInterval == 0)
                System.out.println(data.estimatePi());

            int x = (int) (Math.random() * squareSide);
            int y = (int) (Math.random() * squareSide);
            data.addPoint(new Point(x, y));
        }
    }

    public static void main(String[] args) {
        int squareSide = 800;
        int N = 1000000;

        MonteCarloPiDataWithoutRendering exp = new MonteCarloPiDataWithoutRendering(squareSide, N);
        exp.setOutputInterval(N / 10);
        exp.run();
    }
}
