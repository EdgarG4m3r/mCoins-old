package tech.solexgames.coins;

import tech.solexgames.coins.utils.Utilities;

public class CoinsAPI {

    public static int getCoins(String uuid) {
        return Utilities.getCoins(uuid);
    }

    public static void setCoins(String uuid, int coins) {
        Utilities.setCoins(uuid, coins);
    }

    public static void addCoins(String uuid, int coins) {
        Utilities.addCoins(uuid, coins);
    }

    public static void removeCoins(String uuid, int coins) {
        Utilities.removeCoins(uuid, coins);
    }
}