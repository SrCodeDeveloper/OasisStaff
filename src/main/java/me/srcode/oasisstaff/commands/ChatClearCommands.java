package me.srcode.oasisstaff.commands;

import java.util.Iterator;
import me.srcode.oasisstaff.helper.PluginHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatClearCommands implements CommandExecutor {
   private final FileConfiguration configuration;

   public ChatClearCommands(@NotNull FileConfiguration configuration) {
      this.configuration = configuration;
   }

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (sender instanceof ConsoleCommandSender) {
         return false;
      } else {
         Player executor = (Player)sender;
         if (!executor.hasPermission("oasisstaff.command.chatclear")) {
            executor.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.no-permission")));
            return true;
         } else {
            String playersMessage = PluginHelper.toFormat(this.configuration.getString("messages.chatclear-players").replace("%player%", executor.getName()));
            Iterator var7 = Bukkit.getOnlinePlayers().iterator();

            while(var7.hasNext()) {
               Player player = (Player)var7.next();
               player.sendMessage(new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""});
               player.sendMessage(playersMessage);
            }

            executor.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.chatclear-executor")));
            return true;
         }
      }
   }
}
