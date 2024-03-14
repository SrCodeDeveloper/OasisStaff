package me.srcode.oasisstaff.listener;

import me.srcode.oasisstaff.inventories.EnderChestPreview;
import me.srcode.oasisstaff.inventories.InventoryPreview;
import me.srcode.oasisstaff.helper.PluginHelper;
import me.srcode.oasisstaff.manager.ItemManager;
import me.srcode.oasisstaff.manager.StaffManager;
import me.srcode.oasisstaff.user.UserHandler;
import me.srcode.oasisstaff.user.types.FreezeUserModel;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class InventoryListener implements Listener {
   private final ItemManager itemManager;
   private final FileConfiguration configuration;

   public InventoryListener(@NotNull StaffManager staffManager, @NotNull FileConfiguration configuration) {
      this.itemManager = staffManager.getItemManager();
      this.configuration = configuration;
   }

   @EventHandler
   public void onInventoryClickEvent(InventoryClickEvent clickEvent) {
      Player player = (Player)clickEvent.getWhoClicked();
      Inventory inventory = clickEvent.getClickedInventory();
      if (inventory != null) {
         InventoryHolder inventoryHolder = inventory.getHolder();
         if (inventoryHolder instanceof EnderChestPreview) {
            clickEvent.setCancelled(true);
         } else {
            InventoryHolder var6 = inventory.getHolder();
            int slotInteract;
            if (var6 instanceof InventoryPreview) {
               InventoryPreview inventoryPreview = (InventoryPreview)var6;
               if (inventory.getType() == InventoryType.CHEST) {
                  slotInteract = clickEvent.getSlot();
                  if ((slotInteract < 0 || slotInteract > 35) && (slotInteract < 45 || slotInteract > 49) && slotInteract != 53) {
                     if ((slotInteract < 36 || slotInteract > 43) && slotInteract != 51) {
                        if (slotInteract == 52) {
                           clickEvent.setCancelled(true);
                           if (!player.hasPermission("kingdomstaff.preview.enderchest")) {
                              player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.inventory-see-enderchest-cancel")));
                           } else if (player.hasPermission("kingdomstaff.remove.enderchest")) {
                              player.openInventory(inventoryPreview.getPlayer().getEnderChest());
                           } else {
                              player.openInventory((new EnderChestPreview(inventoryPreview.getPlayer())).getInventory());
                           }
                        } else {
                           clickEvent.setCancelled(true);
                           if (!player.hasPermission("kingdomstaff.inventory.remove")) {
                              player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.inventory-see-save-cancel")));
                           } else {
                              ItemStack[] itemStacks = new ItemStack[41];

                              for(int index = 0; index <= 35; ++index) {
                                 itemStacks[index] = inventory.getItem(index);
                              }

                              Player targetPlayer = inventoryPreview.getPlayer();
                              PlayerInventory targetInventory = targetPlayer.getInventory();
                              itemStacks[36] = inventory.getItem(49);
                              itemStacks[37] = inventory.getItem(48);
                              itemStacks[38] = inventory.getItem(47);
                              itemStacks[39] = inventory.getItem(46);
                              itemStacks[40] = inventory.getItem(53);
                              itemStacks[targetInventory.getHeldItemSlot()] = inventory.getItem(45);
                              targetInventory.setContents(itemStacks);
                              player.sendMessage(PluginHelper.toFormat(this.configuration.getString("messages.inventory-see-save-done")));
                           }
                        }
                     } else {
                        clickEvent.setCancelled(true);
                     }
                  } else if (!player.hasPermission("kingdomstaff.inventory.remove")) {
                     clickEvent.setCancelled(true);
                  }
               }
            } else if (UserHandler.isLoaded(player)) {
               ClickType clickType = clickEvent.getClick();
               if (clickType != ClickType.CONTROL_DROP && clickType != ClickType.NUMBER_KEY) {
                  if (UserHandler.find(player) instanceof FreezeUserModel) {
                     clickEvent.setCancelled(true);
                  } else {
                     slotInteract = clickEvent.getSlot();
                     if (this.isSlot(slotInteract)) {
                        clickEvent.setCancelled(true);
                     }
                  }
               } else {
                  clickEvent.setCancelled(true);
               }
            }
         }
      }

   }

   private boolean isSlot(int slot) {
      return slot == this.itemManager.getFreezeItem().getInventorySlot() || slot == this.itemManager.getVanishItem().getInventorySlot() || slot == this.itemManager.getRandomTeleportItem().getInventorySlot();
   }
}
