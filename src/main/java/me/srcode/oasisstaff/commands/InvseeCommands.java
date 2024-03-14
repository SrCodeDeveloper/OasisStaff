package me.srcode.oasisstaff.commands;

import me.srcode.oasisstaff.inventories.InventoryPreview;
import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.helper.PluginHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InvseeCommands implements CommandExecutor {
   private final PluginLauncher pluginLauncher;

   public InvseeCommands(PluginLauncher pluginLauncher) {
      this.pluginLauncher = pluginLauncher;
   }

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (sender instanceof ConsoleCommandSender) {
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("oasisstaff.command.invsee")) {
            player.sendMessage(PluginHelper.toFormat(this.pluginLauncher.getConfig().getString("messages.no-permission")));
            return true;
         } else {
            String argument = args[0];
            if (argument.isEmpty()) {
               player.sendMessage(PluginHelper.toFormat(this.pluginLauncher.getConfig().getString("messages.inventory-see-invalid")));
               return true;
            } else {
               Player targetPlayer = Bukkit.getPlayerExact(argument);
               if (targetPlayer == null) {
                  player.sendMessage(PluginHelper.toFormat(this.pluginLauncher.getConfig().getString("messages.inventory-see-notfound")));
                  return true;
               } else {
                  player.openInventory((new InventoryPreview(this.pluginLauncher, targetPlayer)).getInventory());
                  return true;
               }
            }
         }
      }
   }
}
