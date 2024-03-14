package me.srcode.oasisstaff.listener;

import me.srcode.oasisstaff.user.UserHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {
   @EventHandler
   public void onFoodLevelChange(FoodLevelChangeEvent changeEvent) {
      Player player = (Player)changeEvent.getEntity();
      if (UserHandler.isLoaded(player)) {
         changeEvent.setCancelled(true);
      }

   }
}
