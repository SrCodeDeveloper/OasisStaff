package me.srcode.oasisstaff.listener;

import me.srcode.oasisstaff.user.UserHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {
   @EventHandler
   public void onPlayerDamageEvent(EntityDamageEvent damageEvent) {
      Entity entity = damageEvent.getEntity();
      if (entity instanceof Player) {
         Player player = (Player)entity;
         if (UserHandler.isLoaded(player)) {
            damageEvent.setCancelled(true);
         }
      }

   }

   @EventHandler
   public void onEntityDamageByEntity(EntityDamageByEntityEvent entityEvent) {
      Entity entity = entityEvent.getEntity();
      if (entity instanceof Player) {
         Player player = (Player)entity;
         if (UserHandler.isLoaded(player)) {
            entityEvent.setCancelled(true);
         }
      }

   }
}
