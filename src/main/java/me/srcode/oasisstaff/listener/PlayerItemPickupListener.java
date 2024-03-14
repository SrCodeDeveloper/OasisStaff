package me.srcode.oasisstaff.listener;

import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.UserModel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;

public class PlayerItemPickupListener implements Listener {
   @EventHandler
   public void onPlayerItemPuckup(PlayerAttemptPickupItemEvent itemEvent) {
      Player player = itemEvent.getPlayer();
      UserModel userModel = UserHandler.find(player);
      if (userModel != null) {
         itemEvent.setCancelled(true);
      }

   }
}
