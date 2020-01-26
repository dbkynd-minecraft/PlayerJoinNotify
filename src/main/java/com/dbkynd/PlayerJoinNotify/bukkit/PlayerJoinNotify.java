package com.dbkynd.playerjoinnotify.bukkit;

import com.dbkynd.playerjoinnotify.bukkit.bstats.Metrics;
import com.dbkynd.playerjoinnotify.bukkit.listeners.PlayerLoginListener;
import com.dbkynd.playerjoinnotify.utils.EmailUtil;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerJoinNotify extends JavaPlugin {
    private Configuration config;

    @Override
    public void onEnable() {
        new Metrics(this, 6302);
        loadConfig();
        // getProxy().getPluginManager().registerCommand(this, new ReloadCommand(this))
        getServer().getPluginManager().registerEvents(new PlayerLoginListener(this), this);

        if (config.getBoolean("send-test-email-on-startup")) {
            getLogger().info("Sending email to: " + config.getString("toEmail"));
            sendMail(null, true);
        }
    }

    public void sendMail(String player, Boolean isTest) {
        getServer().getScheduler().runTaskAsynchronously(this, () -> EmailUtil.sendEmail(config, player, isTest));
    }

    public void loadConfig() {
        saveDefaultConfig();
        config = getConfig();
    }
}
