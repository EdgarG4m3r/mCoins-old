package vip.descent.coins;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import vip.descent.coins.commands.CoinsCommand;
import vip.descent.coins.events.OnJoin;

import java.sql.*;
import java.util.logging.Logger;

public class CoinsHandler extends JavaPlugin {

    private CoinsAPI coinsAPI = new CoinsAPI(this);

    public Connection connection;
    public Statement statement;
    private String host;
    private String database;
    private int port;
    private String username;
    private String password;
    public PluginManager pm = getServer().getPluginManager();
    public static Logger log = Logger.getLogger("Minecraft");
    private OnJoin onJoin = new OnJoin(this, coinsAPI);


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
        getCommand("coins").setExecutor(new CoinsCommand(this, coinsAPI));
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

        String profile = "CREATE TABLE IF NOT EXISTS coins (UUID VARCHAR(100), coins INT(16))";

        try {
            PreparedStatement statement = this.connection.prepareStatement(profile);
            statement.executeUpdate();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }
}
