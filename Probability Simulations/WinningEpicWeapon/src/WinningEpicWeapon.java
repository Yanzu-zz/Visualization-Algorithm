public class WinningEpicWeapon {
    private double chance; // 中史诗武器概率
    private int playTime; // 一轮抽 x 次
    private int N; // 试验 N 次

    public WinningEpicWeapon(double chance, int playTime, int N) {
        if (chance < 0.0 || chance > 1.0)
            throw new IllegalArgumentException("Chance must be between 0 and 1");

        if (playTime <= 0 || N <= 0)
            throw new IllegalArgumentException("PlayTime or N must be greater than 0");

        this.chance = chance;
        this.playTime = playTime;
        this.N = N;
    }

    public void run() {
        int wins = 0;
        for (int i = 0; i < N; i++) {
            if (play())
                wins++;
        }

        System.out.println("Winning rate: " + (double) wins / N);
    }

    private boolean play() {
        for (int i = 0; i < playTime; i++)
            if (Math.random() < chance)// 模拟 chance 的概率
                return true;

        return false;
    }

    public static void main(String[] args) {
        double chance = 0.2;
        int playTime = 5;
        int N = 1000000;

        WinningEpicWeapon exp = new WinningEpicWeapon(chance, playTime, N);
        exp.run();
    }
}
