package com.dbkynd.playerjoinnotify.bungee.listeners;

import com.dbkynd.playerjoinnotify.bungee.PlayerJoinNotify;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class PostLoginListener implements Listener {

    final PlayerJoinNotify plugin;

    public PostLoginListener(PlayerJoinNotify plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
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
