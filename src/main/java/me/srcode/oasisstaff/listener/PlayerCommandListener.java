package me.srcode.oasisstaff.listener;

import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.UserModel;
import me.srcode.oasisstaff.user.types.StaffUserModel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandListener implements Listener {
   @EventHandler
   public void onPlayerCommand(PlayerCommandPreprocessEvent preprocessEvent) {
      Player player = preprocessEvent.getPlayer();
      UserModel userModel = UserHandler.find(player);
      if (userModel != null && !(userModel instanceof StaffUserModel)) {
         preprocessEvent.setCancelled(true);
      }

   }
}
