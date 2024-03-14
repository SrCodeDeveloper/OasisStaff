package me.srcode.oasisstaff.listener;

import java.util.Iterator;
import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.manager.StaffManager;
import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.UserModel;
import me.srcode.oasisstaff.user.types.FreezeUserModel;
import me.srcode.oasisstaff.user.types.StaffUserModel;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;

public class PlayerQuitListener implements Listener {
   private final PluginLauncher pluginLauncher;
   private final StaffManager staffManager;

   public PlayerQuitListener(PluginLauncher pluginLauncher) {
      this.pluginLauncher = pluginLauncher;
      this.staffManager = pluginLauncher.getStaffManager();
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent quitEvent) {
      Player player = quitEvent.getPlayer();
      NamespacedKey key = new NamespacedKey(this.pluginLauncher, "vanish");
      PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
      if (persistentDataContainer.has(key)) {
         persistentDataContainer.remove(key);
      }

      UserModel userModel = UserHandler.find(player);
      if (userModel != null) {
         if (userModel instanceof StaffUserModel) {
            Bukkit.getLogger().info("StaffUserModel");
            Player bukkitFreezePlayer = ((StaffUserModel)userModel).getBukkitFreezePlayer();
            if (bukkitFreezePlayer != null) {
               FreezeUserModel freezeUser = (FreezeUserModel)UserHandler.find(bukkitFreezePlayer);
               freezeUser.unfreeze();
            }

            this.staffManager.unjoin(player);
         } else {
            Bukkit.getLogger().info("FreezeStaffModel");
            FreezeUserModel freezeUser = (FreezeUserModel)userModel;
            freezeUser.unfreeze();
            UserHandler.remove(player);
            Iterator var7 = this.pluginLauncher.getConfig().getStringList("executed_commands.commands").iterator();

            while(var7.hasNext()) {
               String string = (String)var7.next();
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", player.getName()));
            }
         }
      }

   }
}
