package me.srcode.oasisstaff.commands;

import me.srcode.oasisstaff.helper.PluginHelper;
import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.types.StaffUserModel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatPrivateCommands implements CommandExecutor {
   private final FileConfiguration configuration;

   public ChatPrivateCommands(FileConfiguration configuration) {
      this.configuration = configuration;
   }

   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (sender instanceof ConsoleCommandSender) {
         return false;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("oasisstaff.command.chatprivate")) {
            player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.no-permission")));
            return true;
         } else {
            StaffUserModel staffUser = (StaffUserModel)UserHandler.find(player);
            if (staffUser != null && staffUser.getFreezeUser() != null) {
               if (staffUser.isPrivateChat()) {
                  staffUser.setPrivateChat(false);
                  player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.staff-private-chat-off")));
                  staffUser.getBukkitFreezePlayer().sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.player-private-chat-off")));
                  return true;
               } else {
                  staffUser.setPrivateChat(true);
                  player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.staff-private-chat-on")));
                  staffUser.getBukkitFreezePlayer().sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.player-private-chat-on")));
                  return true;
               }
            } else {
               player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.private-chat-unknown")));
               return true;
            }
         }
      }
   }
}
