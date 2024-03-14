package me.srcode.oasisstaff.listener;

import me.srcode.oasisstaff.builder.ItemAction;
import me.srcode.oasisstaff.manager.ItemManager;
import me.srcode.oasisstaff.manager.StaffManager;
import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.types.StaffUserModel;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class PlayerInteractEntityListener implements Listener {
   private final ItemManager manager;

   public PlayerInteractEntityListener(@NotNull StaffManager manager) {
      this.manager = manager.getItemManager();
   }

   @EventHandler
   public void onPlayerInteract(PlayerInteractEntityEvent interactEvent) {
      if (interactEvent.getHand() == EquipmentSlot.HAND) {
         Entity entity = interactEvent.getRightClicked();
         if (entity instanceof Player) {
            Player player = interactEvent.getPlayer();
            if (UserHandler.find(player) instanceof StaffUserModel) {
               PlayerInventory playerInventory = player.getInventory();
               int slotInteract = playerInventory.getHeldItemSlot();
               ItemAction<PlayerInteractEntityEvent> itemAction = this.getItem(slotInteract);
               if (itemAction != null) {
                  itemAction.getAction().execute(interactEvent);
               }
            }
         }
      }

   }

   private ItemAction<PlayerInteractEntityEvent> getItem(int slotInteract) {
      if (this.manager.getFreezeItem().getInventorySlot() == slotInteract) {
         return this.manager.getFreezeItem();
      } else {
         return this.manager.getInventoryItem().getInventorySlot() == slotInteract ? this.manager.getInventoryItem() : null;
      }
   }
}
