package me.srcode.oasisstaff.listener;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.types.FreezeUserModel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJumpListener implements Listener {
   @EventHandler
   public void onPlayerJump(PlayerJumpEvent jumpEvent) {
      Player player = jumpEvent.getPlayer();
      if (UserHandler.find(player) instanceof FreezeUserModel) {
         jumpEvent.setCancelled(true);
      }

   }
}
