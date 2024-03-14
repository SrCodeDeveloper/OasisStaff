package me.srcode.oasisstaff.user.data;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class UserDataContainer {
   protected final ItemStack[] armorContents;
   protected final ItemStack[] inventoryContents;
   protected final GameMode gameMode;
   protected final boolean flight;
   protected final int foodLevel;

   public UserDataContainer(@NotNull ItemStack[] armorContents, @NotNull ItemStack[] inventoryContents, @NotNull GameMode gameMode, boolean flight, int foodLevel) {
      this.armorContents = armorContents;
      this.inventoryContents = inventoryContents;
      this.gameMode = gameMode;
      this.flight = flight;
      this.foodLevel = foodLevel;
   }

   public abstract void restore(@NotNull Player var1);
}
