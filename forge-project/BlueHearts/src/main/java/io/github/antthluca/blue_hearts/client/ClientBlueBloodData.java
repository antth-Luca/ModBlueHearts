package io.github.antthluca.blue_hearts.client;

public class ClientBlueBloodData {
    private static float blueBlood = 0;
    private static float maxBlueBlood = 0;

    public static void setBlueBlood(float newBlood) {
        ClientBlueBloodData.blueBlood = newBlood;
    }

    public static float getBlueBlood() {
        return blueBlood;
    }

    public static void setMaxBlueBlood(float newMax) {
        ClientBlueBloodData.maxBlueBlood = newMax;
    }

    public static float getMaxBlueBlood() {
        return maxBlueBlood;
    }
}