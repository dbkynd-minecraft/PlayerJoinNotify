package com.dbkynd.playerjoinnotify.bungee;

import com.dbkynd.playerjoinnotify.bungee.bstats.Metrics;
import com.dbkynd.playerjoinnotify.bungee.commands.ReloadCommand;
import com.dbkynd.playerjoinnotify.bungee.listeners.PostLoginListener;
import com.dbkynd.playerjoinnotify.utils.EmailUtil;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public final class PlayerJoinNotify extends Plugin {
    private Configuration config;

    @Override
    public void onEnable() {
        new Metrics(this, 6302);
        loadConfig();
        getProxy().getPluginManager().registerCommand(this, new ReloadCommand(this));
        getProxy().getPluginManager().registerListener(this, new PostLoginListener(this));

        if (config.getBoolean("send-test-email-on-startup")) {
            getLogger().info("Sending email to: " + config.getString("toEmail"));
            sendMail("TEST", true);
        }
    }

    public void sendMail(String player, Boolean isTest) {
        getProxy().getScheduler().runAsync(this, () -> EmailUtil.sendEmail(config, player, isTest));
    }

    public void loadConfig() {
        File file;

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        file = new File(getDataFolder(), "config.yml");

        try {
            if (!file.exists()) {
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(ConfigurationProvider.getProvider(YamlConfiguration.class).load(getResourceAsStream("config.yml")), file);
                getLogger().warning("Default config.yml generated. Please edit the fields and restart the server!");
            }
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig() {
        return config;
    }
}
