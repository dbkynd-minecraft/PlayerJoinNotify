package com.dbkynd.PlayerConnectNotify.Listeners;

import com.dbkynd.PlayerConnectNotify.PlayerConnectNotify;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginListener implements Listener {

    PlayerConnectNotify plugin;

    public PostLoginListener(PlayerConnectNotify plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        String uuid = event.getPlayer().getUniqueId().toString().replaceAll("-", "");
        String name = event.getPlayer().getName();

        for (String item : plugin.getConfig().getStringList("players")) {
            if (item.equalsIgnoreCase(name)) {
                plugin.getProxy().getScheduler().runAsync(plugin, () -> plugin.sendMail(name, false));
                break;
            }
            if (item.replace("-", "").equals(uuid)) {
                plugin.getProxy().getScheduler().runAsync(plugin, () -> plugin.sendMail(name, false));
                break;
            }
        }
    }
}
