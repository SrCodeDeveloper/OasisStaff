package me.srcode.oasisstaff.listener;

import me.srcode.oasisstaff.builder.ItemAction;
import me.srcode.oasisstaff.manager.ItemManager;
import me.srcode.oasisstaff.manager.StaffManager;
import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.UserModel;
import me.srcode.oasisstaff.user.types.StaffUserModel;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerInteractListener implements Listener {
   private final ItemManager itemManager;

   public PlayerInteractListener(@NotNull StaffManager staffManager) {
      this.itemManager = staffManager.getItemManager();
   }

   @EventHandler
   public void onPlayerInteract(@NotNull PlayerInteractEvent interactEvent) {
      ItemStack itemStack = interactEvent.getItem();
      if (itemStack != null && itemStack.getType() != Material.AIR) {
         if (itemStack.getType() == Material.FIREWORK_ROCKET) {
            UserModel userModel = UserHandler.find(interactEvent.getPlayer());
            if (userModel != null && !(userModel instanceof StaffUserModel)) {
               interactEvent.setCancelled(true);
            }
         } else if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
            Player player = interactEvent.getPlayer();
            UserModel userModel = UserHandler.find(player);
            if (userModel instanceof StaffUserModel) {
               int slotInteract = player.getInventory().getHeldItemSlot();
               ItemAction<PlayerInteractEvent> itemAction = this.itemAction(slotInteract);
               if (itemAction != null) {
                  itemAction.getAction().execute(interactEvent);
               }
            }
         }
      }

   }

   @Nullable
   private ItemAction<PlayerInteractEvent> itemAction(int slot) {
      if (slot == this.itemManager.getVanishItem().getInventorySlot()) {
         return this.itemManager.getVanishItem();
      } else {
         return slot == this.itemManager.getRandomTeleportItem().getInventorySlot() ? this.itemManager.getRandomTeleportItem() : null;
      }
   }
}
