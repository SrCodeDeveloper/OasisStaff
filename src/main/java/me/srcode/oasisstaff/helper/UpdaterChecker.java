package me.srcode.oasisstaff.helper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdaterChecker {

    private final JavaPlugin plugin;
    private final int resourceId;

    public UpdaterChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://www.spigotmc.org/resources/cmi-298-commands-insane-kits-portals-essentials-economy-mysql-sqlite-much-more.3742/" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                plugin.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aA new version has been found: ") + exception.getMessage());
            }
        });
    }
}