package me.srcode.oasisstaff.commands;

import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.helper.PluginHelper;
import me.srcode.oasisstaff.loader.ItemLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OasisStaffCommand implements CommandExecutor {
   private final PluginLauncher pluginLauncher;

   public OasisStaffCommand(PluginLauncher pluginLauncher) {
      this.pluginLauncher = pluginLauncher;
   }

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (sender instanceof ConsoleCommandSender) {
         return true;
      } else {
         if (args[0].equalsIgnoreCase("reload")) {
            Player player = (Player)sender;
            if (!player.hasPermission("oasisstaff.command.reload")) {
               player.sendMessage(PluginHelper.toFormat(this.pluginLauncher.getConfig().getString("messages.no-permission")));
               return true;
            }

            this.pluginLauncher.reloadConfig();
            this.pluginLauncher.saveConfig();
            new ItemLoader(this.pluginLauncher.getConfig(), this.pluginLauncher.getStaffManager());
            player.sendMessage(PluginHelper.toFormat(this.pluginLauncher.getConfig().getString("messages.reload")));
         }

         return true;
      }
   }
}
