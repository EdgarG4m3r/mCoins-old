package tech.solexgames.coins.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tech.solexgames.coins.CoinsHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Utilities {

    private static CoinsHandler plugin;

    public Utilities(CoinsHandler plugin) {
        this.plugin = plugin;
    }

    public static int getCoins(String uuid) {
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

    public void createProfile(UUID uuid, String name) {
        try {
            PreparedStatement st = plugin.connection.prepareStatement("INSERT INTO coins (UUID,name,coins) VALUES (?,?,?)");
            st.setString(1, String.valueOf(uuid));
            st.setString(2, name);
            st.setInt(3, 0);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProfile(Player p, UUID uuid) {
        try {
            PreparedStatement st = plugin.connection.prepareStatement("UPDATE coins SET name = ? WHERE UUID = ?");
            st.setString(1, p.getName());
            st.setString(2, String.valueOf(uuid));
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

    public static void setCoins(String uuid, int coins) {
        if (getCoins(uuid) == -1) {
            try {
                PreparedStatement st = plugin.connection.prepareStatement("UPDATE INTO coins (UUID,coins) VALUES = ?,?");
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

    public static void addCoins(String uuid, int coins) {
        int current = getCoins(uuid);
        setCoins(uuid, coins + current);

    }

    public static void removeCoins(String uuid, int coins) {
        setCoins(uuid, getCoins(uuid) - coins);
        /*
        placeholder
         */
    }

    public void sendLeaderboardMessage(Player p, int i) {
        try {
            PreparedStatement st = plugin.connection.prepareStatement("SELECT * FROM coins ORDER BY coins DESC LIMIT 10");
            ResultSet rs = st.executeQuery();

            p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Leaderboards:");
            while (rs.next()) {
                String name =  rs.getString(2);
                int coins = rs.getInt(3);
                p.sendMessage(ChatColor.BLUE + String.valueOf(++i + 0 / (i - 11)) + ChatColor.GRAY + ". "+ name + " - " + coins);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isInteger(String value) {
        if (value == null) {
            return false;
        }
        try {
            double num = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
