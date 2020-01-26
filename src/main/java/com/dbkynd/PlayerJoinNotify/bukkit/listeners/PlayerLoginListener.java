package com.dbkynd.playerjoinnotify.bukkit.listeners;

import com.dbkynd.playerjoinnotify.bukkit.PlayerJoinNotify;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;

public class PlayerLoginListener implements Listener {
    final PlayerJoinNotify plugin;

    public PlayerLoginListener(PlayerJoinNotify plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String uuid = event.getPlayer().getUniqueId().toString().replaceAll("-", "");
        String name = event.getPlayer().getName();
        boolean inverted = plugin.getConfig().getBoolean("inverted-mode");
        List<String> players = plugin.getConfig().getStringList("players");

        for (String item : players) {
            if (!inverted) {
                // Whitelist mode
                if (item.equalsIgnoreCase(name) || item.replace("-", "").equals(uuid)) {
                    plugin.sendMail(name, false);
                    return;
                }
                return;
            } else {
                // Blacklist mode
                if (item.equalsIgnoreCase(name)) return;
                if (item.replace("-", "").equals(uuid)) return;
            }
        }
        plugin.sendMail(name, false);
    }
}
