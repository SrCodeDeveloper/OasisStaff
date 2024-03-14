package me.srcode.oasisstaff.commands;

import java.util.Iterator;
import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.helper.PluginHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatToggleCommands implements CommandExecutor {
   private final FileConfiguration configuration;

   public ChatToggleCommands(FileConfiguration configuration) {
      this.configuration = configuration;
   }

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (sender instanceof ConsoleCommandSender) {
         return true;
      } else {
         Player executor = (Player)sender;
         if (!executor.hasPermission("oasisstaff.command.chattoggle")) {
            executor.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.no-permission")));
            return true;
         } else {
            String message;
            if (PluginLauncher.isChatEnabled()) {
               PluginLauncher.setChatEnabled(false);
               message = PluginHelper.toFormat(this.configuration.getString("messages.chattoggle-players-off").replace("%player%", executor.getName()));
               this.sendMessage(message);
               executor.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.chattoggle-executor-off")));
               return true;
            } else {
               PluginLauncher.setChatEnabled(true);
               message = PluginHelper.toFormat(this.configuration.getString("messages.chattoggle-players-on").replace("%player%", executor.getName()));
               this.sendMessage(message);
               executor.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.chattoggle-executor-on")));
               return true;
            }
         }
      }
   }

   private void sendMessage(String message) {
      Iterator var2 = Bukkit.getOnlinePlayers().iterator();

      while(var2.hasNext()) {
         Player player = (Player)var2.next();
         player.sendMessage(message);
      }

   }
}
