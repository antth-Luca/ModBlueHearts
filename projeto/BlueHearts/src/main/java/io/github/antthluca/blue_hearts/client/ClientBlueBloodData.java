package io.github.antthluca.blue_hearts.client;

public class ClientBlueBloodData {
    private static float playerBlueBlood = 0;
    private static float maxBlueBlood = 0;

    public static void setPlayerBlueBlood(float blueBlood) {
        ClientBlueBloodData.playerBlueBlood = blueBlood;
    }

    public static float getPlayerBlueBlood() {
        return playerBlueBlood;
    }

    public static void setMaxBlueBlood(float blueBlood) {
        ClientBlueBloodData.maxBlueBlood = blueBlood;
    }

    public static float getMaxBlueBlood() {
        return maxBlueBlood;
    }
}
