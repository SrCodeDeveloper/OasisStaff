package me.srcode.oasisstaff.inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class EnderChestPreview implements InventoryHolder {
   private final Player player;
   private final Inventory inventory;

   public EnderChestPreview(@NotNull Player player) {
      this.player = player;
      this.inventory = Bukkit.createInventory(this, 27, player.getEnderChest().getType().defaultTitle());
      this.init();
   }

   private void init() {
      this.inventory.setContents(this.player.getEnderChest().getContents());
   }

   @NotNull
   public Inventory getInventory() {
      return this.inventory;
   }
}
