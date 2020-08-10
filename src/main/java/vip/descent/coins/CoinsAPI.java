package vip.descent.coins;

import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CoinsAPI {

    private CoinsHandler plugin;

    public CoinsAPI(CoinsHandler plugin) {
        this.plugin = plugin;
    }

    public int getCoins(String uuid) {
        try {
            PreparedStatement st = plugin.connection.prepareStatement("SELECT * FROM coins WHERE UUID = ?");
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

    public void createProfile(UUID uuid) {
        try {
            PreparedStatement st = plugin.connection.prepareStatement("INSERT INTO coins (UUID,coins) VALUES (?,?)");
            st.setString(1, String.valueOf(uuid));
            st.setInt(2, 0);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasProfile(Player player) {
        try {
            PreparedStatement e = plugin.connection.prepareStatement("SELECT * FROM coins WHERE UUID = (\'" + player.getUniqueId() + "\');");
            ResultSet results = e.executeQuery();
            return results.next();
        } catch (SQLException var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public void setCoins(String uuid, int coins) {
        if (getCoins(uuid) == -1) {
            try {
                PreparedStatement st = plugin.connection.prepareStatement("INSERT INTO coins (UUID,coins) VALUES = ?,?");
                st.setInt(2, coins);
                st.setString(1, uuid);
                st.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                PreparedStatement st = plugin.connection.prepareStatement("UPDATE coins SET coins = ? WHERE UUID = ?");
                st.setString(2, uuid);
                st.setInt(1, coins);
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
