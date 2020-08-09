package vip.descent.coins;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CoinsCommand implements CommandExecutor {

    private CoinsAPI api;
    private CoinsHandler plugin;

    public CoinsCommand(CoinsHandler plugin, CoinsAPI api) {
        this.api = api;
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("coins") || cmd.getName().equalsIgnoreCase("money") || cmd.getName().equalsIgnoreCase("cash") ) {

            if (!(sender instanceof Player)) {
                if (args.length == 0) {
                    sender.sendMessage("------------------------------");
                    sender.sendMessage("Coins Help:");
                    sender.sendMessage("/coins <player>");
                    sender.sendMessage("/coins set <player> <amount>");
                    sender.sendMessage("/coins add <player> <amount>");
                    sender.sendMessage("/coins remove <player> <amount>");
                    sender.sendMessage("------------------------------");
                } else if (args[0].equalsIgnoreCase("set")) {
                    if (args.length <= 1) {
                        sender.sendMessage("Usage: /coins set <player> <amount>");
                    } else {
                        String target = Bukkit.getPlayer(args[1]).getUniqueId().toString();
                        int coin = Integer.parseInt(args[2]);

                        api.setCoins(target, coin);
                        sender.sendMessage("Successfully set " + args[1] + " coins to" + coin + "!");
                    }
                } else if (args[0].equalsIgnoreCase("add")) {
                    if (args.length <= 1) {
                        sender.sendMessage("Usage: /coins add <player> <amount>");
                    } else {
                        String target = Bukkit.getPlayer(args[1]).getUniqueId().toString();
                        int coin = Integer.parseInt(args[2]);

                        api.addCoins(target, coin);
                        sender.sendMessage("Successfully add " + args[1] + " coins to" + coin + "!");
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (args.length <= 1) {
                        sender.sendMessage("Usage: /coins remove <player> <amount>");
                    } else {
                        String target = Bukkit.getPlayer(args[1]).getUniqueId().toString();
                        int coin = Integer.parseInt(args[2]);

                        if (api.getCoins(target) == 0) {
                            sender.sendMessage("Player does not have any coins left to remove!");
                        } else {
                            api.removeCoins(target, coin);
                            sender.sendMessage("Successfully remove " + coin + " from " + args[2]);
                        }
                    }
                }
            }

            Player p;

            p = (Player) sender;

            if (p.hasPermission("descentcore.coins.manage")) {
                if (args.length == 0) {
                    sender.sendMessage("§7§m------------------------------");
                    sender.sendMessage("§9§lCoins Help:");
                    sender.sendMessage("/coins <player>");
                    sender.sendMessage("/coins set <player> <amount>");
                    sender.sendMessage("/coins add <player> <amount>");
                    sender.sendMessage("/coins remove <player> <amount>");
                    sender.sendMessage("§7§m------------------------------");
                } else if (args[0].equalsIgnoreCase("set")) {
                    if (args.length <= 1) {
                        sender.sendMessage("§cUsage: /coins set <player> <amount>");
                    } else {
                        String target = Bukkit.getPlayer(args[1]).getUniqueId().toString();
                        int coin = Integer.parseInt(args[2]);

                        api.setCoins(target, coin);
                        sender.sendMessage("§aSuccessfully set " + args[1] + " coins to" + coin + "!");
                    }
                } else if (args[0].equalsIgnoreCase("add")) {
                    if (args.length <= 1) {
                        sender.sendMessage("§cUsage: /coins add <player> <amount>");
                    } else {
                        String target = Bukkit.getPlayer(args[1]).getUniqueId().toString();
                        int coin = Integer.parseInt(args[2]);

                        api.addCoins(target, coin);
                        sender.sendMessage("§aSuccessfully add " + args[1] + " coins to" + coin + "!");
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (args.length <= 1) {
                        sender.sendMessage("§cUsage: /coins remove <player> <amount>");
                    } else {
                        String target = Bukkit.getPlayer(args[1]).getUniqueId().toString();
                        int coin = Integer.parseInt(args[2]);

                        if (api.getCoins(target) == 0) {
                            sender.sendMessage("§cPlayer does not have any coins left to remove!");
                        } else {
                            api.removeCoins(target, coin);
                            sender.sendMessage("§aSuccessfully remove " + coin + " from " + args[2] + "!");
                        }
                    }
                }
            } else {
                sender.sendMessage("§9Coins: §7" + api.getCoins(p.getUniqueId().toString()));
            }
        }

        return false;
    }
}
