package me.srcode.oasisstaff.listener;

import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.UserModel;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener implements Listener {
   @EventHandler
   public void onEntityDamage(EntityDamageByEntityEvent entityEvent) {
      Entity entity = entityEvent.getDamager();
      if (entity instanceof Player) {
         Player player = (Player)entity;
         UserModel userModel = UserHandler.find(player);
         if (userModel == null) {
            return;
         }

         entityEvent.setCancelled(true);
      }

   }
}
