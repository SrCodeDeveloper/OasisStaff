package me.srcode.oasisstaff.listener;

import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.types.FreezeUserModel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerItemConsumeListener implements Listener {
   @EventHandler
   public void onPlayerItemConsume(PlayerItemConsumeEvent consumeEvent) {
      Player player = consumeEvent.getPlayer();
      if (UserHandler.find(player) instanceof FreezeUserModel) {
         consumeEvent.setCancelled(true);
      }

   }
}
