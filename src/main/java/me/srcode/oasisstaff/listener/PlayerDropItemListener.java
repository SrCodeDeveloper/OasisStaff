package me.srcode.oasisstaff.listener;

import me.srcode.oasisstaff.user.UserHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {
   @EventHandler
   public void onPlayerItemDropEvent(PlayerDropItemEvent playerDropItemEvent) {
      Player player = playerDropItemEvent.getPlayer();
      if (UserHandler.isLoaded(player)) {
         playerDropItemEvent.setCancelled(true);
      }

   }
}
