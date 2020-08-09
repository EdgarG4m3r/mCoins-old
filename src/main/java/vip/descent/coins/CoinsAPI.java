package vip.descent.coins;

import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoinsAPI {

    private CoinsHandler plugin;

    public int getCoins(String uuid) {
        try {
            PreparedStatement st = plugin.connection.prepareStatement("SELECT * FROM data.coins WHERE UUID = ?");
            st.setString(1, uuid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("coins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setCoins(String uuid, int coins){
        if (getCoins(uuid) == -1) {
            try {
                PreparedStatement st = plugin.connection.prepareStatement("INSERT INTO data (UUID,coins) VALUES (?,?)");
                st.setString(1, uuid);
                st.setInt(2, coins);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                PreparedStatement st = plugin.connection.prepareStatement("UPDATE data SET coins = ? WHERE UUID = ?");
                st.setString(1, uuid);
                st.setInt(2, coins);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addCoins(String uuid, int coins) {
        int current = getCoins(uuid);
        setCoins(uuid, coins + current);

    }

    public void removeCoins(String uuid, int coins) {
        setCoins(uuid, getCoins(uuid) - coins);
    }
}
