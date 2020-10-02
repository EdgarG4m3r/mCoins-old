package tech.solexgames.coins.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.solexgames.coins.utils.Utilities;
import tech.solexgames.coins.CoinsHandler;

public class PayCommand implements CommandExecutor {

    private CoinsHandler plugin;
    private Utilities api;

    public PayCommand(CoinsHandler plugin, Utilities api) {
        this.api = api;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pay") || cmd.getName().equalsIgnoreCase("transfer") || cmd.getName().equalsIgnoreCase("send")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command!");
            } else {

                Player p;

                p = (Player) sender;

                if (args.length == 0) {
                    p.sendMessage(ChatColor.RED + "Usage: /pay <player> <amount>");
                    return true;
                }

                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    p.sendMessage(ChatColor.RED + args[0] + " is offline!");
                    return true;
                } else if (target == p) {
                    p.sendMessage(ChatColor.RED + "You cannot pay yourself money!");
                    return false;
                } else {
                    api.removeCoins(p.getUniqueId().toString(), Integer.parseInt(args[1]));
                    p.sendMessage(ChatColor.GREEN + "Successfully sent " + args[1] + " coins to " + target.getName() + "!");

                    api.addCoins(target.getUniqueId().toString(), Integer.parseInt(args[1]));
                    target.sendMessage(ChatColor.GREEN + p.getName() + " has sent you " + args[1] + " coins!");
                    return false;
                }
            }
        }
        return false;
    }
}
