package vip.descent.coins.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import vip.descent.coins.CoinsAPI;
import vip.descent.coins.CoinsHandler;

public class OnJoin implements Listener {

    private CoinsAPI api;
    private CoinsHandler plugin;

    public OnJoin(CoinsHandler plugin, CoinsAPI api) {
        this.plugin = plugin;
        this.api = api;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!(api.hasProfile(e.getPlayer()))) {
            api.createProfile(e.getPlayer().getUniqueId());

            if (plugin.getConfig().getBoolean("debug-messages")) {
                plugin.log.info("[Coins] CREATED " + e.getPlayer().getName() + "'S PROFILE!");
            }
        } else {
            // do nothing LOL

            if (!plugin.getConfig().getBoolean("debug-messages")) {
                plugin.log.info("[Coins] PLAYER " + e.getPlayer().getName() + " ALREADY HAVE A PROFILE");
            }
        }
    }
}
