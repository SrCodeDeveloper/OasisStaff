package me.srcode.oasisstaff.commands;

import me.srcode.oasisstaff.helper.PluginHelper;
import me.srcode.oasisstaff.helper.StaffHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RandomPlayerCommands implements CommandExecutor {
   private final FileConfiguration configuration;

   public RandomPlayerCommands(FileConfiguration configuration) {
      this.configuration = configuration;
   }

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (sender instanceof ConsoleCommandSender) {
         return false;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("oasisstaff.command.rtp")) {
            player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.no-permission")));
            return true;
         } else {
            StaffHelper.randomTeleport(player, this.configuration);
            return true;
         }
      }
   }
}
