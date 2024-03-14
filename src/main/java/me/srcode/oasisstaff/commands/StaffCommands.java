package me.srcode.oasisstaff.commands;

import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.helper.PluginHelper;
import me.srcode.oasisstaff.manager.StaffManager;
import me.srcode.oasisstaff.user.UserHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffCommands implements CommandExecutor {
   private final StaffManager manager;
   private final FileConfiguration configuration;

   public StaffCommands(@NotNull PluginLauncher pluginLauncher) {
      this.manager = pluginLauncher.getStaffManager();
      this.configuration = pluginLauncher.getConfig();
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (sender instanceof ConsoleCommandSender) {
         return false;
      } else if (!sender.hasPermission("oasisstaff.command.staffmode")) {
         sender.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.no-permission")));
         return true;
      } else {
         Player player = (Player)sender;
         if (UserHandler.isLoaded(player)) {
            sender.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.staffmode-quit")));
            this.manager.unjoin(player);
            return true;
         } else {
            sender.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.staffmode-join")));
            this.manager.join(player);
            return true;
         }
      }
   }
}
