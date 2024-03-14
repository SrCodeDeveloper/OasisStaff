package me.srcode.oasisstaff.inventories;

import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.builder.ItemBuilder;
import me.srcode.oasisstaff.helper.PluginHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class InventoryPreview implements InventoryHolder {
   private final FileConfiguration configuration;
   private final Inventory inventory;
   private final Player player;

   public InventoryPreview(PluginLauncher pluginLauncher, Player player) {
      this.configuration = pluginLauncher.getConfig();
      this.player = player;
      this.inventory = Bukkit.createInventory(this, 54, Component.text(PluginHelper.toFormat(this.configuration.getString("inventory-preview-name")).replace("<player>", player.getName())));
      this.initialize();
   }

   private void initialize() {
      PlayerInventory playerInventory = this.player.getInventory();

      int index;
      for(index = 0; index <= 9; ++index) {
         if (index == playerInventory.getHeldItemSlot()) {
            this.inventory.setItem(index, ItemBuilder.builder().material(Material.RED_STAINED_GLASS_PANE).build());
         } else {
            this.inventory.setItem(index, playerInventory.getItem(index));
         }
      }

      for(index = 9; index <= 35; ++index) {
         this.inventory.setItem(index, playerInventory.getItem(index));
      }

      ItemStack refillItem = new ItemStack(Material.CYAN_STAINED_GLASS_PANE);

      for(index = 36; index <= 44; ++index) {
         if (index == 40) {
            this.inventory.setItem(index, ItemBuilder.builder().name(this.configuration.getString("inventory-see.information.display-name")).loreWithPlaceholder(this.player, this.configuration.getStringList("inventory-see.information.lore")).material(Material.GOLDEN_APPLE).build());
         } else if (index == 44) {
            this.inventory.setItem(index, ItemBuilder.builder().name(this.configuration.getString("inventory-see.save-inventory.display-name")).lore(this.configuration.getStringList("inventory-see.save-inventory.lore")).material(Material.NETHER_STAR).build());
         } else {
            this.inventory.setItem(index, refillItem);
         }
      }

      this.inventory.setItem(50, refillItem);
      this.inventory.setItem(51, refillItem);
      this.inventory.setItem(45, playerInventory.getItemInMainHand());
      this.inventory.setItem(46, playerInventory.getHelmet());
      this.inventory.setItem(47, playerInventory.getChestplate());
      this.inventory.setItem(48, playerInventory.getLeggings());
      this.inventory.setItem(49, playerInventory.getBoots());
      this.inventory.setItem(52, ItemBuilder.builder().name(this.configuration.getString("inventory-see.enderchest.display-name")).lore(this.configuration.getStringList("inventory-see.enderchest.lore")).material(Material.ENDER_CHEST).build());
      this.inventory.setItem(53, playerInventory.getItemInOffHand());
   }

   @NotNull
   public Inventory getInventory() {
      return this.inventory;
   }

   public Player getPlayer() {
      return this.player;
   }
}
