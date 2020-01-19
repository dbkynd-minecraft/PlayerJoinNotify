package com.dbkynd.PlayerConnectNotify;

import com.dbkynd.PlayerConnectNotify.Commands.ReloadCommand;
import com.dbkynd.PlayerConnectNotify.Listeners.PostLoginListener;
import com.dbkynd.PlayerConnectNotify.Utils.EmailUtil;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PlayerConnectNotify extends Plugin {

    private Configuration config;

    @Override
    public void onEnable() {
        loadConfig();
        getProxy().getPluginManager().registerCommand(this, new ReloadCommand(this));
        getProxy().getPluginManager().registerListener(this, new PostLoginListener(this));


        if (config.getBoolean("send-test-email-on-startup")) {
            getLogger().info("Sending a test email to: " + config.getString("toEmail"));
            getProxy().getScheduler().runAsync(this, () -> sendMail("TEST", true));
        }
    }

    public void sendMail(String player, Boolean isTest) {
        final String fromEmail = config.getString("fromEmail");
        final String password = config.getString("smtpPassword");
        final String toEmail = config.getString("toEmail");
        final String subject = config.getString("emailSubject");
        final String host = config.getString("smtpHost");
        String body = player + " has joined the server!";
        if (isTest) body = "This is a test message.";

        EmailUtil.sendEmail(toEmail, fromEmail, subject, body, host, password);
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
