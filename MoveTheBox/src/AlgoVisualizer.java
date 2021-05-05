import java.awt.*;

// 控制器
public class AlgoVisualizer {
    private static int DELAY = 10;
    private static final int blockSize = 80;

    private GameData data;
    private AlgoFrame frame;

    public AlgoVisualizer(String filename) {
        data = new GameData(filename);
        int sceneWidth = data.M() * blockSize;
        int sceneHeight = data.N() * blockSize;

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Move the Box Solver", sceneWidth, sceneHeight);

            new Thread(this::run).start();
        });
    }

    private void run() {
        setData();
    }

    private void setData() {
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {
        String filename = "level/boston_09.txt";

        AlgoVisualizer vis = new AlgoVisualizer(filename);
    }
}
