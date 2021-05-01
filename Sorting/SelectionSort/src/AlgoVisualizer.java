import java.awt.*;

// 控制器
public class AlgoVisualizer {
    private static int DELAY = 10;

    private SelectionSortData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N) {
        // 初始化数据
        data = new SelectionSortData(N, sceneHeight);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Selection Sort Visualization", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 执行的动画逻辑
    // 下面代码就不注释了，希望你能好好阅读以下，体会体会其中的意义
    private void run() {
        setData(0, -1, -1);

        for (int i = 0; i < data.N(); i++) {
            int minIndex = i;
            setData(i, -1, minIndex);

            for (int j = i + 1; j < data.N(); j++) {
                setData(i, j, minIndex);
                if (data.get(j) < data.get(minIndex)) {
                    minIndex = j;
                    setData(i, j, minIndex);
                }
            }

            data.swap(i, minIndex);
            setData(i + 1, -1, -1);
        }

        setData(data.N(), -1, -1);
    }

    // 通过改变三个可视化最重要的元素来渲染 选择排序过程
    private void setData(int orderedIndex, int currentCompareIndex, int currentMinIndex) {
        data.orderdIndex = orderedIndex;
        data.currentCompareIndex = currentCompareIndex;
        data.currentMinIndex = currentMinIndex;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {
        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 20;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}
