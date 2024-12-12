package io.github.antthluca.blue_hearts.client;

public class ClientBlueBloodData {
    private static int playerBlueBlood;

    public static void set(int blueBlood) {
        ClientBlueBloodData.playerBlueBlood = blueBlood;
    }

    public static int getPlayerBlueBlood() {
        return playerBlueBlood;
    }
}
