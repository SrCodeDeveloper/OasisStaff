package me.srcode.oasisstaff.commands;

import java.util.Iterator;
import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.helper.PluginHelper;
import me.srcode.oasisstaff.user.UserHandler;
import org.bukkit.Bukkit;
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

public class VanishCommands implements CommandExecutor {
   private final PluginLauncher pluginLauncher;
   private final FileConfiguration configuration;

   public VanishCommands(PluginLauncher pluginLauncher) {
      this.pluginLauncher = pluginLauncher;
      this.configuration = pluginLauncher.getConfig();
   }

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (sender instanceof ConsoleCommandSender) {
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("oasisstaff.command.vanish")) {
            player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.no-permission")));
            return true;
         } else if (UserHandler.isLoaded(player)) {
            player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.vanish-instaff")));
            return true;
         } else {
            PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
            NamespacedKey vanishKey = new NamespacedKey(this.pluginLauncher, "vanish");
            Iterator var8;
            Player target;
            if (persistentDataContainer.has(vanishKey)) {
               persistentDataContainer.remove(vanishKey);
               var8 = Bukkit.getOnlinePlayers().iterator();

               while(var8.hasNext()) {
                  target = (Player)var8.next();
                  target.showPlayer(this.pluginLauncher, player);
               }

               player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.vanish-off")));
               return true;
            } else {
               var8 = Bukkit.getOnlinePlayers().iterator();

               while(var8.hasNext()) {
                  target = (Player)var8.next();
                  target.hidePlayer(this.pluginLauncher, player);
               }

               player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.vanish-on")));
               persistentDataContainer.set(vanishKey, PersistentDataType.STRING, "vanish");
               return true;
            }
         }
      }
   }
}
