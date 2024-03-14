package me.srcode.oasisstaff.builder;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemAction<T> extends ItemBuilder {
   private Actionable<T> action;
   private ItemStack itemStack;
   private final int inventorySlot;

   public ItemAction(@Nullable String name, @Nullable List<String> lore, @NotNull Material material, int inventorySlot) {
      super(name, lore, material);
      this.inventorySlot = inventorySlot;
   }

   public ItemStack item() {
      if (this.itemStack == null) {
         this.itemStack = this.build();
      }

      return this.itemStack;
   }

   public ItemAction<T> action(@NotNull Actionable<T> action) {
      this.action = action;
      return this;
   }

   public Actionable<T> getAction() {
      return this.action;
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }

   public int getInventorySlot() {
      return this.inventorySlot;
   }

   public void setAction(Actionable<T> action) {
      this.action = action;
   }
}
