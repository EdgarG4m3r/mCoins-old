package vip.descent.coins.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vip.descent.coins.CoinsAPI;
import vip.descent.coins.CoinsHandler;

public class PayCommand implements CommandExecutor {

    private CoinsHandler plugin;
    private CoinsAPI api;

    public PayCommand(CoinsHandler plugin, CoinsAPI api) {
        this.api = api;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
        } else {

        }
        return false;
    }
}
