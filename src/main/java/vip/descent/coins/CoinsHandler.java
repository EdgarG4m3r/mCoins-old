package vip.descent.coins;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.logging.Logger;

public class CoinsHandler extends JavaPlugin {

    private CoinsAPI coinsAPI;

    public Connection connection;
    public Statement statement;
    private String host;
    private String database;
    private int port;
    private String username;
    private String password;
    public static Logger log = Logger.getLogger("Minecraft");


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

        log.info("Initialized the database successfully!");

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
