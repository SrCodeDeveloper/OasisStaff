package me.srcode.oasisstaff.commands;

import me.srcode.oasisstaff.helper.PluginHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportCommands implements CommandExecutor {
   private final FileConfiguration configuration;

   public TeleportCommands(FileConfiguration configuration) {
      this.configuration = configuration;
   }

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (sender instanceof ConsoleCommandSender) {
         return false;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("oasisstaff.command.xraytp")) {
            player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.no-permission")));
            return true;
         } else {
            String targetName = args[0];
            Player targetPlayer = Bukkit.getPlayerExact(targetName);
            if (targetPlayer == null) {
               return true;
            } else {
               player.teleport(targetPlayer);
               return true;
            }
         }
      }
   }
}
