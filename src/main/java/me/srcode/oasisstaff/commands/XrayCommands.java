package me.srcode.oasisstaff.commands;

import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.helper.PluginHelper;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class XrayCommands implements CommandExecutor {
   private final PluginLauncher pluginLauncher;
   private final FileConfiguration configuration;

   public XrayCommands(PluginLauncher pluginLauncher) {
      this.pluginLauncher = pluginLauncher;
      this.configuration = pluginLauncher.getConfig();
   }

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (sender instanceof ConsoleCommandSender) {
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("oasisstaff.command.xraytoggle")) {
            player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.no-permission")));
            return true;
         } else if (args.length == 0) {
            player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.xray-invalid")));
            return true;
         } else if (args[0].equalsIgnoreCase("toggle")) {
            NamespacedKey key = new NamespacedKey(this.pluginLauncher, "xray");
            PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
            if (persistentDataContainer.has(key)) {
               persistentDataContainer.remove(key);
               player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.xray-on")));
               return true;
            } else {
               persistentDataContainer.set(key, PersistentDataType.STRING, "xray");
               player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.xray-off")));
               return true;
            }
         } else {
            return true;
         }
      }
   }
}
