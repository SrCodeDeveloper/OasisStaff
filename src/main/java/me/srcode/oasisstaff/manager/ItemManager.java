package me.srcode.oasisstaff.manager;

import java.util.Objects;
import me.srcode.oasisstaff.PluginLauncher;
import me.srcode.oasisstaff.builder.ItemAction;
import me.srcode.oasisstaff.helper.PluginHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ItemManager {
   private ItemAction<PlayerInteractEvent> vanishItem;
   private ItemAction<PlayerInteractEvent> randomTeleportItem;
   private ItemAction<PlayerInteractEntityEvent> freezeItem;
   private ItemAction<PlayerInteractEntityEvent> inventoryItem;

   public ItemStack getVanishItemStack() {
      return this.vanishItem.item();
   }

   public ItemStack getUnVanishItemStack() {
      ItemStack itemStack = this.vanishItem.build();
      ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
      FileConfiguration configuration = PluginLauncher.getInstance().getConfig();
      itemStack.setType((Material)Objects.requireNonNull(Material.getMaterial((String)Objects.requireNonNull(configuration.getString("items.vanish.material-2")))));
      itemMeta.displayName(Component.text(PluginHelper.toFormat(configuration.getString("items.vanish.display-name-2"))));
      itemStack.setItemMeta(itemMeta);
      return itemStack;
   }

   public void getItems(@NotNull Player player) {
      PlayerInventory playerInventory = player.getInventory();
      playerInventory.setItem(this.vanishItem.getInventorySlot(), this.vanishItem.item());
      playerInventory.setItem(this.freezeItem.getInventorySlot(), this.freezeItem.item());
      playerInventory.setItem(this.randomTeleportItem.getInventorySlot(), this.randomTeleportItem.item());
      playerInventory.setItem(this.inventoryItem.getInventorySlot(), this.inventoryItem.item());
   }

   public ItemAction<PlayerInteractEvent> getVanishItem() {
      return this.vanishItem;
   }

   public ItemAction<PlayerInteractEvent> getRandomTeleportItem() {
      return this.randomTeleportItem;
   }

   public ItemAction<PlayerInteractEntityEvent> getFreezeItem() {
      return this.freezeItem;
   }

   public ItemAction<PlayerInteractEntityEvent> getInventoryItem() {
      return this.inventoryItem;
   }

   public void setVanishItem(ItemAction<PlayerInteractEvent> vanishItem) {
      this.vanishItem = vanishItem;
   }

   public void setRandomTeleportItem(ItemAction<PlayerInteractEvent> randomTeleportItem) {
      this.randomTeleportItem = randomTeleportItem;
   }

   public void setFreezeItem(ItemAction<PlayerInteractEntityEvent> freezeItem) {
      this.freezeItem = freezeItem;
   }

   public void setInventoryItem(ItemAction<PlayerInteractEntityEvent> inventoryItem) {
      this.inventoryItem = inventoryItem;
   }
}
