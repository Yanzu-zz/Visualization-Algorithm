// 具体问题描述请看 README.md
public class MontyHallExperiment {
    private int N;

    public MontyHallExperiment(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("N must be larger than 0!");

        this.N = N;
    }

    public void run(boolean changeDoor) {
        int wins = 0;
        for (int i = 0; i < N; i++) {
            if (play(changeDoor))
                wins++;
        }

        System.out.println(changeDoor ? "Change" : "Not Change");
        System.out.println("Winning rate: " + (double) wins / N);
    }

    /**
     * 换门/不换门 玩三门游戏是否胜利
     *
     * @param changeDoor
     */
    private boolean play(boolean changeDoor) {
        // 假设三扇门编号为：0, 1, 2
        // 然后随机生成一个获奖门
        int prizeDoor = (int) (Math.random() * 3);
        // 然后玩家也不可能知道哪扇门有奖，故是随机选择的
        int playerChoice = (int) (Math.random() * 3);

        // 下面的逻辑就很简单了
        // 当主持人公布了一扇一定没有奖品的门后，而玩家又选择了对的门
        // 此时换门就失败，坚持不换门就赢
        if (playerChoice == prizeDoor) {
            return changeDoor ? false : true;
        }
        // 否则的逻辑也很好理解
        // 如果一开始玩家选错了，而主持人又开了没有奖品的门
        // 此时玩家只要是选择换门就胜利，否则失败
        else {
            return changeDoor ? true : false;
        }
    }

    public static void main(String[] args) {
        int N = 100000;

        MontyHallExperiment exp = new MontyHallExperiment(N);
        exp.run(true);
        System.out.println();
        exp.run(false);
    }
}
