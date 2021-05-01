import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

// 控制器
public class AlgoVisualizer {
    private static int DELAY = 40;
    private int[] money;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight) {
        // 初始化数据
        money = new int[100];
        for (int i = 0; i < money.length; i++)
            money[i] = 100;

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Money Problem", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 执行的动画逻辑
    private void run() {
        while (true) {
            Arrays.sort(money);// 排下序，这样就能更好看清钱的分布
            frame.render(money);
            AlgoVisHelper.pause(DELAY);

            // 50轮 更新一次
            for (int k = 0; k < 50; k++) {
                for (int i = 0; i < money.length; i++) {
                    //if (money[i] > 0) {
                    int j = (int) (Math.random() * money.length);
                    money[i] -= 1;
                    money[j] += 1;
                    //}
                }
            }
        }
    }


    public static void main(String[] args) {
        int sceneWidth = 1000;
        int sceneHeight = 800;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight);
    }
}
