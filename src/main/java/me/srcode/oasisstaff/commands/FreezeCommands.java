package me.srcode.oasisstaff.commands;

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

public class FreezeCommands implements CommandExecutor {
   private final PluginLauncher pluginLauncher;
   private final FileConfiguration configuration;

   public FreezeCommands(PluginLauncher pluginLauncher) {
      this.pluginLauncher = pluginLauncher;
      this.configuration = pluginLauncher.getConfig();
   }

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (sender instanceof ConsoleCommandSender) {
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("oasisstaff.command.freeze")) {
            player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.no-permission")));
            return true;
         } else if (args.length == 0) {
            player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.freeze-invalid")));
            return true;
         } else {
            String targetName = args[0];
            Player targetPlayer = Bukkit.getPlayerExact(targetName);
            if (targetPlayer == null) {
               player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.freeze-notfound")));
               return true;
            } else if (targetName.equals(player.getName())) {
               player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.freeze-me")));
               return true;
            } else {
               this.pluginLauncher.getStaffManager().freeze(player, targetPlayer);
               return true;
            }
         }
      }
   }
}
