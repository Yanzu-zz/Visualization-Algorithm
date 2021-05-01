import java.awt.*;
import java.util.LinkedList;

// 控制器
public class AlgoVisualizer {
    private static int DELAY = 20;

    private AlgoFrame frame;
    MonteCarloPiData data;
    private int N; // 用户决定向屏幕中打入多少个点

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N) {
        if (sceneWidth != sceneHeight)
            throw new IllegalArgumentException("This demo need run in a square window!");

        // 初始化数据
        this.N = N;
        Circle circle = new Circle(sceneWidth / 2, sceneHeight / 2, sceneWidth / 2);
        data = new MonteCarloPiData(circle);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Get Pi with Monte Carlo", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 执行的动画逻辑
    private void run() {
        for (int i = 0; i < N; i++) {
            if (i % 100 == 0) {
                frame.render(data);
                AlgoVisHelper.pause(DELAY);
                System.out.println(data.estimatePi());
            }

            int x = (int) (Math.random() * frame.getCanvasWidth());
            int y = (int) (Math.random() * frame.getCanvasHeight());
            data.addPoint(new Point(x, y));
        }
    }

    public static void main(String[] args) {
        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 10000;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}
