package io.github.antthluca.blue_hearts.client;

public class ClientBlueBloodData {
    private static int playerBlueBlood = 0;
    private static int maxBlueBlood = 0;

    public static void setPlayerBlueBlood(int blueBlood) {
        ClientBlueBloodData.playerBlueBlood = blueBlood;
    }

    public static int getPlayerBlueBlood() {
        return playerBlueBlood;
    }

    public static void setMaxBlueBlood(int blueBlood) {
        ClientBlueBloodData.maxBlueBlood = blueBlood;
    }

    public static int getMaxBlueBlood() {
        return maxBlueBlood;
    }
}
