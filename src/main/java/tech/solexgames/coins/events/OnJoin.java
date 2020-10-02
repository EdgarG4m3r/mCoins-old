package tech.solexgames.coins.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tech.solexgames.coins.utils.Utilities;
import tech.solexgames.coins.CoinsHandler;

public class OnJoin implements Listener {

    private Utilities api;
    private CoinsHandler plugin;

    public OnJoin(CoinsHandler plugin, Utilities api) {
        this.plugin = plugin;
        this.api = api;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        api.updateProfile(e.getPlayer(), e.getPlayer().getUniqueId());
        if (!(api.hasProfile(e.getPlayer()))) {
            api.createProfile(e.getPlayer().getUniqueId(), e.getPlayer().getName());
            if (plugin.getConfig().getBoolean("debug-messages")) {
                plugin.log.info("[Coins] CREATED " + e.getPlayer().getName() + "'S PROFILE!");
            }
        } else {
            // do nothing LOL

            if (!plugin.getConfig().getBoolean("debug-messages")) {
                plugin.log.info("[Coins] PLAYER " + e.getPlayer().getName() + " ALREADY HAS A PROFILE!");
            }
        }
    }
}
