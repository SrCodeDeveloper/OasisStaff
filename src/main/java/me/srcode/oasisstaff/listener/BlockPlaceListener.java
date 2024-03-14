package me.srcode.oasisstaff.listener;

import me.srcode.oasisstaff.user.UserHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
   @EventHandler
   public void onBlockPlace(BlockPlaceEvent placeEvent) {
      if (UserHandler.isLoaded(placeEvent.getPlayer())) {
         placeEvent.setCancelled(true);
      }

   }
}
