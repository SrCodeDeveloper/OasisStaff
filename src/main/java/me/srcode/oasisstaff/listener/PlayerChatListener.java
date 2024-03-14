package me.srcode.oasisstaff.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.helper.PluginHelper;
import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.types.FreezeUserModel;
import me.srcode.oasisstaff.user.types.StaffUserModel;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerChatListener implements Listener {
   private final FileConfiguration configuration;

   public PlayerChatListener(FileConfiguration configuration) {
      this.configuration = configuration;
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onPlayerAsyncChat(AsyncChatEvent chatEvent) {
      Player player = chatEvent.getPlayer();
      if (!PluginLauncher.isChatEnabled() && !player.hasPermission("kingdomstaff.chattoggle.bypass")) {
         chatEvent.setCancelled(true);
         player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.chat-disabled")));
      } else if (UserHandler.isLoaded(player)) {
         if (UserHandler.find(player) instanceof StaffUserModel) {
            StaffUserModel staffUser = (StaffUserModel)UserHandler.find(player);
            if (staffUser != null && staffUser.isPrivateChat()) {
               String result = this.configuration.getString("messages.private-chat-format").replace("<sender>", player.getName()).replace("<receiver>", staffUser.getBukkitFreezePlayer().getName()).replace("<message>", LegacyComponentSerializer.legacySection().serialize(chatEvent.message()));
               String resultFormat = PluginHelper.toFormat(result);
               staffUser.getBukkitFreezePlayer().sendMessage(resultFormat);
               player.sendMessage(resultFormat);
               chatEvent.setCancelled(true);
            }
         } else {
            FreezeUserModel freezeUser = (FreezeUserModel)UserHandler.find(player);
            Player staffPlayer = freezeUser.getBukkitStaffPlayer();
            StaffUserModel staffUser = (StaffUserModel)UserHandler.find(staffPlayer);
            if (staffUser != null && staffUser.isPrivateChat()) {
               String result = this.configuration.getString("messages.private-chat-format").replace("<sender>", player.getName()).replace("<receiver>", staffPlayer.getName()).replace("<message>", LegacyComponentSerializer.legacySection().serialize(chatEvent.message()));
               String resultFormat = PluginHelper.toFormat(result);
               staffPlayer.sendMessage(resultFormat);
               player.sendMessage(resultFormat);
               chatEvent.setCancelled(true);
            }
         }
      }

   }
}
