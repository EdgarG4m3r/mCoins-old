package tech.solexgames.coins.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.solexgames.coins.CoinsHandler;
import tech.solexgames.coins.utils.Utilities;

public class PlayerInfoCommand implements CommandExecutor {

    /*
        GrowlyX - 10/2/2020
     */

    private Utilities api;
    private CoinsHandler plugin;

    public PlayerInfoCommand(CoinsHandler plugin, Utilities api) {
        this.api = api;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player p = (Player) commandSender;

        p.sendMessage(Utilities.translate("&7&m---------------------------------------"));
        p.sendMessage(Utilities.translate("&6&lPlayer Information"));
        p.sendMessage(Utilities.translate("&fUsername: &6" + p.getDisplayName()));
        p.sendMessage(Utilities.translate("&fPing: " + Utilities.colorPing(Utilities.getPing(p))));
        p.sendMessage(Utilities.translate("&fCoins: " + Utilities.getCoins(p.getUniqueId().toString())));
        p.sendMessage(Utilities.translate("&7&m---------------------------------------"));

        return false;
    }
}
