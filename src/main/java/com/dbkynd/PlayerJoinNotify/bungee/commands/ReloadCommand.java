package com.dbkynd.playerjoinnotify.bungee.commands;

import com.dbkynd.playerjoinnotify.bungee.PlayerJoinNotify;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {

    final PlayerJoinNotify plugin;

    public ReloadCommand(PlayerJoinNotify plugin) {
        super("pjnotify", "pjnotify.reload");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) return;
        if (!args[0].toLowerCase().equals("reload")) return;
        try {
            plugin.loadConfig();
            sender.sendMessage(new TextComponent("[" + ChatColor.GOLD + "PlayerJoinNotify" + ChatColor.RESET + "]: The config.yml file has been reloaded."));
        } catch (Exception e) {
            sender.sendMessage(new TextComponent("[" + ChatColor.GOLD + "PlayerJoinNotify" + ChatColor.RESET + "]: " + ChatColor.RED + "There was an error reloading the config.yml file."));
        }
    }
}
