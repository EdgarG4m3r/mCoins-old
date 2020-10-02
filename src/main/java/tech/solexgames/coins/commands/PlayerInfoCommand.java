package tech.solexgames.coins.commands;

import com.broustudio.MizuAPI.MizuAPI;
import org.bukkit.Bukkit;
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

        if (Bukkit.getPluginManager().isPluginEnabled("Mizu") || Bukkit.getPluginManager().isPluginEnabled("MizuAPI")) {
            p.sendMessage(Utilities.translate("&fRank: " + MizuAPI.getAPI().getRankColor(MizuAPI.getAPI().getRank(p.getUniqueId())) + MizuAPI.getAPI().getRank(p.getUniqueId())));
            p.sendMessage(Utilities.translate("&fTag: " + MizuAPI.getAPI().getTagDisplay(MizuAPI.getAPI().getTag(p.getUniqueId()))));
        }

        p.sendMessage(Utilities.translate("&7&m---------------------------------------"));

        return false;
    }
}
