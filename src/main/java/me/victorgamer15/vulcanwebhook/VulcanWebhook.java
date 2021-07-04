package me.victorgamer15.vulcanwebhook;

import me.frep.vulcan.api.event.VulcanPunishEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.io.IOException;

public final class VulcanWebhook extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        FileConfiguration config = getConfig();
        config.addDefault("enable-webhook", true);
        config.addDefault("webhook-url", "Copy here the webhook link");
        config.addDefault("webhook-avatar", "https://i.imgur.com/JPG1Kwk.png");
        config.addDefault("webhook-username", "Vulcan");
        config.addDefault("embed-title", "Vulcan Punishment");
        config.addDefault("embed-subtitle", "Vulcan punished **%p%** for cheating!");
        config.addDefault("embed-color.r", "255");
        config.addDefault("embed-color.g", "0");
        config.addDefault("embed-color.b", "0");
        config.addDefault("embed-reason", "Reason");
        config.addDefault("embed-server-name", "Server Name");
        config.addDefault("embed-check-info", "Check information");
        config.addDefault("embed-check-exp", "Experimental?");
        config.addDefault("server-name", "Default");
        config.options().copyDefaults(true);
        saveConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("vulcanwebhook")) {
            if (args.length == 0) {
                player.sendMessage("§8§m--------------------------");
                player.sendMessage("§4§lVulcanWebhook");
                player.sendMessage("§7by VictorGamer15");
                player.sendMessage("§7");
                player.sendMessage("§7Usage »");
                player.sendMessage("§8§l● §r§8testwebhook");
                player.sendMessage("§8§m--------------------------");
            } else {
                if (args[0].equalsIgnoreCase("testwebhook")) {
                    if (player.hasPermission("vulcan.testwebhook")) {
                        Plugin plugin = this;
                        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                            String SERVER_NAME = getConfig().getString("server-name");
                            String WEBHOOK_URL = getConfig().getString("webhook-url");
                            String WEBHOOK_AVATAR = getConfig().getString("webhook-avatar");
                            String WEBHOOK_USERNAME = getConfig().getString("webhook-username");
                            String EMBED_TITLE = getConfig().getString("embed-title");
                            String EMBED_SUBTITLE = getConfig().getString("embed-subtitle");
                            String EMBED_COLOR_R = getConfig().getString("embed-color.r");
                            String EMBED_COLOR_G = getConfig().getString("embed-color.g");
                            String EMBED_COLOR_B = getConfig().getString("embed-color.b");
                            String EMBED_REASON = getConfig().getString("embed-reason");
                            String EMBED_SERVER_NAME = getConfig().getString("embed-server-name");
                            String EMBED_CHECK_INFO = getConfig().getString("embed-check-info");
                            String EMBED_CHECK_EXP = getConfig().getString("embed-check-exp");
                            DiscordWebhook webhook = new DiscordWebhook(WEBHOOK_URL);
                            webhook.setAvatarUrl(WEBHOOK_AVATAR);
                            webhook.setUsername(WEBHOOK_USERNAME);
                            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                    .setTitle(EMBED_TITLE)
                                    .setDescription(EMBED_SUBTITLE.replace("%p%", player.getName()))
                                    .setThumbnail("http://cravatar.eu/avatar/" + player.getName() + "/64.png")
                                    .setColor(new Color(Integer.parseInt(EMBED_COLOR_R), Integer.parseInt(EMBED_COLOR_G), Integer.parseInt(EMBED_COLOR_B)))
                                    .addField(EMBED_REASON, "Webhook test", true)
                                    .addField(EMBED_SERVER_NAME, SERVER_NAME, true)
                                    .addField("⠀", "⠀", true)
                                    .addField(EMBED_CHECK_INFO, "Webhook command test", true)
                                    .addField(EMBED_CHECK_EXP, "false", true));
                            player.sendMessage("§4§lVulcan §8» §eSending request...");
                            try {
                                webhook.execute();
                                player.sendMessage("§4§lVulcan §8» §aWebhook sended!");
                            } catch (IOException e) {
                                e.printStackTrace();
                                player.sendMessage("§4§lVulcan §8» §cThere was an error trying to send the request! You can find more information in the server console.");
                            }
                        });
                    } else {
                        player.sendMessage("§cYou don't have permission to execute this command!");
                    }
                }
            }
        }
        return true;
    }

    @EventHandler
    public void onPunish(VulcanPunishEvent e) {
        Plugin plugin = this;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Player player = e.getPlayer();
            String SERVER_NAME = getConfig().getString("server-name");
            String WEBHOOK_URL = getConfig().getString("webhook-url");
            String WEBHOOK_AVATAR = getConfig().getString("webhook-avatar");
            String WEBHOOK_USERNAME = getConfig().getString("webhook-username");
            String EMBED_TITLE = getConfig().getString("embed-title");
            String EMBED_SUBTITLE = getConfig().getString("embed-subtitle");
            String EMBED_COLOR_R = getConfig().getString("embed-color.r");
            String EMBED_COLOR_G = getConfig().getString("embed-color.g");
            String EMBED_COLOR_B = getConfig().getString("embed-color.b");
            String EMBED_REASON = getConfig().getString("embed-reason");
            String EMBED_SERVER_NAME = getConfig().getString("embed-server-name");
            String EMBED_CHECK_INFO = getConfig().getString("embed-check-info");
            String EMBED_CHECK_EXP = getConfig().getString("embed-check-exp");
            if (getConfig().get("enable-webhook").equals("false")) return;
            DiscordWebhook webhook = new DiscordWebhook(WEBHOOK_URL);
            webhook.setAvatarUrl(WEBHOOK_AVATAR);
            webhook.setUsername(WEBHOOK_USERNAME);
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setTitle(EMBED_TITLE)
                    .setDescription(EMBED_SUBTITLE.replace("%p%", player.getName()))
                    .setThumbnail("http://cravatar.eu/avatar/" + player.getName() + "/64.png")
                    .setColor(new Color(Integer.parseInt(EMBED_COLOR_R), Integer.parseInt(EMBED_COLOR_G), Integer.parseInt(EMBED_COLOR_B)))
                    .addField(EMBED_REASON, e.getCheck().getDisplayName() + " " + Character.toString(e.getCheck().getType()).toUpperCase(), true)
                    .addField(EMBED_SERVER_NAME, SERVER_NAME, true)
                    .addField("⠀", "⠀", true)
                    .addField(EMBED_CHECK_INFO, e.getCheck().getDescription(), true)
                    .addField(EMBED_CHECK_EXP, Boolean.toString(e.getCheck().isExperimental()), true));
            try {
                webhook.execute();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
