package tech.solexgames.coins.utils;

import com.broustudio.MizuAPI.MizuAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tech.solexgames.coins.CoinsHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utilities {

    private static CoinsHandler plugin;

    public Utilities(CoinsHandler plugin) {
        this.plugin = plugin;
    }

    private static final String CRAFT_BUKKIT_PACKAGE;
    private static final String NET_MINECRAFT_SERVER_PACKAGE;

    private static final Class CRAFT_SERVER_CLASS;
    private static final Method CRAFT_SERVER_GET_HANDLE_METHOD;

    private static final Class ENTITY_PLAYER_CLASS;
    private static final Field ENTITY_PLAYER_PING_FIELD;

    private static final Class CRAFT_PLAYER_CLASS;
    private static final Method CRAFT_PLAYER_GET_HANDLE_METHOD;


    static {
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

            CRAFT_BUKKIT_PACKAGE = "org.bukkit.craftbukkit." + version + ".";
            NET_MINECRAFT_SERVER_PACKAGE = "net.minecraft.server." + version + ".";

            CRAFT_SERVER_CLASS = Class.forName(CRAFT_BUKKIT_PACKAGE + "CraftServer");
            CRAFT_SERVER_GET_HANDLE_METHOD = CRAFT_SERVER_CLASS.getDeclaredMethod("getHandle");
            CRAFT_SERVER_GET_HANDLE_METHOD.setAccessible(true);

            CRAFT_PLAYER_CLASS = Class.forName(CRAFT_BUKKIT_PACKAGE + "entity.CraftPlayer");
            CRAFT_PLAYER_GET_HANDLE_METHOD = CRAFT_PLAYER_CLASS.getDeclaredMethod("getHandle");
            CRAFT_PLAYER_GET_HANDLE_METHOD.setAccessible(true);

            ENTITY_PLAYER_CLASS = Class.forName(NET_MINECRAFT_SERVER_PACKAGE + "EntityPlayer");
            ENTITY_PLAYER_PING_FIELD = ENTITY_PLAYER_CLASS.getDeclaredField("ping");
            ENTITY_PLAYER_PING_FIELD.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("Failed to initialize NMS");
        }
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

            p.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "------------------------------------");
            p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Coins Leaderboards:");
            while (rs.next()) {
                String name =  rs.getString(2);
                int coins = rs.getInt(3);
                p.sendMessage(ChatColor.WHITE + "#" + String.valueOf(++i + 0 / (i - 11)) + ChatColor.GRAY + " - "+ ChatColor.GOLD + name + ChatColor.GRAY + " - " + ChatColor.WHITE + coins);
            }
            p.sendMessage(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "------------------------------------");
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

    public static int getPing(Player player) {
        try {
            int ping = ENTITY_PLAYER_PING_FIELD.getInt(CRAFT_PLAYER_GET_HANDLE_METHOD.invoke(player));

            return ping > 0 ? ping : 0;
        } catch (Exception e) {
            return 1;
        }
    }

    public static String colorPing(int ping) {
        if (ping <= 40) {
            return Utilities.translate("&a" + ping);
        } else if (ping <= 100) {
            return Utilities.translate("&e" + ping);
        } else if (ping <= 120) {
            return Utilities.translate("&6" + ping);
        } else {
            return Utilities.translate("&4" + ping);
        }
    }

    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> translate(List<String> text) {
        return text.stream().map(Utilities::translate).collect(Collectors.toList());
    }

}
