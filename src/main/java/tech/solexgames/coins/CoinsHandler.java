package tech.solexgames.coins;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tech.solexgames.coins.commands.CoinsCommand;
import tech.solexgames.coins.commands.PayCommand;
import tech.solexgames.coins.events.OnJoin;
import tech.solexgames.coins.utils.Utilities;

import java.sql.*;
import java.util.logging.Logger;

public class CoinsHandler extends JavaPlugin {

    private Utilities utilities = new Utilities(this);

    public Connection connection;
    public Statement statement;
    private String host;
    private String database;
    private int port;
    private String username;
    private String password;
    public PluginManager pm = getServer().getPluginManager();
    public static Logger log = Logger.getLogger("Minecraft");

    private OnJoin onJoin = new OnJoin(this, utilities);


    @Override
    public void onEnable() {

        this.host = getConfig().getString("mysql.host");
        this.port = getConfig().getInt("mysql.port");
        this.database = getConfig().getString("mysql.database");
        this.username = getConfig().getString("mysql.username");
        this.password = getConfig().getString("mysql.password");

        try {
            this.openConnection();
            this.setupDatabase();
        } catch (Exception sql) {
            sql.printStackTrace();
        }

        log.info("[Coins] Initialized the database successfully!");

        saveDefaultConfig();

        getCommand("coins").setExecutor(new CoinsCommand(this, utilities));
        getCommand("pay").setExecutor(new PayCommand(this, utilities));

        pm.registerEvents(onJoin, this);
        log.info("[Coins] Registered the command!");

    }

    @Override
    public void onDisable() {

    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        if(this.connection == null || this.connection.isClosed()) {
            synchronized(this) {
                if(this.connection == null || this.connection.isClosed()) {
                    Class.forName("com.mysql.jdbc.Driver");
                    this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
                }
            }
        }
    }

    public void setupDatabase() {

        String profile = "CREATE TABLE IF NOT EXISTS coins (UUID VARCHAR(100), name VARCHAR(64), coins INT(16))";
        String makeunique = "ALTER TABLE `coins` ADD UNIQUE(`UUID`)";

        try {
            PreparedStatement st1 = this.connection.prepareStatement(profile);
            st1.executeUpdate();
            PreparedStatement st2 = this.connection.prepareStatement(makeunique);
            st2.executeUpdate();
        } catch (SQLException sql) {
            return;
        }
    }
}
